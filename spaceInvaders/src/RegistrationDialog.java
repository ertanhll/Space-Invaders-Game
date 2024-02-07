import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton cancelButton;
    private UserDatabase userDatabase;

    public RegistrationDialog(JFrame parent, UserDatabase userDatabase) {
        super(parent, "Register", true);
        this.userDatabase = userDatabase;
        setSize(300, 150);
        setLayout(new BorderLayout());


        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        inputPanel.add(passwordField);
        add(inputPanel, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel(new FlowLayout());
        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                User user = new User(usernameField.getText(), new String(passwordField.getPassword()));
                boolean success = userDatabase.addUser(user);
                usernameField.setText("");
                passwordField.setText("");
                if (success) {
                    dispose();
                }
            }
        });
        buttonPanel.add(registerButton);
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }
}
