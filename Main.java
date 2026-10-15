import javax.swing.JFrame; //main application window in which the game appears
import javax.swing.SwingUtilities;// Starts GUI on Swing's Event Dispatch Thread

public class Main {
    public static void main(String[] args) {
        //runs GUI code on Swing's event thread
        SwingUtilities.invokeLater(() -> {
            //creates the main window with a title
            JFrame frame = new JFrame("Snake Game");
            //closes the application when the window is closed
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);//prevents the user from resizing the window
            //adds the game panel to the window
            frame.add(new GamePanel());

            //adjusts the window to fit the game panel
            frame.pack();
            //places the window in the center of the screen
            frame.setLocationRelativeTo(null);
            //makes the window visible
            frame.setVisible(true);
        });
    }
}