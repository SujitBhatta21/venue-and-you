import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginDashboard extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JLabel statusLabel;

    private ImageIcon logo; // edit

    private static final String DB_URL = "jdbc:mysql://sst-stuproj.city.ac.uk:3306/in2033t21";
    private static final String DB_USER = "in2033t21_a";
    private static final String DB_PASSWORD = "lrLUWCLVzDQ";

    public LoginDashboard() {
        setTitle("Lancaster's Music Hall - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(0xCCD1D2));

        logo = new ImageIcon("src/img/lancasters-music-hall-high-resolution-logo.png"); // lancaster logo
        Image logo1 = logo.getImage();
        Image scale = logo1.getScaledInstance(170, 135, Image.SCALE_SMOOTH);
        logo = new ImageIcon(scale);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(0xCCD1D2));

        JLabel titleLabel = new JLabel("Lancaster's Music Hall", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0x142524));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(0xCCD1D2));
        loginPanel.setBorder(BorderFactory.createLineBorder(new Color(0x848D94), 2));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        loginPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        // Logo image
        JLabel logoLabel = new JLabel(logo);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.SOUTHWEST; // Align to bottom-left
        loginPanel.add(logoLabel, gbc);

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0x30C142));
        loginButton.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 5, 5, 5);
        loginPanel.add(loginButton, gbc);

        statusLabel = new JLabel(" ", JLabel.CENTER);
        statusLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
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

            SwingUtilities.invokeLater(() -> {
                MarketingPage mp = new MarketingPage();
                mp.setVisible(true);
                dispose(); // Close login window after successful login
            });
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
}