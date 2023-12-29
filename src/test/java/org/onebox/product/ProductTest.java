package org.onebox.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductTest {

    Product product;
    @BeforeEach
    void setUp() {
        product = new Product(1, "Pen", 3.50);
    }

    @Test
    void testToString() {
        Assertions.assertEquals(product.toString(), "Product 1: Pen, 3,50€");
    }

    @Test
    void getId() {
        Assertions.assertEquals(product.getId(), 1);
    }

    @Test
    void getDescription() {
        Assertions.assertEquals(product.getDescription(), "Pen");
    }

    @Test
    void getAmount() {
        Assertions.assertEquals(product.getAmount(), 3.50);
    }

    @Test
    void setId() {
        product.setId(2);
        Assertions.assertEquals(product.getId(), 2);
    }

    @Test
    void setDescription() {
        product.setDescription("Apple");
        Assertions.assertEquals(product.getDescription(), "Apple");
    }

    @Test
    void setAmount() {
        product.setAmount(1.50);
        Assertions.assertEquals(product.getAmount(), 1.50);
    }
}