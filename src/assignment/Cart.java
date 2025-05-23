package assignment;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private int number;
    private List<CartItem> items;
    
    // Constructor
    public Cart(){
        items = new ArrayList<>();
    }
    
    public Cart(List<CartItem> items) {
        this.number = items.size();
        this.items = new ArrayList<>(items); // Defensive copy
    }
    
    // getter
    public int getNumber() {
        return number;
    }
    
    public List<CartItem> getItems(){
        return items;
    }
    
    // setter
    public void setNumber(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Number cannot be negative");
        }
        this.number = number;
    }
    
    public void setItems(List<CartItem> items) {
        if (items == null) {
            throw new IllegalArgumentException("Items list cannot be null");
        }
        this.items = items;
        this.number = items.size();
    }
    
    // other methods
    public void addItem(Product product, int quantity) {
        if (items == null){
            number = 0;
        }
        for(CartItem item : items){
            if(item.getProduct().getProductID() == product.getProductID()){
                item.setQuantity(item.getQuantity() + quantity);
                System.out.println("\nIncreased quantity of " + product.getProductName() + " in cart.");
                return;
            }
        }

        // Increment the item number before adding the item to the cart
        number++; // Increment the number for the new item
        items.add(new CartItem(number, product, quantity));
        System.out.println("\nProduct (" + product.getProductName() + ") added to cart!");
    }
    
    public void addItemsSilently(Product product, int quantity) {
        if (items == null){
            number = 0;
        }
        for(CartItem item : items){
            if(item.getProduct().getProductID() == product.getProductID()){
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }

        // Increment the item number before adding the item to the cart
        number++; // Increment the number for the new item
        items.add(new CartItem(number, product, quantity));
    }
    
    // Remove item from cart
    public void removeItem(int itemNumber) {
        // Search for the item with the given itemNumber
        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            if (item.getNumber() == itemNumber) {
                items.remove(i);
                System.out.println(item.getProduct().getProductName() + " removed from cart.");

                // After removing the item, adjust the item numbers for the rest of the items
                for (int j = i; j < items.size(); j++) {
                    CartItem adjustedItem = items.get(j);
                    adjustedItem.setNumber(adjustedItem.getNumber() - 1); // Decrease the number by 1
                }
                number--;
                return;
            }
        }
        // If no matching itemNumber is found
        System.out.println("Product not found in cart.");
    }
    
    // View cart contents
    public void viewCart(){
        if(isEmptyCart())
            System.out.println("Your cart is empty.");
        else {
            System.out.println("Items in your cart:");
            DisplayEffect.drawLine();
            System.out.println("");
            
            for(CartItem item : items) {
                System.out.println(item.getNumber() + ". " + item);
            }
            System.out.println("");
            DisplayEffect.drawLine();
            System.out.println("Total: RM " + String.format("%.2f", getTotal()) + "\n");
        }
    }
    
    public double getTotal(){
        double total = 0.0;
        for (CartItem item : items)
            total += item.calculateSubtotal();
        
        return total;
    }
    
    public int getItemCount() {
        return items.size();
    }
    
    public void clearCart(){
        number = 0;
        items.clear();
        System.out.println("\nAll item removed from cart.");
    }
    
    public boolean isEmptyCart(){
        return items.isEmpty();
    }
    
    @Override
    public String toString() {
        return "Total: RM " + String.format("%.2f", getTotal());
    }
}