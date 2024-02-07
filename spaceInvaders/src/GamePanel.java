import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GamePanel extends JPanel implements ActionListener {
    private static final int WIDTH =994 ;
    private static final int HEIGHT = 712;

    private static final int PLAYER_SIZE = 60;

    private static final int NUM_BOMBS = 5;
    private ExecutorService executor;
    private Player player;
    private List<Shot> shots;
    private BombManager bombManager;
    private Timer timer;

    private long elapsedTime;

    private int level = 1;

    private int score = 0;

    private int lives = 3;
    private long startTime;

    private User user;
    private long frameCount = 0;
    private long lastTime;
    private int fps;



        public GamePanel() {

        executor = Executors.newFixedThreadPool(1);
        player = new Player();
        shots = new ArrayList<>();
        bombManager = new BombManager(1000);
        startTime = System.currentTimeMillis();
        lastTime = System.nanoTime();
        fpsTimer.start();

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);

        timer = new Timer(16, this);  //(1000/16) yaklasik  60fps
        timer.start();

        executor.execute(new PlayerMovement(player, this));
        executor.execute(new BombMovement(bombManager,bombManager.bombs ));
        executor.execute(new ShotMovement(shots, bombManager));






        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (shots.size() < 10) {
                    shots.add(player.shoot());
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                player.posX = e.getX() - player.size / 2;
                player.posY = e.getY() - player.size / 2;
            }
        });


        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_LEFT) {
                    player.moveLeft = true;
                }

                if (key == KeyEvent.VK_RIGHT) {
                    player.moveRight = true;
                }

                if (key == KeyEvent.VK_UP) {
                    player.moveUp = true;
                }

                if (key == KeyEvent.VK_DOWN) {
                    player.moveDown = true;
                }

                if (key == KeyEvent.VK_SPACE) {
                    shots.add(player.shoot());
                }
            }

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT) {
                player.moveLeft = false;
            }

            if (key == KeyEvent.VK_RIGHT) {
                player.moveRight = false;
            }

            if (key == KeyEvent.VK_UP) {
                player.moveUp = false;
            }

            if (key == KeyEvent.VK_DOWN) {
                player.moveDown = false;
            }
        }
    });

        setFocusable(true);
        requestFocusInWindow();

}


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);



        for (Bomb bomb : bombManager.bombs) {
            if (bomb.posY >= getHeight()) {
                bomb.reset();

            }
            if (Math.random() < 0.9) {
                bomb.draw(g);

            } else {
                bomb.draw2(g);

            }
        }
        for (Shot shot : shots)
            shot.draw(g);

        player.draw(g);

        Font font = new Font("Arial", Font.BOLD, 20);
        Color textColor = Color.RED;


        System.out.println("elapsedtime"+ elapsedTime);

        if ( elapsedTime > 0 && elapsedTime < 3000) {
            g.setColor(textColor);
            g.setFont(font);
            g.drawString("READY FOR GAME", getWidthX() /2-90, getHeightY()/2);

        } else if (elapsedTime < 30000) {
            g.setColor(textColor);
            g.setFont(font);
            g.drawString("Novice", 110, 20);
            level = 1;
        } else if (elapsedTime < 60000) {
            g.setColor(textColor);
            g.setFont(font);
            g.drawString("Intermediate", 110, 20);
            level = 2;
        } else {
            g.setColor(textColor);
            g.setFont(font);
            g.drawString("Professional", 110, 20);
            level = 3;
        }

        g.drawString("Lives: " + lives, 10, 20);
        g.drawString("Score: " + score, 320, 20);
        g.drawString("FPS: " + fps, getWidth() - 100, 20);



        if (lives <= 0) {
            g.setColor(textColor);
            g.setFont(font);
            g.drawString("GAME OVER", getWidthX() /2-70, getHeightY()/2);

        }

    }





    @Override
    public void actionPerformed(ActionEvent e) {
        frameCount++;


        elapsedTime = System.currentTimeMillis() - startTime;

       if (elapsedTime < 30000) {
            bombManager.update(elapsedTime, 1);

        } else if (elapsedTime < 60000) {
            bombManager.update(elapsedTime, 2);

        } else {
            bombManager.update(elapsedTime, 3);

        }

        Bomb collidedBomb = bombManager.checkCollision(player);
        if (collidedBomb != null) {
            collidedBomb.reset();
            lives--;
            if (lives <= 0) {
                timer.stop();
            } else {
                resetGame();
            }
        }


        List<Shot> shotsToRemove = new ArrayList<>();

        for (Shot shot : shots) {
            shot.update();
            if (shot.posY < 0) {
                shotsToRemove.add(shot);
            } else {
                for (Bomb bomb : bombManager.bombs) {
                    if (shot.collide(bomb)) {
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                synchronized (GamePanel.this) {
                                    score += 10;
                                    repaint();
                                    revalidate();
                                }
                            }
                        });

                        bomb.reset();

                        shotsToRemove.add(shot);
                        break;
                    }
                }
            }
        }

        shots.removeAll(shotsToRemove);


        for (Bomb bomb : bombManager.bombs) {
            if (player.collide(bomb)) {
                bomb.reset();
                lives--;
                if (lives <= 0) {
                    timer.stop();
                } else {
                    resetGame();
                }
                break;
            }
        }



        if (lives <= 0) {
            timer.stop();
            gameOver();
        }


        repaint();
    }

    private void gameOver() {

        HighScoresFrame highScoresFrame = new HighScoresFrame();
        highScoresFrame.addHighScore(user.getUsername(), score);
        highScoresFrame.updateHighScores();
    }

    void resetGame() {
        player.posX = WIDTH / 2 - player.size / 2;
        player.posY = HEIGHT - player.size;

        if (lives <= 0) {
            timer.stop();
            gameOver();
        }
        shots.clear();
        for (Bomb bomb : bombManager.bombs) {
            bomb.reset();
        }
        elapsedTime = 0;


    }
    private Timer fpsTimer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            long currentTime = System.nanoTime();
            double elapsedTimeInSeconds = (currentTime - lastTime) / 1_000_000_000.0;
            if (elapsedTimeInSeconds > 0) {
                fps = (int) (frameCount / elapsedTimeInSeconds);
            } else {
                fps = 60;
            }
            frameCount = 0;
            lastTime = currentTime;
        }
    });

    public void setUser(User user) {
        this.user = user;
    }
    public static int getWidthX() {
        return WIDTH;
    }
    public static int getHeightY() {
        return HEIGHT;
    }
    public static int getPlayerSize() {
        return PLAYER_SIZE;
    }

    public static int getNumBombs() {
        return NUM_BOMBS;
    }

}


