# Checklist de Cumplimiento de Consignas
## Ejercicios: Creación y Uso de APIs RESTful
**ET N° 20 – Polo Educativo Mataderos**  
**Materia:** Procesamiento de la Información  
**Profesor:** Fernandez Bogarin, Nicolas Nahuel  
**Alumno:** Tobias Vera

---

## Módulo 1 – Fundamentos de APIs REST

### Ejercicio 1 – Análisis de una API existente
> *Objetivo: Analizar cómo funciona una API real (JSONPlaceholder)*

| Consigna | Estado | Detalle |
|----------|--------|---------|
| Ingresar a https://jsonplaceholder.typicode.com | ✅ Cumplido | Se accedió y analizó la API |
| Probar endpoint `/posts` | ✅ Cumplido | Documentado con método, respuesta y parámetros |
| Probar endpoint `/users` | ✅ Cumplido | Documentado con todos los campos del objeto User |
| Probar endpoint `/todos` | ✅ Cumplido | Documentado incluyendo filtro por `completed` |
| Documentar qué hace cada endpoint | ✅ Cumplido | Tabla comparativa de los 3 endpoints |
| Documentar qué método HTTP usa | ✅ Cumplido | GET, POST, PUT, PATCH, DELETE por endpoint |
| Documentar qué devuelve (formato de respuesta) | ✅ Cumplido | JSON de ejemplo incluido para cada uno |
| Documentar qué parámetros acepta | ✅ Cumplido | Query params documentados (`userId`, `completed`, `username`) |
| **Resultado esperado:** informe técnico | ✅ Cumplido | Archivo `Ejercicio1_Informe_API_JSONPlaceholder.md` |

---

## Módulo 2 – Desarrollo de una API propia

### Ejercicio 2 – Crear una API de "Alumnos"
> *Objetivo: Crear una API desde cero con Java + Spring Boot*

| Consigna | Estado | Detalle |
|----------|--------|---------|
| GET `/api/alumnos` → lista todos | ✅ Cumplido | `AlumnoController.listar()` |
| GET `/api/alumnos/{id}` → busca uno por ID | ✅ Cumplido | `AlumnoController.buscarPorId()` |
| POST `/api/alumnos` → crea nuevo alumno | ✅ Cumplido | `AlumnoController.crear()` |
| PUT `/api/alumnos/{id}` → edita un alumno | ✅ Cumplido | `AlumnoController.editar()` |
| DELETE `/api/alumnos/{id}` → elimina un alumno | ✅ Cumplido | `AlumnoController.eliminar()` |
| Base de datos MySQL | ✅ Cumplido | Conectado a MySQL via XAMPP, tabla `alumnos` creada automáticamente |
| Uso de Swagger | ✅ Cumplido | Swagger UI disponible en `localhost:8080/swagger-ui.html` |
| Separación en capas: Modelo | ✅ Cumplido | `Alumno.java` – entidad JPA |
| Separación en capas: Repositorio | ✅ Cumplido | `AlumnoRepository.java` – extiende JpaRepository |
| Separación en capas: Servicio | ✅ Cumplido | `AlumnoService.java` – lógica de negocio |
| Separación en capas: Controlador | ✅ Cumplido | `AlumnoController.java` – endpoints REST |
| **Resultado esperado:** API funcional en localhost:8080 | ✅ Cumplido | Verificado con datos persistiendo en MySQL/XAMPP |

### Ejercicio 3 – Agregar validaciones y manejo de errores
> *Objetivo: Robustecer la API agregando controles*

| Consigna | Estado | Detalle |
|----------|--------|---------|
| Validar que el nombre del alumno no esté vacío | ✅ Cumplido | `@NotBlank` en `Alumno.java` |
| Capturar errores si se consulta un ID inexistente | ✅ Cumplido | `AlumnoNotFoundException` lanza error 404 |
| Devolver mensajes personalizados en los errores | ✅ Cumplido | `GlobalExceptionHandler` con `@ControllerAdvice` |
| Usar `@ExceptionHandler` o `@ControllerAdvice` | ✅ Cumplido | Implementado en `GlobalExceptionHandler.java` |
| **Resultado esperado:** API que no se rompe con datos incorrectos | ✅ Cumplido | Respuestas 400, 404 y 500 con JSON descriptivo |

---

## Módulo 3 – Consumo de la API

### Ejercicio 4 – App de escritorio (JavaFX o Swing) que use la API
> *Objetivo: Conectar un sistema externo a la API*

