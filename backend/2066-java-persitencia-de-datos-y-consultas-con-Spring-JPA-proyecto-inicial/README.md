![Programação-Java_ Persistencia de datos y consultas con Spring Data JPA](https://github.com/genesysR-dev/2066-java-persitencia-de-datos-y-consultas-con-Spring-JPA/assets/91544872/e0e3a9f8-afc7-4e7b-be83-469351ef2d70)

# ScreenMatch

Proyecto desarrollado durante el segundo curso de la formación Avanzando con Java de Alura

## � Estructura del Proyecto

```
proyecto/
├── BACKEND.md                           # 🔙 Identificador del Backend
├── src/main/java/.../screenmatch/      # Código Java Spring Boot
├── src/main/resources/                  # Configuración y recursos
├── pom.xml                              # Dependencias Maven
└── frontend/                            # 🎨 Aplicación Frontend
    └── FRONTEND.md                      # Identificador del Frontend
```

## 🔨 Objetivos del proyecto

* Avanzar en el proyecto Screenmatch, iniciado en el primer curso de la formación, creando un menú con varias opciones;
* Modelar las abstracciones de la aplicación a través de clases, enums, atributos y métodos;
* Consumir la API del ChatGPT(Opcional;
* Utilizar Spring Data JPA para persistir datos en la base de datos;
* Conocer varios tipos de bases de datos y utilizar PostgreSQL;
* Trabajar con varios tipos de consultas a la base de datos;
* Profundizar en la interfaz JPA Repository.

## ⚠️ Nota de Seguridad

**Clave de API ubicada en:**
- `src/main/java/com/aluracursos/screenmatch/principal/Principal.java` (línea 15)

```java
private final String API_KEY = "&apikey=723c0b5";
```

💡 **Recomendación:** Considera mover esta clave a variables de entorno antes de hacer commits públicos.
