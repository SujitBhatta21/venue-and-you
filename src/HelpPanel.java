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
                BorderFactory.createLineBorder(OSLO_GRAY)
        ));

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
                "The Marketing Team at Lancaster Theatre plays a crucial role in:\n" +
                        "• Advertising shows and the venue\n" +
                        "• Handling venue inquiries\n" +
                        "• Managing bookings\n" +
                        "• Maximizing venue utilization\n\n" +
                        "Our core values include:\n" +
                        "• Supporting local, national, and international talent\n" +
                        "• Sustainability\n" +
                        "• Efficient use of technology to reduce manual processes"
        );

        sections.put("Contact Information",
                "Contact Details:\n" +
                        "• Joe Lancaster (Marketing Team Lead)\n" +
                        "  - Email: joe.lancaster@lancastertheatre.co.uk\n" +
                        "  - Phone: +44 (0)1234 567890\n\n" +
                        "• Robert Lancaster (Operations Coordinator)\n" +
                        "  - Email: robert.lancaster@lancastertheatre.co.uk\n" +
                        "  - Phone: +44 (0)1234 567891\n\n" +
                        "• IT Support\n" +
                        "  - Email: it.support@lancastertheatre.co.uk\n" +
                        "  - Phone: +44 (0)1234 567892"
        );

        sections.put("Technical Support",
                "Technical Support Guidelines:\n" +
                        "• Common Issues:\n" +
                        "  - Calendar Synchronization\n" +
                        "  - Booking System Access\n" +
                        "  - Database Connection Errors\n\n" +
                        "• Troubleshooting Steps:\n" +
                        "  1. Restart the application\n" +
                        "  2. Check internet connection\n" +
                        "  3. Verify login credentials\n" +
                        "  4. Clear application cache"
        );

        sections.put("Marketing Database",
                "Marketing Database Requirements:\n" +
                        "• Key Data Tracking:\n" +
                        "  - Friends of Lancaster Membership\n" +
                        "  - Client Contact Information\n" +
                        "  - Booking History\n" +
                        "  - Ticket Sales Tracking\n\n" +
                        "• Desired Features:\n" +
                        "  - Easy Contact Management\n" +
                        "  - Automated Reminder System\n" +
                        "  - Reporting and Analytics\n" +
                        "  - Secure Data Storage"
        );

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
                        "  - Manual Backup: Export data weekly"
        );

        return sections;
    }

    private void updateContent(String section) {
        contentArea.setText(section + "\n\n" + helpSections.get(section));
        contentArea.setCaretPosition(0); // Scroll to top
    }

    // For testing purposes
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Help Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.add(new HelpPanel());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
