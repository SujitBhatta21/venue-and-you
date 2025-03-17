import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OperationsRequirementsImplementation implements OperationsRequirements {
    private static final String URL = "jdbc:mysql://localhost:3306/operations_schema";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "MarketingTeam";

    @Override
    public List<FilmBooking> getFilmSchedule(LocalDateTime from, LocalDateTime to) {
        List<FilmBooking> filmSchedule = new ArrayList<>();
        String query = "SELECT * FROM film_bookings WHERE booking_time BETWEEN ? AND ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setTimestamp(1, Timestamp.valueOf(from));
            stmt.setTimestamp(2, Timestamp.valueOf(to));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    filmSchedule.add(new FilmBooking(
                            rs.getInt("eventID"),
                            rs.getString("filmName"),
                            rs.getDate("startDate").toLocalDate(),
                            rs.getDate("endDate").toLocalDate(),
                            rs.getTime("startTime").toLocalTime(),
                            rs.getTime("endTime").toLocalTime(),
                            rs.getInt("expectedParticipants"),
                            rs.getInt("availableSeats")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filmSchedule;
    }

    @Override
    public List<LargeGroupBooking> getGroupBookings(LocalDateTime from, LocalDateTime to) {
        List<LargeGroupBooking> groupBookings = new ArrayList<>();
        String query = "SELECT * FROM large_group_bookings WHERE startTime BETWEEN ? AND ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setTimestamp(1, Timestamp.valueOf(from));
            stmt.setTimestamp(2, Timestamp.valueOf(to));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    groupBookings.add(new LargeGroupBooking(
                            rs.getInt("bookingID"),
                            rs.getString("eventName"),
                            rs.getTimestamp("startTime").toLocalDateTime(),
                            rs.getTimestamp("endTime").toLocalDateTime(),
                            rs.getInt("groupSize")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupBookings;
    }

    @Override
    public List<MeetingBooking> getMeetingWorkshops(LocalDateTime from, LocalDateTime to) {
        List<MeetingBooking> meetingWorkshops = new ArrayList<>();
        String query = "SELECT * FROM meeting_bookings WHERE startDateTime BETWEEN ? AND ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setTimestamp(1, Timestamp.valueOf(from));
            stmt.setTimestamp(2, Timestamp.valueOf(to));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    meetingWorkshops.add(new MeetingBooking(
                            rs.getInt("meetingID"),
                            rs.getString("meetingTitle"),
                            rs.getTimestamp("startDateTime").toLocalDateTime(),
                            rs.getTimestamp("endDateTime").toLocalDateTime(),
                            rs.getString("location"),
                            rs.getInt("participants")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return meetingWorkshops;
    }

    @Override
    public List<FriendsHold> getFriendsHoldsForShow(String showID) {
        List<FriendsHold> friendsHolds = new ArrayList<>();
        String query = "SELECT * FROM friends_holds WHERE showID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, showID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    friendsHolds.add(new FriendsHold(
                            rs.getString("holdID"),
                            rs.getString("showID"),
                            rs.getString("groupName"),
                            rs.getInt("reservedSeats"),
                            rs.getTimestamp("holdStartTime").toLocalDateTime(),
                            rs.getTimestamp("holdExpiration").toLocalDateTime(),
                            rs.getBoolean("confirmed")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return friendsHolds;
    }
}
