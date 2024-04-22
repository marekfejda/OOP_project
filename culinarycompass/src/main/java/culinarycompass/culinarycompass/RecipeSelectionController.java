package culinarycompass.culinarycompass;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class RecipeSelectionController {

    @FXML
    private VBox recipesBox;
    @FXML
    private Button backButton;
    private User user;

    @FXML
    public void initialize() {
        recipesBox.setPadding(new Insets(10, 10, 10, 10));
    }

    public void setRecipes(List<Recipe> recipes) {
        recipesBox.getChildren().clear();
        for (Recipe recipe : recipes) {
            Button recipeButton = new Button(recipe.getName());
            recipeButton.setOnAction(event -> showRecipeDetails(recipe));
            recipesBox.getChildren().add(recipeButton);
        }
    }

    // Method to set the user in the RecipeSelectionController
    public void setUser(User user) {
        this.user = user;
        System.out.println("User set: " + user);
    }

    @FXML
    protected void handleBack() {
        try {
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("FoodSelection.fxml"));
            Parent root = loader.load();

            FoodSelectionController foodSelectionController = loader.getController();
            foodSelectionController.setUser(this.user);

            Stage stage = new Stage();
            stage.setTitle("Select Ingredients");
            stage.setScene(new Scene(root));
            stage.setWidth(715);
            stage.setHeight(340);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showRecipeDetails(Recipe recipe) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RecipeDetails.fxml"));
            Parent root = loader.load();

            RecipeDetailsController recipeDetailsController = loader.getController();
            recipeDetailsController.setRecipe(recipe);

            Stage stage = new Stage();
            stage.setTitle(recipe.getName());
            stage.setScene(new Scene(root));
            stage.setWidth(715);
            stage.setHeight(340);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleSignOut() {
        if (user != null) {
            user.saveSelectedIngredients(); // Save the selected ingredients
        }

        try {
            // Load login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginScreen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root, 700, 300));
            stage.show();

            // Close the current window (the recipe selection screen)
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
