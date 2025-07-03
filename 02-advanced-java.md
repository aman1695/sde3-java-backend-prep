# Advanced Java

## 1. JVM Internals

### Theory
The Java Virtual Machine (JVM) is the engine that runs Java applications. It provides platform independence, memory management, garbage collection, and class loading. Understanding JVM internals helps you write efficient and robust Java code.

#### JVM Architecture Overview
- **Class Loader Subsystem:** Loads class files (.class) into memory.
- **Runtime Data Areas:** Manages memory for running programs (Heap, Stack, Method Area, etc.).
- **Execution Engine:** Executes bytecode instructions.
- **Native Interface:** Interacts with native libraries (JNI).
- **Garbage Collector:** Automatically frees unused memory.

---

### Memory Management in JVM

JVM divides memory into several areas:
- **Heap:** Stores objects and instance variables. Shared among all threads.
- **Stack:** Stores method calls and local variables. Each thread has its own stack.
- **Method Area:** Stores class metadata, static variables, and bytecode.
- **PC Register:** Stores the address of the current instruction for each thread.
- **Native Method Stack:** Used for native (non-Java) methods.

**Live Example: Heap vs Stack**
```java
public class MemoryDemo {
    public static void main(String[] args) {
        int x = 10; // Stored in stack
        String s = new String("Hello"); // 's' reference in stack, object in heap
        System.out.println(x + ", " + s);
    }
}
```
**Output:**
```
10, Hello
```

---

### Garbage Collection in JVM

Garbage Collection (GC) is the process of automatically freeing memory by destroying objects that are no longer reachable.

**Live Example: Object eligible for GC**
```java
public class GCDemo {
    public static void main(String[] args) {
        GCDemo obj = new GCDemo();
        obj = null; // Now eligible for GC
        System.gc(); // Suggests GC (may or may not run immediately)
        System.out.println("End of main");
    }
    @Override
    protected void finalize() throws Throwable {
        System.out.println("Object is garbage collected");
    }
}
```
**Output:**
```
End of main
Object is garbage collected
```

**Do's:**
- Let the JVM manage memory automatically.
- Release references to large objects when no longer needed.

**Don'ts:**
- Don't rely on System.gc() for memory management.
- Don't override finalize() for critical cleanup.

---

### Class Loading in JVM

The class loader loads Java classes into memory when required. There are three main types:
- **Bootstrap ClassLoader:** Loads core Java classes (rt.jar).
- **Extension ClassLoader:** Loads classes from the extension directory.
- **Application ClassLoader:** Loads classes from the application classpath.

**Live Example: Custom Class Loader**
```java
public class LoaderDemo {
    public static void main(String[] args) {
        ClassLoader loader = LoaderDemo.class.getClassLoader();
        System.out.println("ClassLoader: " + loader);
    }
}
```
**Output:**
```
ClassLoader: sun.misc.Launcher$AppClassLoader@<hash>
```

---

### Do's
- Understand JVM memory areas to write efficient code.
- Use profiling tools (jvisualvm, jconsole) to monitor memory usage.
- Be aware of class loading issues in large applications.

### Don'ts
- Don't create unnecessary objects (increases GC overhead).
- Don't ignore memory leaks (e.g., static references).
- Don't assume all objects are immediately garbage collected after becoming unreachable.

---

### Most Asked Interview Questions: Memory Management (with Answers)

**Q: What are the different memory areas allocated by JVM?**
A: Heap, Stack, Method Area, PC Register, and Native Method Stack.

**Q: What is the difference between stack and heap memory?**
A: Stack stores method calls and local variables (thread-specific); heap stores objects and is shared among threads.

**Q: How does JVM allocate memory to objects and variables?**
A: Objects are allocated on the heap; local variables and method calls are on the stack.

**Q: What is a memory leak? How can you avoid it in Java?**
A: A memory leak occurs when objects are no longer used but not garbage collected due to lingering references. Avoid by releasing references and not using unnecessary static fields.

**Q: What is the PermGen/Metaspace area?**
A: PermGen (pre-Java 8) and Metaspace (Java 8+) store class metadata. Metaspace grows dynamically, while PermGen had a fixed size.

