# Multiplayer Adventure Quest – Game Project Plan

## 1. Project Overview
A multiplayer, server-based adventure game where players connect to a central Java server, explore a world, interact with each other, fight monsters, and collect items. The game is designed to demonstrate advanced Java concepts and a wide range of design patterns.

---

## 2. Architecture

### Server
- Written in Java (Spring Boot, embedded Tomcat)
- Manages the game world, player states, and all game logic
- Handles multiple player connections using sockets and multithreading
- Exposes REST endpoints for non-realtime actions (login, registration, data fetch)
- Exposes WebSocket endpoints for real-time game events (movement, chat, combat)
- Uses design patterns for extensibility and maintainability

### Client
- Web frontend (React, Angular, or Vue recommended)
- Connects to the backend via REST (HTTP) and WebSocket
- Handles UI, user input, and displays game state

### Communication
- **REST API:** For login, registration, and non-realtime actions
- **WebSocket:** For real-time, bi-directional communication (gameplay, chat, events)
- Messages serialized as JSON or custom protocol

---

## 3. Backend-Frontend Communication: When and How

### 1. Communication Types

**A. REST API (HTTP)**
- **When to use:** For actions that are not real-time or do not require instant feedback.
- **Examples:** User registration, login, fetching player stats, loading inventory, saving settings.
- **How:** The frontend sends HTTP requests (GET, POST, etc.) to the backend's REST endpoints. The backend responds with JSON data.

**B. WebSocket**
- **When to use:** For real-time, bi-directional communication (instant updates, multiplayer events).
- **Examples:** Player movement, chat messages, combat actions, world updates, notifications.
- **How:** The frontend opens a persistent WebSocket connection to the backend. Both frontend and backend can send messages to each other at any time.

### 2. Example Communication Flows

**A. User Login (REST)**
1. User enters credentials in the UI.
2. Frontend sends a POST request to `/api/login` with username/password.
3. Backend authenticates and responds with a success/failure message (and maybe a session token).

**B. Joining the Game (WebSocket)**
1. After login, the frontend opens a WebSocket connection to `/ws/game`.
2. The frontend sends a "join game" message with the player's ID/token.
3. Backend adds the player to the game world and sends back the initial game state.

**C. Real-Time Actions (WebSocket)**
- **Player moves:** Frontend sends a "move" message (e.g., `{action: 'move', direction: 'north'}`).
- **Backend processes the move, updates the game state, and broadcasts the new state to all relevant players.**
- **Chat:** Frontend sends a "chat" message; backend broadcasts it to all players in the same area.

**D. Fetching Data (REST)**
- **Inventory:** Frontend requests `/api/inventory` to get the player's items.
- **Profile:** Frontend requests `/api/profile` for player stats.

### 3. Example: WebSocket Message Structure
```json
// Sent from frontend to backend
{
  "action": "move",
  "direction": "north"
}

// Sent from backend to frontend
{
  "event": "playerMoved",
  "playerId": "123",
  "newPosition": {"x": 10, "y": 5}
}
```

### 4. How to Implement in Spring Boot
- **REST:** Use `@RestController` and `@RequestMapping` for HTTP endpoints.
- **WebSocket:** Use Spring's `@Controller` with `@MessageMapping` (Spring WebSocket/STOMP).

### 5. How to Implement in Frontend
- **REST:** Use `fetch` or `axios` (JS) to call backend endpoints.
- **WebSocket:** Use the browser's `WebSocket` API or libraries like `@stomp/stompjs` for STOMP over WebSocket.

### 6. Summary Table

| Action                | Protocol   | When/Why                                 | Example Endpoint         |
|-----------------------|------------|------------------------------------------|-------------------------|
| Login/Register        | REST       | One-time, not real-time                  | POST `/api/login`       |
| Fetch Inventory/Stats | REST       | On demand, not real-time                 | GET `/api/inventory`    |
| Player Movement       | WebSocket  | Real-time, needs instant feedback        | `/ws/game`              |
| Chat                  | WebSocket  | Real-time, broadcast to many             | `/ws/game`              |
| Combat                | WebSocket  | Real-time, affects multiple players      | `/ws/game`              |

