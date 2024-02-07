import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class LoginDialog extends JDialog {
    private UserDatabase userDatabase;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;
    private boolean loginSuccessful = false;

    public LoginDialog(JFrame parent, UserDatabase userDatabase) {
        super(parent, "Login", true);
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
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                User user = userDatabase.getUser(username);
                if (user != null && user.getPassword().equals(password)) {

                    loginSuccessful = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginDialog.this, "Incorrect username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                    usernameField.setText("");
                    passwordField.setText("");
                }
            }
        });
        buttonPanel.add(loginButton);
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

    public User getLoggedUser() {
        return userDatabase.getUser(usernameField.getText());
    }

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }
}

