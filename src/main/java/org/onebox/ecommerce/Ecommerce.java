package org.onebox.ecommerce;

import lombok.Data;
import org.onebox.cart.Cart;
import org.onebox.option.Option;
import org.onebox.product.Product;

import java.util.*;


@Data
public class Ecommerce {
    private LinkedHashMap<Integer, Cart> carts;
    private LinkedHashMap<Integer, Product> products;

    public static final String ACTIVE_CARTS = "These are the active carts:";
    public static final String ADDING_NEW_CART = "Adding new cart.";
    public static final String ADDING_NEW_PRODUCT = "Adding new product.";
    public static final String CART_ID = "Enter the cart id: ";
    public static final String ENTER_THE_AMOUNT_OF_THE_PRODUCT = "Enter the amount of the product: ";
    public static final String ENTER_THE_PRODUCT_DESCRIPTION = "Enter the product description: ";
    public static final String GOODBYE = "See you next time!";
    public static final String INACTIVITY = "Sorry, your cart has been deleted due to inactivity.";
    public static final String INCORRECT_ID = "Incorrect id";
    public static final String INTRO_MENU = "Please,select an option: ";
    public static final String LINE_BREAK = "\n";
    public static final String NEW_CART_ADDED = "New cart added: ";
    public static final String NEW_PRODUCT_ADDED = "New product added: ";
    public static final String NO_CARTS_YET = "There are no carts yet.";
    public static final String NO_PRODUCTS_YET = "There are no products yet.";
    public static final String PRODUCT_ID = "Enter the product id: ";
    public static final String PRODUCTS_AVAILABLE = "These are the products available: ";
    public static final String REMOVED_CART_WITH_ID = "Removed cart with id: ";
    public static final String REMOVED_PRODUCT_WITH_ID = "Removed product with id: ";
    public static final String SELECTED_OPTION = "You have selected the option: ";
    public static final String SELECT_THE_ID_OF_THE_CART_YOU_WANT_TO_DELETE = "Select the id of the cart you want to delete:";
    public static final String SELECT_THE_ID_OF_THE_PRODUCT_YOU_WANT_TO_DELETE = "Select the id of the product you want to delete:";
    public static final String THERE_IS_NO_PRODUCT_WITH_ID = "There is no product with id: ";
    public static final String WELCOME = "Hello! Welcome to my application!" + LINE_BREAK + "How can i help you?";
    public static final String WRONG_VALUE = "Please, enter a valid value.";

    public Ecommerce() {
        carts = new LinkedHashMap<>();
        products = new LinkedHashMap<>();
    }

    public Cart createCart() {
        System.out.println(ADDING_NEW_CART);
        int cartId = 0;
        for (Cart cart : carts.values()) {
            cartId = cart.getId();
        }
        Cart cart = new Cart(++cartId);
        carts.put(cart.getId(), cart);
        System.out.println(NEW_CART_ADDED + cart);
        return cart;
    }

    private Cart getCart(int cartId) {
        Cart cart = carts.get(cartId);
        if (cart != null) {
            if (cart.isActive()) {
                cart.updateLastActivityTime();
                return cart;
            }
            else {
                deleteCart(cart.getId());
            }
        }
        return null;
    }

    private boolean deleteCart(int cartId) {
        carts.remove(cartId);
        System.out.println(REMOVED_CART_WITH_ID + cartId);
        return true;
    }

    public boolean deleteSelectedCart() {
        if (showCarts()) {
            System.out.println(SELECT_THE_ID_OF_THE_CART_YOU_WANT_TO_DELETE);
            int deleteCartId = selectOption();
            Cart cart = getCart(deleteCartId);
            if (cart != null) {
                return deleteCart(cart.getId());
            }
            else {
                System.out.println(INCORRECT_ID);
                return false;
            }
        }
        return false;
    }

    public boolean showCarts() {
        if (!carts.isEmpty()) {
            System.out.println(ACTIVE_CARTS);
            // An auxiliary collection is created here, so if any of the carts are deleted due to inactivity,
            // when we continue scrolling through the collection, we do not get any errors.
            Collection<Cart> auxCollection = new ArrayList<>(carts.values());
            for (Cart cart : auxCollection) {
                if (getCart(cart.getId()) != null) {
                    System.out.println(getCart(cart.getId()));
                }
                else {
                    System.out.println(INACTIVITY);
                }
            }
            return true;
        }
        else {
            System.out.println(NO_CARTS_YET);
            return false;
        }
    }

