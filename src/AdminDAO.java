import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class AdminDAO {
    private HashMap<String, Admin> admins = new HashMap<>();

    private void loadAdmins() {
        System.out.println("loadAdmins");
        try {
            Connection con = DbConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from admins");
            while (rs.next()) {
                admins.put(rs.getString(2), new Admin(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
            con.close();


            System.out.println("--- loaded admins ---");
            System.out.println(admins);
        } catch(Exception e){ System.out.println(e);}
    }
    public Admin getAdminByLogin(String adminLogin) {
        if (admins.containsKey(adminLogin)) {
            return admins.get(adminLogin);
        }
        return null;
    }

    public void printAll() {
        System.out.println("--- all admins ---");
        System.out.println(admins);
    }


    // Singleton
    private static AdminDAO instance;
    public static AdminDAO createInstance() {
        if (null == instance) {
            instance = new AdminDAO();
        }
        return instance;
    }
    public static AdminDAO getInstance() {
        return instance;
    }
    private AdminDAO() {
        loadAdmins();
    }
}
