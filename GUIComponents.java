import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// Custom JPanel to draw a background image
class ImagePanel extends JPanel {
    private Image backgroundImage;

    public ImagePanel(String imagePath) {
        try {
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
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
}

public class GUIComponents {
    private JFrame frame;
    private JButton rentButton, returnButton, listButton, logoutButton;
    private JPanel panel;
    private Logic logic;
    private LoginSignupApp app;

    public GUIComponents(JFrame frame, Logic logic, LoginSignupApp app) {
        this.frame = frame;
        this.logic = logic;
        this.app = app;
        initComponents();
    }

    private void initComponents() {
        // Replace "path_to_your_image.jpg" with the actual path to your image
        String imagePath = "Menupic.png";
        
        // Create the panel with a background image
        panel = new ImagePanel(imagePath);
        panel.setLayout(new GridLayout(4, 2, 5, 7));

        Border border = BorderFactory.createLineBorder(Color.GREEN, 3);
        panel.setBorder(border);

        JLabel label = new JLabel("");
        panel.add(label);

        JLabel blank = new JLabel();
        panel.add(blank);

        rentButton = new JButton("Rent a laptop");
        rentButton.addActionListener(e -> handleRentButtonClick());
        panel.add(rentButton);

        returnButton = new JButton("Return a laptop");
        returnButton.addActionListener(e -> handleReturnButtonClick());
        panel.add(returnButton);

        listButton = new JButton("Check available laptops");
        listButton.addActionListener(e -> handleListButtonClick());
        panel.add(listButton);

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> handleLogoutButtonClick());
        panel.add(logoutButton);

        frame.add(panel);
    }

    private void handleRentButtonClick() {
        logic.createTextFieldWindow("Rent a Laptop", "Student ID", "Username", "Laptop ID");
    }

    private void handleReturnButtonClick() {
        logic.returnLaptop();
    }

    private void handleListButtonClick() {
        logic.displayAvailableLaptops();
    }

    public JButton getRentButton() {
        return rentButton;
    }

    public JButton getReturnButton() {
        return returnButton;
    }

    public JButton getListButton() {
        return listButton;
    }

    public void handleLogoutButtonClick() {
        app.showAdminPanel(false);
        frame.setVisible(false); // Hide the GUI
        app.updateUserDetails();
        LoginSignupApp newAppInstance = new LoginSignupApp(); // Create a new instance of LoginSignupApp
        newAppInstance.setVisible(true); // Show the new login/signup app window
    }
}
