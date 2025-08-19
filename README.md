---

# 🍔 Chat-Based Online Ordering System

A **real-time chat-driven food ordering platform** built with **Java 21, Spring Boot, React, MySQL, and Redis**.
Customers can interact with a chatbot to browse menu items, add/remove items from their cart, and place food orders.
Admins can manage menu items and update order statuses, with **live order tracking** via WebSocket.
<img width="1536" height="1024" alt="Online Ordering Chat Interface" src="https://github.com/user-attachments/assets/9be42fec-9e26-4976-9c9f-31fe504c3965" />



---

## 🚀 Features

### 👤 User (Customer)

* 🔐 Secure login using **JWT / OAuth2**
* 💬 **Chatbot-based ordering**

  * Add items: `add 2 burgers`
  * Remove items: `remove fries`
  * Checkout: `checkout`
* 🛒 Cart stored in **Redis**
* 📦 Place orders and view status in real-time

### 🛠️ Admin

* ➕ Manage menu items (CRUD)
* 📊 View and update order statuses (CREATED → PAID → PREPARING → READY → DELIVERED)
* 🔒 **Role-based access control** (CUSTOMER vs ADMIN)

### ⚡ Tech Highlights

* **Java 21 + Spring Boot 3.5** – backend & REST APIs
* **MySQL** – persistent storage (users, menu, orders)
* **Redis** – fast cart storage & pub/sub
* **WebSocket (STOMP)** – real-time messaging
* **React (Vite)** – interactive frontend chat interface
* **JWT / OAuth2 Security** – secure access

---

## 🏗️ Architecture

```
[ React Frontend ]
   |-- REST: /api/menu, /api/cart, /api/orders
   |-- WS (STOMP): /ws -> /topic/order-updates, /user/queue/replies
        |
[ Spring Boot Backend ]
   |-- Auth: JWT / OAuth2 (CUSTOMER, ADMIN)
   |-- Services: ChatBot, Cart, Order, Payment (mock)
   |-- MySQL: users, menu_items, orders, order_items
   |-- Redis: cart:{userId}, order-stream pub/sub
```

---

## 📂 Project Structure

```
chat-based-online-ordering-system/
│── backend/
│   ├── src/main/java/com/example/orderingsystem
│   │   ├── config/       # Security, WebSocket, CORS configs
│   │   ├── controller/   # REST + WebSocket controllers
│   │   ├── model/        # JPA entities
│   │   ├── repo/         # Spring Data JPA repositories
│   │   ├── service/      # ChatBot, Cart, Order services
│   │   └── Application.java
│   └── resources/
│       ├── application.yml
│       └── data.sql      # Sample menu items
│
│── frontend/
│   ├── src/
│   │   ├── api.js        # Axios client
│   │   ├── ws.js         # STOMP WebSocket client
│   │   ├── App.jsx       # Chat UI
│   │   └── components/   # UI components
│   └── package.json
│
└── README.md
```

---

## 🗄️ Database Schema (MySQL)

```sql
users(id, email, password, role, created_at)
menu_item(id, name, price, in_stock)
orders(id, user_id, status, total, created_at)
order_item(id, order_id, menu_item_id, qty, price)
```

---

## ⚙️ Installation & Setup

### 🔧 Prerequisites

* **Java 21**
* **Maven**
* **Node.js (16+)**
* **MySQL 8**
* **Redis 7**
* Docker (optional, for local MySQL & Redis)

### 1️⃣ Backend Setup

```bash
cd backend
mvn spring-boot:run
```

### 2️⃣ Frontend Setup

```bash
cd frontend
npm install
npm run dev
```

### 3️⃣ Run Databases via Docker (optional)

```bash
docker run --name mysql -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql:8
docker run --name redis -p 6379:6379 -d redis:7
```

---

## 🔐 Authentication

* **Option A (Dev Mode):** Use a simple `/auth/login` endpoint to issue JWTs
* **Option B (Production):** Integrate with **Auth0 / Keycloak / Google OAuth2**

JWT contains:

```json
{
  "sub": "user123",
  "roles": ["CUSTOMER"]
}
```

---

## 💬 Sample Chat Flow

1. **User:** `add 2 burgers`
   **Bot:** `Added 2 × Burger to your cart.`

2. **User:** `checkout`
   **Bot:** `Ready to checkout! Type 'place order' or click 'Place Order'.`

3. **User clicks:** **Place Order**
   ✅ Order created → broadcast to `/topic/order-updates`

4. **Admin:** Updates order status → customers see **real-time updates**

---

## 📸 Screenshots

### 🔹 Chat UI

💬 Chat panel on the left, 📋 menu & order updates on the right.
*(insert screenshot here)*

---

## 🧩 Future Enhancements

* 🏦 Payment Gateway integration (Stripe, Paystack)
* 🤖 AI/NLP-powered chatbot (LangChain/Rasa)
* 📱 Mobile app (React Native)
* 🐳 Docker Compose for full stack setup

---

## 🤝 Contributing

Contributions are welcome!

* Fork the repo
* Create a feature branch (`git checkout -b feature-x`)
* Commit changes (`git commit -m 'add feature x'`)
* Push branch & create PR

---

## 📜 License

This project is licensed under the **MIT License**.

---

## 👨‍💻 Author

Built with ❤️ by **\[ENGRIPAYE]**

---

👉 Do you want me to also **include the README badges (build, license, tech stack icons, etc.)** to make it more visually professional, like a top GitHub project?
