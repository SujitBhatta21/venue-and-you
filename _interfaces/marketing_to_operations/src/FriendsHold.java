import java.time.LocalDateTime;

public class FriendsHold {
    private String holdID;
    private String showID;
    private String groupName;
    private int reservedSeats;
    private LocalDateTime holdStartTime;
    private LocalDateTime holdExpiration;
    private boolean confirmed;  // Whether the booking is confirmed or still on hold

    public FriendsHold(String holdID, String showID, String groupName, int reservedSeats,
                       LocalDateTime holdStartTime, LocalDateTime holdExpiration, boolean confirmed) {
        this.holdID = holdID;
        this.showID = showID;
        this.groupName = groupName;
        this.reservedSeats = reservedSeats;
        this.holdStartTime = holdStartTime;
        this.holdExpiration = holdExpiration;
        this.confirmed = confirmed;
    }

    // Getters
    public String getHoldID() { return holdID; }
    public String getShowID() { return showID; }
    public String getGroupName() { return groupName; }
    public int getReservedSeats() { return reservedSeats; }
    public LocalDateTime getHoldStartTime() { return holdStartTime; }
    public LocalDateTime getHoldExpiration() { return holdExpiration; }
    public boolean isConfirmed() { return confirmed; }

    // Setter
    public void setConfirmed(boolean confirmed) { this.confirmed = confirmed; }
}
