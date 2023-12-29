package org.onebox;

import org.onebox.ecommerce.Ecommerce;

import static org.onebox.ecommerce.Ecommerce.*;

public class Main {
    public static void main(String[] args) {
        Ecommerce ecommerce = new Ecommerce();
        System.out.println(WELCOME);
        startApp(ecommerce);
    }

    private static void startApp(Ecommerce ecommerce) {
        boolean exitSelected = false;
        do {
            ecommerce.showMenu();
            int option = ecommerce.selectOption();
            switch (option) {
                case 1:
                    ecommerce.showProducts();
                    break;
                case 2:
                    ecommerce.showCarts();
                    break;
                case 3:
                    ecommerce.showSelectedProduct();
                    break;
                case 4:
                    ecommerce.showSelectedCart();
                    break;
                case 5:
                    ecommerce.addNewProduct();
                    break;
                case 6:
                    ecommerce.createCart();
                    break;
                case 7:
                    ecommerce.addProductToCart();
                    break;
                case 8:
                    ecommerce.deleteSelectedProduct();
                    break;
                case 9:
                    ecommerce.deleteSelectedCart();
                    break;
                case 10:
                    System.out.println(GOODBYE);
                    exitSelected = true;
                    break;
                default:
                    System.out.println(WRONG_VALUE);
                    break;
            }
        } while (!exitSelected);
    }
}