# KnowYourMood_Backend

A complete **Spring Boot + MySQL + JWT Authentication** backend for a Mood Tracking Application.

Users can:
- ✅ Register & Login
- ✅ Log daily moods
- ✅ View mood history (analytics ready)
- ✅ Secure authentication using JWT

---

## Tech Stack

| Component        | Technology Used |
|------------------|------------------|
| Backend Framework | Spring Boot (3.x) |
| Database          | MySQL (Hosted on Railway) |
| Build Tool        | Maven |
| Authentication    | JWT (JSON Web Tokens) |
| ORM               | Hibernate / JPA |

Method	Endpoint	     Description
POST	/auth/register	Register new user
POST	/auth/login	    Login & return JWT
POST	/mood/add	       Add mood entry (Auth required)
GET  	/mood/history	    Get user's mood history

Make sure to give credit if you use this: Credits: Created by Udbhav Awasthi (github.com/dgbru77)


If you like this project, consider giving the repo a star ⭐ on GitHub.
