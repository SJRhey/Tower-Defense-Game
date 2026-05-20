import java.util.ArrayList;
import java.awt.Color;

public class Speedster extends Enemy {
    // Constructor in super already sets up the enemy correctly
    public Speedster(ArrayList<Waypoint> path) {
        super(50, 12 + speedInc, 5, path);
    }
    
    // Make the speedster orange
    public Color getColor() {
        return Color.ORANGE;
    }
}