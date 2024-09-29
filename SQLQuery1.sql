CREATE DATABASE sistemabancario
GO
USE sistemabancario
SELECT * FROM cliente
CREATE TABLE cliente (
cpf					VARCHAR(14),
nome				VARCHAR(100),
dataPrimeiraConta	DATE,
senha				VARCHAR(25),
PRIMARY KEY (cpf)
)

CREATE TABLE agencia (
codigo			VARCHAR(20),
nome			VARCHAR(100),
cep				VARCHAR(10),
cidade			VARCHAR(30), 
PRIMARY KEY (codigo)
)

CREATE TABLE conta (
cpfCliente1			VARCHAR(14),
cpfCliente2			VARCHAR(14),
codigoAgencia		VARCHAR(20),
codigo				VARCHAR(30),
dataAbertura		DATE,
saldo				DECIMAL(10,2),
FOREIGN KEY (cpfCliente1) REFERENCES cliente(cpf),
FOREIGN KEY (cpfCliente2) REFERENCES cliente(cpf),
FOREIGN KEY (codigoAgencia) REFERENCES agencia(codigo),
PRIMARY KEY (codigo)
)


CREATE TABLE contacorrente (
codigo				VARCHAR(30),
limiteCredito		DECIMAL(7,2),
FOREIGN KEY (codigo) REFERENCES conta(codigo),
PRIMARY KEY (codigo)
)

CREATE TABLE contapoupanca (
codigo					VARCHAR(30),
percentualRendimento	DECIMAL(3,2),
diaAniversario			INT,
FOREIGN KEY (codigo) REFERENCES conta(codigo),
PRIMARY KEY (codigo)
)

/*O código da conta deve ser o código da agência, concatenado com os 3
últimos dígitos do CPF do titular (Se for conta conjunta, deve trazer os dois),
concatenado com um dígito verificador CHECK: FUNCIONANDO*/

CREATE PROCEDURE sp_geracodigoconta(@codAgencia VARCHAR(20), @cpfCliente1 VARCHAR(14), @cpfCliente2 VARCHAR(14), @codConta VARCHAR(30) OUTPUT)
AS
	DECLARE @temp VARCHAR(40)
	SET @cpfCliente1 = SUBSTRING(@cpfCliente1, 9, 3)
	IF (@cpfCliente2 IS NOT NULL)
		BEGIN
			SET @cpfCliente2 = SUBSTRING(@cpfCliente1, 9, 3)
			SET @cpfCliente1 = @cpfCliente1 + @cpfCliente2
		END
	SET @temp = @codAgencia + @cpfCliente1
	SET @codConta = @temp 
	EXEC sp_geraDigito @codConta, @codConta OUT
/*• O dígito verificador é a soma de todos os dígitos do RA gerado anteriormente
e o resultado dividido por 5, por fim, pega-se apenas a parte inteira do resto
da divisão. CHECK: FUNCIONANDO*/
CREATE PROCEDURE sp_geraDigito(@codigo VARCHAR(30), @codigoDigito VARCHAR(30) OUTPUT)
AS
	DECLARE @n INT, @digito INT
	SET @digito = 0
	SET @n = 1
	WHILE @n <= (LEN(@codigo) + 1)
		BEGIN
			SET @digito = @digito + CAST(SUBSTRING(@codigo, @n, 1) AS INT)
			SET @n = @n + 1
		END	
		SET @digito = @digito / 5
		SET @codigoDigito = @codigo + CAST(@digito AS VARCHAR(3))

/*• A senha do aluno para acesso ao acervo deve ser cadastrada pelo usuário e,
deve ter 8 caracteres, sendo que pelo menos 3 deles devem ser numéricos. CHECK: FUNCIONANDO*/ 
CREATE PROCEDURE sp_verificaSenhaCliente(@senha VARCHAR(9), @erro VARCHAR(200) OUTPUT)
AS
	DECLARE @cont INT
	DECLARE @num INT
	SET @num = 0
	SET @cont = 1
	IF(LEN(@senha) = 8)
	BEGIN
		
		WHILE(@cont <= LEN(@senha))
		BEGIN
			IF( ISNUMERIC(SUBSTRING(@senha, @cont, 1)) = 1)
			BEGIN
				SET @num = @num + 1
			END
			SET @cont = @cont + 1
		END
		IF(@num < 3)
		BEGIN
			SET @erro = 'A senha deve ter pelo menos 3 números'			
		END
		
	END
		
	 ELSE IF (LEN(@senha) != 8)
	 BEGIN
		SET @erro = 'A senha deve ter 8 caracteres'
	 END

