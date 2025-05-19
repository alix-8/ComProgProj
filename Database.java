import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:mysql://db-mysql-sgp1-information-management-do-user-9437339-0.l.db.ondigitalocean.com:25060/reconnect?useSSL=true&requireSSL=true";
    private static final String USER = "reconnect_user";
    private static final String PASSWORD = "AVNS_DB3f2_oo-klHmzd1Nxk";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection successful!");

            migrateUp(conn);

            conn.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void migrateUp(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(20) NOT NULL UNIQUE, password VARCHAR(20) NOT NULL)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS game_progress (id INT AUTO_INCREMENT PRIMARY KEY, user_id INT, oxygen_remaining INT DEFAULT 100, systems_fixed INT DEFAULT 0, completion_time TIME, FOREIGN KEY (user_id) REFERENCES users(id))");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS questions (id INT AUTO_INCREMENT PRIMARY KEY, question TEXT NOT NULL, answer VARCHAR(255) NOT NULL)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS player_answers (question_id INT, user_id INT, is_correct BOOLEAN, PRIMARY KEY (question_id, user_id), FOREIGN KEY (question_id) REFERENCES questions(id), FOREIGN KEY (user_id) REFERENCES users(id))");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS leader_board (id INT AUTO_INCREMENT PRIMARY KEY, user_id INT, game_progress_id INT, is_completed BOOLEAN, FOREIGN KEY (user_id) REFERENCES users(id), FOREIGN KEY (game_progress_id) REFERENCES game_progress(id))");

            System.out.println("✅ Tables created successfully.");
        } catch (SQLException e) {
            System.err.println("❌ Failed to migrate up: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
