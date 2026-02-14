# Spring Project 
<img width="1902" height="907" alt="image" src="https://github.com/user-attachments/assets/d4e6d39d-5692-43c2-a427-746443a4cfe3" />


Este proyecto es una aplicación backend desarrollada con **Spring Boot**, expuesta como **API REST**, pensada para ser consumida por un **frontend** y conectada a una base de datos **PostgreSQL**.

## Tecnologías utilizadas

- Java  
- Spring Boot  
- Spring Web  
- Spring Data JPA  
- PostgreSQL  
- Maven  

## Arquitectura

El proyecto sigue una arquitectura en capas:

- **Controller**: Manejo de las peticiones HTTP  
- **Service**: Lógica de negocio  
- **Repository**: Acceso a datos  
- **Entity**: Modelos persistentes  
- **DTO (opcional)**: Transferencia de datos entre capas  

El backend expone endpoints REST que pueden ser consumidos por cualquier cliente frontend (web o móvil).

## Configuración del proyecto

### Base de datos

La aplicación utiliza **PostgreSQL**.  
Configura las credenciales en el archivo `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tu_base_de_datos
spring.datasource.username=usuario
spring.datasource.password=password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
Ejecución
Ejecuta la aplicación desde el método main o usando Maven:

mvn spring-boot:run
Por defecto, el servidor se inicia en:

http://localhost:8080

### Frontend
Este proyecto está diseñado para funcionar como backend, por lo que el frontend se desarrolla de manera independiente.
La comunicación se realiza mediante peticiones HTTP en formato JSON.

Estado del proyecto
Proyecto en desarrollo con enfoque educativo y práctico para el aprendizaje de Spring Boot, APIs REST y conexión con bases de datos relacionales.
