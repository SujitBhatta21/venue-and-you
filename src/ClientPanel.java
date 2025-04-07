import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.UUID;

public class ClientPanel extends JPanel {

    private JTextField nameField, cityField, contactField;
    private JPanel resultsPanel;
    private JScrollPane scrollPane;

    private JButton addClientBtn, removeClientBtn;
    private String selectedClientId = null; // Track which client is selected

    private static final String DB_URL = "jdbc:mysql://sst-stuproj.city.ac.uk:3306/in2033t21";
    private static final String DB_USER = "in2033t21_a";
    private static final String DB_PASSWORD = "lrLUWCLVzDQ";

    public ClientPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#CCD1D2")); // Background

        // FILTER SECTION
        JPanel filterPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        filterPanel.setBackground(Color.decode("#CCD1D2"));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nameField = new JTextField();
        cityField = new JTextField();
        contactField = new JTextField();

        filterPanel.add(new JLabel("Company Name:"));
        filterPanel.add(nameField);
        filterPanel.add(new JLabel("City:"));
        filterPanel.add(cityField);
        filterPanel.add(new JLabel("Contact Name:"));
        filterPanel.add(contactField);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBackground(Color.decode("#30C142"));
        searchBtn.setForeground(Color.BLACK);
        searchBtn.addActionListener(e -> updateResults());

        filterPanel.add(new JLabel()); // filler
        filterPanel.add(searchBtn);

        // ---- BUTTON PANEL Add/Del ----
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionPanel.setBackground(Color.decode("#CCD1D2"));

        addClientBtn = new JButton("➕ Add Client");
        addClientBtn.setBackground(Color.decode("#30C142"));
        addClientBtn.setForeground(Color.BLACK);
        addClientBtn.addActionListener(e -> showAddClientDialog());

        removeClientBtn = new JButton("🗑 Remove Selected Client");
        removeClientBtn.setBackground(Color.RED);
        removeClientBtn.setForeground(Color.BLACK);
        removeClientBtn.setEnabled(false);
        removeClientBtn.addActionListener(e -> deleteSelectedClient());

        actionPanel.add(addClientBtn);
        actionPanel.add(removeClientBtn);

        // Combine filter + action panels into one top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(Color.decode("#CCD1D2"));

        topPanel.add(filterPanel);
        topPanel.add(actionPanel);

        add(topPanel, BorderLayout.NORTH);

        // RESULT PANEL (Cards go here)
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBackground(Color.decode("#CCD1D2"));

        scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        updateResults();
    }

    private void showAddClientDialog() {
        JTextField name = new JTextField();
        JTextField contact = new JTextField();
        JTextField email = new JTextField();
        JTextField phone = new JTextField();
        JTextField address = new JTextField();
        JTextField city = new JTextField();
        JTextField postcode = new JTextField();
        JTextField billingName = new JTextField();
        JTextField billingEmail = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Company Name:"));
        panel.add(name);
        panel.add(new JLabel("Contact Name:"));
        panel.add(contact);
        panel.add(new JLabel("Contact Email:"));
        panel.add(email);
        panel.add(new JLabel("Phone Number:"));
        panel.add(phone);
        panel.add(new JLabel("Street Address:"));
        panel.add(address);
        panel.add(new JLabel("City:"));
        panel.add(city);
        panel.add(new JLabel("Postcode:"));
        panel.add(postcode);
        panel.add(new JLabel("Billing Name:"));
        panel.add(billingName);
        panel.add(new JLabel("Billing Email:"));
        panel.add(billingEmail);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Client", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO companies (company_id, company_name, contact_name, contact_email, phone_number, street_address, city, postcode, billing_name, billing_email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                String companyId = "CID" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

                stmt.setString(1, companyId);
                stmt.setString(2, name.getText());
                stmt.setString(3, contact.getText());
                stmt.setString(4, email.getText());
                stmt.setString(5, phone.getText());
                stmt.setString(6, address.getText());
                stmt.setString(7, city.getText());
                stmt.setString(8, postcode.getText());
                stmt.setString(9, billingName.getText());
                stmt.setString(10, billingEmail.getText());

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Client added successfully!");
                updateResults();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error adding client: " + ex.getMessage());
            }
        }
    }

    private void deleteSelectedClient() {
        if (selectedClientId == null)
            return;

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this client?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM companies WHERE company_id = ?");
                stmt.setString(1, selectedClientId);
                int rows = stmt.executeUpdate();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Client deleted.");
                    selectedClientId = null;
                    removeClientBtn.setEnabled(false);
                    updateResults();
                } else {
                    JOptionPane.showMessageDialog(this, "Client not found.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error deleting client: " + ex.getMessage());
            }
        }
    }

    private void updateResults() {
        resultsPanel.removeAll();

        String company = nameField.getText().trim();
        String city = cityField.getText().trim();
        String contact = contactField.getText().trim();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            StringBuilder query = new StringBuilder("SELECT * FROM companies WHERE 1=1");

            if (!company.isEmpty())
                query.append(" AND company_name LIKE ?");
            if (!city.isEmpty())
                query.append(" AND city LIKE ?");
            if (!contact.isEmpty())
                query.append(" AND contact_name LIKE ?");

            PreparedStatement stmt = conn.prepareStatement(query.toString());

            int index = 1;
            if (!company.isEmpty())
                stmt.setString(index++, "%" + company + "%");
            if (!city.isEmpty())
                stmt.setString(index++, "%" + city + "%");
            if (!contact.isEmpty())
                stmt.setString(index++, "%" + contact + "%");

            ResultSet rs = stmt.executeQuery();
            boolean found = false;

            while (rs.next()) {
                found = true;
                resultsPanel.add(createClientCard(rs));
            }

            if (!found) {
                JLabel noResult = new JLabel("No matching companies found.");
                noResult.setForeground(Color.RED);
                resultsPanel.add(noResult);
            }

        } catch (SQLException ex) {
            resultsPanel.add(new JLabel("Database error: " + ex.getMessage()));
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private JPanel createClientCard(ResultSet rs) throws SQLException {
        String companyId = rs.getString("company_id");
        Client client = new Client(
                rs.getString("company_name"),
                rs.getString("contact_name"),
                rs.getString("contact_email"),
                rs.getString("phone_number"),
                rs.getString("street_address") + ", " + rs.getString("city") + ", " + rs.getString("postcode"),
                rs.getString("billing_name"),
                rs.getString("billing_email"));

        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.decode("#848D94"), 1));
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(800, 60));

        JLabel nameLabel = new JLabel("Company: " + client.companyName);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JButton moreBtn = new JButton("Show Bookings / More");
        moreBtn.setBackground(Color.darkGray);
        moreBtn.setForeground(Color.BLACK);
        moreBtn.addActionListener(e -> showClientDetailsDialog(client));

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Update selection
                selectedClientId = companyId;
                removeClientBtn.setEnabled(true);
                // Reset background for all cards
                for (Component c : resultsPanel.getComponents()) {
                    if (c instanceof JPanel) {
                        c.setBackground(Color.WHITE);
                    }
                }
                card.setBackground(Color.LIGHT_GRAY); // selected card
            }
        });

        card.add(nameLabel, BorderLayout.WEST);
        card.add(moreBtn, BorderLayout.EAST);
        return card;
    }

    private void showClientDetailsDialog(Client client) {
        StringBuilder details = new StringBuilder();
        details.append("Company: ").append(client.companyName).append("\n");
        details.append("Contact: ").append(client.contactName).append("\n");
        details.append("Email: ").append(client.contactEmail).append("\n");
        details.append("Phone: ").append(client.phone).append("\n");
        details.append("Address: ").append(client.address).append("\n");
        details.append("Billing Name: ").append(client.billingName).append("\n");
        details.append("Billing Email: ").append(client.billingEmail).append("\n");

        JTextArea textArea = new JTextArea(details.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        JOptionPane.showMessageDialog(this, scrollPane, "Client Details", JOptionPane.INFORMATION_MESSAGE);
    }

}