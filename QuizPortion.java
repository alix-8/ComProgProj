import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class QuizPortion{
//function for clearing the screen (galing sa internet)
    public static void clear() {
        try {
            String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    //function para di mag-loop ng mag-loop yung error (para sa input validation)
    public static void pause() {
        System.out.print("Press 'Enter' to continue.");
        try {
            System.in.read();
        } catch (IOException ignored) {
        }
    }

    //MAIN
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        try {
            // calling the methods
            clear();
            System.out.print("Nagana 'to, yey!");
            // displayQuestion("easy", 1);
            displayQuestion("hard", 1);

        } catch (Exception e) {
            System.out.println("Something's wrong:(");
            e.printStackTrace();
        }
    }


    // show user status every quiz item
    public static void showStatus(){

    }

    // display 5 questions every difficulty
public static void displayQuestion(String difficulty, int userId) {
    String URL = "jdbc:mysql://db-mysql-sgp1-information-management-do-user-9437339-0.l.db.ondigitalocean.com:25060/reconnect?useSSL=true&requireSSL=true";
    String USER = "reconnect_user";
    String PASSWORD = "AVNS_DB3f2_oo-klHmzd1Nxk";

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

        String questionQuery = "SELECT id, question FROM questions WHERE difficulty = ? ORDER BY RAND() LIMIT 5";
        PreparedStatement questionStmt = conn.prepareStatement(questionQuery);
        questionStmt.setString(1, difficulty);
        ResultSet questionRs = questionStmt.executeQuery();

        String deleteQuery = "DELETE FROM player_answers WHERE user_id = ?";
        PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
        deleteStmt.setInt(1, userId);
        deleteStmt.executeUpdate();

        Scanner scanner = new Scanner(System.in);

        while (questionRs.next()) {
            int questionId = questionRs.getInt("id");
            String questionText = questionRs.getString("question");

            System.out.println("\n" + questionText);

            // Get choices including correctness
            String choiceQuery = "SELECT choice_text, is_correct FROM choices WHERE question_id = ? ORDER BY RAND()";
            PreparedStatement choiceStmt = conn.prepareStatement(choiceQuery);
            choiceStmt.setInt(1, questionId);
            ResultSet choiceRs = choiceStmt.executeQuery();

            Map<String, String> choiceTextMap = new LinkedHashMap<>();
            Map<String, Boolean> isCorrectMap = new LinkedHashMap<>();
            char option = 'A';
            String correctLetter = "";

            while (choiceRs.next() && option <= 'D') {
                String choiceText = choiceRs.getString("choice_text");
                boolean isCorrect = choiceRs.getBoolean("is_correct");
                String letter = String.valueOf(option);
                choiceTextMap.put(letter, choiceText);
                isCorrectMap.put(letter, isCorrect);
                if (isCorrect) {
                    correctLetter = letter;
                }
                System.out.println(letter + ". " + choiceText);
                option++;
            }

            System.out.print("Your answer (A-D): ");
            String userAnswer = scanner.nextLine().trim().toUpperCase();

            // Evaluate and record answer
            boolean isCorrect = isCorrectMap.getOrDefault(userAnswer, false);

            String insertQuery = "INSERT INTO player_answers (user_id, question_id, selected_answer, is_correct) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setInt(1, userId);
            insertStmt.setInt(2, questionId);
            insertStmt.setString(3, userAnswer);
            insertStmt.setBoolean(4, isCorrect);
            insertStmt.executeUpdate();

            if (isCorrect) {
                System.out.println("✅ Correct!");
            } else {
                System.out.println("❌ Incorrect! Correct answer was: " + correctLetter);
            }
        }

        conn.close();

    } catch (Exception e) {
        System.out.println("Something's wrong on displayQuestion() :(");
        e.printStackTrace();
    }
}





//     public static void checkAnswer(Connection conn, int userId, int questionId, String userAnswer) throws SQLException {
//     String getChoicesQuery = "SELECT choice_text, is_correct FROM choices WHERE question_id = ?";
//     PreparedStatement ps = conn.prepareStatement(getChoicesQuery);
//     ps.setInt(1, questionId);
//     ResultSet rs = ps.executeQuery();

//     Map<String, Boolean> choicesMap = new LinkedHashMap<>();
//     char option = 'A';
//     String correctAnswer = "";

//     while (rs.next()) {
//         String choiceText = rs.getString("choice_text");
//         boolean isCorrect = rs.getBoolean("is_correct");
//         String optionLetter = String.valueOf(option);
//         choicesMap.put(optionLetter, isCorrect);
//         if (isCorrect) {
//             correctAnswer = optionLetter;
//         }
//         option++;
//     }

//     boolean isCorrect = choicesMap.getOrDefault(userAnswer, false);

//     // Insert result into player_answers table
//     String insertQuery = "INSERT INTO player_answers (user_id, question_id, selected_answer, is_correct) VALUES (?, ?, ?, ?)";
//     PreparedStatement insertPs = conn.prepareStatement(insertQuery);
//     insertPs.setInt(1, userId);
//     insertPs.setInt(2, questionId);
//     insertPs.setString(3, userAnswer);
//     insertPs.setBoolean(4, isCorrect);
//     insertPs.executeUpdate();

//     // Feedback
//     if (isCorrect) {
//         System.out.println("✅ Correct!");
//     } else {
//         System.out.println("❌ Incorrect! Correct answer was: " + correctAnswer);
//     }
// }


    // insert game progress
    public static void insertGameProgress(){

    }


    // display game summary/review
    public static void review(){
        
    }


}

    
