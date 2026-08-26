package com.retotcs.accountservice.listeners;

import com.retotcs.accountservice.dto.ClienteEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClienteEventListener {

    @RabbitListener(queues = "customer.created.queue")
    public void handleClienteCreadoEvent(ClienteEventDTO evento) {
        log.info("Evento recibido en accountservice para clienteId: {}", evento.getClienteId());
        
        // Lógica asíncrona a ejecutar (ej. inicializar registro de auditoría, validar cliente, etc.)
    }
}