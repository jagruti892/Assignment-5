import java.awt.Point; //Stores food coordinates
import java.util.Random; //Generates random food positions

public class Food {
    private final Random random = new Random();
    private Point position;
    //constructor: creates food at a random position
    public Food(int columns, int rows, Snake snake) {
        respawn(columns, rows, snake);
    }
    //returns a copy of the food's position
    public Point getPosition() {
        return new Point(position);
    }

    //places food somewhere the snake is not occupying
    public void respawn(int columns, int rows, Snake snake) {
        Point candidate;

        do {
            candidate = new Point(
                random.nextInt(columns),
                random.nextInt(rows)
            );
        } while (snake.contains(candidate));

        position = candidate;
    }
}