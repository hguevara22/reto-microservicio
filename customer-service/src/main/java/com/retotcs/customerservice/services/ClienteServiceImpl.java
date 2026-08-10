package com.retotcs.customerservice.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.retotcs.customerservice.config.RabbitMQConfig;
import com.retotcs.customerservice.dto.ClienteEventDTO;
import com.retotcs.customerservice.dto.ClienteRequestDTO;
import com.retotcs.customerservice.dto.ClienteResponseDTO;
import com.retotcs.customerservice.dto.ClienteUpdateRequestDTO;
import com.retotcs.customerservice.exception.RecursoDuplicadoException;
import com.retotcs.customerservice.exception.ResourceNotFoundException;
import com.retotcs.customerservice.persistence.entities.Cliente;
import com.retotcs.customerservice.persistence.repositories.ClienteRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	@Transactional(readOnly = true)
	public List<ClienteResponseDTO> obtenerClientes() {
		// TODO Auto-generated method stub
		return clienteRepository.findAll()
                .stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
	}
	
	@Override
	@Transactional(readOnly = true)
	public ClienteResponseDTO obtenerClientePorId(Long id) {	
		// TODO Auto-generated method stub
		Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con el ID: " + id));
        return mapearAResponseDTO(cliente);
	}
	
	@Override
    @Transactional
    public ClienteResponseDTO crearCliente(ClienteRequestDTO clienteDTO) {
        // Validaciones de negocio 
        if (clienteRepository.existsByIdentificacion(clienteDTO.getIdentificacion())) {
        	throw new RecursoDuplicadoException("La identificación " + clienteDTO.getIdentificacion() + " ya está registrada.");
        }

        if (clienteRepository.existsByClienteId(clienteDTO.getClienteId())) {
        	throw new RecursoDuplicadoException("Ya existe un cliente registrado con el código: " + clienteDTO.getClienteId()); 
        }
	
        // Mapeo de DTO a Entidad utilizando el @SuperBuilder de Lombok
        Cliente cliente = mapearAEntity(clienteDTO);

        // Guardar en la base de datos (se guardará en las tablas 'personas' y 'clientes' automáticamente por la estrategia JOINED)
        Cliente clienteGuardado = clienteRepository.save(cliente);
        
     // 2. Intento de publicación en RabbitMQ
        try {
     // Evento Asincrónico - Publicar evento asíncrono hacia RabbitMQ
        ClienteEventDTO event = new ClienteEventDTO(
        		clienteGuardado.getClienteId(),
        		clienteGuardado.getNombre(),
        		clienteGuardado.getIdentificacion(),
        		clienteGuardado.getEstado()
        );
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, event);
        log.info("Evento publicado en RabbitMQ para clienteId: {}", clienteGuardado.getClienteId());
        } catch (AmqpException e) {
            // El servicio responde con éxito al cliente web/Mobile aunque RabbitMQ no esté activo
            log.error("No se pudo enviar el evento a RabbitMQ. El servicio continúa. Motivo: {}", e.getMessage());
        }
        // Mapear Entidad guardada a DTO de respuesta
        return mapearAResponseDTO(clienteGuardado);
          
    }

	@Override
    @Transactional
    public ClienteResponseDTO modificarCliente(Long id, ClienteUpdateRequestDTO clienteDTO) {
        // Buscar si el cliente existe
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con el ID: " + id));


        // Actualizar los campos modificables
        if (clienteDTO.getNombre() != null) clienteExistente.setNombre(clienteDTO.getNombre());
        if (clienteDTO.getGenero() != null) clienteExistente.setGenero(clienteDTO.getGenero());
        if (clienteDTO.getEdad() != null) clienteExistente.setEdad(clienteDTO.getEdad());
        if (clienteDTO.getDireccion() != null) clienteExistente.setDireccion(clienteDTO.getDireccion());
        if (clienteDTO.getTelefono() != null) clienteExistente.setTelefono(clienteDTO.getTelefono());
        if (clienteDTO.getContrasenia() != null) clienteExistente.setContrasenia(clienteDTO.getContrasenia());
        if (clienteDTO.getEstado() != null) clienteExistente.setEstado(clienteDTO.getEstado());
   

        // Guardar cambios (JPA actualizará automáticamente en las tablas 'personas' y 'clientes')
        Cliente clienteActualizado = clienteRepository.save(clienteExistente);
        return mapearAResponseDTO(clienteActualizado);
    }

    @Override
    @Transactional
    public void eliminarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar. Cliente no encontrado con el ID: " + id);
        }
        // Al eliminar el cliente, JPA eliminará los registros correspondientes en 'clientes' y 'personas'
        clienteRepository.deleteById(id);
    }
	
	private ClienteResponseDTO mapearAResponseDTO(Cliente cliente) {
        return ClienteResponseDTO.builder()
        		// Atributos de Persona (padre)
                .id(cliente.getId())
                .nombre(cliente.getNombre())
                .genero(cliente.getGenero())
                .edad(cliente.getEdad())
                .identificacion(cliente.getIdentificacion())
                .direccion(cliente.getIdentificacion())
                .telefono(cliente.getTelefono())
                // Atributos de Cliente (hijo)
                .clienteId(cliente.getClienteId())
                .contrasenia(cliente.getContrasenia())
                .estado(cliente.getEstado())
                .build();
    }
	
	private Cliente mapearAEntity(ClienteRequestDTO clienteDTO) {
        return   Cliente.builder()
                // Atributos de Persona (padre)
                .nombre(clienteDTO.getNombre())
                .genero(clienteDTO.getGenero())
                .edad(clienteDTO.getEdad())
                .identificacion(clienteDTO.getIdentificacion())
                .direccion(clienteDTO.getDireccion())
                .telefono(clienteDTO.getTelefono())
                // Atributos de Cliente (hijo)
                .clienteId(clienteDTO.getClienteId())
                .contrasenia(clienteDTO.getContrasenia())
                .estado(clienteDTO.getEstado()!= null ? clienteDTO.getEstado() : true)
                .build();
    }
	

}
