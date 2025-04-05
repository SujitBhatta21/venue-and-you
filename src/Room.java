public class Room {
    public String name;
    public String type; // "performance" or "meeting"
    public int hourlyRate;
    public int minHours;
    public int eveningRate;
    public int dailyRate;

    public Room(String name, String type, int hourlyRate, int minHours, int eveningRate, int dailyRate) {
        this.name = name;
        this.type = type;
        this.hourlyRate = hourlyRate;
        this.minHours = minHours;
        this.eveningRate = eveningRate;
        this.dailyRate = dailyRate;
    }

    public String getRateSummary() {
        return "Hourly: £" + hourlyRate + " | Evening: £" + eveningRate + " | Daily: £" + dailyRate;
    }
}
