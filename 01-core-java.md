# Core Java Concepts

## 1. OOP Principles

Object-Oriented Programming (OOP) is a way of designing and writing programs using objects and classes. The four main principles are:

### 1. Inheritance

Inheritance allows a class (child) to inherit properties and methods from another class (parent).

**Example:**
```java
class Animal {
    void eat() {
        System.out.println("This animal eats food.");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("The dog barks.");
    }
}

public class Main {
    public static void main(String[] args) {
        Dog d = new Dog();
        d.eat();  // Inherited method
        d.bark(); // Own method
    }
}
```
**Live Example Output:**
```
This animal eats food.
The dog barks.
```

### 2. Polymorphism

Polymorphism means "many forms". It allows one interface to be used for a general class of actions.

**Example:**
```java
class Animal {
    void sound() {
        System.out.println("Animal makes a sound");
    }
}

class Cat extends Animal {
    void sound() {
        System.out.println("Cat meows");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal a = new Cat();
        a.sound(); // Calls Cat's sound()
    }
}
```
**Live Example Output:**
```
Cat meows
```

### 3. Encapsulation

Encapsulation is the process of wrapping data (variables) and code (methods) together as a single unit. It restricts direct access to some of the object's components.

**Example:**
```java
class Person {
    private String name; // private = restricted access

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }
}

public class Main {
    public static void main(String[] args) {
        Person p = new Person();
        p.setName("Alice");
        System.out.println(p.getName());
    }
}
```
**Live Example Output:**
```
Alice
```

### 4. Abstraction

Abstraction means hiding complex implementation details and showing only the necessary features.

**Example:**
```java
abstract class Vehicle {
    abstract void start();
}

class Car extends Vehicle {
    void start() {
        System.out.println("Car starts with key");
    }
}

public class Main {
    public static void main(String[] args) {
        Vehicle v = new Car();
        v.start();
    }
}
```
**Live Example Output:**
```
Car starts with key
```

### Tricky Interview Questions on OOP Principles

**Q1: Can you override a private or static method in Java?**

**Answer:**
No, you cannot override private or static methods in Java. Private methods are not visible to subclasses, and static methods belong to the class, not to instances. If you declare a static method with the same signature in a subclass, it hides the superclass method (method hiding), not overrides it.

**Guess Output:**
A common guess is that the subclass's static or private method will override the parent's method, but in reality, static methods are hidden, not overridden, and private methods are not accessible in the subclass at all.

---

**Q2: What is the difference between method overloading and method overriding?**

**Answer:**
- **Overloading**: Same method name, different parameters, within the same class.
- **Overriding**: Same method name and parameters, but in a subclass (child class) to provide a specific implementation.

**Guess Output:**
Some candidates think overloading and overriding are the same, but overloading is resolved at compile time, while overriding is resolved at runtime.

---

**Q3: Can you instantiate an abstract class? If not, why?**

**Answer:**
No, you cannot instantiate an abstract class directly because it may have abstract methods (methods without a body) that must be implemented by subclasses.

**Guess Output:**
A common guess is that you can create an object of an abstract class, but doing so will result in a compile-time error.

---

**Q4: What is the real use of encapsulation in Java?**

**Answer:**
Encapsulation helps protect the internal state of an object from unintended or harmful changes. It allows you to control how important variables are accessed and modified using getters and setters.

**Guess Output:**
Some may guess encapsulation is only about using private variables, but it also involves providing controlled access through public methods.

---

**Q5: How does Java achieve runtime polymorphism?**

**Answer:**
Java achieves runtime polymorphism through method overriding. When a superclass reference variable refers to a subclass object, the overridden method in the subclass is called at runtime.

**Guess Output:**
A common guess is that the method of the reference type (superclass) will be called, but actually, the method of the actual object (subclass) is executed at runtime.

## 2. Collections Framework

### What is the Collections Framework?

The Java Collections Framework is a set of classes and interfaces that help you store and manage groups of objects efficiently. It provides ready-to-use data structures like lists, sets, maps, and queues.

---

### 1. List

A `List` is an ordered collection that allows duplicate elements. The most common implementation is `ArrayList`.

**Example:**
```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String> fruits = new ArrayList<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Apple"); // duplicate allowed
        System.out.println(fruits);
    }
}
```
**Live Example Output:**
```
[Apple, Banana, Apple]
```

