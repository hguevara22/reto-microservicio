package com.retotcs.customerservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.retotcs.customerservice.dto.ClienteResponseDTO;
import com.retotcs.customerservice.persistence.entities.Cliente;
import com.retotcs.customerservice.persistence.repositories.ClienteRepository;
import com.retotcs.customerservice.services.ClienteServiceImpl;
import com.retotcs.customerservice.exception.ResourceNotFoundException;

import java.util.Optional;


@ExtendWith(MockitoExtension.class) // <--- OBLIGATORIO para inicializar los mocks
public class ClienteServiceTest {

	@Mock
	private ClienteRepository clienteRepository;

	@InjectMocks
	private ClienteServiceImpl clienteService;

	private Cliente cliente;
	private ClienteResponseDTO clienteResponseDTO;

	@BeforeEach
	void setUp() {
		cliente = new Cliente();
	}

	@Test
	@DisplayName("Debería retornar una lista clientes")
	void testObtenerClientes() {
		// Given
	    when(clienteRepository.findAll()).thenReturn(List.of(cliente));

	    // When
	    List<ClienteResponseDTO> listaClientes = clienteService.obtenerClientes();

	    // Then
	    assertNotNull(listaClientes);
	    assertFalse(listaClientes.isEmpty());
	    assertEquals(1, listaClientes.size());
	    
	    // Validación de contenido de los campos
	    ClienteResponseDTO dto = listaClientes.get(0);
	    assertEquals(cliente.getClienteId(), dto.getClienteId());
	    assertEquals(cliente.getNombre(), dto.getNombre());

	    // Verificación de interacción con el repositorio
	    verify(clienteRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("Debería retornar un cliente por su ID exitosamente")
	void testFindClienteById_Exitoso() {
	    // Given
	    Long clienteId = 3L;
	    when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

	    // When
	    ClienteResponseDTO resultado = clienteService.obtenerClientePorId(clienteId);

	    // Then
	    assertNotNull(resultado);
	    assertEquals(cliente.getNombre(), resultado.getNombre());
	    assertEquals(cliente.getIdentificacion(), resultado.getIdentificacion());

	    // Verificación
	    verify(clienteRepository, times(1)).findById(clienteId);
	}
	
	@Test
	@DisplayName("Debería lanzar ResourceNotFoundException cuando el cliente no existe")
	void testFindClienteById_NoEncontrado() {
	    // Given
	    Long clienteId = 99L;
	    when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

	    // When & Then
	    assertThrows(ResourceNotFoundException.class, () -> {
	        clienteService.obtenerClientePorId(clienteId);
	    });

	    verify(clienteRepository, times(1)).findById(clienteId);
	}


}
