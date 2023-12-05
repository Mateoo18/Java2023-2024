package org.simplestore.service;

import org.simplestore.model.Inventory;
import org.simplestore.model.Product;
import org.simplestore.model.ProductNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart extends ProductNotFoundException {
    private final Inventory inventory;
    private final Map<Integer, Integer> cartItems = new HashMap<>();

    public ShoppingCart(Inventory inventory) {
     this.inventory=inventory;
    }

    public synchronized void addItem(int productId, int quantity) {
        /*if (inventory.getProduct(productId) == null) {
            throw new ProductNotFoundException("Product with ID " + productId + " not found in the invventory.");
        }
        */
        cartItems.merge(productId, quantity, Integer::sum);
    }

    public synchronized void removeItem(int productId, int quantity) {
        cartItems.computeIfPresent(productId, (key, value) -> value - quantity);
        cartItems.remove(productId, 0);
    }
    public synchronized double calculateTotalCost() throws ProductNotFoundException {
        double totalPrice = 0.0;
        for (Map.Entry<Integer, Integer> entry : cartItems.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();
            Product product;
            try {
                product = inventory.getProduct(productId);
                totalPrice += product.getPrice() * quantity;
            } catch (ProductNotFoundException e) {
                throw new ProductNotFoundException();
            }
        }
        return totalPrice;
    }

    public synchronized void clearCart() {
        cartItems.clear();
    }

    public synchronized int getItemQuantity(int i) {

            return cartItems.getOrDefault(i, 0);

    }
}
