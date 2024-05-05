package culinarycompass.culinarycompass;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.util.Objects;

public class RecipeDetailsController {
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private Label nameLabel;
    @FXML
    private Label ingredientsLabel;
    @FXML
    private Label timeAndPortionsLabel;
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
    @FXML
    private ImageView recipeImageView;
    @FXML
    private Label likesLabel;


    public void setRecipe(Recipe recipe) {
        rootAnchorPane.setPadding(new Insets(10, 10, 10, 10));
        nameLabel.setText(recipe.getName());
        ingredientsLabel.setText("Ingredients: " + String.join(", ", recipe.getIngredients()));
        timeAndPortionsLabel.setText("Time: " + recipe.getTimeEstimation() + ", Portions: " + recipe.getPortions());

        recipeTextLabel.setWrapText(true);
        recipeTextLabel.setMaxWidth(420);
        recipeTextLabel.setText("Recipe: " + recipe.getRecipeText());
        likesLabel.setText("Likes: " + recipe.getLikes());

        configureBadges(recipe);
        loadImage(recipe.getId());
    }

    private void configureBadges(Recipe recipe) {
        int badge_size_px = 50;
        glutenFreeBadge.setFitWidth(badge_size_px);
        glutenFreeBadge.setPreserveRatio(true);
        notGlutenFreeBadge.setFitWidth(badge_size_px);
        notGlutenFreeBadge.setPreserveRatio(true);
        veganBadge.setFitWidth(badge_size_px);
        veganBadge.setPreserveRatio(true);
        notVeganBadge.setFitWidth(badge_size_px);
        notVeganBadge.setPreserveRatio(true);

        if (recipe.isGlutenFree()) {
            glutenFreeBadge.setImage(new Image(getClass().getResourceAsStream("/culinarycompass/culinarycompass/badges/gluten-free.png")));
            glutenFreeBadge.setVisible(true);
            notGlutenFreeBadge.setVisible(false);
        } else {
            notGlutenFreeBadge.setImage(new Image(getClass().getResourceAsStream("/culinarycompass/culinarycompass/badges/gluten-free-NOT.png")));
            notGlutenFreeBadge.setVisible(true);
            glutenFreeBadge.setVisible(false);
        }
        if (recipe.isVegetarian()) {
            veganBadge.setImage(new Image(getClass().getResourceAsStream("/culinarycompass/culinarycompass/badges/vegan.png")));
            veganBadge.setVisible(true);
            notVeganBadge.setVisible(false);
        } else {
            notVeganBadge.setImage(new Image(getClass().getResourceAsStream("/culinarycompass/culinarycompass/badges/vegan-NOT.png")));
            notVeganBadge.setVisible(true);
            veganBadge.setVisible(false);
        }
    }

    private void loadImage(int recipeId) {
        String imagePath = "/culinarycompass/culinarycompass/FoodPictures/jedlo" + recipeId + ".jpg";
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        recipeImageView.setImage(image);
    }


    @FXML
    protected void handleBack() {
        // Close the recipe details stage
        nameLabel.getScene().getWindow().hide();
    }
}
