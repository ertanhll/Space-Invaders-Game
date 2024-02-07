import java.util.ArrayList;

public class BombManager {
    ArrayList<Bomb> bombs;

    private static final double SPEED_INCREASE_AMOUNT = 3;
    private long startTime;
    private long elapsedTime;
    private int speedIncreaseInterval;


    public BombManager(int count) {
        bombs = new ArrayList<>();
        this.startTime = System.currentTimeMillis();
        this.elapsedTime = 0;
        this.speedIncreaseInterval = 0;

        for (int i = 0; i < count; i++) {
            bombs.add(new Bomb(i * 50));
        }
    }


    public synchronized void update(long elapsedTime, int level) {
        this.elapsedTime += elapsedTime;

       if (elapsedTime < 3000) {
           for (Bomb bomb : bombs)
               bomb.reset();
       }

            if (level > speedIncreaseInterval) {
                speedIncreaseInterval = level;
                increaseBombSpeed();
            }


        for (Bomb bomb : bombs) {
            bomb.update();
            if (bomb.posY > GamePanel.getHeightY() && bomb.isFalling) {
                bomb.isFalling = false;
                int nextBombIndex = bombs.indexOf(bomb) + 1;
                if (nextBombIndex < bombs.size()) {
                    bombs.get(nextBombIndex).reset();
                }
                bomb.reset();
            }
        }
    }


    private void increaseBombSpeed() {

        for (Bomb bomb : bombs) {
            bomb.increaseSpeed(SPEED_INCREASE_AMOUNT);
        }
    }

    public synchronized Bomb checkCollision(Player player) {
        for (Bomb bomb : bombs) {
            if (bomb.collide(player)) {
                return bomb;
            }
        }
        return null;
    }
}

