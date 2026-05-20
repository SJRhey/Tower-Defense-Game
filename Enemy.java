import java.util.ArrayList;
import java.awt.Color;

/**
 * Abstract base class for all enemy types.
 * Handles waypoint-following movement and health management.
 * Subclasses must provide their display color via getColor().
 */
public abstract class Enemy {

    // ---------------------------------------------------------------
    // Instance variables — already declared for you, do not change
    // ---------------------------------------------------------------
    private double x;
    private double y;
    private int health;
    private int maxHealth;
    private int speed;
    private int goldReward;

    private ArrayList<Waypoint> path;
    private int currentWaypointIndex;
    private boolean reachedEnd;
    
    public static int speedInc;

    // ---------------------------------------------------------------
    // Constructor — already written for you, do not change
    // ---------------------------------------------------------------

    /**
     * Constructs an Enemy that will follow the given path.
     * Starts positioned at the first waypoint.
     *
     * @param health     starting (and max) health points
     * @param speed      pixels moved per frame toward the next waypoint
     * @param goldReward gold awarded to the player when this enemy is defeated
     * @param path       the ordered list of Waypoints to follow
     */
    public Enemy(int health, int speed, int goldReward, ArrayList<Waypoint> path) {
        this.health = health;
        this.maxHealth = health;
        this.speed = speed;
        this.goldReward = goldReward;
        this.path = path;
        this.currentWaypointIndex = 1;
        this.reachedEnd = false;

        this.x = path.get(0).getX();
        this.y = path.get(0).getY();
    }

    // ---------------------------------------------------------------
    // Abstract method — you define this in each subclass
    // ---------------------------------------------------------------

    /**
     * Returns the color used to draw this enemy on screen.
     * Must be implemented by each subclass.
     *
     * @return the display Color of this enemy
     */
    public abstract Color getColor();

    // ---------------------------------------------------------------
    // TODO #1 — Implement move()
    // ---------------------------------------------------------------

    /**
     * Moves this enemy toward its current target waypoint at its speed.
     *
     * ALGORITHM:
     *  1. If reachedEnd is true OR currentWaypointIndex is out of bounds, set
     *     reachedEnd = true and return immediately.
     *  2. Get the target Waypoint at currentWaypointIndex.
     *  3. Calculate dx = target.getX() - x  and  dy = target.getY() - y.
     *  4. Calculate distance = Math.sqrt(dx * dx + dy * dy).
     *  5. If distance <= speed:
     *       - Snap x and y to the waypoint's coordinates.
     *       - Increment currentWaypointIndex.
     *       - If currentWaypointIndex >= path.size(), set reachedEnd = true.
     *  6. Otherwise:
     *       - Move one step: x += (dx / distance) * speed  (same for y).
     */
    public void move() {
        // TODO #1: Write the waypoint-following movement logic here.
        if (reachedEnd || currentWaypointIndex > path.size()) {
            reachedEnd = true;
            return;
        }
        
        Waypoint targetWaypoint = path.get(currentWaypointIndex);
        double dx = targetWaypoint.getX() - x;
        double dy = targetWaypoint.getY() - y;
        
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        if (distance <= speed) {
            x = targetWaypoint.getX();
            y = targetWaypoint.getY();
            currentWaypointIndex++;
            
            if (currentWaypointIndex >= path.size()) {
                reachedEnd = true;
            }
        }
        else {
            x += (dx / distance) * speed;
            y += (dy / distance) * speed;
        }
    }

    // ---------------------------------------------------------------
    // TODO #2 — Implement takeDamage(int amount)
    // ---------------------------------------------------------------

    /**
     * Reduces this enemy's health by the given amount.
     * Health should not drop below zero.
     *
     * @param amount the amount of damage to deal
     */
    public void takeDamage(int amount) {
        // TODO #2: Subtract amount from health. Make sure health cannot go below 0.
        health -= amount;
        if (health < 0) {
            health = 0;
        }
    }

    // ---------------------------------------------------------------
    // TODO #3 — Implement isDead()
    // ---------------------------------------------------------------

    /**
     * Returns true if this enemy's health has reached zero.
     *
     * @return true if dead, false otherwise
     */
    public boolean isDead() {
        // TODO #3: Return true if health is less than or equal to 0.
        return health <= 0;
    }

    // ---------------------------------------------------------------
    // Accessors — already written for you, do not change
    // ---------------------------------------------------------------

    /**
     * Returns the current x coordinate of this enemy (as an int for rendering).
     * @return x position
     */
    public int getX() {
        return (int) x;
    }

    /**
     * Returns the current y coordinate of this enemy (as an int for rendering).
     * @return y position
     */
    public int getY() {
        return (int) y;
    }

    /**
     * Returns the gold reward granted when this enemy is defeated.
     * @return gold reward amount
     */
    public int getGoldReward() {
        return goldReward;
    }

    /**
     * Returns true if this enemy has completed the full path.
     * @return true if the enemy reached the end
     */
    public boolean hasReachedEnd() {
        return reachedEnd;
    }

    /**
     * Returns a value from 0.0 to 1.0 representing remaining health.
     * Used by GamePanel to draw the health bar.
     * @return health ratio
     */
    public double getHealthRatio() {
        return (double) health / maxHealth;
    }
    
    public static void increaseSpeed() {
        speedInc++;
    }
}
