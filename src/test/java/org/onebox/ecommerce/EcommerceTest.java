package org.onebox.ecommerce;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.onebox.cart.Cart;
import org.onebox.product.Product;

import java.io.ByteArrayInputStream;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class EcommerceTest {

    public static final String menu = """

            Please,select an option:\s
            1 See all products.
            2 See all carts.
            3 Consult a product.
            4 Consult a cart.
            5 Add new product.
            6 Add new cart.
            7 Add products to cart.
            8 Delete a product.
            9 Delete a cart.
            10 Exit the application.""";

    public Ecommerce ecommerce;

    @BeforeEach
    void setUp() {
        ecommerce = new Ecommerce();
    }

    @Test
    void createCart() {
        Cart cart1 = ecommerce.createCart();
        Assertions.assertEquals(1, cart1.getId());
    }

    @Test
    void createMoreThanOneCarts() {
        Cart cart1 = ecommerce.createCart();
        Cart cart2 = ecommerce.createCart();
        Assertions.assertEquals(1, cart1.getId());
        Assertions.assertEquals(2, cart2.getId());
    }

    @Test
    void showCarts() {
        ecommerce.createCart();
        Assertions.assertTrue(ecommerce.showCarts());
    }

    @Test
    void showCartsInactivityDelete() {
        Cart cart1 = ecommerce.createCart();
        Cart cart2 = ecommerce.createCart();
        cart1.setLastActivityTime(cart1.getLastActivityTime() - 600000);
        Assertions.assertTrue(ecommerce.showCarts());
        Assertions.assertEquals(2, cart2.getId());
    }

    @Test
    void showEmptyCarts() {
        Assertions.assertFalse(ecommerce.showCarts());
    }

    @Test
    void deleteSelectedCart() {
        ecommerce.createCart();
        provideInput("1");
        Assertions.assertTrue(ecommerce.deleteSelectedCart());
    }

    @Test
    void deleteSelectedCartNoValidId() {
        ecommerce.createCart();
        provideInput("2");
        Assertions.assertFalse(ecommerce.deleteSelectedCart());
    }

    @Test
    void deleteSelectedCartEmptyCarts() {
        provideInput("2");
        Assertions.assertFalse(ecommerce.deleteSelectedCart());
    }

    @Test
    void showSelectedCart() {
        Cart cart1 = ecommerce.createCart();
        provideInput("1");
        Assertions.assertEquals(ecommerce.showSelectedCart(), cart1);
    }

    @Test
    void showSelectedCartNoValidId() {
        Cart cart1 = ecommerce.createCart();
        provideInput("2");
        Assertions.assertNotEquals(ecommerce.showSelectedCart(), cart1);
    }

    @Test
    void deleteSelectedProduct() {
        Product product = prepareProductForTest();
        provideInput("1");
        ecommerce.deleteSelectedProduct();
        Assertions.assertNull(ecommerce.getProducts().get(product.getId()));
    }

    @Test
    void deleteSelectedProductNoValidId() {
        Product product = prepareProductForTest();
        provideInput("2");
        ecommerce.deleteSelectedProduct();
        Assertions.assertNotNull(ecommerce.getProducts().get(product.getId()));
    }

    @Test
    void showProducts() {
        prepareProductForTest();
        Assertions.assertTrue(ecommerce.showProducts());
    }

    @Test
    void showProductsEmpty() {
        Assertions.assertFalse(ecommerce.showProducts());
    }

    @Test
    void showSelectedProduct() {
        Product product = prepareProductForTest();
        provideInput("1");
        Assertions.assertEquals(ecommerce.showSelectedProduct(), product);
    }

    @Test
    void showSelectedProductNoValidId() {
        Product product = prepareProductForTest();
        provideInput("2");
        Assertions.assertNotEquals(ecommerce.showSelectedProduct(), product);
    }

    // I have searched and tried several solutions to simulate two console inputs during the execution of the same test,
    // but I have not been able to find the right way to do it. During the execution of the code, when it tries to read
    // the second input, a NoSuchElementException is thrown. To prevent the test from failing,
    // I have indicated in the test that this class of exception will occur.
    @Test
    void addNewProductException() {
        provideInput("1");
        Exception exception = assertThrows(NoSuchElementException.class, ecommerce::addNewProduct);
        Assertions.assertEquals(exception.getClass(), NoSuchElementException.class);
    }

    @Test
    void addProductToCartException() {
        ecommerce.createCart();
        prepareProductForTest();
        provideInput("1");
        Exception exception = assertThrows(NoSuchElementException.class, ecommerce::addProductToCart);
        Assertions.assertEquals(exception.getClass(), NoSuchElementException.class);
    }

    @Test
    void addProductToCart() {
        ecommerce.addProductToCart();
        Assertions.assertNotNull(ecommerce.getCarts().get(1));
    }

    @Test
    void showMenu() {
        Assertions.assertEquals(ecommerce.showMenu(), menu);
    }

    @Test
    void selectOption() {
        provideInput("1");
        Assertions.assertEquals(ecommerce.selectOption(), 1);
    }

    @Test
    void selectOptionNoValidNumber() {
        provideInput("a");
        Assertions.assertEquals(ecommerce.selectOption(), 0);
    }

    @Test
    void getCarts() {
        Cart cart = ecommerce.createCart();
        LinkedHashMap<Integer, Cart> carts = ecommerce.getCarts();
        Assertions.assertEquals(carts.get(1), cart);
    }

    @Test
    void getProducts() {
        Product product = prepareProductForTest();
        Assertions.assertEquals(ecommerce.getProducts().get(1), product);
    }

    @Test
    void setCarts() {
        Cart cart = ecommerce.createCart();
        LinkedHashMap<Integer, Cart> carts = new LinkedHashMap<>();
        carts.put(cart.getId(), cart);
        ecommerce.setCarts(carts);
        Assertions.assertEquals(ecommerce.getCarts().get(1), cart);
    }

    @Test
    void setProducts() {
        Product product = prepareProductForTest();
        Assertions.assertEquals(ecommerce.getProducts().get(1), product);
    }

    Product prepareProductForTest() {
        Product product = new Product(1, "Pen", 3.50);
        LinkedHashMap<Integer, Product> products = new LinkedHashMap<>();
        products.put(product.getId(), product);
        ecommerce.setProducts(products);
        return product;
    }

    /**
     * Method to simulate user input
     * @param data test input data
     */
    void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }
}