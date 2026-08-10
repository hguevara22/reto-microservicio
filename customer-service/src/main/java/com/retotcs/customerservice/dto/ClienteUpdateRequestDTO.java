package com.retotcs.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteUpdateRequestDTO {

	// Datos de Persona
    private String nombre;
    private String genero;
	private Integer edad;
	private String direccion;
	private String telefono;

    // Datos propios de Cliente
	private String contrasenia;
	private Boolean estado;
}
