# Taller de Pruebas Unitarias con Spring Boot y Mockito

## Descripción General

Este repositorio contiene el material práctico y teórico utilizado para la capacitación de **Pruebas Unitarias en Spring Boot**, utilizando **JUnit 5**, **Mockito** y la implementación de **Arquitectura Limpia (Clean Architecture)** basada en el scaffold oficial de Bancolombia.

El objetivo principal del ejercicio es comprender cómo diseñar y construir pruebas unitarias enfocadas en la capa de negocio, aislando completamente las dependencias externas mediante el uso de mocks.

Durante el desarrollo del taller se construye un microservicio funcional de evaluación de créditos, persistiendo información en una base de datos local y exponiendo endpoints REST para su consulta.

---

# Objetivos de Aprendizaje

Al finalizar este ejercicio el estudiante estará en capacidad de:

- Comprender qué es una prueba unitaria.
- Identificar qué debe y qué no debe probarse mediante Unit Testing.
- Crear pruebas utilizando JUnit 5.
- Simular dependencias mediante Mockito.
- Utilizar mocks y stubs.
- Verificar interacciones con dependencias externas.
- Validar escenarios exitosos y escenarios de error.
- Interpretar reportes de cobertura de código mediante JaCoCo.
- Aplicar pruebas unitarias dentro de una Arquitectura Hexagonal/Clean Architecture.

---

# Arquitectura Implementada

El proyecto utiliza el scaffold de **Clean Architecture de Bancolombia**, separando claramente las responsabilidades de cada capa.

## Domain

Contiene los modelos de dominio, contratos y excepciones.

Ejemplos:

- Loan
- LoanRequest
- LoanRepository
- BusinessException

---

## Use Cases

Contiene toda la lógica de negocio de la aplicación.

Ejemplos:

- EvaluateLoanUseCase
- GetLoansUseCase
- GetLoanByIdUseCase

Esta es la capa donde se concentra el mayor esfuerzo de pruebas unitarias.

---

## Entry Points

Expone la funcionalidad mediante una API REST.

Ejemplo:

```text
POST /api/loans
GET  /api/loans
GET  /api/loans/{id}
```

---

## Driven Adapters

Implementan las dependencias externas.

Para este ejercicio se utiliza:

- Spring Data JPA
- Base de datos H2/PostgreSQL

La capa de negocio nunca conoce detalles de persistencia.

---

# Caso de Negocio

## Sistema de Evaluación de Créditos

Una entidad financiera requiere automatizar la evaluación inicial de las solicitudes de crédito realizadas por sus clientes.

El sistema deberá analizar la información financiera suministrada por el cliente y determinar si una solicitud debe ser aprobada o rechazada.

Independientemente del resultado, la solicitud deberá almacenarse para mantener un historial de evaluaciones realizadas.

---

# Datos de Entrada

Cada solicitud de crédito contiene la siguiente información:

```json
{
  "customerId": "10001",
  "age": 35,
  "creditScore": 850,
  "monthlyIncome": 8000000,
  "monthlyDebt": 2000000,
  "requestedAmount": 30000000
}
```

---

# Datos de Salida

El sistema responde indicando el resultado de la evaluación:

```json
{
  "id": 1,
  "approved": true,
  "status": "APPROVED",
  "interestRate": 8,
  "rejectionReason": null
}
```

o en caso de rechazo:

```json
{
  "id": 2,
  "approved": false,
  "status": "REJECTED",
  "interestRate": null,
  "rejectionReason": "Customer must be at least 18 years old"
}
```

---

# Reglas de Negocio

El caso de uso principal es **EvaluateLoanUseCase**.

Durante la evaluación de una solicitud se aplican las siguientes reglas:

## 1. Validación de Edad

El cliente debe tener mínimo 18 años.

Si el cliente es menor de edad la solicitud será rechazada.

---

## 2. Validación de Score Crediticio

El score crediticio mínimo permitido es:

```text
600 puntos
```

Cualquier valor inferior genera rechazo de la solicitud.

---

## 3. Capacidad de Endeudamiento

La relación entre deudas e ingresos no puede superar el 40%.

La fórmula utilizada es:

```text
Deuda mensual / Ingreso mensual
```

Ejemplo:

