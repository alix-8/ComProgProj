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

    //MAIN
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        try {
            // calling the methods
            clear();
            // displayQuestion("easy", 1);
            displayHardQuestions(1);

        } catch (Exception e) {
            System.out.println("Something's wrong:(");
            e.printStackTrace();
        }
    }


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

    public static void pause() {
    System.out.print("Press 'Enter' to continue.");
    try {
        new java.util.Scanner(System.in).nextLine();
    } catch (Exception ignored) {
    }
}


    //method used for right
    public static void updateSystemsFixed(Connection conn, int userId) throws SQLException {
        String update = "INSERT INTO game_progress (user_id, systems_fixed) VALUES (?, 1) " +
                        "ON DUPLICATE KEY UPDATE systems_fixed = systems_fixed + 1";
        PreparedStatement stmt = conn.prepareStatement(update);
        stmt.setInt(1, userId);
        stmt.executeUpdate();
    }

    //method used for wrong answers
    public static void reduceOxygen(Connection conn, int userId) throws SQLException {
        String update = "INSERT INTO game_progress (user_id, oxygen_remaining) VALUES (?, 80) " +
                        "ON DUPLICATE KEY UPDATE oxygen_remaining = oxygen_remaining - 20";
        PreparedStatement stmt = conn.prepareStatement(update);
        stmt.setInt(1, userId);
        stmt.executeUpdate();
    }

    //show status every after question
    public static void showStatus(Connection conn, int userId) throws SQLException {
        String query = "SELECT oxygen_remaining, systems_fixed FROM game_progress WHERE user_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int oxygen = rs.getInt("oxygen_remaining");
            int fixed = rs.getInt("systems_fixed");
            System.out.println("\n== Player Status ==");
            System.out.println("Oxygen Remaining: " + oxygen);
            System.out.println("Systems Fixed: " + fixed);
            System.out.println("====================");
        }
    }



    public static void displayHardQuestions(int userId) {
        String URL = "jdbc:mysql://db-mysql-sgp1-information-management-do-user-9437339-0.l.db.ondigitalocean.com:25060/reconnect?useSSL=true&requireSSL=true";
        String USER = "reconnect_user";
        String PASSWORD = "AVNS_DB3f2_oo-klHmzd1Nxk";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

            String questionQuery = "SELECT id, question FROM questions WHERE difficulty = 'hard' ORDER BY RAND()";
            PreparedStatement questionStmt = conn.prepareStatement(questionQuery);
            ResultSet questionRs = questionStmt.executeQuery();

            resetGame(conn, userId);

            Scanner scanner = new Scanner(System.in);
            int validQuestionCount = 0;
            
            while (questionRs.next() && validQuestionCount < 5) {
                int questionId = questionRs.getInt("id");
                String questionText = questionRs.getString("question");

                while(true){
                    System.out.println("\n" + questionText);

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
                        if (isCorrect) correctLetter = letter;

                        System.out.println(letter + ". " + choiceText);
                        option++;
                    }

                    System.out.print("Your answer (A-D): ");
                    String userAnswer = scanner.nextLine().trim().toUpperCase();

                    boolean isCorrect = isCorrectMap.getOrDefault(userAnswer, false);

                    // Record the answer
                    String insertQuery = """
                            INSERT INTO player_answers (user_id, question_id, selected_answer, is_correct)
                            VALUES (?, ?, ?, ?)
                            ON DUPLICATE KEY UPDATE
                            selected_answer = VALUES(selected_answer),
                            is_correct = VALUES(is_correct)"""; 
                    PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                    insertStmt.setInt(1, userId);
                    insertStmt.setInt(2, questionId);
                    insertStmt.setString(3, userAnswer);
                    insertStmt.setBoolean(4, isCorrect);
                    insertStmt.executeUpdate();

                if (userAnswer.equalsIgnoreCase("a") || userAnswer.equalsIgnoreCase("b") || userAnswer.equalsIgnoreCase("c") || userAnswer.equalsIgnoreCase("d")){
                    if (isCorrect) {
                        clear();
                        System.out.println("Correct!");
                        updateSystemsFixed(conn, userId);
                    } else {
                        clear();
                        System.out.println("Oops, Incorrect!\nThe correct answer was: " + correctLetter);
                        reduceOxygen(conn, userId);
                    }
                    
                    validQuestionCount++;
                    break;

                } else {
                    clear();
                    System.out.println("Wrong input. Enter letters A-D only.");
                    
                }
              
                }
                  

                showStatus(conn, userId);
            }

            conn.close();

        } catch (Exception e) {
            System.out.println("Something's wrong in displayQuestions() :(");
            e.printStackTrace();
        }
    }

    
    public static void resetGame(Connection conn, int userId) {
        try{
            // Clear old answers
            String deleteAnswers = "DELETE FROM player_answers WHERE user_id = ?";
            PreparedStatement delAnsStmt = conn.prepareStatement(deleteAnswers);
            delAnsStmt.setInt(1, userId);
            delAnsStmt.executeUpdate();

            // Clear old progress
            String deleteProgress = "DELETE FROM game_progress WHERE user_id = ?";
            PreparedStatement delProgStmt = conn.prepareStatement(deleteProgress);
            delProgStmt.setInt(1, userId);
            delProgStmt.executeUpdate();
        }catch(Exception e) {
            System.out.println("Something's wrong in resestGame() :(");
            e.printStackTrace();
        }
            
        }

    // display game summary/review
    public static void review(){
            
    }


}

    
