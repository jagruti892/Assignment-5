import java.awt.Point; //Stores snake body coordinates
import java.util.ArrayList; //Stores snake body segments

public class Snake {

    //stores the snake's body starting with its head
    private final ArrayList<Point> body = new ArrayList<>();

    //Initial direction: right
    private int dx = 1;
    private int dy = 0;

    // Constructor: creates a snake with three segments.
    public Snake(int startX, int startY) {
        body.add(new Point(startX, startY));
        body.add(new Point(startX - 1, startY));
        body.add(new Point(startX - 2, startY));
    }

    //returns a copy of the snake's head
    public Point getHead() {
        return new Point(body.get(0));
    }

    //returns a copy of the snake's body
    public ArrayList<Point> getBody() {
        return new ArrayList<>(body);
    }

    //changes direction without allowing a direct reversal
    public void changeDirection(int newDx, int newDy) {
        if (newDx == -dx && newDy == -dy) {
            return;
        }

        dx = newDx;
        dy = newDy;
    }

    //calculates where the head will move next
    public Point getNextHead() {
        Point head = body.get(0);
        return new Point(head.x + dx, head.y + dy);
    }

    //moves the snake and grows it when food is eaten.
    public void move(boolean grow) {
        Point newHead = getNextHead();
        body.add(0, newHead);

        if (!grow) {
            body.remove(body.size() - 1);
        }
    }
    //checks whether a point is occupied by the snake
    public boolean contains(Point point) {
        return body.contains(point);
    }
    //checks whether the head touches the snake's body
    public boolean hitsItself() {
        Point head = body.get(0);

        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }
        return false;
    }
}