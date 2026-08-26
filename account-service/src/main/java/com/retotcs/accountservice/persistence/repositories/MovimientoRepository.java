package com.retotcs.accountservice.persistence.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retotcs.accountservice.persistence.entities.Movimiento;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long>{

	Optional<Movimiento> findById(Long id);
	List<Movimiento> findByCuentaIdAndFechaBetween(Long id,LocalDateTime inicio,LocalDateTime fin);
}
