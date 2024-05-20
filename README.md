# Spring Security Practice

---
It's a basic use of Spring Security in order to improve skills.

### **Dependencies**:

- Lombok
- Configuration Processor
- Spring Data JPA
- Spring Security
- H2 Database
- Spring Web
- Validation

---

## Database Access

``http://localhost/8080/h2``

- Database: ``test``
- Username: ``admin``
- Password: ``admin``

---

## Public Endpoints

### Log-In:``http://localhost/8080/login``
### Request-Body Log-in Example
``{
"username" : "user-one",
"password" : "1234"
}``


### Register: ``http://localhost/8080/register``
### Request-Body Register Example
``{
"username" : "yellow-duck",
"password" : "burnout",
"email": "duck@aol.com"
}
``

### Role Assign: ``http://localhost/8080/addRole/{userId}/{roleId}``