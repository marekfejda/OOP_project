package culinarycompass.culinarycompass;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.util.Objects;

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
    @FXML
    private ImageView glutenFreeBadge;
    @FXML
    private ImageView notGlutenFreeBadge;
    @FXML
    private ImageView veganBadge;
    @FXML
    private ImageView notVeganBadge;



    private final int badge_size_px = 50;
    public void setRecipe(Recipe recipe) {
        rootVBox.setPadding(new Insets(10, 10, 10, 10));
        nameLabel.setText("Name: " + recipe.getName());
        ingredientsLabel.setText("Ingredients: " + String.join(", ", recipe.getIngredients()));
        timeEstimationLabel.setText("Time: " + recipe.getTimeEstimation());
        portionsLabel.setText("Portions: " + recipe.getPortions());
        recipeTextLabel.setText("Recipe: " + recipe.getRecipeText());

        try {
            glutenFreeBadge.setFitWidth(badge_size_px);
            glutenFreeBadge.setPreserveRatio(true);
            notGlutenFreeBadge.setFitWidth(badge_size_px);
            notGlutenFreeBadge.setPreserveRatio(true);
            veganBadge.setFitWidth(badge_size_px);
            veganBadge.setPreserveRatio(true);
            notVeganBadge.setFitWidth(badge_size_px);
            notVeganBadge.setPreserveRatio(true);

            if (recipe.isGlutenFree()) {
                Image glutenFreeImage = new Image(getClass().getResourceAsStream("/culinarycompass/culinarycompass/badges/gluten-free.png"));
                glutenFreeBadge.setImage(glutenFreeImage);
                glutenFreeBadge.setVisible(true);
                notGlutenFreeBadge.setVisible(false);
            } else {
                Image notGlutenFreeImage = new Image(getClass().getResourceAsStream("/culinarycompass/culinarycompass/badges/gluten-free-NOT.png"));
                notGlutenFreeBadge.setImage(notGlutenFreeImage);
                notGlutenFreeBadge.setVisible(true);
                glutenFreeBadge.setVisible(false);
            }
            if (recipe.isVegetarian()) {
                Image veganImage = new Image(getClass().getResourceAsStream("/culinarycompass/culinarycompass/badges/vegan.png"));
                veganBadge.setImage(veganImage);
                veganBadge.setVisible(true);
                notVeganBadge.setVisible(false);
            } else {
                Image notVeganImage = new Image(getClass().getResourceAsStream("/culinarycompass/culinarycompass/badges/vegan-NOT.png"));
                notVeganBadge.setImage(notVeganImage);
                notVeganBadge.setVisible(true);
                veganBadge.setVisible(false);
            }
        } catch (Exception e) {
            e.printStackTrace(); // This will print any errors loading the images
        }

    }

    @FXML
    protected void handleBack() {
        // Close the recipe details stage
        nameLabel.getScene().getWindow().hide();
    }
}
