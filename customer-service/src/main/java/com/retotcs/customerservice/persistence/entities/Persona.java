package com.retotcs.customerservice.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "persona")
@Data // Genera getters, setters, equals, hashCode y toString
@NoArgsConstructor // Constructor vacío
@SuperBuilder  // Permite que las clases hijas hereden el Builder
@Inheritance(strategy = InheritanceType.JOINED) // Permite herencia entre entidades
public class Persona {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Clave primaria (PK)
	
	@Column(name = "nombre",length = 100)
	private String nombre;
	
	@Column(name = "genero",length = 20)
	private String genero;
	
	@Column(name = "edad")
	private Integer edad;
	
	@Column(name = "identificacion",unique = true, nullable = false,length = 20)
	private String identificacion;
	
	@Column(name = "direccion",length = 500)
	private String direccion;
	
	@Column(name = "telefono",length = 20)
	private String telefono;

}
