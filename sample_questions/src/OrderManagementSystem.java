import java.util.HashMap;
import java.util.Map;

public class OrderManagementSystem {

    /*
    Problem Statement:
Design an Order Management System for an e-commerce platform with the following features:

    Add items to the cart → addItemToCart(userId, itemId, quantity)
    Remove items from the cart → removeItemFromCart(userId, itemId)
    Calculate the total price → calculateTotal(userId)
    Apply tax (10%) and discounts (5% off if total > $100)
    Checkout the cart → checkout(userId)

Constraints:
    The system should handle multiple users independently.
    Invalid item IDs should throw an error.
    Orders above $100 should get a 5% discount.
    Prices are predefined in the system.
     */

    final private Map<String, ShoppingCart> carts = new HashMap<>();
    private final static double TAX = 0.10;

    public void addItemToCart(String userId, String itemId, int quantity) {
        carts.computeIfAbsent(userId, k -> new ShoppingCart()).addItem(itemId, quantity);
    }

    public void removeItemFromCart(String userId, String itemId) {
        getUserCart(userId).removeItem(itemId);
    }

    public double calculateTotal(String userId) {
        return getUserCart(userId).calculateTotal();
    }

    public void checkout(String userId) {
        double total = getUserCart(userId).calculateTotal();
        total = applyTax(total);

        System.out.println("Processing Payment for cart: " + userId + " with total: " + total);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            System.out.println("Payment failed for cart: " + userId);
        }
        carts.remove(userId);
    }

    private ShoppingCart getUserCart(String userId) {
        if (!carts.containsKey(userId)) {
            throw new IllegalArgumentException("No cart found for userId: " + userId);
        }
        return carts.get(userId);
    }

    private double applyTax(double total) {
        return total * (1 + TAX);
    }

    public static class ShoppingCart {
        final private Map<String, Integer> items = new HashMap<>();
        final static private Map<String, Double> itemsPriceMap = new HashMap<>();
        private final static double DISCOUNT = 0.05;

        static {
            itemsPriceMap.put("item1", 10.0);
            itemsPriceMap.put("item2", 20.0);
            itemsPriceMap.put("item3", 30.0);
        }

        public ShoppingCart() {}

        public void addItem(String itemId, int quantity) {
            items.put(itemId, items.getOrDefault(itemId, 0) + quantity);
        }

        public void removeItem(String itemId) {
            if (!items.containsKey(itemId)) {
                throw new IllegalArgumentException("Item not in cart to remove: " + itemId);
            }
            items.remove(itemId);
        }

        public double calculateTotal() {
            if (items.isEmpty()) {
                return 0.0f;
            }
            double total = items.keySet().stream().mapToDouble(itemId -> getItemPrice(itemId) * items.get(itemId)).sum();
            return applyDiscount(total);
        }

        private double getItemPrice(String itemId) {
            // Assuming predefined prices in the system
            Double price = itemsPriceMap.getOrDefault(itemId, null);
            if (price == null) {
                throw new IllegalArgumentException("Invalid item ID: " + itemId);
            }
            return price;
        }

        private double applyDiscount(double total) {
            return total > 100.0 ? total * (1 - DISCOUNT) : total;
        }

    }

}
