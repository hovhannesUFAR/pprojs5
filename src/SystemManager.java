import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SystemManager {
    public int getRestaurantReviewCorrespondingWeight(String restaurantReviewDescription) {
        String[] words = restaurantReviewDescription.split(" ");
        int totalScore = 0;
        boolean containsAtLeastOneAwfulWord = false;
        Weight awful = WeightDAO.getInstance().getWeightByName("Awful");
        Weight bad = WeightDAO.getInstance().getWeightByName("Bad");
        Weight normal = WeightDAO.getInstance().getWeightByName("Normal");
        Weight good = WeightDAO.getInstance().getWeightByName("Good");
        Weight excellent = WeightDAO.getInstance().getWeightByName("Excellent");
        for (String x : words) {
            x = x.toLowerCase();
            Keyword keyword = KeywordDAO.getInstance().getKeywordByName(x);
            if (null != keyword) {
                totalScore += WeightDAO.getInstance().getWeightById(keyword.getWeightId()).getValue();
                if (awful.getId() == keyword.getWeightId()) {
                    containsAtLeastOneAwfulWord = true;
                }
            }
        }
        if (totalScore <= -5) {
            return awful.getId();
        } else if (totalScore <= -2) {
            return bad.getId();
        } else if (!containsAtLeastOneAwfulWord) {
            if (totalScore >= 5) {
                return excellent.getId();
            } else if (totalScore >= 2) {
                return good.getId();
            }
        }
        return normal.getId();
    }

    // Singleton
    private static SystemManager instance;
    public static SystemManager createInstance() {
        if (null == instance) {
            instance = new SystemManager();
        }
        return instance;
    }
    public static SystemManager getInstance() {
        return instance;
    }
    private SystemManager() {

    }
}
