
package gui;
import javax.swing.*;
import java.awt.*;
// import java.awt.event.*;

public class gui {
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("MAC Address Changer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(4, 1, 10, 10));

        // Interface input
        JPanel interfacePanel = new JPanel();
        interfacePanel.add(new JLabel("Interface:"));
        JTextField interfaceField = new JTextField(20);
        interfacePanel.add(interfaceField);
        frame.add(interfacePanel);

        // MAC address input
        JPanel macPanel = new JPanel();
        macPanel.add(new JLabel("MAC Address:"));
        JTextField macField = new JTextField(20);
        macPanel.add(macField);
        frame.add(macPanel);

        // Submit button
        JButton submitButton = new JButton("Change MAC");
        frame.add(submitButton);

        // Result label
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);
        frame.add(resultLabel);

        // Button logic
        // submitButton.addActionListener(e -> {
            String iface = interfaceField.getText();
            String mac = macField.getText();

            // Simple validation
            if (iface.isEmpty() || mac.isEmpty()) {
                resultLabel.setText("❗ Please fill in both fields.");
            } else {
                // Replace this line with actual logic to change MAC
                resultLabel.setText("✅ Interface: " + iface + " | New MAC: " + mac);
                //to do: Add logic to run your backend code here
            }
        //});

        // Show the frame
        frame.setVisible(true);
    }
}