---

### Most Asked Interview Questions: Garbage Collection (with Answers)

**Q: What is garbage collection in Java and how does it work?**
A: GC automatically frees memory by destroying unreachable objects. The JVM periodically runs GC to reclaim heap space.

**Q: What are the different types of garbage collectors in JVM?**
A: Serial, Parallel, CMS (Concurrent Mark Sweep), G1, and ZGC (Java 11+).

**Q: How do you make an object eligible for garbage collection?**
A: Remove all references to the object so it becomes unreachable.

**Q: What is the role of finalize() method?**
A: finalize() is called by GC before reclaiming an object, but its use is discouraged and not guaranteed.

**Q: Can you force garbage collection in Java?**
A: No, you can only suggest it using System.gc(); JVM decides when to run GC.

**Q: What is the difference between minor and major GC?**
A: Minor GC collects the young generation; major (full) GC collects the entire heap, including old generation.

---

### Most Asked Interview Questions: Class Loading (with Answers)

**Q: What is class loading in Java?**
A: The process of loading class files into JVM memory at runtime.

**Q: What are the different types of class loaders?**
A: Bootstrap, Extension, and Application class loaders.

**Q: Explain the class loader delegation model.**
A: Each class loader delegates the loading request to its parent before attempting to load the class itself.

**Q: How can you create a custom class loader?**
A: By extending java.lang.ClassLoader and overriding the findClass() method.

**Q: What is the difference between static and dynamic class loading?**
A: Static loading uses the 'new' keyword; dynamic loading uses Class.forName() or custom class loaders at runtime.

**Q: What is the parent-child relationship in class loaders?**
A: Class loaders form a hierarchy; each loader has a parent, and delegation flows up the hierarchy.

---

### Interview & Tricky Questions on JVM Internals

**Q1: What is the difference between Heap and Stack memory in JVM?**

**Answer:**
- Heap stores objects and is shared among threads.
- Stack stores method calls and local variables, and is thread-specific.

**Guess Output:**
Some may think both store objects, but only the heap does.

---

**Q2: What happens if you call System.gc()?**

**Answer:**
It suggests the JVM to run garbage collection, but it is not guaranteed to run immediately.

**Guess Output:**
Some may think it forces GC, but it's just a suggestion.

---

**Q3: Can you run out of stack memory? What causes StackOverflowError?**

**Answer:**
Yes, deep or infinite recursion can exhaust stack memory, causing StackOverflowError.

**Guess Output:**
Some may think only heap can run out, but stack can too.

---

**Q4: What is classloader delegation model?**

**Answer:**
A classloader first delegates the class loading request to its parent before attempting to load the class itself.

**Guess Output:**
Some may think each classloader loads classes independently, but delegation is the default model.

---

**Q5: Write a code snippet that causes a memory leak in Java.**

**Answer:**
```java
import java.util.*;
public class LeakDemo {
    static List<byte[]> list = new ArrayList<>();
    public static void main(String[] args) {
        while (true) {
            list.add(new byte[1024 * 1024]); // Keeps adding 1MB blocks
        }
    }
}
```

**Guess Output:**
Some may expect the JVM to clean up, but static references prevent GC, leading to OutOfMemoryError.

---

**Q6: What is the output of the following code?
```java
public class Test {
    public static void main(String[] args) {
        Object obj = new Object();
        obj = null;
        System.gc();
        System.out.println("Done");
    }
    protected void finalize() {
        System.out.println("finalize called");
    }
}
```
**Answer:**
"Done" will always print, but "finalize called" may or may not print, depending on whether GC runs.

**Guess Output:**
Some may think finalize is always called, but it's not guaranteed.

---

## 2. Advanced Java Concepts

## 2.1 Memory Management

### 2.1.1 JVM Memory Structure

```
JVM Memory
├── Heap (shared by all threads)
│   ├── Young Generation
│   │   ├── Eden Space
│   │   └── Survivor Spaces (S0, S1)
│   └── Old Generation
├── Non-Heap
│   ├── Metaspace (replaces PermGen)
│   ├── Code Cache
│   └── Thread Stacks
└── Native Memory
```

