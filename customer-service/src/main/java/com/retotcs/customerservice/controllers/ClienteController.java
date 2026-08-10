package com.retotcs.customerservice.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.retotcs.customerservice.dto.ClienteRequestDTO;
import com.retotcs.customerservice.dto.ClienteResponseDTO;
import com.retotcs.customerservice.dto.ClienteUpdateRequestDTO;
import com.retotcs.customerservice.services.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

/**
 * Controlador REST para la gestión de clientes.
 * 
 * Expone la API RESTful para el registro, consulta, actualización y eliminación de clientes.
 * 
 * @author Henry Guevara
 * @version 1.0
 */
@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Endpoints para la gestión de clientes del sistema")
public class ClienteController {

	private final ClienteService clienteService;

	// Inyección por constructor
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@Operation(summary = "Obtener todos los clientes", description = "Retorna el listado completo de clientes registrados")
	@ApiResponse(responseCode = "200", description = "Lista recuperada exitosamente")
	@GetMapping
	public ResponseEntity<List<ClienteResponseDTO>> obtenerClientes() {
		return ResponseEntity.ok(clienteService.obtenerClientes());
	}

	@Operation(summary = "Registrar un cliente", description = "Crea un nuevo cliente en el sistema y notifica a los servicios suscritos")
	@ApiResponse(responseCode = "201", description = "Cliente creado exitosamente")
	@ApiResponse(responseCode = "400", description = "Datos de entrada no válidos")
	@PostMapping
	public ResponseEntity<ClienteResponseDTO> crearCliente(@Valid @RequestBody ClienteRequestDTO clienteDTO) {
		return new ResponseEntity<>(clienteService.crearCliente(clienteDTO), HttpStatus.CREATED);
	}

	@Operation(summary = "Obtener cliente por ID", description = "Busca y retorna un cliente dado su identificador único")
	@ApiResponse(responseCode = "200", description = "Cliente encontrado")
	@ApiResponse(responseCode = "404", description = "Cliente no encontrado")
	@GetMapping("/{id}")
	public ResponseEntity<ClienteResponseDTO> obtenerClientePorId(@PathVariable Long id) {
		ClienteResponseDTO cliente = clienteService.obtenerClientePorId(id);
		return ResponseEntity.ok(cliente);
	}

	@Operation(summary = "Actualizar cliente", description = "Modifica la información de un cliente existente")
	@ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente")
	@ApiResponse(responseCode = "404", description = "Cliente no encontrado")
	@PutMapping("/{id}")
	public ResponseEntity<ClienteResponseDTO> modificarCliente(@PathVariable Long id,
			@RequestBody ClienteUpdateRequestDTO clienteDTO) {
		ClienteResponseDTO clienteActualizado = clienteService.modificarCliente(id, clienteDTO);
		return ResponseEntity.ok(clienteActualizado);
	}

	@Operation(summary = "Eliminar cliente", description = "Elimina un registro de cliente según su ID")
	@ApiResponse(responseCode = "200", description = "Cliente eliminado con éxito")
	@ApiResponse(responseCode = "404", description = "Cliente no encontrado")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> eliminarCliente(@PathVariable Long id) {
		clienteService.eliminarCliente(id);
		Map<String, String> response = new HashMap<>();
		response.put("mensaje", "El cliente " + id + " fue eliminado exitosamente");

		return ResponseEntity.ok(response); // Status 200 + Body JSON
	}

}
