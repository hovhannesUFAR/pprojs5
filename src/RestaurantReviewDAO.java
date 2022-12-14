import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Vector;

public class RestaurantReviewDAO {
    private int highestRestaurantReviewId = 0;
    private HashMap<Integer, Vector<RestaurantReview>> restaurantReviews = new HashMap<>();

    private void loadRestaurantReviews() {
        System.out.println("loadRestaurantReviews");
        try {
            Connection con = DbConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from restaurant_reviews");
            while (rs.next()) {
                int restaurantId = rs.getInt(4);
                if (! restaurantReviews.containsKey(restaurantId)) {
                    restaurantReviews.put(restaurantId, new Vector<RestaurantReview>());
                }
                restaurantReviews.get(restaurantId).add(new RestaurantReview(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
                highestRestaurantReviewId = rs.getInt(1);
            }
            con.close();


            System.out.println("--- loaded restaurant reviews ---");
            System.out.println(restaurantReviews);
        } catch(Exception e){ System.out.println(e);}
    }
    public Vector<RestaurantReview> getRestaurantReviewsByRestaurantId(int restaurantId) {
        if (restaurantReviews.containsKey(restaurantId)) {
            return restaurantReviews.get(restaurantId);
        }
        return null;
    }

    public void saveRestaurantReview(RestaurantReview restaurantReview, String restaurantName) {
        System.out.println("saveRestaurantReview");
        try {
            Connection con = DbConnection.getConnection();

            String insertString = "insert into restaurant_reviews(description, user_id, restaurant_id, weight_id) values(?, ?, ?, ?)";

            PreparedStatement insertRestaurant = con.prepareStatement(insertString);
            insertRestaurant.setString(1, restaurantReview.getDescription());
            insertRestaurant.setInt(2, restaurantReview.getUserId());
            insertRestaurant.setInt(3, restaurantReview.getRestaurantId());
            insertRestaurant.setInt(4, restaurantReview.getWeightId());


            insertRestaurant.executeUpdate();
            con.close();

            restaurantReview.setId(++highestRestaurantReviewId);

            if (! restaurantReviews.containsKey(restaurantReview.getRestaurantId())) {
                restaurantReviews.put(restaurantReview.getRestaurantId(), new Vector<RestaurantReview>());
            }
            restaurantReviews.get(restaurantReview.getRestaurantId()).add(restaurantReview);

            Restaurant restaurant = RestaurantDAO.getInstance().getRestaurantByName(restaurantName);
            RestaurantDAO.getInstance().updateRestaurantScore(restaurant, restaurant.getScore() + WeightDAO.getInstance().getWeightById(restaurantReview.getWeightId()).getValue());

            System.out.println("--- after posting restaurant review, loaded restaurant reviews ---");
            System.out.println(restaurantReviews);
        } catch(Exception e){ System.out.println(e);}
    }

    public void printAll() {
        System.out.println("--- all restaurant reviews ---");
        System.out.println(restaurantReviews);
    }


    // Singleton
    private static RestaurantReviewDAO instance;
    public static RestaurantReviewDAO createInstance() {
        if (null == instance) {
            instance = new RestaurantReviewDAO();
        }
        return instance;
    }
    public static RestaurantReviewDAO getInstance() {
        return instance;
    }
    private RestaurantReviewDAO() {
        loadRestaurantReviews();
    }
}
