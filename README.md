# Word-Library-backend

This is the backend for Word Library application which manages requests
from the frontend like user authentication, word or word set manipulation.

## Goal

This app helps people to learn and practice vocabulary in a new language by
providing predefined word lists or word lists created by users.

## Features

- **User Authentication with JWT**
- **CRUD operations for word lists and words**
- **User-defined and predefined word lists**
- **Spring Security for API protection**
- **PostgreSQL database integration**
- **Exception Handling**
- **DTOs for making data transfer easier**
- **EntityDtoMapper to map between DTOs and entities**
- **Repositories to interact with database using JPA**
- **Controllers to handle api requests**

## Technologies Used
    
- **Backend: Spring boot, Spring Security**
- **Database: PostgresSQL**
- **Authentication: JWT**
- **Frontend: Frontend will be built with React, Tailwind etc.**

## Example API Usage :

All of the other endpoints are visible at the controller classes.

### Login User
- **Endpoint:** `POST /auth/login`
    - Request Body:
    ```json
    {
      "email": "user_email",
      "password": "user_password"
    }
    ```
    - Response:
    ```json
    {
      "status": 200,
      "message": "User logged in successfully",
      "token": "jwt_token_here",
      "expirationTime": "7 days",
      "timestamp": "2025-02-13T15:01:28.35181792"
    }
    ```
---


### Filter Word Sets
- **Endpoint:** `GET /sets/filter`
- **Query Parameters:**
  - `name` (optional): Name of the set.
  - `language` (optional): Language of the word set.
- **Example Request:** `GET /sets/filter?name=English&language=English`
- **Response:**
    ```json
    {
    "status": 200,
    "message": "Wordsets filtered successfully",
    "wordSetList": [
        {
            "id": 2,
            "name": "Objects",
            "language": "ENGLISH"
        }
    ],
    "timestamp": "2025-02-13T15:07:40.007985324"
    }
    ```

---
