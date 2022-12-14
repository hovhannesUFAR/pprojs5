import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserWindow extends JFrame implements ActionListener {
    public static User user = null;
    private static UserViewRestaurantsWindow viewRestaurantsWindow = new UserViewRestaurantsWindow();

    // Logged In Panel

    private JPanel loggedInPanel = new JPanel();
    private JLabel loggedInUserName = new JLabel();

    private JButton viewRestaurantsBtn = new JButton("View Restaurants");
    private JButton logoutBtn = new JButton("Logout");


    // Login/Register Panel

    private JPanel loginPanel = new JPanel();

    private JLabel loginLbl = new JLabel("login");
    private JTextField loginInput = new JTextField(20);

    private JLabel passwordLbl = new JLabel("password");
    private JTextField passwordInput = new JTextField(20);

    private JButton submitLoginBtn = new JButton("Login");
    private JButton submitRegisterBtn = new JButton("Register");

    // ~ Login/Register Panel

    private void clearLoginInputs() {
        loginInput.setText("");
        passwordInput.setText("");
    }
    private boolean isValidRegisterAttempt(String login) {
        User realUser = UserDAO.getInstance().getUserByLogin(login);
        return null == realUser;
    }
    private void doRegister(String login, String password) {
        if (null != user) {
            System.out.println("user: " + user.getLogin() + ", is already logged in");
            return;
        }
        UserDAO.getInstance().saveUser(new User(login, password));
    }
    private boolean isValidLoginAttempt(String login, String password) {
        User realUser = UserDAO.getInstance().getUserByLogin(login);
        return null != realUser && realUser.getPassword().equals(password);
    }
    private boolean isLoggedIn() {
        return null != user;
    }
    private void doLogin(String login) {
        if (isLoggedIn()) {
            System.out.println("user: " + user.getLogin() + ", is already logged in");
            return;
        }
        User realUser = UserDAO.getInstance().getUserByLogin(login);
        user = new User(realUser.getId(), realUser.getLogin(), realUser.getPassword());
        loginPanel.setVisible(false);
        loggedInUserName.setText("user: " + user.getLogin());
        loggedInPanel.setVisible(true);
    }
    private void openViewRestaurantsWindow() {
        viewRestaurantsWindow.setVisible(true);
    }
    private void doLogout() {
        if (!isLoggedIn()) {
            System.out.println("no user is logged in, can't do logout");
            return;
        }
        user = null;
        viewRestaurantsWindow.hideInternalWindows();
        viewRestaurantsWindow.setVisible(false);
        loggedInPanel.setVisible(false);
        loginPanel.setVisible(true);
    }

    public UserWindow() {
        setTitle("User");
        setLayout(new FlowLayout());

        loginPanel.add(loginLbl);
        loginPanel.add(loginInput);
        loginPanel.add(passwordLbl);
        loginPanel.add(passwordInput);
        loginPanel.add(submitLoginBtn);
        loginPanel.add(submitRegisterBtn);

        submitLoginBtn.addActionListener(this);
        submitRegisterBtn.addActionListener(this);

        add(loginPanel);

        loggedInPanel.add(loggedInUserName);
        loggedInPanel.add(viewRestaurantsBtn);
        loggedInPanel.add(logoutBtn);
        loggedInPanel.setVisible(false);

        viewRestaurantsBtn.addActionListener(this);

        logoutBtn.addActionListener(this);

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
        } else if (e.getSource() == submitRegisterBtn) {
            if (isValidRegisterAttempt(loginInput.getText())) {
                doRegister(loginInput.getText(), passwordInput.getText());
                doLogin(loginInput.getText());
                clearLoginInputs();
            }
        } else if (e.getSource() == viewRestaurantsBtn) {
            openViewRestaurantsWindow();
        } else if (e.getSource() == logoutBtn) {
            doLogout();
        }
    }
}
