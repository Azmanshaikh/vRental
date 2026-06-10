import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SYLLABUS: LAB 1 – Object Oriented Fundamentals (Method Overloading)
 * SYLLABUS: LAB 2 – Inheritance & Polymorphism
 * SYLLABUS: LAB 5 – Exception Handling
 * SYLLABUS: LAB 7 – Swing GUI
 *
 * Main graphical user interface for the Vehicle Rental Portal.
 * Uses BorderLayout and GridBagLayout with a Soft Blue Professional theme.
 */
public class VehicleRentalGUI extends JFrame {

    // Theme colors
    private static final Color BG_COLOR = new Color(0xF4, 0xF8, 0xFB);
    private static final Color BTN_COLOR = new Color(0x3B, 0x82, 0xF6);
    private static final Color BTN_TEXT = Color.WHITE;
    private static final Font UI_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 14);

    // GUI components
    private JTextField customerNameField;
    private JComboBox<String> vehicleComboBox;
    private JTextField daysField;
    private JTextField discountField;
    private JTextArea resultArea;
    private JLabel clockLabel;

    // Business objects – polymorphism: reference type Vehicle, actual type varies
    private Vehicle selectedVehicle;
    private double lastCalculatedTotal = 0.0;
    private int lastRentalDays = 0;
    private String lastCustomerName = "";
    private String lastVehicleType = "";

    // Background clock thread
    private Thread clockThread;
    private SystemClockThread systemClock;

    /**
     * Constructor – builds and displays the GUI.
     */
    public VehicleRentalGUI() {
        initializeFrame();
        buildGUI();
        startClockThread();
    }

    /**
     * Configures JFrame properties.
     */
    private void initializeFrame() {
        setTitle("Vehicle Rental Portal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 680);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_COLOR);
    }

    /**
     * Builds all GUI panels and components.
     */
    private void buildGUI() {
        setLayout(new BorderLayout(10, 10));

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createFormPanel(), BorderLayout.CENTER);
        add(createResultPanel(), BorderLayout.SOUTH);

        pack();
        setMinimumSize(new Dimension(720, 650));
    }

    /**
     * Header with project title and live clock.
     */
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_COLOR);
        header.setBorder(new EmptyBorder(15, 20, 10, 20));

        JLabel titleLabel = new JLabel("Vehicle Rental Portal", SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(new Color(0x1E, 0x40, 0xAF));

        clockLabel = new JLabel("  Loading clock...  ", SwingConstants.CENTER);
        clockLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        clockLabel.setForeground(new Color(0x37, 0x47, 0x51));
        clockLabel.setBorder(BorderFactory.createLineBorder(new Color(0xBF, 0xDB, 0xFE), 1));

        header.add(titleLabel, BorderLayout.NORTH);
        header.add(Box.createVerticalStrut(8), BorderLayout.CENTER);
        header.add(clockLabel, BorderLayout.SOUTH);

        return header;
    }

    /**
     * Center form panel using GridBagLayout.
     */
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BG_COLOR);
        formPanel.setBorder(new EmptyBorder(5, 20, 5, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // --- Customer Information Section ---
        JPanel customerPanel = createSectionPanel("Customer Information");
        customerNameField = styledTextField();
        addToSection(customerPanel, "Customer Name:", customerNameField);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(customerPanel, gbc);

        // --- Vehicle Selection Section ---
        JPanel vehiclePanel = createSectionPanel("Vehicle Selection");
        vehicleComboBox = new JComboBox<>(new String[]{"Car", "Bike", "Premium Car"});
        vehicleComboBox.setFont(UI_FONT);
        addToSection(vehiclePanel, "Select Vehicle:", vehicleComboBox);

        gbc.gridy = 1;
        formPanel.add(vehiclePanel, gbc);

        // --- Rental Information Section ---
        JPanel rentalPanel = createSectionPanel("Rental Information");
        daysField = styledTextField();
        discountField = styledTextField();
        discountField.setText("0");
        addToSection(rentalPanel, "Number of Days:", daysField);
        addToSection(rentalPanel, "Discount (%):", discountField);

        gbc.gridy = 2;
        formPanel.add(rentalPanel, gbc);

        // --- Buttons Section ---
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 8, 0));
        buttonPanel.setBackground(BG_COLOR);
        buttonPanel.setBorder(new EmptyBorder(10, 0, 5, 0));

        JButton calculateBtn = styledButton("Calculate Rent");
        JButton saveBtn = styledButton("Save Rental");
        JButton historyBtn = styledButton("View History");
        JButton receiptBtn = styledButton("Print Receipt");
        JButton clearBtn = styledButton("Clear");

        // ActionListeners for all buttons (LAB 7 requirement)
        calculateBtn.addActionListener(e -> calculateRent());
        saveBtn.addActionListener(e -> saveRental());
        historyBtn.addActionListener(e -> viewHistory());
        receiptBtn.addActionListener(e -> printReceipt());
        clearBtn.addActionListener(e -> clearForm());

        buttonPanel.add(calculateBtn);
        buttonPanel.add(saveBtn);
        buttonPanel.add(historyBtn);
        buttonPanel.add(receiptBtn);
        buttonPanel.add(clearBtn);

        gbc.gridy = 3;
        formPanel.add(buttonPanel, gbc);

        return formPanel;
    }

    /**
     * Results area at the bottom.
     */
    private JPanel createResultPanel() {
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBackground(BG_COLOR);
        resultPanel.setBorder(new EmptyBorder(5, 20, 15, 20));

        resultArea = new JTextArea(10, 50);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        resultArea.setEditable(false);
        resultArea.setBackground(Color.WHITE);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setText("Rental summary will appear here...\n");

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0xBF, 0xDB, 0xFE)),
                " Rental Summary ",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                HEADER_FONT,
                new Color(0x1E, 0x40, 0xAF)
        ));

        resultPanel.add(scrollPane, BorderLayout.CENTER);
        return resultPanel;
    }

    /** Creates a styled section panel with titled border */
    private JPanel createSectionPanel(String title) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0xBF, 0xDB, 0xFE)),
                " " + title + " ",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                HEADER_FONT,
                new Color(0x1E, 0x40, 0xAF)
        ));
        return panel;
    }

    /** Adds label + component row inside a section panel */
    private void addToSection(JPanel panel, String labelText, JComponent component) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 8, 4, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int row = panel.getComponentCount() / 2;

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        JLabel label = new JLabel(labelText);
        label.setFont(UI_FONT);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(component, gbc);
    }

    private JTextField styledTextField() {
        JTextField field = new JTextField(20);
        field.setFont(UI_FONT);
        return field;
    }

    private JButton styledButton(String text) {
        JButton button = new JButton(text);

        button.setFont(new Font("Segoe UI", Font.BOLD, 11));

        button.setBackground(BTN_COLOR);
        button.setForeground(Color.WHITE);

        button.setOpaque(true);
        button.setContentAreaFilled(true);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    /**
     * Starts background clock thread (LAB 4 – Multithreading).
     */
    private void startClockThread() {
        systemClock = new SystemClockThread(clockLabel);
        clockThread = new Thread(systemClock, "SystemClockThread");
        clockThread.setDaemon(true);
        clockThread.start();
    }

    // ============================================================
    // LAB 1 – Method Overloading: two versions of calculateTotal
    // ============================================================

    /**
     * Overloaded method – calculates total without discount.
     */
    private double calculateTotal(Vehicle vehicle, int days) {
        return vehicle.calculateBaseRent(days);
    }

    /**
     * Overloaded method – calculates total with discount percentage.
     */
    private double calculateTotal(Vehicle vehicle, int days, double discountPercent) {
        double baseTotal = vehicle.calculateBaseRent(days);
        double discountAmount = baseTotal * (discountPercent / 100.0);
        return baseTotal - discountAmount;
    }

    /**
     * Creates Vehicle object based on combo box selection.
     * Demonstrates POLYMORPHISM – Vehicle reference, different actual types.
     */
    private Vehicle createVehicle(String type) {
        Vehicle vehicle;

        if ("Car".equals(type)) {
            vehicle = new Car("CAR001", "Maruti Swift", 1500.0, 5);
        } else if ("Bike".equals(type)) {
            vehicle = new Bike("BIKE001", "Honda Activa", 500.0, "Scooter");
        } else {
            vehicle = new PremiumVehicle("PREM001", "Toyota Fortuner", 3500.0, 7,
                    "HDFC Ergo", "GPS-2024-PREM");
        }

        return vehicle;
    }

    /**
     * Calculate Rent button handler with exception handling (LAB 5).
     */
    private void calculateRent() {
        String customerName = "";
        int days = 0;
        double discount = 0.0;

        try {
            customerName = customerNameField.getText().trim();
            if (customerName.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter customer name.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            days = Integer.parseInt(daysField.getText().trim());
            discount = Double.parseDouble(discountField.getText().trim());

            if (days <= 0) {
                throw new InvalidDaysException("Rental days must be greater than zero.");
            }

            if (discount < 0 || discount > 100) {
                JOptionPane.showMessageDialog(this,
                        "Discount must be between 0 and 100.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            lastVehicleType = (String) vehicleComboBox.getSelectedItem();
            selectedVehicle = createVehicle(lastVehicleType);

            double total;
            if (discount > 0) {
                total = calculateTotal(selectedVehicle, days, discount);
            } else {
                total = calculateTotal(selectedVehicle, days);
            }

            lastCalculatedTotal = total;
            lastRentalDays = days;
            lastCustomerName = customerName;

            StringBuilder summary = new StringBuilder();
            summary.append("===== RENTAL CALCULATION RESULT =====\n\n");
            summary.append("Customer Name : ").append(customerName).append("\n");
            summary.append("Vehicle Type  : ").append(lastVehicleType).append("\n");
            summary.append(selectedVehicle.displayDetails()).append("\n");
            summary.append("Rental Days   : ").append(days).append("\n");
            summary.append("Discount      : ").append(discount).append("%\n");
            summary.append("Total Cost    : Rs. ").append(String.format("%.2f", total)).append("\n");

            if (selectedVehicle instanceof PremiumVehicle) {
                PremiumVehicle premium = (PremiumVehicle) selectedVehicle;
                summary.append("\n--- Premium Features (Interfaces) ---\n");
                summary.append(premium.getInsuranceInfo()).append("\n");
                summary.append(premium.getGPSInfo()).append("\n");
            }

            resultArea.setText(summary.toString());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid numbers for Days and Discount.",
                    "Number Format Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (InvalidDaysException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Invalid Days Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            System.out.println("[LOG] Calculate Rent operation completed at "
                    + new SimpleDateFormat("HH:mm:ss").format(new Date())
                    + " | Customer: " + (customerName.isEmpty() ? "N/A" : customerName)
                    + " | Days: " + days);
        }
    }

    /**
     * Saves current rental to SQLite database.
     */
    private void saveRental() {
        if (lastCalculatedTotal <= 0 || lastCustomerName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please calculate rent first before saving.",
                    "Save Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String rentalDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        boolean saved = DatabaseManager.insertRental(
                lastCustomerName, lastVehicleType, lastRentalDays,
                lastCalculatedTotal, rentalDate);

        if (saved) {
            JOptionPane.showMessageDialog(this,
                    "Rental saved successfully to database!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to save rental. Check SQLite JDBC driver.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Loads and displays all rental history from database.
     */
    private void viewHistory() {
        String history = DatabaseManager.getAllRentals();
        resultArea.setText(history);
    }

    /**
     * Generates receipt.txt using File I/O.
     */
    private void printReceipt() {
        if (lastCalculatedTotal <= 0 || lastCustomerName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please calculate rent first before printing receipt.",
                    "Receipt Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        boolean success = ReceiptGenerator.generateReceipt(
                lastCustomerName, lastVehicleType, lastRentalDays,
                lastCalculatedTotal, date);

        if (success) {
            JOptionPane.showMessageDialog(this,
                    "Receipt saved as " + ReceiptGenerator.getReceiptFileName()
                            + " in project folder.",
                    "Receipt Generated",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to generate receipt file.",
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Clears all input fields and result area.
     */
    private void clearForm() {
        customerNameField.setText("");
        daysField.setText("");
        discountField.setText("0");
        vehicleComboBox.setSelectedIndex(0);
        resultArea.setText("Rental summary will appear here...\n");
        selectedVehicle = null;
        lastCalculatedTotal = 0.0;
        lastRentalDays = 0;
        lastCustomerName = "";
        lastVehicleType = "";
    }

    /**
     * Stops clock thread when window closes.
     */
    @Override
    public void dispose() {
        if (systemClock != null) {
            systemClock.stopClock();
        }
        super.dispose();
    }
}
