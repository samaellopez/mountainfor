# Sistema MTTO - Mantenimiento de Equipos

Sistema de gestión de mantenimiento de equipos (MTTO) construido con **arquitectura hexagonal** usando Java 17 y Quarkus 3.x.

## 🚀 Quick Start

### Prerrequisitos

- Java 17+
- Maven 3.8+
- PostgreSQL 14+ (para producción) o H2 (para testing)

### Base de Datos

Crear base de datos PostgreSQL:

```sql
CREATE DATABASE mtto_db;
CREATE USER mtto_user WITH PASSWORD 'mtto_password';
GRANT ALL PRIVILEGES ON DATABASE mtto_db TO mtto_user;
```

### Ejecutar en modo desarrollo

```bash
./mvnw quarkus:dev
```

La aplicación estará disponible en:
- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui
- Health Check: http://localhost:8080/q/health

### Ejecutar Tests

```bash
# Todos los tests (unitarios + integración)
./mvnw test

# Solo tests unitarios
./mvnw test -Dtest=*Test

# Solo tests de integración
./mvnw test -Dtest=*IT
```

## 📐 Arquitectura

Este proyecto implementa **arquitectura hexagonal (puertos y adaptadores)**. Ver [ARCHITECTURE.md](ARCHITECTURE.md) para detalles completos.

### Estructura de Capas

- **Domain**: Entidades y reglas de negocio puras (sin dependencias de frameworks)
- **Application**: Casos de uso que orquestan el dominio
- **Infrastructure**: Adaptadores JPA, mappers, persistencia
- **Web**: REST API endpoints

### Tecnologías

- Java 17
- Quarkus 3.15.1
- PostgreSQL / H2
- Flyway (migraciones)
- JUnit 5, Mockito, AssertJ
- REST/JSON

## 📚 Documentación

- [ARCHITECTURE.md](ARCHITECTURE.md) - Documentación completa de arquitectura
- [Swagger UI](http://localhost:8080/swagger-ui) - Documentación interactiva de la API

## 🎯 Funcionalidades Implementadas

### ✅ Equipos (Equipment)
- Crear equipos con código único
- Consultar por ID o código
- Listar todos los equipos
- Tipos: MECHANICAL, ELECTRICAL, ELECTRONIC, HVAC, etc.
- Estados: ACTIVE, INACTIVE, IN_MAINTENANCE

### ✅ Órdenes de Trabajo (WorkOrder)
- Crear órdenes correctivas
- Consultar por ID
- Listar por equipo
- Prioridades: LOW, MEDIUM, HIGH, CRITICAL
- Estados: OPEN, IN_PROGRESS, WAITING_FOR_PARTS, CLOSED

## 📝 API Ejemplos

### Crear Equipo

```bash
curl -X POST http://localhost:8080/api/equipment \
  -H "Content-Type: application/json" \
  -d '{
    "code": "EQ-001",
    "description": "Motor Principal",
    "type": "MECHANICAL",
    "location": "Planta A",
    "costCenter": "CC-1000",
    "installationDate": "2024-01-15",
    "estimatedLifeYears": 10
  }'
```

### Crear Orden de Trabajo Correctiva

```bash
curl -X POST http://localhost:8080/api/workorders/corrective \
  -H "Content-Type: application/json" \
  -d '{
    "equipmentId": 1,
    "failureDescription": "Falla en el motor",
    "priority": "HIGH",
    "assignedTo": "Juan Pérez"
  }'
```

## 🔮 Próximas Funcionalidades

- [ ] Mantenimiento Preventivo (planes y programación)
- [ ] Inventario de Repuestos
- [ ] Solicitudes de Compra
- [ ] Cálculo de Costos por OT
- [ ] Reportes y Dashboards

## 🧪 Testing

El proyecto incluye:

- **4 tests unitarios** para casos de uso (con Mockito)
- **5 tests de integración** para endpoints REST (con QuarkusTest)

Todos los tests pasan exitosamente ✅

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `mountainfor-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/mountainfor-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/mountainfor-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.

# RESTEasy JAX-RS

Guide: https://quarkus.io/guides/rest-json


