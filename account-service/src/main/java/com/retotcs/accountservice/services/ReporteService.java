package com.retotcs.accountservice.services;

import java.time.LocalDate;
import java.util.List;

import com.retotcs.accountservice.dto.EstadoCuentaDTO;

public interface ReporteService {

	List<EstadoCuentaDTO> generarReporteEstadoCuenta(String clienteId, LocalDate fechaInicio, LocalDate fechaFin);
}
