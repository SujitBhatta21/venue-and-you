import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RevenueReportingServiceImpl implements RevenueReportingService {
    private static final String URL = "jdbc:mysql://localhost:3306/revenue_schema";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "MarketingTeam";

    @Override
    public void generateSalesReport(String startDate, String endDate) {
        // Generates a sales report for events between startDate and endDate.
        String query = "SELECT eventName, SUM(ticketRevenue) AS totalRevenue " +
                "FROM event_revenue WHERE eventDate BETWEEN ? AND ? " +
                "GROUP BY eventName";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setString(1, startDate);
                pstmt.setString(2, endDate);

                try (ResultSet resultSet = pstmt.executeQuery()) {
                    System.out.println("Sales Report from " + startDate + " to " + endDate + ":");
                    while (resultSet.next()) {
                        String eventName = resultSet.getString("eventName");
                        double revenue = resultSet.getDouble("totalRevenue");
                        System.out.println("Event: " + eventName + " | Revenue: $" + revenue);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error generating sales report: " + e.getMessage());
        }
    }

    @Override
    public double getEventRevenue(String eventName, String date) {
        // Retrieves total revenue for a given event on a specific date.
        double revenue = 0.0;
        String query = "SELECT SUM(ticketRevenue) AS totalRevenue FROM event_revenue " +
                "WHERE eventName = ? AND eventDate = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setString(1, eventName);
                pstmt.setString(2, date);

                try (ResultSet resultSet = pstmt.executeQuery()) {
                    if (resultSet.next()) {
                        revenue = resultSet.getDouble("totalRevenue");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error retrieving event revenue: " + e.getMessage());
        }
        return revenue;
    }

    @Override
    public void sendAudienceDemographicsReport(String eventName, String demographicsData) {
        // Stores audience demographics data for an event.
        String query = "INSERT INTO audience_demographics (eventName, demographicsData) VALUES (?, ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setString(1, eventName);
                pstmt.setString(2, demographicsData);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Demographics report stored for event: " + eventName);
                } else {
                    System.out.println("Failed to store demographics report.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error storing demographics report: " + e.getMessage());
        }
    }
}
