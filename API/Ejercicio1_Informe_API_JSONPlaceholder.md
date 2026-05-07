# Informe Técnico – Análisis de la API JSONPlaceholder

**Materia:** Procesamiento de la Información  
**Escuela:** ET N° 20 – Polo Educativo Mataderos  
**Profesor:** Fernandez Bogarin, Nicolas Nahuel  

---

## ¿Qué es JSONPlaceholder?

JSONPlaceholder (https://jsonplaceholder.typicode.com) es una API REST falsa pero funcional, usada para prototipado y pruebas. Devuelve datos de ejemplo en formato JSON sin necesidad de autenticación. Es de solo lectura: los métodos POST, PUT y DELETE simulan cambios pero no los persisten realmente.

---

## Endpoint 1: `/posts`

| Propiedad | Detalle |
|-----------|---------|
| URL completa | `https://jsonplaceholder.typicode.com/posts` |
| Método HTTP principal | `GET` |
| Descripción | Devuelve una lista de 100 publicaciones ficticias |

### Métodos disponibles

| Método | URL | Acción |
|--------|-----|--------|
| GET | `/posts` | Lista todas las publicaciones |
| GET | `/posts/{id}` | Obtiene una publicación por ID (1–100) |
| GET | `/posts/{id}/comments` | Obtiene los comentarios de una publicación |
| POST | `/posts` | Crea una publicación (simulado) |
| PUT | `/posts/{id}` | Reemplaza una publicación (simulado) |
| PATCH | `/posts/{id}` | Actualiza parcialmente (simulado) |
| DELETE | `/posts/{id}` | Elimina una publicación (simulado) |

### Parámetros de consulta (query params)

| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| `userId` | número | Filtra publicaciones por usuario. Ej: `/posts?userId=1` |

### Formato de respuesta (GET /posts)

```json
[
  {
    "userId": 1,
    "id": 1,
    "title": "sunt aut facere repellat provident",
    "body": "quia et suscipit\nsuscipit recusandae..."
  }
]
```

### Campos del objeto `Post`

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | número | Identificador único de la publicación |
| `userId` | número | ID del usuario autor |
| `title` | string | Título de la publicación |
| `body` | string | Contenido del texto |

### Código de estado HTTP esperado

- `200 OK` → lista o elemento encontrado  
- `201 Created` → POST exitoso  
- `200 OK` → PUT/PATCH/DELETE exitoso (simulado)

---

## Endpoint 2: `/users`

| Propiedad | Detalle |
|-----------|---------|
| URL completa | `https://jsonplaceholder.typicode.com/users` |
| Método HTTP principal | `GET` |
| Descripción | Devuelve una lista de 10 usuarios ficticios con datos completos |

### Métodos disponibles

| Método | URL | Acción |
|--------|-----|--------|
| GET | `/users` | Lista todos los usuarios |
| GET | `/users/{id}` | Obtiene un usuario por ID (1–10) |
| POST | `/users` | Crea un usuario (simulado) |
| PUT | `/users/{id}` | Reemplaza un usuario (simulado) |
| DELETE | `/users/{id}` | Elimina un usuario (simulado) |

### Parámetros de consulta

| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| `username` | string | Filtra por nombre de usuario. Ej: `/users?username=Bret` |

### Formato de respuesta (GET /users)

```json
[
  {
    "id": 1,
    "name": "Leanne Graham",
    "username": "Bret",
    "email": "Sincere@april.biz",
    "address": {
      "street": "Kulas Light",
      "suite": "Apt. 556",
      "city": "Gwenborough",
      "zipcode": "92998-3874",
      "geo": {
        "lat": "-37.3159",
        "lng": "81.1496"
      }
    },
    "phone": "1-770-736-8031 x56442",
    "website": "hildegard.org",
    "company": {
      "name": "Romaguera-Crona",
      "catchPhrase": "Multi-layered client-server neural-net",
      "bs": "harness real-time e-markets"
    }
  }
]
```

### Campos del objeto `User`

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | número | Identificador único |
| `name` | string | Nombre completo |
| `username` | string | Nombre de usuario |
| `email` | string | Correo electrónico |
| `address` | objeto | Dirección completa (calle, ciudad, geo) |
| `phone` | string | Teléfono de contacto |
| `website` | string | Sitio web personal |
| `company` | objeto | Datos de la empresa donde trabaja |

---

## Endpoint 3: `/todos`

| Propiedad | Detalle |
|-----------|---------|
| URL completa | `https://jsonplaceholder.typicode.com/todos` |
| Método HTTP principal | `GET` |
| Descripción | Devuelve una lista de 200 tareas (to-do items) ficticias |

### Métodos disponibles

| Método | URL | Acción |
|--------|-----|--------|
| GET | `/todos` | Lista todas las tareas |
| GET | `/todos/{id}` | Obtiene una tarea por ID (1–200) |
| POST | `/todos` | Crea una tarea (simulado) |
| PUT | `/todos/{id}` | Reemplaza una tarea (simulado) |
| PATCH | `/todos/{id}` | Actualiza parcialmente (simulado) |
| DELETE | `/todos/{id}` | Elimina una tarea (simulado) |

### Parámetros de consulta

| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| `userId` | número | Filtra tareas por usuario. Ej: `/todos?userId=2` |
| `completed` | booleano | Filtra por estado. Ej: `/todos?completed=true` |

### Formato de respuesta (GET /todos)

```json
[
  {
    "userId": 1,
    "id": 1,
    "title": "delectus aut autem",
    "completed": false
  }
]
```

### Campos del objeto `Todo`

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | número | Identificador único de la tarea |
| `userId` | número | ID del usuario dueño de la tarea |
| `title` | string | Descripción de la tarea |
| `completed` | booleano | `true` si la tarea fue completada, `false` si no |

---

## Resumen comparativo de los tres endpoints

| Endpoint | Recursos | Cantidad | Filtros disponibles | Anidado relacionado |
|----------|----------|----------|---------------------|---------------------|
| `/posts` | Publicaciones | 100 | `userId` | `/posts/{id}/comments` |
| `/users` | Usuarios | 10 | `username` | `/users/{id}/posts`, `/users/{id}/todos` |
| `/todos` | Tareas | 200 | `userId`, `completed` | — |

---

## Cabeceras HTTP importantes en la respuesta

Todas las respuestas de JSONPlaceholder incluyen:

```
Content-Type: application/json; charset=utf-8
Access-Control-Allow-Origin: *   ← permite uso desde cualquier dominio (CORS habilitado)
```

---

## Observaciones y conclusiones

1. **Formato universal**: todos los endpoints devuelven JSON, el estándar de facto en APIs REST modernas.
2. **Sin autenticación**: JSONPlaceholder no requiere API key ni token, lo que la hace ideal para aprender.
3. **CORS abierto**: se puede consumir directamente desde el navegador o con `fetch()` en JavaScript sin problemas de origen cruzado.
4. **Escritura simulada**: los métodos POST, PUT, PATCH y DELETE responden con éxito pero no persisten datos; al recargar, los datos originales vuelven.
5. **Relaciones entre recursos**: los recursos están relacionados (un usuario tiene posts y todos), lo que permite practicar consultas anidadas.
6. **Convención REST correcta**: usa sustantivos en plural (`/posts`, `/users`, `/todos`), IDs en la URL para recursos individuales, y métodos HTTP según la acción (GET para leer, POST para crear, etc.).

---

## Ejemplo de uso con `fetch()` en JavaScript

```javascript
// GET - Listar todos los posts
fetch('https://jsonplaceholder.typicode.com/posts')
  .then(response => response.json())
  .then(data => console.log(data));

// GET - Obtener usuario por ID
fetch('https://jsonplaceholder.typicode.com/users/1')
  .then(response => response.json())
  .then(user => console.log(user));

// GET - Filtrar todos completados del usuario 1
fetch('https://jsonplaceholder.typicode.com/todos?userId=1&completed=true')
  .then(response => response.json())
  .then(tareas => console.log(tareas));

// POST - Crear un nuevo post (simulado)
fetch('https://jsonplaceholder.typicode.com/posts', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    title: 'Mi nuevo post',
    body: 'Contenido del post',
    userId: 1
  })
})
  .then(response => response.json())
  .then(nuevoPost => console.log(nuevoPost)); // Devuelve el objeto creado con id: 101
```

---

*Informe elaborado para la materia Procesamiento de la Información – ET N° 20*
