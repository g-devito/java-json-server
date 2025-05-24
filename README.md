
# Java JSON Server

A lightweight, multithreaded JSON server built from scratch in Java.  
Designed as part of a learning challenge to master working with sockets, concurrency, and JSON serialization in a clean and modular way.

![Java](https://img.shields.io/badge/language-Java-blue.svg)
![Status](https://img.shields.io/badge/status-ongoing-brightgreen.svg)

---

## 🚀 Project Overview

This server simulates a simple database that accepts requests from multiple clients over TCP. It supports both reading and writing of JSON-formatted data in a thread-safe manner. The project is designed to demonstrate:

- Socket programming in Java  
- Handling multiple clients via threads  
- Parsing and building JSON without frameworks  
- Basic in-memory database logic  
- Command-based protocol design  
- Clean architecture for scalability and readability

---

## 🛠 Features

- 🔄 **Store and retrieve data** using JSON keys and values
- ⚡ **Handles concurrent connections** (multithreaded)
- 📦 **In-memory database** with simple array-based structure
- 🧪 Built with **clean separation of concerns**
- 🧵 Thread-safe operations

---

## 📚 What I Learned

- How to build a TCP server and client in Java
- Manual JSON formatting and parsing
- Designing communication protocols
- Writing modular and testable code
- Version control and project structuring with Git

---

## 📁 Project Structure

```
task/
├── client/
│   └── Main.java         # Sample client implementation
├── server/
│   └── Main.java         # Server entry point
├── db/
│   └── Database.java     # In-memory JSON-like data handler
└── utils/
    └── JsonParser.java   # JSON format utilities
```

---

## 🔧 How to Run

### Prerequisites
- Java 11+
- IntelliJ IDEA (Community or Ultimate)

### Server
```bash
cd task/server
java Main
```

### Client
Open another terminal:
```bash
cd task/client
java Main
```

---

## 📈 Version Control

This project uses the `main` branch as the **default and only working branch**. The legacy `master` branch has been removed to maintain consistency with modern Git conventions.

---

## 💡 Future Improvements

- Add persistent file-based storage
- Use JSON libraries like Gson or Jackson
- Create a simple web frontend for interaction
- Add unit tests and logging

---

## 🤝 Contributions

While this is a learning project, constructive feedback and contributions are welcome. Feel free to fork, improve, or use it as inspiration for your own work.

---

## 📄 License

This project is released under the MIT License.

---

## 🧑‍💻 Author

Giovanni Devito  
[LinkedIn](https://www.linkedin.com/in/giovanni-de-vito-39084a210/) • [Portfolio](https://workinprogress.com) • [Email](mailto:giodevito99@gmail.com)
