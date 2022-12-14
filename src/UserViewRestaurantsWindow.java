import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class UserViewRestaurantsWindow extends JFrame {
    private UserViewSingleRestaurantWindow viewSingleRestaurantWindow = new UserViewSingleRestaurantWindow(this);
    private JPanel restaurantsPanel = new JPanel();
    private JScrollPane restaurantsScrollPane = null;
    private JTable restaurantsTbl = null;

    public void hideInternalWindows() {
        viewSingleRestaurantWindow.setVisible(false);
    }
    public DefaultTableModel getRestaurantsModel() {
        return (DefaultTableModel) restaurantsTbl.getModel();
    }

    public UserViewRestaurantsWindow() {
        setTitle("User View Restaurants");
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
        // TODO: don't allow to change the text, add some disabling logic but not setEnabled(false)
        restaurantsTbl.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    System.out.println("clicked row: " + row);
                    DefaultTableModel model = (DefaultTableModel) restaurantsTbl.getModel();


                    String restaurantName = model.getValueAt(row, 1).toString();
                    System.out.println("name: " + model.getValueAt(row, 1));
                    viewSingleRestaurantWindow.setRestaurant(RestaurantDAO.getInstance().getRestaurantByName(restaurantName));
                    viewSingleRestaurantWindow.setVisible(true);
                }
            }
        });

        restaurantsScrollPane = new JScrollPane(restaurantsTbl);
        restaurantsPanel.add(restaurantsScrollPane);

        add(restaurantsPanel);



        setSize(600, 600);
        setVisible(false);
    }
}
