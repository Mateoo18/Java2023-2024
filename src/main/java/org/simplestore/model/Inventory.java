package org.simplestore.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private final Map<Integer, Product> products = new HashMap<>();

    public synchronized void addProduct(Product product) {
        products.put(product.getId(), product);
    }
    public synchronized Product getProduct(int id) throws ProductNotFoundException {
        Product product = products.get(id);
        if (product == null) {
            throw new ProductNotFoundException("Product with ID " + id + " was not found.");
        }
        return product;
    }
    public synchronized Collection<Product> listAllProducts() {

        return new ArrayList<>(products.values());
    }
    public synchronized void removeproduct(int id) throws ProductNotFoundException {
        if (!products.containsKey(id)) {
            throw new ProductNotFoundException("Product with ID " + id + " not found.");
        }
        products.remove(id);
    }
}