**Common List Operations and Time Complexity (ArrayList):**

| Operation         | Time Complexity |
|------------------|----------------|
| Access (get)     | O(1)           |
| Insert at end    | O(1) (amortized)|
| Insert/remove at index | O(n)      |
| Search (contains)| O(n)           |
| Remove by value  | O(n)           |

---

### 2. Set

A `Set` is a collection that does not allow duplicate elements. The most common implementation is `HashSet`.

**Example:**
```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Set<String> fruits = new HashSet<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Apple"); // duplicate ignored
        System.out.println(fruits);
    }
}
```
**Live Example Output:**
```
[Apple, Banana]
```
(Note: The order may vary because `HashSet` does not maintain order.)

**Common Set Operations and Time Complexity (HashSet):**

| Operation         | Time Complexity |
|------------------|----------------|
| Add              | O(1)            |
| Remove           | O(1)            |
| Contains         | O(1)            |
| Size             | O(1)            |

---

### 3. Map

A `Map` stores key-value pairs. Each key is unique. The most common implementation is `HashMap`.

**Example:**
```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String, Integer> ages = new HashMap<>();
        ages.put("Alice", 25);
        ages.put("Bob", 30);
        ages.put("Alice", 28); // value for "Alice" is updated
        System.out.println(ages);
    }
}
```
**Live Example Output:**
```
{Alice=28, Bob=30}
```
(Note: The order may vary.)

**Common Map Operations and Time Complexity (HashMap):**

| Operation         | Time Complexity |
|------------------|----------------|
| Put (insert)     | O(1)            |
| Get (by key)     | O(1)            |
| Remove (by key)  | O(1)            |
| Contains Key     | O(1)            |
| Iterate          | O(n)            |

---

### 4. Queue

A `Queue` is used to hold elements before processing, typically in FIFO (First-In-First-Out) order. The most common implementation is `LinkedList`.

**Example:**
```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<>();
        queue.add("First");
        queue.add("Second");
        queue.add("Third");
        System.out.println(queue.poll()); // removes and returns the head
        System.out.println(queue);
    }
}
```
**Live Example Output:**
```
First
[Second, Third]
```

**Common Queue Operations and Time Complexity (LinkedList):**

| Operation         | Time Complexity |
|------------------|----------------|
| Add (offer/add)  | O(1)            |
| Remove (poll)    | O(1)            |
| Peek             | O(1)            |
| Contains         | O(n)            |

---

### Tricky Interview Questions on Collections Framework

**Q1: What happens if you add a null element to a HashSet or HashMap?**

**Answer:**
- `HashSet` allows one null element.
- `HashMap` allows one null key and multiple null values.

**Guess Output:**
Some candidates think adding null will throw an exception, but it is allowed in these collections.

---

**Q2: What is the difference between ArrayList and LinkedList in terms of performance?**

**Answer:**
- `ArrayList` provides fast random access (O(1)), but slow insertions/removals except at the end (O(n)).
- `LinkedList` provides fast insertions/removals at the beginning or middle (O(1) if you have the node), but slow random access (O(n)).

**Guess Output:**
A common guess is that both have similar performance, but their internal structure makes them suitable for different use cases.

---

**Q3: Can a HashMap have duplicate keys? What happens if you put the same key again?**

**Answer:**
No, a `HashMap` cannot have duplicate keys. If you put the same key again, the old value is replaced by the new value.

**Guess Output:**
Some may guess both values are stored, but only the latest value is kept for a key.

---

**Q4: What is the output order of elements in a HashSet or HashMap?**

**Answer:**
The order is not guaranteed. If you need order, use `LinkedHashSet` or `LinkedHashMap`.

**Guess Output:**
A common guess is that elements will be in the order they were added, but this is not true for `HashSet` and `HashMap`.

---

**Q5: How do you make a collection thread-safe?**

**Answer:**
You can use `Collections.synchronizedList(new ArrayList<>())`, or use concurrent collections like `ConcurrentHashMap`.

**Guess Output:**
Some may guess that all collections are thread-safe by default, but most are not.

## 3. Generics, Streams, and Lambda Expressions

### 1. Generics

Generics allow you to write classes and methods that work with any data type, providing type safety at compile time.

