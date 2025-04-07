package marketing.service;

import marketing.db.MySQLDatabaseHelper;
import marketing.model.GroupBooking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GroupBookingService {
    private final List<GroupBooking> bookings = new ArrayList<>();

    public boolean holdGroupBooking(GroupBooking booking) {
        if (booking.getGroupSize() >= 12) {
            booking.setStatus("Held");
            bookings.add(booking);
            System.out.println("Group booking held for: " + booking.getGroupName());
            return true;
        }
        return false;
    }

    public boolean confirmGroupBooking(String groupName) {
        for (GroupBooking b : bookings) {
            if (b.getGroupName().equals(groupName) && "Held".equals(b.getStatus())) {
                b.setStatus("Confirmed");
                System.out.println("Group booking confirmed for: " + groupName);
                return true;
            }
        }
        return false;
    }

    public void deleteBooking(String groupName, LocalDateTime bookingTime) {
        MySQLDatabaseHelper.deleteGroupBooking(groupName, bookingTime);
    }

    public List<GroupBooking> getAllBookings() {
        return bookings;
    }
}