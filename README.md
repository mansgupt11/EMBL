# Getting Started

This is a POC for EMBL-EBI Technical test. This application consist of Persons CRUD operation api's

Technology Used
1- Java 11
2- Spring Boot
3- Spring security With JWT
4- Docker for containerization
5- Mongo DB for database
6- Maven for Build tool
7- Lombok to reduce boiler plate code (To configure this application on an IDE such as IntelliJ, Lombok annotation processor needs to be enabled)
8- Swagger for documentation
9- Junit & Mockito for testing

To build application

Prerequisite
     Docker Engine should be up and running to run this application as docker container

1 - docker-compose up -d

#For API Documentation

Swagger documentation can be accessed on the below url

   http://localhost:8080/swagger-ui.html


#For JWT Token

http://localhost:8080/authenticate

Request Type: Post
Request Body : 

{
"username":"admin",
"password": "admin"
}

For POC, I have hardcoded username and password to admin and admin (In real world, It should be authenticated via database or ldap etc.)


