package culinarycompass.culinarycompass.controllers;

import culinarycompass.culinarycompass.models.Recipe;
import culinarycompass.culinarycompass.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Kontroler pre vyber a zobrazenie zoznamu receptov.
 */
public class RecipeSelectionController {

    @FXML
    private VBox recipesBox;
    @FXML
    private Button backButton;
    private User user;

    /**
     * Inicializuje ovladacie prvky na obrazovke vyberu receptov.
     */
    @FXML
    public void initialize() {
        recipesBox.setPadding(new Insets(10, 10, 10, 10));
    }

    /**
     * Nastavi zoznam receptov na zobrazenie.
     *
     * @param recipes Zoznam receptov.
     */
    public void setRecipes(List<Recipe> recipes) {
        recipesBox.getChildren().clear();
        for (Recipe recipe : recipes) {
            HBox recipeItem = new HBox(10);
            recipeItem.setPadding(new Insets(5, 0, 5, 0));

            ImageView imageView = new ImageView();
            imageView.setFitWidth(100);
            imageView.setFitHeight(60);
            imageView.setPreserveRatio(true);

            loadImage(imageView, recipe.getId());

            Button recipeButton = new Button(recipe.getName());
            recipeButton.setOnAction(event -> showRecipeDetails(recipe));
            recipeButton.setMinWidth(560);
            recipeButton.setMinHeight(60);

            recipeItem.getChildren().addAll(imageView, recipeButton);
            recipesBox.getChildren().add(recipeItem);
        }
    }

    /**
     * Nastavi aktualneho pouzivatela.
     *
     * @param user Aktualny pouzivatel.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Vrati sa spat na obrazovku vyberu potravin.
     */
    @FXML
    protected void handleBack() {
        try {
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/culinarycompass/culinarycompass/FoodSelection.fxml"));
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

    /**
     * Zobrazi detaily vybraneho receptu.
     *
     * @param recipe Recept, ktory sa ma zobrazit.
     */
    private void showRecipeDetails(Recipe recipe) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/culinarycompass/culinarycompass/RecipeDetails.fxml"));
            Parent root = loader.load();

            RecipeDetailsController recipeDetailsController = loader.getController();
            recipeDetailsController.setRecipe(recipe);
            recipeDetailsController.setUser(this.user);

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

    /**
     * Spracovava odhlasenie pouzivatela.
     */
    @FXML
    protected void handleSignOut() {
        if (user != null) {
            user.saveSelectedIngredients();
            user.saveToFile(); // SERIALIZACIA
            System.out.println("User data has been saved. Serialization");
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/culinarycompass/culinarycompass/loginScreen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root, 700, 300));
            stage.show();

            Stage currentStage = (Stage) backButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Nacita obrazok receptu do zadaneho ImageView podla ID receptu.
     *
     * @param imageView Komponent na zobrazenie obrazka.
     * @param recipeId  ID receptu.
     */
    private void loadImage(ImageView imageView, int recipeId) {
        String imagePath = "/culinarycompass/culinarycompass/FoodPictures/jedlo" + recipeId + ".jpg";
        try {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            imageView.setImage(image);
        } catch (NullPointerException e) {
            System.out.println("Image not found: jedlo" + recipeId + ".jpg");
            imageView.setImage(null);
        }
    }
}
