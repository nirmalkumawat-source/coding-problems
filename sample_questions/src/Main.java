import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        System.out.println("Start Execution!");

//        // Test cases for - Find Loyal Customers
//        System.out.println("Executing test cases for - Find Loyal Customers");
//        findLoyalCustomers_test1();
//        findLoyalCustomers_test2();
//        findLoyalCustomers_test3();
//
//        // Test cases for - Find Nearest Cities
//        System.out.println("Executing test cases for - Find Nearest Cities");
//        findNearestCities_test1();


//        System.out.println("Executing test cases for - Order Management System");
//        orderManagementSystem_test1();
//        orderManagementSystem_test2_invalidItem();
//        orderManagementSystem_test2_invalidUser();
//        orderManagementSystem_test_applyDiscount();

        System.out.println("Executing test cases for - Cargo Management System");
        cargoManagementSystem_test_totalCost();

        System.out.println("End Execution!");
    }

    public static void findLoyalCustomers_test1() {

        List<LoyalCustomers.CustomerLog> log1 = List.of(
                new LoyalCustomers.CustomerLog(1, "p1", "c1"),
                new LoyalCustomers.CustomerLog(2, "p2", "c2"),
                new LoyalCustomers.CustomerLog(3, "p3", "c3")
        );

        List<LoyalCustomers.CustomerLog> log2 = List.of(
                new LoyalCustomers.CustomerLog(2, "p2", "c1"),
                new LoyalCustomers.CustomerLog(3, "p3", "c2"),
                new LoyalCustomers.CustomerLog(4, "p3", "c3")
        );

        LoyalCustomers solution = new LoyalCustomers();
        Set<String> loyalCustomers = solution.findLoyalCustomers(log1, log2);

        System.out.println(loyalCustomers);
    }

    public static void findLoyalCustomers_test2() {
        List<LoyalCustomers.CustomerLog> log1 = List.of(
                new LoyalCustomers.CustomerLog(1, "p1", "c1"),
                new LoyalCustomers.CustomerLog(2, "p2", "c2"),
                new LoyalCustomers.CustomerLog(3, "p3", "c3")
        );

        List<LoyalCustomers.CustomerLog> log2 = Collections.emptyList();

        LoyalCustomers solution = new LoyalCustomers();
        Set<String> loyalCustomers = solution.findLoyalCustomers(log1, log2);

        System.out.println(loyalCustomers);
    }

    public static void findLoyalCustomers_test3() {
        String timestamp = "2025-01-11     12:34:32";
        String[] parts = timestamp.split("\\s+");
        String date = parts[0];
        String time = parts[1];

        System.out.println("Date: " + date);
        System.out.println("Time: " + time);

        List<LoyalCustomers.CustomerLog> log1 = Collections.emptyList();
        List<LoyalCustomers.CustomerLog> log2 = Collections.emptyList();

        LoyalCustomers solution = new LoyalCustomers();
        Set<String> loyalCustomers = solution.findLoyalCustomers(log1, log2);

        System.out.println(loyalCustomers);
    }

    public static void findNearestCities_test1() {

        var cities = List.of(
                new NearestCity.City("c1", 30,30),
                new NearestCity.City("c2", 20,20),
                new NearestCity.City("c3", 10,30),
                new NearestCity.City("c4", 20,40),
                new NearestCity.City("c5", 10,30)
        );

        var nearestCitiesObj = new NearestCity(cities);
        var queryCities = List.of("c1", "c2", "c3");
        var result = nearestCitiesObj.findNearestCities(queryCities);

        System.out.println(result);
    }


    public static void orderManagementSystem_test1() {
        OrderManagementSystem oms = new OrderManagementSystem();

        oms.addItemToCart("user1", "item1", 2);
        oms.addItemToCart("user2", "item2", 2);
        oms.addItemToCart("user1", "item3", 2);

        oms.removeItemFromCart("user1", "item1");
        oms.calculateTotal("user1"); // prints 60.0 before tax.
        oms.checkout("user1"); // prints 66.0
    }

    public static void orderManagementSystem_test2_invalidItem() {
        OrderManagementSystem oms = new OrderManagementSystem();

        oms.addItemToCart("user1", "item1", 2);
        oms.addItemToCart("user2", "item2", 2);
        oms.addItemToCart("user1", "item3", 2);

        oms.removeItemFromCart("user1", "item1");
        oms.removeItemFromCart("user1", "item3");
        oms.checkout("user1"); // returns 0.0; no items in the cart
    }

    public static void orderManagementSystem_test2_invalidUser() {
        OrderManagementSystem oms = new OrderManagementSystem();

        oms.addItemToCart("user1", "item1", 2);
        oms.addItemToCart("user2", "item2", 2);
        oms.addItemToCart("user1", "item3", 2);

        oms.removeItemFromCart("user1", "item1");

        try {
            oms.checkout("user5"); // throws exception.
        } catch (IllegalArgumentException e) {
            System.out.println("Expected exception: " + e.getMessage());
        }
    }

    public static void orderManagementSystem_test_applyDiscount() {
        OrderManagementSystem oms = new OrderManagementSystem();

        oms.addItemToCart("user1", "item1", 20);
        oms.addItemToCart("user2", "item2", 20);
        oms.addItemToCart("user1", "item3", 20);

        oms.removeItemFromCart("user1", "item1");
        oms.checkout("user1"); // returns 627 => 600 - 5% discount = 570 * 10% tax = 627
    }

    public static void cargoManagementSystem_test_totalCost() {
        CargoManagementSystem cms = new CargoManagementSystem();

        cms.insert(new CargoManagementSystem.Package( 10, 10, 10, 10,  CargoManagementSystem.PackageType.HAZARDOUS, 10));
        cms.insert(new CargoManagementSystem.Package( 10, 10, 10, 10,  CargoManagementSystem.PackageType.STANDARD, 11));
        cms.insert(new CargoManagementSystem.Package( 10, 10, 10, 10,  CargoManagementSystem.PackageType.FRAGILE, 12));

        cms.getTotalCosts().forEach((k, v) -> {
            System.out.println("Package Type: " + k + ", Total Cost: " + v);
        });
    }
}