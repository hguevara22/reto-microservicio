package com.retotcs.accountservice.persistence.entities;

import java.math.BigDecimal;

import com.retotcs.accountservice.persistence.enums.TipoCuenta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cuenta")
@Data // Genera getters, setters, equals, hashCode y toString
@NoArgsConstructor // Constructor vacío
@AllArgsConstructor // Constructor con todos los campos
@Builder // Permite usar el patrón Builder
public class Cuenta {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_cuenta", unique = true, nullable = false)
    private String numeroCuenta;

    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta; // AHORRO, CORRIENTE

    @Column(name = "saldo_inicial", nullable = false)
    private BigDecimal saldoInicial;

    @Column(name = "saldo_disponible", nullable = false)
    private BigDecimal saldoDisponible;

    private Boolean estado;

    @Column(name = "cliente_id", nullable = false)
    private String clienteId;
}
