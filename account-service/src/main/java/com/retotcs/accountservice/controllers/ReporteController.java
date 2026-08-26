package com.retotcs.accountservice.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retotcs.accountservice.dto.EstadoCuentaDTO;
import com.retotcs.accountservice.services.ReporteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/reportes")
@Tag(name = "Reportes", description = "Endpoints para la generación de reportes y estados de cuenta")
public class ReporteController {

	private final ReporteService reporteService;

	ReporteController(ReporteService reporteService) {
		this.reporteService = reporteService;
	}

	// GET /reportes?clienteId=CLI-001&fechaInicio=2026-08-01&fechaFin=2026-08-09
	@Operation(summary = "Generar estado de cuenta", description = "Genera el reporte consolidado de estado de cuenta de un cliente con sus movimientos en un rango de fechas determinado")
	@ApiResponse(responseCode = "200", description = "Reporte generado exitosamente")
	@ApiResponse(responseCode = "400", description = "Parámetros de consulta inválidos o formato de fecha incorrecto")
	@ApiResponse(responseCode = "404", description = "Cliente o cuentas no encontradas para el rango especificado")
	@GetMapping
	public ResponseEntity<List<EstadoCuentaDTO>> generarReporte(@RequestParam("clienteId") String clienteId,
			@RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
			@RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

		List<EstadoCuentaDTO> reporte = reporteService.generarReporteEstadoCuenta(clienteId, fechaInicio, fechaFin);
		return ResponseEntity.ok(reporte);
	}
}
