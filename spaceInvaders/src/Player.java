import java.awt.*;

public class Player {
    int posX, posY, size;
    boolean moveLeft, moveRight, moveUp, moveDown;

    public boolean collide(Bomb bomb) {
        int distX = posX - bomb.posX;
        int distY = posY - bomb.posY;
        return Math.sqrt(distX * distX + distY * distY) < (double) (size + bomb.size) / 2;
    }

    public Player() {
        this.size = GamePanel.getPlayerSize();
        this.posX = GamePanel.getWidthX()/ 2 - size / 2;
        this.posY = GamePanel.getHeightY() - size;
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(posX, posY, size, size);
    }

    public Shot shoot() {
        return new Shot(posX + size / 2, posY);
    }
}


