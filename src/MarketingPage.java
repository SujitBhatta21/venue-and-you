import javax.swing.*;
import java.awt.*;

public class MarketingPage extends JFrame {

    private JPanel subMenuPanel;

    public MarketingPage() {
        setTitle("Marketing Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create and add top nav bar
        JPanel navBar = createNavBar();
        add(navBar, BorderLayout.NORTH);

        // Area to hold submenu buttons
        subMenuPanel = new JPanel();
        subMenuPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        add(subMenuPanel, BorderLayout.CENTER);

        // Add welcome label or default content
        JLabel label = new JLabel("Welcome to the Marketing Dashboard", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        add(label, BorderLayout.SOUTH);
    }

    private JPanel createNavBar() {
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        navBar.setBackground(Color.decode("#142524")); // Aztec

        // Booking button (acts like dropdown trigger)
        JButton bookingBtn = new JButton("Booking");
        bookingBtn.setBackground(Color.decode("#30C142")); // Apple
        bookingBtn.setForeground(Color.WHITE);
        bookingBtn.addActionListener(e -> showBookingSubMenu());

        // Add other nav buttons as needed
        JButton folBtn = new JButton("Friends of Lancaster");
        folBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "FoL Panel"));

        JButton clientBtn = new JButton("Clients");
        clientBtn.addActionListener(e -> showClientPanel());

        JButton reportBtn = new JButton("Reports");
        reportBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Reports Panel"));

        JButton helpBtn = new JButton("Help");
        helpBtn.addActionListener(e -> showHelpPanel());

        navBar.add(bookingBtn);
        navBar.add(folBtn);
        navBar.add(clientBtn);
        navBar.add(reportBtn);
        navBar.add(helpBtn);

        return navBar;
    }

    private void showClientPanel() {
        subMenuPanel.removeAll();
        subMenuPanel.setLayout(new BorderLayout());
        subMenuPanel.add(new ClientPanel(), BorderLayout.CENTER);
        subMenuPanel.revalidate();
        subMenuPanel.repaint();
    }

    private void showHelpPanel() {
        subMenuPanel.removeAll();
        subMenuPanel.setLayout(new BorderLayout());
        subMenuPanel.add(new HelpPanel(), BorderLayout.CENTER);
        subMenuPanel.revalidate();
        subMenuPanel.repaint();
    }
    
    private void showBookingSubMenu() {
        subMenuPanel.removeAll();
        subMenuPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // <-- IMPORTANT FIX

        // --- Room Booking Menu ---
        JButton roomBookingBtn = new JButton("Room Booking");
        roomBookingBtn.addActionListener(e -> showRoomList("room"));
        subMenuPanel.add(roomBookingBtn);

        // --- Hall Booking Menu ---
        JButton hallBookingBtn = new JButton("Hall Booking");
        hallBookingBtn.addActionListener(e -> showRoomList("hall"));
        subMenuPanel.add(hallBookingBtn);

        // --- Group Booking ---
        JButton groupBtn = new JButton("Group Booking");
        groupBtn.addActionListener(e -> highlightButtonAndShow(groupBtn, "Group Booking Panel"));
        subMenuPanel.add(groupBtn);

        // --- Tour Booking ---
        JButton tourBtn = new JButton("Tour Booking");
        tourBtn.addActionListener(e -> highlightButtonAndShow(tourBtn, "Tour Booking Panel"));
        subMenuPanel.add(tourBtn);

        // --- Film Booking ---
        JButton filmBtn = new JButton("Film Booking");
        filmBtn.addActionListener(e -> highlightButtonAndShow(filmBtn, "Film Booking Panel"));
        subMenuPanel.add(filmBtn);

        subMenuPanel.revalidate();
        subMenuPanel.repaint();
    }

    private void showRoomList(String type) {
        subMenuPanel.removeAll();

        JLabel header = new JLabel("Select a " + (type.equals("hall") ? "Hall" : "Room"));
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setForeground(Color.decode("#142524"));
        subMenuPanel.add(header);

        for (Room room : RoomRepository.getRooms()) {
            boolean isHall = room.name.equals("Main Hall") || room.name.equals("Small Hall");
            if ((type.equals("hall") && isHall) || (type.equals("room") && !isHall)) {
                JButton roomBtn = new JButton(room.name);
                roomBtn.setBackground(Color.decode("#848D94"));
                roomBtn.setForeground(Color.WHITE);
                roomBtn.addActionListener(e -> {
                    resetButtonColors();
                    roomBtn.setBackground(Color.decode("#30C142"));
                    subMenuPanel.removeAll();
                    subMenuPanel.setLayout(new BorderLayout());
                    subMenuPanel.add(new RoomBookingPanel(room.name), BorderLayout.CENTER);
                    subMenuPanel.revalidate();
                    subMenuPanel.repaint();
                });
                subMenuPanel.add(roomBtn);
            }
        }

        // Add back button to go to Booking menu
        JButton backBtn = new JButton("← Back to Booking");
        backBtn.setBackground(Color.decode("#30C142"));
        backBtn.setForeground(Color.WHITE);
        backBtn.addActionListener(e -> showBookingSubMenu());
        subMenuPanel.add(backBtn);

        subMenuPanel.revalidate();
        subMenuPanel.repaint();
    }

    private void highlightButtonAndShow(JButton btn, String message) {
        resetButtonColors();
        btn.setBackground(Color.decode("#30C142"));
        btn.setForeground(Color.WHITE);
        JOptionPane.showMessageDialog(this, message);
    }

    private void resetButtonColors() {
        for (Component comp : subMenuPanel.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                button.setBackground(Color.decode("#848D94"));
                button.setForeground(Color.WHITE);
            }
        }
    }
}