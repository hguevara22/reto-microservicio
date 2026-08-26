package com.retotcs.accountservice.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retotcs.accountservice.dto.CuentaRequestDTO;
import com.retotcs.accountservice.dto.CuentaResponseDTO;
import com.retotcs.accountservice.dto.CuentaUpdateRequestDTO;
import com.retotcs.accountservice.exception.RecursoDuplicadoException;
import com.retotcs.accountservice.exception.ResourceNotFoundException;
import com.retotcs.accountservice.persistence.entities.Cuenta;
import com.retotcs.accountservice.persistence.repositories.CuentaRepository;

import org.springframework.transaction.annotation.Transactional;


@Service
public class CuentaServiceImpl implements CuentaService{
	
	@Autowired
	private CuentaRepository cuentaRepository;

	@Override
	@Transactional(readOnly = true)
	public List<CuentaResponseDTO> obtenerCuentas() {
		// TODO Auto-generated method stub
		return cuentaRepository.findAll()
                .stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public CuentaResponseDTO obtenerCuentaPorId(Long id) {
		// TODO Auto-generated method stub
		Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrado con el ID: " + id));
        return mapearAResponseDTO(cuenta);
	}

	@Override
	@Transactional(readOnly = true)
	public CuentaResponseDTO obtenerCuentaPorNumero(String numeroCuenta) {
		// TODO Auto-generated method stub
		Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con número: " + numeroCuenta));
		return mapearAResponseDTO(cuenta);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CuentaResponseDTO> obtenerCuentaPorClienteId(String clienteId) {
		return cuentaRepository.findByClienteId(clienteId)
                .stream()
                .map(this::mapearAResponseDTO)
                .toList();
	}

	@Override
	@Transactional
	public CuentaResponseDTO crearCuenta(CuentaRequestDTO cuentaDTO) {
		// TODO Auto-generated method stub
		if (cuentaRepository.existsByNumeroCuenta(cuentaDTO.getNumeroCuenta())) {
            throw new RecursoDuplicadoException("Ya existe una cuenta registrada con el número: " + cuentaDTO.getNumeroCuenta());
        }
        Cuenta cuenta =  mapearAEntity(cuentaDTO);
        // Al crear la cuenta, el saldo disponible inicia igual al saldo inicial
        cuenta.setSaldoDisponible(cuentaDTO.getSaldoInicial());
        Cuenta guardada = cuentaRepository.save(cuenta);
        return mapearAResponseDTO(guardada);
	}

	@Override
	@Transactional
	public CuentaResponseDTO modificarCuenta(Long id, CuentaUpdateRequestDTO cuentaDTO) {
		// TODO Auto-generated method stub
		Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con id: " + id));

        cuenta.setTipoCuenta(cuentaDTO.getTipoCuenta());
        cuenta.setEstado(cuentaDTO.getEstado());
        
        // Si el clienteId se actualiza o mantiene
        if (cuentaDTO.getClienteId() != null) {
            cuenta.setClienteId(cuentaDTO.getClienteId());
        }

        Cuenta actualizada = cuentaRepository.save(cuenta);
        return mapearAResponseDTO(actualizada);
	}

	@Override
	@Transactional
	public void eliminarCuenta(Long id) {
		// TODO Auto-generated method stub
		if (!cuentaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cuenta no encontrada con id: " + id);
        }
        cuentaRepository.deleteById(id);
	}

	private CuentaResponseDTO mapearAResponseDTO(Cuenta cuenta) {
        return CuentaResponseDTO.builder()
                .id(cuenta.getId())
                .numeroCuenta(cuenta.getNumeroCuenta())
                .tipoCuenta(cuenta.getTipoCuenta())
                .saldoInicial(cuenta.getSaldoInicial())
                .saldoDisponible(cuenta.getSaldoDisponible())
                .estado(cuenta.getEstado())
                .clienteId(cuenta.getClienteId())
                .build();
    }
	
	private Cuenta mapearAEntity(CuentaRequestDTO cuentaDTO) {
        return   Cuenta.builder()
        		 .id(cuentaDTO.getId())
                 .numeroCuenta(cuentaDTO.getNumeroCuenta())
                 .tipoCuenta(cuentaDTO.getTipoCuenta())
                 .saldoInicial(cuentaDTO.getSaldoInicial())
                 .saldoDisponible(cuentaDTO.getSaldoDisponible())
                 .estado(cuentaDTO.getEstado())
                 .clienteId(cuentaDTO.getClienteId())
                .build();
    }
	
}
