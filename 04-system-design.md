# System Design

## 1. High-level & Low-level Design

## 2. Scalability, Availability, Consistency

## 3. Database Design (SQL & NoSQL)

## 4. Caching, Load Balancing, Message Queues

## 4. System Design

### 4.1 Design Principles

#### 4.1.1 SOLID Principles

**Single Responsibility Principle (SRP):**
```java
// Bad: Multiple responsibilities
class UserManager {
    public void saveUser(User user) { }
    public void sendEmail(User user) { }
    public void generateReport() { }
}

// Good: Single responsibility
class UserRepository {
    public void save(User user) { }
}

class EmailService {
    public void sendEmail(User user) { }
}

class ReportGenerator {
    public void generateReport() { }
}
```

**Open/Closed Principle (OCP):**
```java
// Open for extension, closed for modification
interface PaymentProcessor {
    void processPayment(double amount);
}

class CreditCardProcessor implements PaymentProcessor {
    @Override
    public void processPayment(double amount) { }
}

class PayPalProcessor implements PaymentProcessor {
    @Override
    public void processPayment(double amount) { }
}
```

**Liskov Substitution Principle (LSP):**
```java
// Subtypes must be substitutable for their base types
class Rectangle {
    protected int width, height;
    
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public int getArea() { return width * height; }
}

class Square extends Rectangle {
    @Override
    public void setWidth(int width) {
        this.width = width;
        this.height = width; // Violates LSP
    }
}
```

**Interface Segregation Principle (ISP):**
```java
// Bad: Fat interface
interface Worker {
    void work();
    void eat();
    void sleep();
}

// Good: Segregated interfaces
interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

interface Sleepable {
    void sleep();
}
```

**Dependency Inversion Principle (DIP):**
```java
// Depend on abstractions, not concretions
interface MessageService {
    void sendMessage(String message);
}

class EmailService implements MessageService {
    @Override
    public void sendMessage(String message) { }
}

class NotificationService {
    private final MessageService messageService;
    
    public NotificationService(MessageService messageService) {
        this.messageService = messageService;
    }
}
```

#### 4.1.2 DRY (Don't Repeat Yourself)

```java
// Bad: Repeated code
class UserService {
    public void validateUser(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new ValidationException("Email is required");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new ValidationException("Name is required");
        }
    }
}

class ProductService {
    public void validateProduct(Product product) {
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new ValidationException("Name is required");
        }
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new ValidationException("Price must be positive");
        }
    }
}

// Good: Reusable validation
class ValidationUtils {
    public static void validateNotNull(Object value, String fieldName) {
        if (value == null) {
            throw new ValidationException(fieldName + " is required");
        }
    }
    
    public static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " is required");
        }
    }
}
```

#### 4.1.3 KISS (Keep It Simple, Stupid)

```java
// Bad: Over-engineered
class ComplexUserValidator {
    private final List<ValidationRule> rules;
    private final ValidationContext context;
    
    public ValidationResult validate(User user) {
        // Complex validation logic
    }
}

// Good: Simple and clear
class UserValidator {
    public void validate(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new ValidationException("Email is required");
        }
        if (!user.getEmail().contains("@")) {
            throw new ValidationException("Invalid email format");
        }
    }
}
```

### 4.2 Design Patterns

#### 4.2.1 Creational Patterns

**Singleton Pattern:**
```java
// Thread-safe singleton
public class DatabaseConnection {
    private static volatile DatabaseConnection instance;
    private static final Object lock = new Object();
    
    private DatabaseConnection() { }
    
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }
}

// Enum singleton (recommended)
public enum DatabaseConnection {
    INSTANCE;
    
    public void connect() { }
}
```

**Factory Pattern:**
```java
interface PaymentProcessor {
    void processPayment(double amount);
}

class CreditCardProcessor implements PaymentProcessor {
    @Override
    public void processPayment(double amount) { }
}

class PayPalProcessor implements PaymentProcessor {
    @Override
    public void processPayment(double amount) { }
}

class PaymentProcessorFactory {
    public static PaymentProcessor createProcessor(String type) {
        switch (type.toLowerCase()) {
            case "creditcard":
                return new CreditCardProcessor();
            case "paypal":
                return new PayPalProcessor();
            default:
                throw new IllegalArgumentException("Unknown payment type");
        }
    }
}
```

**Builder Pattern:**
```java
public class User {
    private final String email;
    private final String name;
    private final int age;
    private final String phone;
    
    private User(Builder builder) {
        this.email = builder.email;
        this.name = builder.name;
        this.age = builder.age;
        this.phone = builder.phone;
    }
    
    public static class Builder {
        private String email;
        private String name;
        private int age;
        private String phone;
        
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        
        public Builder age(int age) {
            this.age = age;
            return this;
        }
        
        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }
        
        public User build() {
            return new User(this);
        }
    }
}

// Usage
User user = new User.Builder()
    .email("test@example.com")
    .name("John Doe")
    .age(30)
    .phone("1234567890")
    .build();
```

// ... (Add more patterns as needed)

### 4.3 Interview Questions

#### 4.3.1 Design Principles

**Q: What is the difference between cohesion and coupling?**
A:
- Cohesion: Degree to which elements of a module belong together
- Coupling: Degree of interdependence between modules
- High cohesion and low coupling are desirable

**Q: What is scalability?**
A:
- Ability of a system to handle increased load by adding resources
- Vertical scaling: Add more power to existing machines
- Horizontal scaling: Add more machines to the pool

#### 4.3.2 Design Patterns

**Q: When would you use the Singleton pattern?**
A:
- When only one instance of a class should exist
- For shared resources like configuration, logging, or connection pools

**Q: What is the difference between Factory and Builder patterns?**
A:
- Factory: Creates objects based on input or context, hides instantiation logic
- Builder: Step-by-step construction of complex objects, separates construction from representation

Add notes, diagrams, and resources for each topic above. 