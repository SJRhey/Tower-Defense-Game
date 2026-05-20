import java.util.ArrayList;
import java.awt.Color;

/**
 * Provides shared game constants including the path waypoints and display colors.
 * All methods are static — this class is never instantiated.
 */
public class GameAssets {

    // ---------------------------------------------------------------
    // Color constants used by GamePanel for rendering
    // ---------------------------------------------------------------
    public static final Color PATH_COLOR      = new Color(180, 150, 100);
    public static final Color GRASS_COLOR     = new Color(60, 120, 40);
    public static final Color SIDEBAR_COLOR   = new Color(40, 40, 40);
    public static final Color GOLD_COLOR      = new Color(255, 215, 0);
    public static final Color LIVES_COLOR     = new Color(220, 60, 60);
    public static final Color HEALTH_BG       = new Color(80, 0, 0);
    public static final Color HEALTH_FG       = new Color(0, 210, 0);

    // Path thickness in pixels (used when drawing the road)
    public static final int PATH_WIDTH = 48;

    /**
     * Builds and returns the ordered list of Waypoints that define the enemy path.
     * Enemies travel from index 0 to the last index in sequence.
     * The path creates a winding route across the 650x600 play area
     * (the sidebar occupies x = 650 to 800).
     *
     * @return ArrayList of Waypoints in travel order
     */
    public static ArrayList<Waypoint> buildPath() {
        ArrayList<Waypoint> path = new ArrayList<Waypoint>();

        // Entry point — left edge, upper third
        path.add(new Waypoint(-10,  120));
        // First horizontal run to the right
        path.add(new Waypoint( 200, 120));
        // Drop down
        path.add(new Waypoint( 200, 350));
        // Run left briefly
        path.add(new Waypoint(  80, 350));
        // Drop to bottom third
        path.add(new Waypoint(  80, 490));
        // Long run right toward exit
        path.add(new Waypoint( 500, 490));
        // Rise up
        path.add(new Waypoint( 500, 280));
        // Final run to right edge (into the sidebar area — enemy is removed here)
        path.add(new Waypoint( 660, 280));

        return path;
    }
}
