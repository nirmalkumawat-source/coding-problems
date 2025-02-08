import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

public class LoyalCustomers {
    public LoyalCustomers(){}

    // https://carloarg02.medium.com/my-favorite-coding-question-to-give-candidates-17ea4758880c

    public Set<String> findLoyalCustomers(List<CustomerLog> log1, List<CustomerLog> log2) {

        Map<String, Set<String>> day1Logs = parseLog(log1);

        Set<String> loyalCustomers = new HashSet<>();
        for (CustomerLog log: log2) {
            if (day1Logs.containsKey(log.cId)) {
                if (day1Logs.get(log.cId).size() >= 2 ||
                    !day1Logs.get(log.cId).contains(log.pId)
                ) {
                    loyalCustomers.add(log.cId);

                    day1Logs.remove(log.cId);
                }
            }
        }
        return loyalCustomers;
    }

    private Map<String, Set<String>> parseLog(List<CustomerLog> logs) {
        Map<String, Set<String>> dayLogs = new HashMap<>();
        for (CustomerLog log : logs) {
            dayLogs.computeIfAbsent(log.cId, k -> new HashSet<>()).add(log.pId);
        }
        return dayLogs;
    }

    public static class CustomerLog {
        int ts;
        String pId;
        String cId;
        public CustomerLog(int ts, String pId, String cId) {
            this.ts = ts;
            this.pId = pId;
            this.cId = cId;
        }
    }

    /*


    import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        List<String> logfile1 = new ArrayList<String>();
        List<String> logfile2 = new ArrayList<String>();

        String pageVisit = br.readLine();
        while (pageVisit != null) {
            if (pageVisit.startsWith("logfile_1")) {
                logfile1.add(pageVisit.replace("logfile_1,", ""));
            } else if (pageVisit.startsWith("logfile_2")) {
                logfile2.add(pageVisit.replace("logfile_2,", ""));
            }
            pageVisit = br.readLine();
        }

        // Write your code here

        var day1ParsedLogs = parseLog(logfile1);
        var day1LogsMap = loadLog(day1ParsedLogs);
        Set<String> loyalCustomers = findLoyalCustomers(day1LogsMap, logfile2);

        // Use PrintWiter out to print results, expected output... list of loyal customerid delimited by line separator.
        for (String cId: loyalCustomers) {
            System.out.println(cId);
        }

      //  loyalCustomers.forEach(s -> System.out.println(s));

        out.flush();
        out.close();
    }

    private static Set<String> findLoyalCustomers(Map<String, Set<String>> day1LogsMap, List<String> day2UnparsedLogs) {

        if (day1LogsMap.isEmpty() || day2UnparsedLogs.isEmpty()) {
            return new HashSet<>();
        }

        TreeSet<String> loyalCustomers = new TreeSet<>();

        for (String day2Log: day2UnparsedLogs) {
            String customer = day2Log.split(",")[1];
            String product = day2Log.split(",")[2];

            if (day1LogsMap.containsKey(customer)) {
                Set<String> products = day1LogsMap.get(customer);

                if (products.size() >= 2 || !products.contains(product)) {
                    loyalCustomers.add(customer);

                    day1LogsMap.remove(customer);
                }
            }
        }
        return loyalCustomers;
    }

    private static List<FileLog> parseLog(List<String> logfile) {
        List<FileLog> parsedLogs = new ArrayList<>();

        if (logfile.isEmpty()) {
            return parsedLogs;
        }

        for (String log: logfile) {
            String[] tokens = log.split(",");
            FileLog fileLog = new FileLog(tokens[0], tokens[1], tokens[2]);
            parsedLogs.add(fileLog);
        }
        return parsedLogs;
    }

    private static Map<String, Set<String>> loadLog(List<FileLog> parsedLogs) {
        Map<String, Set<String>> dailyLog = new HashMap<>();
        if (parsedLogs.isEmpty()) {
            return dailyLog;
        }

        for (FileLog log: parsedLogs) {
            dailyLog.computeIfAbsent(log.cId, k -> new HashSet<>()).add(log.pId);
        }
        return dailyLog;
    }

    private static class FileLog {
        String ts;
        String cId;
        String pId;
        public FileLog(String ts, String cId, String pId) {
            this.ts = ts;
            this.cId = cId;
            this.pId = pId;
        }
    }
}
     */
}
