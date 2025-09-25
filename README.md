# 🌌 Kronos API

Kronos API is a RESTful service built with **Spring Boot** for managing **Users, Students, Subjects, and Attendance Records** in academic environments.  
It powers features like **faculty login, student management, subject CRUD, and attendance tracking**.

## 🚀 Deployment

**Base URL**

[https://kronos-api-l0cm.onrender.com](https://kronos-api-l0cm.onrender.com)

---

## 📖 Table of Contents

- [Authentication & Users](#-authentication--users)
- [Students](#-students)
- [Subjects](#-subjects)
- [Attendance](#-attendance)
- [Response Codes](#-response-codes)
- [Testing with cURL](#-testing-with-curl)
- [Contributing](#-contributing)

---

## 🔐 Authentication & Users

**Base Path:** `/user`

### 1. Login User

```http
POST /user/login-user
```

**Body:**

```json
{
  "username": "john123",
  "password": "mypassword"
}
```

**cURL:**

```bash
curl -X POST https://kronos-api-l0cm.onrender.com/user/login-user \
  -H "Content-Type: application/json" \
  -d '{"username":"john123","password":"mypassword"}'
```

---

### 2. Register User

```http
POST /user/register-user
```

**Body:**

```json
{
  "username": "newuser",
  "password": "secure123",
  "role": "FACULTY"
}
```

**Response:**

- `201 Created` → `1` (success)
- `200 OK` → `3` (failure)

**cURL:**

```bash
curl -X POST https://kronos-api-l0cm.onrender.com/user/register-user \
  -H "Content-Type: application/json" \
  -d '{"username":"newuser","password":"secure123","role":"FACULTY"}'
```

---

### 3. Get User by Username

```http
GET /user/get-user-by-username/{username}
```

Example:

```
/user/get-user-by-username/john123
```

**cURL:**

```bash
curl https://kronos-api-l0cm.onrender.com/user/get-user-by-username/john123
```

---

### 4. Get All Users

```http
GET /user/get-all-user
```

**cURL:**

```bash
curl https://kronos-api-l0cm.onrender.com/user/get-all-user
```

---

### 5. Get All Admins

```http
GET /user/get-all-admin
```

### 6. Get All Faculty

```http
GET /user/get-all-faculty
```

### 7. Delete User by Username

```http
DELETE /user/delete-user-by-username?username=john123
```

### 8. Update User

```http
PUT /user/update-user
```

**Body:**

```json
{
  "username": "john123",
  "password": "newpass",
  "role": "ADMIN"
}
```

---

## 🎓 Students

**Base Path:** `/student`

### 1. Get All Students

```http
GET /student/get-all-students
```

### 2. Add Student

```http
POST /student/add-student
```

**Body:**

```json
{
  "name": "Alice",
  "rollNumber": "2025CS01"
}
```

### 3. Get Student by ID

```http
GET /student/get-student-by-id/{id}
```

### 4. Update Student

```http
PUT /student/update-student
```

### 5. Delete Student

```http
DELETE /student/delete-student/{id}
```

---

## 📚 Subjects

**Base Path:** `/subject`

### 1. Get All Subjects

```http
GET /subject/get-all-subjects
```

### 2. Add Subject

```http
POST /subject/add-subject
```

**Body:**

```json
{
  "name": "Mathematics",
  "code": "MTH101"
}
```

### 3. Get Subject by ID

```http
GET /subject/get-subject-by-id/{id}
```

### 4. Update Subject

```http
PUT /subject/update-subject
```

### 5. Delete Subject

```http
DELETE /subject/delete-subject/{id}
```

---

## 🕒 Attendance

**Base Path:** `/attendance`

### 1. Get All Attendance Records

```http
GET /attendance/get-all-attendance-records
```

### 2. Get Attendance by Date & Subject

```http
GET /attendance/get-attendance-by-date-subjet/{date}/{subjectId}
```

Example:

```
/attendance/get-attendance-by-date-subjet/2025-09-23/3
```

### 3. Take Attendance

```http
POST /attendance/take-attendance
```

**Body:**

```json
{
  "username": "faculty01",
  "subjectId": 2,
  "date": "2025-09-23",
  "time": "10:00",
  "studentIds": [1, 2, 3]
}
```

**Response:**

```json
{
  "id": 15,
  "date": "2025-09-23",
  "time": "10:00",
  "subject": { "id": 2, "name": "Math" },
  "students": [
    { "id": 1, "name": "Alice" },
    { "id": 2, "name": "Bob" }
  ],
  "numberOfStudents": 2,
  "user": { "username": "faculty01" }
}
```

---

## ✅ Response Codes

| Code                        | Meaning                       |
| --------------------------- | ----------------------------- |
| `200 OK`                    | Successful Request            |
| `201 Created`               | Resource Created Successfully |
| `400 Bad Request`           | Invalid Request Payload       |
| `401 Unauthorized`          | Authentication Failed         |
| `404 Not Found`             | Resource Not Found            |
| `500 Internal Server Error` | Server Error                  |

---

## 🧪 Testing with cURL

Example - Take Attendance:

```bash
curl -X POST https://kronos-api-l0cm.onrender.com/attendance/take-attendance \
  -H "Content-Type: application/json" \
  -d '{
    "username": "faculty01",
    "subjectId": 2,
    "date": "2025-09-23",
    "time": "10:00",
    "studentIds": [1,2,3]
  }'
```

---

## 🤝 Contributing

1. Fork the repo
2. Create a feature branch (`git checkout -b feature/new-feature`)
3. Commit changes (`git commit -m 'Add new feature'`)
4. Push (`git push origin feature/new-feature`)
5. Open a Pull Request

---

✨ Kronos API — Bringing **clarity** and **order** to academic management.