**Example:**
```java
List<String> names = new ArrayList<>();
names.add("Aman");
// names.add(10); // Compile-time error
```

**Live Example Output:**
```
[Aman]
```

**Do's:**
- Use generics to avoid casting and runtime errors.
- Use bounded types (e.g., <T extends Number>) for constraints.

**Don'ts:**
- Don't use raw types (e.g., List list) in new code.
- Don't mix different types in a generic collection.

---

### 2. Streams

Streams process sequences of elements (from collections, arrays, etc.) in a functional style (filter, map, reduce, etc.).

**Basic Example:**
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
int sum = numbers.stream().filter(n -> n % 2 == 0).mapToInt(Integer::intValue).sum();
System.out.println(sum);
```
**Live Example Output:**
```
6
```

#### Common Stream Functionalities

**1. filter**
Filters elements based on a condition.
```java
List<String> names = Arrays.asList("Aman", "Rahul", "Priya");
names.stream().filter(name -> name.startsWith("A")).forEach(System.out::println);
```
**Output:**
```
Aman
```

**2. map**
Transforms each element.
```java
List<String> names = Arrays.asList("Aman", "Rahul");
names.stream().map(String::toUpperCase).forEach(System.out::println);
```
**Output:**
```
AMAN
RAHUL
```

**3. reduce**
Combines elements to produce a single result.
```java
List<Integer> nums = Arrays.asList(1, 2, 3);
int product = nums.stream().reduce(1, (a, b) -> a * b);
System.out.println(product);
```
**Output:**
```
6
```

**4. collect**
Collects the result into a collection or other structure.
```java
List<String> names = Arrays.asList("Aman", "Rahul");
List<String> upper = names.stream().map(String::toUpperCase).collect(Collectors.toList());
System.out.println(upper);
```
**Output:**
```
[AMAN, RAHUL]
```

**5. sorted**
Sorts the elements.
```java
List<Integer> nums = Arrays.asList(3, 1, 2);
nums.stream().sorted().forEach(System.out::println);
```
**Output:**
```
1
2
3
```

**6. distinct**
Removes duplicate elements.
```java
List<Integer> nums = Arrays.asList(1, 2, 2, 3);
nums.stream().distinct().forEach(System.out::println);
```
**Output:**
```
1
2
3
```

**7. limit**
Limits the number of elements.
```java
List<Integer> nums = Arrays.asList(1, 2, 3, 4);
nums.stream().limit(2).forEach(System.out::println);
```
**Output:**
```
1
2
```

**8. skip**
Skips the first n elements.
```java
List<Integer> nums = Arrays.asList(1, 2, 3, 4);
nums.stream().skip(2).forEach(System.out::println);
```
**Output:**
```
3
4
```

**9. flatMap**
Flattens nested structures.
```java
List<List<String>> list = Arrays.asList(
    Arrays.asList("A", "B"),
    Arrays.asList("C", "D")
);
list.stream().flatMap(Collection::stream).forEach(System.out::println);
```
**Output:**
```
A
B
C
D
```

---

### 3. Lambda Expressions

Lambda expressions provide a concise way to implement functional interfaces (interfaces with a single abstract method).

**What is a Lambda Expression?**
A lambda expression is a short block of code which takes in parameters and returns a value. Lambda expressions are similar to methods, but they do not need a name and can be implemented right in the body of a method.

**Syntax:**
```
(parameters) -> expression
(parameters) -> { statements; }
```

**Examples:**

1. **No parameters:**
```java
Runnable r = () -> System.out.println("Hello from Lambda!");
r.run();
```

2. **With one parameter:**
```java
Consumer<String> printer = name -> System.out.println("Hello, " + name);
printer.accept("Aman");
```

3. **With multiple parameters and return value:**
```java
BinaryOperator<Integer> add = (a, b) -> a + b;
System.out.println(add.apply(2, 3));
```

4. **With block body:**
```java
Comparator<String> comp = (s1, s2) -> {
    int len1 = s1.length();
    int len2 = s2.length();
    return Integer.compare(len1, len2);
};
System.out.println(comp.compare("hi", "hello"));
```

5. **Method reference (shorthand for a lambda that calls an existing method):**
```java
List<String> names = Arrays.asList("Aman", "Rahul");
names.forEach(System.out::println);
```

**Types of Lambda Expressions:**
- No parameter: `() -> System.out.println("Hi")`
- Single parameter: `x -> x * x`
- Multiple parameters: `(x, y) -> x + y`
- With block: `(x, y) -> { int sum = x + y; return sum; }`

**When to Use Lambda Expressions:**
- When you need to provide a short implementation for a functional interface (Runnable, Comparator, etc.)
- For event handling, collection processing, and stream operations

**Do's:**
- Use lambdas for short, simple implementations.
- Use method references where possible (e.g., System.out::println).

**Don'ts:**
- Don't use lambdas for complex logic.
- Don't overuse lambdas where a named class is clearer.

---

### Returning a Function (Method) from a Method

In Java, you can return a function (method) using functional interfaces and lambda expressions. This is called a higher-order function.

**Example:**
```java
import java.util.function.Function;

