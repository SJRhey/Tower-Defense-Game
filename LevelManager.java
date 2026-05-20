import java.util.ArrayList;

/**
 * The central game-logic controller.
 * Manages all active towers and enemies, handles the economy (gold and lives),
 * controls wave spawning, and runs the per-frame update loop.
 *
 * YOU WILL COMPLETE THE TODO SECTIONS in this file.
 */
public class LevelManager {

    // ---------------------------------------------------------------
    // Constants — do not change
    // ---------------------------------------------------------------
    public static final int STARTING_GOLD  = 100;
    public static final int STARTING_LIVES = 20;
    private static final int SPAWN_INTERVAL = 30;

    // ---------------------------------------------------------------
    // Instance variables — do not change
    // ---------------------------------------------------------------
    private ArrayList<Enemy>    activeEnemies;
    private ArrayList<Tower>    activeTowers;
    private ArrayList<Waypoint> path;

    private int gold;
    private int lives;
    private int frameCount;
    private int waveNumber;
    private int enemiesSpawnedThisWave;
    private int enemiesPerWave;
    private boolean gameOver;

    // ---------------------------------------------------------------
    // Constructor — already written for you, do not change
    // ---------------------------------------------------------------

    /**
     * Initializes the LevelManager with empty lists, starting gold,
     * starting lives, and the path from GameAssets.
     */
    public LevelManager() {
        activeEnemies          = new ArrayList<Enemy>();
        activeTowers           = new ArrayList<Tower>();
        path                   = GameAssets.buildPath();

        gold                   = STARTING_GOLD;
        lives                  = STARTING_LIVES;
        frameCount             = 0;
        waveNumber             = 1;
        enemiesSpawnedThisWave = 0;
        enemiesPerWave         = 5;
        gameOver               = false;
    }

    // ---------------------------------------------------------------
    // TODO #6 — Implement addTower(int x, int y, int type)
    // ---------------------------------------------------------------

    /**
     * Attempts to place a tower at the specified coordinates.
     * Creates the correct tower type based on the type parameter,
     * then checks whether the player can afford it.
     * If they can, deduct the cost from gold and add the tower to activeTowers.
     * If they cannot, do nothing.
     *
     * Tower type codes:
     *   0 = BasicTower   (cost 50)
     *   1 = RapidTower   (cost 75)
     *   2 = SniperTower  (cost 100)
     *
     * @param x    x coordinate of placement
     * @param y    y coordinate of placement
     * @param type 0, 1, or 2 as described above
     */
    public void addTower(int x, int y, int type) {
        // TODO #6: Create the correct tower subclass using if/else on type.
        //          Then check if the player has enough gold.
        //          If yes: deduct the cost and add the tower to activeTowers.
        //          If no:  do nothing.
        if (type == 0 && gold >= 50) {
            activeTowers.add(new BasicTower(x, y));
            gold -= 50;
        }
        else if (type == 1 && gold >= 75) {
            activeTowers.add(new RapidTower(x, y));
            gold -= 75;
        }
        else if (type == 2 && gold >= 100) {
            activeTowers.add(new SniperTower(x, y));
            gold -= 100;
        }
    }

    // ---------------------------------------------------------------
    // TODO #7 — Implement spawnEnemy()
    // ---------------------------------------------------------------

    /**
     * Spawns one enemy and adds it to activeEnemies.
     * Also increments enemiesSpawnedThisWave.
     *
     * Wave composition rules:
     *   - If waveNumber >= 3 AND this is the last enemy of the wave
     *     (enemiesSpawnedThisWave == enemiesPerWave - 1): spawn a Tank.
     *   - Else if waveNumber >= 2 AND enemiesSpawnedThisWave % 3 == 2: spawn a Speedster.
     *   - Otherwise: spawn a Grunt.
     *
     * All enemy constructors require the path ArrayList as their only argument.
     */
    private void spawnEnemy() {
        // TODO #7: Use if/else to decide which enemy to spawn.
        //          Create the enemy, add it to activeEnemies, and increment enemiesSpawnedThisWave.
        if (waveNumber >= 3 && enemiesSpawnedThisWave == enemiesPerWave - 1) {
            activeEnemies.add(new Tank(path));
            enemiesSpawnedThisWave++;
        }
        else if (waveNumber >= 2 && enemiesSpawnedThisWave % 3 == 2) {
            activeEnemies.add(new Speedster(path));
            enemiesSpawnedThisWave++;
        }
        else {
            activeEnemies.add(new Grunt(path));
            enemiesSpawnedThisWave++;
        }
    }

