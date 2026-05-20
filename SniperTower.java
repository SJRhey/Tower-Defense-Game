import java.awt.Color;

public class SniperTower extends Tower {
    
    public SniperTower(int x, int y) {
        super(x, y, 250, 25, 100, 30);
    }
    
    public Color getColor() {
        return Color.MAGENTA;
    }
}