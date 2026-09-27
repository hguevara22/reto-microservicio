package com.retotcs.accountservice.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoRequestDTO {

	@NotBlank(message = "El número de cuenta es obligatorio")
    private String numeroCuenta;

    @NotNull(message = "El valor del movimiento es obligatorio")
    private BigDecimal valor; // Positivo (Depósito) o Negativo (Retiro)
    
 // Validación para descartar el cero
    @JsonIgnore // Para evitar que Jackson serialize este método en la respuesta
    @AssertTrue(message = "El valor del movimiento no puede ser cero")
    public boolean isValorNoZero() {
        if (valor == null) {
            return true; 
        }
        return valor.compareTo(BigDecimal.ZERO) != 0;
    }
}
