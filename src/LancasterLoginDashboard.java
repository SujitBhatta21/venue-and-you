import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LancasterLoginDashboard extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JLabel statusLabel;

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://sst-stuproj.city.ac.uk:3306/in2033t21";
    private static final String DB_USER = "in2033t21_a";
    private static final String DB_PASSWORD = "lrLUWCLVzDQ";

    public LancasterLoginDashboard() {
        setTitle("Lancaster's Music Hall - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Increased window size
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(0xCCD1D2)); // Background color

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(0xCCD1D2));

        JLabel titleLabel = new JLabel("Lancaster's Music Hall", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0x142524)); // Menu/Secondary color
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(0xCCD1D2));
        loginPanel.setBorder(BorderFactory.createLineBorder(new Color(0x848D94), 2)); // Subtle border
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);
        gbc.gridx = 0; gbc.gridy = 0;
        loginPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        loginPanel.add(usernameField, gbc);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        gbc.gridx = 0; gbc.gridy = 1;
        loginPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        // Login button
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0x30C142)); // Apple color
        loginButton.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 5, 5, 5);
        loginPanel.add(loginButton, gbc);

        // Status label
        statusLabel = new JLabel(" ", JLabel.CENTER);
        statusLabel.setForeground(Color.RED);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        loginPanel.add(statusLabel, gbc);

        mainPanel.add(loginPanel, BorderLayout.CENTER);

        JLabel footerLabel = new JLabel("Marketing Team Access Only", JLabel.CENTER);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerLabel.setForeground(new Color(0x142524));
        mainPanel.add(footerLabel, BorderLayout.SOUTH);

        add(mainPanel);

        loginButton.addActionListener(e -> attemptLogin());
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    attemptLogin();
                }
            }
        });
    }

    private void attemptLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (validateLogin(username, password)) {
            statusLabel.setText("Login successful!");
            statusLabel.setForeground(new Color(0x30C142));
            JOptionPane.showMessageDialog(this, "Welcome to Lancaster's Music Hall System.", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
        } else {
            statusLabel.setText("Invalid username or password!");
            statusLabel.setForeground(Color.RED);
            passwordField.setText("");
        }
    }

    private boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE username=? AND password=?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection error!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new LancasterLoginDashboard().setVisible(true));
    }
}