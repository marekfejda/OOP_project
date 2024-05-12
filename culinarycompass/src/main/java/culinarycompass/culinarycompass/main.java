package culinarycompass.culinarycompass;

import culinarycompass.culinarycompass.models.Recipe;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Hlavná trieda pre aplikáciu Culinary Compass.
 * Zabezpečuje načítanie receptov a zobrazenie prihlasovacej obrazovky.
 */
public class main extends Application {

    private static main instance;

    private List<Recipe> allRecipes;

    /**
     * Štartuje hlavnú fázu aplikácie a načítava prihlásenie.
     *
     * @param primaryStage Hlavná scéna JavaFX aplikácie.
     */
    @Override
    public void start(Stage primaryStage) {
        instance = this;

        try {
            allRecipes = Recipe.loadRecipesFromFile("recipes.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Parent root = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
            primaryStage.setTitle("Login");
            primaryStage.setScene(new Scene(root, 700, 300));
            primaryStage.setOnShown(event -> root.requestFocus());
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Hlavná metóda, ktorá spúšťa aplikáciu.
     *
     * @param args Argumenty príkazového riadku.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Vráti inštanciu hlavnej triedy aplikácie.
     *
     * @return Inštancia triedy main.
     */
    public static main getInstance() {
        return instance;
    }

    /**
     * Vráti všetky načítané recepty.
     *
     * @return Zoznam všetkých receptov.
     */
    public List<Recipe> getAllRecipes() {
        return allRecipes;
    }
}