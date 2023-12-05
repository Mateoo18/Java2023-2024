package org.simplestore;

import org.simplestore.model.Inventory;
import org.simplestore.model.Product;
import org.simplestore.model.ProductNotFoundException;
import org.simplestore.service.ShoppingCart;
import org.simplestore.util.InventoryLoader;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws ProductNotFoundException {
        Inventory inventory = new Inventory();
        InventoryLoader.loadInventory("/home/mateusz/Pulpit/Java/ShopSim/src/main/resources/inventory.txt", inventory);
        ShoppingCart shoppingCart=new ShoppingCart(inventory);
        shoppingCart.addItem(1,1);
        shoppingCart.addItem(10,2);
        System.out.println("Total cost: "+shoppingCart.calculateTotalCost());
        shoppingCart.clearCart();

        Product product1=new Product(11,"Plastic car",20.00);
        inventory.addProduct(product1);
        ArrayList<Product> products = (ArrayList<Product>) inventory.listAllProducts();

        for(int i=0;i<products.size();i++){
            System.out.println(products.get(i).toString());
        }
        inventory.removeproduct(4);
        ArrayList<Product> products1 = (ArrayList<Product>) inventory.listAllProducts();

        for ( Product product:products1){
            System.out.println(product.toString());
        }
    }
}

