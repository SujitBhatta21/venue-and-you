import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RoomBookingPanel extends JPanel {

    private final JComboBox<String> dateCombo;
    private final JComboBox<String> timeCombo;
    private final JTextArea resultsArea;
    private final Room selectedRoom;

    private static final String DB_URL = "jdbc:mysql://sst-stuproj.city.ac.uk:3306/in2033t21";
    private static final String DB_USER = "in2033t21_a";
    private static final String DB_PASSWORD = "lrLUWCLVzDQ";

    public RoomBookingPanel(String roomName) {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#CCD1D2"));
        selectedRoom = RoomRepository.getRoomByName(roomName);

        // --- Filter Section ---
        JPanel filterPanel = new JPanel(new FlowLayout());
        filterPanel.setBackground(Color.decode("#CCD1D2"));

        JLabel roomLabel = new JLabel("Room: " + roomName);
        JLabel rateLabel = new JLabel("Rates: " + selectedRoom.getRateSummary());

        dateCombo = new JComboBox<>(generateNext7Days());
        timeCombo = new JComboBox<>(generateTimeSlots());

        JButton filterBtn = new JButton("Apply Filter");
        filterBtn.setBackground(Color.decode("#30C142"));
        filterBtn.setForeground(Color.WHITE);
        filterBtn.addActionListener(e -> loadBookingData());

        filterPanel.add(roomLabel);
        filterPanel.add(rateLabel);
        filterPanel.add(new JLabel("Date:"));
        filterPanel.add(dateCombo);
        filterPanel.add(new JLabel("Time:"));
        filterPanel.add(timeCombo);
        filterPanel.add(filterBtn);

        add(filterPanel, BorderLayout.NORTH);

        // --- Results Section ---
        resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        add(scrollPane, BorderLayout.CENTER);

        loadBookingData(); // Load initially
    }

    private String[] generateNext7Days() {
        String[] days = new String[7];
        LocalDate now = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            days[i] = now.plusDays(i).toString();
        }
        return days;
    }

    private String[] generateTimeSlots() {
        String[] times = new String[24 * 12]; // 5 min intervals
        int index = 0;
        for (int hour = 0; hour < 24; hour++) {
            for (int min = 0; min < 60; min += 5) {
                times[index++] = String.format("%02d:%02d", hour, min);
            }
        }
        return times;
    }

    private void loadBookingData() {
        String selectedDate = (String) dateCombo.getSelectedItem();
        String selectedTime = (String) timeCombo.getSelectedItem();

        resultsArea.setText("Searching bookings for: " + selectedRoom.name + " on " + selectedDate + " at "
                + selectedTime + "\n\n");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM room_bookings WHERE room_name = ? AND booking_date = ? AND booking_time = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, selectedRoom.name);
            stmt.setString(2, selectedDate);
            stmt.setString(3, selectedTime);

            ResultSet rs = stmt.executeQuery();
            boolean hasResults = false;

            while (rs.next()) {
                hasResults = true;
                String client = rs.getString("client_name");
                String purpose = rs.getString("purpose");
                resultsArea.append("Client: " + client + "\nPurpose: " + purpose + "\n------------------------\n");
            }

            if (!hasResults) {
                resultsArea.append("No bookings found for this slot.");
            }

        } catch (SQLException ex) {
            resultsArea.setText("Error loading booking data:\n" + ex.getMessage());
        }
    }
}
