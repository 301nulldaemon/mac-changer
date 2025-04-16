package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class gui {
    public static void main(String[] args) {

        // === Customization for colors ===
        Color bgColor = new Color(30, 30, 30);              // Dark background
        Color textColor = new Color(0, 0, 0);               // Light text
        Color inputBgColor = new Color(255, 255, 255);      // Input box background
        Color buttonColor = Color.BLACK;
        Color buttonTextColor = Color.WHITE;
        Color outputBgColor = new Color(20, 20, 20);        // Output area background
        Color outputTextColor = new Color(0, 255, 0);

        // === Frame setup ===
        JFrame frame = new JFrame("MAC Address Changer");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(bgColor);

        // === Top input panel ===
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(bgColor);

        JTextField interfaceField = new JTextField(10);
        JTextField macField = new JTextField(15);
        JButton changeButton = new JButton("Change MAC");

        // Apply colors
        changeButton.setBackground(buttonColor);
        changeButton.setForeground(buttonTextColor);
        interfaceField.setFont(new Font("Arial", Font.BOLD, 12));
        interfaceField.setBackground(inputBgColor);
        interfaceField.setForeground(textColor);
        interfaceField.setCaretColor(textColor);
        macField.setFont(new Font("Arial", Font.BOLD, 12));
        macField.setBackground(inputBgColor);
        macField.setForeground(textColor);
        macField.setCaretColor(textColor);

        // Tooltips
        interfaceField.setToolTipText("Enter your network interface, like eth0 or wlan0");
        macField.setToolTipText("Enter new MAC address, e.g., 00:11:22:33:44:55");

        // Assemble top input panel
        JLabel interfaceLabel = new JLabel("Interface:");
        interfaceLabel.setForeground(buttonTextColor); // Use your custom light color
        inputPanel.add(interfaceLabel);
        inputPanel.add(interfaceField);

        
        JLabel macLabel = new JLabel("New MAC:");
        macLabel.setForeground(buttonTextColor); // Use your custom light color
        inputPanel.add(macLabel);
        inputPanel.add(macField);
        
        frame.add(inputPanel, BorderLayout.NORTH);
        inputPanel.add(changeButton);


        // === Center output panel ===
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBackground(outputBgColor);
        outputArea.setForeground(outputTextColor);
        outputArea.setCaretColor(outputTextColor);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // === Bottom gif + quote panel ===
        JPanel gifPanel = new JPanel(new BorderLayout());
        gifPanel.setBackground(bgColor);

        File currentDir = new File(System.getProperty("user.dir"));
        File gifFile = new File(currentDir, "/gifs/wiggle.gif");
        ImageIcon gif = new ImageIcon(gifFile.getAbsolutePath());

        JLabel gifLabel = new JLabel(gif);
        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel quoteLabel = new JLabel("Make sure the MAC starts with 00. For example: 00:11:22:33:44:55");
        quoteLabel.setFont(new Font("Courier New", Font.PLAIN, 14));
        quoteLabel.setForeground(Color.GREEN);
        quoteLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel secondLabel = new JLabel("(BTW these texts can be edited!)");
        secondLabel.setFont(new Font("Courier New", Font.PLAIN, 14));
        secondLabel.setForeground(Color.GREEN);
        secondLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gifPanel.add(gifLabel, BorderLayout.CENTER);
        gifPanel.add(quoteLabel, BorderLayout.SOUTH);
        gifPanel.add(secondLabel, BorderLayout.NORTH);


        frame.add(gifPanel, BorderLayout.SOUTH);

        // === Action Listener for button ===
        changeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String iface = interfaceField.getText();
                String mac = macField.getText();

                try {
                    File currentDir = new File(System.getProperty("user.dir"));
                    File pythonScript = new File(currentDir, "mac.py");

                    System.out.println("Looking for mac.py in: " + pythonScript.getAbsolutePath());

                    if (!pythonScript.exists()) {
                        outputArea.append("Error: mac.py not found in the current directory.\n");
                        return;
                    }

                    ProcessBuilder builder = new ProcessBuilder("python", pythonScript.getAbsolutePath(), "-i", iface, "-m", mac);
                    builder.directory(currentDir);
                    Process process = builder.start();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        outputArea.append(line + "\n");
                    }

                    int exitCode = process.waitFor();
                    outputArea.append("Exited with code: " + exitCode + "\n");

                } catch (IOException | InterruptedException ex) {
                    outputArea.append("Error: " + ex.getMessage() + "\n");
                }
            }
        });
        // makes the window open in the middle of the screen
        frame.setLocationRelativeTo(null);

        // makes the application visible
        frame.setVisible(true);
    }
}
