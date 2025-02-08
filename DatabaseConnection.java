import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/salonfrizerie";
    private static final String USER = "root";
    private static final String PASSWORD = "1111"; // Înlocuiește cu parola ta

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Eroare la conectarea la baza de date!");
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Connection connection = getConnection();
        if (connection != null) {
            System.out.println("Conexiunea la baza de date a fost realizată cu succes!");
        }
    }
}
