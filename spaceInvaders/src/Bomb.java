import java.awt.*;
import java.util.Random;

public class Bomb implements  GameObject {


    Random rand = new Random();
    public boolean isFalling;
    int posX, posY, size, xSpeed, ySpeed, moveCount;

    public int delay;

    public Bomb(int delay) {
        this.size = GamePanel.getPlayerSize();
        this.delay = delay;
        this.isFalling = false;

        reset();
    }



    public void increaseSpeed(double amount) {
        this.xSpeed += amount;
        this.ySpeed += amount;
    }

    boolean collide(Player player) {
        int distX = posX - player.posX;
        int distY = posY - player.posY;
        return Math.sqrt(distX * distX + distY * distY) < (size + player.size) / 2;
    }

    public void reset() {
        posX = rand.nextInt(GamePanel.getWidthX()- size);
        posY = -size;
        isFalling = false;
        moveCount = 0;
    }
@Override
    public void update() {
        moveCount++;

        if (moveCount >= delay) {
            isFalling = true;
            posY += ySpeed;
            posX += xSpeed;

            if (posY > GamePanel.getHeightY()) {
                reset();
            }

            if (posX < 0 || posX + size >GamePanel.getWidthX()) {
                xSpeed = -xSpeed;
            }


        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(posX, posY, size/2, size);

    }
    public void draw2(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(posX, posY, size/2, size);
    }
}