package com.retotcs.accountservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.retotcs.accountservice.persistence.enums.TipoMovimiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoResponseDTO {

	private Long id;
    private LocalDateTime fecha;
    private TipoMovimiento tipoMovimiento;
    private BigDecimal valor;
    private BigDecimal saldo;
    private String numeroCuenta;
}
