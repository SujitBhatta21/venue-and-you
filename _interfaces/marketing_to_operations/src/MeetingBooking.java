import java.time.LocalDateTime;

public class MeetingBooking {
    private int meetingID;
    private String meetingName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String location;
    private int participants;

    public MeetingBooking(int meetingID, String meetingName, LocalDateTime startDateTime,
                          LocalDateTime endDateTime, String location, int participants) {
        this.meetingID = meetingID;
        this.meetingName = meetingName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = location;
        this.participants = participants;
    }

    // Getters
    public int getMeetingID() { return meetingID; }
    public String getMeetingName() { return meetingName; }
    public LocalDateTime getStartDateTime() { return startDateTime; }
    public LocalDateTime getEndDateTime() { return endDateTime; }
    public String getLocation() { return location; }
    public int getParticipants() { return participants; }
}