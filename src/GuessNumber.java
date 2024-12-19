import java.util.*;
import java.util.concurrent.TimeUnit;


public class GuessNumber {
    public void startGame() {
        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("Rules: Guess a number between 1 and 100.");

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Choose your difficulty level:");
        while (true) {
            System.out.println("\nCommands: easy, medium, hard, customize");
            System.out.print("> ");
            String command = scanner.next().toLowerCase(Locale.ROOT);

            switch (command) {
                case "easy" -> playGame(scanner, random, 10, "Easy");
                case "medium" -> playGame(scanner, random, 7, "Medium");
                case "hard" -> playGame(scanner, random, 5, "Hard");
                case "customize" -> playGame(scanner, random,1000000000, "Customize");
                default -> System.out.println("Invalid command. Please choose easy, medium, or hard.");
            }
        }
    }
    private void playGame(Scanner scanner, Random random, int attempts, String difficulty) {
        long start = System.nanoTime();
        if(difficulty.equalsIgnoreCase("customize")) {
            System.out.println("Welcome to mode " + difficulty + " you can play infinite time");
        }
        else {
            System.out.println("You have " + attempts + " chances. Good luck! [" + difficulty + " Mode]");
        }
        int targetNumber = random.nextInt(1, 101); // Random number between 1 and 100

        for (int i = 0; i < attempts; i++) {
            System.out.print("Guess a number: ");
            int userGuess = scanner.nextInt();

            if (userGuess == targetNumber) {
                System.out.println("Congratulations! You guessed it! The number was " + targetNumber);
                long stop = System.currentTimeMillis();
                long totalTimer = stop - start;
                timerConvert(totalTimer);
                return;
            } else if (userGuess < targetNumber) {
                System.out.println("Too low!");
            } else {
                System.out.println("Too high!");
            }

            if (i == attempts - 1) {
                System.out.println("Out of attempts! The correct number was " + targetNumber);
            }
        }
    }
    public void timerConvert(long timer) {
        // This method uses this formula :minutes =
        // (milliseconds / 1000) / 60;

        long minutes
                = TimeUnit.MILLISECONDS.toMinutes(timer);

        // This method uses this formula seconds =
        // (milliseconds / 1000);
        long seconds
                = (TimeUnit.MILLISECONDS.toSeconds(timer)
                % 60);

        // Print the answer
        System.out.format(minutes + " minutes and " + seconds + " seconds");
    }
}
