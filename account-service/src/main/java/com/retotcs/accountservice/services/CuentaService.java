package com.retotcs.accountservice.services;

import java.util.List;

import com.retotcs.accountservice.dto.CuentaRequestDTO;
import com.retotcs.accountservice.dto.CuentaResponseDTO;
import com.retotcs.accountservice.dto.CuentaUpdateRequestDTO;

public interface CuentaService {

	List<CuentaResponseDTO> obtenerCuentas();
	CuentaResponseDTO obtenerCuentaPorId(Long id);
	CuentaResponseDTO obtenerCuentaPorNumero(String numeroCuenta);
    List<CuentaResponseDTO> obtenerCuentaPorClienteId(String clienteId);
    CuentaResponseDTO crearCuenta(CuentaRequestDTO cuentaDTO);
    CuentaResponseDTO modificarCuenta(Long id, CuentaUpdateRequestDTO cuentaDTO);
    void eliminarCuenta(Long id);
}
