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

/**
 * Kontrolér pre zobrazenie detailov receptu a spracovanie lajkov.
 */
public class RecipeDetailsController extends BadgeController {
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

    /**
     * Nastaví aktuálneho používateľa.
     * 
     * @param user Aktuálny používateľ.
     */
    public void setUser(User user) {
        this.currentUser = user;
        loadLikes();
    }

    /**
     * Nastaví detaily receptu na zobrazenie.
     * 
     * @param recipe Aktuálny recept.
     */
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

    /**
     * Aktualizuje ikonu lajku podľa toho, či je recept lajknutý.
     */
    private void updateLikeIcon() {
        if (likedRecipes.contains(currentRecipe.getId())) {
            likeIcon.setImage(new Image(getClass().getResourceAsStream("/culinarycompass/culinarycompass/likeIcons/liked.png")));
        } else {
            likeIcon.setImage(new Image(getClass().getResourceAsStream("/culinarycompass/culinarycompass/likeIcons/likedGhost.png")));
        }
    }

    /**
     * Spracováva lajkovanie a nelajkovanie receptu.
     */
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

    /**
     * Aktualizuje zobrazenie počtu lajkov.
     */
    private void updateLikesDisplay() {
        likesLabel.setText(String.valueOf(currentRecipe.getLikes()));
    }

    /**
     * Konfiguruje odznaky (badges) receptu.
     * 
     * @param recipe Aktuálny recept.
     */
    private void configureBadges(Recipe recipe) {
        super.configureBadges(recipe, glutenFreeBadge, notGlutenFreeBadge, veganBadge, notVeganBadge);
    }

    /**
     * Načíta obrázok receptu podľa jeho ID.
     * 
     * @param recipeId ID receptu.
     */
    private void loadImage(int recipeId) {
        String imagePath = "/culinarycompass/culinarycompass/FoodPictures/jedlo" + recipeId + ".jpg";
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        recipeImageView.setImage(image);
    }

    /**
     * Načíta lajknuté recepty pre aktuálneho používateľa.
     */
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

    /**
     * Zavrie okno s detailmi receptu a vráti sa späť.
     */
    @FXML
    protected void handleBack() {
        nameLabel.getScene().getWindow().hide();
    }
}
