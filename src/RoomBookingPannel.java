public class RoomBookingPannel {
    
}

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class RoomBookingPanel extends JPanel {

    private JComboBox<String> dateComboBox;
    private JSpinner timeSpinner;
    private JTextArea resultArea;
    private String roomName;

    public RoomBookingPanel(String roomName) {
        this.roomName = roomName;
        setLayout(new BorderLayout());
        setBackground(Color.decode("#CCD1D2")); // Iron background

        // FILTER PANEL
        JPanel filterPanel = new JPanel(new FlowLayout());
        filterPanel.setBackground(Color.decode("#CCD1D2"));
        filterPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Date ComboBox
        JLabel dateLabel = new JLabel("Select Date:");
        dateComboBox = new JComboBox<>(generateNext7Days());
        filterPanel.add(dateLabel);
        filterPanel.add(dateComboBox);

        // Time Spinner (intervals of 5 mins)
        JLabel timeLabel = new JLabel("Select Time:");
        SpinnerDateModel model = new SpinnerDateModel();
        timeSpinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(editor);
        roundTimeSpinnerTo5();

        filterPanel.add(timeLabel);
        filterPanel.add(timeSpinner);

        // Search Button
        JButton searchBtn = new JButton("Apply Filter");
        searchBtn.setBackground(Color.decode("#30C142")); // Apple
        searchBtn.setForeground(Color.WHITE);
        searchBtn.addActionListener(e -> updateResults());

        filterPanel.add(searchBtn);
        add(filterPanel, BorderLayout.NORTH);

        // RESULT AREA
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createLineBorder(Color.decode("#848D94"))); // Oslo Gray
        resultArea.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(resultArea);
        add(scroll, BorderLayout.CENTER);

        // Initial load
        updateResults();
    }

    private void roundTimeSpinnerTo5() {
        timeSpinner.addChangeListener((ChangeEvent e) -> {
            SpinnerDateModel model = (SpinnerDateModel) timeSpinner.getModel();
            LocalTime time = LocalTime.ofInstant(model.getDate().toInstant(), java.time.ZoneId.systemDefault());
            int minute = time.getMinute();
            int roundedMin = (minute / 5) * 5;
            model.setValue(java.util.Date.from(
                    time.withMinute(roundedMin).withSecond(0).atZone(java.time.ZoneId.systemDefault()).toInstant()));
        });
    }

    private String[] generateNext7Days() {
        String[] days = new String[7];
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            days[i] = today.plusDays(i).toString();
        }
        return days;
    }

    private void updateResults() {
        String date = (String) dateComboBox.getSelectedItem();
        String time = new java.text.SimpleDateFormat("HH:mm").format(timeSpinner.getValue());

        // Dummy results - replace with real DB results
        ArrayList<String> results = new ArrayList<>();
        results.add("Room: " + roomName);
        results.add("Filtered for: " + date + " at " + time);
        results.add("================================");
        results.add("Booking: 10:00 - 12:00 | John Smith | Meeting");
        results.add("Booking: 14:00 - 15:00 | Drama Club | Rehearsal");

        // Output results
        resultArea.setText(String.join("\n", results));
    }
}
