import java.util.ArrayList;
import java.awt.Color;

public class Tank extends Enemy {
    
    // Super constructor already sets up the enemy correctly
    public Tank(ArrayList<Waypoint> path) {
        super(400, 2 + speedInc, 15, path);
    }
    
    // Make the tank pink
    public Color getColor() {
        return Color.PINK;
    }
}