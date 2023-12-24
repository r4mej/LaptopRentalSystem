import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class AdminHandling {
    private HashMap<String, Admin> adminMap; // Store admin credentials in a map

    public AdminHandling() {
        // Load admin credentials when the class is initialized
        this.adminMap = loadAdminsFromFile();
    }

    // Method to load admin credentials from file
    public HashMap<String, Admin> loadAdminsFromFile() {
        HashMap<String, Admin> admins = new HashMap<>();
        try {
            File adminFile = new File("admin.txt");
            if (adminFile.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(adminFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] adminDetails = line.split(":");
                    Admin admin = new Admin(adminDetails[0], adminDetails[1]);
                    admins.put(adminDetails[0], admin); // Username as key, password as value
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return admins;
    }

    // Method to save admin credentials
    public void saveAdmin(Admin admin) {
        try {
            File adminFile = new File("admin.txt");
            FileWriter writer = new FileWriter(adminFile, true); // Append mode
            writer.write(admin.getUsername() + ":" + admin.getPassword() + "\n");
            writer.close();

            adminMap.put(admin.getUsername(), admin); // Update adminMap
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveAdminCredentials(String username, String password) {
        // Perform the necessary operation to save admin credentials
        Admin admin = new Admin(username, password);
        saveAdmin(admin); // Utilize the existing saveAdmin method
    }
    public Admin retrieveAdminByUsername(String adminUsername){
        return adminMap.get(adminUsername);
    }
    public boolean isAdmin(String username){
        return adminMap.containsKey(username);
    }

}
