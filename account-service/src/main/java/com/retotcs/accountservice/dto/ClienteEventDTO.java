package com.retotcs.accountservice.dto;

import lombok.Data;

@Data
public class ClienteEventDTO {
    private String clienteId;
    private String nombre;
    private String identificacion;
    private Boolean estado;
}