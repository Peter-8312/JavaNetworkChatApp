# 💬 Java Network Chat App
This is a simple **client-server text-based chat application** built in Java using the Java Socket API. It allows multiple clients to connect to a server and exchange messages in real time over a network.

---

## 🚀 How to Run

#### 1. Clone this repository (or download the source code manually)

```bash
git clone https://github.com/Peter-8312/JavaNetworkChatApp.git
cd JavaNetworkChatApp
```

#### 2. Compile the Java files
```bash
javac ChatServer.java ChatClient.java ConnectionWrapper.java
```

#### 3. Run the Server
```bash
java ChatServer
```
By default, the server listens on port 12345.

#### 4. Run one or more Clients (in separate terminals)
```bash
java ChatClient
```
You’ll be prompted to:
- Enter a username
- Enter the server address (use localhost if testing locally)

You can run multiple clients at once to simulate real-time chat between users.

---

## 💻 Technologies Used
- Java – Core programming language
- Sockets (java.net.ServerSocket & Socket) – For network communication
- Multithreading – To handle multiple clients concurrently
- Object Streams – For serializing and sending/receiving messages
- Command Line Interface (CLI) – For simple user interaction

  ---

  ## 🧠 Learning Outcomes
- Implemented a client-server architecture in Java
- Used sockets and object streams for sending messages between clients and server
- Applied multithreading to support multiple clients simultaneously
- Designed and reused a ConnectionWrapper class to manage I/O logic
- Strengthened debugging and testing skills in concurrent and networked environments

  ---

## 📦 Project Structure
```bash
  JavaNetworkChatApp/
├── ChatServer.java         # Handles multiple client connections and message broadcasting
├── ChatClient.java         # Manages the user interface and client-side communication
├── ConnectionWrapper.java  # Wraps input/output streams for easier message passing
├── README.md               # Project documentation
```

---

## 📄 License
This project was completed as part of academic coursework and is provided for educational purposes only.

---

## 📫 Contact
For questions or feedback, feel free to reach out via GitHub.
