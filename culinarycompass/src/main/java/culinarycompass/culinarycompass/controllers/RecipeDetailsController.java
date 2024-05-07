package culinarycompass.culinarycompass.controllers;

import culinarycompass.culinarycompass.models.Recipe;
import culinarycompass.culinarycompass.models.User;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

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
    @FXML
    private ImageView likeIcon;
    private User currentUser;
    private Recipe currentRecipe;
    private Set<Integer> likedRecipes = new HashSet<>();

    public void setUser(User user) {
        this.currentUser = user;
        loadLikes();
    }
    public void setRecipe(Recipe recipe) {
        this.currentRecipe = recipe;
        rootAnchorPane.setPadding(new Insets(10, 10, 10, 10));
        nameLabel.setText(recipe.getName());
        updateLikeIcon();
        ingredientsLabel.setText("Ingredients: " + String.join(", ", recipe.getIngredients()));
        timeAndPortionsLabel.setText("Time: " + recipe.getTimeEstimation() + ", Portions: " + recipe.getPortions());
        recipeTextLabel.setWrapText(true);
        recipeTextLabel.setMaxWidth(420);
        recipeTextLabel.setText("Recipe: " + recipe.getRecipeText());
        likesLabel.setText(String.valueOf(currentRecipe.getLikes()));
        configureBadges(recipe);
        loadImage(recipe.getId());
    }

    private void updateLikeIcon() {
        if (likedRecipes.contains(currentRecipe.getId())) {
            likeIcon.setImage(new Image(getClass().getResourceAsStream("/culinarycompass/culinarycompass/likeIcons/liked.png")));
        } else {
            likeIcon.setImage(new Image(getClass().getResourceAsStream("/culinarycompass/culinarycompass/likeIcons/likedGhost.png")));
        }
    }

    @FXML
    protected void handleLike() {
        if (likedRecipes.contains(currentRecipe.getId())) {
            likedRecipes.remove(currentRecipe.getId());
            currentRecipe.decrementLikes();
            likeIcon.setImage(new Image(getClass().getResourceAsStream("/culinarycompass/culinarycompass/likeIcons/likedGhost.png")));
        } else {
            likedRecipes.add(currentRecipe.getId());
            currentRecipe.incrementLikes();
            likeIcon.setImage(new Image(getClass().getResourceAsStream("/culinarycompass/culinarycompass/likeIcons/liked.png")));
        }
        updateLikesDisplay();
    }

    private void updateLikesDisplay() {
        likesLabel.setText(String.valueOf(currentRecipe.getLikes()));
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

    private void loadLikes() {
        File file = new File("user_likes_" + currentUser.getId() + ".txt");
        likedRecipes.clear();
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextInt()) {
                    likedRecipes.add(scanner.nextInt());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    protected void handleBack() {
        nameLabel.getScene().getWindow().hide();
    }
}