```text
2.000.000 / 5.000.000 = 40%
```

---

## 4. Monto Máximo Permitido

El monto solicitado no puede exceder diez veces el ingreso mensual.

Ejemplo:

```text
Ingreso mensual:
5.000.000

Monto máximo:
50.000.000
```

---

## 5. Asignación de Tasa de Interés

Cuando una solicitud es aprobada se asigna una tasa de interés según el score crediticio.

```text
Score >= 800 -> 8%

Score >= 700 -> 10%

Score >= 600 -> 12%
```

---

# Comportamiento Implementado

A diferencia de otros sistemas donde una validación fallida interrumpe el flujo mediante excepciones, este ejercicio registra tanto créditos aprobados como rechazados.

Cuando una regla falla:

- El crédito es marcado como REJECTED.
- Se almacena el motivo del rechazo.
- El registro se persiste en base de datos.

Esto permite mantener trazabilidad histórica de todas las evaluaciones realizadas.

---

# Endpoints Disponibles

## Crear Solicitud de Crédito

```http
POST /api/loans
```

---

## Consultar Todos los Créditos

```http
GET /api/loans
```

---

## Consultar Crédito por Id

```http
GET /api/loans/{id}
```

---

# Escenarios de Prueba Implementados

Las pruebas unitarias cubren los siguientes casos de negocio.

## Escenarios Exitosos

### Crédito Aprobado con Tasa del 8%

```text
Score = 850
```

Resultado esperado:

```text
APPROVED
Interest Rate = 8%
```

---

### Crédito Aprobado con Tasa del 10%

```text
Score = 750
```

Resultado esperado:

```text
APPROVED
Interest Rate = 10%
```

---

### Crédito Aprobado con Tasa del 12%

```text
Score = 650
```

Resultado esperado:

```text
APPROVED
Interest Rate = 12%
```

---

## Escenarios de Rechazo

### Cliente Menor de Edad

```text
Age < 18
```

Resultado esperado:

```text
REJECTED
Customer must be at least 18 years old
```

---

### Score Crediticio Insuficiente

```text
Credit Score < 600
```

Resultado esperado:

```text
REJECTED
Credit score must be at least 600
```

---

### Endeudamiento Superior al 40%

Resultado esperado:

```text
REJECTED
Debt ratio exceeds 40%
```

---

### Monto Solicitado Superior al Permitido

Resultado esperado:

```text
REJECTED
Requested amount exceeds allowed limit
```

---

# Pruebas de API

Además de las pruebas unitarias, el proyecto incluye escenarios de validación mediante Postman para demostrar:

- Consumo de endpoints.
- Validación automática de respuestas.
- Ejecución de colecciones.
- Verificación de códigos HTTP.
- Validación de estructuras JSON.

---

# Cobertura de Código

La cobertura del proyecto puede ser analizada utilizando JaCoCo.

El objetivo es validar que todas las ramas del caso de uso principal sean ejecutadas mediante pruebas automatizadas.

Especial interés se tiene en:

- Condiciones de aprobación.
- Condiciones de rechazo.
- Asignación de tasas.
- Persistencia de resultados.

---

# Tecnologías Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Lombok
- H2 Database / PostgreSQL
- JUnit 5
- Mockito
- JaCoCo
- Gradle
- Postman

---

# Alcance del Taller

El enfoque principal del ejercicio es la construcción de pruebas unitarias sobre reglas de negocio.

Incluye:

✅ Casos de uso  
✅ Persistencia de datos  
✅ API REST  
✅ Mockito  
✅ JUnit 5  
✅ Cobertura de código  
✅ Arquitectura Limpia

No incluye:

❌ Seguridad (JWT, OAuth)  
❌ Docker  
❌ Kubernetes  
❌ Integraciones externas reales  
❌ Mensajería  
❌ Pruebas E2E

---

# Conclusión

Este ejercicio permite comprender de forma práctica cómo aplicar pruebas unitarias dentro de una arquitectura empresarial basada en Clean Architecture. El estudiante aprende a aislar dependencias externas mediante mocks, validar reglas de negocio complejas y medir la calidad de sus pruebas mediante métricas de cobertura, reproduciendo escenarios comunes encontrados en proyectos reales desarrollados con Spring Boot.