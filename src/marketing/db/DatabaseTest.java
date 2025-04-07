package marketing.db;

import java.sql.Timestamp;

public class DatabaseTest {
    public static void main(String[] args) {
        // Sample data for testing
        String groupName = "TestGroup";
        Timestamp bookingTime = new Timestamp(System.currentTimeMillis());
        String heldRows = "A1,A2";
        int groupSize = 2;
        double price = 50.0;

        // Test group booking
        //MySQLDatabaseHelper.saveGroupBooking(groupName, bookingTime, heldRows, groupSize, price);

        // Test single booking
        String customerName = "John Doe";
        String seatNumber = "A1";
        MySQLDatabaseHelper.saveSingleBooking(customerName, bookingTime, seatNumber, price);
    }
}