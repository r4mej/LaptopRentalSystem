import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;


public class SignupPanel extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField idField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JButton signupButton;
    private JButton backButton;
    private Image backgroundImage;
    private JComboBox<String> roleDropdown;
    private LoginSignupApp app;
    private HashMap<String, Student> users;

    public SignupPanel(LoginSignupApp app, HashMap<String, Student> users) {
        this.app = app;
        this.users = users;
        setLayout(null);

        JLabel firstnameLabel = new JLabel ("First Name:");
        firstnameLabel.setBounds(50, 50, 100, 25);
        add(firstnameLabel);

        firstNameField = new JTextField(15);
        firstNameField.setBounds(160,50,150,25);
        add(firstNameField);

        JLabel lastnameLabel = new JLabel ("Last Name:");
        lastnameLabel.setBounds(50, 100, 100, 25);
        add(lastnameLabel);

        lastNameField = new JTextField(15);
        lastNameField.setBounds(160,100,150,25);
        add(lastNameField);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 150, 100, 25);
        add(usernameLabel);

        usernameField = new JTextField(15);
        usernameField.setBounds(160, 150, 150, 25);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 200, 100, 25);
        add(passwordLabel);

        passwordField = new JPasswordField(15);
        passwordField.setBounds(160, 200, 150, 25);
        add(passwordField);

        JLabel idLabel = new JLabel("Student ID:");
        idLabel.setBounds(50, 250, 100, 25);
        add(idLabel);

        idField = new JTextField(15);
        idField.setBounds(160, 250, 150, 25);
        add(idField);

        signupButton = new JButton("Signup");
        signupButton.setBounds(100, 340, 80, 30);
        signupButton.addActionListener(e -> handleSignup());
        add(signupButton);

        backButton = new JButton("Back");
        backButton.setBounds(200, 340, 80, 30);
        backButton.addActionListener(e -> app.showLoginForm());
        add(backButton);

        // Dropdown for selecting role
        roleDropdown = new JComboBox<>(new String[] { "Student", "Admin" });
        roleDropdown.setBounds(100, 300, 180, 25);
        add(roleDropdown);

        try {
            backgroundImage = ImageIO.read(new File("bg/Signuppic.png"));
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
    //method for handling signup buttton
    private void handleSignup() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String id = idField.getText();
        String selectedRole = (String) roleDropdown.getSelectedItem();
    
        /*  Validation checks
         blank Username
         First name, Last name, username, password, and id should not be empty
         First name and last name should contain only letters
         Username should not be a single character
         Username should not contain numbers
         Username should contain only alphanumeric characters
         Password should be at least 8 characters long
         Student ID should not be empty/'0000-0000' format/should not be letters
        */

        if (username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a username!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!firstName.matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(this, "First name should contain only letters!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        if (!lastName.matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(this, "Last name should contain only letters!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        if (username.length() == 1 || username.equals(".")) {
            JOptionPane.showMessageDialog(this, "Username should not be a single character or a dot!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (username.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(this, "Username should not contain numbers!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!username.matches("^[a-zA-Z0-9]*$")) {
            JOptionPane.showMessageDialog(this, "Username should contain only alphanumeric characters!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
    
        if (password.length() < 8) {
            JOptionPane.showMessageDialog(this, "Password should be at least 8 characters long!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        if (!id.matches("\\d{4}-\\d{4}")) {
            JOptionPane.showMessageDialog(this, "Student ID should be in the format '0000-0000'!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }        
        // username taken
        if (isUsernameTaken(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // id taken
        if (isIdTaken(id)) {
            JOptionPane.showMessageDialog(this, "Student ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        
        // dropdown value logic
        // hashes passwords with PasswordUtils.hashPassword method
        if ("Admin".equals(selectedRole)) {
            String hashedPassword = PasswordUtils.hashPassword(password);
            app.saveAdminCredentials(username, hashedPassword); 
        } else if ("Student".equals(selectedRole)) {
            String hashedPassword = PasswordUtils.hashPassword(password);
            Student newStudent = new Student(username, hashedPassword, id, firstName, lastName); // Store hashed password
            users.put(username, newStudent);
            app.saveUsersToFile();
        }
    
        JOptionPane.showMessageDialog(this, "Signup successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        app.showLoginForm();
    }
    //simple method for handling taken usernames 
    private boolean isUsernameTaken(String username) {
        return users.containsKey(username);
    }
    // weird method for handling taken id
    private boolean isIdTaken(String id){
        return id != null && users.values().stream().anyMatch(student -> id.equals(student.getId()));
    }
}
