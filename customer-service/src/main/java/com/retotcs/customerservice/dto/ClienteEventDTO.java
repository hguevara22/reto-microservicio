package com.retotcs.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEventDTO implements Serializable {
    private String clienteId;
    private String nombre;
    private String identificacion;
    private Boolean estado;
}