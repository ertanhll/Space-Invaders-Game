import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HighScorePanel extends JPanel {

    private JLabel titleLabel;
    private JList<String> highScoreList;


    public HighScorePanel(HighScoresFrame parentFrame) {

        String[] top10 = parentFrame.getHighScores();
        top10 = addNumbersToScores(top10);


        setBackground(Color.BLACK);

        titleLabel = new JLabel("HIGH SCORES");
        titleLabel.setForeground(Color.GREEN);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);


        highScoreList = new JList<>(top10);
        highScoreList.setBackground(Color.BLACK);
        highScoreList.setForeground(Color.WHITE);
        highScoreList.setFont(new Font("Arial", Font.PLAIN, 30));
        highScoreList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);



        JScrollPane scrollPane = new JScrollPane(highScoreList);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.setForeground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.BLACK);
        setLayout(new BorderLayout());


        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);


    }

    private String[] addNumbersToScores(String[] scores) {
        String[] numberedScores = new String[scores.length];
        for (int i = 0; i < scores.length; i++) {
            String[] split = scores[i].split(" ");
            String format;
            if (i + 1 < 10) {
                format = "%43s. %-20s %-20s";
            } else {
                format = "%42s. %-20s %-20s";
            }
            numberedScores[i] = String.format(format, (i + 1), split[0], split[1]);
        }
        return numberedScores;
    }







}

