import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.io.File;
import javax.imageio.ImageIO;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LoginPanel extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;
    private Image backgroundImage;

    private LoginSignupApp app;
    private HashMap<String, Student> users;

    public LoginPanel(LoginSignupApp app, HashMap<String, Student> users) {
        this.app = app;
        this.users = users;
        setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 50, 100, 25);
        add(usernameLabel);

        usernameField = new JTextField(15);
        usernameField.setBounds(160, 50, 150, 25);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 100, 100, 25);
        add(passwordLabel);

        passwordField = new JPasswordField(15);
        passwordField.setBounds(160, 100, 150, 25);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 150, 80, 30);
        loginButton.addActionListener(e -> handleLogin());
        add(loginButton);

        signupButton = new JButton("Signup");
        signupButton.setBounds(200, 150, 80, 30);
        signupButton.addActionListener(e -> app.showSignupForm());
        add(signupButton);

        try {
            backgroundImage = ImageIO.read(new File("Loginpic.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
    
        if (isValidLogin(username, password)) {
            JOptionPane.showMessageDialog(this, "Welcome, " + username + "!", "Login Successful",
                    JOptionPane.INFORMATION_MESSAGE);
            if (isAdmin(username)) {
                app.showAdminPanel(true);
            } else {
                app.showGUI();
            }
            app.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean isAdmin(String username) {
        AdminHandling adminHandler = new AdminHandling();
        return adminHandler.isAdmin(username);
    }
    

    private boolean isValidLogin(String username, String password) {
        if (users.containsKey(username)) {
            Student student = users.get(username);
            String storedPasswordHash = student.getPassword(); // Get the stored hashed password
    
            // Hash the entered password for comparison using the same method
            String enteredPasswordHash = PasswordUtils.hashPassword(password);
    
            // Compare the entered password's hash with the stored hash
            return enteredPasswordHash != null && enteredPasswordHash.equals(storedPasswordHash);
        } else {
            // Admin handling for comparison
            AdminHandling adminHandler = new AdminHandling();
            String storedPassword = adminHandler.retrieveAdminCredentials(username);
    
            if (storedPassword != null) {
                return storedPassword.equals(password);
            }
        }
        return false;
    }
    
    
}
