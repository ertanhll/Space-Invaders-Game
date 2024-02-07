
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;




public class MyFrame extends JFrame {

    private UserDatabase userDatabase;

    public MyFrame() {

        userDatabase = new UserDatabase();
        userDatabase.loadUsersFromFile();
        setTitle("Space Invaders");
      
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();

        leftPanel.setBackground(Color.BLACK);
        rightPanel.setBackground(Color.BLACK);

        ImageIcon backgroundImage = new ImageIcon("src/welcome.png");
        int panelWidth = 130;
        int panelHeight = backgroundImage.getIconHeight();
        leftPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        rightPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(panelWidth, 0, backgroundImage.getIconWidth(), backgroundImage.getIconHeight());


        getContentPane().add(leftPanel, BorderLayout.WEST);
        getContentPane().add(rightPanel, BorderLayout.EAST);
        getContentPane().add(backgroundLabel, BorderLayout.CENTER);


        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu aboutMenu = new JMenu("Help");


        JMenuItem registerItem = new JMenuItem("Register");
        JMenuItem playItem = new JMenuItem("Play Game");
        JMenuItem highscoreItem = new JMenuItem("High Score");
        JMenuItem quitItem = new JMenuItem("Quit");
        fileMenu.add(registerItem);
        fileMenu.add(playItem);
        fileMenu.add(highscoreItem);
        fileMenu.add(quitItem);


        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> {
            AboutDialog dialog = new AboutDialog(MyFrame.this);
            dialog.setVisible(true);
        });

        aboutMenu.add(aboutItem);
        menuBar.add(fileMenu);
        menuBar.add(aboutMenu);
        setJMenuBar(menuBar);


        registerItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrationDialog dialog = new RegistrationDialog(MyFrame.this, userDatabase);
                dialog.setVisible(true);
            }
        });

        playItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginDialog dialog = new LoginDialog(MyFrame.this, userDatabase);
                dialog.setVisible(true);

                if (dialog.isLoginSuccessful()) {

                    User user = dialog.getLoggedUser();
                    GamePanel gamePanel = new GamePanel();
                    gamePanel.setUser(user);


                    getContentPane().removeAll();
                    getContentPane().setLayout(new BorderLayout());
                    getContentPane().add(gamePanel, BorderLayout.CENTER);

                    gamePanel.requestFocusInWindow();

                    revalidate();
                    repaint();


                }
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                userDatabase.saveUsersToFile();
            }
        });

        quitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                userDatabase.saveUsersToFile();

                System.exit(0);
            }
        });

        highscoreItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                HighScoresFrame highScoresFrame = new HighScoresFrame();
                getContentPane().add(new HighScorePanel(highScoresFrame));
                revalidate();
                repaint();
            }
        });



    }


}