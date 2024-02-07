import java.util.Iterator;
import java.util.List;

public class BombMovement implements Runnable {

    private BombManager  bombManager = new BombManager(GamePanel.getNumBombs());
    private List<Bomb> bombs;

    public BombMovement(BombManager bombManager, List<Bomb> bombs) {
        this.bombManager = bombManager;
        this.bombs = bombs;
    }

    public void run() {
        while (true) {
            for (Bomb bomb : bombManager.bombs) {
                bomb.update();
            }

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

        }

    }


}