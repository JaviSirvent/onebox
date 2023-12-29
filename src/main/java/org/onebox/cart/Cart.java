package org.onebox.cart;

import lombok.Data;
import org.onebox.product.Product;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

@Data
public class Cart {
    private int id;
    private LinkedHashMap<Integer, Product> products;
    private long lastActivityTime;

    private static final long MAX_TIMEOUT = 600000;
    public static final String PATTERN = "dd-MM-yyyy HH:mm:ss";

    public Cart(int idCart) {
        this.id = idCart;
        this.products = new LinkedHashMap<>();
        this.lastActivityTime = System.currentTimeMillis();
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
        updateLastActivityTime();
    }

    public void deleteProduct(int productId) {
        products.remove(productId);
        updateLastActivityTime();
    }

    public LinkedHashMap<Integer, Product> getProducts() {
        updateLastActivityTime();
        return products;
    }

    public void updateLastActivityTime() {
        lastActivityTime = System.currentTimeMillis();
    }

    public boolean isActive() {
        return System.currentTimeMillis() - lastActivityTime < MAX_TIMEOUT;
    }

    @Override
    public String toString() {
        Date date = new Date(lastActivityTime);
        DateFormat dateFormat = new SimpleDateFormat(PATTERN);
        String timeFormat = dateFormat.format(date);
        return "Cart " + id + ", contains " + getProducts() + ". " + timeFormat;
    }
}
