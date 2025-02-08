import java.util.*;

public class CargoManagementSystem {

    Cargo cargo = new Cargo();
    public void insert(Package pkg) {
        cargo.insert(pkg);
    }

    public void remove(String id) {
        cargo.remove(id);
    }

    public Map<String, Double> getTotalCosts() {
        return cargo.getTotalCosts();
    }

    public List<Package> getPackages() {
        return cargo.getPackages();
    }

    enum PackageType {
        STANDARD(0.5),
        HAZARDOUS(0.75),
        FRAGILE(0.625);
        private final double rateMultiplier;
        PackageType(double rateMultiplier) {
            this.rateMultiplier = rateMultiplier;
        }
        public double getRateMultiplier() {
            return rateMultiplier;
        }
    }

//    interface IPackage {
//        String getId();
//        void setId(int id);
//        double getWeight();
//        void setWeight(double weight);
//        double getHeight();
//        void setHeight(double height);
//        double getWidth();
//        void setWidth(double width);
//        double getLength();
//        void setLength(double length);
//        String getType();
//        void setType(String type);
//        double getDistance();
//        void setDistance(double distance);
//    }

    interface IShipment {
        void insert(Package pkg);
        void remove(String id);
        Map<String, Double> getTotalCosts();
        List<Package> getPackages();
    }

    public static class Package {
        String id;
        double weight;
        double height;
        double width;
        double length;
        double volume;
        PackageType type;
        double distance;

        public Package(double weight, double height, double width, double length, PackageType type, double distance) {
            this.id = UUID.randomUUID().toString();
            this.weight = weight;
            this.height = height;
            this.width = width;
            this.length = length;
            this.volume = height * width * length;
            this.type = type;
            this.distance = distance;
        }
    }

    public static class Cargo implements IShipment {

        private final List<Package> packages;

        private final IChargeCalculator transportChargeCalculator = new TransportChargeCalculator();
        private final IChargeCalculator serviceChargeCalculator = new ServiceChargeCalculator();

        public Cargo() {
            this.packages = new ArrayList<>();
        }

        @Override
        public void insert(Package pkg) {
            packages.add(pkg);
        }

        @Override
        public void remove(String id) {
            packages.removeIf(pkg -> Objects.equals(pkg.id, id));
        }

        @Override
        public Map<String, Double> getTotalCosts() {
            Map<String, Double> totalCosts = new HashMap<>();
            packages.forEach(pkg -> {
                double transportCharges = transportChargeCalculator.calculateCost(pkg);
                double serviceCharges = serviceChargeCalculator.calculateCost(pkg);
                totalCosts.put(pkg.type.toString(), transportCharges + serviceCharges);
            });
            return totalCosts;
        }

        @Override
        public List<Package> getPackages() {
            return packages;
        }
    }


    public interface IChargeCalculator {
        double calculateCost(Package pkg);
    }

    static public class TransportChargeCalculator implements IChargeCalculator {
        @Override
        public double calculateCost(Package pkg) {
            return pkg.volume * pkg.type.getRateMultiplier();
        }
    }

    static public class ServiceChargeCalculator implements IChargeCalculator {
        @Override
        public double calculateCost(Package pkg) {
            return (pkg.weight + pkg.distance) * pkg.type.getRateMultiplier();
        }
    }
}