public class Main {
    // Method that returns a function (lambda)
    public static Function<Integer, Integer> multiplier(int factor) {
        return x -> x * factor;
    }

    public static void main(String[] args) {
        Function<Integer, Integer> timesTwo = multiplier(2);
        System.out.println(timesTwo.apply(5)); // Output: 10
    }
}
```
**Output:**
```
10
```

This allows you to create flexible and reusable code, similar to how you would use function pointers or delegates in other languages.

**More Examples: Returning Functions in Java**

1. **Returning a Predicate (for filtering):**
```java
import java.util.function.Predicate;

public class Main {
    // Returns a predicate that checks if a number is greater than a threshold
    public static Predicate<Integer> isGreaterThan(int threshold) {
        return x -> x > threshold;
    }

    public static void main(String[] args) {
        Predicate<Integer> greaterThan10 = isGreaterThan(10);
        System.out.println(greaterThan10.test(15)); // true
        System.out.println(greaterThan10.test(5));  // false
    }
}
```
**Output:**
```
true
false
```

2. **Returning a Custom Functional Interface:**
```java
@FunctionalInterface
interface StringTransformer {
    String transform(String input);
}

public class Main {
    public static StringTransformer getUpperCaseTransformer() {
        return s -> s.toUpperCase();
    }

    public static void main(String[] args) {
        StringTransformer transformer = getUpperCaseTransformer();
        System.out.println(transformer.transform("hello")); // HELLO
    }
}
```
**Output:**
```
HELLO
```

3. **Chaining Returned Functions:**
```java
import java.util.function.Function;

public class Main {
    public static Function<Integer, Integer> add(int y) {
        return x -> x + y;
    }

