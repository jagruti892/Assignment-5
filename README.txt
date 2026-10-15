ASSIGNMENT 5 - SNAKE GAME
Java Swing GUI Game using OOP

FILES
- Main.java: starts the application window.
- GamePanel.java: draws the game, reads keyboard input, and updates the score.
- Snake.java: stores and moves the snake body.
- Food.java: creates food at random empty cells.

HOW TO RUN
1. Install a JDK (Java Development Kit).
2. Open a terminal/command prompt in the code folder.
3. Compile: javac *.java
4. Run: java Main

CONTROLS
- Arrow keys: move the snake.
- Space: restart after Game Over.

OOP CONCEPTS
- Classes and objects: Main, GamePanel, Snake, Food.
- Encapsulation: private fields and public methods.
- Constructors: initialize objects.
- Inheritance: GamePanel extends JPanel.
- Interfaces: GamePanel implements ActionListener.
- Polymorphism / overriding: paintComponent() and actionPerformed().
- Collections: ArrayList stores snake body segments.
- Event handling: Swing key bindings and Timer.

