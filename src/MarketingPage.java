import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        navBar.setBackground(new Color(220, 220, 220));

        // Booking button (acts like dropdown trigger)
        JButton bookingBtn = new JButton("Booking");
        bookingBtn.addActionListener(e -> showBookingSubMenu());

        // Add other nav buttons as needed
        JButton folBtn = new JButton("Friends of Lancaster");
        folBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "FoL Panel"));

        JButton clientBtn = new JButton("Clients");
        clientBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Client Panel"));

        JButton reportBtn = new JButton("Reports");
        reportBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Reports Panel"));

        JButton helpBtn = new JButton("Help");
        helpBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Help Section"));

        navBar.add(bookingBtn);
        navBar.add(folBtn);
        navBar.add(clientBtn);
        navBar.add(reportBtn);
        navBar.add(helpBtn);

        return navBar;
    }

    private void showBookingSubMenu() {
        subMenuPanel.removeAll();

        JButton roomBtn = new JButton("Room Booking");
        roomBtn.addActionListener(e -> showRoomOptions());

        JButton groupBtn = new JButton("Group Booking");
        JButton tourBtn = new JButton("Tour Booking");
        JButton filmBtn = new JButton("Film Booking");

        // Dummy actions for demo
        groupBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Group Booking Panel"));
        tourBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Tour Booking Panel"));
        filmBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Film Booking Panel"));

        subMenuPanel.add(roomBtn);
        subMenuPanel.add(groupBtn);
        subMenuPanel.add(tourBtn);
        subMenuPanel.add(filmBtn);

        subMenuPanel.revalidate();
        subMenuPanel.repaint();
    }

    private void showRoomOptions() {
        subMenuPanel.removeAll();

        String[] rooms = { "Main Hall", "Small Hall", "Rehearsal Space", "Meeting Room A", "Meeting Room B" };
        for (String room : rooms) {
            JButton roomBtn = new JButton(room);
            roomBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Booking Info for " + room));
            subMenuPanel.add(roomBtn);
        }

        // Option to go back
        JButton backBtn = new JButton("← Back to Booking");
        backBtn.addActionListener(e -> showBookingSubMenu());
        subMenuPanel.add(backBtn);

        subMenuPanel.revalidate();
        subMenuPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MarketingPage mp = new MarketingPage();
            mp.setVisible(true);
        });
    }
}
