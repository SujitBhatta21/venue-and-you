import java.util.*;

public class RoomRepository {

    public static List<Room> getRooms() {
        List<Room> rooms = new ArrayList<>();

        // Performance spaces
        rooms.add(new Room("Main Hall", "performance", 325, 3, 1850, 3800));
        rooms.add(new Room("Small Hall", "performance", 225, 3, 950, 2200));
        rooms.add(new Room("Rehearsal Space", "performance", 60, 3, 240, 240));

        // Meeting rooms
        rooms.add(new Room("The Green Room", "meeting", 25, 1, 75, 600));
        rooms.add(new Room("Brontë Boardroom", "meeting", 40, 1, 120, 900));
        rooms.add(new Room("Dickens Den", "meeting", 30, 1, 90, 700));
        rooms.add(new Room("Poe Parlor", "meeting", 35, 1, 100, 800));
        rooms.add(new Room("Globe Room", "meeting", 50, 1, 150, 1100));
        rooms.add(new Room("Chekhov Chamber", "meeting", 38, 1, 110, 850));

        return rooms;
    }

    public static Room getRoomByName(String name) {
        for (Room r : getRooms()) {
            if (r.name.equalsIgnoreCase(name))
                return r;
        }
        return null;
    }
}