/*• Clientes com contas conjuntas não podem ser excluídos da base. STATUS: funcionando*/

CREATE PROCEDURE sp_verifcontaconj(@cpf VARCHAR(14), @existe BIT OUTPUT)
AS
IF ((SELECT cpfCliente2 FROM conta WHERE cpfCliente1 = @cpf) IS NULL AND (SELECT cpfCliente2 FROM conta WHERE cpfCliente2 = @cpf) IS NULL)
BEGIN
	SET @existe = 1
END
ELSE
BEGIN
	SET @existe = 0
END

CREATE PROCEDURE sp_excluiconta(@cpf VARCHAR(14))
AS
	DECLARE @valido BIT
	EXEC sp_verifcontaconj @cpf, @valido OUT
	IF (@valido = 1)
	BEGIN
		
		DELETE FROM contacorrente
		WHERE codigo = (SELECT codigo FROM conta WHERE cpfCliente1 = @cpf) 

		DELETE FROM contapoupanca
		WHERE codigo = (SELECT codigo FROM conta WHERE cpfCliente1 = @cpf) 

		DELETE FROM conta
		WHERE cpfCliente1 = @cpf
	END
	

/*• O sistema deve permitir atualizar a senha do cliente, o saldo, o limite de crédito e o
percentual de rendimento da poupança. Outros atributos não podem ser
atualizados.	status : FUNCIONANDO*/

CREATE PROCEDURE sp_updateClienteSenha(@cpf VARCHAR(14), @senha VARCHAR(20))
AS
DECLARE @erro VARCHAR(100)
BEGIN TRY
		UPDATE cliente
		SET senha = @senha
		WHERE cpf = @cpf
END TRY
BEGIN CATCH
	SET @erro = ERROR_MESSAGE()
	RAISERROR(@erro, 16, 1)
END CATCH

CREATE PROCEDURE sp_updateContaSaldo(@codigo VARCHAR(30), @saldo DECIMAL(10,2))
AS
	DECLARE @erro VARCHAR(100)
	BEGIN TRY
		UPDATE conta
		SET saldo = @saldo
		WHERE codigo = @codigo
	END TRY
	BEGIN CATCH
		SET @erro = ERROR_MESSAGE()
		RAISERROR(@erro, 16, 1)
	END CATCH

CREATE PROCEDURE sp_updateContaLimCredito(@codigo VARCHAR(30), @limite DECIMAL(7,2))
AS
	DECLARE @erro VARCHAR(100)
	BEGIN TRY
		UPDATE contacorrente
		SET limiteCredito = @limite
		WHERE codigo = @codigo
	END TRY
	BEGIN CATCH
		SET @erro = ERROR_MESSAGE()
		RAISERROR(@erro, 16, 1)
	END CATCH

CREATE PROCEDURE sp_updateContaPerPoupanca(@codigo VARCHAR(30), @percentual DECIMAL(3,2))
AS
	DECLARE @erro VARCHAR(100)
	BEGIN TRY
		UPDATE contapoupanca
		SET percentualRendimento = @percentual
		WHERE codigo = @codigo
	END TRY
	BEGIN CATCH
		SET @erro = ERROR_MESSAGE()
		RAISERROR(@erro, 16, 1)
	END CATCH

	
	 
/*• Um cliente novo deve preencher seus dados, o tipo de conta escolhida que
inicia com saldo zerado e limite de crédito em 500,00. Se for poupança, o dia
de aniversário padrão é dia 10, com início de rendimento em 1%.   CHECK: FUNCIONANDO*/
CREATE PROCEDURE sp_verificaCPF(@cpf VARCHAR(14), @valido VARCHAR(30) OUTPUT)
AS
	IF (LEN(@cpf) != 11 OR ISNUMERIC(@cpf) = 0)
	BEGIN
		SET @valido = 'CPF inválido.'
	END
	ELSE IF ((SELECT cpf FROM cliente WHERE cpf = @cpf) IS NOT NULL)
	BEGIN
		SET @valido = 'Cliente já cadastrado.'
	END
	
