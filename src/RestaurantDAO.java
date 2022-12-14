import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Vector;

public class RestaurantDAO {
    private int highestRestaurantId = 0;
    private HashMap<String, Restaurant> restaurants = new HashMap<>();

    public Vector<Vector<String>> getAllRestaurantsTableData() {
        Vector<Vector<String>> result = new Vector<Vector<String>>();
        for (Restaurant x : restaurants.values()) {
            Vector<String> row = new Vector<String>();
            row.add(Integer.toString(x.getId()));
            row.add(x.getName());
            row.add(x.getAddress());
            row.add(x.getDescription());
            row.add(Integer.toString(x.getEstablishmentYear()));
            row.add(Integer.toString(x.getScore()));
            result.add(row);
        }
        return result;
    }
    public void updateRestaurantScore(Restaurant restaurant, int newScore) {
        try {
            Connection con = DbConnection.getConnection();

            String updateString = "update restaurants set score = ? where id = ?";

            PreparedStatement updateRestaurant = con.prepareStatement(updateString);
            updateRestaurant.setInt(1, newScore);
            updateRestaurant.setInt(2, restaurant.getId());

            updateRestaurant.executeUpdate();
            con.close();

            restaurant.setScore(newScore);


            System.out.println("--- loaded restaurants ---");
            System.out.println(restaurants);
        } catch(Exception e){ System.out.println(e);}
    }
    private void loadRestaurants() {
        System.out.println("loadRestaurants");
        try {
            Connection con = DbConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from restaurants");
            while (rs.next()) {
                restaurants.put(rs.getString(2), new Restaurant(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6)));
                highestRestaurantId = rs.getInt(1);
            }
            con.close();

            System.out.println("--- loaded restaurants ---");
            System.out.println(restaurants);
        } catch(Exception e){ System.out.println(e);}
    }
    public Restaurant getRestaurantByName(String restaurantName) {
        if (restaurants.containsKey(restaurantName)) {
            return restaurants.get(restaurantName);
        }
        return null;
    }

    public void saveRestaurant(Restaurant restaurant) {
        System.out.println("saveRestaurant");
        if (restaurants.containsKey(restaurant.getName())) {
            System.out.println("restaurant: " + restaurant.getName() + ", already exists, can't save the restaurant");
            return;
        }
        try {
            Connection con = DbConnection.getConnection();

            String insertString = "insert into restaurants(name, address, description, establishment_year, score) values(?, ?, ?, ?, ?)";

            PreparedStatement insertRestaurant = con.prepareStatement(insertString);
            insertRestaurant.setString(1, restaurant.getName());
            insertRestaurant.setString(2, restaurant.getAddress());
            insertRestaurant.setString(3, restaurant.getDescription());
            insertRestaurant.setInt(4, restaurant.getEstablishmentYear());
            insertRestaurant.setInt(5, restaurant.getScore());


            insertRestaurant.executeUpdate();
            con.close();

            restaurant.setId(++highestRestaurantId);
            restaurants.put(restaurant.getName(), restaurant);


            System.out.println("--- after save, loaded restaurants ---");
            System.out.println(restaurants);
        } catch(Exception e){ System.out.println(e);}
    }

    public void printAll() {
        System.out.println("--- all restaurants ---");
        System.out.println(restaurants);
    }


    // Singleton
    private static RestaurantDAO instance;
    public static RestaurantDAO createInstance() {
        if (null == instance) {
            instance = new RestaurantDAO();
        }
        return instance;
    }
    public static RestaurantDAO getInstance() {
        return instance;
    }
    private RestaurantDAO() {
        loadRestaurants();
    }
}
