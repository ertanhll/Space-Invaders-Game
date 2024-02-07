import javax.swing.*;
import java.awt.*;

public class AboutDialog extends JDialog {

    public AboutDialog(JFrame parent) {
        super(parent, "About", true);
        setLayout(new GridLayout(5, 1));
        JPanel panel = new JPanel();
        JLabel nameLabel = new JLabel("Name: Halil");
        JLabel surnameLabel = new JLabel("Surname: Ertan");
        JLabel schoolNumberLabel = new JLabel("School Number: 20220702109");
        JLabel emailLabel = new JLabel("Email: halil.ertan3@std.yeditepe.edu.tr");
        add(nameLabel);
        add(surnameLabel);
        add(schoolNumberLabel);
        add(emailLabel);

        pack();
        setLocationRelativeTo(parent);
    }

}


