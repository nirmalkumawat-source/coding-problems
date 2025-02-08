import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

// NOTE: This is not a runnable code.
// This is LLD for a bike rental shop.
// The problem-statement is available at - https://leetcode.com/discuss/interview-question/4503513/Wayfair-or-L2-L4-or-Virual-Onsite-or-LLD-or-Bike-Rental-Shop
public class BikeRentalShop {

    public static abstract class Product {
        String id;
        Boolean isAvailable;
        Double rentalRate;
        ProductType type;

        public Product(ProductType type, Double rentalRate) {
            this.isAvailable = true;
            this.type = type;
            this.rentalRate = rentalRate;
        }
    }

    public static class Bike extends Product {
        BikeSize size;

        public Bike(Double rentalRate, BikeSize size) {
            super(ProductType.BIKE, rentalRate);
            this.size = size;
        }

        public BikeSize getSize() {
            return size;
        }
    }

    public static class Scooter extends Product {
        MotorStyle style;

        public Scooter(MotorStyle style, Double rentalRate) {
            super(ProductType.SCOOTER, rentalRate);
            this.style = style;
        }

        public MotorStyle getStyle() {
            return style;
        }
    }
    public enum BikeSize {
        SMALL,
        MEDIUM,
        LARGE
    }

    public enum MotorStyle {
        ELECTRIC,
        GAS
    }

    public enum ProductType {
        BIKE,
        SCOOTER
    }

    public static class Customer {
        String id;
        String name;
        Double balance;
        List<RentalRecord> rentals;
    }

    public static class RentalRecord {
        String id;
        String productId;
        String customerId;
        String startDate;
        String dueDate;
        String endDate;
        Double totalCost;
        Boolean isActive;

        public RentalRecord(String pId, String cId) {
            this.productId = pId;
            this.customerId = cId;
            this.startDate = Instant.now().toString();
            this.dueDate = Instant.now().plus(2, ChronoUnit.DAYS).toString();
            this.endDate = null;
            this.totalCost = 0.0;
            this.isActive = true;
        }
    }

    public static class Charge {
        String id;
        String customerId;
        Double amount;
        String date;
        String description;

        public Charge(String cId, Double amount, String description) {
            this.customerId = cId;
            this.amount = amount;
            this.description = description;
            this.date = Instant.now().toString();
        }
    }

    public interface ProductRepository {
        Product save(Product product);
        void delete(String productId);
        List<Product> getAllAvailable();
        Product findById(String id);
        List<Product> findAllRented();
        List<Product> countByTypeAndSize(ProductType type, BikeSize size);
    }

    public interface CustomerRepository {
        Customer save(Customer customer);
        void delete(String customerId);
        List<Customer> getAll();
        Optional<Customer> findById(String id);
        Double getBalance(String customerId);
    }

    public interface RentalRepository {
        RentalRecord save(RentalRecord rental);
        List<RentalRecord> findOverdueRentals();
        List<RentalRecord> findByCustomer(String customerId);
        Optional<RentalRecord> findById(String rentalId);
        Optional<RentalRecord> findByProductId(String productId);
        List<RentalRecord> findActiveRentals();
    }

    public interface ChargeRepository {
        Charge save(Charge charge);
        List<Charge> findByCustomerId(String customerId);
    }

    public interface RentalService {

        List<Product> getAvailableBikes(BikeSize size);
        List<Product> getAvailableProducts();

        Double getBalance(String customerId);
        List<RentalRecord> getRentedProducts();
        List<RentalRecord> getOverdueRentals();
        List<RentalRecord> getCustomerRentals(String customerId);

        Product addProduct(Product product);
        Customer addCustomer(Customer customer);
        void removeProduct(String productId);
        RentalRecord rentProduct(String productId, String customerId);
        Charge chargeCustomer(String customerId, Double amount, String description);
    }

    public static class RentalServiceImpl implements RentalService {

