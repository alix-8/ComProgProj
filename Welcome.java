import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Welcome {

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

    //function para ma-display ang main menu
    public static void mainMenu(){
        clear();
        System.out.println("\t\tWelcome to");
        System.out.print(
        " ____                                      _   \n" +
        "|  _ \\ ___  ___ ___  _ __  _ __   ___  ___| |_ \n" +
        "| |_) / _ \\/ __/ _ \\| '_ \\| '_ \\ / _ \\/ __| __|\n" +
        "|  _ <  __/ (_| (_) | | | | | | |  __/ (__| |_ \n" +
        "|_| \\_\\___|\\___\\___/|_| |_|_| |_|\\___|\\___|\\__|\n"
        );
    }

    //MAIN
    public static void main (String[] args){

        Scanner scanner = new Scanner(System.in);
        Boolean isExit = false;

        while (!isExit){
            clear();
            mainMenu();
            System.out.println("====================================");
            System.out.println("1: Log in");
            System.out.println("2: Sign up");
            System.out.println("3: Leaderboard");
            System.out.println("4: Exit");
            System.out.println("====================================");
            try{
                System.out.print("Your input here: ");
                int input = scanner.nextInt();

                
                switch(input){
                    case 1:
                        clear();
                        logIn();
                        pause();
                        break;
                    case 2:
                        clear();
                        signUp();
                        pause();
                        break;
                    case 3:
                        clear();
                        leaderboard();
                        pause();
                        break;
                    case 4:
                        clear();
                        isExit = true;
                    
                        break;
                    default:
                        System.out.println("Invalid input! Please enter a number between 1 and 4. (change color)");
                }
            }catch (InputMismatchException e) {
                //change color ng errors
                System.out.println("Invalid input! Please enter a number. (change color)");
                scanner.nextLine(); // consume invalid input
                pause();
                break;
            }


        }      
    
    }

    //Log in function (username and password (forget password))
    public static void logIn() {
        System.out.print("The log in function is not made yet.");
      
    }

    //Sign up function (username and password)
    public static void signUp() {
        System.out.print("The sign up function is not made yet.");
      
    }

    //leaderboard function (may 'open leaderboard' at 'reset leaderboard' na choices)
     public static void leaderboard() {
        System.out.print("The leader board function is not made yet.");
      
    }

}
