import javax.swing.*;
import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Logic {
    private Map<String, String> rentedLaptops;
    private Map<String, Laptop> availableLaptops;
    private FileHandler fileHandler;
    private Menu guiComponents;
    private boolean isLoggedIn;

    // Constructor to initialize GUIComponents, rentedLaptops, and fileHandler

    public Logic(Menu guiComponents) {
        this.guiComponents = guiComponents;
        availableLaptops = initializeAvailableLaptops();
        rentedLaptops = new HashMap<>();
        fileHandler = new FileHandler("rentedLaptops.txt");
        rentedLaptops = fileHandler.readFromFile(); // Read data from file when starting the application
    }

    public void createTextFieldWindow(String title, String label1, String label2, String label3) {
        JFrame textFieldFrame = new JFrame(title);
        textFieldFrame.setSize(300, 200);
        textFieldFrame.setLayout(null);
        textFieldFrame.setResizable(false);

        JLabel labelOne = new JLabel(label1);
        labelOne.setBounds(10, 10, 100, 25);
        textFieldFrame.add(labelOne);

        JTextField textFieldOne = new JTextField();
        textFieldOne.setBounds(120, 10, 150, 25);
        textFieldFrame.add(textFieldOne);

        if (label2 != null) {
            JLabel labelTwo = new JLabel(label2);
            labelTwo.setBounds(10, 40, 100, 25);
            textFieldFrame.add(labelTwo);

            JTextField textFieldTwo = new JTextField();
            textFieldTwo.setBounds(120, 40, 150, 25);
            textFieldFrame.add(textFieldTwo);

            JLabel labelThree = new JLabel(label3);
            labelThree.setBounds(10, 70, 100, 25);
            textFieldFrame.add(labelThree);

            JTextField textFieldThree = new JTextField();
            textFieldThree.setBounds(120, 70, 150, 25);
            textFieldFrame.add(textFieldThree);

            JButton confirmButton = new JButton("Confirm");
        confirmButton.setBounds(100, 120, 100, 25);
        textFieldFrame.add(confirmButton);
        confirmButton.addActionListener(e -> {
            String studentName = textFieldTwo.getText();
            String studentId = textFieldOne.getText();
            String laptopId = textFieldThree.getText();

            if (!studentName.isEmpty() && !studentId.isEmpty() && !laptopId.isEmpty()) {
                boolean isValidStudent = validateStudent(studentName, studentId);
        
                if (!isValidStudent) {
                    JOptionPane.showMessageDialog(null, "Invalid Student Name or ID.");
                } else if (hasAlreadyRented(studentId)) {
                    JOptionPane.showMessageDialog(null, "You have already rented a laptop.");
                } else if (availableLaptops.containsKey(laptopId)) {
                    if (rentedLaptops.containsKey(laptopId)) {
                        JOptionPane.showMessageDialog(null, "Laptop already rented.");
                    } else {
                        rentedLaptops.put(laptopId, studentId);
                        fileHandler.writeToFile(rentedLaptops, availableLaptops);
                        JOptionPane.showMessageDialog(null, "Laptop rented successfully.\n Bring your Student ID to the receptionist for pickup.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Laptop ID.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            }
            textFieldFrame.dispose();
        });
        }

        textFieldFrame.setVisible(true);
        textFieldFrame.setLocationRelativeTo(null); // Center the new window
    }

    public boolean validateStudent(String studentName, String studentId) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" : ");
                if (parts.length > 2 && parts[0].equals(studentName) && parts[2].equals(studentId)) {
                    reader.close();
                    return true; // Student name and ID found in users.txt
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Student name and ID not found in users.txt
    }

    public void returnLaptop() {
    JFrame returnFrame = new JFrame("Return Laptop");
    returnFrame.setSize(300, 150);
    returnFrame.setLayout(null);
    returnFrame.setResizable(false);
    String laptopId = JOptionPane.showInputDialog("Enter the laptop ID to return:");
    if (rentedLaptops.containsKey(laptopId)) {
        String studentId = JOptionPane.showInputDialog("Enter your Student ID:");
        String rentedStudentId = rentedLaptops.get(laptopId);

        if (studentId.equals(rentedStudentId)) {
            rentedLaptops.remove(laptopId);
            fileHandler.writeToFile(rentedLaptops, availableLaptops); // Update rentedLaptops.txt

            // Write returned laptop to "returnedLaptops.txt"
            try (BufferedWriter returnedWriter = new BufferedWriter(new FileWriter("returnedLaptops.txt", true))) {
                returnedWriter.write(laptopId + "," + studentId);
                returnedWriter.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            JOptionPane.showMessageDialog(null, "Laptop returned successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Student ID.");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Laptop not found or already returned.");
    }
}


    public Map<String, Laptop> initializeAvailableLaptops() {
        Map<String, Laptop> laptops = new HashMap<>(); // Initialize laptops as a new HashMap

        laptops.put("ID001", new Laptop("ID001", "Lenovo L13", "laptops/lenovo.png", 200.0,"10th Gen Intel Core i7, 16GB RAM ,1TB SSD"));
        laptops.put("ID002", new Laptop("ID002", "Dell Latitude", "laptops/dell.png", 400.0,"13th Gen Intel Core 15, 8GB RAM, 256GB SSD"));
        laptops.put("ID003", new Laptop("ID003", "Acer Aspire", "laptops/acer.png", 300.0, "Intel UHD Graphics, 8GB RAM, 256GB SSD"));
        laptops.put("ID004", new Laptop("ID004", "MSI GF63", "laptops/msi.png", 600.0, "9th Gen Intel Core i7, 64GB RAM, 1TB SSD"));
        laptops.put("ID005", new Laptop("ID005", "HP Envy", "laptops/hp.png", 300.0, "5th Gen Intel Core i7, 12GB RAM, 1TB HDD"));
        laptops.put("ID006", new Laptop("ID006", "Lenovo Thinkpad", "laptops/LenovoT.png", 200.0, "3rd Gen Intel Core i7, 4GB RAM, 128GB SSD"));
        // Add more laptops here...

        return laptops; 
    }

    public void displayAvailableLaptops() {
        JFrame availableLaptopsFrame = new JFrame("Available Laptops");
        availableLaptopsFrame.setSize(900, 600);
        availableLaptopsFrame.setResizable(false);
    
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 2));
    
        int imageSize = 200;
        int panelWidth = 400;
    
        for (Laptop laptop : availableLaptops.values()) {
            JPanel laptopPanel = new JPanel();
            laptopPanel.setLayout(null);
            laptopPanel.setPreferredSize(new Dimension(panelWidth, imageSize + 100));
    
            JLabel nameLabel = new JLabel("Name: " + laptop.getName());
            JLabel specsLabel = new JLabel("Specifications: "+ laptop.getSpecs());
            ImageIcon icon = new ImageIcon(new ImageIcon(laptop.getImagePath()).getImage().getScaledInstance(imageSize,
                    imageSize, Image.SCALE_SMOOTH));
            JLabel imageLabel = new JLabel(icon);
            JLabel idLabel = new JLabel("ID: " + laptop.getId());
            JLabel priceLabel = new JLabel("Price: " + laptop.getPrice());
            JLabel dayLabel = new JLabel("(Per day)");
    
            nameLabel.setBounds(5, 5, 150, 25);
            imageLabel.setBounds((400 - imageSize) / 2, 40, imageSize, imageSize);
            specsLabel.setBounds(5,20,400,25);
            idLabel.setBounds(5, 250, 100, 25);
            priceLabel.setBounds(350, 249, 100, 25);
            dayLabel.setBounds(360, 259, 100, 25);
    
            if (rentedLaptops.containsKey(laptop.getId())) {
                if (rentedLaptops.containsKey(laptop.getId())) {
                    JLabel rentedLabel = new JLabel("Rented");
                    rentedLabel.setForeground(Color.RED);
                    rentedLabel.setBounds(5, 270, 100, 25);
                    laptopPanel.add(rentedLabel);
                }
                laptopPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
            } else {
                JLabel available = new JLabel("Available");
                available.setForeground(Color.GREEN);
                available.setBounds(5, 270, 100, 25);
                laptopPanel.add(available);
                laptopPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
            }
    
            laptopPanel.add(nameLabel);
            laptopPanel.add(imageLabel);
            laptopPanel.add(specsLabel);
            laptopPanel.add(idLabel);
            laptopPanel.add(priceLabel);
            laptopPanel.add(dayLabel);
            mainPanel.add(laptopPanel);
        }
    
        JScrollPane scrollPane = new JScrollPane(mainPanel);
    
        availableLaptopsFrame.add(scrollPane);
        availableLaptopsFrame.setVisible(true);
        availableLaptopsFrame.setLocationRelativeTo(null);
    }
    
    public List<String> loadUserDetails() {
        // Load and return the updated user details from "users.txt" file
        // Process the file and return the user details as a List<String>
        List<String> userDetails = new ArrayList<>();
        // Read "users.txt" and populate userDetails
        // Example:
        // userDetails = readFromFile("users.txt");
        return userDetails;
    }
    public boolean hasAlreadyRented(String studentId) {
        for (String rentedStudentId : rentedLaptops.values()) {
            if (rentedStudentId.equals(studentId)) {
                return true; // User has already rented a laptop
            }
        }
        return false; // User has not rented any laptop
    }
    

    public void handleAction(ActionEvent e) {
        if (e.getSource() == guiComponents.getRentButton()) {
            createTextFieldWindow("Rent a Laptop", "Student ID", "Username", "Laptop ID");
        } else if (e.getSource() == guiComponents.getReturnButton()) {
            returnLaptop();
        } else if (e.getSource() == guiComponents.getListButton()) {
            displayAvailableLaptops();
        }
    }
}
