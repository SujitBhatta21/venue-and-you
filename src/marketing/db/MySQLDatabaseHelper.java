package marketing.db;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import marketing.model.SingleBooking;

public class MySQLDatabaseHelper {
    private static final String URL = "jdbc:mysql://sst-stuproj.city.ac.uk:3306/in2033t21";
    private static final String USER = "in2033t21_a";
    private static final String PASSWORD = "lrLUWCLVzDQ";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Save to group_bookings table
    public static void saveGroupBooking(String groupName, int groupSize, Timestamp bookingTime, String heldRows,
            double price, String status) {
        String sql = "INSERT INTO group_bookings (group_name, group_size, booking_time, held_rows, price, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, groupName);
            stmt.setInt(2, groupSize);
            stmt.setTimestamp(3, bookingTime);
            stmt.setString(4, heldRows); // e.g., "A1,A2,A3"
            stmt.setDouble(5, price);
            stmt.setString(6, status); // e.g., "Confirmed"

            stmt.executeUpdate();
            System.out.println("✅ Group booking saved to MySQL: " + groupName + ", " + heldRows);
        } catch (SQLException e) {
            System.out.println("❌ Error saving group booking:");
            e.printStackTrace();
        }
    }

    // Save to single_bookings table
    public static void saveSingleBooking(String customerName, Timestamp bookingTime, String seatNumber, double price) {
        String sql = "INSERT INTO single_bookings (customer_name, booking_time, seat_number, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customerName);
            pstmt.setTimestamp(2, bookingTime);
            pstmt.setString(3, seatNumber);
            pstmt.setDouble(4, price);
            pstmt.executeUpdate();
            System.out.println("✅ Single booking saved to MySQL: " + customerName + ", " + seatNumber);
        } catch (SQLException e) {
            System.err.println("❌ Error saving single booking:");
            e.printStackTrace();
            throw new RuntimeException("Failed to save single booking", e); // Temporarily throw to catch in app
        }
    }

    public List<SingleBooking> getAllBookings() {
        return MySQLDatabaseHelper.getAllSingleBookings();
    }

    public static void deleteGroupBooking(String groupName, LocalDateTime time) {
        String sql = "DELETE FROM group_bookings WHERE group_name = ? AND booking_time = ?";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, groupName);
            stmt.setTimestamp(2, Timestamp.valueOf(time));
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteSingleBooking(String customerName, LocalDateTime time) {
        String sql = "DELETE FROM single_bookings WHERE customer_name = ? AND booking_time = ?";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customerName);
            stmt.setTimestamp(2, Timestamp.valueOf(time));
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<SingleBooking> getAllSingleBookings() {
        List<SingleBooking> bookings = new ArrayList<>();

        String query = "SELECT customer_name, booking_time, seat_number, price FROM single_bookings";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("customer_name");
                LocalDateTime time = rs.getTimestamp("booking_time").toLocalDateTime();
                String seat = rs.getString("seat_number");
                double price = rs.getDouble("price");

                SingleBooking booking = new SingleBooking(name, time, seat);
                booking.setPrice(price);
                bookings.add(booking);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookings;
    }
}