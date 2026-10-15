import java.awt.Color; //sets game colors
import java.awt.Dimension; // sets panel size
import java.awt.Font;//sets text font and size
import java.awt.Graphics;//draws game objects
import java.awt.Point;//stores grid coordinates
import java.awt.event.ActionEvent;//reprsents action events
import java.awt.event.ActionListener;//handles timer events

import javax.swing.AbstractAction;//defines keyboard actions
import javax.swing.ActionMap;//maps names to actions
import javax.swing.InputMap;// maps keys to actions
import javax.swing.JPanel;//creates the game panel
import javax.swing.KeyStroke; //represents keyboard keys
import javax.swing.Timer; //Controls game timing

//inheritance: GamePanel extends JPanel to display the game
//interface: ActionListener handles timer events
public class GamePanel extends JPanel implements ActionListener {

    //FEATURE: Constants define size and speed of the game
    private static final int CELL_SIZE=20;
    private static final int COLUMNS=30;
    private static final int ROWS=25;
    private static final int PANEL_WIDTH=CELL_SIZE * COLUMNS;
    private static final int PANEL_HEIGHT=CELL_SIZE * ROWS;
    private static final int DELAY = 120; // Timer delay in milliseconds

    // Encapsulation: Private variables store game data
    private Snake snake;
    private Food food;
    private Timer timer;
    private int score;
    private boolean running;
    private boolean gameOver;

    // Constructor: Initializes the game panel
    public GamePanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(new Color(25, 30, 35)); // Sets background color
        setFocusable(true);

        setupKeyBindings(); // Sets keyboard controls
        startNewGame();    // Starts a new game
    }

    // FEATURE: Maps keyboard keys to game actions
    private void setupKeyBindings() {
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        // Connects each key to an action name
        inputMap.put(KeyStroke.getKeyStroke("UP"), "up");
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "down");
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "left");
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "restart");

        // Moves the snake upward
        actionMap.put("up", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (running) snake.changeDirection(0, -1);
            }
        });

        // Moves the snake downward
        actionMap.put("down", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (running) snake.changeDirection(0, 1);
            }
        });

        // Moves the snake left
        actionMap.put("left", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (running) snake.changeDirection(-1, 0);
            }
        });

        // Moves the snake right
        actionMap.put("right", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (running) snake.changeDirection(1, 0);
            }
        });

        // Restarts the game after Game Over
        actionMap.put("restart", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (gameOver) startNewGame();
            }
        });
    }

    // FEATURE: Initializes or restarts the game
    private void startNewGame() {
        if (timer != null) {
            timer.stop(); // Stops the previous timer
        }

        // Creates new Snake and Food objects
        snake = new Snake(COLUMNS / 2, ROWS / 2);
        food = new Food(COLUMNS, ROWS, snake);

        score = 0;
        running = true;
        gameOver = false;

        timer = new Timer(DELAY, this); // Updates the game regularly
        timer.start();

        repaint(); // Redraws the panel
        requestFocusInWindow();
    }

    // Called automatically whenever the timer fires
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!running) {
            return; // Stops updates if the game is not running
        }

        // Gets the snake's next position and the food position
        Point nextHead = snake.getNextHead();
        Point foodPosition = food.getPosition();
        boolean willEat = nextHead.equals(foodPosition);

        // Moves the snake and grows it if food will be eaten
        snake.move(willEat);
        Point head = snake.getHead();

        // Checks for wall or self-collision
        if (head.x < 0 || head.x >= COLUMNS
                || head.y < 0 || head.y >= ROWS
                || snake.hitsItself()) {
            endGame();
            return;
        }

        // Increases score and creates new food after eating
        if (willEat) {
            score += 10;
            food.respawn(COLUMNS, ROWS, snake);
        }

        repaint(); // Refreshes the game display
    }

    // Ends the game when a collision occurs
    private void endGame() {
        running = false;
        gameOver = true;
        timer.stop();
        repaint();
    }

    // FEATURE: Method overriding to draw the game
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGrid(g);
        drawFood(g);
        drawSnake(g);
        drawScore(g);

        if (gameOver) {
            drawGameOver(g);
        }
    }

    // Draws the grid lines
    private void drawGrid(Graphics g) {
        g.setColor(new Color(42, 48, 54));

        for (int x = 0; x < PANEL_WIDTH; x += CELL_SIZE) {
            g.drawLine(x, 0, x, PANEL_HEIGHT);
        }

        for (int y = 0; y < PANEL_HEIGHT; y += CELL_SIZE) {
            g.drawLine(0, y, PANEL_WIDTH, y);
        }
    }

    // Draws each segment of the snake
    private void drawSnake(Graphics g) {
        java.util.ArrayList<Point> body = snake.getBody();

        for (int i = 0; i < body.size(); i++) {
            Point part = body.get(i);

            // Gives the head a different color from the body
            g.setColor(i == 0
                    ? new Color(160, 255, 90)
                    : new Color(70, 190, 85));

            g.fillRoundRect(
                    part.x * CELL_SIZE + 1,
                    part.y * CELL_SIZE + 1,
                    CELL_SIZE - 2,
                    CELL_SIZE - 2,
                    6, 6
            );
        }
    }

    // Draws the food as a red circle
    private void drawFood(Graphics g) {
        Point p = food.getPosition();
        g.setColor(new Color(255, 80, 80));

        g.fillOval(
                p.x * CELL_SIZE + 2,
                p.y * CELL_SIZE + 2,
                CELL_SIZE - 4,
                CELL_SIZE - 4
        );
    }

    // Displays the current score
    private void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 18));
        g.drawString("Score: " + score, 12, 24);
    }

    // Displays the Game Over message and restart instructions
    private void drawGameOver(Graphics g) {
        // Draws a transparent dark overlay.
        g.setColor(new Color(0, 0, 0, 175));
        g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

        // Displays the Game Over title in the center
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 36));

        String title = "GAME OVER";
        int titleWidth = g.getFontMetrics().stringWidth(title);

        g.drawString(
                title,
                (PANEL_WIDTH - titleWidth) / 2,
                PANEL_HEIGHT / 2 - 20
        );

        // Displays the final score
        g.setFont(new Font("SansSerif", Font.PLAIN, 20));

        String scoreText = "Final score: " + score;
        int scoreWidth = g.getFontMetrics().stringWidth(scoreText);

        g.drawString(
                scoreText,
                (PANEL_WIDTH - scoreWidth) / 2,
                PANEL_HEIGHT / 2 + 15
        );

        // Tells the player how to restart
        String restartText = "Press SPACE to play again";
        int restartWidth = g.getFontMetrics().stringWidth(restartText);

        g.drawString(
                restartText,
                (PANEL_WIDTH - restartWidth) / 2,
                PANEL_HEIGHT / 2 + 50
        );
    }
}