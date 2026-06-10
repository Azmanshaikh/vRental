import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * SYLLABUS: LAB 8 – JDBC
 *
 * DatabaseManager handles all database operations using JDBC.
 * Uses PreparedStatement to prevent SQL injection and improve performance.
 * Creates the rental_history table automatically if it does not exist.
 */
public class DatabaseManager {

    /**
     * Creates rental_history table if not already present.
     * Called once when application starts.
     */
    public static void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS rental_history ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "customer_name TEXT NOT NULL, "
                + "vehicle_type TEXT NOT NULL, "
                + "days INTEGER NOT NULL, "
                + "total_cost REAL NOT NULL, "
                + "rental_date TEXT NOT NULL"
                + ")";

        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        } finally {
            // finally block ensures resources are closed even if exception occurs
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    /**
     * Inserts a new rental record into rental_history table.
     *
     * @param customerName customer name
     * @param vehicleType  type of vehicle rented
     * @param days         number of rental days
     * @param totalCost    final rental cost
     * @param rentalDate   date of rental
     * @return true if insert successful, false otherwise
     */
    public static boolean insertRental(String customerName, String vehicleType,
                                       int days, double totalCost, String rentalDate) {
        String insertSQL = "INSERT INTO rental_history "
                + "(customer_name, vehicle_type, days, total_cost, rental_date) "
                + "VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(insertSQL);
            pstmt.setString(1, customerName);
            pstmt.setString(2, vehicleType);
            pstmt.setInt(3, days);
            pstmt.setDouble(4, totalCost);
            pstmt.setString(5, rentalDate);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Insert failed: " + e.getMessage());
            return false;
        } finally {
            closeQuietly(pstmt);
            closeQuietly(conn);
        }
    }

    /**
     * Retrieves all rental records from the database.
     *
     * @return formatted String of all rental history
     */
    public static String getAllRentals() {
        String selectSQL = "SELECT id, customer_name, vehicle_type, days, total_cost, rental_date "
                + "FROM rental_history ORDER BY id DESC";

        StringBuilder history = new StringBuilder();
        history.append("========== RENTAL HISTORY ==========\n\n");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(selectSQL);
            rs = pstmt.executeQuery();

            boolean hasRecords = false;
            while (rs.next()) {
                hasRecords = true;
                history.append("ID: ").append(rs.getInt("id")).append("\n");
                history.append("Customer: ").append(rs.getString("customer_name")).append("\n");
                history.append("Vehicle: ").append(rs.getString("vehicle_type")).append("\n");
                history.append("Days: ").append(rs.getInt("days")).append("\n");
                history.append("Total Cost: Rs. ").append(rs.getDouble("total_cost")).append("\n");
                history.append("Date: ").append(rs.getString("rental_date")).append("\n");
                history.append("-----------------------------------\n");
            }

            if (!hasRecords) {
                history.append("No rental records found.\n");
            }
        } catch (SQLException e) {
            history.append("Error reading history: ").append(e.getMessage()).append("\n");
        } finally {
            closeQuietly(rs);
            closeQuietly(pstmt);
            closeQuietly(conn);
        }

        return history.toString();
    }

    /** Helper to close AutoCloseable resources safely */
    private static void closeQuietly(AutoCloseable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (Exception ignored) {
                // intentionally ignored during cleanup
            }
        }
    }
}
