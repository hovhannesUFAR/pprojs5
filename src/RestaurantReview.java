public class RestaurantReview {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getWeightId() {
        return weightId;
    }

    public void setWeightId(int weightId) {
        this.weightId = weightId;
    }

    @Override
    public String toString() {
        return "RestaurantReview{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                ", restaurantId=" + restaurantId +
                ", weightId=" + weightId +
                '}';
    }

    public RestaurantReview(String description) {
        this.description = description;
    }

    public RestaurantReview(String description, int userId, int restaurantId, int weightId) {
        this.description = description;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.weightId = weightId;
    }

    public RestaurantReview(int id, String description, int userId, int restaurantId, int weightId) {
        this.id = id;
        this.description = description;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.weightId = weightId;
    }

    private int id;
    private String description;
    private int userId;
    private int restaurantId;
    private int weightId;
}
