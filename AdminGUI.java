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

    private JButton updateUserListButton;
    private JButton updateRentHistoryButton;
    private JButton updateReturnedLaptopsButton;

    public AdminGUI(LoginSignupApp app) {
        this.app = app;
        this.userListModel = new DefaultListModel<>(); // Initialize the DefaultListModel
        this.userList = new JList<>(userListModel); // Assign the DefaultListModel to the JList
        this.returnedLaptopsListModel = new DefaultListModel<>();
        this.returnedLaptopsList = new JList<>(returnedLaptopsListModel);
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
        updateRentHistoryButton.addActionListener(e -> updateRentHistoryFromFile("rentedLaptops.txt"));

        updateReturnedLaptopsButton = new JButton("Update Returned Laptops");
        updateReturnedLaptopsButton.setBounds(460, 550, 180, 30);
        updateReturnedLaptopsButton.addActionListener(e -> updateReturnedLaptopsFromFile("returnedLaptops.txt"));

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

    private void updateRentHistoryFromFile(String fileName) {
        List<String> rentHistory = readFromFile(fileName);
        paymentHistoryList.setModel(new AbstractListModel<String>() {
            @Override
            public int getSize() {
                return rentHistory.size();
            }

            @Override
            public String getElementAt(int index) {
                return rentHistory.get(index);
            }
        });
    }

    private void updateReturnedLaptopsFromFile(String fileName) {
        List<String> returnedLaptops = readFromFile(fileName);
        returnedLaptopsList.setModel(new AbstractListModel<String>() {
            @Override
            public int getSize() {
                return returnedLaptops.size();
            }

            @Override
            public String getElementAt(int index) {
                return returnedLaptops.get(index);
            }
        });
    }

    private List<String> transformUsersFileContents(List<String> fileContents) {
        List<String> transformedData = new ArrayList<>();
        for (String line : fileContents) {
            String[] parts = line.split(" : ");
            if (parts.length >= 5) { // Assuming each user entry has at least 5 parts
                transformedData.add("Username: " + parts[0]);
                transformedData.add("-------------------------------"); // Separator between users
            } else {
                // Handle the case where data is incomplete or incorrect
            }
        }
        return transformedData;
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

    //method to display user details in a separate window
    private void displayUserDetails(List<String> userDetails) {
        JFrame userDetailsFrame = new JFrame("User Details");
        userDetailsFrame.setSize(300, 300);
    
        JPanel userDetailsPanel = new JPanel();
        userDetailsPanel.setLayout(null);
    
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel studentIdLabel = new JLabel("Student ID:");
        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel lastNameLabel = new JLabel("Last Name:");
    
        int labelX = 40; // X position for labels
        int yPosition = 40; // Initial y position
        int labelWidth = 100;
        int textFieldWidth = 150;
        int height = 20;
        int verticalGap = 30;
    
        usernameLabel.setBounds(labelX, yPosition, labelWidth, height);
        passwordLabel.setBounds(labelX, yPosition + verticalGap, labelWidth, height);
        studentIdLabel.setBounds(labelX, yPosition + 2 * verticalGap, labelWidth, height);
        firstNameLabel.setBounds(labelX, yPosition + 3 * verticalGap, labelWidth, height);
        lastNameLabel.setBounds(labelX, yPosition + 4 * verticalGap, labelWidth, height);
    
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
                textField.setBounds(labelX + labelWidth , yPosition, textFieldWidth, height);
                textField.setEditable(false);
    
                for (int i = 1; i < parts.length; i++) {
                    if (i == 1) {
                        JLabel infoLabel = new JLabel("[Encrypted]");
                        userDetailsPanel.add(infoLabel);
                        infoLabel.setBounds(labelX + labelWidth , yPosition + (i * verticalGap), textFieldWidth, height);
                    } else {
                        JLabel infoLabel = new JLabel(parts[i]);
                        userDetailsPanel.add(infoLabel);
                        infoLabel.setBounds(labelX + labelWidth , yPosition + (i * verticalGap), textFieldWidth, height);
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
