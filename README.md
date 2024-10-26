# TaskTracking: A Collaborative Task Tracking API

## Overview

TaskTracking is a backend API designed for facilitating task tracking and management within teams or projects. 
This system allows users to create, assign, and track tasks, collaborate with team members and enhance organizational productivity.

## ENDPoints

- User authentication and Register -> /auth 
- Secure profile management - /user
- Task management -> /task
- project collaboration -> /project

#### Future
- Comments and attachments for collaborative task management.
- Implement real-time updates and notifications.
- integrate a generative AI model to help in task descriptions

## Technology Stack

- **Backend:** Java , Spring Boot
- **Database:** h2

## Prerequisites

- Java 17+ and Maven/Gradle (for Java variant)
- Database system (H2)

## Setup Instructions

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/bhargavd2/taskTrackingSystem.git
   cd taskTrackingSystem

2. **Install Dependencies:**
   ```bash
   mvn clean install
   
3. **Run the Application**
   ```bash
   mvn spring-boot:run

## Using the Application

- all Exposed Endpoint are printed on console 
- add /v3/api-docs
- add swagger in http://localhost:8080/swagger-ui/index.html


