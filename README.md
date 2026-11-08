# Number Guessing Game (Java)

A number guessing game built in Java. The computer picks a random number, and the player guesses it with "Too High!" or "Too Low!" hints until they win or run out of attempts.

Two versions are included:
- **Console version** (`NumberGuessingGame.java`): uses `Scanner` for input.
- **GUI version** (`NumberGuessingGameGUI.java`): built with Java Swing.

## Features
- Random number generated at the start of each round
- "Too High!", "Too Low!" and "Correct!" feedback
- Visible attempt counter
- Maximum attempts limit, with a "You Lost!" message that reveals the number
- Play Again option after each round
- Scoreboard across rounds, for example "Round 1 - guessed in 5 attempts"
- Difficulty levels:
  - Easy: 1-50, 10 attempts
  - Medium: 1-100, 7 attempts
  - Hard: 1-200, 5 attempts

## Tech Stack
- Java 17+
- `java.util.Random`, `Scanner`, Swing

## How to Run
1. Install JDK 17 or newer.
2. Clone the repo and open the folder in VS Code (with the Extension Pack for Java).
3. Click **Run** above `main`, or use the terminal:

    javac *.java
    java NumberGuessingGame        # console version
    java NumberGuessingGameGUI     # GUI version

## Author
Your Name
