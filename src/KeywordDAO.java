import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Vector;

public class KeywordDAO {
    private int highestKeywordId = 0;
    private HashMap<String, Keyword> keywords = new HashMap<>();
    private void loadKeywords() {
        System.out.println("loadKeywords");
        try {
            Connection con = DbConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from keywords");
            while (rs.next()) {
                keywords.put(rs.getString(2), new Keyword(rs.getInt(1), rs.getString(2), rs.getInt(3)));
                highestKeywordId = rs.getInt(1);
            }
            con.close();

            System.out.println("--- loaded keywords ---");
            System.out.println(keywords);
        } catch(Exception e){ System.out.println(e);}
    }
    public Vector<Vector<String>> getAllKeywordsTableData() {
        Vector<Vector<String>> result = new Vector<Vector<String>>();
        for (Keyword x : keywords.values()) {
            Vector<String> row = new Vector<String>();
            row.add(Integer.toString(x.getId()));
            row.add(x.getName());
            row.add(WeightDAO.getInstance().getWeightById(x.getWeightId()).getName());
            result.add(row);
        }
        return result;
    }
    public void saveKeyword(Keyword keyword) {
        System.out.println("saveKeyword");
        if (keywords.containsKey(keyword.getName())) {
            System.out.println("keyword: " + keyword.getName() + ", already exists, can't save the keyword");
            return;
        }
        try {
            Connection con = DbConnection.getConnection();

            String insertString = "insert into keywords(name, weight_id) values(?, ?)";

            PreparedStatement insertRestaurant = con.prepareStatement(insertString);
            insertRestaurant.setString(1, keyword.getName());
            insertRestaurant.setInt(2, keyword.getWeightId());

            insertRestaurant.executeUpdate();
            con.close();

            keyword.setId(++highestKeywordId);
            keywords.put(keyword.getName(), keyword);


            System.out.println("--- after save, loaded keywords ---");
            System.out.println(keywords);
        } catch(Exception e){ System.out.println(e);}
    }
    public Keyword getKeywordByName(String keywordName) {
        if (keywords.containsKey(keywordName)) {
            return keywords.get(keywordName);
        }
        return null;
    }

    public void printAll() {
        System.out.println("--- all keywords ---");
        System.out.println(keywords);
    }


    // Singleton
    private static KeywordDAO instance;
    public static KeywordDAO createInstance() {
        if (null == instance) {
            instance = new KeywordDAO();
        }
        return instance;
    }
    public static KeywordDAO getInstance() {
        return instance;
    }
    private KeywordDAO() {
        loadKeywords();
    }
}