    public static void main(String[] args) {
        Function<Integer, Integer> add5 = add(5);
        Function<Integer, Integer> add10 = add(10);
        // Chaining: add 5, then add 10
        Function<Integer, Integer> add15 = add5.andThen(add10);
        System.out.println(add15.apply(2)); // (2+5)+10 = 17
    }
}
```
**Output:**
```
17
```

These examples show how you can:
- Return different types of functions (Predicate, custom interfaces, Function)
- Use returned functions for filtering, transforming, and chaining operations
- Make your code more flexible and reusable

---

### Tricky Interview Questions on Generics, Streams, and Lambda Expressions

**Q1: What happens if you use a raw type with a generic collection?**

**Answer:**
You lose type safety, and the compiler will not catch type errors, which may lead to runtime exceptions.

**Guess Output:**
Some may think it works fine, but it can cause ClassCastException at runtime.

---

**Q2: Can you use primitive types with generics?**

**Answer:**
No, generics only work with reference types (e.g., Integer, not int).

**Guess Output:**
Some may try List<int>, but it will not compile.

---

**Q3: What is the difference between intermediate and terminal operations in streams?**

**Answer:**
- Intermediate operations (e.g., filter, map) return a stream and are lazy.
- Terminal operations (e.g., collect, forEach, sum) produce a result or side-effect and end the stream pipeline.

**Guess Output:**
Some may think all stream operations are executed immediately, but only terminal operations trigger processing.

---

**Q4: Can a lambda expression access non-final local variables from the enclosing scope?**

**Answer:**
No, only effectively final variables can be accessed inside a lambda.

**Guess Output:**
Some may think any local variable can be used, but it must not be modified after assignment.

---

**Q5: What is the output of the following code?
```java
List<String> list = Arrays.asList("a", "b", "c");
list.stream().map(String::toUpperCase);
System.out.println(list);
```
**Answer:**
The original list is unchanged. Streams do not modify the source; they produce new results.

**Guess Output:**
Some may expect the list to be uppercased, but it remains [a, b, c].

## 4. Exception Handling

### Theory
Exception handling in Java is a mechanism to handle runtime errors, so the normal flow of the application can be maintained. Java uses try-catch blocks to catch exceptions and handle them gracefully, instead of crashing the program.

- **Exception:** An event that disrupts the normal flow of the program (e.g., dividing by zero, accessing an invalid index).
- **Checked Exception:** Checked at compile time (e.g., IOException).
- **Unchecked Exception:** Occurs at runtime (e.g., NullPointerException, ArithmeticException).

### Example: Exception Handling in Java
```java
public class ExceptionDemo {
    public static void main(String[] args) {
        try {
            int result = 10 / 0; // This will throw ArithmeticException
            System.out.println(result);
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("This block always executes.");
        }
    }
}
```
**Output:**
```
Error: / by zero
This block always executes.
```

### Do's
- Use specific exception types in catch blocks (e.g., catch IOException, not just Exception).
- Use finally blocks for cleanup code (closing files, releasing resources).
- Document exceptions thrown by your methods (using throws keyword and Javadoc).
- Handle exceptions at the right level (not too early, not too late).

### Don'ts
- Don't use empty catch blocks (they hide errors).
- Don't catch Exception or Throwable unless absolutely necessary.
- Don't use exceptions for normal control flow.
- Don't ignore checked exceptions.

---

### Interview & Tricky Questions on Exception Handling

**Q1: What is the difference between checked and unchecked exceptions?**

**Answer:**
- Checked exceptions are checked at compile time; you must handle or declare them.
- Unchecked exceptions occur at runtime; handling is optional.

**Guess Output:**
Some may think all exceptions must be handled, but only checked exceptions are enforced by the compiler.

---

**Q2: What happens if an exception is thrown in the finally block?**

**Answer:**
If an exception is thrown in the finally block, it can override any exception thrown in the try or catch block, potentially hiding the original exception.

**Guess Output:**
Some may think finally always runs safely, but exceptions in finally can cause unexpected behavior.

---

**Q3: Can you catch multiple exceptions in a single catch block?**

**Answer:**
Yes, using multi-catch (e.g., catch (IOException | SQLException e)).

**Guess Output:**
Some may think you need separate catch blocks for each exception, but multi-catch is allowed.

---

**Q4: What is the output of the following code?
```java
try {
    return;
} finally {
    System.out.println("Finally block");
}
```
**Answer:**
"Finally block" will be printed even if return is called in try.

**Guess Output:**
Some may think finally is skipped after return, but it always executes.

---

**Q5: Is it good practice to catch Exception or Throwable? Why or why not?**

**Answer:**
No, because it can hide programming errors and make debugging difficult. Only catch specific exceptions you can handle.

**Guess Output:**
Some may think catching Exception is safe, but it can lead to poor error handling and hidden bugs.

## 5. Multithreading and Concurrency

### Theory
Multithreading allows multiple parts of a program to run at the same time, making better use of CPU resources. Concurrency is the ability to run several programs or parts of a program in parallel. In Java, you can create threads by extending the Thread class or implementing the Runnable interface.

- **Thread:** A lightweight process; smallest unit of execution.
- **Runnable:** Functional interface for thread tasks.
- **Synchronization:** Mechanism to control access to shared resources.
- **Deadlock:** Situation where two or more threads are blocked forever, waiting for each other.

### Example: Creating and Running Threads
```java
// Using Runnable
class MyTask implements Runnable {
    public void run() {
        System.out.println("Task running in: " + Thread.currentThread().getName());
    }
}

