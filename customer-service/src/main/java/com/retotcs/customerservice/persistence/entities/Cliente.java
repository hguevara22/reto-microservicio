package com.retotcs.customerservice.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "cliente")
@Data // Genera getters, setters, equals, hashCode y toString
@NoArgsConstructor // Constructor vacío
@AllArgsConstructor // Constructor con todos los campos
@SuperBuilder // Permite usar el patrón Builder
@EqualsAndHashCode(callSuper = true) // Incluye atributos heredados en equals/hashCode
public class Cliente extends Persona {

	@Column(unique = true, nullable = false)
	private String clienteId; // Clave única 
	 
	@Column(name = "contrasenia")
	private String contrasenia;
	
	@Column(name = "estado")
	private Boolean estado = true;
}
