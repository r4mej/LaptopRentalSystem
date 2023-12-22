import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
    private GUIComponents components;

    private JButton updateUserListButton;
    private JButton updateRentHistoryButton;
    private JButton updateReturnedLaptopsButton;

    public AdminGUI(LoginSignupApp app) {
        this.app = app;
        this.components = components;
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
        JLabel things = new JLabel("(Update to transform list)");
        userListLabel.setBounds(10, 10, 100, 20);
        things.setBounds(10,25,200,20);

        JLabel paymentHistoryListLabel = new JLabel("Rent History:");
        JLabel thingy = new JLabel("(Laptop ID:Student ID:Price)");
        paymentHistoryListLabel.setBounds(230, 10, 200, 20);
        thingy.setBounds(230,25, 200,20);

        JLabel returnedLaptopsListLabel = new JLabel("Returned Laptops:");
        JLabel thingz = new JLabel("(Laptop ID:Student ID)");
        returnedLaptopsListLabel.setBounds(450, 10, 200, 20);
        thingz.setBounds(450,25, 200,20);

        updateUserListButton = new JButton("Update User List");
        updateUserListButton.setBounds(30, 550, 150, 30);
        updateUserListButton.addActionListener(e -> updateUserListFromFile("users.txt"));

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
        menuButton.addActionListener(e -> handleMenu()); // Call method for handling menu actions
        mainPanel.add(menuButton);

        JButton dataSummary = new JButton("Show Data");
        dataSummary.setBounds(670, 500, 110, 25);
        dataSummary.addActionListener(e -> handleDataSummary()); // Call method for handling menu actions
        mainPanel.add(dataSummary);

        try {
            backgroundImage = ImageIO.read(new File("Adminpic.png"));
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

    private void handleMenu() {
        app.logout();
    }
    private void handleDataSummary() {
        DataSummary dataSummary = new DataSummary();
        dataSummary.showDataSummary();
    }
    

    public void updateUserList(HashMap<String, Student> users) {
        List<String> userList = convertUserDetailsToStringList(users);
        userListModel.clear();
        userListModel.addAll(userList);
    }
    private List<String> convertUserDetailsToStringList(HashMap<String, Student> users) {
        List<String> userList = new ArrayList<>();
        for (String username : users.keySet()) {
            Student student = users.get(username);
            String userDetails = "Username: " + student.getUsername() + "\n"
                    + "Password: " + "[Encrypted]" + "\n"
                    + "Student ID: " + student.getId() + "\n"
                    + "First Name: " + student.getFirstname() + "\n"
                    + "Last Name: " + student.getLastname() + "\n";
            userList.add(userDetails);
            userList.add("-------------------------"); // Separating users
        }
        return userList;
    }
    
    private void updateUserListFromFile(String fileName) {
        List<String> fileContents = readFromFile(fileName);
        List<String> transformedData = transformUsersFileContents(fileContents); // Use the method to transform data
        
        userList.setModel(new AbstractListModel<String>() {
            @Override
            public int getSize() {
                return transformedData.size();
            }
    
            @Override
            public String getElementAt(int index) {
                return transformedData.get(index);
            }
        });
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
                transformedData.add("Password: " + "[Encrypted]");
                transformedData.add("Student ID: " + parts[2]);
                transformedData.add("First Name: " + parts[3]);
                transformedData.add("Last Name: " + parts[4]);
                transformedData.add("-------------------------------"); // Separator between users
            } else {
                // Handle the case where data is incomplete or incorrect
            }
        }
        return transformedData;
    }
    
}
