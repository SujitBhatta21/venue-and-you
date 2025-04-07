import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class HelpPanel extends JPanel {
    // Color palette
    private static final Color AZTEC = Color.decode("#142524");
    private static final Color IRON = Color.decode("#CCD1D2");
    private static final Color APPLE = Color.decode("#30C142");
    private static final Color OSLO_GRAY = Color.decode("#848D94");

    private JTextArea contentArea;
    private Map<String, String> helpSections;

    public HelpPanel() {
        setLayout(new BorderLayout());
        setBackground(IRON);

        // Create help sections
        helpSections = createHelpSections();

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(AZTEC);

        // Create buttons for each section
        String[] sectionNames = {
                "Team Overview",
                "Contact Information",
                "Technical Support",
                "Marketing Database",
                "Troubleshooting"
        };

        for (String name : sectionNames) {
            JButton btn = new JButton(name);
            btn.setBackground(APPLE);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 12));
            btn.setBorder(BorderFactory.createLineBorder(OSLO_GRAY));
            btn.addActionListener(e -> updateContent(name));
            buttonPanel.add(btn);
        }

        add(buttonPanel, BorderLayout.NORTH);

        // Create content area
        contentArea = new JTextArea();
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);

        // Custom font and styling
        Font customFont = new Font("Arial", Font.PLAIN, 14);
        contentArea.setFont(customFont);
        contentArea.setBackground(Color.WHITE);
        contentArea.setForeground(AZTEC);
        contentArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(OSLO_GRAY)));

        // Initial content
        updateContent("Team Overview");

        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBackground(IRON);
        add(scrollPane, BorderLayout.CENTER);
    }

    private Map<String, String> createHelpSections() {
        Map<String, String> sections = new HashMap<>();

        sections.put("Team Overview",
                "The Marketing Team at Lancaster's Music Hall plays a crucial role in:\n" +
                        "• Advertising shows and the venue\n" +
                        "• Handling venue inquiries\n" +
                        "• Managing bookings\n" +
                        "• Maximizing venue utilization\n\n" +
                        "Our core values include:\n" +
                        "• Supporting local, national, and international talent\n" +
                        "• Sustainability\n" +
                        "• Efficient use of technology to reduce manual processes");

        sections.put("Contact Information",
                "Lancaster's Music Hall - Contact Information\n\n" +
                        "• Joe Lancaster\n" +
                        "• Robert Lancaster\n" +
                        "• Venue Address: 5374 Main Street City, County WC2N 5DN\n" +
                        "• Venue Phone Number: 020 7946 5374\n\n");

        sections.put("Technical Support",
                "Technical Support Guidelines:\n" +
                        "For any technical issues with the new system, please contact Venue & You at:\n" +
                        "• Email: support@venueandyou.com\n" +
                        "• Phone: 020 7123 4567\n\n" +
                        "• Common Issues (that we can help with):\n" +
                        " - Calendar Synchronization\n" +
                        " - Booking System Access\n" +
                        " - Database Connection Errors\n\n" +
                        "• Troubleshooting Steps (you can try first):\n" +
                        " 1. Restart the application\n" +
                        " 2. Check your internet connection\n" +
                        " 3. Verify your login credentials\n" +
                        " 4. Clear the application cache");

        sections.put("Marketing Database",
                "The Marketing application helps manage key aspects of Lancaster's Music Hall operations. It includes tools for:\n\n" +
                        "• Booking Management: This feature allows the team to view the venue's calendar, book available spaces and manage event details.\n\n" +
                        "• Friends of Lancaster Management: This section is for managing the 'Friends' membership program, including member information, priority bookings, and communication.\n\n" +
                        "• Client Management: Here, the team can store and organize information about clients, track interactions, and manage communications.\n\n" +
                        "• Reporting: The application provides tools to generate reports on bookings, ticket sales, and other relevant data to help with analysis and decision-making.\n\n" +
                        "• General Information: The application also stores general information, which could include venue details, standard contracts, and pricing information.\n");


        sections.put("Troubleshooting",
                "Troubleshooting Guide:\n" +
                        "• Login Issues:\n" +
                        "  - Forgotten Password: Use 'Reset Password'\n" +
                        "  - Account Locked: Contact IT Support\n\n" +
                        "• Performance Problems:\n" +
                        "  - Slow System: Close unnecessary apps\n" +
                        "  - Frequent Crashes: Update to latest version\n\n" +
                        "• Data Backup:\n" +
                        "  - Automatic Backup: Daily at 11 PM\n" +
                        "  - Manual Backup: Export data weekly");

        return sections;
    }

    private void updateContent(String section) {
        contentArea.setText(section + "\n\n" + helpSections.get(section));
        contentArea.setCaretPosition(0); // Scroll to top
    }
}
