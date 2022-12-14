import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class UserDAO {
    private int highestUserId = 0;
    private HashMap<String, User> users = new HashMap<>();

    private void loadUsers() {
        System.out.println("loadUsers");
        try {
            Connection con = DbConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from users");
            while (rs.next()) {
                users.put(rs.getString(2), new User(rs.getInt(1), rs.getString(2), rs.getString(3)));
                highestUserId = rs.getInt(1);
            }
            con.close();


            System.out.println("--- loaded users ---");
            System.out.println(users);
        } catch(Exception e){ System.out.println(e);}
    }
    public User getUserByLogin(String userLogin) {
        if (users.containsKey(userLogin)) {
            return users.get(userLogin);
        }
        return null;
    }
    public User getUserById(int userId) {
        for (User x : users.values()) {
            if (x.getId() == userId) {
                return x;
            }
        }
        return null;
    }
    public void saveUser(User user) {
        System.out.println("saveUser");
        if (users.containsKey(user.getLogin())) {
            System.out.println("user: " + user.getLogin() + ", already exists, can't save the user");
            return;
        }
        try {
            Connection con = DbConnection.getConnection();
            //Statement stmt = con.createStatement();

            String insertString = "insert into users(login, password) values(?, ?)";

            PreparedStatement insertUser = con.prepareStatement(insertString);
            insertUser.setString(1, user.getLogin());
            insertUser.setString(2, user.getPassword());

            insertUser.executeUpdate();
            con.close();

            user.setId(++highestUserId);
            users.put(user.getLogin(), user);


            System.out.println("--- after save, loaded users ---");
            System.out.println(users);
        } catch(Exception e){ System.out.println(e);}
    }

    public void printAll() {
        System.out.println("--- all users ---");
        System.out.println(users);
    }


    // Singleton
    private static UserDAO instance;
    public static UserDAO createInstance() {
        if (null == instance) {
            instance = new UserDAO();
        }
        return instance;
    }
    public static UserDAO getInstance() {
        return instance;
    }
    private UserDAO() {
        loadUsers();
    }
}
