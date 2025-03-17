import java.time.LocalDateTime;

// LargeGroupBooking Class
class LargeGroupBooking {
    private int eventID;
    private String eventName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int groupSize;

    public LargeGroupBooking(int eventID, String eventName, LocalDateTime startTime, LocalDateTime endTime, int groupSize) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.groupSize = groupSize;
    }

    public String getEventName() { return eventName; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public int getGroupSize() { return groupSize; }
}
