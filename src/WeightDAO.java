import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Vector;

public class WeightDAO {
    private HashMap<Integer, Weight> weights = new HashMap<>();
    private void loadWeights() {
        System.out.println("loadWeights");
        try {
            Connection con = DbConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from weights");
            while (rs.next()) {
                weights.put(rs.getInt(1), new Weight(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }
            con.close();

            System.out.println("--- loaded weights ---");
            System.out.println(weights);
        } catch(Exception e){ System.out.println(e);}
    }
    public Weight getWeightById(int weightId) {
        if (weights.containsKey(weightId)) {
            return weights.get(weightId);
        }
        return null;
    }
    public Weight getWeightByName(String weightName) {
        for (Weight x : weights.values()) {
            if (x.getName().equals(weightName)) {
                return x;
            }
        }
        return null;
    }

    public void printAll() {
        System.out.println("--- all weights ---");
        System.out.println(weights);
    }


    // Singleton
    private static WeightDAO instance;
    public static WeightDAO createInstance() {
        if (null == instance) {
            instance = new WeightDAO();
        }
        return instance;
    }
    public static WeightDAO getInstance() {
        return instance;
    }
    private WeightDAO() {
        loadWeights();
    }
}
