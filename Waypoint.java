/**
 * Represents a single point on the enemy path.
 * Enemies navigate from one Waypoint to the next in sequence.
 */
public class Waypoint {
    private int x;
    private int y;

    /**
     * Constructs a Waypoint at the given coordinates.
     * @param x the x coordinate of this waypoint
     * @param y the y coordinate of this waypoint
     */
    public Waypoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x coordinate of this waypoint.
     * @return x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y coordinate of this waypoint.
     * @return y coordinate
     */
    public int getY() {
        return y;
    }
}
