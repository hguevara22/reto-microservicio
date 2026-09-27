package com.retotcs.accountservice.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.retotcs.accountservice.dto.MovimientoRequestDTO;
import com.retotcs.accountservice.dto.MovimientoResponseDTO;
import com.retotcs.accountservice.exception.ResourceNotFoundException;
import com.retotcs.accountservice.exception.SaldoNoDisponibleException;
import com.retotcs.accountservice.persistence.entities.Cuenta;
import com.retotcs.accountservice.persistence.entities.Movimiento;
import com.retotcs.accountservice.persistence.enums.TipoMovimiento;
import com.retotcs.accountservice.persistence.repositories.CuentaRepository;
import com.retotcs.accountservice.persistence.repositories.MovimientoRepository;

@Service
public class MovimientoServiceImpl implements MovimientoService {

	private final MovimientoRepository movimientoRepository;
	private final CuentaRepository cuentaRepository;

	public MovimientoServiceImpl(MovimientoRepository movimientoRepository, CuentaRepository cuentaRepository) {
		this.movimientoRepository = movimientoRepository;
		this.cuentaRepository = cuentaRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<MovimientoResponseDTO> obtenerMovimientos() {

		return movimientoRepository.findAll().stream().map(this::mapearAResponseDTO).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public MovimientoResponseDTO obtenerMovimientoPorId(Long id) {

		Movimiento movimiento = movimientoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Movimiento no encontrado con id: " + id));
		return mapearAResponseDTO(movimiento);
	}

	@Override
	@Transactional
	public MovimientoResponseDTO registrarMovimiento(MovimientoRequestDTO dto) {

		// Validar existencia de la cuenta
		Cuenta cuenta = cuentaRepository.findByNumeroCuenta(dto.getNumeroCuenta()).orElseThrow(
				() -> new ResourceNotFoundException("Cuenta no encontrada con número: " + dto.getNumeroCuenta()));

		// Calcular el nuevo saldo
		BigDecimal nuevoSaldo = cuenta.getSaldoDisponible().add(dto.getValor());

		// Validar si el saldo resulta negativo (Saldo no disponible)
		if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
			throw new SaldoNoDisponibleException("Saldo no disponible");
		}

		// Actualizar el saldo disponible de la cuenta
		cuenta.setSaldoDisponible(nuevoSaldo);
		cuentaRepository.save(cuenta);

		// Crear y guardar el registro del movimiento
		Movimiento movimiento = new Movimiento();
		movimiento.setFecha(LocalDateTime.now(ZoneId.systemDefault()));
		movimiento.setValor(dto.getValor());
		movimiento.setSaldo(nuevoSaldo);
		movimiento.setCuenta(cuenta);

		// Asignar tipo según el signo del valor
		if (dto.getValor().compareTo(BigDecimal.ZERO) > 0) {
			movimiento.setTipoMovimiento(TipoMovimiento.DEPOSITO);
		} else {
			movimiento.setTipoMovimiento(TipoMovimiento.RETIRO);
		}

		Movimiento guardado = movimientoRepository.save(movimiento);
		return mapearAResponseDTO(guardado);
	}

	@Override
	@Transactional
	public void eliminarMovimiento(Long id) {

		if (!movimientoRepository.existsById(id)) {
			throw new ResourceNotFoundException("Movimiento no encontrado con id: " + id);
		}
		movimientoRepository.deleteById(id);

	}

	private MovimientoResponseDTO mapearAResponseDTO(Movimiento movimiento) {
		return MovimientoResponseDTO.builder().id(movimiento.getId()).fecha(movimiento.getFecha())
				.tipoMovimiento(movimiento.getTipoMovimiento()).valor(movimiento.getValor())
				.saldo(movimiento.getSaldo()).numeroCuenta(movimiento.getCuenta().getNumeroCuenta()).build();
	}

}
