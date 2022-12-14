import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdminWindow extends JFrame implements ActionListener {
    private Admin admin = null;
    private static AdminViewRestaurantsWindow viewRestaurantsWindow = new AdminViewRestaurantsWindow();
    private static AdminCreateRestaurantWindow createRestaurantWindow = new AdminCreateRestaurantWindow(viewRestaurantsWindow);
    private static AdminManageKeywordsWindow manageKeywordsWindow = new AdminManageKeywordsWindow();


    // Logged In Panel

    private JPanel loggedInPanel = new JPanel();
    private JLabel loggedInAdminName = new JLabel();
    private JButton logoutBtn = new JButton("Logout");

    private JButton createRestaurantBtn = new JButton("Create Restaurant");
    private JButton viewRestaurantsBtn = new JButton("View Restaurants");

    private JButton manageKeywordsBtn = new JButton("Manage Keywords");


    // Login Panel

    private JPanel loginPanel = new JPanel();

    private JLabel loginLbl = new JLabel("login");
    private JTextField loginInput = new JTextField(20);

    private JLabel passwordLbl = new JLabel("password");
    private JTextField passwordInput = new JTextField(20);

    private JButton submitLoginBtn = new JButton("Login");

    // ~ Login Panel

    private void clearLoginInputs() {
        loginInput.setText("");
        passwordInput.setText("");
    }
    private boolean isValidLoginAttempt(String login, String password) {
        Admin realAdmin = AdminDAO.getInstance().getAdminByLogin(login);
        return null != realAdmin && realAdmin.getPassword().equals(password);
    }
    private boolean isLoggedIn() {
        return null != admin;
    }
    private void doLogin(String login) {
        if (isLoggedIn()) {
            System.out.println("admin: " + admin.getLogin() + ", is already logged in");
            return;
        }
        Admin realAdmin = AdminDAO.getInstance().getAdminByLogin(login);
        admin = new Admin(realAdmin.getId(), realAdmin.getLogin(), realAdmin.getPassword());
        loginPanel.setVisible(false);
        loggedInAdminName.setText("admin: " + admin.getLogin());
        loggedInPanel.setVisible(true);
    }
    private void doLogout() {
        if (!isLoggedIn()) {
            System.out.println("no admin is logged in, can't do logout");
            return;
        }
        admin = null;
        loggedInPanel.setVisible(false);
        createRestaurantWindow.setVisible(false);
        viewRestaurantsWindow.setVisible(false);
        manageKeywordsWindow.setVisible(false);
        loginPanel.setVisible(true);
    }
    private void openCreateRestaurantWindow() {
        createRestaurantWindow.setVisible(true);
    }
    private void openViewRestaurantsWindow() {
        viewRestaurantsWindow.setVisible(true);
    }
    private void openManageKeywordsWindow() {
        manageKeywordsWindow.setVisible(true);
    }

    public AdminWindow() {
        setTitle("Admin");
        setLayout(new FlowLayout());

        loginPanel.add(loginLbl);
        loginPanel.add(loginInput);
        loginPanel.add(passwordLbl);
        loginPanel.add(passwordInput);
        loginPanel.add(submitLoginBtn);

        submitLoginBtn.addActionListener(this);

        add(loginPanel);

        loggedInPanel.add(loggedInAdminName);
        loggedInPanel.add(logoutBtn);
        loggedInPanel.add(createRestaurantBtn);
        loggedInPanel.add(viewRestaurantsBtn);
        loggedInPanel.add(manageKeywordsBtn);
        loggedInPanel.setVisible(false);

        logoutBtn.addActionListener(this);
        createRestaurantBtn.addActionListener(this);
        viewRestaurantsBtn.addActionListener(this);
        manageKeywordsBtn.addActionListener(this);

        add(loggedInPanel);

        setSize(800, 600);
        setVisible(false);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (isLoggedIn()) {
                    doLogout();
                }
                MainWindow.getInstance().setVisible(true);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitLoginBtn) {
            if (isValidLoginAttempt(loginInput.getText(), passwordInput.getText())) {
                doLogin(loginInput.getText());
                clearLoginInputs();
            }
        } else if (e.getSource() == logoutBtn) {
            doLogout();
        } else if (e.getSource() == createRestaurantBtn) {
            openCreateRestaurantWindow();
        } else if (e.getSource() == viewRestaurantsBtn) {
            openViewRestaurantsWindow();
        } else if (e.getSource() == manageKeywordsBtn) {
            openManageKeywordsWindow();
        }
    }
}
