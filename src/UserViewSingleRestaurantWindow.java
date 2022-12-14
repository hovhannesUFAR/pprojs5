import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class UserViewSingleRestaurantWindow extends JFrame implements ActionListener {
    UserViewRestaurantsWindow parentWindow = null;
    private Restaurant restaurant = null;

    private JPanel mainPanel = new JPanel();

    // Restaurant Info Panel

    private JPanel restaurantInfoPanel = new JPanel();


    private JPanel restaurantInfoNamePanel = new JPanel();
    private JLabel restaurantInfoNameLbl = new JLabel("Name:");
    private JLabel restaurantInfoName = new JLabel();

    private JPanel restaurantInfoAddressPanel = new JPanel();
    private JLabel restaurantInfoAddressLbl = new JLabel("Address:");
    private JLabel restaurantInfoAddress = new JLabel();

    private JPanel restaurantInfoDescriptionPanel = new JPanel();
    private JLabel restaurantInfoDescriptionLbl = new JLabel("Description:");
    private JLabel restaurantInfoDescription = new JLabel();

    private JPanel restaurantInfoEstablishmentYearPanel = new JPanel();
    private JLabel getRestaurantInfoEstablishmentYearLbl = new JLabel("Established:");
    private JLabel restaurantInfoEstablishmentYear = new JLabel();

    private JPanel restaurantInfoScorePanel = new JPanel();
    private JLabel restaurantInfoScoreLbl = new JLabel("Score:");
    private JLabel restaurantInfoScore = new JLabel();

    // Write Review Panel

    private JPanel restaurantWriteReviewPanel = new JPanel();
    private JLabel restaurantWriteReviewDescriptionLbl = new JLabel("Your Review");
    private JTextArea restaurantWriteReviewDescriptionInput = new JTextArea(5, 20);
    private JButton restaurantWriteReviewPostBtn = new JButton("Post");

    // All reviews

    private JPanel restaurantAllReviewsPanel = new JPanel();
    private JLabel restaurantAllReviewsLbl = new JLabel("All reviews");
    private JScrollPane restaurantAllReviewsScrollPane = null;
    DefaultListModel restaurantAllReviewsListModel = new DefaultListModel();
    private JList restaurantAllReviewsList = new JList(restaurantAllReviewsListModel);

    private void updateInfoPanel() {
        restaurantInfoName.setText(restaurant.getName());
        restaurantInfoAddress.setText(restaurant.getAddress());
        restaurantInfoDescription.setText(restaurant.getDescription());
        restaurantInfoEstablishmentYear.setText(Integer.toString(restaurant.getEstablishmentYear()));
        restaurantInfoScore.setText(Integer.toString(restaurant.getScore()));

        restaurantAllReviewsListModel.clear();
        Vector<RestaurantReview> singleRestaurantReviews = RestaurantReviewDAO.getInstance().getRestaurantReviewsByRestaurantId(restaurant.getId());
        if (null != singleRestaurantReviews) {
            for (RestaurantReview x : singleRestaurantReviews) {
                restaurantAllReviewsListModel.addElement(UserDAO.getInstance().getUserById(x.getUserId()).getLogin() + ": " + x.getDescription());
            }
        }
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        updateInfoPanel();
    }

    private boolean isValidRestaurantReviewPostAttempt(String reviewDescription) {
        return (reviewDescription.length() > 0 && reviewDescription.length() <= 400);
    }
    private void postReview(String reviewDescription) {
        RestaurantReview restaurantReview = new RestaurantReview(reviewDescription);
        restaurantReview.setRestaurantId(restaurant.getId());
        restaurantReview.setUserId(UserWindow.user.getId());
        restaurantReview.setWeightId(SystemManager.getInstance().getRestaurantReviewCorrespondingWeight(restaurantReview.getDescription()));

        RestaurantReviewDAO.getInstance().saveRestaurantReview(restaurantReview, restaurant.getName());

        // manually add the new review and update the score
        restaurantAllReviewsListModel.addElement(UserWindow.user.getLogin() + ": " + reviewDescription);
        restaurantInfoScore.setText(Integer.toString(restaurant.getScore()));
        int currentRestaurantRowInParent = -1;
        DefaultTableModel mdl = parentWindow.getRestaurantsModel();
        int restaurantIdColumn = 0;
        for (int rowCount = mdl.getRowCount(), i = 0; i < rowCount; ++i) {
            if (mdl.getValueAt(i, restaurantIdColumn).equals(Integer.toString(restaurant.getId()))) {
                currentRestaurantRowInParent = i;
                break;
            }
        }
        if (-1 != currentRestaurantRowInParent) {
            int restaurantScoreColumn = 5;
            mdl.setValueAt(Integer.toString(restaurant.getScore()), currentRestaurantRowInParent, restaurantScoreColumn);
            mdl.fireTableCellUpdated(currentRestaurantRowInParent, restaurantScoreColumn);
        }
    }
    void clearInputs() {
        restaurantWriteReviewDescriptionInput.setText("");
    }
    private void prepareRestaurantInfoPanel() {
        restaurantInfoPanel.setLayout(new BoxLayout(restaurantInfoPanel, BoxLayout.PAGE_AXIS));

        restaurantInfoNamePanel.add(restaurantInfoNameLbl);
        restaurantInfoNamePanel.add(restaurantInfoName);

        restaurantInfoAddressPanel.add(restaurantInfoAddressLbl);

        restaurantInfoAddressPanel.add(restaurantInfoAddress);

        restaurantInfoDescriptionPanel.add(restaurantInfoDescriptionLbl);
        restaurantInfoDescriptionPanel.add(restaurantInfoDescription);

        restaurantInfoEstablishmentYearPanel.add(getRestaurantInfoEstablishmentYearLbl);
        restaurantInfoEstablishmentYearPanel.add(restaurantInfoEstablishmentYear);

        restaurantInfoScorePanel.add(restaurantInfoScoreLbl);
        restaurantInfoScorePanel.add(restaurantInfoScore);

        restaurantInfoPanel.add(restaurantInfoNamePanel);
        restaurantInfoPanel.add(restaurantInfoAddressPanel);
        restaurantInfoPanel.add(restaurantInfoDescriptionPanel);
        restaurantInfoPanel.add(restaurantInfoEstablishmentYearPanel);
        restaurantInfoPanel.add(restaurantInfoScorePanel);

        mainPanel.add(restaurantInfoPanel);
    }

    void prepareRestaurantWriteReviewPanel() {
        restaurantWriteReviewPanel.setLayout(new BoxLayout(restaurantWriteReviewPanel, BoxLayout.PAGE_AXIS));

        restaurantWriteReviewDescriptionInput.setBackground(new Color(222,224,223));
        restaurantWriteReviewDescriptionInput.setLineWrap(true);

        restaurantWriteReviewPanel.add(restaurantWriteReviewDescriptionLbl);
        restaurantWriteReviewPanel.add(restaurantWriteReviewDescriptionInput);
        restaurantWriteReviewPanel.add(restaurantWriteReviewPostBtn);

        restaurantWriteReviewPostBtn.addActionListener(this);

        mainPanel.add(restaurantWriteReviewPanel);
    }

    void prepareRestaurantAllReviewsPanel() {
        restaurantAllReviewsPanel.setLayout(new BoxLayout(restaurantAllReviewsPanel, BoxLayout.PAGE_AXIS));

        restaurantAllReviewsPanel.add(restaurantAllReviewsLbl);

        restaurantAllReviewsScrollPane = new JScrollPane(restaurantAllReviewsList);
        restaurantAllReviewsPanel.add(restaurantAllReviewsScrollPane);

        mainPanel.add(restaurantAllReviewsPanel);
    }

    private void init() {
        setTitle("User View Single Restaurant");
        setLayout(new FlowLayout());

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        prepareRestaurantInfoPanel();
        prepareRestaurantWriteReviewPanel();
        prepareRestaurantAllReviewsPanel();

        add(mainPanel);

        setSize(1100, 600);
        setVisible(false);
    }
    public UserViewSingleRestaurantWindow() throws HeadlessException {
        init();
    }

    public UserViewSingleRestaurantWindow(UserViewRestaurantsWindow parentWindow) throws HeadlessException {
        this.parentWindow = parentWindow;
        init();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == restaurantWriteReviewPostBtn) {
            if (isValidRestaurantReviewPostAttempt(restaurantWriteReviewDescriptionInput.getText())) {
                postReview(restaurantWriteReviewDescriptionInput.getText());
                clearInputs();
            }
        }
    }
}
