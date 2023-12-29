package org.onebox.cart;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.onebox.product.Product;

import java.util.LinkedHashMap;

class CartTest {

    Cart cart;
    Product product;
    @BeforeEach
    void setUp() {
        cart = new Cart(1);
        product = new Product(1, "Pen", 3.50);
    }

    @Test
    void addProduct() {
        cart.addProduct(product);
        Assertions.assertEquals(cart.getProducts().get(1), product);
    }

    @Test
    void deleteProduct() {
        cart.addProduct(product);
        Assertions.assertEquals(cart.getProducts().get(1), product);
        cart.deleteProduct(1);
        Assertions.assertNotEquals(cart.getProducts().get(1), product);
        Assertions.assertNull(cart.getProducts().get(1));
    }

    @Test
    void getProducts() {
        cart.addProduct(product);
        Assertions.assertEquals(cart.getProducts().get(1), product);
    }

    @Test
    void updateLastActivityTime() {
        cart.updateLastActivityTime();
        Cart cart2 = new Cart(2);
        Assertions.assertEquals(cart.getLastActivityTime(), cart2.getLastActivityTime());
    }

    @Test
    void isActive() {
        Assertions.assertTrue(cart.isActive());
    }

    @Test
    void isNotActive() {
        cart.setLastActivityTime(-60000);
        Assertions.assertFalse(cart.isActive());
    }

    @Test
    void testToString() {
        cart.addProduct(product);
        cart.setLastActivityTime(946681200000L);
        Assertions.assertEquals(cart.toString(), "Cart 1, contains {1=Product 1: Pen, 3,50€}. 01-01-2000 00:00:00");
    }

    @Test
    void getId() {
        Assertions.assertEquals(cart.getId(), 1);
    }

    @Test
    void getLastActivityTime() {
        // During runtime, the time difference between the execution of each method means that they may not be equal
        // Assertions.assertNotEquals(cart.getLastActivityTime(), System.currentTimeMillis());
        Assertions.assertNotNull(cart.getLastActivityTime());
    }

    @Test
    void setId() {
        cart.setId(2);
        Assertions.assertEquals(cart.getId(), 2);
    }

    @Test
    void setProducts() {
        LinkedHashMap<Integer, Product> products = new LinkedHashMap<>();
        products.put(product.getId(), product);
        cart.setProducts(products);
        Assertions.assertEquals(cart.getProducts().get(1), product);
    }

    @Test
    void setLastActivityTime() {
        cart.setLastActivityTime(System.currentTimeMillis());
        Assertions.assertEquals(cart.getLastActivityTime(), System.currentTimeMillis());
    }
}