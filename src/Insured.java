/**
 * SYLLABUS: LAB 3 – Multiple Inheritance using Interfaces
 *
 * This interface demonstrates the concept of an interface in Java.
 * A class can implement multiple interfaces, which allows a form of
 * multiple inheritance (since Java does not support multiple class inheritance).
 *
 * PremiumVehicle implements both Insured and GPS interfaces.
 */
public interface Insured {

    /**
     * Returns insurance information for an insured vehicle.
     *
     * @return insurance details as a String
     */
    String getInsuranceInfo();
}
