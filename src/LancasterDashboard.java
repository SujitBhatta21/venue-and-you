import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class LancasterDashboard extends JFrame {

    private final JPanel mainPanel;
    private JLabel logoLabel;
    private JPanel navbarPanel;
    private JPanel userPanel;
    private JPanel timelinePanel;
    private JPanel calendarPanel;

    public LancasterDashboard() {
        // Set up the frame
        setTitle("Lancaster's Music Hall - Marketing Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null); // Center on screen

        // Create the main panel
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.decode("#CCD1D2")); // Background color
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Create the top panel (header)
        JPanel headerPanel = new JPanel(new BorderLayout(5, 5));
        headerPanel.setBackground(Color.decode("#142524")); // Menu color

        // Logo in the top left
        logoLabel = new JLabel("Logo");
        logoLabel.setPreferredSize(new Dimension(150, 50));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(logoLabel, BorderLayout.WEST);

        // Navbar in the top middle
        navbarPanel = new JPanel();
        navbarPanel.setBackground(Color.decode("#142524"));
        JLabel navbarLabel = new JLabel("Navbar");
        navbarLabel.setForeground(Color.WHITE);
        navbarPanel.add(navbarLabel);
        headerPanel.add(navbarPanel, BorderLayout.CENTER);

        // User box in the top right
        userPanel = new JPanel();
        userPanel.setPreferredSize(new Dimension(100, 50));
        userPanel.setBackground(Color.decode("#142524"));
        JLabel userLabel = new JLabel("User");
        userLabel.setForeground(Color.WHITE);
        userPanel.add(userLabel);
        headerPanel.add(userPanel, BorderLayout.EAST);

        // Add header to the main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Create content panel (timeline + calendar)
        JPanel contentPanel = new JPanel(new BorderLayout(10, 0));
        contentPanel.setBackground(Color.decode("#CCD1D2"));

        // Timeline panel (left side)
        JPanel timelineContainer = new JPanel(new BorderLayout());
        timelineContainer.setBorder(new EmptyBorder(70, 0, 0, 0));
        timelineContainer.setBackground(Color.decode("#CCD1D2"));

        timelinePanel = new JPanel();
        timelinePanel.setLayout(new BoxLayout(timelinePanel, BoxLayout.Y_AXIS));
        timelinePanel.setBorder(BorderFactory.createTitledBorder("Timeline"));
        timelinePanel.setBackground(Color.decode("#CCD1D2"));

        // Add coursework items with booking buttons
        addBookingItem("17 March 2025", "The Green Room", "Prices 1 hour: £25 Morning/Afternoon: £75 Week: £600 ");
        addBookingItem("24 March 2025", "Dickens Den", "Prices 1 hour: £25 Morning/Afternoon: £75 Week: £600 ");
        addBookingItem("31 March 2025", "Poe Parlor", "Prices 1 hour: £25 Morning/Afternoon: £75 Week: £600 ");

        // Scroll pane for timeline
        JScrollPane timelineScrollPane = new JScrollPane(timelinePanel);
        timelineScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        timelineContainer.add(timelineScrollPane, BorderLayout.CENTER);

        contentPanel.add(timelineContainer, BorderLayout.CENTER);

        // Calendar panel (right side)
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.decode("#CCD1D2"));
        JLabel nameLabel = new JLabel("NAME");
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(nameLabel, BorderLayout.NORTH);

        calendarPanel = new JPanel(new BorderLayout());
        calendarPanel.setBorder(BorderFactory.createTitledBorder("Calendar"));
        calendarPanel.setBackground(Color.decode("#CCD1D2"));
        
        // Add CalendarBooking to the calendar panel
        CalendarBooking calendarBooking = new CalendarBooking();
        calendarPanel.add(calendarBooking.getCalendarPanel(), BorderLayout.CENTER);
        
        rightPanel.add(calendarPanel, BorderLayout.CENTER);
        rightPanel.setPreferredSize(new Dimension(300, 0));
        contentPanel.add(rightPanel, BorderLayout.EAST);

        // Add content panel to main panel
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Add main panel to frame
        setContentPane(mainPanel);
    }

    public static void openDashboard() {
        SwingUtilities.invokeLater(() -> {
            LancasterDashboard dashboard = new LancasterDashboard();
            dashboard.setVisible(true);
        });
    }

    private void addBookingItem(String date, String roomName, String price) {
        JPanel bookingPanel = new JPanel(new BorderLayout());
        bookingPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#848D94")));
        bookingPanel.setBackground(Color.decode("#CCD1D2"));
        bookingPanel.setPreferredSize(new Dimension(250, 60));

        JLabel dateLabel = new JLabel("<html><b>" + date + "</b></html>");
        JLabel roomLabel = new JLabel(roomName);
        JLabel priceLabel = new JLabel(price);
        priceLabel.setForeground(Color.decode("#30C142"));

        JButton bookButton = new JButton("Book Room");
        bookButton.setBackground(Color.decode("#30C142"));
        bookButton.setForeground(Color.WHITE);
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        LancasterDashboard.this,
                        "You have booked a room on " + date + " for " + price,
                        "Booking Confirmed",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        JPanel textPanel = new JPanel(new GridLayout(3, 1));
        textPanel.setBackground(Color.decode("#CCD1D2"));
        textPanel.add(dateLabel);
        textPanel.add(roomLabel);
        textPanel.add(priceLabel);

        bookingPanel.add(textPanel, BorderLayout.WEST);
        bookingPanel.add(bookButton, BorderLayout.EAST);

        timelinePanel.add(bookingPanel);
    }
}
