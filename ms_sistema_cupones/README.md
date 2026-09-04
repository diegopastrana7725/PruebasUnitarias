# Taller de Pruebas Unitarias con Spring Boot - Sistema de Cupones

## Descripción General

Este proyecto fue desarrollado como material práctico para la capacitación de **Pruebas Unitarias con Spring Boot**, utilizando:

- Java 17
- Spring Boot
- JUnit 5
- Mockito
- JaCoCo
- H2 Database
- Gradle
- Clean Architecture (Scaffold Bancolombia)

El objetivo es construir un sistema de aplicación de cupones de descuento que permita demostrar la implementación de reglas de negocio, pruebas unitarias y pruebas de API.

---

# Objetivos de Aprendizaje

Al finalizar este ejercicio el estudiante podrá:

- Comprender qué es una prueba unitaria.
- Aplicar JUnit 5 y Mockito.
- Utilizar mocks para aislar dependencias.
- Identificar escenarios de negocio.
- Probar reglas de validación.
- Medir cobertura de código con JaCoCo.
- Implementar pruebas dentro de Clean Architecture.

---

# Arquitectura

El proyecto fue construido utilizando el scaffold de Arquitectura Limpia de Bancolombia.

## Domain

Contiene:

- Modelos de negocio.
- Puertos (Gateways).
- Excepciones.

Ejemplos:

```text
Coupon
CouponUsage
ApplyCouponRequest
ApplyCouponResponse
CouponRepository
CouponUsageRepository
BusinessException
```

---

## UseCases

Contiene la lógica de negocio.

Casos de uso implementados:

```text
ApplyCouponUseCase
GetCouponsUseCase
GetCouponsByIdUseCase
```

---

## Entry Points

Exponen la funcionalidad mediante una API REST.

```text
POST /api/coupons/apply
GET  /api/coupons
GET  /api/coupons/{id}
```

---

## Driven Adapters

Implementan:

- Spring Data JPA
- H2 Database

---

# Caso de Negocio

Una compañía desea implementar un sistema de cupones promocionales.

Los clientes podrán utilizar cupones de descuento durante una compra siempre que cumplan determinadas reglas de negocio.

El sistema deberá determinar si el cupón es válido y calcular el descuento correspondiente.

---

# Entidades Principales

## Coupon

Representa un cupón configurable.

Atributos principales:

```text
code
discountType
discountValue
maxDiscount
minimumPurchaseAmount
category
active
expirationDate
maxUsesPerCustomer
```

---

## CouponUsage

Representa cada utilización de un cupón por parte de un cliente.

Permite conocer:

- Cuántas veces fue utilizado.
- Quién lo utilizó.
- Cuándo fue utilizado.

---

# Reglas de Negocio

Todas las reglas son implementadas en:

```java
ApplyCouponUseCase
```

---

## Regla 1 - Existencia del Cupón

El cupón debe existir en la base de datos.

### Válido

```text
WELCOME10
```

### Inválido

```text
INVALID
```

Resultado:

```text
Coupon not found
```

---

## Regla 2 - Cupón Activo

El cupón debe estar habilitado para ser utilizado.

### Campo

```text
active = true
```

Si:

```text
active = false
```

Resultado:

```text
Coupon is inactive
```

---

## Regla 3 - Vigencia

El cupón no puede estar vencido.

### Ejemplo válido

```text
2030-12-31
```

### Ejemplo inválido

```text
2020-01-01
```

Resultado:

```text
Coupon has expired
```

---

## Regla 4 - Compra Mínima

La compra debe cumplir un valor mínimo.

### Cupón

```text
minimumPurchaseAmount = 100000
```

### Compra

```text
50000
```

Resultado:

```text
Minimum purchase amount not reached
```

---

## Regla 5 - Categoría Permitida

El cupón sólo puede utilizarse para la categoría configurada.

### Cupón

```text
TECHNOLOGY
```

### Compra

```text
FOOD
```

Resultado:

```text
Coupon is not valid for this category
```

---

## Regla 6 - Límite de Uso

Cada cupón define cuántas veces puede ser utilizado por un mismo cliente.

### Configuración

```text
maxUsesPerCustomer = 3
```

### Historial

```text
Cliente ya utilizó el cupón 3 veces
```

Resultado:

```text
Coupon usage limit exceeded
```

---

## Regla 7 - Descuento Fijo

Un cupón puede otorgar un valor fijo.

### Configuración

```text
discountType = FIXED

discountValue = 50000
```

### Compra

```text
200000
```

Resultado:

```text
Descuento: 50000

Total Final: 150000
```

---

## Regla 8 - Descuento Porcentual

El descuento se calcula a partir del porcentaje configurado.

### Configuración

```text
discountType = PERCENTAGE

discountValue = 10
```

### Compra

```text
200000
```

Resultado:

```text
Descuento: 20000

Total Final: 180000
```

---

## Regla 9 - Tope Máximo de Descuento

Un cupón porcentual puede limitar el descuento máximo otorgado.

### Configuración

```text
discountValue = 20%

maxDiscount = 30000
```

### Compra

```text
300000
```

Cálculo:

```text
20% de 300000 = 60000
```

Sin embargo:

```text
60000 > 30000
```

Resultado:

```text
Descuento aplicado = 30000
```

---

# Flujo General

```text
Cliente
   |
   ▼
Aplicar Cupón
   |
   ▼
Buscar Cupón
   |
   ▼
Validar:
- Existencia
- Estado
- Vigencia
- Compra mínima
- Categoría
- Límite de uso
   |
   ▼
Calcular descuento
   |
   ▼
Registrar uso
   |
   ▼
Retornar resultado
```

---

# Endpoints

## Aplicar Cupón

```http
POST /api/coupons/apply
```

Ejemplo:

```json
{
  "customerId": "1001",
  "couponCode": "WELCOME10",
  "purchaseAmount": 200000,
  "category": "TECHNOLOGY"
}
```

Respuesta:

```json
{
  "applied": true,
  "couponCode": "WELCOME10",
  "purchaseAmount": 200000,
  "discountAmount": 20000,
  "finalAmount": 180000,
  "message": "Coupon applied successfully"
}
```

---

## Consultar Cupones

```http
GET /api/coupons
```

---

## Consultar Cupón por Id

```http
GET /api/coupons/{id}
```

---

# Datos Iniciales

El proyecto carga automáticamente varios cupones para facilitar las pruebas.

## WELCOME10

```text
10% descuento
Máximo 50000
```

---

## INACTIVE10

```text
Cupón inactivo
```

---

## EXPIRED10

```text
Cupón vencido
```

---

## FIXED50

```text
Descuento fijo de 50000
```

---

## TOP20

```text
20% descuento
Tope máximo 30000
```

---

# Pruebas Unitarias

Se implementan pruebas para todos los escenarios principales.

## ApplyCouponUseCase

### Escenarios Exitosos

✅ Aplicar cupón porcentual

✅ Aplicar cupón fijo

✅ Aplicar cupón con tope máximo

---

### Escenarios de Error

✅ Cupón inexistente

✅ Cupón inactivo

✅ Cupón vencido

✅ Compra mínima insuficiente

✅ Categoría inválida

✅ Límite de uso alcanzado

---

## GetCouponsUseCase

✅ Obtener todos los cupones

---

## GetCouponsByIdUseCase

✅ Cupón encontrado

✅ Cupón inexistente

---

# Cobertura de Código

La cobertura puede generarse mediante:

```bash
./gradlew clean test jacocoTestReport
