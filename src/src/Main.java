import java.time.*;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean repeat = false, errorInRepeatChoice = true;
        String inputString = "";
        int inputInt;
        do {
            repeat = false;
            inputString = printMenu();
            inputInt = parseInput(inputString);
            if (inputInt == 0) {
                repeat = true;
            } else if (inputInt < 1 || inputInt > 3) {
                System.out.println(ConsoleColors.ANSI_RED + "Please enter only 1, 2 or 3\n" + ConsoleColors.ANSI_RESET);
                repeat = true;
            } else {
                guessingGame(inputInt);
            }
            while (errorInRepeatChoice) {
                System.out.print("Do you want to play again? 1 --> Yes 2 --> No: ");
                inputString = scanner.nextLine();
                System.out.println();
                inputInt = parseInput(inputString);
                switch (inputInt) {
                    case 1 -> {
                        repeat = true;
                        errorInRepeatChoice = false;
                    }
                    case 2 -> {
                        repeat = false;
                        errorInRepeatChoice = false;
                    }
                    default -> {
                        System.out.println(ConsoleColors.ANSI_RED + "Please only enter 1 to repeat or 2 to not repeat the Game.\n" + ConsoleColors.ANSI_RESET);
                    }
                }
            }
        } while (repeat);
    }

    public static void guessingGame(int input) {
        Random random = new Random();
        int guesses = 0, inputGuess, guessCounter = 0, randomNumber = random.nextInt(100);
        String difficulty = "";
        boolean winCondition;

        switch (input) {
            case 1 -> {
                guesses = 10;
                difficulty = "Easy";
            }
            case 2 -> {
                guesses = 5;
                difficulty = "Medium";
            }
            case 3 -> {
                guesses = 3;
                difficulty = "Hard";
            }
        }

        System.out.printf("Great! You have selected the %s difficulty level.%nLet's start the game%n%n", difficulty);

        // Setting start time for calculating timer
        Instant start = Instant.now();
        while (guessCounter < guesses) {
            System.out.print("Enter you guess: ");
            String inputGuessString = scanner.nextLine();
            int inputGuessInt = parseInput(inputGuessString);
            if (inputGuessInt < 0 || inputGuessInt > 100) {
                System.out.println(ConsoleColors.ANSI_RED + "Please enter numbers between 0 and 100" + ConsoleColors.ANSI_RESET);
            } else {
                guessCounter++;
                winCondition = checkResult(inputGuessInt, randomNumber);
                if (winCondition) {
                    System.out.printf("Congratulations! You guessed the correct number in %d attempts.%n", guessCounter);
                    // Setting end time for calculating timer
                    Instant stop = Instant.now();
                    // Calculating the difference between start and end time
                    Duration timeElapsed = Duration.between(start, stop);
                    System.out.printf("You took %d seconds to guess the number!%n", timeElapsed.toSeconds());
                    break;
                }
            }
        }
    }

    private static boolean checkResult(int inputInt, int randomNumber) {
        if (inputInt == randomNumber) {
            return true;
        } else if (inputInt > randomNumber) {
            System.out.printf("Incorrect! The number is less than %d%n%n", inputInt);
            return false;
        } else {
            System.out.printf("Incorrect! The number is greater than %d%n%n", inputInt);
            return false;
        }
    }

    private static String printMenu() {
        System.out.println("""
                Welcome to the Number Guessing Game!
                I'm thinking of a number between 1 and 100
                Please choose your difficulty:
                
                1. Easy (10 chances)
                2. Medium (5 chances)
                3. Hard (3 chances)\n""");
        System.out.print("Enter your choice: ");
        String inputString = scanner.nextLine();
        System.out.println();
        return inputString;
    }

    private static int parseInput(String input) {
        int inputInt;
        try {
            return inputInt = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println(ConsoleColors.ANSI_RED + "You did not enter a valid input please try again!" + ConsoleColors.ANSI_RESET);
        }
        return 0;
    }
}