import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * The sidebar panel displayed on the right side of the game window.
 * Provides three tower-selection buttons and displays the player's
 * current gold, lives, and wave number.
 *
 * The selected tower type is stored as an integer:
 *   0 = BasicTower, 1 = RapidTower, 2 = SniperTower
 *
 * GamePanel reads getSelectedTowerType() each time the player clicks the map.
 */
public class Sidebar extends JPanel {

    private int selectedTowerType = 0;

    private JLabel goldLabel;
    private JLabel livesLabel;
    private JLabel waveLabel;

    /**
     * Constructs the Sidebar and adds all UI components.
     */
    public Sidebar() {
        setPreferredSize(new Dimension(150, 600));
        setBackground(GameAssets.SIDEBAR_COLOR);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 8, 6, 8);

        // ---- Title ----
        JLabel title = makeLabel("TOWERS", 16, true, Color.WHITE);
        gbc.gridy = 0;
        add(title, gbc);

        // ---- Tower buttons ----
        JButton basicBtn  = makeTowerButton("Basic Tower",  "Cost: 50g",  Color.BLUE,    0);
        JButton rapidBtn  = makeTowerButton("Rapid Tower",  "Cost: 75g",  Color.GREEN,   1);
        JButton sniperBtn = makeTowerButton("Sniper Tower", "Cost: 100g", Color.MAGENTA, 2);

        gbc.gridy = 1; add(basicBtn,  gbc);
        gbc.gridy = 2; add(rapidBtn,  gbc);
        gbc.gridy = 3; add(sniperBtn, gbc);

        // ---- Stats ----
        JLabel statsTitle = makeLabel("── STATS ──", 12, true, Color.LIGHT_GRAY);
        gbc.gridy = 4;
        add(statsTitle, gbc);

        goldLabel = makeLabel("Gold: 100", 13, false, GameAssets.GOLD_COLOR);
        livesLabel = makeLabel("Lives: 20", 13, false, GameAssets.LIVES_COLOR);
        waveLabel  = makeLabel("Wave: 1",  13, false, Color.CYAN);

        gbc.gridy = 5; add(goldLabel,  gbc);
        gbc.gridy = 6; add(livesLabel, gbc);
        gbc.gridy = 7; add(waveLabel,  gbc);
    }

    // ---------------------------------------------------------------
    // Helper builders
    // ---------------------------------------------------------------

    private JButton makeTowerButton(String name, String costText, Color color, int type) {
        JButton btn = new JButton("<html><center>" + name + "<br><small>" + costText + "</small></center></html>");
        btn.setForeground(color);
        btn.setBackground(new Color(60, 60, 60));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(color, 2));
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.addActionListener(e -> selectedTowerType = type);
        return btn;
    }

    private JLabel makeLabel(String text, int size, boolean bold, Color color) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(new Font("Arial", bold ? Font.BOLD : Font.PLAIN, size));
        label.setForeground(color);
        return label;
    }

    // ---------------------------------------------------------------
    // Update display
    // ---------------------------------------------------------------

    /**
     * Updates the displayed gold, lives, and wave number.
     * Called by GamePanel once per frame.
     *
     * @param gold  current gold amount
     * @param lives remaining lives
     * @param wave  current wave number
     */
    public void updateStats(int gold, int lives, int wave) {
        goldLabel.setText("Gold: " + gold);
        livesLabel.setText("Lives: " + lives);
        waveLabel.setText("Wave: " + wave);
    }

    // ---------------------------------------------------------------
    // Accessor
    // ---------------------------------------------------------------

    /**
     * Returns the currently selected tower type index.
     * 0 = BasicTower, 1 = RapidTower, 2 = SniperTower
     *
     * @return selected tower type
     */
    public int getSelectedTowerType() {
        return selectedTowerType;
    }
}
