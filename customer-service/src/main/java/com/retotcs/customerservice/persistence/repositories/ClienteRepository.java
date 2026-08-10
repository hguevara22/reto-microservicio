package com.retotcs.customerservice.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.retotcs.customerservice.persistence.entities.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	boolean existsByIdentificacion(String identificacion);
    boolean existsByClienteId(String clienteId);
  
}
