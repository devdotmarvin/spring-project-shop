package dev.marvin.shop.cart;

import dev.marvin.shop.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    public static List<Product> cartItems;

    static {
        cartItems = new ArrayList<>();
    }
}