        private ProductRepository productRepository;
        private CustomerRepository customerRepository;
        private RentalRepository rentalRepository;
        private ChargeRepository chargeRepository;

        public RentalServiceImpl() {
//            this.productRepository = new ProductRepositoryImpl();
//            this.customerRepository = new CustomerRepositoryImpl();
//            this.rentalRepository = new RentalRepositoryImpl();
        }

        @Override
        public List<Product> getAvailableBikes(BikeSize size) {
            return productRepository.countByTypeAndSize(ProductType.BIKE, size);
        }

        @Override
        public List<Product> getAvailableProducts() {
            return productRepository.getAllAvailable();
        }

        @Override
        public Double getBalance(String customerId) {
            return customerRepository.getBalance(customerId);
        }

        @Override
        public List<RentalRecord> getRentedProducts() {
            return rentalRepository.findActiveRentals();
        }

        @Override
        public List<RentalRecord> getOverdueRentals() {
            return rentalRepository.findOverdueRentals();
        }

        @Override
        public List<RentalRecord> getCustomerRentals(String customerId) {
            return rentalRepository.findByCustomer(customerId);
        }

        @Override
        public Product addProduct(Product product) {
            return productRepository.save(product);
        }

        @Override
        public Customer addCustomer(Customer customer) {
            return customerRepository.save(customer);
        }

        @Override
        public void removeProduct(String productId) {
            productRepository.delete(productId);
        }

        @Override
        public RentalRecord rentProduct(String productId, String customerId) {
            return rentalRepository.save(new RentalRecord(productId, customerId));
        }

        @Override
        public Charge chargeCustomer(String customerId, Double amount, String description) {
            return chargeRepository.save(new Charge(customerId, amount, description));
        }
    }

    /*
    PostgreSQL DB Schema:

    erDiagram
    PRODUCTS {
        uuid id PK
        string type
        string size
        string motor_type
        decimal rental_rate
        boolean is_available
        timestamp created_at
        timestamp updated_at
    }

    CUSTOMERS {
        uuid id PK
        string name
        string email
        string phone
        decimal balance
        timestamp created_at
        timestamp updated_at
    }

    RENTAL_RECORDS {
        uuid id PK
        uuid customer_id FK
        uuid product_id FK
        timestamp start_time
        timestamp end_time
        decimal charges
        string status
        timestamp created_at
        timestamp updated_at
    }

    CHARGES {
        uuid id PK
        uuid customer_id FK
        decimal amount
        string description
        timestamp charge_date
        timestamp created_at
    }

    CUSTOMERS ||--o{ RENTAL_RECORDS : has
    PRODUCTS ||--o{ RENTAL_RECORDS : "rented in"
    CUSTOMERS ||--o{ CHARGES : has
     */

    /*
    SQL Queries to create table -

CREATE TABLE products (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    type VARCHAR(50) NOT NULL CHECK (type IN ('BIKE', 'SCOOTER')),
    size VARCHAR(20) CHECK (size IN ('SMALL', 'MEDIUM', 'LARGE')),
    motor_type VARCHAR(20) CHECK (motor_type IN ('ELECTRIC', 'GAS')),
    rental_rate DECIMAL(10,2) NOT NULL,
    is_available BOOLEAN DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE customers (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(50),
    balance DECIMAL(10,2) DEFAULT 0.00,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE rental_records (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_id UUID REFERENCES customers(id),
    product_id UUID REFERENCES products(id),
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP,
    charges DECIMAL(10,2),
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE charges (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_id UUID REFERENCES customers(id),
    amount DECIMAL(10,2) NOT NULL,
    description TEXT,
    charge_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Indexes
CREATE INDEX idx_products_type_size ON products(type, size);
CREATE INDEX idx_rental_records_status ON rental_records(status);
CREATE INDEX idx_rental_records_customer ON rental_records(customer_id);
CREATE INDEX idx_charges_customer ON charges(customer_id);


     */
}
