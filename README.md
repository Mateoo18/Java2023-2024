# 💬 Chatbot Reservation System

This project is a simple command-based **chatbot server** built in Java using **sockets** and **MySQL** for storing and managing restaurant reservations. The application consists of a TCP server, client, and business logic that interprets user commands such as adding, deleting, and listing reservations.

---

## ⚙️ Features

- 🧠 Text-based conversation with a chatbot
- 📅 Add, list, and delete reservations
- ✅ Confirmation mechanism before deletion
- 💾 MySQL database backend
- 🧵 Multithreaded server (handles multiple clients)
- 🔌 Socket communication over TCP

---

## 📁 Project Structure

```
src/
├── client/
│   └── ChatClient.java
├── server/
│   ├── ChatServer.java
│   └── ClientHandler.java
├── logic/
│   └── ChatbotLogic.java
├── database/
│   ├── DatabaseConnection.java
│   └── IDatabaseConnection.java
├── response/
│   ├── Response.java
│   └── ResponseType.java
```

---

## 💻 Technologies Used

- Java 11+
- MySQL (via JDBC)
- Sockets (java.net)
- Multithreading
- SQL prepared statements (safe against injection)

---

## 🧠 Commands Supported

- `rezerwuj` – Add a new reservation  
- `pokaż rezerwacje` – List all current reservations  
- `usuń rezerwację <id>` – Request to delete reservation (asks for confirmation)  
- `tak` / `nie` – Confirmation for deletion  
- Any unrecognized command triggers a default error response

---

## 🗃️ Example MySQL Table

```sql
CREATE TABLE reservations (
  id INT AUTO_INCREMENT PRIMARY KEY,
  customer_name VARCHAR(100),
  reservation_time TIMESTAMP,
  number_of_guests INT
);
```

---

## ▶️ How to Run

### 1. Start MySQL

Make sure your database is running and `chatbot_db` is created with table `reservations`.

### 2. Compile

```bash
javac -d out $(find ./src -name "*.java")
```

### 3. Start Server

```bash
java -cp out org.chatbot.server.ChatServer
```

### 4. Start Client (in a separate terminal)

```bash
java -cp out org.chatbot.client.ChatClient
```

You can now chat with the bot via terminal input.

---

## 🧩 Possible Improvements

- Natural language understanding (e.g., NLP for flexible input)
- WebSocket support or REST API
- Persistent client sessions
- GUI interface for clients
- Localization / language switching

---

## 👤 Author

**Mateusz Nowak**  
Socket & Java Developer | Interested in conversational AI and backend systems
