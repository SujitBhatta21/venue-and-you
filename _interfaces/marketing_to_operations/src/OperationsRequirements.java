import java.time.LocalDateTime;
import java.util.List;

public interface OperationsRequirements {
    List<FilmBooking> getFilmSchedule(LocalDateTime from, LocalDateTime to);
    List<LargeGroupBooking> getGroupBookings(LocalDateTime from, LocalDateTime to);
    List<MeetingBooking> getMeetingWorkshops(LocalDateTime from, LocalDateTime to);
    List<FriendsHold> getFriendsHoldsForShow(String showID);
}
