package culinarycompass.culinarycompass;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class LoginController {

    @FXML
    private TextField nicknameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label messageLabel;
    private User authenticatedUser;

    private Optional<User> authenticate(String nickname, String password) {
        List<User> users = loadUsers();
        return users.stream()
                .filter(u -> u.getNickname().equals(nickname) && u.getPassword().equals(password))
                .findFirst();
    }

    @FXML
    protected void handleLogin() {
        String nickname = nicknameField.getText();
        String password = passwordField.getText();
        Optional<User> user = authenticate(nickname, password);
        if (user.isPresent()) {
            authenticatedUser = user.get();
            showMessage("You have successfully logged in!", Color.GREEN);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FoodSelection.fxml"));
                Parent root = loader.load();
                FoodSelectionController foodSelectionController = loader.getController();
                foodSelectionController.setUser(authenticatedUser);

                // Get the instance of the main application to retrieve recipes
                main application = main.getInstance();
                foodSelectionController.setAllRecipes(application.getAllRecipes());

                Stage stage = new Stage();
                stage.setTitle("Select Your Food/Groceries");
                stage.setScene(new Scene(root, 700, 400));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showMessage("Failed to load the food selection screen.", Color.RED);
            }
        } else {
            showMessage("Incorrect nickname or password.", Color.RED);
        }
    }
    @FXML
    protected void handleRegister() {
        String nickname = nicknameField.getText();
        String password = passwordField.getText();
        if (nickname.isEmpty() || password.isEmpty()) {
            showMessage("Nickname and password cannot be empty.", Color.RED);
            return;
        }
        if (userExists(nickname)) {
            showMessage("User with this nickname already exists.", Color.RED);
        } else {
            if (registerNewUser(nickname, password)) {
                showMessage("User has been registered successfully!", Color.GREEN);
            } else {
                showMessage("There was an error registering the user.", Color.RED);
            }
        }
    }

    private boolean userExists(String nickname) {
        List<User> users = loadUsers();
        return users.stream().anyMatch(u -> u.getNickname().equalsIgnoreCase(nickname));
    }

    private boolean registerNewUser(String nickname, String password) {
        try (FileWriter fw = new FileWriter("credentials.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            int newId = generateNewUserId();
            out.println(newId + " " + nickname + " " + password);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("credentials.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.trim().startsWith("//")) {
                    String[] credentials = line.split(" ");
                    if (credentials.length == 3) {
                        users.add(new User(credentials[0].trim(), credentials[1].trim(), credentials[2].trim()));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    private int generateNewUserId() {
        Optional<User> maxIdUser = loadUsers().stream()
                .max((u1, u2) -> Integer.compare(Integer.parseInt(u1.getId()), Integer.parseInt(u2.getId())));
        return maxIdUser.map(u -> Integer.parseInt(u.getId()) + 1).orElse(1);
    }

    private void showMessage(String message, Color color) {
        messageLabel.setTextFill(color);
        messageLabel.setText(message);
    }
}
