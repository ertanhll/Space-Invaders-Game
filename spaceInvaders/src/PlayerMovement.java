public class PlayerMovement implements Runnable {
    private Player player;
    private GamePanel gamePanel;

    public PlayerMovement(Player player, GamePanel gamePanel) {
        this.player = player;
        this.gamePanel = gamePanel;
    }
    @Override
    public void run() {
        while (true) {
            if (player.moveLeft && player.posX - 10 > 0) {
                player.posX -= 10;
            }
            if (player.moveRight && player.posX + player.size + 10 < GamePanel.getWidthX()) {
                player.posX += 10;
            }
            if (player.moveUp && player.posY - 10 > 0) {
                player.posY -= 10;
            }
            if (player.moveDown && player.posY + player.size + 10 < GamePanel.getHeightY()) {
                player.posY += 10;
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
