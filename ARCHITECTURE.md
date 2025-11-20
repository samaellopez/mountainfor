# Sistema MTTO - Arquitectura Hexagonal

## 📐 Arquitectura

Este proyecto implementa una **arquitectura hexagonal (puertos y adaptadores)** para un sistema de mantenimiento de equipos (MTTO).

### Stack Técnico

- **Java 17** (adaptable a Java 21 cuando esté disponible)
- **Quarkus 3.15.1**
- **PostgreSQL** (producción)
- **H2** (testing)
- **Flyway** para migraciones de base de datos
- **JUnit 5, Mockito, AssertJ** para testing
- **REST/JSON** para APIs

### Estructura de Paquetes

```
src/main/java/org/comunity/mountainfor/
├── domain/                          # Capa de Dominio (Core Business Logic)
│   ├── model/                       # Entidades de dominio (POJOs sin JPA)
│   │   ├── equipment/              # Equipos/Activos
│   │   │   ├── Equipment.java
│   │   │   ├── EquipmentType.java
│   │   │   └── EquipmentStatus.java
│   │   └── workorder/              # Órdenes de Trabajo
│   │       ├── WorkOrder.java
│   │       ├── WorkOrderType.java
│   │       ├── WorkOrderStatus.java
│   │       └── WorkOrderPriority.java
│   ├── port/                        # Puertos (Interfaces)
│   │   └── out/                     # Puertos de salida (repositorios)
│   │       ├── EquipmentRepositoryPort.java
│   │       └── WorkOrderRepositoryPort.java
│   └── service/                     # Servicios de dominio
│
├── application/                     # Capa de Aplicación (Casos de Uso)
│   └── usecase/
│       ├── equipment/
│       │   └── CreateEquipmentUseCase.java
│       └── workorder/
│           └── CreateCorrectiveWorkOrderUseCase.java
│
├── infrastructure/                  # Capa de Infraestructura (Adaptadores Secundarios)
│   └── persistence/
│       ├── jpa/
│       │   ├── entity/             # Entidades JPA (con anotaciones)
│       │   │   ├── EquipmentJpaEntity.java
│       │   │   └── WorkOrderJpaEntity.java
│       │   └── repository/         # Repositorios JPA
│       │       ├── EquipmentJpaRepository.java
│       │       ├── EquipmentRepositoryAdapter.java
│       │       ├── WorkOrderJpaRepository.java
│       │       └── WorkOrderRepositoryAdapter.java
│       └── mapper/                  # Mappers Domain <-> JPA
│           ├── EquipmentMapper.java
│           └── WorkOrderMapper.java
│
└── web/                             # Capa Web (Adaptadores Primarios)
    └── rest/
        ├── equipment/
        │   ├── EquipmentResource.java
        │   ├── CreateEquipmentRequest.java
        │   └── EquipmentResponse.java
        └── workorder/
            ├── WorkOrderResource.java
            ├── CreateCorrectiveWorkOrderRequest.java
            └── WorkOrderResponse.java
```

## 🎯 Principios de Arquitectura Hexagonal

### 1. Separación de Capas

- **Dominio**: Lógica de negocio pura, sin dependencias de frameworks
- **Aplicación**: Orquesta casos de uso usando el dominio
- **Infraestructura**: Implementa los puertos con tecnologías específicas (JPA, etc.)
- **Web**: Expone la API REST

### 2. Puertos e Interfaces

- **Puertos de Salida** (`EquipmentRepositoryPort`): Interfaces definidas en el dominio
- **Adaptadores** (`EquipmentRepositoryAdapter`): Implementaciones en infraestructura

### 3. Reglas de Dependencia

```
Web → Application → Domain ← Infrastructure
```

- Las capas externas dependen de las internas
- El dominio no depende de nada
- La infraestructura implementa interfaces del dominio

## 🏗️ Entidades Principales

### Equipment (Equipo)

Representa equipos/activos del sistema:
- Código único
- Tipo (MECHANICAL, ELECTRICAL, etc.)
- Estado (ACTIVE, INACTIVE, IN_MAINTENANCE)
- Ubicación y centro de costo
- Jerarquía (equipo padre)

**Reglas de Negocio:**
- No se puede desactivar un equipo en mantenimiento
- No se puede realizar mantenimiento a equipos inactivos

### WorkOrder (Orden de Trabajo)

Representa órdenes de trabajo correctivas o preventivas:
- Tipo (CORRECTIVE, PREVENTIVE)
- Prioridad (LOW, MEDIUM, HIGH, CRITICAL)
- Estado (OPEN, IN_PROGRESS, WAITING_FOR_PARTS, CLOSED)
- Fechas de creación, inicio y cierre

**Reglas de Negocio:**
- No se puede crear OT para equipos inactivos
- Solo se pueden iniciar OTs abiertas
- Solo se pueden completar OTs en progreso

## 🔄 Flujo de una Petición

