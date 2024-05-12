package culinarycompass.culinarycompass.controllers;

import culinarycompass.culinarycompass.main;
import culinarycompass.culinarycompass.models.User;
import culinarycompass.culinarycompass.exceptions.AuthenticationException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Kontroler spravujuci prihlasovanie a registraciu uzivatelov.
 */
public class LoginController extends culinarycompass.culinarycompass.controllers.BaseController {

    @FXML
    private TextField nicknameField;
    @FXML
    private PasswordField passwordField;
    private User authenticatedUser;

    /**
     * Overuje uzivatelske meno a heslo a vracia autentifikovaneho uzivatela.
     *
     * @param nickname Uzivatelske meno.
     * @param password Heslo.
     * @return Autentifikovany uzivatel.
     * @throws AuthenticationException Vynimka, ktora sa vyvola, ak je uzivatelske meno alebo heslo nespravne.
     */
    private User authenticate(String nickname, String password) throws AuthenticationException {
        List<User> users = loadUsers();
        return users.stream()
                .filter(u -> u.getNickname().equals(nickname) && u.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new AuthenticationException("Incorrect nickname or password."));
    }

    /**
     * Spracuva prihlasenie uzivatela.
     */
    @FXML
    protected void handleLogin() {
        String nickname = nicknameField.getText();
        String password = passwordField.getText();
        try {
            User user = authenticate(nickname, password);
            authenticatedUser = user;
            setMessageTextColor(Color.GREEN);
            showMessage("You have successfully logged in!");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/culinarycompass/culinarycompass/FoodSelection.fxml"));
            Parent root = loader.load();
            FoodSelectionController foodSelectionController = loader.getController();
            foodSelectionController.setUser(authenticatedUser);

            main application = main.getInstance();
            foodSelectionController.setAllRecipes(application.getAllRecipes());

            authenticatedUser.loadSelectedIngredients();

            Stage stage = new Stage();
            stage.setTitle("Select Your Food/Groceries");
            stage.setScene(new Scene(root, 700, 300));
            stage.show();

            ((Stage) nicknameField.getScene().getWindow()).close();
        } catch (AuthenticationException e) {
            setMessageTextColor(Color.RED);
            showMessage(e.getMessage());
        } catch (IOException e) {
            setMessageTextColor(Color.RED);
            showMessage("Failed to load the food selection screen.");
            e.printStackTrace();
        }
    }

    /**
     * Spracuva registraciu noveho uzivatela.
     */
    @FXML
    protected void handleRegister() {
        String nickname = nicknameField.getText();
        String password = passwordField.getText();
        if (nickname.isEmpty() || password.isEmpty()) {
            setMessageTextColor(Color.RED);
            showMessage("Nickname and password cannot be empty.");
            return;
        }
        if (userExists(nickname)) {
            setMessageTextColor(Color.RED);
            showMessage("User with this nickname already exists.");
        } else {
            if (registerNewUser(nickname, password)) {
                setMessageTextColor(Color.GREEN);
                showMessage("User has been registered successfully!");
            } else {
                setMessageTextColor(Color.RED);
                showMessage("There was an error registering the user.");
            }
        }
    }

    /**
     * Kontroluje, ci uz existuje uzivatel so zadanym uzivatelskym menom.
     *
     * @param nickname Uzivatelske meno.
     * @return {@code true}, ak uz existuje.
     */
    private boolean userExists(String nickname) {
        List<User> users = loadUsers();
        return users.stream().anyMatch(u -> u.getNickname().equalsIgnoreCase(nickname));
    }

    /**
     * Registruje noveho uzivatela s danym menom a heslom.
     *
     * @param nickname Uzivatelske meno.
     * @param password Heslo.
     * @return {@code true}, ak sa uzivatel uspesne zaregistruje.
     */
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

    /**
     * Nacita uzivatelov zo suboru a vracia ich zoznam.
     *
     * @return Zoznam uzivatelov.
     */
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

    /**
     * Generuje nove ID pre uzivatela.
     *
     * @return Nove uzivatelske ID.
     */
    private int generateNewUserId() {
        Optional<User> maxIdUser = loadUsers().stream()
                .max((u1, u2) -> Integer.compare(Integer.parseInt(u1.getId()), Integer.parseInt(u2.getId())));
        return maxIdUser.map(u -> Integer.parseInt(u.getId()) + 1).orElse(1);
    }
}
