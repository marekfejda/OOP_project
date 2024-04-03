package culinarycompass.culinarycompass;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class RecipeDetailsController {
    @FXML
    private VBox rootVBox;
    @FXML
    private Label nameLabel;
    @FXML
    private Label ingredientsLabel;
    @FXML
    private Label timeEstimationLabel;
    @FXML
    private Label portionsLabel;
    @FXML
    private Label recipeTextLabel;

    public void setRecipe(Recipe recipe) {
        rootVBox.setPadding(new Insets(10, 10, 10, 10));
        nameLabel.setText("Name: " + recipe.getName());
        ingredientsLabel.setText("Ingredients: " + String.join(", ", recipe.getIngredients()));
        timeEstimationLabel.setText("Time: " + recipe.getTimeEstimation());
        portionsLabel.setText("Portions: " + recipe.getPortions());
        recipeTextLabel.setText("Recipe: " + recipe.getRecipeText());
    }

    @FXML
    protected void handleBack() {
        // Close the recipe details stage
        nameLabel.getScene().getWindow().hide();
    }
}
