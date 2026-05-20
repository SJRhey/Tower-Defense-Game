import java.util.ArrayList;
import java.awt.Color;

/**
 * Abstract base class for all tower types.
 * Handles targeting (finding the closest enemy in range) and
 * attack cooldown tracking. Subclasses define their own stats
 * and display color.
 */
public abstract class Tower {

    // ---------------------------------------------------------------
    // Instance variables — already declared for you, do not change
    // ---------------------------------------------------------------
    private int x;
    private int y;
    private int range;
    private int damage;
    private int cost;
    private int cooldownMax;
    private int cooldownTimer;

    // ---------------------------------------------------------------
    // Constructor — already written for you, do not change
    // ---------------------------------------------------------------

    /**
     * Constructs a Tower at the given position with the given stats.
     *
     * @param x           x coordinate of the tower
     * @param y           y coordinate of the tower
     * @param range       attack range in pixels
     * @param damage      damage dealt per attack
     * @param cost        gold cost to place this tower
     * @param cooldownMax frames between attacks (1 = every frame, 30 = once per ~second)
     */
    public Tower(int x, int y, int range, int damage, int cost, int cooldownMax) {
        this.x = x;
        this.y = y;
        this.range = range;
        this.damage = damage;
        this.cost = cost;
        this.cooldownMax = cooldownMax;
        this.cooldownTimer = 0;
    }

    // ---------------------------------------------------------------
    // Abstract method — you define this in each subclass
    // ---------------------------------------------------------------

    /**
     * Returns the color used to draw this tower on screen.
     * Must be implemented by each subclass.
     *
     * @return the display Color of this tower
     */
    public abstract Color getColor();

    // ---------------------------------------------------------------
    // TODO #4 — Implement getClosestEnemy(ArrayList<Enemy> enemies)
    // ---------------------------------------------------------------

    /**
     * Searches the provided list and returns the Enemy closest to this tower
     * that is also within range. Returns null if no enemy qualifies.
     *
     * ALGORITHM:
     *  1. Declare a variable closestTarget = null and minDistance = range.
     *     (Using range as the starting minimum means only in-range enemies qualify.)
     *  2. Loop through every Enemy e in the list.
     *  3. Call calculateDistance(e) to get the distance.
     *  4. If the distance is <= minDistance, update minDistance and closestTarget.
     *  5. Return closestTarget (may be null if nothing was in range).
     *
     * @param enemies the list of active enemies to search
     * @return the closest Enemy within range, or null
     */
    public Enemy getClosestEnemy(ArrayList<Enemy> enemies) {
        // TODO #4: Implement this method using the algorithm above.
        Enemy closestTarget = null;
        double minDistance = range;
        
        for (int i = 0; i < enemies.size(); i++) {
            double currentDistance = calculateDistance(enemies.get(i));
            if (currentDistance <= minDistance) {
                closestTarget = enemies.get(i);
                minDistance = currentDistance;
            }
        }
        
        return closestTarget;
    }

    // ---------------------------------------------------------------
    // TODO #5 — Implement calculateDistance(Enemy e)
    // ---------------------------------------------------------------

    /**
     * Calculates and returns the straight-line distance between this tower
     * and the given enemy using the distance formula.
     *
     * distance = Math.sqrt( (ex - tx)^2 + (ey - ty)^2 )
     *
     * @param e the enemy to measure distance to
     * @return the distance in pixels
     */
    public double calculateDistance(Enemy e) {
        // TODO #5: Use the distance formula. You have getX()/getY() on both this tower and the enemy.
        return Math.sqrt(((e.getX() - x) * (e.getX() - x)) + ((e.getY() - y) * (e.getY() - y)));
    }

    // ---------------------------------------------------------------
    // Cooldown methods — already written for you, do not change
    // ---------------------------------------------------------------

    /**
     * Returns true if this tower is ready to fire (cooldown has expired).
     * @return true if the tower can attack this frame
     */
    public boolean isReadyToFire() {
        return cooldownTimer <= 0;
    }

    /**
     * Resets the cooldown timer to its maximum. Call this after the tower fires.
     */
    public void resetCooldown() {
        cooldownTimer = cooldownMax;
    }

    /**
     * Decrements the cooldown timer by one. Called every frame.
     */
    public void tickCooldown() {
        if (cooldownTimer > 0) {
            cooldownTimer--;
        }
    }

    // ---------------------------------------------------------------
    // Accessors — already written for you, do not change
    // ---------------------------------------------------------------

    /** Returns the x coordinate of this tower. */
    public int getX() { return x; }

    /** Returns the y coordinate of this tower. */
    public int getY() { return y; }

    /** Returns the range of this tower in pixels. */
    public int getRange() { return range; }

    /** Returns the damage this tower deals per attack. */
    public int getDamage() { return damage; }

    /** Returns the gold cost to place this tower. */
    public int getCost() { return cost; }
}
