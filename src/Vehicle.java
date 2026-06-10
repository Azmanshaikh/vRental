/**
 * SYLLABUS: LAB 2 – Inheritance & Polymorphism
 * SYLLABUS: LAB 6 – Abstraction
 *
 * Vehicle is an abstract parent class. It cannot be instantiated directly.
 * Child classes Car and Bike extend Vehicle and provide specific implementations.
 *
 * Polymorphism example in GUI:
 *   Vehicle vehicle;
 *   vehicle = new Car(...);
 *   vehicle = new Bike(...);
 */
public abstract class Vehicle {

    // Encapsulated fields – common to all vehicle types
    protected String vehicleId;
    protected String vehicleName;
    protected double rentPerDay;

    /**
     * Constructor to initialize common vehicle fields.
     * Child classes call this using super().
     *
     * @param vehicleId   unique identifier for the vehicle
     * @param vehicleName name/model of the vehicle
     * @param rentPerDay  daily rental charge
     */
    public Vehicle(String vehicleId, String vehicleName, double rentPerDay) {
        this.vehicleId = vehicleId;
        this.vehicleName = vehicleName;
        this.rentPerDay = rentPerDay;
    }

    /**
     * Abstract method – each vehicle type calculates rent differently.
     *
     * @param days number of rental days
     * @return base rental amount
     */
    public abstract double calculateBaseRent(int days);

    /**
     * Abstract method – each vehicle displays its own details.
     *
     * @return formatted vehicle details
     */
    public abstract String displayDetails();

    // Getter methods for accessing protected fields
    public String getVehicleId() {
        return vehicleId;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public double getRentPerDay() {
        return rentPerDay;
    }
}
