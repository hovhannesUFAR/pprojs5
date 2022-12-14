import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Vector;

public class AdminViewRestaurantsWindow extends JFrame {
    private JPanel restaurantsPanel = new JPanel();
    private JScrollPane restaurantsScrollPane = null;
    private JTable restaurantsTbl = null;
    public void addRestaurantRow(Restaurant restaurant) {
        DefaultTableModel model = (DefaultTableModel) restaurantsTbl.getModel();
        model.addRow(new Object[]{restaurant.getId(), restaurant.getName(), restaurant.getAddress(), restaurant.getDescription(), restaurant.getEstablishmentYear(), restaurant.getScore()});
    }

    public AdminViewRestaurantsWindow() {
        setTitle("Admin View Restaurants");
        setLayout(new FlowLayout());

        Vector<Restaurant> rs = new Vector<>();


        Vector<String> columns = new Vector<String>();
        columns.add("id");
        columns.add("name");
        columns.add("address");
        columns.add("description");
        columns.add("est.");
        columns.add("score");

        restaurantsTbl = new JTable(RestaurantDAO.getInstance().getAllRestaurantsTableData(), columns);
        restaurantsTbl.setEnabled(false);

        restaurantsScrollPane = new JScrollPane(restaurantsTbl);
        restaurantsPanel.add(restaurantsScrollPane);

        add(restaurantsPanel);



        setSize(600, 600);
        setVisible(false);
    }
}
