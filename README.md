# ORION_MDD

## Description
<img src="./front/src/assets/pictures/small_logo.png" alt="Logo" style="float: right; margin-left: 20px;" />  

This project is the MVP for Orion's new social media dedicated to developers.  
- Users can subscribe to and unsubscribe from themes.  
- Subscribed users will see articles related to the themes they follow on their article feed.  
- Users can view individual articles, comment on them, and see other users' comments.  
- Users can create articles by specifying the theme and title  
- Articles can be filtered by creation date in ascending or descending order.  
- Users have a profile page where they can update their username and email.  
- Users can view the themes they are subscribed to on their profile page.  
- Includes registration and login pages for user authentication.  
- Users can navigate through the app, log in, and log out as needed

This repository contains both the frontend and backend code for the Minimum Viable Product (MVP) of the application.

## Prerequisites
Before starting, make sure the following are installed
#### <u>*Technologies Used*</u>
<div align="center">

| **Back-end**                                      | **Front-end**                                      | **Database**       | **Tools**           |
|---------------------------------------------------|----------------------------------------------------|--------------------|---------------------|
| ![Java](https://img.shields.io/badge/Java-17.0.12-blue) | ![Angular](https://img.shields.io/badge/Angular-14.1.0-brightgreen) | ![MySQL](https://img.shields.io/badge/MySQL-8.0.32-blue) | ![Git](https://img.shields.io/badge/Git-2.35.1-orange) |
| ![Spring Boot](https://img.shields.io/badge/Spring_Boot-2.7.3-green) | ![Angular CLI](https://img.shields.io/badge/Angular_CLI-14.1.3-blue) |                    | ![GitHub](https://img.shields.io/badge/GitHub--black) |
| ![Apache Maven](https://img.shields.io/badge/Maven-3.9.9-yellow) | ![Node.js](https://img.shields.io/badge/Node.js-20.17.0-brightgreen) |                    | ![Postman](https://img.shields.io/badge/Postman-11.34.5-blue) |
| ![Spring Security](https://img.shields.io/badge/Spring_Security-2.7.3-orange) | ![rxjs](https://img.shields.io/badge/rxjs-7.5.0-blue) |                    | ![Visual Studio Code](https://img.shields.io/badge/VS_Code-1.97.2-blue)|
| ![Spring OAuth2](https://img.shields.io/badge/Spring_OAuth2-2.7.3-red) | ![TypeScript](https://img.shields.io/badge/TypeScript-4.7.2-blue) |                    |                     |
| ![JJWT](https://img.shields.io/badge/JJWT-0.11.5-green) | ![Angular Material](https://img.shields.io/badge/Angular_Material-14.2.5-blue) |                    |                     |
| ![ModelMapper](https://img.shields.io/badge/ModelMapper-3.2.0-blue) | ![Zone.js](https://img.shields.io/badge/Zone.js-0.11.4-lightblue) |                    |                     |
| ![MapStruct](https://img.shields.io/badge/MapStruct-1.5.1.Final-green) |                                                    |                    |                     |
| ![Lombok](https://img.shields.io/badge/Lombok-1.18.30-lightgray) |                                                    |                    |                     |
</div>
<br>

## Install the Application
1- Clone the git repository with the following command in a terminal
````
git clone https://github.com/chirazpiriou/ORION_MDD.git
````
2- Install the frontend dependencies

In your terminal, execute the following command to install the dependencies specified in the package.json file.
    
 ```
cd front
npm install
```

3- Install the backend dependencies

In your terminal, execute the command below to install the dependencies specified in the pom.xml file.
    
```
cd back
mvn clean install
```

## Setting Up the Database
To create the database, follow these steps:
- Make sure that MySQL Server is running on your machine before proceeding.
- You can create the database using either MySQL Workbench for a graphical interface or MySQL CLI for command-line interaction.
- Authenticate by logging in with your MySQL username and password "root" for both
- Navigate to the following path to find the SQL script:  `./back/schema.sql`.

- In MySQL Workbench, go to `File > Open SQL Script`, and select the script located at the path above.
- At the beginning of the script, add the following lines to ensure that the `db_mdd` database is created:
   ```sql
   CREATE DATABASE `db_mdd`;
   USE `db_mdd`;

- Run the Script: Click on the lightning bolt icon (⚡) in MySQL Workbench to execute the script.
- To connect the database to the Spring Boot API, the necessary configurations have been set up in the back/src/main/resources/application.properties file.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/db_MDD
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

```

## Run the Application
**- Frontend**

Make sure you are in the frontend directory (front). Then, follow these steps:
* Run the following command to launch the Angular application
```
ng serve
```
* Once the command completes, open your browser and go to http://localhost:4200. The frontend will be live and accessible. 

**- Backend**

Ensure you are in the backend directory (back). Then, follow these steps:
* Start the Spring Boot backend server by running:
```
mvn spring-boot:run
```
* Once the backend is up and running, the API will be available at http://localhost:8080.

## Authentication
The API uses JWT for authentication. Protected endpoints require a valid JWT token in the Authorization header.

## Responsive Design

- This application is fully responsive and adapts to various screen sizes, ensuring an optimal user experience across devices.
- Media queries have been used throughout the application to provide flexible layouts, images, and buttons that adjust based on the screen width.
- Whether you're using a mobile phone, tablet, or desktop, the application will automatically adjust its design to fit your device.

## Environment Variable Configuration
You need to define this environment variable to make it work:  
**- PASSWORD_db_MDD ->**  The password for the database   

```
PASSWORD_db_MDD : root
```