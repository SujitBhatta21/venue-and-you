import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

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
     * Loads booked dates from a file.
     */
    private static void loadBookedDates() {
        File file = new File(BOOKINGS_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Object loadedObject = ois.readObject();
                if (loadedObject instanceof Map) {
                    monthlyBookings = (Map<String, Set<Integer>>) loadedObject;
                }
            } catch (IOException | ClassNotFoundException e) {
                monthlyBookings = new HashMap<>();
            }
        }
    }

    /**
     * Saves booked dates to a file.
     */
    private static void saveBookedDates() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BOOKINGS_FILE))) {
            oos.writeObject(monthlyBookings);
        } catch (IOException e) {
            System.out.println("Error saving bookings: " + e.getMessage());
        }
    }

    /**
     * Returns a JPanel containing the calendar UI.
     * This allows embedding in another JFrame instead of running a separate window.
     */
    public JPanel getCalendarPanel() {
        loadBookedDates();

        JPanel calendarPanel = new JPanel(new BorderLayout(10, 10));
        calendarPanel.setBackground(Color.decode("#CCD1D2"));
        calendarPanel.setBorder(BorderFactory.createTitledBorder("Calendar"));

        // Top panel for navigation
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.decode("#142524"));

        // Navigation buttons
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
                comp.setBorder(new EmptyBorder(5, 5, 5, 5));

                String value = (String) getValueAt(row, column);
                if (value != null && !value.isEmpty()) {
                    try {
                        int day = Integer.parseInt(value);
                        Set<Integer> bookedDates = getCurrentMonthBookedDates();
                        if (bookedDates.contains(day)) {
                            comp.setBackground(Color.decode("#30C142"));
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
                                JOptionPane.showMessageDialog(null, "Unbooked date: " + day);
                            } else {
                                bookedDates.add(day);
                                JOptionPane.showMessageDialog(null, "Booked date: " + day);
                            }
                            saveBookedDates();
                            table.repaint();
                        } catch (NumberFormatException ignored) {
                        }
                    }
                }
            }
        });

        table.setRowHeight(45);
        table.setGridColor(Color.decode("#848D94"));
        table.setShowGrid(true);
        table.getTableHeader().setBackground(Color.decode("#142524"));
        table.getTableHeader().setForeground(Color.white);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        // Fill the calendar with dates
        fillCalendar();

        // Add components
        calendarPanel.add(topPanel, BorderLayout.NORTH);
        calendarPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        return calendarPanel;
    }

    private static Set<Integer> getCurrentMonthBookedDates() {
        String key = currentYear + "-" + currentMonth;
        return monthlyBookings.getOrDefault(key, new HashSet<>());
    }

    private static void changeMonth(int delta) {
        Calendar calendar = new GregorianCalendar(currentYear, currentMonth, 1);
        calendar.add(Calendar.MONTH, delta);

        saveBookedDates();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);

        monthYearLabel.setText(MONTH_NAMES[currentMonth] + " " + currentYear);
        fillCalendar();
    }

    private static void fillCalendar() {
        model.setRowCount(0);
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
