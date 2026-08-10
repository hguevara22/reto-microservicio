package com.retotcs.accountservice.exception;

public class SaldoNoDisponibleException extends RuntimeException {

	   public SaldoNoDisponibleException(String mensaje) {
           super(mensaje);
       }
}
