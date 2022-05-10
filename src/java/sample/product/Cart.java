/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.product;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author idchi
 */
public class Cart {
    private Map<String, ProductDTO> cart;

    public Cart() {
    }

    public Cart(Map<String, ProductDTO> cart) {
        this.cart = cart;
    }

    public Map<String, ProductDTO> getCart() {
        return cart;
    }

    public void setCart(Map<String, ProductDTO> cart) {
        this.cart = cart;
    }
    
    public boolean addToCart(ProductDTO newProduct){
        boolean check = false;
        if (this.cart == null) {
            this.cart = new HashMap<>();
        }
        if (this.cart.containsKey(newProduct.getProductID())) {
            int currentQuantity = this.cart.get(newProduct.getProductID()).getQuantity();
            newProduct.setQuantity(currentQuantity + newProduct.getQuantity());
        }
        cart.put(newProduct.getProductID(), newProduct);
        check = true;
        return check;
    }
    public boolean remove(String id) {
        boolean check = false;
        if (this.cart != null) {
            if (this.cart.containsKey(id)) {
                this.cart.remove(id);
                check = true;
            }
        }
        return check;
    }

    public boolean update(String id, ProductDTO newProduct) {
        boolean check = false;
        if (this.cart != null) {
            if (this.cart.containsKey(id)) {
                this.cart.replace(id, newProduct);
                check = true;
            }
        }
        return check;
    }
}
