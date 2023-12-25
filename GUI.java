import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {
    private LoginSignupApp app;
    private Menu guiComponents;
    private Logic logic;

    public GUI(LoginSignupApp app) {
        this.app = app;
        setTitle("DORSU Laptop Rental");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        guiComponents = new Menu(this, new Logic(guiComponents), app);

        setVisible(false);
    }

    public void showGUIComponents() {

        guiComponents = new Menu(this, new Logic(guiComponents), app);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logic.handleAction(e);
    }

}
