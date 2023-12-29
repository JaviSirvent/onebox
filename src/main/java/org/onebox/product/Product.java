package org.onebox.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    private int id;
    private String description;
    private double amount;

    @Override
    public String toString() {
        return "Product " + id + ": " + description + ", " + String.format("%.2f", amount) + "€";
    }
}