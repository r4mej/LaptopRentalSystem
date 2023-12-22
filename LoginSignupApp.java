import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class LoginSignupApp extends JFrame {

    private JPanel cardPanel;
    private CardLayout cardLayout;

    private LoginPanel loginPanel;
    private SignupPanel signupPanel;
    private AdminPanel adminPanel;
    private JFrame adminFrame;

    private HashMap<String, Student> users;
    private GUI gui;
    private UsersHandling usersHandler;
    private AdminHandling adminHandler;

    public LoginSignupApp() {
        super("DORSU Authentication");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 420);
        setLocationRelativeTo(null);
        setResizable(false);

        users = new HashMap<>();
        usersHandler = new UsersHandling(users);
        adminHandler = new AdminHandling();

        adminHandler.loadAdminCredentialsFromFile();
        usersHandler.loadUsersFromFile();

        hashLoadedUserPasswords();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        loginPanel = new LoginPanel(this, users);
        signupPanel = new SignupPanel(this, users);
        adminPanel = new AdminPanel(this); // Instantiate the AdminPanel


        cardPanel.add(adminPanel, "admin");
        cardPanel.add(adminPanel, "admin");
        cardPanel.add(loginPanel, "login");
        cardPanel.add(signupPanel, "signup");

        cardLayout.show(cardPanel, "login");

        add(cardPanel);
        setVisible(true);
        adminPanel = new AdminPanel(this);
        adminFrame = new JFrame("Admin Panel");
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.add(adminPanel);
        adminFrame.pack();
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setResizable(false);
    
    }

    public void showSignupForm() {
        cardLayout.show(cardPanel, "signup");
    }

    public void showLoginForm() {
        cardLayout.show(cardPanel, "login");
        
    }

    public void saveUsersToFile() {
        usersHandler.saveUsersToFile(); 
    }
    public void saveAdminCredentials(String username, String password) {
        adminHandler.saveAdminCredentials(username, password); 
    }

    public void showGUI() {
        gui = new GUI(this);
        gui.setVisible(true);
    }
    public void logout(){
        showAdminPanel(false);
        setVisible(false); // Hide the GUI
        updateUserDetails();
        LoginSignupApp newAppInstance = new LoginSignupApp(); // Create a new instance of LoginSignupApp
        newAppInstance.setVisible(true); // Show the new login/signup app window
    }

    public GUI getGUI() {
        return gui;
    }
    public void showAdminPanel(boolean isVisible) {
        adminFrame.setVisible(isVisible);
    }
    public void updateAdminPanel() {
        adminPanel.updateUserDetails(users); // Pass the updated user information to the AdminPanel
    }
    public void updateUserDetails() {
        adminPanel.updateUserDetails(users); // Pass the updated user data to the Admin Panel
    }
    private void hashLoadedUserPasswords() {
        for (Student student : users.values()) {
            String hashedPassword = student.getPassword();
            student.setPassword(hashedPassword);
        }
    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginSignupApp app = new LoginSignupApp();
            app.setVisible(true);
        });
    }
}
