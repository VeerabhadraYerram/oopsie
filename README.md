# 📘 **Property Recommender System**

## 🏡 Overview

The **Property Recommender System** is a Java-based console application that allows buyers, sellers, and admins to interact with a real estate marketplace.
It features **role-based dashboards**, **property management**, **booking system**, and **recommendation logic**, all connected to a **MySQL database using JDBC**.

This project demonstrates strong **OOP concepts**, **DAO-Service architecture**, **exception handling**, **collections**, **generics**, and **database integration**.

---

## 🚀 Features

### 👥 **Role-Based System**

* **Admin**
* **Seller**
* **Buyer**
* **Guest (no login)**

---

### 🟦 Guest Features

* View available properties
* Read-only browsing

---

### 🟩 Buyer Features

* Search properties by locality, price, and BHK
* Get recommended properties
* Book a property
* View & update profile

---

### 🟧 Seller Features

* Add new properties
* Update own properties
* Delete own properties
* View all properties they own

---

### 🟥 Admin Features

* Manage all properties (CRUD)
* Manage all users
* View all bookings in the system

---

## 🛠️ Tech Stack

### **Backend**

* Java 17+
* JDBC
* MySQL

### **Architecture**

* DAO (Data Access Layer)
* Services Layer
* Models Layer
* Utility Layer
* Exceptions Layer
* Menu-driven Console UI

### **OOP Concepts Used**

* Classes & Objects
* Inheritance
* Polymorphism (method overriding)
* Encapsulation
* Abstraction
* Interfaces
* Generics
* Serializable
* Collections

---

## 🗂️ Project Structure

```
src/
└── com/propertyrecommender/
    ├── App.java
    ├── config/
    │    └── DBConfig.java
    ├── util/
    │    ├── JDBCUtil.java
    │    └── InputUtil.java
    ├── model/
    │    ├── User.java
    │    ├── Property.java
    │    ├── Booking.java
    │    ├── Admin.java
    │    ├── Seller.java
    │    └── Buyer.java
    ├── dao/
    │    ├── GenericDAO.java
    │    ├── UserDAO.java
    │    ├── PropertyDAO.java
    │    └── BookingDAO.java
    ├── service/
    │    ├── AuthService.java
    │    ├── UserService.java
    │    ├── GuestService.java
    │    ├── SellerService.java
    │    ├── AdminService.java
    │    ├── PropertyService.java
    │    ├── BookingService.java
    │    └── RecommendationService.java
    └── exception/
         ├── DataAccessException.java
         └── ValidationException.java
```

---

## 🏃‍♂️ How to Run

### **1. Clone Repository**

```
git clone https://github.com/<your-username>/Oopsie.git
cd Oopsie
```

### **2. Create MySQL Database**

```
CREATE DATABASE property_recommender;
USE property_recommender;
```

Run the provided SQL script to create tables.

### **3. Update DB Credentials**

Edit:

```
src/com/propertyrecommender/config/DBConfig.java
```

Set:

```java
URL  = "jdbc:mysql://localhost:3306/property_recommender?useSSL=false&allowPublicKeyRetrieval=true";
USER = "root";
PASS = "<your_password>";
```

### **4. Run the Application**

In Eclipse / IntelliJ / VS Code:

Right-click → **Run App.java**

---

## 🧪 Sample Logins

### **Admin**

```
username: admin
password: admin123
```

### **Buyer**

```
username: buyer1
password: pass123
```

### **Seller**

```
username: seller1
password: pass123
```

---





