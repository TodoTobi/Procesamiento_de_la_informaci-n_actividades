<<<<<<< HEAD
# 📚 Procesamiento de la Información
### Escuela Técnica N° 20 – Polo Educativo Mataderos

> **Materia:** Laboratorio de Desarrollo de Aplicaciones Informáticas  
> **Profesor:** Fernandez Bogarin, Nicolas Nahuel  
> **Alumno:** Tobias Vera  
> **Año:** 2025

---

## 🗂️ Índice

- [📌 Sobre este repositorio](#-sobre-este-repositorio)
- [🧵 Unidad 1 – Hilos (Threads)](#-unidad-1--hilos-threads)
- [🌐 Unidad 2 – APIs RESTful](#-unidad-2--apis-restful)
- [🤖 Unidad 3 – Inteligencia Artificial y LLMs](#-unidad-3--inteligencia-artificial-y-llms)
- [🐳 Unidad 4 – Integración y Despliegue](#-unidad-4--integración-y-despliegue)
- [📦 Tecnologías utilizadas](#-tecnologías-utilizadas)
- [🚀 Cómo ejecutar los proyectos](#-cómo-ejecutar-los-proyectos)
- [📈 Estado del repositorio](#-estado-del-repositorio)

---

## 📌 Sobre este repositorio

Este repositorio contiene **todos los trabajos prácticos, ejercicios y proyectos** desarrollados a lo largo de la materia *Procesamiento de la Información*, estructurados bajo estándares de ingeniería de software de nivel profesional (**FAANG**).

Los contenidos abarcan desde la programación concurrente con hilos en Java, hasta el desarrollo de APIs RESTful seguras con autenticación JWT, consumo de APIs desde múltiples plataformas, e introducción a la arquitectura de Modelos de Lenguaje (LLMs).

> ⚠️ Este repositorio está **en construcción activa**. Se irán agregando nuevas unidades, ejercicios y proyectos a medida que avanza el cursado.

---

## 🧵 Unidad 1 – Hilos (Threads)

Programación concurrente en Java: creación, sincronización y coordinación de hilos.

### 📁 `1.Hilos/` — Ejercicios introductorios

| # | Archivo | Concepto |
|---|---------|----------|
| 1 | `Ejercicio_1.java` | Creación de un hilo básico con `Thread` y `sleep()` |
| 2 | `Ejercicio_2.java` | Dos hilos en paralelo (números y letras) |
| 3 | `Ejercicio_3.java` | Contador compartido con `synchronized` |
| 5 | `Ejercicio_5.java` | Patrón Productor-Consumidor con `wait()` / `notify()` |
| 6 | `Ejercicio_6.java` | `ExecutorService` con `FixedThreadPool` |
| 7 | `Ejercicio_7.java` | `Callable` y `Future` para retornar resultados |
| 8 | `Ejercicio_8.java` | Deadlock: cómo provocarlo y cómo prevenirlo |
| 9 | `Ejercicio_9.java` | Simulación de descargas concurrentes |
| 10 | `Ejercicio_10.java` | `SwingWorker` con barra de progreso en UI |

### 📁 `2.Hilos/` — Ejercicios avanzados (TP Hilos 2)

| # | Archivo | Concepto |
|---|---------|----------|
| 1 | `Ejercicio01_OrdenConJoin.java` | Orden garantizado con `join()` |
| 2 | `Ejercicio02_HiloInterrumpido.java` | `interrupt()`, `isInterrupted()`, `InterruptedException` |
| 3 | `Ejercicio03_RunVsStart.java` | Diferencia fundamental: `run()` vs `start()` |
| 4 | `Ejercicio04_CarreraAutos.java` | Carrera con tiempos aleatorios y ranking sincronizado |
| 5 | `Ejercicio05_ContadorSinSincronizar.java` | Race condition intencional — por qué el resultado falla |
| 6 | `Ejercicio06_ContadorConAtomicInteger.java` | `AtomicInteger` y operaciones CAS |
| 7 | `Ejercicio07_Semaforo.java` | Semáforo con `volatile` y `enum` |
| 8 | `Ejercicio08_TurnosImpresion.java` | Exclusión mutua con método `synchronized` |
| 9 | `Ejercicio09_BancoMultiplesCuentas.java` | Transferencias thread-safe + prevención de deadlock |
| 10 | `Ejercicio10_MiniScheduler.java` | `ExecutorService`, pool de hilos, `awaitTermination()` |
| 11 | `Ejercicio11_DescargaProgreso.java` | Progreso compartido con `AtomicInteger` + hilo monitor |
| 12 | `Ejercicio12_ChatEntreHilos.java` | Chat entre hilos con `wait()` y `notify()` |
| 13 | `Ejercicio13_DeadlockSolucion.java` | Deadlock: condiciones de Coffman + orden canónico |
| 14 | `Ejercicio14_SistemaPedidos.java` | `BlockingQueue` + Productor-Consumidor + `SwingWorker` |

<details>
<summary><strong>📖 Conceptos clave de la unidad</strong></summary>

- **Thread vs Process:** un proceso tiene memoria propia; un hilo comparte memoria dentro del proceso → creación más eficiente pero requiere sincronización.
- **Ciclo de vida:** `New → Runnable → Blocked/Waiting → Dead`.
- **Race Condition:** `contador++` no es atómica (leer → sumar → escribir). Dos hilos leyendo el mismo valor antes de que alguno escriba = incremento perdido.
- **`synchronized`:** garantiza exclusión mutua (mutex). Solo un hilo a la vez ejecuta el bloque protegido.
- **`volatile`:** garantiza visibilidad inmediata de cambios entre hilos (sin caching en registros de CPU).
- **`AtomicInteger`:** operaciones atómicas usando CAS (Compare-And-Swap) del hardware. Más eficiente que `synchronized` para contadores simples.
- **`wait()` / `notify()`:** coordinación entre hilos. `wait()` libera el lock y suspende; `notify()` despierta un hilo en espera. Solo dentro de `synchronized`.
- **Deadlock:** ciclo de espera circular. Solución: adquirir locks siempre en el mismo orden (orden canónico).
- **`ExecutorService`:** pool de hilos reutilizables. Evita crear/destruir hilos manualmente.
- **`BlockingQueue`:** cola thread-safe. `put()` bloquea si está llena; `take()` bloquea si está vacía. Ideal para Productor-Consumidor.
- **`SwingWorker`:** ejecuta tareas pesadas fuera del EDT y comunica progreso a la UI de forma segura.

</details>

---

## 🌐 Unidad 2 – APIs RESTful

Diseño, desarrollo y consumo de APIs REST con Java + Spring Boot.

### 📁 `API/ejercicios_2_al_7/` — API de Alumnos

API REST completa con autenticación JWT, documentación Swagger y arquitectura en capas.

**Endpoints:**

| Método | URL | Descripción |
|--------|-----|-------------|
| `POST` | `/api/login` | Obtener token JWT |
| `GET` | `/api/alumnos` | Listar todos los alumnos |
| `GET` | `/api/alumnos/{id}` | Buscar alumno por ID |
| `POST` | `/api/alumnos` | Crear nuevo alumno |
| `PUT` | `/api/alumnos/{id}` | Editar alumno existente |
| `DELETE` | `/api/alumnos/{id}` | Eliminar alumno |

**Estructura del proyecto:**
```
src/
├── controller/    AlumnoController.java, AuthController.java
├── service/       AlumnoService.java
├── repository/    AlumnoRepository.java
├── model/         Alumno.java
├── security/      JwtFilter.java, JwtUtil.java
├── exception/     GlobalExceptionHandler.java, AlumnoNotFoundException.java
└── config/        SecurityConfig.java, SwaggerConfig.java
```

**Ejercicios cubiertos:**

| # | Contenido |
|---|-----------|
| Ej. 1 | Análisis de API real (JSONPlaceholder) — informe técnico |
| Ej. 2 | CRUD completo de alumnos con Spring Boot + MySQL |
| Ej. 3 | Validaciones (`@NotBlank`, `@Pattern`) y manejo de errores (`@ControllerAdvice`) |
| Ej. 4 | App de escritorio en Java Swing que consume la API |
| Ej. 5 | Frontend web en HTML + JS (`fetch()`) conectado al backend |
| Ej. 6 | Documentación automática con Swagger / OpenAPI |
| Ej. 7 | Autenticación stateless con JWT (JSON Web Token) |

### 📁 `API/ejercicio_8/` — Sistema de Gestión Escolar (Actividad Integradora)

Sistema completo multi-plataforma con una única API backend.

**Entidades:** Estudiantes · Materias · Calificaciones

**Plataformas que consumen la API:**
- 🖥️ App de escritorio Java Swing (`GestionEscolarDesktop.java`)
- 🌍 Frontend web (`frontend/index.html`) con HTML + JS puro

**Características:** CRUD completo · JWT · Swagger · MySQL · Cálculo de promedios · Manejo de errores global

<details>
<summary><strong>📖 Conceptos clave de la unidad</strong></summary>

- **REST:** arquitectura basada en HTTP + JSON. Sustantivos en plural para recursos, métodos HTTP para acciones.
- **Arquitectura N-Tier:** Controller → Service → Repository → Model/Entity.
- **Códigos de estado:** `200 OK`, `201 Created`, `204 No Content`, `400 Bad Request`, `401 Unauthorized`, `404 Not Found`, `500 Internal Server Error`.
- **JWT:** token firmado (HMAC-SHA256) que viaja en el header `Authorization: Bearer <token>`. Autenticación stateless (sin sesión en servidor).
- **Spring Security:** intercepta cada request en el filtro `JwtFilter` antes de que llegue al controller.
- **`@ControllerAdvice`:** manejo centralizado de excepciones para toda la API.
- **Swagger/OpenAPI:** genera documentación interactiva automáticamente desde las anotaciones del código.
- **CORS:** `@CrossOrigin(origins = "*")` permite que el frontend web consuma la API desde otro origen.

</details>

---

## 🤖 Unidad 3 – Inteligencia Artificial y LLMs

> 🚧 **Próximamente** — contenidos en desarrollo

Arquitectura de Transformers, tokenización, embeddings y sistemas RAG.

**Temas que se abordarán:**
- Arquitectura Transformer y mecanismo de autoatención (self-attention)
- Tokens y embeddings: representación vectorial de conceptos
- Parámetros de modelos: diferencias entre modelos 7B, 65B y su impacto en hardware (VRAM/GPU)
- Alucinaciones y cómo mitigarlas
- RAG (Retrieval-Augmented Generation): Vector Store + retriever
- Fine-tuning vs RAG: cuándo usar cada enfoque
- Ecosistema de modelos open-source (LLaMA, Mistral, etc.)

---

## 🐳 Unidad 4 – Integración y Despliegue

> 🚧 **Próximamente** — contenidos en desarrollo

Contenedores, CI/CD y despliegue en producción.

**Temas que se abordarán:**
- Docker: contenedores para garantizar paridad entre entornos
- `Dockerfile` y `docker-compose.yml` para el stack Java + MySQL
- Variables de entorno para configuración segura
- Introducción a CI/CD
- Despliegue en la nube

---

## 📦 Tecnologías utilizadas

| Tecnología | Versión | Uso |
|------------|---------|-----|
| Java | 17 / 21 | Lenguaje principal |
| Spring Boot | 3.2.0 | Framework web y API REST |
| Spring Security | 3.2.0 | Autenticación y autorización |
| Spring Data JPA | 3.2.0 | Abstracción de base de datos |
| MySQL | 8.x | Base de datos relacional |
| JWT (jjwt) | 0.11.5 | Tokens de autenticación |
| Swagger / OpenAPI | 2.3.0 | Documentación de la API |
| Java Swing | JDK built-in | Apps de escritorio |
| HTML + JS | ES6+ | Frontend web sin frameworks |
| Maven | 3.x | Gestión de dependencias |
| Docker | — | Contenedores *(próximamente)* |
=======
# 📚 Procesamiento de la Información — ET N° 20

> **Escuela Técnica N° 20 – Polo Educativo Mataderos**  
> **Materia:** Procesamiento de la Información  
> **Profesor:** Fernandez Bogarin, Nicolas Nahuel  
> **Alumno:** Tobias Vera  

Repositorio con todos los trabajos prácticos, ejercicios y proyectos desarrollados a lo largo del año. El contenido abarca desde programación concurrente en Java hasta el desarrollo de APIs RESTful completas con seguridad y documentación, siguiendo estándares de ingeniería de software de nivel profesional (**FAANG**).

---

## 🗂️ Estructura del repositorio

```
📁 1.Hilos/           → Ejercicios introductorios de hilos en Java
📁 2.Hilos/           → Ejercicios avanzados de concurrencia
📁 API/               → Desarrollo de APIs RESTful con Spring Boot
   📁 ejercicios_2_al_7/   → API de Alumnos (CRUD + JWT + Swagger)
   📁 ejercicio_8/         → Sistema Escolar completo (multi-entidad)
   📁 ejercicio_8/frontend/     → Frontend web con HTML + JS puro
   📁 ejercicio_8/desktop/      → App de escritorio con Java Swing
```

> 🔧 *Se irán agregando nuevas carpetas a medida que avance el año.*

---

## 📦 Módulos y contenidos

### 🧵 Módulo 1 — Programación Concurrente (Java)

Ejercicios progresivos sobre el manejo de hilos, sincronización y patrones de concurrencia en Java.

| # | Archivo | Tema |
|---|---------|------|
| 1 | `1.Hilos/Ejercicio_1.java` | Creación básica de un hilo con `Thread` |
| 2 | `1.Hilos/Ejercicio_2.java` | Hilos en paralelo (números y letras) |
| 3 | `1.Hilos/Ejercicio_3.java` | Contador compartido con `synchronized` |
| 4 | `1.Hilos/Ejercicio_5.java` | Productor-Consumidor con `wait()` / `notify()` |
| 5 | `1.Hilos/Ejercicio_6.java` | `ExecutorService` con `FixedThreadPool` |
| 6 | `1.Hilos/Ejercicio_7.java` | `Callable` y `Future` para retorno de resultados |
| 7 | `1.Hilos/Ejercicio_8.java` | Deadlocks: causa y solución |
| 8 | `1.Hilos/Ejercicio_9.java` | Simulación de descargas concurrentes |
| 9 | `1.Hilos/Ejercicio_10.java` | `SwingWorker` con barra de progreso |

**Ejercicios avanzados (`2.Hilos/`):**

| # | Archivo | Tema |
|---|---------|------|
| 1 | `Ejercicio01_OrdenConJoin.java` | Control de orden con `join()` |
| 2 | `Ejercicio02_HiloInterrumpido.java` | Interrupción de hilos (`interrupt()`) |
| 3 | `Ejercicio03_RunVsStart.java` | Diferencia entre `run()` y `start()` |
| 4 | `Ejercicio04_CarreraAutos.java` | Carrera de autos con ranking sincronizado |
| 5 | `Ejercicio05_ContadorSinSincronizar.java` | Race condition demostrado |
| 6 | `Ejercicio06_ContadorConAtomicInteger.java` | Solución con `AtomicInteger` |
| 7 | `Ejercicio07_Semaforo.java` | Semáforo de tráfico con `volatile` |
| 8 | `Ejercicio08_TurnosImpresion.java` | Exclusión mutua con `synchronized` |
| 9 | `Ejercicio09_BancoMultiplesCuentas.java` | Banco con transferencias y anti-deadlock |
| 10 | `Ejercicio10_MiniScheduler.java` | Mini scheduler con `ExecutorService` |
| 11 | `Ejercicio11_DescargaProgreso.java` | Progreso compartido con `AtomicInteger` |
| 12 | `Ejercicio12_ChatEntreHilos.java` | Chat entre hilos con `wait()` / `notify()` |
| 13 | `Ejercicio13_DeadlockSolucion.java` | Deadlock: provocar y resolver |
| 14 | `Ejercicio14_SistemaPedidos.java` | Sistema de pedidos + `SwingWorker` |

---

### 🌐 Módulo 2 — APIs RESTful con Spring Boot

Desarrollo completo de APIs REST con Java, desde los fundamentos hasta sistemas multi-entidad con seguridad JWT.

#### Ejercicio 1 — Análisis de API externa
Informe técnico sobre [JSONPlaceholder](https://jsonplaceholder.typicode.com): métodos HTTP, formatos de respuesta, parámetros y ejemplos con `fetch()`.
📄 [`API/Ejercicio1_Informe_API_JSONPlaceholder.md`](API/Ejercicio1_Informe_API_JSONPlaceholder.md)

#### Ejercicios 2–7 — API de Alumnos (`ejercicios_2_al_7/`)

API RESTful completa sobre un recurso único (`Alumno`) con todas las capas de una aplicación profesional.

| Ejercicio | Contenido |
|-----------|-----------|
| **Ej. 2** | CRUD completo: `GET`, `POST`, `PUT`, `DELETE` con Spring Boot + MySQL |
| **Ej. 3** | Validaciones con `@NotBlank`, `@Pattern` y manejo de errores con `@ControllerAdvice` |
| **Ej. 4** | App de escritorio en Java Swing que consume la API con `HttpClient` |
| **Ej. 5** | Frontend web en HTML + JS puro con `fetch()` |
| **Ej. 6** | Documentación automática con Swagger / OpenAPI (`/swagger-ui.html`) |
| **Ej. 7** | Autenticación stateless con JWT: login → token → protección de endpoints |

**Stack técnico:**
`Spring Boot 3.2` · `Spring Data JPA` · `Spring Security` · `MySQL` · `JWT (jjwt)` · `Swagger / SpringDoc OpenAPI`

#### Ejercicio 8 — Sistema Escolar Completo (`ejercicio_8/`)

Sistema multi-entidad que gestiona **Estudiantes**, **Materias** y **Calificaciones** con una única API que sirve tanto al frontend web como a la app de escritorio.

**Arquitectura en capas:**

```
Controller → Service → Repository → Model/Entity (MySQL)
```

**Características:**
- CRUD completo para las 3 entidades
- Cálculo de promedio por estudiante y materia
- Autenticación JWT en todos los endpoints
- Documentación Swagger con esquema de seguridad Bearer
- Frontend web con tabs, login y operaciones en tiempo real
- App de escritorio en Java Swing

📄 [`API/Checklist_Cumplimiento_Consignas.md`](API/Checklist_Cumplimiento_Consignas.md) — todas las consignas cumplidas ✅

---

## 🛠️ Tecnologías utilizadas

| Tecnología | Uso |
|------------|-----|
| **Java 17** | Lenguaje principal |
| **Spring Boot 3.2** | Framework para APIs REST |
| **Spring Data JPA / Hibernate** | Persistencia de datos |
| **Spring Security** | Seguridad y autenticación |
| **MySQL + XAMPP** | Base de datos relacional |
| **JWT (jjwt 0.11.5)** | Tokens de autenticación stateless |
| **Swagger / SpringDoc OpenAPI 2.3** | Documentación interactiva de la API |
| **Java Swing + SwingWorker** | Aplicaciones de escritorio |
| **HTML + CSS + JavaScript (fetch)** | Frontend web sin frameworks |
| **Maven** | Gestión de dependencias |
| **Docker** *(próximamente)* | Contenedores para entornos reproducibles |
>>>>>>> 31e179531b6297bf43b13bb812471c704597d56b

---

## 🚀 Cómo ejecutar los proyectos

<<<<<<< HEAD
### Ejercicios de Hilos (sin dependencias externas)

```bash
# Compilar un ejercicio
javac Ejercicio01_OrdenConJoin.java

# Ejecutar
java Ejercicio01_OrdenConJoin
```

### API REST (Spring Boot)

**Requisitos:** Java 17+, Maven, MySQL/XAMPP corriendo en `localhost:3306`

```bash
# Navegar al proyecto
cd API/ejercicios_2_al_7

# Ejecutar con Maven
mvn spring-boot:run
```

La API queda disponible en `http://localhost:8080`  
Swagger UI: `http://localhost:8080/swagger-ui.html`

**Credenciales de prueba:** usuario `admin` · contraseña `1234`

### Frontend Web

Abrir directamente en el navegador:
```
API/ejercicios_2_al_7/frontend/index.html
API/ejercicio_8/frontend/index.html
```

> Asegurarse de que la API esté corriendo antes de abrir el frontend.

---

## 📈 Estado del repositorio

| Unidad | Estado | Ejercicios |
|--------|--------|------------|
| 🧵 Hilos – Parte 1 | ✅ Completa | 9 ejercicios |
| 🧵 Hilos – Parte 2 | ✅ Completa | 14 ejercicios |
| 🌐 API REST – Módulos 1 al 4 | ✅ Completa | 7 ejercicios + actividad integradora |
| 🤖 IA y LLMs | 🚧 En desarrollo | — |
| 🐳 Docker y Despliegue | 🚧 En desarrollo | — |

---

<div align="center">

**ET N° 20 – Polo Educativo Mataderos**  
Materia: Procesamiento de la Información · 2025

</div>
=======
### APIs (Spring Boot)

**Requisitos:** Java 17+, Maven, MySQL corriendo en `localhost:3306`

```bash
# Navegar a la carpeta del proyecto
cd API/ejercicios_2_al_7

# Compilar y ejecutar
mvn spring-boot:run
```

La API queda disponible en `http://localhost:8080`.  
Swagger UI en `http://localhost:8080/swagger-ui.html`.

**Login por defecto:**
```json
POST /api/login
{ "username": "admin", "password": "1234" }
```

### Ejercicios de hilos (Java puro)

Compilar y ejecutar cualquier archivo directamente:

```bash
javac Ejercicio_1.java
java Ejercicio_1
```

---

## 📋 Conceptos clave cubiertos

**Concurrencia:**
- Ciclo de vida de un hilo (`New` → `Runnable` → `Blocked` → `Dead`)
- `synchronized`, `volatile`, `AtomicInteger`
- `wait()` / `notify()` — patrón Productor-Consumidor
- `ExecutorService`, `ThreadPool`, `Callable`, `Future`
- Deadlocks: detección, prevención y orden canónico de bloqueos
- `SwingWorker` para UIs responsivas

**APIs REST:**
- Arquitectura N-Tier: Controller / Service / Repository / Model
- Métodos HTTP y códigos de estado correctos
- Validaciones con Bean Validation (`@Valid`, `@NotBlank`, `@Pattern`)
- Manejo global de errores con `@ControllerAdvice`
- Autenticación stateless con JWT
- Documentación con Swagger/OpenAPI
- Consumo desde JS (`fetch`) y Java (`HttpClient`)
- CORS para integración frontend-backend

---

## 📅 Roadmap

- [x] Módulo de hilos básicos
- [x] Módulo de concurrencia avanzada
- [x] API RESTful de recurso único (Alumnos)
- [x] Sistema multi-entidad con JWT + Swagger
- [x] Frontend web + app de escritorio
- [ ] Arquitectura de LLMs y RAG *(próximamente)*
- [ ] Computación distribuida *(próximamente)*
- [ ] Docker y contenedores *(próximamente)*
- [ ] Más módulos del curso...

---

## 📝 Licencia

Repositorio educativo — ET N° 20, Buenos Aires.  
Todo el código fue desarrollado con fines de aprendizaje en el marco de la materia *Procesamiento de la Información*.
>>>>>>> 31e179531b6297bf43b13bb812471c704597d56b
