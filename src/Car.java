/**
 * SYLLABUS: LAB 2 – Inheritance & Polymorphism
 *
 * Car extends the abstract Vehicle class using the 'extends' keyword.
 * The constructor uses 'super' to call the parent class constructor.
 * displayDetails() is overridden to provide Car-specific information.
 */
public class Car extends Vehicle {

    private int numberOfSeats;

    /**
     * Car constructor – demonstrates use of super keyword.
     *
     * @param vehicleId      car ID
     * @param vehicleName    car model name
     * @param rentPerDay     daily rent
     * @param numberOfSeats  seating capacity
     */
    public Car(String vehicleId, String vehicleName, double rentPerDay, int numberOfSeats) {
        super(vehicleId, vehicleName, rentPerDay); // super calls parent constructor
        this.numberOfSeats = numberOfSeats;
    }

    /**
     * Calculates base rent for a car (standard rate).
     *
     * @param days rental days
     * @return total base rent
     */
    @Override
    public double calculateBaseRent(int days) {
        return rentPerDay * days;
    }

    /**
     * Overrides abstract method from Vehicle – polymorphism in action.
     *
     * @return car details as String
     */
    @Override
    public String displayDetails() {
        return "Car ID: " + vehicleId
                + " | Model: " + vehicleName
                + " | Seats: " + numberOfSeats
                + " | Rent/Day: Rs. " + rentPerDay;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }
}
