package marketing.service;

import marketing.db.MySQLDatabaseHelper;
import marketing.model.SingleBooking;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class SingleBookingService {
    public boolean confirmSingleBooking(SingleBooking booking) {
        String customerName = booking.getCustomerName();
        Timestamp bookingTime = Timestamp.valueOf(booking.getBookingTime());
        String seatNumber = extractSeatNumber(booking.getSeatNumber());
        double price = booking.getPrice();

        MySQLDatabaseHelper.saveSingleBooking(customerName, bookingTime, seatNumber, price);
        return true;
    }

    public List<SingleBooking> getAllBookings() {
        return MySQLDatabaseHelper.getAllSingleBookings();
    }

    public void deleteBooking(String customerName, LocalDateTime bookingTime) {
        MySQLDatabaseHelper.deleteSingleBooking(customerName, bookingTime);
    }

    private String extractSeatNumber(String fullSeat) {
        return fullSeat.split(" ")[0];
    }
}
