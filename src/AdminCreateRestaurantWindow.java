import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminCreateRestaurantWindow extends JFrame implements ActionListener {
    private AdminViewRestaurantsWindow viewRestaurantsWindow = null;
    private JPanel creationPanel = new JPanel();
    private JPanel restaurantNamePanel = new JPanel();

    private JLabel restaurantNameLbl = new JLabel("Restaurant Name");
    private JTextField restaurantNameInput = new JTextField(20);

    private JPanel restaurantAddressPanel = new JPanel();
    private JLabel restaurantAddressLbl = new JLabel("Restaurant Address");
    private JTextField restaurantAddressInput = new JTextField(20);


    private JPanel restaurantDescriptionPanel = new JPanel();
    private JLabel restaurantDescriptionLbl = new JLabel("Restaurant Description");
    private JTextArea restaurantDescriptionInput = new JTextArea(5, 20);

    private JPanel restaurantEstablishmentYearPanel = new JPanel();
    private JLabel restaurantEstablishmentYearLbl = new JLabel("Restaurant Establishment year");
    private JTextField restaurantEstablishmentYearInput = new JTextField(5);

    private JButton createRestaurantBtn = new JButton("Create");


    private void clearInputs() {
        restaurantNameInput.setText("");
        restaurantAddressInput.setText("");
        restaurantDescriptionInput.setText("");
        restaurantEstablishmentYearInput.setText("");
    }
    private boolean isValidCreationAttempt(String restaurantName, String restaurantAddress, String restaurantDescription, String restaurantEstablishmentYear) {
        Restaurant realRestaurant = RestaurantDAO.getInstance().getRestaurantByName(restaurantName);
        return null == realRestaurant
                && (restaurantName.length() > 0 && restaurantName.length() <= 80)
                && (restaurantAddress.length() > 0 && restaurantAddress.length() <= 80)
                && (restaurantDescription.length() > 0 && restaurantDescription.length() <= 300)
                && restaurantEstablishmentYear.matches("^[1-2][0-9][0-9][0-9]$");
    }
    private void doCreate(String restaurantName, String restaurantAddress, String restaurantDescription, Integer restaurantEstablishmentYear) {
        RestaurantDAO.getInstance().saveRestaurant(new Restaurant(restaurantName, restaurantAddress, restaurantDescription, restaurantEstablishmentYear, 0));
        viewRestaurantsWindow.addRestaurantRow(RestaurantDAO.getInstance().getRestaurantByName(restaurantName));
    }

    public AdminCreateRestaurantWindow(AdminViewRestaurantsWindow viewRestaurantsWindow) {
        this.viewRestaurantsWindow = viewRestaurantsWindow;

        setTitle("Admin Create Restaurant");
        setLayout(new FlowLayout());

        creationPanel.setLayout(new BoxLayout(creationPanel, BoxLayout.PAGE_AXIS));

        restaurantNamePanel.add(restaurantNameLbl);
        restaurantNamePanel.add(restaurantNameInput);
        creationPanel.add(restaurantNamePanel);

        restaurantAddressPanel.add(restaurantAddressLbl);
        restaurantAddressPanel.add(restaurantAddressInput);
        creationPanel.add(restaurantAddressPanel);


        restaurantDescriptionPanel.add(restaurantDescriptionLbl);
        restaurantDescriptionPanel.add(restaurantDescriptionInput);

        restaurantDescriptionInput.setBackground(new Color(222,224,223));
        restaurantDescriptionInput.setLineWrap(true);
        creationPanel.add(restaurantDescriptionPanel);


        restaurantEstablishmentYearPanel.add(restaurantEstablishmentYearLbl);
        restaurantEstablishmentYearPanel.add(restaurantEstablishmentYearInput);
        creationPanel.add(restaurantEstablishmentYearPanel);

        creationPanel.add(createRestaurantBtn);

        createRestaurantBtn.addActionListener(this);

        add(creationPanel);



        setSize(600, 600);
        setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createRestaurantBtn) {
            if (isValidCreationAttempt(restaurantNameInput.getText(), restaurantAddressInput.getText(),
                    restaurantDescriptionInput.getText(), restaurantEstablishmentYearInput.getText())) {
                doCreate(restaurantNameInput.getText(), restaurantAddressInput.getText(),
                        restaurantDescriptionInput.getText(), Integer.parseInt(restaurantEstablishmentYearInput.getText()));
                clearInputs();
            }
        }
    }
}
