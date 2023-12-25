import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class AdminGUI extends JFrame {
    private LoginSignupApp app;
    private JPanel mainPanel;
    private JList<String> userList;
    private JList<String> paymentHistoryList;
    private JList<String> returnedLaptopsList;
    private DefaultListModel returnedLaptopsListModel;
    private DefaultListModel userListModel;
    private Image backgroundImage;
    private Logic logic;
    private Menu menu;

    private JButton updateUserListButton;
    private JButton updateRentHistoryButton;
    private JButton updateReturnedLaptopsButton;

    public AdminGUI(LoginSignupApp app) {
        this.app = app;
        this.userListModel = new DefaultListModel<>(); // Initialize the DefaultListModel
        this.userList = new JList<>(userListModel); // Assign the DefaultListModel to the JList
        this.returnedLaptopsListModel = new DefaultListModel<>();
        this.returnedLaptopsList = new JList<>(returnedLaptopsListModel);
        this.logic = new Logic(menu);
        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        mainPanel.setPreferredSize(new Dimension(800, 600));

        List<String> users = readFromFile("users.txt");
        userList = new JList<>(users.toArray(new String[0]));

        List<String> payments = readFromFile("rentedLaptops.txt");
        paymentHistoryList = new JList<>(payments.toArray(new String[0]));

        List<String> returnedLaptops = readFromFile("returnedLaptops.txt");
        returnedLaptopsList = new JList<>(returnedLaptops.toArray(new String[0]));

        JScrollPane userScrollPane = new JScrollPane(userList);
        JScrollPane paymentHistoryScrollPane = new JScrollPane(paymentHistoryList);
        userScrollPane.setBounds(10, 45, 200, 500);
        paymentHistoryScrollPane.setBounds(230, 45, 200, 500);

        JScrollPane returnedLaptopsScrollPane = new JScrollPane(returnedLaptopsList);
        returnedLaptopsScrollPane.setBounds(450, 45, 200, 500);

        JLabel userListLabel = new JLabel("Users:");
        JLabel things = new JLabel("(Select a user to show details)");
        userListLabel.setBounds(10, 10, 100, 20);
        things.setBounds(10, 25, 200, 20);

        JLabel paymentHistoryListLabel = new JLabel("Rent History:");
        JLabel thingy = new JLabel("(Laptop ID:Student ID:Price)");
        paymentHistoryListLabel.setBounds(230, 10, 200, 20);
        thingy.setBounds(230, 25, 200, 20);

        JLabel returnedLaptopsListLabel = new JLabel("Returned Laptops:");
        JLabel thingz = new JLabel("(Laptop ID:Student ID)");
        returnedLaptopsListLabel.setBounds(450, 10, 200, 20);
        thingz.setBounds(450, 25, 200, 20);

        updateUserListButton = new JButton("Show User Details");
        updateUserListButton.setBounds(30, 550, 150, 30);
        updateUserListButton.addActionListener(e -> handleUpdateUserList());

        updateRentHistoryButton = new JButton("Update Rent History");
        updateRentHistoryButton.setBounds(250, 550, 150, 30);
        updateRentHistoryButton.addActionListener(e -> handleUpdateRentHistory());

        updateReturnedLaptopsButton = new JButton("Update Returned Laptops");
        updateReturnedLaptopsButton.setBounds(460, 550, 180, 30);
        updateReturnedLaptopsButton.addActionListener(e -> handleUpdateReturnedLaptops());

        Border border = BorderFactory.createLineBorder(Color.YELLOW, 3);

        mainPanel.setBorder(border);
        mainPanel.add(userScrollPane);
        mainPanel.add(paymentHistoryScrollPane);
        mainPanel.add(userListLabel);
        mainPanel.add(things);
        mainPanel.add(paymentHistoryListLabel);
        mainPanel.add(thingy);
        mainPanel.add(returnedLaptopsScrollPane);
        mainPanel.add(returnedLaptopsListLabel);
        mainPanel.add(thingz);
        mainPanel.add(updateUserListButton);
        mainPanel.add(updateRentHistoryButton);
        mainPanel.add(updateReturnedLaptopsButton);

        JButton menuButton = new JButton("Logout");
        menuButton.setBounds(675, 550, 100, 30);
        menuButton.addActionListener(e -> handleMenu()); // Call method for handling menu
        mainPanel.add(menuButton);

        JButton dataSummary = new JButton("Show Data");
        dataSummary.setBounds(670, 500, 110, 25);
        dataSummary.addActionListener(e -> handleDataSummary()); // Call method for handling data summary
        mainPanel.add(dataSummary);

        try {
            backgroundImage = ImageIO.read(new File("bg/Adminpic.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    // Read data from file
    private List<String> readFromFile(String fileName) {
        List<String> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    // usually handleLogout() but im too lazy to change it
    private void handleMenu() {
        app.logout();
    }

    // method for going to datasummary class
    private void handleDataSummary() {
        DataSummary dataSummary = new DataSummary();
        dataSummary.showDataSummary();
    }

    private void handleUpdateRentHistory() {
        int selectedIndex = paymentHistoryList.getSelectedIndex();
        if (selectedIndex != -1) {
            List<String> rentHistory = readFromFile("rentedLaptops.txt");
            String selectedRent = rentHistory.get(selectedIndex);
            List<String> rentDetails = Collections.singletonList(selectedRent);
            displayRentDetails(rentDetails);
        }
    }

    // Method to display rent details in a separate window
    private void displayRentDetails(List<String> rentDetails) {
        JFrame rentDetailsFrame = new JFrame("Rent Details");
        rentDetailsFrame.setSize(300, 250);

        JPanel rentDetailsPanel = new JPanel();
        rentDetailsPanel.setLayout(null);

        int labelX = 40;
        int yPosition = 40;
        int labelWidth = 100;
        int textFieldWidth = 150;
        int height = 20;
        int verticalGap = 30;

        Border border = BorderFactory.createLineBorder(Color.GREEN, 3);

        rentDetailsPanel.setBorder(border);

        for (String rent : rentDetails) {
            String[] parts = rent.split(",");
            if (parts.length >= 3) {
                JLabel idLabel = new JLabel("Laptop ID: ");
                JLabel nameLabel = new JLabel("Laptop Name: ");
                JLabel studentIdLabel = new JLabel("Student ID:");
                JLabel priceLabel = new JLabel("Price:");

                idLabel.setBounds(labelX, yPosition, labelWidth, height);
                nameLabel.setBounds(labelX, yPosition + verticalGap, labelWidth, height);
                studentIdLabel.setBounds(labelX, yPosition + (2 * verticalGap), labelWidth, height);
                priceLabel.setBounds(labelX, yPosition + (3 * verticalGap), labelWidth, height);

                JTextField idField = new JTextField(parts[0]);
                JTextField nameField = new JTextField(getLaptopNameByID(parts[0])); // Pass ID to fetch name
                JTextField studentIdField = new JTextField(parts[1]);
                JTextField priceField = new JTextField(parts[2]);

                idField.setBounds(labelX + labelWidth, yPosition, textFieldWidth, height);
                nameField.setBounds(labelX + labelWidth, yPosition + verticalGap, textFieldWidth, height);
                studentIdField.setBounds(labelX + labelWidth, yPosition + (2 * verticalGap), textFieldWidth, height);
                priceField.setBounds(labelX + labelWidth, yPosition + (3 * verticalGap), textFieldWidth, height);

                idField.setEditable(false);
                nameField.setEditable(false);
                studentIdField.setEditable(false);
                priceField.setEditable(false);

                rentDetailsPanel.add(idLabel);
                rentDetailsPanel.add(nameLabel);
                rentDetailsPanel.add(studentIdLabel);
                rentDetailsPanel.add(priceLabel);
                rentDetailsPanel.add(idField);
                rentDetailsPanel.add(nameField);
                rentDetailsPanel.add(studentIdField);
                rentDetailsPanel.add(priceField);

                yPosition += 4 * verticalGap; // Move to the next set of details
            } else {
                JLabel errorLabel = new JLabel("Invalid rent data format");
                errorLabel.setBounds(labelX, yPosition, labelWidth, height);
                rentDetailsPanel.add(errorLabel);

                yPosition += verticalGap; // Move to the next line
            }
        }

        rentDetailsFrame.add(rentDetailsPanel);
        rentDetailsFrame.setVisible(true);
        rentDetailsFrame.setResizable(false);
        rentDetailsFrame.setLocationRelativeTo(null);
    }

    private String getLaptopNameByID(String id) {
        Map<String, Laptop> availableLaptops = logic.initializeAvailableLaptops(); // Fetch available laptops
        if (availableLaptops.containsKey(id)) {
            return availableLaptops.get(id).getName();
        }
        return "Unknown";
    }

    private void handleUpdateReturnedLaptops() {
        int selectedIndex = returnedLaptopsList.getSelectedIndex();
        if (selectedIndex != -1) {
            List<String> returnedLaptops = readFromFile("returnedLaptops.txt");
            String selectedReturn = returnedLaptops.get(selectedIndex);
            List<String> returnDetails = Collections.singletonList(selectedReturn);
            displayReturnedLaptopDetails(returnDetails);
        }
    }

    // Method to display returned laptops details in a separate window
    private void displayReturnedLaptopDetails(List<String> returnDetails) {
        JFrame returnDetailsFrame = new JFrame("Returned Laptops Details");
        returnDetailsFrame.setSize(300, 250);

        JPanel returnDetailsPanel = new JPanel();
        returnDetailsPanel.setLayout(null);

        int labelX = 40;
        int yPosition = 40;
        int labelWidth = 100;
        int textFieldWidth = 150;
        int height = 20;
        int verticalGap = 30;

        Border border = BorderFactory.createLineBorder(Color.GREEN, 3);
        returnDetailsPanel.setBorder(border);

        for (String returnData : returnDetails) {
            String[] parts = returnData.split(",");
            if (parts.length >= 2) { // Assuming ID and Student ID are present
                String laptopID = parts[0];
                JLabel idLabel = new JLabel("Laptop ID: ");
                JLabel laptopNameLabel = new JLabel("Laptop Name: ");
                JLabel studentIdLabel = new JLabel("Student ID:");

                idLabel.setBounds(labelX, yPosition, labelWidth, height);
                laptopNameLabel.setBounds(labelX, yPosition + verticalGap, labelWidth, height);
                studentIdLabel.setBounds(labelX, yPosition + 2 * verticalGap, labelWidth, height);

                JTextField idField = new JTextField(laptopID);
                JTextField laptopNameField = new JTextField(getLaptopNameByID(laptopID)); // Fetch Name
                JTextField studentIdField = new JTextField(parts[1]);

                idField.setBounds(labelX + labelWidth, yPosition, textFieldWidth, height);
                laptopNameField.setBounds(labelX + labelWidth, yPosition + verticalGap, textFieldWidth, height);
                studentIdField.setBounds(labelX + labelWidth, yPosition + 2 * verticalGap, textFieldWidth, height);

                idField.setEditable(false);
                laptopNameField.setEditable(false);
                studentIdField.setEditable(false);

                returnDetailsPanel.add(idLabel);
                returnDetailsPanel.add(laptopNameLabel);
                returnDetailsPanel.add(studentIdLabel);
                returnDetailsPanel.add(idField);
                returnDetailsPanel.add(laptopNameField);
                returnDetailsPanel.add(studentIdField);

                yPosition += 3 * verticalGap; // Move to the next set of details
            } else {
                JLabel errorLabel = new JLabel("Invalid return data format");
                errorLabel.setBounds(labelX, yPosition, labelWidth, height);
                returnDetailsPanel.add(errorLabel);

                yPosition += verticalGap; // Move to the next line
            }
        }

        returnDetailsFrame.add(returnDetailsPanel);
        returnDetailsFrame.setVisible(true);
        returnDetailsFrame.setResizable(false);
        returnDetailsFrame.setLocationRelativeTo(null);
    }

    private void handleUpdateUserList() {
        int selectedIndex = userList.getSelectedIndex();
        if (selectedIndex != -1) {
            List<String> users = readFromFile("users.txt");
            String selectedUser = users.get(selectedIndex);
            List<String> userDetails = Collections.singletonList(selectedUser);
            displayUserDetails(userDetails);
        }
    }

    // method to display user details in a separate window
    private void displayUserDetails(List<String> userDetails) {
        JFrame userDetailsFrame = new JFrame("User Details");
        userDetailsFrame.setSize(300, 250);

        JPanel userDetailsPanel = new JPanel();
        userDetailsPanel.setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel studentIdLabel = new JLabel("Student ID:");
        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel lastNameLabel = new JLabel("Last Name:");

        int labelX = 40;
        int yPosition = 40;
        int labelWidth = 100;
        int textFieldWidth = 150;
        int height = 20;
        int verticalGap = 30;

        Border border = BorderFactory.createLineBorder(Color.GREEN, 3);

        usernameLabel.setBounds(labelX, yPosition, labelWidth, height);
        passwordLabel.setBounds(labelX, yPosition + verticalGap, labelWidth, height);
        studentIdLabel.setBounds(labelX, yPosition + 2 * verticalGap, labelWidth, height);
        firstNameLabel.setBounds(labelX, yPosition + 3 * verticalGap, labelWidth, height);
        lastNameLabel.setBounds(labelX, yPosition + 4 * verticalGap, labelWidth, height);

        userDetailsPanel.setBorder(border);
        userDetailsPanel.add(usernameLabel);
        userDetailsPanel.add(passwordLabel);
        userDetailsPanel.add(studentIdLabel);
        userDetailsPanel.add(firstNameLabel);
        userDetailsPanel.add(lastNameLabel);

        for (String user : userDetails) {
            String[] parts = user.split(" : ");
            if (parts.length >= 5) {
                JTextField textField = new JTextField(parts[0]);
                userDetailsPanel.add(textField);
                textField.setBounds(labelX + labelWidth, yPosition, textFieldWidth, height);
                textField.setEditable(false);

                for (int i = 1; i < parts.length; i++) {
                    if (i == 1) {
                        JLabel infoLabel = new JLabel("[Encrypted]");
                        userDetailsPanel.add(infoLabel);
                        infoLabel.setBounds(labelX + labelWidth, yPosition + (i * verticalGap), textFieldWidth, height);
                    } else {
                        JLabel infoLabel = new JLabel(parts[i]);
                        userDetailsPanel.add(infoLabel);
                        infoLabel.setBounds(labelX + labelWidth, yPosition + (i * verticalGap), textFieldWidth, height);
                    }
                }

                yPosition += verticalGap * (parts.length - 1); // Update y position for the next user details
            } else {
                // Handle incomplete or incorrect data for a user
                JLabel errorLabel = new JLabel("Invalid user data format");
                userDetailsPanel.add(errorLabel);
                errorLabel.setBounds(labelX, yPosition, labelWidth, height);
                yPosition += verticalGap;
            }
        }

        userDetailsFrame.add(userDetailsPanel);
        userDetailsFrame.setVisible(true);
        userDetailsFrame.setResizable(false);
        userDetailsFrame.setLocationRelativeTo(null);
    }

}
