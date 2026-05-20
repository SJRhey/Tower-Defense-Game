import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * The main game rendering panel.
 * Handles the game loop (60 FPS timer), all drawing, and mouse input for tower placement.
 * Students do NOT modify this file.
 */
public class GamePanel extends JPanel implements ActionListener {

    // ---------------------------------------------------------------
    // Constants
    // ---------------------------------------------------------------
    public static final int PLAY_WIDTH  = 650;
    public static final int PLAY_HEIGHT = 600;

    private static final int HEALTH_BAR_W = 24;
    private static final int HEALTH_BAR_H = 4;

    // ---------------------------------------------------------------
    // Fields
    // ---------------------------------------------------------------
    private Timer       timer;
    private LevelManager manager;
    private Sidebar      sidebar;

    // ---------------------------------------------------------------
    // Constructor
    // ---------------------------------------------------------------

    /**
     * Constructs the GamePanel, wires up the LevelManager and Sidebar,
     * and starts the 60 FPS game loop.
     *
     * @param sidebar the Sidebar instance (shared with Runner)
     */
    public GamePanel(Sidebar sidebar) {
        this.sidebar = sidebar;
        manager = new LevelManager();

        setBackground(GameAssets.GRASS_COLOR);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!manager.isGameOver()) {
                    manager.addTower(e.getX(), e.getY(), sidebar.getSelectedTowerType());
                }
            }
        });

        timer = new Timer(16, this);   // ~60 FPS
        timer.start();
    }

    /**
     * Tells Swing how large the play area should be.
     * Required so that Runner's frame.pack() sizes the window correctly.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PLAY_WIDTH, PLAY_HEIGHT);
    }

    // ---------------------------------------------------------------
    // Game loop
    // ---------------------------------------------------------------

    @Override
    public void actionPerformed(ActionEvent e) {
        manager.updateAll();
        sidebar.updateStats(manager.getGold(), manager.getLives(), manager.getWaveNumber());
        repaint();
    }

    // ---------------------------------------------------------------
    // Rendering
    // ---------------------------------------------------------------

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawPath(g2);
        drawTowers(g2);
        drawEnemies(g2);

        if (manager.isGameOver()) {
            drawGameOver(g2);
        }
    }

    /** Draws the winding path as a thick polyline using the waypoints from LevelManager. */
    private void drawPath(Graphics2D g2) {
        ArrayList<Waypoint> path = manager.getPath();
        g2.setColor(GameAssets.PATH_COLOR);
        g2.setStroke(new BasicStroke(GameAssets.PATH_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        for (int i = 0; i < path.size() - 1; i++) {
            Waypoint a = path.get(i);
            Waypoint b = path.get(i + 1);
            g2.drawLine(a.getX(), a.getY(), b.getX(), b.getY());
        }
        g2.setStroke(new BasicStroke(1));  // Reset stroke
    }

    /** Draws all active towers as colored squares with a range circle. */
    private void drawTowers(Graphics2D g2) {
        for (Tower t : manager.getTowers()) {
            // Range circle (translucent)
            g2.setColor(new Color(t.getColor().getRed(),
                                  t.getColor().getGreen(),
                                  t.getColor().getBlue(), 30));
            int r = t.getRange();
            g2.fillOval(t.getX() - r, t.getY() - r, r * 2, r * 2);

            // Tower body
            g2.setColor(t.getColor());
            g2.fillRect(t.getX() - 14, t.getY() - 14, 28, 28);

            // Tower outline
            g2.setColor(Color.WHITE);
            g2.drawRect(t.getX() - 14, t.getY() - 14, 28, 28);
        }
    }

    /** Draws all active enemies as colored circles with a health bar above them. */
    private void drawEnemies(Graphics2D g2) {
        for (Enemy e : manager.getEnemies()) {
            // Enemy body
            g2.setColor(e.getColor());
            g2.fillOval(e.getX() - 12, e.getY() - 12, 24, 24);
            g2.setColor(Color.WHITE);
            g2.drawOval(e.getX() - 12, e.getY() - 12, 24, 24);

            // Health bar background
            int barX = e.getX() - HEALTH_BAR_W / 2;
            int barY = e.getY() - 20;
            g2.setColor(GameAssets.HEALTH_BG);
            g2.fillRect(barX, barY, HEALTH_BAR_W, HEALTH_BAR_H);

            // Health bar foreground
            int filled = (int) (HEALTH_BAR_W * e.getHealthRatio());
            g2.setColor(GameAssets.HEALTH_FG);
            g2.fillRect(barX, barY, filled, HEALTH_BAR_H);
        }
    }

    /** Draws a game-over overlay when the player has lost all lives. */
    private void drawGameOver(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRect(0, 0, PLAY_WIDTH, PLAY_HEIGHT);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 48));
        String msg = "GAME OVER";
        int w = g2.getFontMetrics().stringWidth(msg);
        g2.drawString(msg, (PLAY_WIDTH - w) / 2, PLAY_HEIGHT / 2);

        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        String sub = "You reached Wave " + manager.getWaveNumber();
        int sw = g2.getFontMetrics().stringWidth(sub);
        g2.drawString(sub, (PLAY_WIDTH - sw) / 2, PLAY_HEIGHT / 2 + 40);
    }
}