```
1. Cliente → REST Resource (EquipmentResource)
2. REST → Use Case (CreateEquipmentUseCase)
3. Use Case → Domain Entity (Equipment.create())
4. Use Case → Repository Port (EquipmentRepositoryPort.save())
5. Repository Adapter → JPA Repository
6. JPA Repository → Mapper (domain → JPA entity)
7. JPA Repository → Database
8. Response → Mapper → Use Case → REST → Cliente
```

## 🗄️ Base de Datos

### Migraciones Flyway

Las migraciones se encuentran en `src/main/resources/db/migration/`:
- `V1__create_equipment_table.sql`: Tabla de equipos
- `V2__create_work_order_table.sql`: Tabla de órdenes de trabajo

### Tablas

#### equipment
- id (BIGSERIAL)
- code (VARCHAR 50, UNIQUE)
- description (VARCHAR 500)
- type (VARCHAR 50)
- location (VARCHAR 200)
- cost_center (VARCHAR 50)
- status (VARCHAR 50)
- installation_date (DATE)
- estimated_life_years (INTEGER)
- parent_equipment_id (BIGINT)

#### work_order
- id (BIGSERIAL)
- equipment_id (BIGINT, FK)
- type (VARCHAR 50)
- description (VARCHAR 500)
- failure_description (VARCHAR 1000)
- priority (VARCHAR 50)
- status (VARCHAR 50)
- created_at (TIMESTAMP)
- started_at (TIMESTAMP)
- completed_at (TIMESTAMP)
- assigned_to (VARCHAR 100)
- maintenance_plan_id (BIGINT)

## 🚀 API REST

### Equipment Endpoints

#### Crear Equipo
```http
POST /api/equipment
Content-Type: application/json

{
  "code": "EQ-001",
  "description": "Motor Principal",
  "type": "MECHANICAL",
  "location": "Planta A",
  "costCenter": "CC-1000",
  "installationDate": "2024-01-15",
  "estimatedLifeYears": 10,
  "parentEquipmentId": null
}
```

#### Obtener Equipo por ID
```http
GET /api/equipment/{id}
```

#### Obtener Equipo por Código
```http
GET /api/equipment/code/{code}
```

#### Listar Todos los Equipos
```http
GET /api/equipment
```

### WorkOrder Endpoints

#### Crear Orden de Trabajo Correctiva
```http
POST /api/workorders/corrective
Content-Type: application/json

{
  "equipmentId": 1,
  "failureDescription": "Falla en el motor",
  "priority": "HIGH",
  "assignedTo": "Juan Pérez"
}
```

#### Obtener Orden de Trabajo por ID
```http
GET /api/workorders/{id}
```

#### Listar Todas las Órdenes de Trabajo
```http
GET /api/workorders
```

#### Listar Órdenes por Equipo
```http
GET /api/workorders/equipment/{equipmentId}
```

## 🧪 Testing

### Tests Unitarios

Ubicación: `src/test/java/.../application/usecase/`

Ejemplo: `CreateCorrectiveWorkOrderUseCaseTest`
- Usa Mockito para mockear repositorios
- Valida reglas de negocio
- No requiere base de datos

```bash
./mvnw test -Dtest=*Test
```

### Tests de Integración

Ubicación: `src/test/java/.../web/rest/`

Ejemplo: `EquipmentResourceIT`
- Usa `@QuarkusTest`
- Prueba el stack completo (REST → DB)
- Usa H2 en memoria

```bash
./mvnw test -Dtest=*IT
```

### Ejecutar Todos los Tests

```bash
./mvnw test
```

## 📝 Configuración

### Desarrollo (application.properties)

```properties
# PostgreSQL
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=mtto_user
quarkus.datasource.password=mtto_password
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/mtto_db

# Flyway
quarkus.flyway.migrate-at-start=true

# OpenAPI/Swagger
quarkus.swagger-ui.always-include=true
quarkus.swagger-ui.path=/swagger-ui
```

### Testing (src/test/resources/application.properties)

```properties
# H2 en memoria
quarkus.datasource.db-kind=h2
quarkus.datasource.jdbc.url=jdbc:h2:mem:testdb
```

## 🔧 Comandos Útiles

### Compilar
```bash
./mvnw clean compile
```

### Ejecutar Tests
```bash
./mvnw test
```

### Ejecutar Aplicación en Modo Dev
```bash
./mvnw quarkus:dev
```

### Construir JAR
```bash
./mvnw package
```

### Ver OpenAPI/Swagger UI
```
http://localhost:8080/swagger-ui
```

## 📦 Extensiones Futuras

El sistema está preparado para agregar:

1. **Mantenimiento Preventivo**
   - `MaintenancePlan` domain entity
   - `CreatePreventiveMaintenancePlanUseCase`
   - Generación automática de órdenes preventivas

2. **Inventario de Repuestos**
   - `SparePart` domain entity
   - `InventoryMovement` para entradas/salidas
   - `ConsumeSparePartUseCase`

