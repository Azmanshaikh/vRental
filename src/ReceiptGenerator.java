import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * SYLLABUS: LAB 8 – File I/O
 *
 * ReceiptGenerator writes rental details to receipt.txt using
 * FileWriter and BufferedWriter. Demonstrates character stream I/O in Java.
 */
public class ReceiptGenerator {

    private static final String RECEIPT_FILE = "receipt.txt";

    /**
     * Generates a text receipt file with rental details.
     *
     * @param customerName customer name
     * @param vehicleType  vehicle type rented
     * @param days         rental days
     * @param totalCost    total amount paid
     * @param date         rental date
     * @return true if file written successfully
     */
    public static boolean generateReceipt(String customerName, String vehicleType,
                                          int days, double totalCost, String date) {
        // try-with-resources automatically closes writers (also demonstrates resource handling)
        try (FileWriter fileWriter = new FileWriter(RECEIPT_FILE);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {

            writer.write("=================================\n");
            writer.write("     VEHICLE RENTAL RECEIPT\n");
            writer.write("=================================\n\n");
            writer.write("Customer Name : " + customerName + "\n");
            writer.write("Vehicle Type  : " + vehicleType + "\n");
            writer.write("Rental Days   : " + days + "\n");
            writer.write("Total Cost    : Rs. " + String.format("%.2f", totalCost) + "\n");
            writer.write("Date          : " + date + "\n");
            writer.write("\n=================================\n");
            writer.write("   Thank you for choosing us!\n");
            writer.write("=================================\n");

            return true;
        } catch (IOException e) {
            System.err.println("Failed to write receipt: " + e.getMessage());
            return false;
        }
    }

    /**
     * @return the receipt file name
     */
    public static String getReceiptFileName() {
        return RECEIPT_FILE;
    }
}
