
import java.sql.*;
class DbConnection{
    public static Connection getConnection() {
        Connection con = null;
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/rrs_db", "root", "root");
        } catch (Exception e) {System.out.println(e);}
        return con;
    }
}