# Issue Management System API

This collection provides a comprehensive set of endpoints for managing issues, users, and administrative actions in an issue tracking system. It covers user registration and authentication, post creation and management, filtering and retrieving posts, administrative approvals, and various statistics endpoints. Use this collection to integrate, test, or document your issue management backend.

## Authentication

Most endpoints require authentication. Register and log in to obtain a token, then include it in your requests as needed (typically via an Authorization header).

---

## Folder Structure & Endpoints

### 1. Post

- **Register User**  
  `POST http://localhost:8080/auth/register`

- **Submit post for approval**  
  `POST http://localhost:8080/api/posts/5/submit`

- **Create Post**  
  `POST` (URL not specified in summary)

- **Register Admin**  
  `POST http://localhost:8080/auth/register`

- **Approve by admin**  
  `POST http://localhost:8080/api/posts/1/approve`

- **Reject by Admin**  
  `POST http://localhost:8080/api/posts/1/reject`

- **Close By Admin**  
  `POST http://localhost:8080/api/posts/1/close`



---

### 2. Get

- **Get post by id**  
  `GET` (URL not specified in summary)

- **Paginated posts**  
  `GET` (URL not specified in summary)

- **Get user status summary**  
  `GET` (URL not specified in summary)

---

### 3. Filter

- **GetMyPost**  
  `GET` (URL not specified in summary)

- **GetMyPostByStatus**  
  `GET` (URL not specified in summary)

- **GetMyPostByType**  
  `GET` (URL not specified in summary)

- **GetPostByStatus**  
  `GET` (URL not specified in summary)

- **GetPostByType**  
  `GET` (URL not specified in summary)

- **New Request**  
  `GET` (URL not specified in summary)

---

### 4. Count

- **CountBystatus**  
  `GET` (URL not specified in summary)

- **CountBytype**  
  `GET` (URL not specified in summary)

- **CountMyPostBytype**  
  `GET` (URL not specified in summary)

- **CountMyPostByStatus**  
  `GET` (URL not specified in summary)

- **CountTotalPosts**  
  `GET` (URL not specified in summary)

- **CountMyTotalPosts**  
  `GET` (URL not specified in summary)

---

### 5. User

- **Login**  
  `POST http://localhost:8080/auth/login`

- **New Request**  
  `GET` (URL not specified in summary)

- **Register**  
  `POST http://localhost:8080/auth/register`

---

### 6. Collection-level Endpoints

- **Paginated users**  
  `GET http://localhost:8080/api/users?page=0&size=2&sortByusername`



---

## Notes

- Replace `{id}` or other path variables in URLs as needed.
- For endpoints where the URL is not specified, please refer to the Postman collection for the exact endpoint or update the documentation as you finalize your API routes.
- Use the provided endpoints to register, authenticate, create, submit, approve, reject, close, and filter posts, as well as to retrieve user and post statistics.

---

## Getting Started

1. Register a user or admin using the registration endpoints.
2. Log in to obtain your authentication token.
3. Use the token to access protected endpoints.
4. Explore post creation, submission, approval, and filtering features.
5. Retrieve statistics and summaries as needed.

---

For more details or to execute these endpoints directly, use the Postman collection:  
[Issue Management System](collection/50280160-c4de7946-1f32-4051-938c-ef213457fd16)

---