package org.onebox.option;

import lombok.Getter;

@Getter
public enum Option {
    OPTION1(Constants.SEE_ALL_PRODUCTS),
    OPTION2(Constants.SEE_ALL_CARTS),
    OPTION3(Constants.CONSULT_PRODUCT),
    OPTION4(Constants.CONSULT_CART),
    OPTION5(Constants.ADD_PRODUCT),
    OPTION6(Constants.ADD_CART),
    OPTION7(Constants.ADD_PRODUCTS_TO_CART),
    OPTION8(Constants.DELETE_PRODUCT),
    OPTION9(Constants.DELETE_CART),
    OPTION10(Constants.EXIT);

    private final String val;

    Option(String val) {
        this.val = val;
    }

    private static class Constants {
        public static final String SEE_ALL_PRODUCTS = "1 See all products.";
        public static final String SEE_ALL_CARTS = "2 See all carts.";
        public static final String CONSULT_PRODUCT = "3 Consult a product.";
        public static final String CONSULT_CART = "4 Consult a cart.";
        public static final String ADD_PRODUCT = "5 Add new product.";
        public static final String ADD_CART = "6 Add new cart.";
        public static final String ADD_PRODUCTS_TO_CART = "7 Add products to cart.";
        public static final String DELETE_PRODUCT = "8 Delete a product.";
        public static final String DELETE_CART = "9 Delete a cart.";
        public static final String EXIT = "10 Exit the application.";
    }
}
