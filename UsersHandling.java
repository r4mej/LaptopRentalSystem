import java.io.*;
import java.util.HashMap;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UsersHandling {
    private HashMap<String, Student> users;

    public UsersHandling(HashMap<String, Student> users) {
        this.users = users;
    }

    public void loadUsersFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
            String line;
    
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" : ");
                if (parts.length >= 3) {
                    String username = parts[0];
                    String storedHashedPassword = parts[1]; // This is the hashed password from the file
                    String id = parts[2];
                    String firstName = (parts.length > 3) ? parts[3] : "default";
                    String lastName = (parts.length > 4) ? parts[4] : "default";
    
                    // No need to hash the loaded password again
                    Student student = new Student(username, storedHashedPassword, id, firstName, lastName);
                    users.put(username, student);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    

    public void saveUsersToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt"));
    
            for (String username : users.keySet()) {
                Student student = users.get(username);
                String line;
    
                // Get the stored hashed password directly
                String storedHashedPassword = student.getPassword();
    
                if (student.getLastname() != null && student.getFirstname() != null) {
                    line = username + " : " + storedHashedPassword + " : " + student.getId() + " : " + student.getFirstname()
                            + " : " + student.getLastname() + "\n";
                } else {
                    line = username + " : " + storedHashedPassword + " : " + student.getId() + "\n";
                }
                writer.write(line);
            }
    
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    

}
