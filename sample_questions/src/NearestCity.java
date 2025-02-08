import java.util.*;

public class NearestCity {

    // https://leetcode.com/discuss/interview-question/949904/amazon-oa2-nearest-city

    Map<Integer, TreeSet<City>> xToCity;
    Map<Integer, TreeSet<City>> yToCity;
    Map<String, City> cityRepo;
    public NearestCity(List<City> cities) {

        // x -> [c1, c3, c2] // sort it based on y.
        // y -> [c2, c3, c1] // sort if based on x.
        xToCity = new HashMap<>();
        yToCity = new HashMap<>();
        cityRepo = new HashMap<>();
        for (City city: cities) {
            cityRepo.put(city.name, city);
            xToCity.computeIfAbsent(city.x, k -> new TreeSet<>((c1, c2) -> {
                if (c1.y == c2.y) {
                    return c1.name.compareTo(c2.name);
                }
                return c1.y - c2.y;
            })).add(city);
            yToCity.computeIfAbsent(city.y, k -> new TreeSet<>(Comparator.comparingInt(c -> c.x))).add(city);
        }

//        System.out.println("xToCity:");
//        printMapping(xToCity);
//        System.out.println("yToCity:");
//        printMapping(yToCity);
    }

    public List<String> findNearestCities(List<String> queries) {
        List<String> result = new ArrayList<>();

        for (String cityName: queries) {
            City city = cityRepo.getOrDefault(cityName, null);
            if (city == null) {
                result.add("None");
                continue;
            }

            var xCities = xToCity.get(city.x);
            xCities.remove(city);
            City xNearest = null;
            if (!xCities.isEmpty()) {
                xNearest = xCities.ceiling(city);
                xNearest = xNearest == null ? xCities.floor(city) : xNearest;
            }

            var yCities = yToCity.get(city.y);
            yCities.remove(city);
            City yNearest = null;
            if (!yCities.isEmpty()) {
                yNearest = yCities.ceiling(city);
                yNearest = yNearest == null ? yCities.floor(city) : yNearest;
            }

            if (xNearest == null && yNearest == null) {
                result.add("None");
            } else if (xNearest == null) {
                result.add(yNearest.name);
            } else if (yNearest == null) {
                result.add(xNearest.name);
            } else {
                int xDist = Math.abs(xNearest.y - city.y);
                int yDist = Math.abs(yNearest.x - city.x);
                if (xDist < yDist) {
                    result.add(xNearest.name);
                } else if (yDist < xDist) {
                    result.add(yNearest.name);
                } else {
                    result.add(xNearest.name.compareTo(yNearest.name) < 0 ? xNearest.name : yNearest.name);
                }
            }
        }
        return result;
    }

    private void printMapping( Map<Integer, TreeSet<City>> coordToCity) {
        for (Integer coord: coordToCity.keySet()) {
            TreeSet<City> cities = coordToCity.get(coord);
            cities.forEach(city -> System.out.print(city.name + ", "));
            System.out.println();
        }
        System.out.println();
    }

    public static class City {
        String name;
        int x;
        int y;

        public City(String name, int x, int y) {
            this.name = name;
            this.x = x;
            this.y = y;
        }
    }
}
