package trab1.Banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import trab1.Banco.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, String>{

}