### 2.1.2 Garbage Collection

**Types of GC:**
- Serial GC: Single-threaded, good for small applications
- Parallel GC: Multi-threaded, throughput-oriented
- CMS (Concurrent Mark Sweep): Low pause time
- G1 GC: Balanced approach, default in Java 9+
- ZGC: Ultra-low latency (Java 11+)

**GC Tuning Parameters:**
```bash
-Xms2g -Xmx4g                    # Initial and max heap size
-XX:NewRatio=3                   # Ratio of old to young generation
-XX:SurvivorRatio=8              # Ratio of Eden to survivor spaces
-XX:+UseG1GC                     # Use G1 garbage collector
```

### 2.1.3 Memory Leaks

```java
// Common memory leak patterns
class MemoryLeak {
    private static final List<Object> cache = new ArrayList<>();
    
    public void addToCache(Object obj) {
        cache.add(obj); // Never removed
    }
}

// Proper cache with eviction
class ProperCache {
    private static final Map<String, Object> cache = new ConcurrentHashMap<>();
    private static final int MAX_SIZE = 1000;
    
    public void addToCache(String key, Object value) {
        if (cache.size() >= MAX_SIZE) {
            // Remove oldest entries
            cache.clear();
        }
        cache.put(key, value);
    }
}
```

## 2.2 Reflection

### 2.2.1 Basic Reflection

```java
// Get class information
Class<?> clazz = String.class;
System.out.println("Class name: " + clazz.getName());
System.out.println("Superclass: " + clazz.getSuperclass());

// Get methods
Method[] methods = clazz.getMethods();
for (Method method : methods) {
    System.out.println("Method: " + method.getName());
}

// Get fields
Field[] fields = clazz.getDeclaredFields();
for (Field field : fields) {
    System.out.println("Field: " + field.getName());
}
```

### 2.2.2 Dynamic Method Invocation

```java
class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
}

// Using reflection to invoke method
Calculator calc = new Calculator();
Class<?> clazz = calc.getClass();
Method addMethod = clazz.getMethod("add", int.class, int.class);
int result = (int) addMethod.invoke(calc, 5, 3);
System.out.println("Result: " + result);
```

### 2.2.3 Creating Objects Dynamically

```java
// Create object using reflection
Class<?> clazz = Class.forName("java.util.ArrayList");
List<String> list = (List<String>) clazz.getDeclaredConstructor().newInstance();
list.add("Hello");
```

## 2.3 Annotations

### 2.3.1 Built-in Annotations

```java
// Method annotations
@Override
public String toString() { return "Custom toString"; }

@Deprecated
public void oldMethod() { }

@SuppressWarnings("unchecked")
public void methodWithWarnings() { }

// Custom annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Test {
    String value() default "";
    int timeout() default 1000;
}

// Using custom annotation
@Test(value = "myTest", timeout = 5000)
public void testMethod() { }
```

### 2.3.2 Annotation Processing

```java
// Process annotations at runtime
Method[] methods = MyClass.class.getMethods();
for (Method method : methods) {
    if (method.isAnnotationPresent(Test.class)) {
        Test test = method.getAnnotation(Test.class);
        System.out.println("Test method: " + method.getName());
        System.out.println("Test value: " + test.value());
    }
}
```

## 2.4 Serialization

### 2.4.1 Basic Serialization

```java
class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;
    
    // Constructor, getters, setters...
}

// Serialize object
Person person = new Person("John", 30);
try (ObjectOutputStream oos = new ObjectOutputStream(
        new FileOutputStream("person.ser"))) {
    oos.writeObject(person);
}

// Deserialize object
try (ObjectInputStream ois = new ObjectInputStream(
        new FileInputStream("person.ser"))) {
    Person deserializedPerson = (Person) ois.readObject();
}
```

### 2.4.2 Custom Serialization

```java
class CustomPerson implements Serializable {
    private String name;
    private transient String password; // Won't be serialized
    
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        // Custom serialization logic
    }
    
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        // Custom deserialization logic
    }
}
```

