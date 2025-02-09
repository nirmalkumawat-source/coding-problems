
import java.time.LocalDateTime;
import java.util.*;

public class ParkingLotService {

    enum ParkingSpotType {
        SMALL,
        MEDIUM,
        LARGE
    }

    public static class ParkingSpot {
        String id;
        String vehicleId;
        Boolean isAvailable;
        ParkingSpotType type;

        public ParkingSpot(ParkingSpotType type) {
            this.id = UUID.randomUUID().toString();
            this.type = type;
            this.isAvailable = true;
        }

        public void park(Vehicle vehicle) {
            if (this.isAvailable) {
                this.vehicleId = vehicle.id;
                this.isAvailable = false;
                return;
            }
            throw new IllegalStateException("Parking spot is not available!");
        }

        public String release() {
            if (!isAvailable) {
                String id = vehicleId;
                isAvailable = true;
                vehicleId = null;
                return id;
            }
            return null;
        }
    }

    public static class Ticket {
        String id;
        String vehicleId;
        String parkingSpotId;
        LocalDateTime issueTime;
        Double cost;
        String paymentStatus; // Pending / Paid.

        public Ticket(String vehicleId, String parkingSpotId) {
            id = UUID.randomUUID().toString();
            issueTime = LocalDateTime.now();
            this.vehicleId = vehicleId;
            this.parkingSpotId = parkingSpotId;
            this.paymentStatus = "Pending";
        }

        public void setCost(Double cost) {
            this.cost = cost;
            this.paymentStatus = "Paid";
        }
    }

    public static class Vehicle {
        String id;
        String licensePlate;
        VehicleType type;
        LocalDateTime entryTime;
        LocalDateTime exitTime;
        String ticketID;

        public Vehicle(String licensePlate, VehicleType type) {
            this.id = UUID.randomUUID().toString();
            this.licensePlate = licensePlate;
            this.type = type;
            this.entryTime = LocalDateTime.now();
        }
    }

    enum VehicleType {
        CAR,
        TRUCK,
        MOTORCYCLE
    }

    private List<ParkingSpot> parkingSpots;
    private List<Ticket> tickets;
    private List<Vehicle> vehicles;

    private final static Map<VehicleType, ParkingSpotType> vehicleTypeToSpotSize = Map.of(
            VehicleType.CAR, ParkingSpotType.MEDIUM,
            VehicleType.TRUCK, ParkingSpotType.LARGE,
            VehicleType.MOTORCYCLE, ParkingSpotType.SMALL
    );

    public ParkingLotService(int capacity) {
        this.parkingSpots = new ArrayList<>(capacity);
        this.tickets = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    public void addParkingSpot(ParkingSpot parkingSpot) {
        parkingSpots.add(parkingSpot);
    }

    public void removeParkingSpot(String parkingSpotId) {
        parkingSpots.removeIf(spot -> spot.id.equals(parkingSpotId));
    }

    public Ticket parkVehicle(String licensePlate, VehicleType type) {
        Optional<ParkingSpot> availableSpot = findAvailableSpot(type);
        if (availableSpot.isPresent()) {
            Vehicle vehicle = new Vehicle(licensePlate, type);

            Ticket ticket = new Ticket(vehicle.id, availableSpot.get().id);
            vehicle.ticketID = ticket.id;

            availableSpot.get().park(vehicle);

            tickets.add(ticket);
            vehicles.add(vehicle);
            return ticket;
        }

        return null;
    }

    public Vehicle removeVehicle(String vehicleId) {

        Vehicle vehicle = vehicles.stream().filter(k -> k.id.equals(vehicleId)).findFirst().orElse(null);
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle with ID doesn't exist!");
        }
        String ticketId = vehicle.ticketID;
        Ticket ticket = tickets.stream().filter(k -> k.id.equals(ticketId)).findFirst().orElse(null);
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket with ID doesn't exist!");
        }

        double fee = calculateParkingFee(vehicle.entryTime, vehicle.type);
        vehicle.exitTime = LocalDateTime.now();
        ticket.setCost(fee);

        ParkingSpot spot = findSpotByVehicle(vehicle);
        spot.release();
        return vehicle;
    }

    private double calculateParkingFee(LocalDateTime entryTime, VehicleType type) {
        return 10.0f;
    }

    private Optional<ParkingSpot> findAvailableSpot(VehicleType type) {
        ParkingSpotType spotType = vehicleTypeToSpotSize.get(type);
        return parkingSpots.stream()
                .filter(k -> k.isAvailable && k.type == spotType)
                .findFirst();
    }

    private ParkingSpot findSpotByVehicle(Vehicle vehicle) {
        return parkingSpots.stream()
                .filter(k -> k.vehicleId.equals(vehicle.id))
                .findFirst().orElse(null);
    }
}

/*
CREATE TABLE parking_spots (
    spot_id SERIAL PRIMARY KEY,
    is_occupied BOOLEAN DEFAULT FALSE,
    vehicle_id INTEGER REFERENCES vehicles(vehicle_id)
);

CREATE TABLE vehicles (
    vehicle_id SERIAL PRIMARY KEY,
    license_plate VARCHAR(20) NOT NULL,
    vehicle_type VARCHAR(20) NOT NULL,
    entry_time TIMESTAMP NOT NULL,
    exit_time TIMESTAMP,
    ticket_id INTEGER REFERENCES tickets(ticket_id)
);

CREATE TABLE tickets (
    ticket_id SERIAL PRIMARY KEY,
    issue_time TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL, // ACTIVE, COMPLETED
    amount_paid DECIMAL(10,2),
    payment_status VARCHAR(20) // PENDING, PAID
);



 */
