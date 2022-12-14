import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame implements ActionListener {
    private static UserWindow userWindow = new UserWindow();
    private static AdminWindow adminWindow = new AdminWindow();

    private JButton forUserBtn = new JButton("For User");
    private JButton forAdminBtn = new JButton("For Admin");

    private static MainWindow instance;
    public static MainWindow createInstance() {
        if (null == instance) {
            instance = new MainWindow();
        }
        return instance;
    }
    public static MainWindow getInstance() {
        return instance;
    }
    private MainWindow() {

        setTitle("Main");
        setLayout(new FlowLayout());
        add(forUserBtn);
        add(forAdminBtn);

        forUserBtn.addActionListener(this);
        forAdminBtn.addActionListener(this);

        setSize(600, 600);
        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == forAdminBtn) {
            setVisible(false);
            adminWindow.setVisible(true);
        } else if (e.getSource() == forUserBtn) {
            setVisible(false);
            userWindow.setVisible(true);
        }
    }
}