## 2.5 NIO (New I/O)

### 2.5.1 Channels and Buffers

```java
// File reading with NIO
try (FileChannel channel = FileChannel.open(Paths.get("file.txt"))) {
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    int bytesRead = channel.read(buffer);
    
    buffer.flip(); // Prepare for reading
    while (buffer.hasRemaining()) {
        System.out.print((char) buffer.get());
    }
}

// File writing with NIO
try (FileChannel channel = FileChannel.open(Paths.get("output.txt"), 
        StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
    ByteBuffer buffer = ByteBuffer.wrap("Hello NIO".getBytes());
    channel.write(buffer);
}
```

### 2.5.2 Non-blocking I/O

```java
// Non-blocking server
ServerSocketChannel serverChannel = ServerSocketChannel.open();
serverChannel.configureBlocking(false);
serverChannel.bind(new InetSocketAddress(8080));

Selector selector = Selector.open();
serverChannel.register(selector, SelectionKey.OP_ACCEPT);

while (true) {
    selector.select();
    Set<SelectionKey> keys = selector.selectedKeys();
    
    for (SelectionKey key : keys) {
        if (key.isAcceptable()) {
            // Handle new connection
        } else if (key.isReadable()) {
            // Handle read operation
        }
    }
}
```

## 2.6 Concurrency Utilities

### 2.6.1 Executor Framework

```java
// Thread pool
ExecutorService executor = Executors.newFixedThreadPool(4);

// Submit tasks
Future<String> future = executor.submit(() -> {
    Thread.sleep(1000);
    return "Task completed";
});

// Get result
String result = future.get(5, TimeUnit.SECONDS);

// Shutdown
executor.shutdown();
executor.awaitTermination(1, TimeUnit.MINUTES);
```

### 2.6.2 Concurrent Collections

```java
// Thread-safe collections
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
BlockingQueue<String> queue = new LinkedBlockingQueue<>();

// Atomic operations
AtomicInteger counter = new AtomicInteger(0);
counter.incrementAndGet();
counter.compareAndSet(1, 2);
```

### 2.6.3 CompletableFuture (Java 8+)

```java
// Asynchronous operations
CompletableFuture<String> future = CompletableFuture
    .supplyAsync(() -> "Hello")
    .thenApply(s -> s + " World")
    .thenAccept(System.out::println);

// Combining futures
CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "World");

CompletableFuture<String> combined = future1.thenCombine(future2, (a, b) -> a + " " + b);
```

## 2.7 Interview Questions

### 2.7.1 Memory Management

**Q: What is the difference between stack and heap memory?**
A:
- Stack: Thread-specific, stores local variables and method calls
- Heap: Shared by all threads, stores objects
- Stack is faster, heap is larger but slower
- Stack has automatic cleanup, heap requires garbage collection

**Q: How does garbage collection work?**
A:
- Mark and Sweep: Mark reachable objects, sweep unreachable ones
- Generational: Objects age and move to different generations
- Young generation uses copying, old generation uses mark-compact
- G1 GC divides heap into regions for better performance

### 2.7.2 Reflection

**Q: What are the drawbacks of using reflection?**
A:
- Performance overhead due to dynamic method resolution
- Security restrictions may prevent access
- Code becomes harder to understand and maintain
- Compile-time type checking is lost

**Q: When would you use reflection?**
A:
- Framework development (Spring, Hibernate)
- Testing frameworks
- Plugin architectures
- Dynamic code generation

### 2.7.3 Concurrency

**Q: What is the difference between synchronized and ReentrantLock?**
A:
- ReentrantLock is more flexible (tryLock, fair ordering)
- ReentrantLock can be interrupted while waiting
- synchronized is simpler and automatically released
- ReentrantLock requires explicit unlock in finally block

**Q: What is the volatile keyword used for?**
A:
- Ensures visibility of changes across threads
- Prevents instruction reordering
- Does not provide atomicity
- Used for simple flags and counters

Add notes, code samples, and resources for each topic above. 