3. **Compras**
   - `PurchaseRequest` domain entity
   - Estados de solicitud de compra

4. **Costos**
   - `WorkOrderCost` domain entity
   - `CalculateWorkOrderCostsUseCase`
   - Reportes de costos por equipo/periodo

## 🎨 Decisiones de Diseño

1. **Entidades de Dominio sin JPA**: Las entidades de dominio son POJOs puros sin anotaciones JPA, manteniendo el dominio limpio.

2. **Factory Methods**: Las entidades usan factory methods (`Equipment.create()`) para garantizar invariantes de negocio.

3. **Mappers Explícitos**: Los mappers convierten entre entidades de dominio y JPA, separando concerns.

4. **Records para DTOs**: Uso de Java records para Request/Response DTOs (inmutables y concisos).

5. **Validation en Dominio**: Las validaciones de negocio están en las entidades/casos de uso, no en la capa web.

6. **Transacciones en Infraestructura**: Las transacciones (`@Transactional`) están en los repositorios JPA, no en el dominio.

## 📚 Referencias

- Clean Architecture (Robert C. Martin)
- Hexagonal Architecture (Alistair Cockburn)
- Domain-Driven Design (Eric Evans)
- Quarkus Documentation: https://quarkus.io

## 🆕 Nuevas Funcionalidades Implementadas

### Inventario de Repuestos (Spare Parts)

#### Dominio
- **SparePart**: Entidad de dominio con stock actual, mínimo y máximo
- **InventoryMovement**: Movimientos de entrada/salida de inventario
- **MovementType**: ENTRY (entrada) / EXIT (salida)

#### Reglas de Negocio
- Validación de código único de repuesto
- Control de stock: no permite consumir más de lo disponible
- Alertas automáticas cuando el stock está bajo el mínimo
- Los movimientos de salida se vinculan a órdenes de trabajo

#### API REST
```
POST /api/spareparts - Crear repuesto
GET /api/spareparts/{id} - Obtener por ID
GET /api/spareparts - Listar todos
GET /api/spareparts/below-minimum - Repuestos con stock bajo mínimo
POST /api/spareparts/consume - Consumir repuestos
```

#### Ejemplo de Uso
```json
POST /api/spareparts
{
  "code": "REP-001",
  "description": "Filtro de aceite",
  "unitOfMeasure": "unidad",
  "minimumStock": 10,
  "maximumStock": 50,
  "location": "Almacén A",
  "unitPrice": 45.50
}

POST /api/spareparts/consume
{
  "sparePartId": 1,
  "quantity": 2,
  "reason": "Mantenimiento preventivo",
  "workOrderId": 5,
  "performedBy": "Juan Pérez"
}
```

### Costos de Mantenimiento (Work Order Costs)

#### Dominio
- **WorkOrderCost**: Calcula costos totales automáticamente
- Componentes: costoManoObra + costoRepuestos + otrosCostos = costoTotal

#### Reglas de Negocio
- Cálculo automático de costos de repuestos basado en movimientos de inventario
- Suma de costos: mano de obra + repuestos + otros
- Un costo por orden de trabajo (relación 1:1)

#### API REST
```
POST /api/costs/calculate - Calcular costos de OT
GET /api/costs/workorder/{id} - Obtener costos por OT
```

#### Ejemplo de Uso
```json
POST /api/costs/calculate
{
  "workOrderId": 5,
  "laborCost": 500.00,
  "otherCosts": 50.00
}

Response:
{
  "id": 1,
  "workOrderId": 5,
  "laborCost": 500.00,
  "sparePartsCost": 91.00,  // Calculado automáticamente
  "otherCosts": 50.00,
  "totalCost": 641.00  // Suma automática
}
```

### Flujo Integrado

1. **Crear Equipo**
   ```
   POST /api/equipment
   ```

2. **Crear Orden de Trabajo Correctiva**
   ```
   POST /api/workorders/corrective
   ```

3. **Consumir Repuestos en la OT**
   ```
   POST /api/spareparts/consume
   → Reduce stock automáticamente
   → Registra movimiento vinculado a OT
   ```

4. **Calcular Costos de la OT**
   ```
   POST /api/costs/calculate
   → Calcula costo de repuestos consumidos
   → Suma mano de obra y otros costos
   → Retorna costo total
   ```

5. **Consultar Stock Bajo Mínimo**
   ```
   GET /api/spareparts/below-minimum
   → Alerta de repuestos a reabastecer
   ```

### Tablas de Base de Datos

#### spare_part
- id, code (unique), description, unit_of_measure
- current_stock, minimum_stock, maximum_stock
- location, unit_price

#### inventory_movement
- id, spare_part_id (FK), type, quantity
- reason, work_order_id (FK), movement_date, performed_by

#### work_order_cost
- id, work_order_id (FK, unique)
- labor_cost, spare_parts_cost, other_costs, total_cost

