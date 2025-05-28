package Reconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Questions {
public static void displayEasyQuestions(int userId) {
    String URL = "jdbc:mysql://db-mysql-sgp1-information-management-do-user-9437339-0.l.db.ondigitalocean.com:25060/reconnect?useSSL=true&requireSSL=true";
    String USER = "reconnect_user";
    String PASSWORD = "AVNS_DB3f2_oo-klHmzd1Nxk";


    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

        String questionQuery = "SELECT id, question FROM questions WHERE difficulty = 'easy' ORDER BY RAND() LIMIT 5";
        PreparedStatement questionStmt = conn.prepareStatement(questionQuery);
        ResultSet questionRs = questionStmt.executeQuery();


        QuizFlow.resetGame(userId);

        Scanner scanner = new Scanner(System.in);
        int validQuestionCount = 0;

        while (questionRs.next() && validQuestionCount < 5) {
            int questionId = questionRs.getInt("id");
            String questionText = questionRs.getString("question");

            while (true) {
                System.out.println("\n" + questionText);
                System.out.print("True or False: ");
                String userAnswer = scanner.nextLine().trim().toLowerCase();


                // Get correct answer from database
                String answerQuery = "SELECT answer FROM questions WHERE id = ?";
                PreparedStatement answerStmt = conn.prepareStatement(answerQuery);
                answerStmt.setInt(1, questionId);
                ResultSet answerRs = answerStmt.executeQuery();
                answerRs.next();
                String correctAnswer = answerRs.getString("answer").trim().toLowerCase();

                boolean isCorrect = userAnswer.equals(correctAnswer);

                // Insert or update answer
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

                 if (userAnswer.equalsIgnoreCase("true") || userAnswer.equalsIgnoreCase("false")) {
                    if (isCorrect) {
                        Clear.clear();
                        System.out.println("-----------------");
                        System.out.println("Correct!");
                        System.out.println("+1 System Fixed");
                        System.out.println("-----------------");
                        QuizFlow.updateSystemsFixed(conn, userId);
                        QuizFlow.pause();
                        Clear.clear();
                    } else {
                        Clear.clear();
                        System.out.println("---------------------------------------------------");
                        System.out.println("Oops, Incorrect!\nThe correct answer was: " + correctAnswer);
                        System.out.println("-20% Oxygen");
                        System.out.println("---------------------------------------------------");
                        QuizFlow.reduceOxygen(conn, userId);
                        QuizFlow.pause();
                        Clear.clear();
                    }

                    QuizFlow.showStatus(conn, userId);
                    validQuestionCount++;
                    break;
                }else{
                    System.out.println("Invalid input. Please type 'True' or 'False'.");
                    QuizFlow.pause();
                    Clear.clear();
                    continue;
                }
            }
        }

        conn.close();

    } catch (Exception e) {
        System.out.println("Something went wrong in displayEasyQuestions()");
        e.printStackTrace();
    }
}

}
