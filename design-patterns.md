# Design Patterns in Java

## Table of Contents
### Creational Patterns
1. Singleton Pattern
2. Factory Pattern
3. Builder Pattern

### Structural Patterns
4. Decorator Pattern
5. Adapter Pattern
6. Proxy Pattern

### Behavioral Patterns
7. Observer Pattern
8. Strategy Pattern
9. Command Pattern
10. Template Method Pattern

---

# Creational Patterns

## 1. Singleton Pattern

### 1. Theory (How and Why)
The Singleton pattern ensures that a class has only one instance and provides a global point of access to it. Useful when exactly one object is needed to coordinate actions across the system (e.g., configuration, logging).

### 2. Detailed Explanation by Example
```java
public class Singleton {
    private static Singleton instance;
    private Singleton() {}
    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

### 3. Uses Based on Scenarios
- Logger classes
- Configuration managers
- Connection pools

### 4. Pros and Cons
**Pros:**
- Controlled access to sole instance
- Saves memory (no duplicate objects)

**Cons:**
- Difficult to unit test (global state)
- Can be misused as a global variable
- Not suitable for multithreaded environments without synchronization

---

## 2. Factory Pattern

### 1. Theory (How and Why)
The Factory pattern provides an interface for creating objects in a superclass but allows subclasses to alter the type of objects that will be created. Useful when the exact type of object to create is determined at runtime.

### 2. Detailed Explanation by Example
```java
interface Animal {
    void speak();
}
class Dog implements Animal {
    public void speak() { System.out.println("Woof"); }
}
class Cat implements Animal {
    public void speak() { System.out.println("Meow"); }
}
class AnimalFactory {
    public static Animal getAnimal(String type) {
        if ("dog".equalsIgnoreCase(type)) return new Dog();
        else if ("cat".equalsIgnoreCase(type)) return new Cat();
        throw new IllegalArgumentException("Unknown type");
    }
}
// Usage:
// Animal a = AnimalFactory.getAnimal("dog");
// a.speak();
```

### 3. Uses Based on Scenarios
- UI libraries (ButtonFactory)
- Database drivers
- Document converters

### 4. Pros and Cons
**Pros:**
- Decouples object creation from usage
- Easy to introduce new types

**Cons:**
- Can lead to many subclasses
- Complexity increases with number of types

---

## 3. Builder Pattern

### 1. Theory (How and Why)
The Builder pattern separates the construction of a complex object from its representation, allowing the same construction process to create different representations. Useful for objects with many optional parameters.

### 2. Detailed Explanation by Example
```java
class User {
    private String name;
    private int age;
    private String email;
    private User(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.email = builder.email;
    }
    public static class Builder {
        private String name;
        private int age;
        private String email;
        public Builder setName(String name) { this.name = name; return this; }
        public Builder setAge(int age) { this.age = age; return this; }
        public Builder setEmail(String email) { this.email = email; return this; }
        public User build() { return new User(this); }
    }
}
// Usage:
// User user = new User.Builder().setName("Aman").setAge(25).build();
```

### 3. Uses Based on Scenarios
- Building complex objects (e.g., HTTP requests, UI components)
- When constructors have many parameters

### 4. Pros and Cons
**Pros:**
- Readable and maintainable code
- Avoids telescoping constructors

**Cons:**
- More code to write
- Slightly increased complexity

---

# Structural Patterns

## 4. Decorator Pattern

### 1. Theory (How and Why)
The Decorator pattern allows behavior to be added to individual objects, dynamically, without affecting the behavior of other objects from the same class. Useful for adhering to the Open/Closed Principle.

### 2. Detailed Explanation by Example
```java
interface Coffee {
    String getDescription();
    double cost();
}
class SimpleCoffee implements Coffee {
    public String getDescription() { return "Simple Coffee"; }
    public double cost() { return 5; }
}
class MilkDecorator implements Coffee {
    private Coffee coffee;
    public MilkDecorator(Coffee coffee) { this.coffee = coffee; }
    public String getDescription() { return coffee.getDescription() + ", Milk"; }
    public double cost() { return coffee.cost() + 1; }
}
// Usage:
// Coffee coffee = new MilkDecorator(new SimpleCoffee());
// System.out.println(coffee.getDescription()); // Simple Coffee, Milk
```

### 3. Uses Based on Scenarios
- Adding features to objects (e.g., UI components, streams)
- Logging, security, or validation wrappers

### 4. Pros and Cons
**Pros:**
- Flexible alternative to subclassing
- Can add responsibilities at runtime

**Cons:**
- Many small classes
- Can be complex to debug

---

## 5. Adapter Pattern

### 1. Theory (How and Why)
The Adapter pattern allows incompatible interfaces to work together. It acts as a bridge between two incompatible interfaces.

### 2. Detailed Explanation by Example
```java
interface Target {
    void request();
}
class Adaptee {
    public void specificRequest() { System.out.println("Specific request"); }
}
class Adapter implements Target {
    private Adaptee adaptee;
    public Adapter(Adaptee adaptee) { this.adaptee = adaptee; }
    public void request() { adaptee.specificRequest(); }
}
// Usage:
// Target t = new Adapter(new Adaptee());
// t.request();
```

### 3. Uses Based on Scenarios
- Integrating legacy code
- Working with third-party libraries

### 4. Pros and Cons
**Pros:**
- Increases code reusability
- Promotes loose coupling

**Cons:**
- Adds extra layer of abstraction
- Can make code harder to follow

---

## 6. Proxy Pattern

### 1. Theory (How and Why)
The Proxy pattern provides a surrogate or placeholder for another object to control access to it. Useful for lazy loading, access control, logging, etc.

### 2. Detailed Explanation by Example
```java
interface Service {
    void operation();
}
class RealService implements Service {
    public void operation() { System.out.println("Real operation"); }
}
class ProxyService implements Service {
    private RealService realService;
    public void operation() {
        if (realService == null) realService = new RealService();
        System.out.println("Proxy: Before operation");
        realService.operation();
        System.out.println("Proxy: After operation");
    }
}
// Usage:
// Service s = new ProxyService();
// s.operation();
```

### 3. Uses Based on Scenarios
- Security proxies
- Remote proxies (RMI)
- Virtual proxies (lazy loading)

### 4. Pros and Cons
**Pros:**
- Adds control without changing real object
- Useful for cross-cutting concerns

**Cons:**
- Adds complexity
- Can introduce performance overhead

---

# Behavioral Patterns

## 7. Observer Pattern

### 1. Theory (How and Why)
The Observer pattern defines a one-to-many dependency between objects so that when one object changes state, all its dependents are notified. Useful for event handling systems.

### 2. Detailed Explanation by Example
```java
import java.util.*;
interface Observer { void update(String msg); }
class Subject {
    private List<Observer> observers = new ArrayList<>();
    public void addObserver(Observer o) { observers.add(o); }
    public void notifyObservers(String msg) {
        for (Observer o : observers) o.update(msg);
    }
}
class ConcreteObserver implements Observer {
    public void update(String msg) { System.out.println("Received: " + msg); }
}
// Usage:
// Subject s = new Subject();
// s.addObserver(new ConcreteObserver());
// s.notifyObservers("Hello");
```

### 3. Uses Based on Scenarios
- GUI event listeners
- Messaging systems
- Data binding

### 4. Pros and Cons
**Pros:**
- Promotes loose coupling
- Easy to add/remove observers

**Cons:**
- Can lead to memory leaks if observers are not removed
- Notification order is not guaranteed

---

## 8. Strategy Pattern

### 1. Theory (How and Why)
The Strategy pattern defines a family of algorithms, encapsulates each one, and makes them interchangeable. Useful for selecting an algorithm at runtime.

### 2. Detailed Explanation by Example
```java
interface PaymentStrategy {
    void pay(int amount);
}
class CreditCardPayment implements PaymentStrategy {
    public void pay(int amount) { System.out.println("Paid by credit card: " + amount); }
}
class PayPalPayment implements PaymentStrategy {
    public void pay(int amount) { System.out.println("Paid by PayPal: " + amount); }
}
class ShoppingCart {
    private PaymentStrategy strategy;
    public void setPaymentStrategy(PaymentStrategy s) { this.strategy = s; }
    public void checkout(int amount) { strategy.pay(amount); }
}
// Usage:
// ShoppingCart cart = new ShoppingCart();
// cart.setPaymentStrategy(new CreditCardPayment());
// cart.checkout(100);
```

### 3. Uses Based on Scenarios
- Payment methods
- Sorting algorithms
- Compression strategies

### 4. Pros and Cons
**Pros:**
- Easy to switch algorithms
- Promotes open/closed principle

**Cons:**
- Increases number of classes
- Client must be aware of strategies

---

## 9. Command Pattern

### 1. Theory (How and Why)
The Command pattern encapsulates a request as an object, allowing you to parameterize clients with different requests, queue or log requests, and support undoable operations.

### 2. Detailed Explanation by Example
```java
interface Command {
    void execute();
}
class Light {
    public void on() { System.out.println("Light ON"); }
    public void off() { System.out.println("Light OFF"); }
}
class LightOnCommand implements Command {
    private Light light;
    public LightOnCommand(Light light) { this.light = light; }
    public void execute() { light.on(); }
}
class RemoteControl {
    private Command command;
    public void setCommand(Command c) { this.command = c; }
    public void pressButton() { command.execute(); }
}
// Usage:
// Light light = new Light();
// Command on = new LightOnCommand(light);
// RemoteControl remote = new RemoteControl();
// remote.setCommand(on);
// remote.pressButton();
```

### 3. Uses Based on Scenarios
- GUI buttons and menu actions
- Task scheduling
- Undo/redo functionality

### 4. Pros and Cons
**Pros:**
- Decouples sender and receiver
- Supports undo/redo

**Cons:**
- Increases number of classes
- Can be overkill for simple tasks

---

## 10. Template Method Pattern

### 1. Theory (How and Why)
The Template Method pattern defines the skeleton of an algorithm in a method, deferring some steps to subclasses. Useful for code reuse and enforcing a sequence of steps.

### 2. Detailed Explanation by Example
```java
abstract class DataProcessor {
    public final void process() {
        readData();
        processData();
        saveData();
    }
    abstract void readData();
    abstract void processData();
    void saveData() { System.out.println("Data saved"); }
}
class CSVDataProcessor extends DataProcessor {
    void readData() { System.out.println("Read CSV"); }
    void processData() { System.out.println("Process CSV"); }
}
// Usage:
// DataProcessor p = new CSVDataProcessor();
// p.process();
```

### 3. Uses Based on Scenarios
- Data processing pipelines
- Frameworks with customizable steps

### 4. Pros and Cons
**Pros:**
- Promotes code reuse
- Enforces algorithm structure

**Cons:**
- Inflexible if algorithm steps need to change
- Can lead to subclass explosion

--- 