CREATE PROCEDURE sp_insertClienteConta(@nome VARCHAR(100), @cpf VARCHAR(14),
@senha VARCHAR(25), @tipo VARCHAR(20), @erro VARCHAR(200) OUTPUT)
AS
	DECLARE @codigo VARCHAR(30)
	DECLARE @agencia VARCHAR(20)
	BEGIN TRY
	BEGIN TRANSACTION
			INSERT INTO cliente
			VALUES(@cpf,@nome, GETDATE(),@senha)
			SET @agencia = (SELECT TOP 1 codigo from agencia ORDER BY NEWID())

			EXEC sp_geracodigoconta @agencia, @cpf, null, @codigo OUT

			INSERT INTO conta
			VALUES(@cpf, NULL, @agencia, @codigo, GETDATE(), 0)

			IF (@tipo LIKE '%corrente%')
			BEGIN
				INSERT INTO contacorrente 
				VALUES(@codigo, 500)
			END
			IF (@tipo LIKE '%poupança%' OR @tipo LIKE '%poupanca%')
			BEGIN
				INSERT INTO contapoupanca
				VALUES(@codigo, 1, 10)
			END
	COMMIT TRANSACTION
	END TRY
	BEGIN CATCH
		ROLLBACK TRANSACTION
		SET @erro = ERROR_MESSAGE()
		RAISERROR(@erro, 16, 1)
	END CATCH


/*• Para se incluir um(a) companheiro(a) na conta conjunta, esta já precisa
existir e ter um cliente cadastrado. Deve se passar por uma tela de login e
senha para autorizar a inclusão de um cliente na conta conjunta. STATUS: ESTA FUNCIONANDO, tem que usar o inserir sem @transactional */

CREATE PROCEDURE sp_verificaLogin(@cpf VARCHAR(14), @senha VARCHAR(30), @valido BIT OUTPUT)
AS
	IF((SELECT cpf FROM cliente WHERE cpf = @cpf AND senha = @senha) IS NOT NULL)
	BEGIN
		SET @valido = 1
	END
	ELSE
	BEGIN
		SET @valido = 0
	END

CREATE PROCEDURE sp_inserirClienteContaConj(@codigo VARCHAR(30), @cpf2 VARCHAR(14))
AS
DECLARE @validoB BIT
	UPDATE conta
	SET cpfCliente2 = @cpf2
	WHERE codigo = @codigo


/*• Não é permitido incluir um(a) companheiro(a) na conta conjunta em uma
conta com saldo menor ou igual a zero. Salvo conta criada no mesmo dia. CHECK: TESTADO
*/
CREATE PROCEDURE sp_validarContaParaConj(@codigoConta VARCHAR(30), @valido BIT OUTPUT)
AS
	IF((SELECT saldo FROM conta WHERE codigo = @codigoConta) > 0 OR (SELECT dataAbertura FROM conta 
		WHERE codigo = @codigoConta) = GETDATE())
	BEGIN
		SET @valido = 1
	END
	ELSE
	BEGIN
		SET @valido = 0
	END

	

/* Essa procedure foi feita para verificar se o cliente tem uma conta poupança ou corrente cadastrada*/
CREATE PROCEDURE sp_verificarContaCorrenteOuPoupanca(@cpfCliente VARCHAR(14), @tipo VARCHAR(11) OUTPUT)
AS
	DECLARE @codigoConta VARCHAR(30)

	SET @codigoConta = (SELECT codigo FROM conta WHERE cpfCliente1 = @cpfCliente OR cpfCliente2 = @cpfCliente)
	IF((SELECT codigo FROM contacorrente WHERE codigo = @codigoConta) IS NOT NULL)
	BEGIN
		SET @tipo = 'corrente'
	END
	ELSE IF ((SELECT codigo FROM contapoupanca WHERE codigo = @codigoConta) IS NOT NULL)
	BEGIN
		SET @tipo = 'poupanca'
	END