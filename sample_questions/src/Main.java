import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        System.out.println("Start Execution!");

        // Test cases for - Find Loyal Customers
        System.out.println("Executing test cases for - Find Loyal Customers");
        findLoyalCustomers_test1();
        findLoyalCustomers_test2();
        findLoyalCustomers_test3();

        // Test cases for - Find Nearest Cities
        System.out.println("Executing test cases for - Find Nearest Cities");
        findNearestCities_test1();
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
}