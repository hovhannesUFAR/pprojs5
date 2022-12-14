public class DbManager {
    
    // Singleton
    private static DbManager instance;
    public static DbManager createInstance() {
        if (null == instance) {
            instance = new DbManager();
        }
        return instance;
    }
    public static DbManager getInstance() {
        return instance;
    }
    private DbManager() {
        AdminDAO.createInstance();
        UserDAO.createInstance();
        RestaurantDAO.createInstance();
        RestaurantReviewDAO.createInstance();
        WeightDAO.createInstance();
        KeywordDAO.createInstance();
    }
}
