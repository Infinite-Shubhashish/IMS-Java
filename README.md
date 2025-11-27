# Issue Management System API Documentation

This document describes the API endpoints for the **Issue Management System (IMS)**. It includes authentication, user management, post management, filtering, and statistics endpoints.

---

## Base URL
```
http://localhost:8080
```

---

## Authentication

- Most endpoints require authentication.
- Register or login to obtain credentials.
- Use **Basic Auth** for endpoints requiring authentication.
- Example of Basic Auth:
    - **Username:** `admin`
    - **Password:** `Admin@123`

---

# 1. Post Endpoints

### 1.1 Register User
- **URL:** `/auth/register`
- **Method:** `POST`
- **Auth:** None
- **Headers:** `Content-Type: application/json`
- **Body:**
```json
{
  "username": "User2",
  "password": "User2@123",
  "firstName": "User2",
  "lastName": "Karki",
  "email": "john.doe@example.com",
  "contact": 9843582796,
  "roles": [{ "roleName": "USER" }]
}
```
- **Response:** User object or success message

### 1.2 Register Admin
- **URL:** `/auth/register`
- **Method:** `POST`
- **Auth:** None
- **Headers:** `Content-Type: application/json`
- **Body:**
```json
{
  "username": "admin",
  "password": "admin@123",
  "firstName": "admin",
  "lastName": "Karki",
  "email": "john.doe@example.com",
  "contact": 9843582796,
  "roles": [{ "roleName": "ADMIN" }]
}
```

### 1.3 Login
- **URL:** `/auth/login`
- **Method:** `POST`
- **Body:**
```json
{
  "username": "admin",
  "password": "Admin@123"
}
```
- **Response:** JWT token or success message

### 1.4 Create Post
- **URL:** `/api/posts`
- **Method:** `POST`
- **Auth:** Basic Auth
- **Body Example:**
```json
{
  "name": "Post title or description"
}
```
- **Response:** Created post object

### 1.5 Submit Post for Approval
- **URL:** `/api/posts/{id}/submit`
- **Method:** `POST`
- **Auth:** Basic Auth
- **Response:** Post submitted status

### 1.6 Approve Post by Admin
- **URL:** `/api/posts/{id}/approve`
- **Method:** `POST`
- **Auth:** Basic Auth (Admin)
- **Response:** Approval confirmation

### 1.7 Reject Post by Admin
- **URL:** `/api/posts/{id}/reject`
- **Method:** `POST`
- **Auth:** Basic Auth (Admin)
- **Body Example:**
```json
{
  "comment": "Reason for rejection"
}
```
- **Response:** Rejection confirmation

### 1.8 Close Post by Admin
- **URL:** `/api/posts/{id}/close`
- **Method:** `POST`
- **Auth:** Basic Auth (Admin)
- **Body Example:**
```json
{
  "comment": "completed"
}
```
- **Response:** Close confirmation

---

# 2. Get Endpoints

### 2.1 Get Post by ID
- **URL:** `/api/posts/{id}`
- **Method:** `GET`
- **Auth:** Basic Auth
- **Response:** Post object

### 2.2 Paginated Posts
- **URL:** `/api/posts?page={page}&size={size}`
- **Method:** `GET`
- **Auth:** Basic Auth
- **Response:** List of posts

### 2.3 Get User Status Summary
- **URL:** `/api/posts/status-summary`
- **Method:** `GET`
- **Auth:** Basic Auth
- **Response:** Summary of user's posts by status

---

# 3. Filter Endpoints

### 3.1 Get My Posts
- **URL:** `/api/posts/my`
- **Method:** `GET`
- **Auth:** Basic Auth
- **Response:** List of user posts

### 3.2 Get My Posts by Status
- **URL:** `/api/posts/my?status={status}`
- **Method:** `GET`
- **Auth:** Basic Auth
- **Response:** List of user posts filtered by status

### 3.3 Get My Posts by Type
- **URL:** `/api/posts/my?type={type}`
- **Method:** `GET`
- **Auth:** Basic Auth
- **Response:** List of user posts filtered by type

### 3.4 Get Posts by Status
- **URL:** `/api/posts?status={status}`
- **Method:** `GET`
- **Auth:** Basic Auth
- **Response:** List of posts filtered by status

### 3.5 Get Posts by Type
- **URL:** `/api/posts?type={type}`
- **Method:** `GET`
- **Auth:** Basic Auth
- **Response:** List of posts filtered by type

---

# 4. Count / Statistics Endpoints

### 4.1 Count by Status
- **URL:** `/api/posts/count?status={status}`
- **Method:** `GET`
- **Auth:** Basic Auth
- **Response:** Count of posts by status

### 4.2 Count by Type
- **URL:** `/api/posts/count?type={type}`
- **Method:** `GET`
- **Auth:** Basic Auth
- **Response:** Count of posts by type

### 4.3 Count My Posts by Type
- **URL:** `/api/posts/my/count?type={type}`
- **Method:** `GET`
- **Auth:** Basic Auth
- **Response:** Count of user posts by type

### 4.4 Count My Posts by Status
- **URL:** `/api/posts/my/count?status={status}`
- **Method:** `GET`
- **Auth:** Basic Auth
- **Response:** Count of user posts by status

### 4.5 Count Total Posts
- **URL:** `/api/posts/count/total`
- **Method:** `GET`
- **Auth:** Basic Auth
- **Response:** Total posts count

### 4.6 Count My Total Posts
- **URL:** `/api/posts/my/count/total`
- **Method:** `GET`
- **Auth:** Basic Auth
- **Response:** Total count of user posts

---

# 5. User Endpoints

### 5.1 User Login
- **URL:** `/auth/login`
- **Method:** `POST`
- **Body:**
```json
{
  "username": "User1",
  "password": "User1@123"
}
```
- **Response:** JWT token

### 5.2 Register User/Admin
- **URL:** `/auth/register`
- **Method:** `POST`
- **Body:** JSON object with user/admin details
- **Response:** Registered user object

### 5.3 Paginated Users
- **URL:** `/api/users?page={page}&size={size}&sortBy={field}`
- **Method:** `GET`
- **Auth:** Basic Auth
- **Response:** Paginated list of users

---

**End of Documentation**