    // ---------------------------------------------------------------
    // TODO #8 — Implement updateAll()
    // ---------------------------------------------------------------

    /**
     * Executes one frame of game logic. Called every frame by GamePanel.
     *
     * STEP-BY-STEP:
     *  1. If gameOver is true, return immediately — do nothing.
     *  2. Increment frameCount.
     *  3. WAVE SPAWNING: If fewer enemies have been spawned than enemiesPerWave
     *     AND frameCount % SPAWN_INTERVAL == 0, call spawnEnemy().
     *  4. MOVE: Loop through activeEnemies and call move() on each one.
     *  5. TOWER COOLDOWNS & FIRING: Loop through activeTowers.
     *       a. Call tickCooldown() on each tower.
     *       b. If isReadyToFire() is true:
     *            - Call getClosestEnemy(activeEnemies) on the tower.
     *            - If a non-null target is returned:
     *                 * Call takeDamage(tower.getDamage()) on the target.
     *                 * Call resetCooldown() on the tower.
     *  6. REMOVE ENEMIES (backward traversal — use a for loop counting DOWN from
     *     activeEnemies.size() - 1 to 0):
     *       a. If the enemy at index i has hasReachedEnd() == true:
     *            - Decrement lives.
     *            - Remove the enemy from activeEnemies.
     *            - If lives <= 0, set lives = 0 and gameOver = true.
     *       b. Else if the enemy at index i has isDead() == true:
     *            - Add that enemy's gold reward to gold.
     *            - Remove the enemy from activeEnemies.
     *  7. WAVE ADVANCEMENT: If enemiesSpawnedThisWave >= enemiesPerWave
     *     AND activeEnemies is empty:
     *            - Increment waveNumber.
     *            - Reset enemiesSpawnedThisWave to 0.
     *            - Increase enemiesPerWave by 3.
     */
    public void updateAll() {
        // TODO #8: Implement all seven steps described above.
        if (gameOver) {
            return;
        }
        
        frameCount++;
        
        if (enemiesSpawnedThisWave < enemiesPerWave && frameCount % SPAWN_INTERVAL == 0) {
            spawnEnemy();
        }
        
        for (int i = 0; i < activeEnemies.size(); i++) {
            activeEnemies.get(i).move();
        }
        
        for (int i = 0; i < activeTowers.size(); i++) {
            Tower tower = activeTowers.get(i);
            
            tower.tickCooldown();
            if (tower.isReadyToFire()) {
                Enemy closest = tower.getClosestEnemy(activeEnemies);
                
                if (closest != null) {
                    closest.takeDamage(tower.getDamage());
                    tower.resetCooldown();
                }
            }
        }
        
        for (int i = activeEnemies.size() - 1; i >= 0; i--) {
            Enemy enemy = activeEnemies.get(i);
            
            if (enemy.hasReachedEnd()) {
                lives--;
                activeEnemies.remove(i);
                
                if (lives <= 0) {
                    lives = 0;
                    gameOver = true;
                }
            }
            else if (enemy.isDead()) {
                gold += enemy.getGoldReward();
                if (gold >= 100) {
                    gold = 100;
                }
                activeEnemies.remove(i);
            }
        }
        
        if (enemiesSpawnedThisWave >= enemiesPerWave && activeEnemies.size() <= 0) {
            waveNumber++;
            enemiesSpawnedThisWave = 0;
            enemiesPerWave += 3;
            Enemy.increaseSpeed();
            if (waveNumber % 2 == 1) {
                Enemy.increaseSpeed();
            }
        }
    }

    // ---------------------------------------------------------------
    // Accessors — already written for you, do not change
    // ---------------------------------------------------------------

    /** Returns the list of active enemies (used for rendering). */
    public ArrayList<Enemy> getEnemies() { return activeEnemies; }

    /** Returns the list of active towers (used for rendering). */
    public ArrayList<Tower> getTowers() { return activeTowers; }

    /** Returns the player's current gold. */
    public int getGold() { return gold; }

    /** Returns the player's remaining lives. */
    public int getLives() { return lives; }

    /** Returns the current wave number. */
    public int getWaveNumber() { return waveNumber; }

    /** Returns true if the game is over. */
    public boolean isGameOver() { return gameOver; }

    /** Returns the path (used by GamePanel to draw the road). */
    public ArrayList<Waypoint> getPath() { return path; }
}
