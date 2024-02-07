import java.awt.*;

public class Shot implements GameObject{
    int posX, posY, size;

    public Shot(int x, int y) {
        this.size = GamePanel.getPlayerSize()/ 10;
        this.posX = x;
        this.posY = y;
    }

    @Override
    public void update() {
        posY -= 10;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(posX, posY, size, 3*size);
    }

    public synchronized boolean collide(Bomb bomb) {
        int distX = posX - bomb.posX;
        int distY = posY - bomb.posY;
        return Math.sqrt(distX * distX + distY * distY) < (size + bomb.size) / 2;
    }
}

