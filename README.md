# Spring Project 
<img width="1902" height="907" alt="image" src="https://github.com/user-attachments/assets/d4e6d39d-5692-43c2-a427-746443a4cfe3" />


# Spring Boot Backend API – Series App

Este proyecto es una aplicación **backend** desarrollada con **Spring Boot**, expuesta como una **API REST**, cuyo propósito es consumir datos de la API pública **OMDb** y almacenar información de series en una base de datos **PostgreSQL**.  
El backend está diseñado para comunicarse con un **frontend** mediante peticiones HTTP en formato JSON.

## Tecnologías utilizadas

- Java  
- Spring Boot  
- Spring Web  
- Spring Data JPA  
- Maven  
- PostgreSQL  
- API externa: OMDb  

## Descripción general

La aplicación consume datos de series desde la API de **OMDb**, procesa la información y la persiste en una base de datos PostgreSQL.  
Estos datos son posteriormente expuestos a través de endpoints REST para ser consumidos por un frontend web.

El proyecto está enfocado en la construcción de un backend estructurado, mantenible y alineado con buenas prácticas de desarrollo en Spring Boot.

## Arquitectura

El proyecto sigue una arquitectura en capas:

- **Controller**: Manejo de las solicitudes HTTP y exposición de endpoints REST.  
- **Service**: Implementación de la lógica de negocio.  
- **Repository**: Acceso y persistencia de datos mediante Spring Data JPA.  
- **Entity**: Modelos de dominio mapeados a la base de datos.  
- **DTO (opcional)**: Transferencia de datos entre capas cuando es necesario.

## Persistencia de datos

La persistencia se realiza utilizando **Spring Data JPA**, lo que permite una interacción sencilla y eficiente con la base de datos PostgreSQL sin necesidad de escribir consultas SQL manuales en la mayoría de los casos.

Las series obtenidas desde la API de OMDb se almacenan en la base de datos para su posterior consulta desde el frontend.
## Integración con el frontend
Este proyecto funciona exclusivamente como backend.
El frontend se desarrolla de manera independiente y se comunica con la API mediante peticiones HTTP, consumiendo y enviando datos en formato JSON.

Estado del proyecto
Proyecto en desarrollo con enfoque educativo y práctico, orientado al aprendizaje de Spring Boot, APIs REST, consumo de APIs externas y persistencia de datos con PostgreSQL.
## Configuración de la base de datos

La aplicación utiliza **PostgreSQL**.  
Las credenciales y configuración se definen en el archivo `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tu_base_de_datos
spring.datasource.username=usuario
spring.datasource.password=password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
Ejecución del proyecto
El proyecto puede ejecutarse desde el método main o utilizando Maven:

mvn spring-boot:run
Por defecto, la aplicación se ejecuta en:

http://localhost:8080
