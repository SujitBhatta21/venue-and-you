import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class FriendsPanel extends JPanel {
    private JTextField nameField, emailField, memberIdField;
    private JComboBox<String> statusFilter;
    private JPanel resultsPanel;
    private JScrollPane scrollPane;

    private JButton addMemberBtn, removeMemberBtn, renewMemberBtn, searchBtn;
    private JButton priorityBookingBtn, bookingReportsBtn, sendRemindersBtn;
    private String selectedMemberId = null;

    private static final String DB_URL = "jdbc:mysql://sst-stuproj.city.ac.uk:3306/in2033t21";
    private static final String DB_USER = "in2033t21_a";
    private static final String DB_PASSWORD = "lrLUWCLVzDQ";

    // Enhanced color scheme
    private static final Color AZTEC = new Color(20, 37, 36);
    private static final Color IRON = new Color(204, 209, 210);
    private static final Color APPLE = new Color(48, 193, 66);
    private static final Color OSLO_GRAY = new Color(132, 141, 148);

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    // Font constants for consistency
    private static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 14);

    public FriendsPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(IRON);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Top panel with filter and actions
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        // Results panel
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBackground(IRON);
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createLineBorder(OSLO_GRAY, 1));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Stats panel
        JPanel statsPanel = createStatsPanel();
        add(statsPanel, BorderLayout.SOUTH);

        updateResults();
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(IRON);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(IRON);
        JLabel titleLabel = new JLabel("Friends of Lancaster");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(AZTEC);
        titlePanel.add(titleLabel);

        // Filter Panel
        JPanel filterPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        filterPanel.setBackground(IRON);
        filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nameField = createStyledTextField();
        emailField = createStyledTextField();
        memberIdField = createStyledTextField();
        statusFilter = new JComboBox<>(new String[]{"All", "Active", "Inactive", "Expired"});
        statusFilter.setFont(NORMAL_FONT);
        statusFilter.setPreferredSize(new Dimension(150, 30));

        JLabel nameLabel = createStyledLabel("Name:");
        JLabel emailLabel = createStyledLabel("Email:");
        JLabel memberIdLabel = createStyledLabel("Member ID:");
        JLabel statusLabel = createStyledLabel("Status:");

        filterPanel.add(nameLabel); filterPanel.add(nameField);
        filterPanel.add(emailLabel); filterPanel.add(emailField);
        filterPanel.add(memberIdLabel); filterPanel.add(memberIdField);
        filterPanel.add(statusLabel); filterPanel.add(statusFilter);

        // Action Panel - using GridLayout
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(3, 3, 5, 5)); // 3 rows, 3 columns with spacing
        actionPanel.setBackground(IRON);
        actionPanel.setBorder(BorderFactory.createLineBorder(APPLE, 1));
        actionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addMemberBtn = new JButton("➕ Add Member");
        styleActionButton(addMemberBtn);

        renewMemberBtn = new JButton("🔄 Renew Membership");
        styleActionButton(renewMemberBtn);
        renewMemberBtn.setEnabled(false);

        removeMemberBtn = new JButton("🗑 Remove Selected");
        styleActionButton(removeMemberBtn);
        removeMemberBtn.setEnabled(false);

        searchBtn = new JButton("🔍 Search");
        styleActionButton(searchBtn);

        priorityBookingBtn = new JButton("🎟️ Priority Booking");
        styleActionButton(priorityBookingBtn);

        bookingReportsBtn = new JButton("📊 Booking Reports");
        styleActionButton(bookingReportsBtn);

        sendRemindersBtn = new JButton("📧 Send Reminders");
        styleActionButton(sendRemindersBtn);

        // Add buttons in a specific order to fit the grid
        actionPanel.add(addMemberBtn);
        actionPanel.add(renewMemberBtn);
        actionPanel.add(removeMemberBtn);
        actionPanel.add(searchBtn);
        actionPanel.add(priorityBookingBtn);
        actionPanel.add(bookingReportsBtn);
        actionPanel.add(new JLabel()); // Empty cell for spacing if needed
        actionPanel.add(new JLabel());
        actionPanel.add(sendRemindersBtn);

        topPanel.add(titlePanel);
        topPanel.add(filterPanel);
        topPanel.add(actionPanel);

        return topPanel;
    }

    private void styleActionButton(JButton button) {
        button.setBackground(new Color(240, 240, 240));
        button.setForeground(Color.BLACK);
        button.setFont(NORMAL_FONT);
        // Add ActionListeners here if not done inline
        if (button.getText().equals("➕ Add Member")) button.addActionListener(e -> showAddMemberDialog());
        if (button.getText().equals("🔄 Renew Membership")) button.addActionListener(e -> renewSelectedMember());
        if (button.getText().equals("🗑 Remove Selected")) button.addActionListener(e -> deleteSelectedMember());
        if (button.getText().equals("🔍 Search")) button.addActionListener(e -> updateResults());
        if (button.getText().equals("🎟️ Priority Booking")) button.addActionListener(e -> showPriorityBookingDialog());
        if (button.getText().equals("📊 Booking Reports")) button.addActionListener(e -> showBookingReportsDialog());
        if (button.getText().equals("📧 Send Reminders")) button.addActionListener(e -> showSendRemindersDialog());
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(NORMAL_FONT);
        field.setPreferredSize(new Dimension(150, 30));
        return field;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(TITLE_FONT);
        label.setForeground(AZTEC);
        return label;
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 20, 10));
        panel.setBackground(IRON);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            int[] stats = new int[4];
            String[] queries = {
                    "SELECT COUNT(*) as total FROM friends",
                    "SELECT COUNT(*) as active FROM friends WHERE status = 'active'",
                    "SELECT COUNT(*) as due FROM friends WHERE status = 'active' AND renewal_date BETWEEN CURRENT_DATE AND DATE_ADD(CURRENT_DATE, INTERVAL 30 DAY)",
                    "SELECT COUNT(*) as new FROM friends WHERE join_date >= DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY)"
            };

            for (int i = 0; i < queries.length; i++) {
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(queries[i])) {
                    if (rs.next()) {
                        stats[i] = rs.getInt(1);
                    }
                }
            }

            String[] statTitles = {"Total Members", "Active Members", "Renewals Due", "New This Month"};
            for (int i = 0; i < statTitles.length; i++) {
                panel.add(createStatCard(statTitles[i], String.valueOf(stats[i])));
            }
        } catch (SQLException ex) {
            // Placeholder cards if database error occurs
            String[] statTitles = {"Total Members", "Active Members", "Renewals Due", "New This Month"};
            for (String title : statTitles) {
                panel.add(createStatCard(title, "?"));
            }
        }

        return panel;
    }

    private JPanel createStatCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(OSLO_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(AZTEC);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 32));
        valueLabel.setForeground(AZTEC);
        valueLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        valueLabel.setHorizontalAlignment(JLabel.CENTER);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private void showAddMemberDialog() {
        // Create dialog for adding a new member with fields matching database structure
        JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Add New Friends Member", true);
        dialog.setSize(500, 400);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(this);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Add New Friends Member");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Form fields
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);
        formPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField nameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField emailField = new JTextField();

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField phoneField = new JTextField();

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"active", "inactive"});

        JLabel joinDateLabel = new JLabel("Join Date:");
        joinDateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField joinDateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);
        formPanel.add(statusLabel);
        formPanel.add(statusCombo);
        formPanel.add(joinDateLabel);
        formPanel.add(joinDateField);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());

        JButton okButton = new JButton("OK");
        okButton.setBackground(new Color(59, 130, 246)); // Blue color
        okButton.setForeground(Color.WHITE);
        okButton.addActionListener(e -> {
            // Validate fields
            if (nameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Name is required", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (emailField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Email is required", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Add member to database
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO friends (name, email, status, join_date, renewal_date, phone_number) VALUES (?, ?, ?, ?, ?, ?)");

                stmt.setString(1, nameField.getText().trim());
                stmt.setString(2, emailField.getText().trim());
                stmt.setString(3, statusCombo.getSelectedItem().toString());

                // Parse join date
                try {
                    Date joinDate = new SimpleDateFormat("yyyy-MM-dd").parse(joinDateField.getText().trim());
                    stmt.setDate(4, new java.sql.Date(joinDate.getTime()));

                    // Set renewal date to 1 year from join date
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(joinDate);
                    cal.add(Calendar.YEAR, 1);
                    stmt.setDate(5, new java.sql.Date(cal.getTimeInMillis()));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Invalid date format. Use yyyy-MM-dd", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                stmt.setString(6, phoneField.getText().trim());

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(dialog, "Member added successfully!");
                    updateResults();
                    dialog.dispose();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Error adding member: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(okButton);

        // Add spacing between components
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(formPanel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(buttonPanel);

        dialog.setContentPane(contentPanel);
        dialog.setVisible(true);
    }

    private void renewSelectedMember() {
        if (selectedMemberId == null) return;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Match column names exactly with the database structure
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE friends SET renewal_date = ?, status = 'active' WHERE friend_id = ?");

            // Set renewal date to 1 year from today
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, 1);
            stmt.setDate(1, new java.sql.Date(cal.getTimeInMillis()));
            stmt.setString(2, selectedMemberId);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Membership renewed successfully!");
                updateResults();
            } else {
                // Show error message
                JOptionPane.showMessageDialog(this,
                        "No membership found with ID: " + selectedMemberId,
                        "Message",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            // Show error
            JOptionPane.showMessageDialog(this,
                    "Renewal error: " + ex.getMessage(),
                    "Message",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelectedMember() {
        if (selectedMemberId == null) return;

        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete this Friends member?", "Confirm", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                PreparedStatement stmt = conn.prepareStatement(
                        "DELETE FROM friends WHERE friend_id = ?");
                stmt.setString(1, selectedMemberId);

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Member deleted.");
                    selectedMemberId = null;
                    removeMemberBtn.setEnabled(false);
                    renewMemberBtn.setEnabled(false);
                    updateResults();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Delete error: " + ex.getMessage());
            }
        }
    }

    private void updateResults() {
        resultsPanel.removeAll();

        // Get filter values
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String memberId = memberIdField.getText().trim();
        String status = statusFilter.getSelectedItem().toString();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = buildSearchQuery(name, email, memberId, status);
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                int paramIndex = 1;
                if (!name.isEmpty()) stmt.setString(paramIndex++, "%" + name + "%");
                if (!email.isEmpty()) stmt.setString(paramIndex++, "%" + email + "%");
                if (!memberId.isEmpty()) stmt.setString(paramIndex++, "%" + memberId + "%");
                if (!status.equals("All")) stmt.setString(paramIndex, status.toLowerCase());

                try (ResultSet rs = stmt.executeQuery()) {
                    boolean foundResults = false;
                    while (rs.next()) {
                        JPanel memberCard = createMemberCard(rs);
                        resultsPanel.add(memberCard);
                        resultsPanel.add(Box.createVerticalStrut(10)); // Add spacing between cards
                        foundResults = true;
                    }

                    if (!foundResults) {
                        JPanel noResultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                        noResultPanel.setBackground(IRON);
                        JLabel noResult = new JLabel("No matching members found.");
                        noResult.setFont(new Font("Arial", Font.BOLD, 16));
                        noResult.setForeground(Color.RED);
                        noResultPanel.add(noResult);
                        resultsPanel.add(noResultPanel);
                    }
                }
            }
        } catch (SQLException ex) {
            JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            errorPanel.setBackground(IRON);
            JLabel errorLabel = new JLabel("Database error: " + ex.getMessage());
            errorLabel.setFont(new Font("Arial", Font.BOLD, 16));
            errorLabel.setForeground(Color.RED);
            errorPanel.add(errorLabel);
            resultsPanel.add(errorPanel);
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private String buildSearchQuery(String name, String email, String memberId, String status) {
        StringBuilder query = new StringBuilder(
                "SELECT * FROM friends WHERE 1=1");

        if (!name.isEmpty()) query.append(" AND name LIKE ?");
        if (!email.isEmpty()) query.append(" AND email LIKE ?");
        if (!memberId.isEmpty()) query.append(" AND friend_id LIKE ?");
        if (!status.equals("All")) query.append(" AND status = ?");

        return query.toString();
    }

    private JPanel createMemberCard(ResultSet rs) throws SQLException {
        // Enhanced member card creation to match database structure
        String memberId = rs.getString("friend_id");
        String customerName = rs.getString("name");
        String status = rs.getString("status").toLowerCase();
        Date joinDate = rs.getDate("join_date");
        Date renewalDate = rs.getDate("renewal_date");

        // Get email and phone from the result set
        String email = rs.getString("email");
        String phoneNumber = rs.getString("phone_number");

        // Set background color based on status
        Color bgColor = Color.WHITE;
        if (status.equals("active")) {
            bgColor = new Color(235, 255, 235); // Light green for active
        } else if (status.equals("inactive")) {
            bgColor = new Color(240, 240, 240); // Light gray for inactive
        } else if (status.equals("expired")) {
            bgColor = new Color(255, 235, 235); // Light red for expired
        }

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(bgColor);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 85)); // Made taller to fit additional info

        // Make card selectable
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectedMemberId = memberId;
                renewMemberBtn.setEnabled(true);
                removeMemberBtn.setEnabled(true);
            }
        });

        // Left side panel with ID, name, email, phone
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(bgColor);

        JPanel idNamePanel = new JPanel();
        idNamePanel.setLayout(new BoxLayout(idNamePanel, BoxLayout.Y_AXIS));
        idNamePanel.setBackground(bgColor);

        JLabel idLabel = new JLabel("ID: " + memberId);
        idLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel nameLabel = new JLabel(customerName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        idNamePanel.add(idLabel);
        idNamePanel.add(nameLabel);

        // Add email and phone
        if (email != null && !email.isEmpty()) {
            JLabel emailLabel = new JLabel("Email: " + email);
            emailLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            idNamePanel.add(emailLabel);
        }

        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            JLabel phoneLabel = new JLabel("Phone: " + phoneNumber);
            phoneLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            idNamePanel.add(phoneLabel);
        }

        leftPanel.add(idNamePanel);

        // Right side panel with status and dates
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(bgColor);

        // Create status indicator
        JPanel statusBox = new JPanel();
        statusBox.setPreferredSize(new Dimension(15, 15));
        if (status.equals("active")) {
            statusBox.setBackground(new Color(0, 180, 0)); // Green for active
        } else if (status.equals("inactive")) {
            statusBox.setBackground(Color.GRAY); // Gray for inactive
        } else if (status.equals("expired")) {
            statusBox.setBackground(Color.RED); // Red for expired
        }

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(bgColor);

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        statusPanel.setBackground(bgColor);

        JLabel statusLabel = new JLabel("Status: " + status.substring(0, 1).toUpperCase() + status.substring(1) + " ");
        statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        statusPanel.add(statusLabel);
        statusPanel.add(statusBox);

        JLabel joinLabel = new JLabel("Join: " + dateFormat.format(joinDate));
        joinLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel renewalLabel = new JLabel("Renewal: " + dateFormat.format(renewalDate));
        renewalLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        infoPanel.add(statusPanel);
        infoPanel.add(joinLabel);
        infoPanel.add(renewalLabel);

        rightPanel.add(infoPanel);

        card.add(leftPanel, BorderLayout.WEST);
        card.add(rightPanel, BorderLayout.EAST);

        return card;
    }

    private void showPriorityBookingDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Priority Booking", true);
        dialog.setSize(500, 400);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(this);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Set Priority Booking for Friends Members");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);
        formPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));

        // Show dropdown populated from the database
        JComboBox<String> showCombo = new JComboBox<>();
        populateShowDropdown(showCombo);

        // Seat blocks
        JTextField seatBlocksField = new JTextField("A1-A20, B1-B15");

        // Number of seats
        JSpinner seatCountSpinner = new JSpinner(new SpinnerNumberModel(35, 1, 100, 1));

        // Priority booking dates
        JSpinner startDateSpinner = new JSpinner(new SpinnerDateModel());
        startDateSpinner.setEditor(new JSpinner.DateEditor(startDateSpinner, "yyyy-MM-dd"));

        JSpinner endDateSpinner = new JSpinner(new SpinnerDateModel());
        endDateSpinner.setEditor(new JSpinner.DateEditor(endDateSpinner, "yyyy-MM-dd"));

        // Notification options
        JCheckBox notifyBoxOfficeCheck = new JCheckBox("Send notification", true);
        JCheckBox notifyMembersCheck = new JCheckBox("Notify Friends members", true);

        // Add components to form
        formPanel.add(new JLabel("Show:"));
        formPanel.add(showCombo);
        formPanel.add(new JLabel("Reserved Seat Blocks:"));
        formPanel.add(seatBlocksField);
        formPanel.add(new JLabel("Number of Seats:"));
        formPanel.add(seatCountSpinner);
        formPanel.add(new JLabel("Priority Start Date:"));
        formPanel.add(startDateSpinner);
        formPanel.add(new JLabel("Priority End Date:"));
        formPanel.add(endDateSpinner);
        formPanel.add(new JLabel("Notifications:"));
        JPanel checkboxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkboxPanel.setBackground(Color.WHITE);
        checkboxPanel.add(notifyBoxOfficeCheck);
        checkboxPanel.add(notifyMembersCheck);
        formPanel.add(checkboxPanel);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());

        JButton saveButton = new JButton("Save Priority Booking");
        saveButton.setBackground(APPLE);
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> {
            // In a real implementation, you would likely store the selected
            // show_id (or title/date) along with other priority booking details
            // in another database table.
            String selectedShow = (String) showCombo.getSelectedItem();
            String seats = seatBlocksField.getText();
            int numSeats = (Integer) seatCountSpinner.getValue();
            Date start = (Date) startDateSpinner.getValue();
            Date end = (Date) endDateSpinner.getValue();
            boolean notifyBoxOffice = notifyBoxOfficeCheck.isSelected();
            boolean notifyMembers = notifyMembersCheck.isSelected();

            JOptionPane.showMessageDialog(dialog,
                    "Priority booking settings:\n" +
                            "Show: " + selectedShow + "\n" +
                            "Seats: " + seats + " (" + numSeats + ")\n" +
                            "Period: " + dateFormat.format(start) + " to " + dateFormat.format(end) + "\n" +
                            "Notify Box Office: " + notifyBoxOffice + "\n" +
                            "Notify Members: " + notifyMembers);
            dialog.dispose();
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        // Assemble panels
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(formPanel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(buttonPanel);

        dialog.setContentPane(contentPanel);
        dialog.setVisible(true);
    }

    // Helper method to populate the show dropdown from the database
    private void populateShowDropdown(JComboBox<String> comboBox) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT title, date FROM shows")) {
            while (rs.next()) {
                String title = rs.getString("title");
                Date date = rs.getDate("date");
                SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
                comboBox.addItem(title + " - " + sdf.format(date));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error fetching shows: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showBookingReportsDialog() {
        JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Booking Reports", true);
        dialog.setSize(650, 500);
        dialog.setLocationRelativeTo(this);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Current bookings tab
        JPanel currentBookingsPanel = new JPanel(new BorderLayout());

        String[] columns = {"Show", "Date", "Friends Tickets", "Revenue", "% of Total"};

        // Sample data - in a real implementation, this would come from database
        Object[][] data = {
                {"Hamlet", "Feb 15, 2025", 28, "£840", "12%"},
                {"The Importance of Being Earnest", "Feb 28, 2025", 22, "£660", "10%"},
                {"A Midsummer Night's Dream", "Mar 10, 2025", 15, "£450", "7%"},
                {"Romeo and Juliet", "Apr 5, 2025", 30, "£900", "14%"},
                {"Macbeth", "Apr 20, 2025", 18, "£540", "9%"}
        };

        JTable bookingsTable = new JTable(data, columns);
        bookingsTable.setFillsViewportHeight(true);
        JScrollPane scrollPane1 = new JScrollPane(bookingsTable);

        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        statsPanel.add(new JLabel("Total Tickets: 113"));
        statsPanel.add(new JLabel("Total Revenue: £3,390"));
        statsPanel.add(new JLabel("Avg. per Show: 22.6"));

        currentBookingsPanel.add(statsPanel, BorderLayout.NORTH);
        currentBookingsPanel.add(scrollPane1, BorderLayout.CENTER);

        // YoY comparison tab
        JPanel comparisonPanel = new JPanel(new BorderLayout());

        String[] compColumns = {"Year", "Total Members", "Tickets Booked", "Avg per Member", "Growth"};
        Object[][] compData = {
                {"2025", 124, 468, 3.8, "+12%"},
                {"2024", 110, 418, 3.8, "+8%"},
                {"2023", 102, 387, 3.8, "N/A"}
        };
        JTable comparisonTable = new JTable(compData, compColumns);
        comparisonTable.setFillsViewportHeight(true);
        JScrollPane scrollPane2 = new JScrollPane(comparisonTable);
        comparisonPanel.add(scrollPane2, BorderLayout.CENTER);

        tabbedPane.addTab("Current Bookings", currentBookingsPanel);
        tabbedPane.addTab("Year-over-Year Comparison", comparisonPanel);

        dialog.setContentPane(tabbedPane);
        dialog.setVisible(true);
}

    private void showSendRemindersDialog() {
        JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Send Reminders", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Send Membership Renewal Reminders");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        optionsPanel.setBackground(Color.WHITE);
        JCheckBox upcomingRenewalsCheckbox = new JCheckBox("Remind members with renewals due in the next 30 days", true);
        JCheckBox expiredMembersCheckbox = new JCheckBox("Remind expired members", false);
        optionsPanel.add(upcomingRenewalsCheckbox);
        optionsPanel.add(expiredMembersCheckbox);
        optionsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());

        JButton sendButton = new JButton("Send Reminders");
        sendButton.setBackground(APPLE);
        sendButton.setForeground(Color.WHITE);
        sendButton.addActionListener(e -> {
            // In a real application, this would trigger email sending logic
            StringBuilder message = new StringBuilder("Reminders will be sent to:\n");
            if (upcomingRenewalsCheckbox.isSelected()) {
                message.append("- Members with upcoming renewals\n");
            }
            if (expiredMembersCheckbox.isSelected()) {
                message.append("- Expired members\n");
            }
            JOptionPane.showMessageDialog(dialog, message.toString(), "Sending Reminders", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(sendButton);

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(optionsPanel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(buttonPanel);

        dialog.setContentPane(contentPanel);
        dialog.setVisible(true);
    }
}
