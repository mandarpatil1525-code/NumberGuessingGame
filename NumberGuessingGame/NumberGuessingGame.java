import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Console Number Guessing Game.
 * Features: random number, higher/lower hints, attempt counter, max attempts,
 * play again, score tracking across rounds, and difficulty levels.
 */
public class NumberGuessingGame {

    enum Difficulty {
        EASY("Easy", 50, 10),
        MEDIUM("Medium", 100, 7),
        HARD("Hard", 200, 5);

        final String label;
        final int max;
        final int maxAttempts;

        Difficulty(String label, int max, int maxAttempts) {
            this.label = label;
            this.max = max;
            this.maxAttempts = maxAttempts;
        }
    }

    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static final List<String> history = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("      NUMBER GUESSING GAME");
        System.out.println("=====================================");

        int round = 0;
        boolean playAgain;

        do {
            round++;
            Difficulty difficulty = chooseDifficulty();
            playRound(round, difficulty);
            printSummary();
            playAgain = askYesNo("\nPlay again? (yes/no): ");
        } while (playAgain);

        System.out.println("\nThanks for playing! Final results:");
        printSummary();
        scanner.close();
    }

    private static Difficulty chooseDifficulty() {
        System.out.println("\nChoose difficulty:");
        System.out.println("  1. Easy   (1-50,  10 attempts)");
        System.out.println("  2. Medium (1-100,  7 attempts)");
        System.out.println("  3. Hard   (1-200,  5 attempts)");

        while (true) {
            System.out.print("Enter 1, 2 or 3: ");
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1": return Difficulty.EASY;
                case "2": return Difficulty.MEDIUM;
                case "3": return Difficulty.HARD;
                default:  System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void playRound(int round, Difficulty d) {
        int secret = random.nextInt(d.max) + 1; // 1..max
        int attempts = 0;
        boolean won = false;

        System.out.printf("%n--- Round %d (%s) ---%n", round, d.label);
        System.out.printf("I'm thinking of a number between 1 and %d. You have %d attempts.%n",
                d.max, d.maxAttempts);

        while (attempts < d.maxAttempts) {
            System.out.printf("Attempt %d/%d - Enter your guess: ", attempts + 1, d.maxAttempts);
            String line = scanner.nextLine().trim();

            int guess;
            try {
                guess = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
                continue; // invalid input doesn't use up an attempt
            }
            if (guess < 1 || guess > d.max) {
                System.out.println("Out of range! Enter a number between 1 and " + d.max + ".");
                continue;
            }

            attempts++;

            if (guess > secret) {
                System.out.println("Too High!");
            } else if (guess < secret) {
                System.out.println("Too Low!");
            } else {
                System.out.println("Correct!");
                won = true;
                break;
            }
        }

        if (won) {
            System.out.printf("You won in %d attempt(s)!%n", attempts);
            history.add(String.format("Round %d - guessed in %d attempts (%s)", round, attempts, d.label));
        } else {
            System.out.println("You Lost! The number was " + secret + ".");
            history.add(String.format("Round %d - lost, number was %d (%s)", round, secret, d.label));
        }
    }

    private static void printSummary() {
        System.out.println("\n========== SCOREBOARD ==========");
        for (String entry : history) {
            System.out.println(entry);
        }
        System.out.println("================================");
    }

    private static boolean askYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim().toLowerCase();
            if (s.equals("yes") || s.equals("y")) return true;
            if (s.equals("no") || s.equals("n")) return false;
            System.out.println("Please type yes or no.");
        }
    }
}
