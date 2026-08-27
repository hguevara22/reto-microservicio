package com.retotcs.customerservice.dto;

import jakarta.validation.constraints.Min;
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
public class ClienteRequestDTO {

	// Datos de Persona
	@NotBlank(message = "El nombre es obligatorio")
    private String nombre;
	
    private String genero;
    
    @NotNull(message = "La edad es obligatoria")
    @Min(value = 0, message = "La edad no puede ser negativa")
	private Integer edad;
    
	@NotBlank(message = "La identificación es obligatoria")
	private String identificacion;
	
	private String direccion;
	private String telefono;

    // Datos propios de Cliente
	@NotBlank(message = "El clienteId es obligatorio")
	private String clienteId; // Clave única (PK lógica adicional)
	
	@NotBlank(message = "La contraseña es obligatoria")
	private String contrasenia;
	
	private Boolean estado;
}
