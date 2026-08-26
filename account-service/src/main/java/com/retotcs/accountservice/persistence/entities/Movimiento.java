package com.retotcs.accountservice.persistence.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.retotcs.accountservice.persistence.enums.TipoCuenta;
import com.retotcs.accountservice.persistence.enums.TipoMovimiento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movimiento")
@Data // Genera getters, setters, equals, hashCode y toString
@NoArgsConstructor // Constructor vacío
@AllArgsConstructor // Constructor con todos los campos
@Builder // Permite usar el patrón Builder
public class Movimiento {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    private TipoMovimiento tipoMovimiento; // DEPOSITO, RETIRO

    @Column(nullable = false)
    private BigDecimal valor; // Positivo (Depósito) o Negativo (Retiro)

    @Column(nullable = false)
    private BigDecimal saldo; // Saldo posterior a la transacción

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Cuenta cuenta;
}
