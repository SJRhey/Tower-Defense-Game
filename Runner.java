import javax.swing.JFrame;
import java.awt.BorderLayout;

/**
 * Entry point for the Tower Defense game.
 * Creates the JFrame, attaches the GamePanel and Sidebar, and makes the window visible.
 * Students do NOT modify this file.
 */
public class Runner {

    /**
     * Main method — launches the game.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tower Defense: Java Edition");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        Sidebar sidebar = new Sidebar();
        GamePanel panel = new GamePanel(sidebar);

        frame.add(panel,   BorderLayout.CENTER);
        frame.add(sidebar, BorderLayout.EAST);

        frame.pack();
        frame.setLocationRelativeTo(null);  // Center on screen
        frame.setVisible(true);
    }
}
