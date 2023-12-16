import java.util.Random;
import java.util.Scanner;

public class task_1_number_game {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int minRange = 1;
        int maxRange = 100;
        int maxAttempts = 7;
        int rounds = 0;
        int totalAttempts = 0;

        System.out.println("Welcome to the Number Game!");

        do {
            int generatedNumber = random.nextInt(maxRange - minRange + 1) + minRange;
            int userGuess;
            int attempts = 0;

            System.out.println("\nRound " + (rounds + 1) + ": Guess the number between " + minRange + " and " + maxRange);

            do {
                System.out.print("Enter your guess: ");
                userGuess = scanner.nextInt();
                attempts++;

                if (userGuess == generatedNumber) {
                    System.out.println("Congratulations! You guessed the correct number in " + attempts + " attempts.");
                } else if (userGuess < generatedNumber) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Too high! Try again.");
                }

            } while (userGuess != generatedNumber && attempts < maxAttempts);

            totalAttempts += attempts;
            rounds++;

            System.out.print("Do you want to play again? (yes/no): ");
        } while (scanner.next().equalsIgnoreCase("yes"));

        System.out.println("\nGame over! You played " + rounds + " rounds with an average of " +
                (double) totalAttempts / rounds + " attempts per round.");

        scanner.close();
    }
}