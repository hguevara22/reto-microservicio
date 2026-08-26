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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retotcs.accountservice.dto.CuentaRequestDTO;
import com.retotcs.accountservice.dto.CuentaResponseDTO;
import com.retotcs.accountservice.dto.CuentaUpdateRequestDTO;
import com.retotcs.accountservice.services.CuentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controlador REST para la gestión de cuentas bancarias.
 * 
 * Expone la API RESTful para creación, consulta, actualización y eliminación de
 * cuentas.
 * 
 * @author Henry Guevara
 * @version 1.0
 */
@RestController
@RequestMapping("/cuentas")
@Tag(name = "Cuentas", description = "Endpoints para la gestión de cuentas del sistema")
public class CuentaController {

	private final CuentaService cuentaService;

	CuentaController(CuentaService cuentaService) {
		this.cuentaService = cuentaService;
	}

	@Operation(summary = "Obtener todas las cuentas", description = "Retorna el listado completo de cuentas registradas")
	@ApiResponse(responseCode = "200", description = "Lista recuperada exitosamente")
	@GetMapping
	public ResponseEntity<List<CuentaResponseDTO>> obtenerCuentas() {
		return ResponseEntity.ok(cuentaService.obtenerCuentas());
	}

	@Operation(summary = "Crear nueva cuenta", description = "Registra una nueva cuenta bancaria en el sistema")
	@ApiResponse(responseCode = "201", description = "Cuenta creada exitosamente")
	@ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
	@PostMapping
	public ResponseEntity<CuentaResponseDTO> crearCuenta(@Valid @RequestBody CuentaRequestDTO cuentaDTO) {
		return new ResponseEntity<>(cuentaService.crearCuenta(cuentaDTO), HttpStatus.CREATED);
	}

	@Operation(summary = "Obtener cuenta por ID", description = "Busca y retorna los detalles de una cuenta dada su clave primaria")
	@ApiResponse(responseCode = "200", description = "Cuenta encontrada")
	@ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
	@GetMapping("/{id}")
	public ResponseEntity<CuentaResponseDTO> obtenerPorId(@PathVariable Long id) {
		return ResponseEntity.ok(cuentaService.obtenerCuentaPorId(id));
	}

	@Operation(summary = "Obtener cuenta por número", description = "Busca una cuenta bancaria mediante su número de cuenta único")
	@ApiResponse(responseCode = "200", description = "Cuenta encontrada")
	@ApiResponse(responseCode = "404", description = "Número de cuenta no registrado")
	@GetMapping("/numero/{numeroCuenta}")
	public ResponseEntity<CuentaResponseDTO> obtenerPorNumeroCuenta(@PathVariable String numeroCuenta) {
		return ResponseEntity.ok(cuentaService.obtenerCuentaPorNumero(numeroCuenta));
	}

	@Operation(summary = "Obtener cuentas por cliente ID", description = "Retorna todas las cuentas asociadas al ID de un cliente específico")
	@ApiResponse(responseCode = "200", description = "Cuentas recuperadas exitosamente")
	@GetMapping("/cliente/{clienteId}")
	public ResponseEntity<List<CuentaResponseDTO>> obtenerPorClienteId(@PathVariable String clienteId) {
		return ResponseEntity.ok(cuentaService.obtenerCuentaPorClienteId(clienteId));
	}

	@Operation(summary = "Actualizar cuenta", description = "Modifica los datos de una cuenta existente según su ID")
	@ApiResponse(responseCode = "200", description = "Cuenta actualizada con éxito")
	@ApiResponse(responseCode = "400", description = "Datos de actualización inválidos")
	@ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
	@PutMapping("/{id}")
	public ResponseEntity<CuentaResponseDTO> actualizar(@PathVariable Long id,
			@Valid @RequestBody CuentaUpdateRequestDTO cuentaDTO) {
		CuentaResponseDTO cuentaActualizada = cuentaService.modificarCuenta(id, cuentaDTO);
		return ResponseEntity.ok(cuentaActualizada);
	}

	@Operation(summary = "Eliminar cuenta", description = "Elimina un registro de cuenta bancaria según su ID")
	@ApiResponse(responseCode = "200", description = "Cuenta eliminada exitosamente")
	@ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {

		cuentaService.eliminarCuenta(id);
		Map<String, String> response = new HashMap<>();
		response.put("mensaje", "La cuenta " + id + " fue eliminada exitosamente");

		return ResponseEntity.ok(response); // Status 200 + Body JSON

	}

}
