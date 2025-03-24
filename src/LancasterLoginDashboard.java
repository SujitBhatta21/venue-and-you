import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;

public class LancasterLoginDashboard extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;

    // Sample credentials (in a real application, these would be stored securely in a database)
    private static final String VALID_USERNAME = "marketing";
    private static final String VALID_PASSWORD = "password123";

    public LancasterLoginDashboard() {
        // Set up the frame
        setTitle("Lancaster's Music Hall - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center on screen

        // Create the main panel with some padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Add the title/header
        JLabel titleLabel = new JLabel("Lancaster's Music Hall");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create the login panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);

        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        loginPanel.add(usernameField, gbc);

        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(passwordField, gbc);

        // Login button
        loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 5, 5, 5);
        loginPanel.add(loginButton, gbc);

        // Status label for feedback
        statusLabel = new JLabel(" ");
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        loginPanel.add(statusLabel, gbc);

        // Add login panel to main panel
        mainPanel.add(loginPanel, BorderLayout.CENTER);

        // Add the footer
        JLabel footerLabel = new JLabel("Marketing Team Access Only");
        footerLabel.setHorizontalAlignment(JLabel.CENTER);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        mainPanel.add(footerLabel, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel);

        // Add action listener to login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attemptLogin();
            }
        });

        // Also enable login on Enter key press
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

        if (username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD)) {
            statusLabel.setText("Login successful!");
            statusLabel.setForeground(new Color(0, 128, 0)); // Green

            // In a real application, you would open the main dashboard here
            JOptionPane.showMessageDialog(this,
                    "Welcome to Lancaster's Music Hall System.\nMarketing Dashboard loading...",
                    "Login Successful",
                    JOptionPane.INFORMATION_MESSAGE);

            // For demo purposes, close this window
            // In a real app, you would launch the main dashboard instead
            // dispose();
            // new MarketingDashboard().setVisible(true);
        } else {
            statusLabel.setText("Invalid username or password!");
            statusLabel.setForeground(Color.RED);
            passwordField.setText("");
        }
    }

    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Launch the application
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LancasterLoginDashboard().setVisible(true);
            }
        });
    }
}