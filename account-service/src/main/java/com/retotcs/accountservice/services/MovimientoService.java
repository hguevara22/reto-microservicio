package com.retotcs.accountservice.services;

import java.util.List;

import com.retotcs.accountservice.dto.MovimientoRequestDTO;
import com.retotcs.accountservice.dto.MovimientoResponseDTO;

public interface MovimientoService {

	List<MovimientoResponseDTO> obtenerMovimientos();
	MovimientoResponseDTO obtenerMovimientoPorId(Long id);
	MovimientoResponseDTO registrarMovimiento(MovimientoRequestDTO dto);
	void eliminarMovimiento(Long id);
}
