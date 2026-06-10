/**
 * SYLLABUS: LAB 3 – Multiple Inheritance using Interfaces
 *
 * PremiumVehicle extends Car (single inheritance) and implements
 * TWO interfaces: Insured and GPS. This demonstrates multiple
 * inheritance through interfaces in Java.
 *
 * Used in the GUI when user selects "Premium Car" from the combo box.
 */
public class PremiumVehicle extends Car implements Insured, GPS {

    private String insuranceProvider;
    private String gpsDeviceId;

    /**
     * Premium car with insurance and GPS features.
     *
     * @param vehicleId          vehicle ID
     * @param vehicleName        model name
     * @param rentPerDay         daily rent (premium rate)
     * @param numberOfSeats      seating capacity
     * @param insuranceProvider  insurance company name
     * @param gpsDeviceId        GPS device identifier
     */
    public PremiumVehicle(String vehicleId, String vehicleName, double rentPerDay,
                          int numberOfSeats, String insuranceProvider, String gpsDeviceId) {
        super(vehicleId, vehicleName, rentPerDay, numberOfSeats);
        this.insuranceProvider = insuranceProvider;
        this.gpsDeviceId = gpsDeviceId;
    }

    /**
     * Premium vehicles charge 25% extra on base car rent.
     *
     * @param days rental days
     * @return premium rental amount
     */
    @Override
    public double calculateBaseRent(int days) {
        return super.calculateBaseRent(days) * 1.25;
    }

    /**
     * Overrides Car's displayDetails and adds premium features info.
     *
     * @return premium vehicle details
     */
    @Override
    public String displayDetails() {
        return super.displayDetails()
                + " | Premium Features: Insured + GPS Enabled";
    }

    /**
     * Implementation of Insured interface method.
     *
     * @return insurance information
     */
    @Override
    public String getInsuranceInfo() {
        return "Insurance Provider: " + insuranceProvider + " | Full Comprehensive Cover";
    }

    /**
     * Implementation of GPS interface method.
     *
     * @return GPS tracking information
     */
    @Override
    public String getGPSInfo() {
        return "GPS Device ID: " + gpsDeviceId + " | Real-time Tracking Active";
    }
}
