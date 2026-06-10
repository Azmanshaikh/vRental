import javax.swing.*;

/**
 * SYLLABUS: Entry Point – ties all modules together
 *
 * Main class launches the Vehicle Rental Portal application.
 * Initializes the SQLite database and starts the Swing GUI on the
 * Event Dispatch Thread (EDT) as required by Swing.
 */
public class Main {

    /**
     * Application entry point.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Initialize database table on startup
        DatabaseManager.initializeDatabase();

        // Swing GUI must be created on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Use system look and feel for native appearance (optional)
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Fall back to default look and feel
            }

            VehicleRentalGUI gui = new VehicleRentalGUI();
            gui.setVisible(true);

            System.out.println("======================================");
            System.out.println("  Vehicle Rental Portal Started");
            System.out.println("  Database: vehicle_rental.db");
            System.out.println("======================================");
        });
    }
}
