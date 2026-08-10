package com.retotcs.customerservice.services;

import java.util.List;

import com.retotcs.customerservice.dto.ClienteRequestDTO;
import com.retotcs.customerservice.dto.ClienteResponseDTO;
import com.retotcs.customerservice.dto.ClienteUpdateRequestDTO;
import com.retotcs.customerservice.persistence.entities.Cliente;

public interface ClienteService {
	
	List<ClienteResponseDTO> obtenerClientes();
	ClienteResponseDTO crearCliente(ClienteRequestDTO clienteDTO);
	
	ClienteResponseDTO obtenerClientePorId(Long id);
    ClienteResponseDTO modificarCliente(Long id, ClienteUpdateRequestDTO clienteDTO);
    void eliminarCliente(Long id);


}
