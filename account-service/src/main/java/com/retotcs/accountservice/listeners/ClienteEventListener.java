package com.retotcs.accountservice.listeners;

import com.retotcs.accountservice.dto.ClienteEventDTO;
import com.retotcs.accountservice.persistence.entities.Cuenta;
import com.retotcs.accountservice.persistence.enums.TipoCuenta;
import com.retotcs.accountservice.persistence.repositories.CuentaRepository;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClienteEventListener {
	
	private final CuentaRepository cuentaRepository;
	
	public ClienteEventListener(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @RabbitListener(queues = "customer.created.queue")
    public void handleClienteCreadoEvent(ClienteEventDTO evento) {
    	 Random random = new Random();
    	// Lógica asíncrona a ejecutar
        log.info("Evento recibido en accountservice para clienteId: {}", evento.getClienteId());
        
        // Crear cuenta asociada al cliente
        Cuenta cuenta = new Cuenta();
        cuenta.setClienteId( evento.getClienteId());
        cuenta.setNumeroCuenta((random.nextInt(900000) + 100000)+"");
        cuenta.setSaldoInicial(BigDecimal.ZERO);
        cuenta.setSaldoDisponible(BigDecimal.ZERO);
        cuenta.setEstado(evento.getEstado());
        cuenta.setTipoCuenta(TipoCuenta.AHORRO);

        // Guardar en BD
        cuentaRepository.save(cuenta);
    }
}