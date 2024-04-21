# Library Management System

Welcome to the Library Management System, a comprehensive application designed to manage and automate various aspects of a library's operations. This system demonstrates expertise in developing and deploying a Spring Boot application using advanced security measures, effective testing, and a robust backend architecture.


# Project Description

The Library Management System is a full-stack web application that simplifies library operations such as book management, patron management, and borrowing records management. This system provides an intuitive interface for library staff and administrators while ensuring data integrity and security through advanced backend features.


# Entities:

**Book**: Includes attributes like ID, title, author, publication year, ISBN, etc.

**Patron**: Contains details like ID, name, contact information, etc.

**Borrowing Record**: Tracks the association between books and patrons, including borrowing and return dates.


# API Endpoints:

Book management endpoints:



* GET /api/books: Retrieve a list of all books.
* GET /api/books/{id}: Retrieve details of a specific book by ID.
* POST /api/books: Add a new book to the library.
* PUT /api/books/{id}: Update an existing book's information.
* DELETE /api/books/{id}: Remove a book from the library.
* Patron management endpoints:

Book management endpoints:



* GET /api/patrons: Retrieve a list of all patrons.
* GET /api/patrons/{id}: Retrieve details of a specific patron by ID.
* POST /api/patrons: Add a new patron to the system.
* PUT /api/patrons/{id}: Update an existing patron's information.
* DELETE /api/patrons/{id}: Remove a patron from the system.
* Borrowing endpoints:

Borrow management endpoints:



* POST /api/borrow/{bookId}/patron/{patronId}: Allow a patron to borrow a book.
* PUT /api/return/{bookId}/patron/{patronId}: Record the return of a borrowed book by a patron.


# Features

**Book Management**: Add, update, delete, and retrieve books with ease.

**Patron Management**: Handle patrons, including adding, updating, deleting, and retrieving patron details.

**Borrowing Records**: Manage borrowing records, including creating, updating, and deleting records.

**JWT Authentication**: Secure endpoints with JSON Web Token (JWT) authentication.

**Role-Based Authorization**: Control access to sensitive operations with role-based permissions (e.g., ROLE_ADMIN).

**RESTful API**: Interact with the system using a RESTful API for seamless integration with front-end applications.

**Testing Suite**: Comprehensive tests for the API endpoints ensure high-quality code and functionality.


# Technologies Used

**Spring Boot**: Framework for building modern, lightweight, and scalable applications.

**Spring Security**: Provides advanced security features including JWT authentication and role-based authorization.

**Hibernate**: ORM framework for efficient database operations and data management.

**JUnit and AssertJ**: Tools for comprehensive testing, including unit and integration tests.

**TestRestTemplate**: Simplifies API testing by providing convenient REST template operations.

**PostgreSQL**: Robust and reliable relational database for data storage.

**BCrypt**: Secure password encoding for data integrity and user authentication.


# Setup and Installation

**Clone the Repository**: Clone the repository to your local machine.

bash

Copy code

git clone https://github.com/MrtnOmwenga/library-management-system.git

**Configure Database**: Update the database connection settings in application.properties for PostgreSQL.

**Install Dependencies**: Use Maven to install dependencies and build the project.

bash

Copy code

mvn clean install

**Run the Application**: Launch the Spring Boot application.

bash

Copy code

mvn spring-boot:run


# Usage

**API Endpoints**: Use the API endpoints to interact with the system for various operations.

Authentication: Authenticate with the system using the /api/login endpoint and include the JWT token in the Authorization header for secured operations.

**Admin Credentials**: Only the admin credentials can perform sensitive operations such as creating, updating, and deleting resources.


# Testing

**Automated Tests:** Comprehensive tests are provided to ensure system functionality and data integrity.

**Tests for Each Controller**: Specific tests are included for book, patron, and borrowing record controllers.

**JWT Validation**: All tests utilize JWT authentication for secured operations.

Security

**JWT Authentication**: Provides secure token-based authentication for endpoints.

**Role-Based Authorization**: Controls access to sensitive operations based on user roles (e.g., ROLE_ADMIN).


# Future Enhancements

**Frontend Integration**: Potential integration with a user-friendly frontend for enhanced user experience.

**Advanced Features**: Explore advanced features such as book recommendations and data analytics for enhanced library management.


# Contact

For questions or further inquiries, please feel free to contact us at omwenga.mrtn@gmail.com.

Thank you for your interest in the Library Management System!
