package org.simplestore.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    @Test
    void productConstructorAndGettersTest() {
        Product product = new Product(1, "Test Product", 10.0);
        assertEquals(1, product.getId(), "Product ID should be 1");
        assertEquals("Test Product", product.getName(), "Product name should be 'Test Product'");
        assertEquals(10.0, product.getPrice(), "Product price should be 10.0");
    }

    @Test
    void productToStringTest() {
        Product product = new Product(2, "Another Product", 20.0);
        String expectedString = "Product{id=2, name='Another Product', price=20.0}";
        assertEquals(expectedString, product.toString(), "The toString method should return a correctly formatted string");
    }
}
