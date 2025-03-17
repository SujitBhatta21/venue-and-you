import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TicketSalesServiceImpl implements TicketSalesService {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/ticketing_schema";
    private static final String USERNAME = "root";  // Change as needed.
    private static final String PASSWORD = "MarketingTeam";  // Change as needed.

    @Override
    public void applyDiscount(String eventName, String discountCode, double percentage) {
        /*
         * Applies a discount to an event based on a given discount code.
         */
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();

            String query = "INSERT INTO discounts (eventName, discountCode, discountPercentage) " +
                    "VALUES ('" + eventName + "', '" + discountCode + "', " + percentage + ") " +
                    "ON DUPLICATE KEY UPDATE discountPercentage = " + percentage;

            int rowsAffected = statement.executeUpdate(query);
            if (rowsAffected > 0) {
                System.out.println("Discount " + percentage + "% applied for event: " + eventName);
            } else {
                System.out.println("Failed to apply discount.");
            }

            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public boolean verifyDiscount(String discountCode, String customerCategory) {
        /*
         * Verifies if a discount code is valid for a specific customer category.
         */
        boolean isValid = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();

            String query = "SELECT COUNT(*) AS count FROM discounts WHERE discountCode = '" + discountCode +
                    "' AND customerCategory = '" + customerCategory + "'";

            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                isValid = resultSet.getInt("count") > 0;
            }

            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return isValid;
    }

    @Override
    public void updateGroupBooking(String eventName, String date, int seatCount, String groupName) {
        /*
         * Updates group booking by reserving seats for a group.
         */
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();

            String query = "UPDATE bookings SET reservedSeats = reservedSeats + " + seatCount +
                    ", groupName = '" + groupName + "', availableSeats = availableSeats - " + seatCount +
                    " WHERE eventName = '" + eventName + "' AND eventDate = '" + date + "' AND availableSeats >= " + seatCount;

            int rowsAffected = statement.executeUpdate(query);
            if (rowsAffected > 0) {
                System.out.println(seatCount + " seats reserved for " + groupName + " at " + eventName + " on " + date);
            } else {
                System.out.println("Failed to reserve seats: Not enough available.");
            }

            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void notifyPromotionalEvent(String eventName, String promotionDetails) {
        /*
         * Stores promotional event details in the database.
         */
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();

            String query = "INSERT INTO promotions (eventName, promotionDetails) " +
                    "VALUES ('" + eventName + "', '" + promotionDetails + "')";

            int rowsAffected = statement.executeUpdate(query);
            if (rowsAffected > 0) {
                System.out.println("Promotion stored for event: " + eventName);
            } else {
                System.out.println("Failed to store promotion.");
            }

            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
