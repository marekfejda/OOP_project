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
 * Hlavna trieda pre aplikaciu Culinary Compass.
 * Zabezpecuje nacitanie receptov a zobrazenie prihlasovacej obrazovky.
 */
public class main extends Application {

    private static main instance;

    private List<Recipe> allRecipes;

    /**
     * Startuje hlavnu fazu aplikacie a nacitava prihlasenie.
     *
     * @param primaryStage Hlavna scena JavaFX aplikacie.
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
     * Hlavna metoda, ktora spusta aplikaciu.
     *
     * @param args Argumenty prikazoveho riadku.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Vrati instanciu hlavnej triedy aplikacie.
     *
     * @return Instancia triedy main.
     */
    public static main getInstance() {
        return instance;
    }

    /**
     * Vrati vsetky nacitane recepty.
     *
     * @return Zoznam vsetkych receptov.
     */
    public List<Recipe> getAllRecipes() {
        return allRecipes;
    }
}