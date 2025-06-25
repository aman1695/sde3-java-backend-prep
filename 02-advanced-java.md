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

## 2. Reflection and Annotations

### Theory
Reflection is a feature in Java that allows you to inspect and manipulate classes, methods, fields, and constructors at runtime. Annotations are metadata that provide information to the compiler or runtime about your code.

---

### Reflection in Java

Reflection enables dynamic inspection and modification of classes and objects. It is commonly used in frameworks, libraries, and tools (like Spring, Hibernate, JUnit).

**Live Example: Inspecting a Class**
```java
import java.lang.reflect.*;

public class ReflectionDemo {
    public static void main(String[] args) {
        Class<?> clazz = String.class;
        System.out.println("Class Name: " + clazz.getName());
        Method[] methods = clazz.getDeclaredMethods();
        System.out.println("Number of methods: " + methods.length);
    }
}
```
**Output:**
```
Class Name: java.lang.String
Number of methods: 70 (number may vary by JDK)
```

**Live Example: Creating an Object Dynamically**
```java
import java.lang.reflect.*;

public class ReflectionCreate {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("java.lang.String");
        Constructor<?> cons = clazz.getConstructor(String.class);
        Object obj = cons.newInstance("Hello");
        System.out.println(obj);
    }
}
```
**Output:**
```
Hello
```

**Do's:**
- Use reflection for frameworks, libraries, and tools that require dynamic behavior.
- Handle exceptions (ClassNotFoundException, NoSuchMethodException, etc.) properly.
- Use reflection for testing and debugging utilities.

**Don'ts:**
- Don't use reflection for regular application logic (it is slower and less safe).
- Don't use reflection to break encapsulation (accessing private fields/methods).
- Don't ignore security and performance implications.

---

### Annotations in Java

Annotations are special markers in code that provide metadata. They can be processed at compile time or runtime.

**Common Built-in Annotations:**
- @Override
- @Deprecated
- @SuppressWarnings

**Live Example: Custom Annotation**
```java
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface MyAnnotation {
    String value();
}

public class AnnotationDemo {
    @MyAnnotation("Test Method")
    public void test() {}

    public static void main(String[] args) throws Exception {
        Method m = AnnotationDemo.class.getMethod("test");
        MyAnnotation ann = m.getAnnotation(MyAnnotation.class);
        System.out.println(ann.value());
    }
}
```
**Output:**
```
Test Method
```

**Do's:**
- Use annotations to provide metadata for frameworks and tools.
- Use built-in annotations for better code clarity and compiler checks.
- Document custom annotations clearly.

**Don'ts:**
- Don't overuse custom annotations.
- Don't rely on annotations for business logic.
- Don't ignore retention policy and target when defining custom annotations.

---

### Most Asked Interview Questions: Reflection (with Answers)

**Q: What is reflection in Java?**
A: Reflection is the ability to inspect and manipulate classes, methods, fields, and constructors at runtime.

**Q: What are some use cases of reflection?**
A: Frameworks (Spring, Hibernate), testing tools (JUnit), serialization, dependency injection, and dynamic proxies.

**Q: What are the drawbacks of using reflection?**
A: Slower performance, security risks, and potential to break encapsulation.

**Q: How do you create an object using reflection?**
A: Use Class.forName() and Constructor.newInstance().

**Q: Can you access private fields/methods using reflection?**
A: Yes, by calling setAccessible(true), but it breaks encapsulation and should be avoided.

---

### Most Asked Interview Questions: Annotations (with Answers)

**Q: What are annotations in Java?**
A: Metadata that provide information to the compiler or runtime about code.

**Q: What is the difference between @Override and @Deprecated?**
A: @Override marks a method as overriding a superclass method; @Deprecated marks code as outdated.

**Q: How do you create a custom annotation?**
A: Use @interface, specify retention policy and target.

**Q: How can you access annotation values at runtime?**
A: Use reflection (e.g., getAnnotation() on a class, method, or field).

**Q: What is retention policy in annotations?**
A: It defines whether the annotation is available at source, compile, or runtime.

---

### Tricky & Coding Interview Questions on Reflection and Annotations

**Q1: What happens if you use reflection to change a final field?**

**Answer:**
It may work at runtime, but is not guaranteed and can lead to unpredictable behavior.

**Guess Output:**
Some may think final fields are always immutable, but reflection can bypass this (not recommended).

---

**Q2: Can you use annotations to enforce business logic?**

**Answer:**
No, annotations are metadata; enforcement must be done by frameworks or tools that process them.

**Guess Output:**
Some may think annotations alone can enforce logic, but they only provide information.

---

**Q3: Write a code snippet to list all methods of a class using reflection.**

**Answer:**
```java
Class<?> clazz = String.class;
for (Method m : clazz.getDeclaredMethods()) {
    System.out.println(m.getName());
}
```

**Guess Output:**
Some may expect only public methods, but getDeclaredMethods() returns all declared methods (including private).

---

**Q4: What is the output of the following code?
```java
@Retention(RetentionPolicy.RUNTIME)
@interface Marker {}

@Marker
class Test {}

public class Main {
    public static void main(String[] args) {
        boolean present = Test.class.isAnnotationPresent(Marker.class);
        System.out.println(present);
    }
}
```
**Answer:**
true

**Guess Output:**
Some may think custom annotations are not available at runtime, but with RUNTIME retention, they are.

---

Add notes, code samples, and resources for each topic above. 