| Consigna | Estado | Detalle |
|----------|--------|---------|
| App que liste alumnos (GET) | ✅ Cumplido | Tabla de alumnos en `GestionEscolarDesktop.java` con Swing |
| App que permita agregar uno nuevo (POST) | ✅ Cumplido | Formulario de alta en la app de escritorio |
| Usar `HttpURLConnection` o `HttpClient` de Java | ✅ Cumplido | Implementado con `HttpClient` de Java 11 |
| **Resultado esperado:** app de escritorio que se comunica con la API | ✅ Cumplido | `GestionEscolarDesktop.java` operativo |

### Ejercicio 5 – Página web con JS puro que consuma la API
> *Objetivo: Mostrar datos de la API en una interfaz web*

| Consigna | Estado | Detalle |
|----------|--------|---------|
| HTML + JS que liste alumnos usando `fetch()` | ✅ Cumplido | Tabla dinámica en `index.html` con `fetch()` |
| Formulario para agregar un alumno nuevo | ✅ Cumplido | Formulario con validación y respuesta visual |
| **Resultado esperado:** interfaz web conectada al backend | ✅ Cumplido | Datos guardados en MySQL verificados desde el frontend |

---

## Módulo 4 – Seguridad, versión y documentación

### Ejercicio 6 – Documentar la API con Swagger
> *Objetivo: Aprender a usar Swagger/OpenAPI*

| Consigna | Estado | Detalle |
|----------|--------|---------|
| Agregar dependencia Swagger en Spring Boot | ✅ Cumplido | `springdoc-openapi-starter-webmvc-ui 2.3.0` en `pom.xml` |
| Verificar que todos los endpoints estén documentados | ✅ Cumplido | Los 5 endpoints de alumnos + login visibles en Swagger UI |
| Usar anotaciones para mostrar descripción | ✅ Cumplido | `@Operation(summary=..., description=...)` en cada método |
| Usar anotaciones para mostrar parámetros | ✅ Cumplido | `@Parameter` y `@ApiResponse` en el controlador |
| Usar anotaciones para mostrar ejemplos de uso | ✅ Cumplido | `@Schema(example=...)` en cada campo del modelo |
| **Resultado esperado:** Swagger UI en `/swagger-ui.html` con todo documentado | ✅ Cumplido | Accesible en `http://localhost:8080/swagger-ui.html` |

### Ejercicio 7 – Agregar autenticación básica con JWT
> *Objetivo: Proteger la API*

| Consigna | Estado | Detalle |
|----------|--------|---------|
| Agregar endpoint de login: POST `/api/login` | ✅ Cumplido | `AuthController.login()` |
| Retornar un token JWT si los datos son correctos | ✅ Cumplido | `JwtUtil.generarToken()` devuelve token firmado con HMAC-SHA256 |
| Proteger los demás endpoints (requieren token) | ✅ Cumplido | `JwtFilter` intercepta cada pedido y valida el token |
| **Resultado esperado:** solo usuarios autenticados pueden acceder | ✅ Cumplido | Sin token → 403. Con token válido → respuesta normal |

---

## Actividad Integradora Final
> *Objetivo: Sistema completo multi-plataforma con una API común*

| Consigna | Estado | Detalle |
|----------|--------|---------|
| API RESTful de estudiantes | ✅ Cumplido | CRUD completo en `EstudianteController` |
| API RESTful de materias | ✅ Cumplido | CRUD completo en `MateriaController` |
| API RESTful de calificaciones | ✅ Cumplido | CRUD + promedio en `CalificacionController` |
| Conectar desde app de escritorio en Java | ✅ Cumplido | `GestionEscolarDesktop.java` con Swing + HttpClient |
| Conectar desde página web con JS | ✅ Cumplido | `frontend/index.html` con fetch() y tabs por entidad |
| Documentar la API con Swagger | ✅ Cumplido | `SwaggerConfig.java` + anotaciones en todos los controladores |
| Agregar autenticación JWT | ✅ Cumplido | Login → token → protección de todos los endpoints |
| **Resultado esperado:** sistema multi-plataforma con una única API | ✅ Cumplido | Backend único consumido desde web y escritorio |

---

## Resumen general

| Módulo | Ejercicios | Consignas totales | Cumplidas |
|--------|-----------|-------------------|-----------|
| Módulo 1 – Fundamentos | Ejercicio 1 | 8 | 8 ✅ |
| Módulo 2 – Desarrollo | Ejercicios 2 y 3 | 17 | 17 ✅ |
| Módulo 3 – Consumo | Ejercicios 4 y 5 | 6 | 6 ✅ |
| Módulo 4 – Seguridad | Ejercicios 6 y 7 | 8 | 8 ✅ |
| Actividad Integradora | Final | 8 | 8 ✅ |
| **Total** | | **47** | **47 ✅** |

**Todas las consignas del trabajo práctico fueron cumplidas.**

---

*ET N° 20 – Procesamiento de la Información – Tobias Vera*