public class Main {
    public static void main(String[] args) {
        Thread t1 = new Thread(new MyTask());
        t1.start();
        System.out.println("Main thread: " + Thread.currentThread().getName());
    }
}
```
**Output:**
```
Main thread: main
Task running in: Thread-0
```

### Example: Synchronization
```java
class Counter {
    private int count = 0;
    public synchronized void increment() {
        count++;
    }
    public int getCount() {
        return count;
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        Thread t1 = new Thread(() -> { for (int i = 0; i < 1000; i++) counter.increment(); });
        Thread t2 = new Thread(() -> { for (int i = 0; i < 1000; i++) counter.increment(); });
        t1.start(); t2.start();
        t1.join(); t2.join();
        System.out.println("Final count: " + counter.getCount());
    }
}
```
**Output:**
```
Final count: 2000
```

### Do's
- Use Runnable or Callable for thread tasks.
- Use synchronization to protect shared resources.
- Use higher-level concurrency utilities (Executors, Locks, etc.) when possible.
- Always handle InterruptedException.

### Don'ts
- Don't use Thread.sleep() for synchronization.
- Don't share mutable data between threads without synchronization.
- Don't create too many threads; use thread pools.
- Don't ignore exceptions in threads.

---

### Interview & Tricky Coding Questions on Multithreading and Concurrency

**Q1: What is the difference between process and thread?**

**Answer:**
- A process is an independent program with its own memory space.
- A thread is a lightweight process that shares memory with other threads in the same process.

**Guess Output:**
Some may think threads are completely independent, but they share memory within a process.

---

**Q2: What is a race condition? How can you prevent it?**

**Answer:**
A race condition occurs when two or more threads access shared data and try to change it at the same time. It can be prevented using synchronization.

**Guess Output:**
Some may think race conditions are rare, but they are common in unsynchronized code.

---

**Q3: What is deadlock? Give an example scenario.**

**Answer:**
Deadlock is when two or more threads are blocked forever, each waiting for the other to release a lock. Example: Thread A holds Lock 1 and waits for Lock 2, while Thread B holds Lock 2 and waits for Lock 1.

**Guess Output:**
Some may think deadlocks are easy to spot, but they can be subtle and hard to debug.

---

**Q4: Write a code snippet to create two threads that print numbers from 1 to 5.**

**Answer:**
```java
class PrintTask implements Runnable {
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Thread t1 = new Thread(new PrintTask(), "Thread-1");
        Thread t2 = new Thread(new PrintTask(), "Thread-2");
        t1.start();
        t2.start();
    }
}
```

**Guess Output:**
Some may expect the numbers to be printed in order, but thread scheduling is unpredictable, so the output may be interleaved.

---

**Q5: Why should you use thread pools instead of creating new threads for every task?**

**Answer:**
Thread pools manage a fixed number of threads, improving performance and resource management. Creating too many threads can exhaust system resources.

**Guess Output:**
Some may think more threads always mean better performance, but too many threads can slow down the system.

## Java New Features (Java 8 and Beyond)

Java has introduced many powerful features in recent versions. Here are some of the most important ones, especially from Java 8 onwards, with code examples and practical do's and don'ts.

### 1. Lambda Expressions (Java 8)

Lambda expressions allow you to write concise code for functional interfaces (interfaces with a single abstract method).

**Example:**
```java
List<String> names = Arrays.asList("Aman", "Rahul", "Priya");
names.forEach(name -> System.out.println(name));
```

**Do's:**
- Use lambdas for short, simple implementations of functional interfaces.
- Use them to make code more readable and concise.

**Don'ts:**
- Don't use lambdas for complex logic; use regular methods for clarity.
- Don't overuse them where a method reference or traditional loop is clearer.

---

### 2. Streams API (Java 8)

Streams allow you to process collections in a functional style (filter, map, reduce, etc.).

**Example:**
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
numbers.stream().filter(n -> n % 2 == 0).forEach(System.out::println);
```

**Do's:**
- Use streams for data processing pipelines (filter, map, collect, etc.).
- Use method references for cleaner code.

**Don'ts:**
- Don't use streams for simple loops; traditional loops may be more efficient.
- Don't modify the source collection inside a stream operation.

---

### 3. Default and Static Methods in Interfaces (Java 8)

Interfaces can now have default and static methods with implementations.

**Example:**
```java
interface MyInterface {
    default void show() {
        System.out.println("Default method");
    }
    static void display() {
        System.out.println("Static method");
    }
}
```

**Do's:**
- Use default methods to add new functionality to interfaces without breaking existing implementations.
- Use static methods for utility or helper methods.

**Don'ts:**
- Don't use default methods to store state; interfaces should remain stateless.
- Don't overuse static methods in interfaces.

