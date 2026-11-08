import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Swing GUI Number Guessing Game with difficulty levels, attempt counter,
 * play again and round-by-round score history.
 */
public class NumberGuessingGameGUI extends JFrame {

    private enum Difficulty {
        EASY("Easy (1-50, 10 attempts)", 50, 10),
        MEDIUM("Medium (1-100, 7 attempts)", 100, 7),
        HARD("Hard (1-200, 5 attempts)", 200, 5);

        final String label; final int max; final int attempts;
        Difficulty(String l, int m, int a) { label = l; max = m; attempts = a; }
        @Override public String toString() { return label; }
    }

    private final Random random = new Random();
    private final JComboBox<Difficulty> difficultyBox = new JComboBox<>(Difficulty.values());
    private final JLabel promptLabel = new JLabel(" ", SwingConstants.CENTER);
    private final JLabel attemptLabel = new JLabel(" ", SwingConstants.CENTER);
    private final JLabel feedbackLabel = new JLabel("Press Start to play!", SwingConstants.CENTER);
    private final JTextField guessField = new JTextField(8);
    private final JButton guessButton = new JButton("Guess");
    private final JButton newGameButton = new JButton("Start / Play Again");
    private final JTextArea historyArea = new JTextArea(8, 30);

    private int secret, attemptsUsed, maxAttempts, maxNumber, round = 0;
    private boolean roundActive = false;

    public NumberGuessingGameGUI() {
        super("Number Guessing Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Top: difficulty + start
        JPanel top = new JPanel(new FlowLayout());
        top.add(new JLabel("Difficulty:"));
        top.add(difficultyBox);
        top.add(newGameButton);
        add(top, BorderLayout.NORTH);

        // Center: prompts, input, feedback
        JPanel center = new JPanel(new GridLayout(5, 1, 5, 5));
        feedbackLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        promptLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        attemptLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        JPanel input = new JPanel(new FlowLayout());
        input.add(new JLabel("Your guess:"));
        input.add(guessField);
        input.add(guessButton);
        center.add(promptLabel);
        center.add(attemptLabel);
        center.add(input);
        center.add(feedbackLabel);
        add(center, BorderLayout.CENTER);

        // Bottom: scoreboard
        historyArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(historyArea);
        scroll.setBorder(BorderFactory.createTitledBorder("Scoreboard"));
        add(scroll, BorderLayout.SOUTH);

        guessButton.addActionListener(e -> handleGuess());
        guessField.addActionListener(e -> handleGuess()); // Enter key
        newGameButton.addActionListener(e -> startRound());

        setInputEnabled(false);
        pack();
        setLocationRelativeTo(null);
    }

    private void startRound() {
        Difficulty d = (Difficulty) difficultyBox.getSelectedItem();
        maxNumber = d.max;
        maxAttempts = d.attempts;
        secret = random.nextInt(maxNumber) + 1;
        attemptsUsed = 0;
        round++;
        roundActive = true;

        promptLabel.setText("Guess a number between 1 and " + maxNumber);
        updateAttempts();
        feedbackLabel.setText("Good luck!");
        feedbackLabel.setForeground(Color.DARK_GRAY);
        difficultyBox.setEnabled(false);
        setInputEnabled(true);
        guessField.setText("");
        guessField.requestFocusInWindow();
    }

    private void handleGuess() {
        if (!roundActive) return;
        int guess;
        try {
            guess = Integer.parseInt(guessField.getText().trim());
        } catch (NumberFormatException ex) {
            feedbackLabel.setText("Enter a whole number!");
            feedbackLabel.setForeground(Color.ORANGE.darker());
            return;
        }
        if (guess < 1 || guess > maxNumber) {
            feedbackLabel.setText("Range is 1-" + maxNumber);
            feedbackLabel.setForeground(Color.ORANGE.darker());
            return;
        }

        attemptsUsed++;
        updateAttempts();
        guessField.setText("");

        if (guess == secret) {
            feedbackLabel.setText("Correct!");
            feedbackLabel.setForeground(new Color(0, 140, 0));
            historyArea.append("Round " + round + " — guessed in " + attemptsUsed + " attempts\n");
            endRound();
        } else if (attemptsUsed >= maxAttempts) {
            feedbackLabel.setText("You Lost! Number was " + secret);
            feedbackLabel.setForeground(Color.RED);
            historyArea.append("Round " + round + " — lost (number was " + secret + ")\n");
            endRound();
        } else if (guess > secret) {
            feedbackLabel.setText("Too High!");
            feedbackLabel.setForeground(Color.RED.darker());
        } else {
            feedbackLabel.setText("Too Low!");
            feedbackLabel.setForeground(Color.BLUE);
        }
        guessField.requestFocusInWindow();
    }

    private void endRound() {
        roundActive = false;
        setInputEnabled(false);
        difficultyBox.setEnabled(true);
        newGameButton.setText("Play Again");
    }

    private void updateAttempts() {
        attemptLabel.setText("Attempts: " + attemptsUsed + " / " + maxAttempts);
    }

    private void setInputEnabled(boolean on) {
        guessField.setEnabled(on);
        guessButton.setEnabled(on);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NumberGuessingGameGUI().setVisible(true));
    }
}
