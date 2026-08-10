# Reto Técnico - Microservicios con Spring Boot, Docker Compose, MySQL y RabbitMQ

## Requisitos previos
- Java 21
- Maven 3.x
- Docker y Docker Compose

## Instrucciones para ejecutar con Docker

1. Clonar el repositorio:
   ```bash
   git clone [https://github.com/TU_USUARIO/TU_REPOSITORIO.git](https://github.com/TU_USUARIO/TU_REPOSITORIO.git)
   cd reto-microservicio
   ```

2. Desplegar los contenedores:
   ```bash
   docker compose up --build -d
   ```

## Endpoints de prueba
- **Customer Service:** `http://localhost:8081/clientes`
- **Account Service:** `http://localhost:8080/cuentas`
- `http://localhost:8080/reportes?clienteId=CLI-001&fechaInicio=2026-08-01&fechaFin=2026-08-09`
- **RabbitMQ Console:** `http://localhost:15672` (guest / guest)
