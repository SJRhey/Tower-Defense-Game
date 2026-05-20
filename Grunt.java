import java.util.ArrayList;
import java.awt.Color;

/**
 * TODO: Write the Grunt class.
 *
 * The Grunt is the standard enemy type with balanced health and speed.
 *
 * REQUIREMENTS:
 *  - Must extend Enemy.
 *  - Constructor takes one parameter: ArrayList<Waypoint> path.
 *  - Call super() with: health = 50, speed = 2, goldReward = 10, path = path.
 *  - Override getColor() to return Color.RED.
 *  - Include a JavaDoc comment above the class and above each method.
 */
public class Grunt extends Enemy {

    // TODO: Write the constructor.
    // Takes the waypoint list and calls super which sets up the enemy already
    public Grunt(ArrayList<Waypoint> path) {
        super(100, 4 + speedInc, 5, path);
    }

    // TODO: Override getColor() and return Color.RED.
    // Returns red
    public Color getColor() {
        return Color.RED;
    }
}
