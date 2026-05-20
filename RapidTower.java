import java.awt.Color;

public class RapidTower extends Tower {
    
    public RapidTower(int x, int y) {
        super(x, y, 80, 4, 75, 1);
    }
    
    public Color getColor() {
        return Color.GREEN;
    }
}