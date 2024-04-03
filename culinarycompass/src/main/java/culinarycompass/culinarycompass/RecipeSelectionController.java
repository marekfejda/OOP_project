package culinarycompass.culinarycompass;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import java.util.List;

public class RecipeSelectionController {

    @FXML
    private VBox recipesBox;

    @FXML
    public void initialize() {
        // Set padding here
        recipesBox.setPadding(new Insets(10, 10, 10, 10));
    }

    public void setRecipes(List<Recipe> recipes) {
        for (Recipe recipe : recipes) {
            Button recipeButton = new Button(recipe.getName());
            recipeButton.setOnAction(event -> showRecipeDetails(recipe));
            recipesBox.getChildren().add(recipeButton);
        }
    }

    private void showRecipeDetails(Recipe recipe) {
        // This method should open the recipe details in a new window or a dialog.
        System.out.println("Recipe selected: " + recipe.getName());
    }
}
