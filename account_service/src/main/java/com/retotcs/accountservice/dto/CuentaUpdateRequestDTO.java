package com.retotcs.accountservice.dto;

import java.math.BigDecimal;

import com.retotcs.accountservice.persistence.enums.TipoCuenta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaUpdateRequestDTO {

    private TipoCuenta tipoCuenta;
    private Boolean estado;
    private String clienteId;
}
