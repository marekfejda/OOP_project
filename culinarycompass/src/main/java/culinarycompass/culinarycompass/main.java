package culinarycompass.culinarycompass;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class main extends Application {

    private static main instance; // New line for Singleton pattern

    private List<Recipe> allRecipes;

    @Override
    public void start(Stage primaryStage) {
        instance = this; // Assign the instance for Singleton pattern

        try {
            allRecipes = Recipe.loadRecipesFromFile("recipes.txt");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error properly, perhaps show an alert to the user
        }

        try {
            Parent root = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
            primaryStage.setTitle("Login");
            primaryStage.setScene(new Scene(root, 700, 300));
            primaryStage.setOnShown(event -> root.requestFocus());
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle this error properly as well
        }
    }

    public static void main(String[] args) { // 'main' method name corrected to lowercase
        launch(args);
    }

    // New method for Singleton pattern
    public static main getInstance() {
        return instance;
    }

    // New getter method for allRecipes
    public List<Recipe> getAllRecipes() {
        return allRecipes;
    }
}