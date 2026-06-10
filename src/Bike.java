/**
 * SYLLABUS: LAB 2 – Inheritance & Polymorphism
 *
 * Bike is another child class of Vehicle.
 * Demonstrates that multiple classes can inherit from the same parent
 * and each provides its own implementation of abstract methods.
 */
public class Bike extends Vehicle {

    private String bikeType; // e.g., Scooter, Sports Bike

    /**
     * Bike constructor using super to initialize parent fields.
     *
     * @param vehicleId   bike ID
     * @param vehicleName bike model
     * @param rentPerDay  daily rent (bikes are cheaper)
     * @param bikeType    type of bike
     */
    public Bike(String vehicleId, String vehicleName, double rentPerDay, String bikeType) {
        super(vehicleId, vehicleName, rentPerDay);
        this.bikeType = bikeType;
    }

    /**
     * Bikes have a slightly lower effective rate (10% discount on base calculation).
     *
     * @param days rental days
     * @return total base rent for bike
     */
    @Override
    public double calculateBaseRent(int days) {
        return (rentPerDay * days) * 0.90; // 10% lower than listed rate
    }

    /**
     * Overridden displayDetails for Bike – runtime polymorphism.
     *
     * @return bike details as String
     */
    @Override
    public String displayDetails() {
        return "Bike ID: " + vehicleId
                + " | Model: " + vehicleName
                + " | Type: " + bikeType
                + " | Rent/Day: Rs. " + rentPerDay;
    }

    public String getBikeType() {
        return bikeType;
    }
}
