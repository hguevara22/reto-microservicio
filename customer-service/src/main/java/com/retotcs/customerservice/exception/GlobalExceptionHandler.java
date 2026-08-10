package com.retotcs.customerservice.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.retotcs.customerservice.util.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Captura excepciones de negocio personalizadas (Recurso duplicado)
    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<ErrorResponse> manejarRecursoDuplicado(RecursoDuplicadoException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .mensaje(ex.getMessage())
                .codigoEstado(HttpStatus.CONFLICT.value()) // 409 Conflict
                .timestamp(LocalDateTime.now())
                .build();
     
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
    
 // Captura IllegalArgumentException (como las lanzadas en tu Service)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> manejarIllegalArgument(IllegalArgumentException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .mensaje(ex.getMessage())
                .codigoEstado(HttpStatus.BAD_REQUEST.value()) // 400 Bad Request
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> manejarResourceNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .mensaje(ex.getMessage())
                .codigoEstado(HttpStatus.NOT_FOUND.value()) // 404 Not Found
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Captura cualquier otro error no controlado (Fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarExcepcionesGenerales(Exception ex) {
        ErrorResponse error = ErrorResponse.builder()
                .mensaje("Ocurrió un error interno en el servidor: " + ex.getMessage())
                .codigoEstado(HttpStatus.INTERNAL_SERVER_ERROR.value()) // 500 Internal Server Error
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("codigoEstado", HttpStatus.BAD_REQUEST.value()); // 400
        
        // Mensaje amigable al cliente
        String mensaje = "El cuerpo de la petición no es válido o contiene valores incorrectos.";
        
        body.put("mensaje", mensaje);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