---

### 4. Optional Class (Java 8)

`Optional` is a container object which may or may not contain a non-null value, used to avoid null pointer exceptions.

**Example:**
```java
Optional<String> name = Optional.ofNullable(null);
System.out.println(name.orElse("Default"));
```

**Do's:**
- Use Optional as a return type for methods that may not return a value.
- Use `orElse`, `ifPresent`, and `map` for safe value handling.

**Don'ts:**
- Don't use Optional for every field or parameter; it's mainly for return types.
- Don't use Optional in performance-critical code (it adds overhead).

---

### 5. Local Date and Time API (Java 8)

New date and time API (`java.time`) is immutable and thread-safe.

**Example:**
```java
LocalDate today = LocalDate.now();
System.out.println(today);
```

**Do's:**
- Use the new API (`LocalDate`, `LocalTime`, `LocalDateTime`) instead of old `Date` and `Calendar` classes.
- Use immutability for thread safety.

**Don'ts:**
- Don't mix old and new date/time APIs.
- Don't mutate date/time objects (they are immutable).

---

### 6. Other Notable Features (Java 9+)

- **Modules (Java 9):** Organize code into modules for better maintainability.
- **var keyword (Java 10):** Local variable type inference.
- **Text Blocks (Java 13+):** Multi-line string literals.
- **Records (Java 16):** Concise immutable data carriers.
- **Pattern Matching (Java 16+):** Simplifies type checks and casting.

**Example (var):**
```java
var message = "Hello, Java 10!";
System.out.println(message);
```

**Example (Text Block):**
```java
String json = """
{
  "name": "Aman",
  "age": 25
}
""";
System.out.println(json);
```

**Example (Record):**
```java
public record Person(String name, int age) {}
Person p = new Person("Aman", 25);
System.out.println(p.name());
```

### Tricky Interview Questions on Java New Features (Java 8+)

**Q1: What happens if you use a lambda expression with a variable from the enclosing scope?**

**Answer:**
The variable must be effectively final (not changed after assignment). Otherwise, the code will not compile.

**Guess Output:**
Some may guess you can freely modify variables inside lambdas, but Java enforces them to be effectively final.

---

**Q2: Can you use 'this' keyword inside a static method in an interface?**

**Answer:**
No, you cannot use 'this' in a static method because static methods do not belong to an instance.

**Guess Output:**
Some may think 'this' is always available, but it is not in static context.

---

**Q3: What is the difference between Optional.of() and Optional.ofNullable()?**

**Answer:**
- `Optional.of(value)` throws NullPointerException if value is null.
- `Optional.ofNullable(value)` returns an empty Optional if value is null.

**Guess Output:**
Some may think both handle nulls the same way, but only ofNullable is safe for nulls.

---

**Q4: What happens if you try to mutate a LocalDate object?**

**Answer:**
LocalDate is immutable. Any operation returns a new object; the original is unchanged.

**Guess Output:**
Some may expect the original object to change, but it never does.

---

**Q5: What is the output of the following code?
```java
var x = null;
System.out.println(x);
```
**Answer:**
This will not compile. The type of 'var' cannot be inferred from null alone.

**Guess Output:**
Some may expect it to print 'null', but it is a compile-time error.

---

## Java Garbage Collector

The Java Garbage Collector (GC) automatically frees up memory by destroying objects that are no longer reachable in your program.

**Key Points:**
- GC helps prevent memory leaks and OutOfMemoryError.
- You cannot force garbage collection, but you can suggest it using `System.gc()` (not recommended for production).
- There are different types of collectors (Serial, Parallel, CMS, G1, ZGC, etc.).

**Example:**
```java
public class GCDemo {
    public static void main(String[] args) {
        GCDemo obj = new GCDemo();
        obj = null; // Eligible for GC
        System.gc(); // Suggests GC (may or may not run immediately)
        System.out.println("End of main");
    }
    @Override
    protected void finalize() throws Throwable {
        System.out.println("Object is garbage collected");
    }
}
```

**Do's:**
- Let the JVM manage memory automatically.
- Release references to large objects when no longer needed.

**Don'ts:**
- Don't rely on `System.gc()` for memory management.
- Don't override `finalize()` for critical cleanup (use try-with-resources or explicit close methods). 