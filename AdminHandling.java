import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class AdminHandling {
    private HashMap<String, String> adminCredentials; // Store admin credentials in a map

    public AdminHandling() {
        // Load admin credentials when the class is initialized
        this.adminCredentials = loadAdminCredentialsFromFile();
    }

    // Method to load admin credentials from file
    public HashMap<String, String> loadAdminCredentialsFromFile() {
        HashMap<String, String> admins = new HashMap<>();
        try {
            File adminFile = new File("admin.txt");
            if (adminFile.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(adminFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] adminDetails = line.split(":");
                    admins.put(adminDetails[0], adminDetails[1]); // Username as key, password as value
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return admins;
    }

    // Method to retrieve admin credentials based on username


    // Method to save admin credentials
    public void saveAdminCredentials(String username, String password) {
        try {
            File adminFile = new File("admin.txt");
            FileWriter writer = new FileWriter(adminFile, true); // Append mode
            writer.write(username + ":" + password + "\n");
            writer.close();

            // Update the admin credentials in memory after saving
            adminCredentials.put(username, password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveAdminCredentialsWithHashedPassword(String username, String password) {
        String hashedPassword = PasswordUtils.hashPassword(password); // Hash the password
        saveAdminCredentials(username, hashedPassword); // Save admin credentials with hashed password
    }
    
    public String retrieveAdminCredentials(String adminUsername) {
        return adminCredentials.get(adminUsername); // Retrieve password based on username
    }
    
    public String retrieveHashedAdminPassword(String adminUsername) {
        String storedPassword = retrieveAdminCredentials(adminUsername);
        if (storedPassword != null) {
            return PasswordUtils.hashPassword(storedPassword); // Hash the retrieved password
        }
        return null;
    }
    public void updateUserDetails(HashMap<String, Student> users) {
        // Update the admin panel's displayed user details based on the received data
        // This could involve updating a JTable or another component in the panel
        // Use the passed user details HashMap to update the displayed information
    }
    // Check if a user is an admin
    public boolean isAdmin(String username) {
        String storedPassword = retrieveAdminCredentials(username);
        return storedPassword != null; // Return true if the password exists (admin)
    }

    // Method to get all admin usernames
    public HashMap<String, String> getAllAdminUsernames() {
        return adminCredentials;
    }
    public void addAdmin(String username, String password) {
        try {
            File adminFile = new File("admin.txt");
            FileWriter writer = new FileWriter(adminFile, true); // Append mode
            writer.write(username + ":" + password + "\n");
            writer.close();

            // Update the admin credentials in memory after saving
            adminCredentials.put(username, password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
