import javax.swing.*;
import java.awt.*;

public class MarketingPage extends JFrame {

    private JPanel subMenuPanel;
    private ImageIcon mainhall;
    private ImageIcon smallhall;

    // Store nav buttons to reset color on selection
    private JButton[] navButtons;

    public MarketingPage() {
        setTitle("Marketing Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Navigation Bar
        JPanel navBar = createNavBar();
        add(navBar, BorderLayout.NORTH);

        // Submenu Panel
        subMenuPanel = new JPanel();
        subMenuPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        subMenuPanel.setBorder(BorderFactory.createLineBorder(new Color(0x848D94), 2));
        subMenuPanel.setBackground(Color.decode("#CCD1D2")); // Updated background
        add(subMenuPanel, BorderLayout.CENTER);
        //

        // Welcome Image
        ImageIcon rawIcon = new ImageIcon("src/img/lancasters-music-hall-high-resolution-logo.png");
        Image scaledImage = rawIcon.getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH);
        JLabel welcomeImage = new JLabel(new ImageIcon(scaledImage));
        welcomeImage.setHorizontalAlignment(JLabel.CENTER);
        welcomeImage.setVerticalAlignment(JLabel.CENTER);
        subMenuPanel.add(welcomeImage);

        // Welcome Label
        JLabel label = new JLabel("Welcome to the Marketing Dashboard", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        add(label, BorderLayout.SOUTH);

        // Load hall images (prepared for future use)
        mainhall = new ImageIcon(new ImageIcon("src/img/MainHall-SeatingPlan.png").getImage().getScaledInstance(50, 200,
                Image.SCALE_SMOOTH));
        smallhall = new ImageIcon(new ImageIcon("src/img/SmallHall-SeatingPlan.png").getImage().getScaledInstance(50,
                200, Image.SCALE_SMOOTH));
    }

    private JPanel createNavBar() {
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        navBar.setBackground(Color.decode("#142524"));

        // Buttons
        JButton bookingBtn = createStyledButton("Booking");
        JButton folBtn = createStyledButton("Friends of Lancaster");
        JButton clientBtn = createStyledButton("Clients");
        JButton reportBtn = createStyledButton("Reports");
        JButton helpBtn = createStyledButton("Help");

        // Store in array for color resets
        navButtons = new JButton[] { bookingBtn, folBtn, clientBtn, reportBtn, helpBtn };

        // Action Listeners
        bookingBtn.addActionListener(e -> {
            resetNavButtonColors();
            bookingBtn.setBackground(Color.decode("#30C142"));
            bookingBtn.setForeground(Color.WHITE);
            showBookingSubMenu();
        });

        folBtn.addActionListener(e -> {
            resetNavButtonColors();
            folBtn.setBackground(Color.decode("#30C142"));
            folBtn.setForeground(Color.WHITE);
            JOptionPane.showMessageDialog(this, "FoL Panel");
        });

        clientBtn.addActionListener(e -> {
            resetNavButtonColors();
            clientBtn.setBackground(Color.decode("#30C142"));
            clientBtn.setForeground(Color.WHITE);
            showClientPanel();
        });

        reportBtn.addActionListener(e -> {
            resetNavButtonColors();
            reportBtn.setBackground(Color.decode("#30C142"));
            reportBtn.setForeground(Color.WHITE);
            JOptionPane.showMessageDialog(this, "Reports Panel");
        });

        helpBtn.addActionListener(e -> {
            resetNavButtonColors();
            helpBtn.setBackground(Color.decode("#30C142"));
            helpBtn.setForeground(Color.WHITE);
            showHelpPanel();
        });

        // Add to nav bar
        navBar.add(bookingBtn);
        navBar.add(folBtn);
        navBar.add(clientBtn);
        navBar.add(reportBtn);
        navBar.add(helpBtn);

        return navBar;
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.BLACK);
        return btn;
    }

    private void resetNavButtonColors() {
        for (JButton btn : navButtons) {
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.BLACK);
        }
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
        subMenuPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Room Booking
        JButton roomBookingBtn = createSubmenuButton("Room Booking");
        roomBookingBtn.addActionListener(e -> showRoomList("room"));
        subMenuPanel.add(roomBookingBtn);

        // Hall Booking
        JButton hallBookingBtn = createSubmenuButton("Hall Booking");
        hallBookingBtn.addActionListener(e -> showRoomList("hall"));
        subMenuPanel.add(hallBookingBtn);

        // Group Booking
        JButton groupBtn = createSubmenuButton("Group Booking");
        groupBtn.addActionListener(e -> highlightButtonAndShow(groupBtn, "Group Booking Panel"));
        subMenuPanel.add(groupBtn);

        // Tour Booking
        JButton tourBtn = createSubmenuButton("Tour Booking");
        tourBtn.addActionListener(e -> highlightButtonAndShow(tourBtn, "Tour Booking Panel"));
        subMenuPanel.add(tourBtn);

        // Film Booking
        JButton filmBtn = createSubmenuButton("Film Booking");
        filmBtn.addActionListener(e -> highlightButtonAndShow(filmBtn, "Film Booking Panel"));
        subMenuPanel.add(filmBtn);

        subMenuPanel.revalidate();
        subMenuPanel.repaint();
    }

    private JButton createSubmenuButton(String label) {
        JButton btn = new JButton(label);
        btn.setBackground(Color.decode("#30C142"));
        btn.setForeground(Color.BLACK);
        return btn;
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
                roomBtn.setBackground(Color.decode("#30C142"));
                roomBtn.setForeground(Color.BLACK);
                roomBtn.addActionListener(e -> {
                    resetButtonColors();
                    roomBtn.setBackground(Color.decode("#30C142"));
                    subMenuPanel.removeAll();
                    subMenuPanel.setLayout(new BorderLayout());
                    subMenuPanel.add(new RoomBookingPanel(room.name), BorderLayout.CENTER);

                });
                subMenuPanel.add(roomBtn);
            }
        }

        JButton backBtn = new JButton("← Back to Booking");
        backBtn.setBackground(Color.decode("#30C142"));
        backBtn.setForeground(Color.BLACK);
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
                button.setForeground(Color.BLACK);
            }
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new MarketingPage().setVisible(true));
    }
}
