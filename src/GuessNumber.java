import java.util.*;
import java.util.concurrent.TimeUnit;

public class GuessNumber {
    private final List<Player> leaderboard = new ArrayList<>(); // List to store leaderboard data

    public void startGame() {
        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("Rules: Guess a number between 1 and 100.");

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        while (true) {
            System.out.println("\nChoose your difficulty level or view the leaderboard:");
            System.out.println("Commands: easy, medium, hard, customize, leaderboard, exit");
            System.out.print("> ");
            String command = scanner.next().toLowerCase(Locale.ROOT);

            switch (command) {
                case "easy" -> playGame(scanner, random, 10, "Easy");
                case "medium" -> playGame(scanner, random, 7, "Medium");
                case "hard" -> playGame(scanner, random, 5, "Hard");
                case "customize" -> playGame(scanner, random, Integer.MAX_VALUE, "Customize");
                case "leaderboard" -> displayLeaderboard();
                case "exit" -> {
                    System.out.println("Thanks for playing! Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid command. Please try again.");
            }
        }
    }

    private void playGame(Scanner scanner, Random random, int attempts, String difficulty) {
        long start = System.currentTimeMillis();
        if (difficulty.equalsIgnoreCase("customize")) {
            System.out.println("Welcome to mode " + difficulty + ". You can play infinitely.");
        } else {
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

                System.out.print("\nEnter your name for the leaderboard: ");
                String name = scanner.next();
                leaderboard.add(new Player(name, difficulty, i + 1, totalTimer));
                leaderboard.sort(Comparator.comparingLong(Player::getTime).thenComparingInt(Player::getAttempts));
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
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timer);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timer) % 60;
        System.out.format(minutes + " minutes and " + seconds + " seconds\n");
    }

    private void displayLeaderboard() {
        if (leaderboard.isEmpty()) {
            System.out.println("No scores yet! Play a game to add your name.");
        } else {
            System.out.println("\n=== Leaderboard ===");
            for (int i = 0; i < leaderboard.size(); i++) {
                Player player = leaderboard.get(i);
                System.out.printf("%d. %s - %s Mode - Attempts: %d, Time: %d seconds%n",
                        i + 1, player.getName(), player.getDifficulty(), player.getAttempts(), player.getTime() / 1000);
            }
        }
    }

    private static class Player {
        private final String name;
        private final String difficulty;
        private final int attempts;
        private final long time;

        public Player(String name, String difficulty, int attempts, long time) {
            this.name = name;
            this.difficulty = difficulty;
            this.attempts = attempts;
            this.time = time;
        }

        public String getName() {
            return name;
        }

        public String getDifficulty() {
            return difficulty;
        }

        public int getAttempts() {
            return attempts;
        }

        public long getTime() {
            return time;
        }
    }
}
