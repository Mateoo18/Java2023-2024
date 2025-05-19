## 🎓 Student Resource System – Decorator Pattern in Java

This module models an extensible system for student-related resources (e.g., courses) using the **Decorator Pattern**.

### 🧱 Core Interfaces & Classes

- **`StudentResource`**  
  Interface for all resources (e.g., courses, tools).

- **`Course`**  
  A concrete implementation of `StudentResource`. Contains:
  - `id`: course ID
  - `name`: course name

- **`ResourceDecorator`** *(abstract)*  
  Base class for all decorators, wraps a `StudentResource`.

### 🧩 Concrete Decorators

- **`CommentableResource`**  
  Adds the ability to attach a comment to a resource.
  - `addComment(String comment)`
  - `getComment()`

- **`RateableResource`**  
  Adds rating functionality.
  - `setRating(double value)`
  - `getRating()`

Each decorator preserves the original interface while adding new responsibilities.

### 🧠 Design Benefits

- Fully open for extension without modifying the original `Course` class
- Encourages reusable, modular behavior extensions

### 🚀 Usage Example

```java
StudentResource course = new Course("CS101", "Intro to Java");
CommentableResource commented = new CommentableResource(course);
commented.addComment("Challenging but useful");

RateableResource rated = new RateableResource(commented);
rated.setRating(4.5);

System.out.println(rated.getName());        // "Intro to Java"
System.out.println(rated.getComment());     // "Challenging but useful"
System.out.println(rated.getRating());      // 4.5
