package culinarycompass.culinarycompass;

import culinarycompass.culinarycompass.models.Recipe;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class main extends Application {

    private static main instance;

    private List<Recipe> allRecipes;

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

    public static void main(String[] args) { // 'main' method name corrected to lowercase
        launch(args);
    }


    public static main getInstance() {
        return instance;
    }

    public List<Recipe> getAllRecipes() {
        return allRecipes;
    }
}