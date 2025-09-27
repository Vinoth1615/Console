import java.util.*;

class Car {
    int id;
    String name;
    String brand;
    String plateNumber;
    double pricePerDay;
    boolean isAvailable;

    public Car(int id, String name, String brand, String plateNumber, double pricePerDay) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.plateNumber = plateNumber;
        this.pricePerDay = pricePerDay;
        this.isAvailable = true;
    }

    public void displayCar() {
        System.out.println("ID: " + id + ", Name: " + name + ", Brand: " + brand +
                           ", Plate: " + plateNumber + ", Price/Day: ₹" + pricePerDay +
                           ", Available: " + isAvailable);
    }

    public String getPlateNumber() {
        return plateNumber;
    }
}

class Customer {
    int id;
    String name;
    int age;
    String licenseNumber;
    Car rentedCar;
    int rentDays;

    public Customer(int id, String name, int age, String licenseNumber) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.licenseNumber = licenseNumber;
        this.rentedCar = null;
        this.rentDays = 0;
    }
}

public class CarRentalSystem {
    static Scanner sc = new Scanner(System.in);
    static List<Car> cars = new ArrayList<>();
    static List<Customer> customers = new ArrayList<>();
    static int carIdCounter = 1;
    static int customerIdCounter = 1;
    static double totalRevenue = 0;

    public static void main(String[] args) {
        addDefaultCars(); 

        int choice;
        do {
            showMenu();
            choice = sc.nextInt();
            sc.nextLine(); 
            switch (choice) {
                case 1 -> addCar();
                case 2 -> viewAllCars();
                case 3 -> viewAvailableCars();
                case 4 -> removeCar();
                case 5 -> addCustomer();
                case 6 -> viewAllCustomers();
                case 7 -> rentCar();
                case 8 -> returnCar();
                case 9 -> viewTotalRevenue();
                case 0 -> System.out.println("Exiting system. Thank you!");
                default -> System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 0);
    }

    static void addDefaultCars() {
        cars.add(new Car(carIdCounter++, "Cullinan", "Rolls-Royce", "TN01AB1234", 50));
        cars.add(new Car(carIdCounter++, "Bugatti La Voiture Noire", "Bugatti", "TN01AB5678", 60));
        cars.add(new Car(carIdCounter++, "Ferrari Purosangue", "Ferrari", "TN01AB9999", 120));
        cars.add(new Car(carIdCounter++, "Swift", "Suzuki", "TN01AB4321", 40));
        cars.add(new Car(carIdCounter++, "M4", "BMW", "TN15DV1507", 180));
    }

    static void showMenu() {
        System.out.println("************************************"); 
        System.out.println("** WELCOME TO  CAR RENTAL SYSTEM **"); 
        System.out.println("************************************"); 
        System.out.println("1. Add Car");
        System.out.println("2. View All Cars");
        System.out.println("3. View Available Cars");
        System.out.println("4. Remove Car");
        System.out.println("5. Add Customer");
        System.out.println("6. View All Customers");
        System.out.println("7. Rent Car");
        System.out.println("8. Return Car");
        System.out.println("9. View Total Revenue");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    static void addCar() {
        System.out.print("Enter Car Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Car Brand: ");
        String brand = sc.nextLine();
        System.out.print("Enter License Plate Number: ");
        String plate = sc.nextLine();
        System.out.print("Enter Price per Day: ");
        double price = sc.nextDouble();
        sc.nextLine();

        Car car = new Car(carIdCounter++, name, brand, plate, price);
        cars.add(car);
        System.out.println("Car added successfully!");
    }

    static void viewAllCars() {
        if (cars.isEmpty()) 
            System.out.println("No cars available.");
        else {
            for (Car car : cars) {
                car.displayCar();
            }
        }
    }

    static void viewAvailableCars() {
        boolean found = false;
        for (Car car : cars) {
            if (car.isAvailable) {
                car.displayCar();
                found = true;
            }
        }
        if (!found) System.out.println("No cars available for rent.");
    }

    static void removeCar() {
        System.out.print("Enter License Plate Number to remove: ");
        String plate = sc.nextLine();
        boolean found = false;

        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getPlateNumber().equalsIgnoreCase(plate)) {
                cars.remove(i);
                found = true;
                break; 
            }
        }

        if (found) System.out.println("Car with plate " + plate + " was removed.");
        else System.out.println("Car with plate " + plate + " not found.");
    }

    static void addCustomer() {
        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter License Number: ");
        String license = sc.nextLine();

        Customer customer = new Customer(customerIdCounter++, name, age, license);
        customers.add(customer);
        System.out.println("Customer added successfully!");
    }

    static void viewAllCustomers() {
        if (customers.isEmpty()) System.out.println("No customers available.");
        else {
            for (Customer c : customers) {
                System.out.println("ID: " + c.id + ", Name: " + c.name + ", Age: " + c.age +
                                   ", License: " + c.licenseNumber +
                                   ", Rented Car: " + (c.rentedCar == null ? "None" : c.rentedCar.name));
            }
        }
    }

    static void rentCar() {
        System.out.print("Enter Customer License Number: ");
        String license = sc.nextLine();
        Customer customer = null;
        for (Customer c : customers) {
            if (c.licenseNumber.equalsIgnoreCase(license)) {
                customer = c;
                break; 
            }
        }

        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }
        if (customer.rentedCar != null) {
            System.out.println("Customer already rented a car!");
            return;
        }

        viewAvailableCars(); 
        System.out.print("Enter Car ID to rent: ");
        int carId = sc.nextInt();
        sc.nextLine();

        Car car = null;
        for (Car c : cars) {
            if (c.id == carId && c.isAvailable) {
                car = c;
                break; 
            }
        }

        if (car == null) {
            System.out.println("Car not available!");
            return;
        }

        System.out.print("Enter number of days: ");
        int days = sc.nextInt();
        sc.nextLine();

        customer.rentedCar = car;
        customer.rentDays = days;
        car.isAvailable = false;
        System.out.println("Car rented successfully!");
    }

    static void returnCar() {
        System.out.print("Enter Customer License Number: ");
        String license = sc.nextLine();
        Customer customer = null;
        for (Customer c : customers) {
            if (c.licenseNumber.equalsIgnoreCase(license)) {
                customer = c;
                break; 
            }
        }

        if (customer == null || customer.rentedCar == null) {
            System.out.println("No rented car found for this customer!");
            return;
        }

        double totalCost = customer.rentedCar.pricePerDay * customer.rentDays;
        customer.rentedCar.isAvailable = true;
        totalRevenue += totalCost; 
        System.out.println("Car returned successfully! Total cost: ₹" + totalCost);

        customer.rentedCar = null;
        customer.rentDays = 0;
    }

    static void viewTotalRevenue() {
        System.out.println("Total Revenue Earned: ₹" + totalRevenue);
    }
}
