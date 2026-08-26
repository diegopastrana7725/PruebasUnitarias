# Taller de Pruebas Unitarias con Spring Boot

## Descripción

Este repositorio contiene el material de apoyo para el taller de **Pruebas Unitarias con Spring Boot**, enfocado en la validación de lógica de negocio dentro de una arquitectura basada en principios de **Arquitectura limpia (Bancolombia)** https://github.com/bancolombia/scaffold-clean-architecture.

El objetivo principal es comprender cómo construir pruebas unitarias efectivas para casos de uso (Use Cases), aislando las dependencias externas mediante mocks y garantizando la validación de las reglas de negocio de forma independiente.

A lo largo del taller se trabajará con dos microservicios de ejemplo que permitirán aplicar conceptos como:

- JUnit 5.
- Mockito.
- Mocking de dependencias.
- Verificación de interacciones.
- Cobertura de código.
- Escenarios exitosos y escenarios de error.
- Buenas prácticas para pruebas unitarias en aplicaciones Spring Boot.

---

## Contenido del repositorio

La estructura del repositorio está organizada en tres grandes componentes:

### 1. Presentación del Taller

Contiene el material utilizado durante la sesión para explicar los conceptos fundamentales de pruebas unitarias.

Entre los temas abordados se encuentran:

- ¿Qué son las pruebas unitarias?
- Beneficios de automatizar pruebas.
- Pirámide de testing.
- Diferencia entre pruebas unitarias y pruebas de integración.
- Introducción a Mockito.
- Uso de mocks y stubs.
- Buenas prácticas en pruebas unitarias.
- Cobertura de código.

---

### 2. Ejercicio Guiado: Sistema de Evaluación de Créditos

Microservicio utilizado durante la clase para explicar paso a paso la construcción de pruebas unitarias sobre un caso de uso con reglas de negocio.

El sistema permite evaluar solicitudes de crédito considerando criterios como:

- Edad mínima del solicitante.
- Score crediticio.
- Capacidad de endeudamiento.
- Monto máximo permitido.
- Restricciones financieras.
- Asignación de tasa de interés.

Durante el ejercicio se implementarán y ejecutarán pruebas unitarias para validar cada una de estas reglas.

---

### 3. Ejercicio de Práctica: Sistema de Cupones de Descuento

Microservicio propuesto como ejercicio independiente para reforzar los conceptos vistos durante la sesión.

El caso de uso consiste en aplicar cupones de descuento sobre una compra, validando aspectos como:

- Existencia del cupón.
- Estado y vigencia.
- Compra mínima requerida.
- Categorías permitidas.
- Límite de uso por cliente.
- Cálculo de descuentos porcentuales y fijos.
- Aplicación de topes máximos de descuento.
- Registro de auditoría.

Los estudiantes deberán implementar las pruebas unitarias necesarias para garantizar el correcto funcionamiento de todas las reglas de negocio.

---

## Alcance del Taller

El foco principal de este repositorio es la construcción de pruebas unitarias para la capa de negocio.

Por esta razón:

✅ Se implementarán los casos de uso y sus respectivas reglas de negocio.

✅ Se utilizarán puertos (Ports) para representar dependencias externas.

✅ Los adaptadores externos serán simulados mediante Mockito.

✅ Se validarán escenarios exitosos y escenarios de error.

✅ Se medirá la cobertura de las pruebas.

No forma parte del alcance:

❌ Integración con bases de datos reales.

❌ Integración con APIs externas.

❌ Implementación de mensajería.

❌ Pruebas de integración.

❌ Pruebas end-to-end.

---

## Tecnologías Utilizadas

- Java 21
- Spring Boot
- Maven
- JUnit 5
- Mockito
- JaCoCo

---

## Objetivos de Aprendizaje

Al finalizar el taller, el participante estará en capacidad de:

- Identificar qué debe probarse mediante pruebas unitarias.
- Diseñar pruebas para reglas de negocio complejas.
- Utilizar Mockito para simular dependencias externas.
- Validar excepciones y comportamientos esperados.
- Verificar interacciones entre componentes.
- Interpretar métricas de cobertura de código.
- Aplicar buenas prácticas en ambientes empresariales basados en Spring Boot y Arquitectura Hexagonal.

---

## Estructura General

```text
.
├── presentation/
│   └── Material del taller
│
├── loan-service/
│   └── Ejercicio guiado
│
├── coupon-service/
│   └── Ejercicio de práctica
│
└── README.md
```

---

## Recomendaciones

Antes de iniciar los ejercicios se recomienda tener conocimientos básicos de:

- Java
- Spring Boot
- Maven
- Programación orientada a objetos

Asimismo, se recomienda ejecutar las pruebas de forma periódica durante el desarrollo para validar el comportamiento esperado de cada regla de negocio.

---

## Autor

Material académico creado para el taller de **Pruebas Unitarias con Spring Boot y Mockito** dictado en la EVC de depósitos y cuentas especiales de vicepresidencia de tecnología de Bancolombia, enfocado en el fortalecimiento de habilidades de testing sobre casos de uso en Arquitectura Limpia.
