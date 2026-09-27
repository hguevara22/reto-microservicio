package com.retotcs.accountservice.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.retotcs.accountservice.dto.EstadoCuentaDTO;
import com.retotcs.accountservice.persistence.entities.Cuenta;
import com.retotcs.accountservice.persistence.entities.Movimiento;
import com.retotcs.accountservice.persistence.repositories.CuentaRepository;
import com.retotcs.accountservice.persistence.repositories.MovimientoRepository;

@Service
public class ReporteServiceImpl implements ReporteService {

	private final CuentaRepository cuentaRepository;
	private final MovimientoRepository movimientoRepository;

	public ReporteServiceImpl(CuentaRepository cuentaRepository, MovimientoRepository movimientoRepository) {
		this.cuentaRepository = cuentaRepository;
		this.movimientoRepository = movimientoRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<EstadoCuentaDTO> generarReporteEstadoCuenta(String clienteId, LocalDate fechaInicio,
			LocalDate fechaFin) {
		// Ajustar el rango a inicio (00:00:00) y fin del día (23:59:59)
		LocalDateTime inicio = fechaInicio.atStartOfDay();
		LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);

		// Buscar todas las cuentas asociadas al cliente
		List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);

		// Mapear cada cuenta con sus movimientos en el rango de fechas
		return cuentas.stream().map(cuenta -> {
			List<Movimiento> movimientos = movimientoRepository.findByCuentaIdAndFechaBetween(cuenta.getId(), inicio,
					fin);

			List<EstadoCuentaDTO.MovimientoReporteDTO> movimientosDto = movimientos.stream()
					.map(m -> EstadoCuentaDTO.MovimientoReporteDTO.builder().id(m.getId()).fecha(m.getFecha())
							.tipoMovimiento(m.getTipoMovimiento() != null ? m.getTipoMovimiento().name() : null)
							.valor(m.getValor()).saldo(m.getSaldo()).build())
					.toList();

			return EstadoCuentaDTO.builder().clienteId(cuenta.getClienteId()).numeroCuenta(cuenta.getNumeroCuenta())
					.tipoCuenta(cuenta.getTipoCuenta() != null ? cuenta.getTipoCuenta().name() : null)
					.saldoInicial(cuenta.getSaldoInicial()).estado(cuenta.getEstado())
					.saldoActual(cuenta.getSaldoDisponible()).movimientos(movimientosDto).build();
		}).toList();

	}

}
