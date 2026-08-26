package com.retotcs.accountservice.persistence.repositories;



import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retotcs.accountservice.persistence.entities.Cuenta;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long>{

	boolean existsByNumeroCuenta(String numeroCuenta);
	
	Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
	
	// Retorna una lista con todas las cuentas del cliente especificado
    List<Cuenta> findByClienteId(String clienteId);
}
