import java.util.ArrayList;
import java.util.List;

public class ShotMovement implements Runnable {
    private List<Shot> shots;
    private BombManager bombManager;

    public ShotMovement(List<Shot> shots, BombManager bombManager) {
        this.shots = shots;
        this.bombManager = bombManager;
    }

    @Override
    public void run() {
        while (true) {
            List<Shot> shotsCopy = new ArrayList<>(shots);

            for (Shot shot : shotsCopy) {
                shot.update();

                if (shot.posY < 0) {
                    shots.remove(shot);
                } else {
                    List<Bomb> bombs = bombManager.bombs;
                    for (Bomb bomb : bombs) {
                        if (shot.collide(bomb)) {
                            bomb.reset();

                            shots.remove(shot);
                            break;
                        }
                    }
                }
            }

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