    public Cart showSelectedCart() {
        System.out.println(CART_ID);
        int cartId = selectOption();
        if (cartId != 0 && getCart(cartId) != null) {
            Cart cart = getCart(cartId);
            System.out.println(cart);
            return cart;
        }
        else {
            System.out.println(INCORRECT_ID);
            return null;
        }
    }

    private Product createProduct(String description, double amount) {
        int productId = 0;
        for (Product product : products.values()) {
            productId = product.getId();
        }
        Product product = new Product(++productId, description, amount);
        products.put(product.getId(), product);
        return product;
    }

    private Product getProduct(int productId) {
        return products.get(productId);
    }

    private void deleteProduct(int productId) {
        if (getProduct(productId) != null) {
            products.remove(productId);
            for (Cart cart : carts.values()) {
                Objects.requireNonNull(getCart(cart.getId())).deleteProduct(productId);
            }
            System.out.println(REMOVED_PRODUCT_WITH_ID + productId);
        }
        else {
            System.out.println(THERE_IS_NO_PRODUCT_WITH_ID + productId);
        }
    }

    public void deleteSelectedProduct() {
        if (showProducts()) {
            System.out.println(SELECT_THE_ID_OF_THE_PRODUCT_YOU_WANT_TO_DELETE);
            int deleteProductId = selectOption();
            deleteProduct(deleteProductId);
        }
    }

    public boolean showProducts() {
        if (!products.isEmpty()) {
            System.out.println(PRODUCTS_AVAILABLE);
            for (Product product : products.values()) {
                System.out.println(getProduct(product.getId()));
            }
            return true;
        }
        else {
            System.out.println(NO_PRODUCTS_YET);
            return false;
        }
    }

    public Product showSelectedProduct() {
        System.out.println(PRODUCT_ID);
        int productId = selectOption();
        if (productId != 0 && getProduct(productId) != null) {
            Product product = getProduct(productId);
            System.out.println(product);
            return product;
        }
        else {
            System.out.println(INCORRECT_ID);
            return null;
        }
    }

    public void addNewProduct() {
        System.out.println(ADDING_NEW_PRODUCT);
        System.out.println(ENTER_THE_PRODUCT_DESCRIPTION);
        String productDescription = enterDescription();
        System.out.println(ENTER_THE_AMOUNT_OF_THE_PRODUCT);
        double productAmount = enterAmount();
        if ((productDescription != null && !productDescription.isBlank()) && productAmount > 0) {
            Product product = createProduct(productDescription, productAmount);
            System.out.println(NEW_PRODUCT_ADDED + product);
        }
        else {
            System.out.println(WRONG_VALUE);
        }
    }

    public void addProductToCart() {
        if (showCarts()) {
            Cart cart = showSelectedCart();
            if (cart != null) {
                if (showProducts()) {
                    Product product = showSelectedProduct();
                    if (product != null) {
                        cart.addProduct(product);
                    }
                }
            }
        }
        else {
            createCart();
        }
    }

    private static String enterDescription() {
        try {
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        } catch (InputMismatchException inputMismatchException) {
            return null;
        }
    }

    private static double enterAmount() {
        try {
            Scanner scanner = new Scanner(System.in);
            return scanner.nextDouble();
        } catch (InputMismatchException inputMismatchException) {
            return -1;
        }
    }

    public String showMenu() {
        String menu = LINE_BREAK + INTRO_MENU;
        for (Option option : Option.values()) {
            menu = menu.concat(LINE_BREAK + option.getVal());
        }
        System.out.println(menu);
        return menu;
    }

    public int selectOption() {
        try {
            Scanner scanner = new Scanner(System.in);
            int option = scanner.nextInt();
            System.out.println(SELECTED_OPTION + option);
            return option;
        } catch (InputMismatchException inputMismatchException) {
            return 0;
        }
    }
}
