import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataSummary {
    private JFrame frame;
    private JPanel panel;
    private JLabel usersLabel;
    private JLabel rentedLabel;
    private JLabel returnedLabel;
    private JLabel availableLaptops;

    public DataSummary() {
        frame = new JFrame("Data Summary");
        frame.setResizable(false);
        panel = new JPanel();
        panel.setLayout(null); // Use null layout for manual positioning

        // Read values from text files
        int numberOfUsers = readIntegerFromFile("users.txt");
        int numberOfRentedLaptops = readIntegerFromFile("rentedLaptops.txt");
        int numberOfReturnedLaptops = readIntegerFromFile("returnedLaptops.txt");
        int totalNumberOfLaptops = 6;
        int numberOfAvailableLaptops = totalNumberOfLaptops - numberOfRentedLaptops;

        usersLabel = new JLabel("Number of Users: " + numberOfUsers);
        rentedLabel = new JLabel("Currently Rented Laptops: " + numberOfRentedLaptops);
        returnedLabel = new JLabel("All of Returned Laptops: " + numberOfReturnedLaptops);
        availableLaptops = new JLabel("Currently Available laptops: " + numberOfAvailableLaptops);


        JButton updateButton = new JButton("Update Data");
        updateButton.addActionListener(e -> updateFiles());

        // Set bounds for each component
        usersLabel.setBounds(10, 10, 200, 20);
        rentedLabel.setBounds(10, 40, 250, 20);
        returnedLabel.setBounds(10, 70, 250, 20);
        availableLaptops.setBounds(10, 100, 250, 20);
        updateButton.setBounds(70, 130, 150, 30);

        // Add components to the panel
        panel.add(usersLabel);
        panel.add(rentedLabel);
        panel.add(returnedLabel);
        panel.add(availableLaptops);
        panel.add(updateButton);

        frame.add(panel);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        updateDisplayedCounts();
    }

    // Method to read an integer from a file
    private int readIntegerFromFile(String fileName) {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    // Method to update files with the latest data
    // Method to update files with the latest data
private void updateFiles() {
    // Perform logic to update the files with the latest data
    try {
        // Read the existing data from the files
        List<String> usersData = readDataFromFile("users.txt");
        List<String> rentedLaptopsData = readDataFromFile("rentedLaptops.txt");
        List<String> returnedLaptopsData = readDataFromFile("returnedLaptops.txt");

        // Perform modifications to the data (for example, adding or modifying entries)

        // Write back the updated data to the files
        writeDataToFile("users.txt", usersData);
        writeDataToFile("rentedLaptops.txt", rentedLaptopsData);
        writeDataToFile("returnedLaptops.txt", returnedLaptopsData);

        // Update the displayed counts after updating the files
        updateDisplayedCounts();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

// Method to read data from a file and return it as a list of strings
private List<String> readDataFromFile(String fileName) throws IOException {
    List<String> data = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
        String line;
        while ((line = br.readLine()) != null) {
            data.add(line);
        }
    }
    return data;
}

// Method to write data to a file
private void writeDataToFile(String fileName, List<String> data) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
        for (String line : data) {
            writer.write(line);
            writer.newLine();
        }
    }
}

// Method to update displayed counts after files are updated
private void updateDisplayedCounts() {
    if (usersLabel != null && rentedLabel != null && returnedLabel != null && availableLaptops != null) {
        // Update labels
        int numberOfUsers = readIntegerFromFile("users.txt");
        int numberOfRentedLaptops = readIntegerFromFile("rentedLaptops.txt");
        int numberOfReturnedLaptops = readIntegerFromFile("returnedLaptops.txt");
        int numberOfAvailableLaptops = 6 - numberOfRentedLaptops;

        usersLabel.setText("Number of Users: " + numberOfUsers);
        rentedLabel.setText("Currently Rented Laptops: " + numberOfRentedLaptops);
        returnedLabel.setText("All of Returned Laptops: " + numberOfReturnedLaptops);
        availableLaptops.setText("Currently Available laptops: " + numberOfAvailableLaptops);
    } else {
        System.out.println("Labels are not initialized properly");
    }
}



    // Method to display the data summary window
    public void showDataSummary() {
        //new DataSummary();
    }
}
