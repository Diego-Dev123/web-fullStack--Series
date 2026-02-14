# Spring Project 
inicio
<img width="1902" height="907" alt="image" src="https://github.com/user-attachments/assets/d4e6d39d-5692-43c2-a427-746443a4cfe3" />
mostrar temporadas
<img width="1911" height="793" alt="image" src="https://github.com/user-attachments/assets/b6f67a28-4d9f-437d-8337-2eba119909d4" />
mirando las temporadas
<img width="1331" height="750" alt="image" src="https://github.com/user-attachments/assets/fd382d0a-26dd-405f-b2da-344f09a1ed11" />


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
<img width="1791" height="767" alt="image" src="https://github.com/user-attachments/assets/6ef23347-6bbf-4920-a5c5-a9f478fa7a41" />

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
<img width="1607" height="786" alt="image" src="https://github.com/user-attachments/assets/47411228-89cd-4ae5-a992-9a10d46d4d48" />

Las credenciales y configuración se definen en el archivo `application.properties`:
<img width="1733" height="337" alt="image" src="https://github.com/user-attachments/assets/e949987b-db62-4d09-93cc-7db69ef82d82" />
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
