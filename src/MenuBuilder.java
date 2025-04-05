import javax.swing.*;
import java.awt.event.ActionEvent;

public class MenuBuilder {

    public static JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // --- Booking Menu ---
        JMenu bookingMenu = new JMenu("Booking");

        // Room Booking Submenu
        JMenu roomBookingMenu = new JMenu("Room Booking");
        roomBookingMenu.add(createRoomItem("Main Hall"));
        roomBookingMenu.add(createRoomItem("Small Hall"));
        roomBookingMenu.add(createRoomItem("Rehearsal Space"));
        roomBookingMenu.add(createRoomItem("Meeting Room A"));
        roomBookingMenu.add(createRoomItem("Meeting Room B"));

        bookingMenu.add(roomBookingMenu);
        bookingMenu.add(new JMenuItem("Group Booking"));
        bookingMenu.add(new JMenuItem("Tour Booking"));
        bookingMenu.add(new JMenuItem("Film Booking"));

        // --- Friends of Lancaster ---
        JMenu folMenu = new JMenu("Friends of Lancaster");
        folMenu.add(new JMenuItem("Manage Friends"));
        folMenu.add(new JMenuItem("Booking Summary"));

        // --- Clients ---
        JMenu clientMenu = new JMenu("Clients");
        clientMenu.add(new JMenuItem("View Clients"));
        clientMenu.add(new JMenuItem("Add New Client"));

        // --- Reports ---
        JMenu reportMenu = new JMenu("Reports");
        reportMenu.add(new JMenuItem("Ticket Sales Summary"));
        reportMenu.add(new JMenuItem("Held Seats Overview"));

        // --- Help ---
        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(new JMenuItem("Documentation"));
        helpMenu.add(new JMenuItem("Contact Support"));

        // Add all menus to the bar
        menuBar.add(bookingMenu);
        menuBar.add(folMenu);
        menuBar.add(clientMenu);
        menuBar.add(reportMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }

    private static JMenuItem createRoomItem(String roomName) {
        JMenuItem item = new JMenuItem(roomName);
        item.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null, "Booking info for: " + roomName);
            // Optional: Open a panel or new window to show booking data
        });
        return item;
    }
}
