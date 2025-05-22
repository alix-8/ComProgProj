import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

            // migrate(conn);
            insertEasyQuestions(conn);
            insertHardQuestions(conn);

            conn.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    //to create tables
    public static void migrate(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(20) NOT NULL UNIQUE, " +
                    "password VARCHAR(20) NOT NULL)"
            );

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS game_progress (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "user_id INT, " +
                    "oxygen_remaining INT DEFAULT 100, " +
                    "systems_fixed INT DEFAULT 0, " +
                    "completion_time TIME, " +
                    "FOREIGN KEY (user_id) REFERENCES users(id))"
            );

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS questions (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "question TEXT NOT NULL, " +
                    "answer VARCHAR(255) NOT NULL, " +
                    "difficulty ENUM('easy', 'hard') NOT NULL)"
            );

            
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS choices (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "question_id INT, " +
                    "choice_text VARCHAR(255) NOT NULL, " +
                    "is_correct BOOLEAN DEFAULT FALSE, " +
                    "FOREIGN KEY (question_id) REFERENCES questions(id))"
            );



            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS player_answers (" +
                    "question_id INT, " +
                    "user_id INT, " +
                    "is_correct BOOLEAN, " +
                    "PRIMARY KEY (question_id, user_id), " +
                    "FOREIGN KEY (question_id) REFERENCES questions(id), " +
                    "FOREIGN KEY (user_id) REFERENCES users(id))"
            );

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS leader_board (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "user_id INT, " +
                    "game_progress_id INT, " +
                    "is_completed BOOLEAN, " +
                    "FOREIGN KEY (user_id) REFERENCES users(id), " +
                    "FOREIGN KEY (game_progress_id) REFERENCES game_progress(id))"
            );

            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to migrate up: " + e.getMessage());
            e.printStackTrace();
        }
    }
    //inserting the easy questions
    public static void insertEasyQuestions(Connection conn) {
    String sql = "INSERT INTO questions (question, answer, difficulty) VALUES (?, ?, ?)";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        String[][] easyQuestions = {
            {"The CPU is also known as the brain of the computer.", "True", "easy"},
            {"A mouse is primarily used to type letters and numbers into a computer.", "False", "easy"},
            {"Wi-Fi allows devices to connect to the internet without cables.", "True", "easy"},
            {"Samsung is the company that makes the iPhone.", "False", "easy"},
            {"WWW stands for World Wide Web.", "True", "easy"},
            {"Java was developed by a team led by James Gosling.", "True", "easy"},
            {"The homepage is the main page of a website.", "True", "easy"},
            {".jpg file is usually an audio file.", "False", "easy"},
            {"Microsoft developed the Windows operating system.", "True", "easy"},
            {"USB stands for United Serial Bus.", "False", "easy"},
            {"Java was originally developed by Microsoft.", "False", "easy"},
            {"Java code is compiled into bytecode that runs on the JVM.", "True", "easy"},
            {"The keyword public in Java makes a class accessible from other classes.", "True", "easy"},
            {"Java is a low-level programming language.", "False", "easy"},
            {"The .java file extension is used for compiled Java code.", "False", "easy"}
        };

        for (String[] q : easyQuestions) {
            pstmt.setString(1, q[0]);
            pstmt.setString(2, q[1]);
            pstmt.setString(3, q[2]);
            pstmt.addBatch();
        }

        pstmt.executeBatch();

        System.out.println("Easy questions inserted successfully.");
    } catch (SQLException e) {
        System.err.println("Failed to insert easy questions: " + e.getMessage());
        e.printStackTrace();
    }
}

//inserting the medium questions
    public static void insertHardQuestions(Connection conn) {
    String sql = "INSERT INTO questions (question, answer, difficulty) VALUES (?, ?, ?)";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        String[][] easyQuestions = {
             {"What does SSD stand for?", "Solid State Drive", "hard"},
            {"What kind of software is Linux?", "Open-source OS", "hard"},
            {"What is the function of RAM in a computer?", "Temporary memory", "hard"},
            {"What type of software is Google Chrome?", "Web browser", "hard"},
            {"What does CAPTCHA help to determine?", "Human or robot user", "hard"},
            {"Where is data stored in cloud computing?", "Remote servers", "hard"},
            {"What does a firewall do?", "Blocks unauthorized access", "hard"},
            {"What does an IP address identify?", "A device on a network", "hard"},
            {"Which company developed ChatGPT?", "OpenAI", "hard"},
            {"What does the \"@\" symbol in emails mean?", "At", "hard"},
            {"Who developed the original version of Java?", "James Gosling", "hard"},
            {"What is the size of an int in Java?", "4 bytes", "hard"},
            {"Which collection class stores elements in insertion order and allows duplicates?", "ArrayList", "hard"},
            {"What does JVM stand for?", "Java Virtual Machine", "hard"},
            {"Which of the following is not a primitive data type in Java?", "String", "hard"}
        };

        for (String[] q : easyQuestions) {
            pstmt.setString(1, q[0]);
            pstmt.setString(2, q[1]);
            pstmt.setString(3, q[2]);
            pstmt.addBatch();
        }

        pstmt.executeBatch();

        System.out.println("Hard questions inserted successfully.");
    } catch (SQLException e) {
        System.err.println("Failed to insert hard questions: " + e.getMessage());
        e.printStackTrace();
    }
}

}

