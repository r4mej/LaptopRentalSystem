import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class AdminPanel extends JPanel {
    private AdminGUI adminGUI;

    public AdminPanel(LoginSignupApp app) {
        adminGUI = new AdminGUI(app);
        add(adminGUI.getMainPanel());

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.setResizable(false); // Set the frame non-resizable
        }
    }
}
