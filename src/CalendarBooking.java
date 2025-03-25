import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;

/**
 * CalendarBookingApp is a Swing-based calendar application that displays booked
 * dates
 * with persistent storage between application sessions.
 */
public class CalendarBooking {
    private static Map<String, Set<Integer>> monthlyBookings = new HashMap<>();
    private static int currentYear = 2025;
    private static int currentMonth = Calendar.MARCH;
    private static JLabel monthYearLabel;
    private static DefaultTableModel model;
    private static final String[] MONTH_NAMES = { "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December" };
    private static final String BOOKINGS_FILE = "bookings.dat";

    /**
     * Main method to launch the application.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Load booked dates before creating the GUI
        loadBookedDates();
        SwingUtilities.invokeLater(CalendarBooking::createAndShowGUI);
    }

    /**
     * Saves booked dates to a file.
     */
    private static void saveBookedDates() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BOOKINGS_FILE))) {
            oos.writeObject(monthlyBookings);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Error saving bookings: " + e.getMessage(),
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Loads booked dates from a file.
     */
    private static void loadBookedDates() {
        File file = new File(BOOKINGS_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Object loadedObject = ois.readObject();

                // Handle backward compatibility
                if (loadedObject instanceof Map) {
                    // New format: Map of monthly bookings
                    monthlyBookings = (Map<String, Set<Integer>>) loadedObject;
                } else if (loadedObject instanceof ArrayList) {
                    // Old format: List of booked dates
                    Set<Integer> oldBookedDates = new HashSet<>((ArrayList<Integer>) loadedObject);

                    // Convert old format to new format
                    monthlyBookings = new HashMap<>();
                    String currentMonthKey = currentYear + "-" + currentMonth;
                    monthlyBookings.put(currentMonthKey, oldBookedDates);
                }
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null,
                        "Error loading bookings: " + e.getMessage(),
                        "Load Error",
                        JOptionPane.ERROR_MESSAGE);
                monthlyBookings = new HashMap<>();
            }
        } else {
            // Start with an empty map of bookings
            monthlyBookings = new HashMap<>();
        }
    }

    /**
     * Get booked dates for the current month.
     * 
     * @return Set of booked dates for the current month
     */
    private static Set<Integer> getCurrentMonthBookedDates() {
        String key = currentYear + "-" + currentMonth;
        return monthlyBookings.getOrDefault(key, new HashSet<>());
    }

    /**
     * Creates and displays the main application window.
     */
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Calendar Booking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.getContentPane().setBackground(Color.decode("#CCD1D2")); // Background color
        frame.setLayout(new BorderLayout(10, 10));

        // Top panel for navigation controls
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.decode("#142524"));

        // Month/year navigation
        JPanel navigationPanel = new JPanel(new FlowLayout());
        navigationPanel.setBackground(Color.decode("#142524"));

        JButton prevButton = new JButton("◀");
        prevButton.setFocusPainted(false);
        prevButton.addActionListener(e -> changeMonth(-1));

        monthYearLabel = new JLabel(MONTH_NAMES[currentMonth] + " " + currentYear, JLabel.CENTER);
        monthYearLabel.setForeground(Color.WHITE);
        monthYearLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JButton nextButton = new JButton("▶");
        nextButton.setFocusPainted(false);
        nextButton.addActionListener(e -> changeMonth(1));

        navigationPanel.add(prevButton);
        navigationPanel.add(monthYearLabel);
        navigationPanel.add(nextButton);

        topPanel.add(navigationPanel, BorderLayout.CENTER);

        // Calendar table
        String[] columns = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                JComponent comp = (JComponent) super.prepareRenderer(renderer, row, column);

                // Add padding to cells
                comp.setBorder(new EmptyBorder(5, 5, 5, 5));

                String value = (String) getValueAt(row, column);
                if (value != null && !value.isEmpty()) {
                    try {
                        int day = Integer.parseInt(value);
                        Set<Integer> bookedDates = getCurrentMonthBookedDates();
                        if (bookedDates.contains(day)) {
                            comp.setBackground(Color.decode("#30C142")); // Booked dates color
                            comp.setForeground(Color.white);
                        } else {
                            comp.setBackground(Color.white);
                            comp.setForeground(Color.black);
                        }
                    } catch (NumberFormatException e) {
                        comp.setBackground(Color.white);
                        comp.setForeground(Color.black);
                    }
                } else {
                    comp.setBackground(Color.white);
                    comp.setForeground(Color.black);
                }

                return comp;
            }
        };

        // Add click listener to book/unbook dates
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());

                if (row >= 0 && col >= 0) {
                    String value = (String) table.getValueAt(row, col);
                    if (value != null && !value.isEmpty()) {
                        try {
                            int day = Integer.parseInt(value);
                            String key = currentYear + "-" + currentMonth;
                            Set<Integer> bookedDates = monthlyBookings.computeIfAbsent(key, k -> new HashSet<>());

                            if (bookedDates.contains(day)) {
                                bookedDates.remove(day);
                                JOptionPane.showMessageDialog(frame,
                                        "Unbooked date: " + day + " " + MONTH_NAMES[currentMonth] + " " + currentYear,
                                        "Date Unbooked", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                bookedDates.add(day);
                                JOptionPane.showMessageDialog(frame,
                                        "Booked date: " + day + " " + MONTH_NAMES[currentMonth] + " " + currentYear,
                                        "Date Booked", JOptionPane.INFORMATION_MESSAGE);
                            }
                            // Save bookings immediately after each change
                            saveBookedDates();
                            table.repaint();
                        } catch (NumberFormatException ex) {
                            // Not a valid day number
                        }
                    }
                }
            }
        });

        table.setRowHeight(45);
        table.setGridColor(Color.decode("#848D94")); // Grid color
        table.setShowGrid(true);
        table.getTableHeader().setBackground(Color.decode("#142524")); // Header background
        table.getTableHeader().setForeground(Color.white);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        // Add legend panel
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        legendPanel.setBackground(Color.decode("#CCD1D2"));

        JPanel bookedColor = new JPanel();
        bookedColor.setBackground(Color.decode("#30C142"));
        bookedColor.setPreferredSize(new Dimension(20, 20));

        JLabel legendLabel = new JLabel("  Booked Date");
        legendPanel.add(bookedColor);
        legendPanel.add(legendLabel);

        JLabel instructionLabel = new JLabel("  (Click on a date to book or unbook)");
        instructionLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        legendPanel.add(instructionLabel);

        // Refresh calendar
        fillCalendar();

        // Add components to frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(legendPanel, BorderLayout.SOUTH);

        // Center the frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Changes the current month by the specified amount.
     * 
     * @param delta The amount to change (-1 for previous month, 1 for next month)
     */
    private static void changeMonth(int delta) {
        Calendar calendar = new GregorianCalendar(currentYear, currentMonth, 1);
        calendar.add(Calendar.MONTH, delta);

        // Save current month's bookings before switching
        saveBookedDates();

        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);

        monthYearLabel.setText(MONTH_NAMES[currentMonth] + " " + currentYear);
        fillCalendar();
    }

    /**
     * Fills the calendar with dates for the current month and year.
     * Booked dates are automatically highlighted.
     */
    private static void fillCalendar() {
        model.setRowCount(0); // Clear existing data

        Calendar calendar = new GregorianCalendar(currentYear, currentMonth, 1);
        int startDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        Vector<String> row = new Vector<>(Collections.nCopies(7, ""));
        for (int i = 1; i <= daysInMonth; i++) {
            int column = (startDay + i - 1) % 7;
            row.set(column, String.valueOf(i));

            if (column == 6 || i == daysInMonth) {
                model.addRow(row.toArray());
                row = new Vector<>(Collections.nCopies(7, ""));
            }
        }
    }
}