# 🛒 E-Commerce Web Application (Spring Boot + Thymeleaf + Spring Security)

🚧 **Project Status: Under Active Development (Continuously Improving & Adding Features)**
This project is not fully completed yet and is actively being enhanced with new modules, bug fixes, and performance improvements.

---

# 📌 Project Overview

A full-stack E-Commerce web application built using **Spring Boot, Spring Security, Thymeleaf, and MySQL** following the MVC architecture.
The application supports role-based authentication (Admin & User), product and category management, shopping cart functionality, secure password reset via email, and dynamic UI rendering using Thymeleaf.

This project is being developed as a **placement-oriented real-world project** and is continuously upgraded with new features.

---

# 🚧 Current Development Status

* ✔ Core E-Commerce functionality implemented
* ✔ Admin Dashboard & Role-Based Access completed
* ✔ Add to Cart System implemented
* ✔ Security & Authentication working

---

# ✨ Key Features (Based on Actual Implementation)

## 🔐 Authentication & Security

* Custom Login System using Spring Security
* Role-Based Authorization (ROLE_USER & ROLE_ADMIN)
* BCrypt Password Encryption
* Custom Authentication Success & Failure Handlers
* Secure Route Protection (`/admin/**`, `/user/**`)
* Principal-Based User Session Management
* Unauthorized Access Prevention (403 Security)
* Token-Based Password Reset via Email

---

## 👤 User Features

* User Registration with Profile Image Upload
* Secure Login & Logout
* Dynamic Navbar with User Info
* Forgot Password & Reset Password (Email Token)
* Session-Based Success & Error Messaging

Endpoints:

* `/signin` – Login Page
* `/register` – Registration Page
* `/forget-password` – Request Password Reset
* `/reset-password` – Secure Password Update

---

## 🛍️ Product Browsing System

* View All Active Products
* Filter Products by Category
* Product Detail Page
* Dynamic Product Rendering with Thymeleaf
* Discount & Dynamic Pricing Handling

Endpoints:

* `/products`
* `/product/{id}`

---

## 🛒 Shopping Cart Module

* Add to Cart Functionality
* User-Specific Cart Management
* Dynamic Cart Count in Navbar
* Role-Protected Cart Access
* Session Feedback Messages

Endpoints:

* `/user/addCart`
* `/user/cart`

---

## 🧑‍💼 Admin Dashboard (Secure Panel)

* Role-Based Admin Access (Only ADMIN)
* Admin Home Dashboard
* Product & Category Management Panel
* User Management System
* Account Status Control (Enable/Disable Users)

Endpoint:

* `/admin/`

---

## 📦 Product Management (Admin)

* Add New Product with Image Upload
* Edit & Update Products
* Delete Products
* Discount Validation Logic
* Server-Side Image Storage Handling

---

## 📂 Category Management (Admin)

* Add Category with Image Upload
* Edit & Delete Categories
* Activate/Deactivate Categories
* Duplicate Category Validation
* Dynamic Category Loading (Active Only)

---

## 👥 User Management (Admin Feature)

* View All Registered Users
* Role-Based User Filtering (ROLE_USER)
* Enable / Disable User Accounts
* Failed Attempt Tracking Support

---

## 📧 Password Reset System (Advanced Feature)

* Forgot Password via Email
* UUID Token Generation
* Secure Reset Link Validation
* Token Expiry Handling
* Encrypted Password Update

---

## 📸 File Upload System

* Product Image Upload
* Category Image Upload
* Profile Image Upload (Registration)
* Static Server Image Storage
* Default Image Handling & Replacement

---

# 🏗️ System Architecture (MVC)

This project follows the **MVC (Model-View-Controller)** architecture:

### 1️⃣ Controller Layer

Handles HTTP requests and user navigation

* HomeController → Public pages & authentication
* UserController → Cart & user features
* AdminController → Admin dashboard & management

### 2️⃣ Service Layer

Contains business logic and application processing

* UserService
* ProductService
* CategoryService
* CartService

### 3️⃣ Repository Layer

Handles database operations using JPA/Hibernate

* UserRepository
* ProductRepository
* CategoryRepository
* CartRepository

### 4️⃣ View Layer (Frontend)

* Thymeleaf Templates
* Bootstrap UI
* Dynamic Layout Fragments

Flow:
User Request → Controller → Service → Repository → Database → View (Thymeleaf)

---

# 🛠️ Tech Stack

## Backend

* Java 17+
* Spring Boot
* Spring MVC
* Spring Security
* Spring Data JPA (Hibernate)

## Frontend

* Thymeleaf
* HTML5, CSS3
* Bootstrap
* Font Awesome

## Database

* MySQL

## Tools

* Maven
* Git & GitHub
* STS / IntelliJ IDEA
* Jakarta Mail (Email Service)

---

# 🔐 Security Highlights

* Role-Based Route Protection
* BCrypt Password Encoding
* Custom Login Page (`/signin`)
* Secure Password Reset via Email Token
* Admin Panel Hidden for Unauthorized Users
* Protected URLs (`/admin/**`, `/user/**`)

---

# 📂 Project Modules

* Authentication & Authorization Module
* Admin Dashboard Module
* Product Management Module
* Category Management Module
* Shopping Cart Module
* Password Reset (Email Token) Module
* File Upload System

---

# 📈 Recent Updates (Changelog)

* Implemented Add-to-Cart Feature
* Added Role-Based Admin Visibility in Navbar
* Fixed Thymeleaf Routing & Navigation Issues
* Improved Spring Security Configuration
* Dynamic Cart Count in Navbar
* Controller Refactoring & Bug Fixes

---

# 🧑‍💻 Author

**Harshit Singh**
Java | Spring Boot | Full Stack Developer

---

# ⭐ GitHub Topics (Recommended Tags)

spring-boot
thymeleaf
ecommerce
spring-security
java
mvc-architecture
shopping-cart
admin-dashboard
full-stack-project
under-development

---

# 📢 Note

This project is continuously evolving and new features, optimizations, and modules are being added regularly as part of ongoing development and learning.
