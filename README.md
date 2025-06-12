# Ikimina USSD Application

A Spring Boot-based USSD application for managing financial transactions and services.

## 🚀 Features

- USSD interface for mobile banking
- PostgreSQL database integration
- Spring Boot backend
- RESTful API endpoints

## 🛠️ Technologies Used

- Java
- Spring Boot
- PostgreSQL
- Hibernate/JPA
- Maven

## 📋 Prerequisites

- Java JDK 8 or higher
- Maven
- PostgreSQL
- Git

## 🔧 Installation

1. Clone the repository

```bash
git clone [your-repository-url]
```

2. Configure PostgreSQL

- Create a database named `ikimina_db`
- Update database credentials in `src/main/resources/application.properties` if needed

3. Build the project

```bash
mvn clean install
```

4. Run the application

```bash
mvn spring-boot:run
```

## ⚙️ Configuration

The application can be configured through `application.properties`:

- Server runs on port 8080
- Database connection details can be modified in the properties file
- JPA/Hibernate is configured to automatically update the database schema

## 📝 Database Configuration

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ikimina_db
spring.datasource.username=postgres
spring.datasource.password=123
```

## 🔐 Security

- Make sure to change the default database credentials in production
- Keep your application.properties secure and never commit sensitive information

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details

## 👥 Authors

- Ndayizeye Ahmed - Initial work

## 🙏 Acknowledgments

- Spring Boot Team
- PostgreSQL Community
