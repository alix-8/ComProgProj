package Reconnect;

import java.util.Scanner;

public class Transitions {
    

    public static void pause() {
    System.out.print("Press 'Enter' to begin the game.");
        try {
            new java.util.Scanner(System.in).nextLine();
        } catch (Exception ignored) {
    }
}
    public static void phaseOne(){
        Scanner scanner = new Scanner(System.in);

        Clear.clear();
        System.out.println("You finished PHASE 1.");
        QuizFlow.pause();
        Clear.clear();

        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Moving on to PHASE 2...");
        System.out.println("You have to fix the comm system by answering multiple choices questions");
        System.out.println("Just answer A, B, C, or D");
        System.out.println("-----------------------------------------------------------------------");
        
    }   
}
