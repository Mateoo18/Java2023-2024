# 🛒 SimpleStore – Java Shopping Cart Simulation

SimpleStore is a lightweight Java application that simulates a store system.  
It demonstrates OOP principles, exception handling, and modular design.  
Key features include inventory management, shopping-cart operations, and loading products from an external file.

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── org/
│   │       └── simplestore/
│   │           ├── model/
│   │           │   ├── Inventory.java
│   │           │   ├── Product.java
│   │           │   └── ProductNotFoundException.java
│   │           ├── service/
│   │           │   └── ShoppingCart.java
│   │           ├── util/
│   │           │   └── InventoryLoader.java
│   │           └── Main.java
│   └── resources/
│       └── inventory.txt
```

## 🔑 Key Classes

### `Product`
Represents a single product with immutable fields:
- `id`
- `name`
- `price`

### `Inventory`
Manages products in the store:
- `addProduct(Product)`
- `getProduct(int id)` – throws `ProductNotFoundException` if not found
- `listAllProducts()`
- `removeProduct(int id)`

### `ShoppingCart`
Handles the customer’s cart logic:
- `addItem(int productId, int quantity)`
- `removeItem(int productId, int quantity)`
- `calculateTotalCost()`
- `clearCart()`
- `getItemQuantity(int productId)`



### `InventoryLoader`
Loads products from a CSV-formatted `.txt` file (`id, name, price`) and adds them to the inventory.  
Malformed lines are skipped with a warning.

### `Main`
Demonstrates the application flow:
- Loading products from file
- Adding/removing products
- Using the shopping cart
- Printing total cost

## 📄 Sample `inventory.txt`

```
1, Wooden Table, 99.99
2, Chair, 49.99
3, Lamp, 19.99
4, Desk, 149.99
5, Monitor, 249.50
```

## ▶️ How to Run

### Compile and Run from Terminal:
```bash
javac -d out $(find ./src -name "*.java")
java -cp out org.simplestore.Main
```

Or open the project in your IDE and run `Main.java`.

## ✅ Requirements

- Java 11 or newer
- No external libraries

