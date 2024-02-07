import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserDatabase {
    private Map<String, User> userMap;
    public UserDatabase() {
        userMap = new HashMap<String, User>();
    }
    public boolean addUser(User user) {
        String username = user.getUsername();
        String password = user.getPassword();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username or password cannot be empty", "Warning", JOptionPane.WARNING_MESSAGE);

            return false;

        } else if (userMap.containsKey(username)) {
            JOptionPane.showMessageDialog(null, "Username " + username + " already exists.", "Warning", JOptionPane.WARNING_MESSAGE);

            return false;
        } else {
            for (User u : userMap.values()) {
                if (u.getUsername().equals(username)) {

                    JOptionPane.showMessageDialog(null, "Username " + username + " already exists.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            }
            userMap.put(username, user);
            saveUsersToFile();
            JOptionPane.showMessageDialog(null, "User added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

            return true;
        }
    }

    public User getUser(String username) {
        return userMap.get(username);
    }

    public void saveUsersToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt"));
            for (User user : userMap.values()) {
                writer.write(user.getUsername() + ":" + user.getPassword());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadUsersFromFile() {
        try {
            File file = new File("users.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                String username = parts[0];
                String password = parts[1];
                User newUser = new User(username, password);

                if (!userMap.containsKey(username)) {
                    userMap.put(username, newUser);

                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}