---

## 4. Design Patterns Used
| Pattern         | Where/How Used                                                      |
|----------------|---------------------------------------------------------------------|
| Singleton      | Game engine, configuration manager                                  |
| Factory        | Creating players, monsters, items                                   |
| Builder        | Building complex objects (e.g., Player, GameLevel)                  |
| Observer       | Event system (UI updates, quest progress, notifications)            |
| Strategy       | Different attack/movement/AI algorithms                             |
| Command        | Handling player actions (move, attack, use item), supports undo     |
| Template       | Game turn/battle skeleton, customizable steps for entities          |
| Decorator      | Adding abilities/power-ups to characters/items at runtime           |
| Adapter        | Integrating third-party libraries (e.g., sound, graphics)           |
| Proxy          | Lazy loading of assets, access control for premium features         |
| Composite      | Inventory system (bags of items)                                    |
| State/Chain    | NPC behavior, quest handling                                        |
| Prototype      | Cloning map sections or entities                                    |

---

## 5. Java Concepts Demonstrated
- **Multithreading:** Each player connection handled in a separate thread
- **Sockets:** Real-time server-client communication
- **Concurrency:** Thread-safe shared game state
- **Serialization:** Sending objects/messages over the network
- **Exception Handling:** Robust error management
- **OOP Principles:** Encapsulation, inheritance, polymorphism, abstraction

---

## 6. Example Game Flow
1. **Server starts:** Initializes game world (Singleton), listens for connections
2. **Player connects:** Server spawns a new thread for each player
3. **Player actions:** Sent as commands to the server (Command pattern)
4. **Game world updates:** Server notifies all players of changes (Observer pattern)
5. **Entities:** Created using Factory/Builder patterns
6. **Game logic:** Uses Strategy, Template, and other patterns for extensibility

---

## 7. Next Steps
1. Set up a Spring Boot backend with REST and WebSocket endpoints (embedded Tomcat)
2. Choose and set up a web frontend (React, Angular, or Vue)
3. Define the communication protocol (message format)
4. Sketch the main entities and class diagram
5. Incrementally add game features and design patterns

---

## 8. Open Questions / Discussion Points
- What features should be in the MVP (minimum viable product)?
- Should we use plain WebSocket or a framework (e.g., STOMP over WebSocket)?
- How should we handle persistent data (player progress, world state)?
- What game mechanics (combat, inventory, quests) should be prioritized?

---

## 9. High-Level Architecture Diagram

![High-Level Architecture](docs/architecture-diagram.svg)

*Note: This SVG is generated from the architecture diagram and should be updated as the system evolves.*

---

## 10. Flow Diagrams for Key Scenarios

![Flow Diagrams for Key Scenarios](docs/flow-diagram.svg)

*Note: This SVG is generated from the flow diagrams and should be updated as the system evolves.*

---

## 11. Detailed Feature List

### Core Features
- User registration and login (REST)
- Real-time multiplayer game world (WebSocket)
- Player movement and map exploration
- Chat system (global, area, private)
- Combat system (player vs. monster, player vs. player)
- Inventory management (items, equipment, power-ups)
- NPCs and quests
- Game world persistence (save/load state)
- Player stats and leveling
- Event/notification system

### Advanced Features
- Party/guild system
- Trading between players
- Leaderboards and achievements
- In-game economy (currency, shops)
- Admin tools (moderation, world editing)
- Customizable avatars/skins
- Sound and music integration
- Mobile-friendly UI

### Technical Features
- Scalable server architecture
- Thread-safe game state management
- Secure authentication and session management
- Error handling and logging
- Modular codebase for easy feature addition
- Automated testing and CI/CD support

--- 