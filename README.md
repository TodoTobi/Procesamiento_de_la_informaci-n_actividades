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

---

## 🚀 Cómo ejecutar los proyectos

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
