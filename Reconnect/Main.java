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
            QuizFlow.displayHardQuestions(1);
            
            Clear.clear();
            System.out.println("Continue playing?\nYes = click 'y'\nNo = click any key");
            System.out.print("Your answer: ");
            String input = scanner.nextLine();
                if (!input.equalsIgnoreCase("y")) {
                    gameEnd = true;
                    Clear.clear();  

                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("Thanks for playing!");
                    System.out.println("-------------------------");
                    System.out.println("1: Go back to main menu");
                    System.out.println("2: Close the game");
                    System.out.print("\nYour choice: ");
                    int input1 = scanner.nextInt();

                    switch (input1) {
                        case 1:
                            Clear.clear();
                            //call here main menu    
                            break;
                        case 2:
                            Clear.clear();
                            gameEnd = true;    
                            break;
                        default:
                            break;
                    }
                    
                }else{
                    gameEnd = false;
                }
            }
            
        } catch (Exception e) {
            System.out.println("Something's wrong :(");
            e.printStackTrace();
        }
    }
}    
