package com.retotcs.accountservice.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retotcs.accountservice.dto.MovimientoRequestDTO;
import com.retotcs.accountservice.dto.MovimientoResponseDTO;
import com.retotcs.accountservice.services.MovimientoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controlador REST para la gestión de transacciones y movimientos bancarios.
 * 
 * Expone la API RESTful para el registro, consulta y eliminación de
 * movimientos.
 * 
 * @author Henry Guevara
 * @version 1.0
 */
@RestController
@RequestMapping("/movimientos")
@Tag(name = "Movimientos", description = "Endpoints para la gestión de transacciones y movimientos bancarios")
public class MovimientoController {

	private final MovimientoService movimientoService;

	MovimientoController(MovimientoService movimientoService) {
		this.movimientoService = movimientoService;
	}

	@Operation(summary = "Obtener todos los movimientos", description = "Retorna la lista completa de transacciones bancarias registradas")
	@ApiResponse(responseCode = "200", description = "Lista de movimientos obtenida exitosamente")
	@GetMapping
	public ResponseEntity<List<MovimientoResponseDTO>> obtenerMovimientos() {
		return ResponseEntity.ok(movimientoService.obtenerMovimientos());
	}

	@Operation(summary = "Obtener movimiento por ID", description = "Busca y retorna un movimiento específico según su ID")
	@ApiResponse(responseCode = "200", description = "Movimiento encontrado")
	@ApiResponse(responseCode = "404", description = "Movimiento no encontrado")
	@GetMapping("/{id}")
	public ResponseEntity<MovimientoResponseDTO> obtenerPorId(@PathVariable Long id) {
		return ResponseEntity.ok(movimientoService.obtenerMovimientoPorId(id));
	}

	@Operation(summary = "Registrar nuevo movimiento", description = "Procesa un depósito o retiro afectando el saldo de la cuenta asociada")
	@ApiResponse(responseCode = "201", description = "Movimiento registrado y procesado exitosamente")
	@ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o saldo insuficiente")
	@ApiResponse(responseCode = "404", description = "Cuenta asociada no encontrada")
	@PostMapping
	public ResponseEntity<MovimientoResponseDTO> registrarMovimiento(@Valid @RequestBody MovimientoRequestDTO dto) {
		MovimientoResponseDTO nuevoMovimiento = movimientoService.registrarMovimiento(dto);
		return new ResponseEntity<>(nuevoMovimiento, HttpStatus.CREATED);
	}

	@Operation(summary = "Eliminar movimiento", description = "Elimina un registro de transacción según su ID")
	@ApiResponse(responseCode = "200", description = "Movimiento eliminado con éxito")
	@ApiResponse(responseCode = "404", description = "Movimiento no encontrado")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
		movimientoService.eliminarMovimiento(id);

		Map<String, String> response = new HashMap<>();
		response.put("mensaje", "El movimiento " + id + " fue eliminado exitosamente");

		return ResponseEntity.ok(response); // Status 200 + Body JSON

	}
}
