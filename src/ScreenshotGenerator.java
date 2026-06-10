import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;

public class ScreenshotGenerator {

    private static VehicleRentalGUI gui;
    private static Robot robot;

    public static void main(String[] args) {
        try {
            // Clean up old database for consistent screenshots
            File dbFile = new File("vehicle_rental.db");
            if (dbFile.exists()) {
                dbFile.delete();
            }

            DatabaseManager.initializeDatabase();
            robot = new Robot();

            // Seed the first three bookings directly in database
            // so they appear in the history screen later.
            String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            DatabaseManager.insertRental("Azman Shaikh", "Car", 5, 7500.0, dateStr);
            Thread.sleep(100);
            DatabaseManager.insertRental("Lakshay Khatri", "Bike", 3, 1425.0, dateStr);
            Thread.sleep(100);
            DatabaseManager.insertRental("Rishabh Kumar", "Premium Car", 10, 37187.5, dateStr);

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Run GUI
            SwingUtilities.invokeAndWait(() -> {
                gui = new VehicleRentalGUI();
                gui.setVisible(true);
            });

            // Wait for GUI to render and tick clock
            Thread.sleep(1500);

            // Capture initial screen
            captureFrame("screenshot1_initial.png");

            // Scenario 2: Car Calculation for Azman Shaikh
            setField("customerNameField", "Azman Shaikh");
            setField("daysField", "5");
            setField("discountField", "0");
            setComboBox("vehicleComboBox", 0); // Car
            invokeMethod("calculateRent");
            Thread.sleep(500);
            captureFrame("screenshot2_car.png");

            // Scenario 3: Bike Calculation for Lakshay Khatri
            setField("customerNameField", "Lakshay Khatri");
            setField("daysField", "3");
            setField("discountField", "5");
            setComboBox("vehicleComboBox", 1); // Bike
            invokeMethod("calculateRent");
            Thread.sleep(500);
            captureFrame("screenshot3_bike.png");

            // Scenario 4: Premium Car Calculation for Rishabh Kumar
            setField("customerNameField", "Rishabh Kumar");
            setField("daysField", "10");
            setField("discountField", "15");
            setComboBox("vehicleComboBox", 2); // Premium Car
            invokeMethod("calculateRent");
            Thread.sleep(500);
            captureFrame("screenshot4_premium.png");

            // Scenario 5: We calculate for Shrikrishna Hegde, then save it (capturing the dialog)
            setField("customerNameField", "Shrikrishna Hegde");
            setField("daysField", "7");
            setField("discountField", "10");
            setComboBox("vehicleComboBox", 0); // Car
            invokeMethod("calculateRent");
            Thread.sleep(500);

            // Start background thread to dismiss the Save Rental dialog
            startDialogDismissThread("screenshot5_save.png");
            invokeMethod("saveRental");
            Thread.sleep(1000);

            // Scenario 6: View History (will show all 4 names!)
            invokeMethod("viewHistory");
            Thread.sleep(500);
            captureFrame("screenshot6_history.png");

            // Scenario 7: Print Receipt (capturing the success dialog for Shrikrishna Hegde)
            startDialogDismissThread("screenshot7_receipt.png");
            invokeMethod("printReceipt");
            Thread.sleep(1000);

            // Close application
            SwingUtilities.invokeAndWait(() -> {
                gui.dispose();
            });

            System.out.println("All screenshots generated successfully!");
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void captureFrame(String filename) throws Exception {
        Point loc = gui.getLocationOnScreen();
        Dimension size = gui.getSize();
        Rectangle rect = new Rectangle(loc.x, loc.y, size.width, size.height);
        BufferedImage img = robot.createScreenCapture(rect);
        ImageIO.write(img, "png", new File(filename));
        System.out.println("Captured " + filename);
    }

    private static void startDialogDismissThread(String filename) {
        new Thread(() -> {
            try {
                // Sleep for dialog to appear
                Thread.sleep(600);
                // Capture the screen region including the dialog
                Point loc = gui.getLocationOnScreen();
                Dimension size = gui.getSize();
                Rectangle rect = new Rectangle(loc.x - 20, loc.y - 20, size.width + 40, size.height + 40);
                BufferedImage img = robot.createScreenCapture(rect);
                ImageIO.write(img, "png", new File(filename));
                System.out.println("Captured dialog " + filename);

                // Press Enter to dismiss dialog
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void setField(String fieldName, String value) throws Exception {
        Field field = VehicleRentalGUI.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        JTextField tf = (JTextField) field.get(gui);
        tf.setText(value);
    }

    private static void setComboBox(String fieldName, int index) throws Exception {
        Field field = VehicleRentalGUI.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        JComboBox<?> cb = (JComboBox<?>) field.get(gui);
        cb.setSelectedIndex(index);
    }

    private static void invokeMethod(String methodName) throws Exception {
        Method method = VehicleRentalGUI.class.getDeclaredMethod(methodName);
        method.setAccessible(true);
        method.invoke(gui);
    }
}
