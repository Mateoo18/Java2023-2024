package org.simplestore.service;

import org.junit.jupiter.api.Test;
import org.simplestore.model.Inventory;
import org.simplestore.model.Product;
import org.simplestore.model.ProductNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
public class ShoppingCartConcurrencyTest {
    private final Inventory inventory = new Inventory();

    @Test
    void addAndRemoveItemsConcurrently() throws InterruptedException{
        ShoppingCart shoppingCart = new ShoppingCart(inventory);
        inventory.addProduct(new Product(1, "Test Product", 10.0));
        int totalThreads = 3;
        AtomicInteger itemsToAdd = new AtomicInteger(14);
        AtomicInteger itemsToRemove = new AtomicInteger(3);

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < totalThreads; i++) {
            threads.add(new Thread(() -> {
                while (itemsToAdd.getAndDecrement() > 0) {
                    shoppingCart.addItem(1, 1);
                }
            }));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        threads.clear();

        for (int i = 0; i < totalThreads; i++) {
            threads.add(new Thread(() -> {
                while (itemsToRemove.getAndDecrement() > 0){
                    shoppingCart.removeItem(1, 1);
                }
            }));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

        assertEquals(11, shoppingCart.getItemQuantity(1));
    }

    @Test
    void calculateTotalCostConcurrently() throws InterruptedException, ProductNotFoundException {
        ShoppingCart shoppingCart = new ShoppingCart(inventory);
        inventory.addProduct(new Product(1, "Test Product", 10.0));

        int totalThreads = 10;
        int itemsToAdd = 100;

        ExecutorService executor = Executors.newFixedThreadPool(totalThreads);
        for (int i = 0; i < itemsToAdd; i++) {
            executor.execute(() -> {
                    shoppingCart.addItem(1, 1);
            });
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        //Uzywanie executor jest lepsze pod wzgledem tworzenai wielu watkow
        assertEquals(1000.0, shoppingCart.calculateTotalCost());
    }

}
