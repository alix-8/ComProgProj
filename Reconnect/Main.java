package Reconnect;

import java.util.Scanner;

public class Main{

    //MAIN
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        boolean gameEnd = false;
        try {
            while (!gameEnd) {
            Clear.clear();
            Questions.displayEasyQuestions(1);

            boolean validChoice = false;
                while (!validChoice) {
                    Transitions.phaseOne();

                System.out.println("Do you want to continue the game?");
                System.out.println("1: Yes");
                System.out.println("2: Back to main menu");
                System.out.println("3: Retry");

                System.out.print("Your choice:");
                int input = scanner.nextInt();

                    switch (input) {
                        case 1:
                            System.out.println("Loading...");
                            Clear.clear();
                            QuizFlow.displayHardQuestions(1);
                            validChoice = true;
                            break;
                        case 2:
                            //call main menu here.
                            validChoice = true;
                            break;
                        case 3:
                            System.out.println("Restarting the game.");
                            QuizFlow.resetGame(1);
                            validChoice = true;
                            break;
                        default:
                            System.out.print("Answer 1, 2, or 3 only.");
                            QuizFlow.pause();
                            Clear.clear();
                            Transitions.phaseOne();
                            break;
                    }
            }
            
                Clear.clear();
            
            
            
            }
            
        } catch (Exception e) {
            System.out.println("Something's wrong :(");
            e.printStackTrace();
        }
    }
}    
