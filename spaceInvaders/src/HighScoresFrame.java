import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

public class HighScoresFrame extends JFrame {
    private static final String HIGH_SCORES_FILE = "highscores.txt";
    private static final int MAX_SCORES = 10;

    public HighScoresFrame() {

        JList<String> scoresList = new JList<>(getHighScores());
        scoresList.setFont(new Font("Arial", Font.PLAIN, 18));
        scoresList.setForeground(Color.WHITE);
        scoresList.setBackground(Color.BLACK);
        scoresList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setLayout(new BorderLayout());
        add(new JScrollPane(scoresList), BorderLayout.CENTER);
        updateHighScores();


    }

    public String[] getHighScores() {
        try {
            File file = new File(HIGH_SCORES_FILE);
            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> scores = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                scores.add(line);

            }
            reader.close();


            Collections.sort(scores, new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    int score1 = Integer.parseInt(s1.split(" ")[1]);
                    int score2 = Integer.parseInt(s2.split(" ")[1]);
                    return Integer.compare(score2, score1);
                }
            });

            List<String> topScores = new ArrayList<>();
            int count = 0;
            for (String score : scores) {
                if (count >= MAX_SCORES) {
                    break;
                }
                topScores.add(score);
                count++;
            }

            writeHighScores(topScores);

            return topScores.toArray(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return new String[]{"No scores found."};
        }
    }

    public void writeHighScores(List<String> scores) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(HIGH_SCORES_FILE));
            for (String score : scores) {
                writer.println(score);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHighScore(String username, int score) {
        try {
            File file = new File(HIGH_SCORES_FILE);
            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> scores = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                scores.add(line);
            }
            reader.close();

            scores.add(username + " " + score);

            Collections.sort(scores, new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    int score1 = Integer.parseInt(s1.split(" ")[1]);
                    int score2 = Integer.parseInt(s2.split(" ")[1]);
                    return Integer.compare(score2, score1);
                }
            });

            if (scores.size() > MAX_SCORES) {
                scores = scores.subList(0, MAX_SCORES);
            }

            writeHighScores(scores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateHighScores() {

        HighScorePanel highScorePanel = new HighScorePanel(this);

        getContentPane().removeAll();

        getContentPane().add(highScorePanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

}
