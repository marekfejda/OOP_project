package culinarycompass.culinarycompass.controllers;

import culinarycompass.culinarycompass.interfaces.Command;
import culinarycompass.culinarycompass.models.Ingredient;
import culinarycompass.culinarycompass.models.Recipe;
import culinarycompass.culinarycompass.models.SelectAllFoodsCommand;
import culinarycompass.culinarycompass.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Kontroler spravujuci vyber potravin uzivatelom.
 */
public class FoodSelectionController {

    @FXML
    private ListView<Ingredient> foodList;
    @FXML
    private TextField searchField;
    @FXML
    private Label messageLabel;

    private ObservableList<Ingredient> masterList = FXCollections.observableArrayList();
    private List<Recipe> allRecipes;
    private User user;

    /**
     * Inicializuje kontroler, naplna zoznam potravin a nastavuje listener na vyhladavacie pole.
     */
    @FXML
    protected void initialize() {
        messageLabel.setManaged(false);
        messageLabel.setVisible(false);
        masterList.addAll(
                new Ingredient("Potatoes"), new Ingredient("Sheep Cheese"), new Ingredient("Flour"), new Ingredient("Bacon"),
                new Ingredient("Sauerkraut"), new Ingredient("Smoked Meat"), new Ingredient("Mushrooms"), new Ingredient("Onion"),
                new Ingredient("Garlic"), new Ingredient("Paprika"), new Ingredient("Bay Leaf"), new Ingredient("Marjoram"),
                new Ingredient("Lard"),
                new Ingredient("Pork"), new Ingredient("Sour Cream"),
                new Ingredient("Oil"), new Ingredient("Egg"),
                new Ingredient("Salt"), new Ingredient("Pepper")
        );

        foodList.setItems(masterList);
        foodList.setCellFactory(CheckBoxListCell.forListView(Ingredient::selectedProperty));

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<Ingredient> filteredList = FXCollections.observableArrayList();
            if (newValue == null || newValue.isEmpty()) {
                foodList.setItems(masterList);
            } else {
                for (Ingredient item : masterList) {
                    if (item.getName().toLowerCase().contains(newValue.toLowerCase())) {
                        filteredList.add(item);
                    }
                }
                foodList.setItems(filteredList);
            }
        });
        populateAllRecipes();
    }

    /**
     * Nacitava vsetky recepty zo suboru.
     */
    private void populateAllRecipes() {
        try {
            this.allRecipes = Recipe.loadRecipesFromFile("recipes.txt");
        } catch (IOException e) {
            e.printStackTrace();
            this.allRecipes = new ArrayList<>();
        }
    }

    /**
     * Nastavuje aktualneho uzivatela a opatovne aplikuje vybery ingrediencii.
     *
     * @param user Uzivatel, ktory je prihlaseny.
     */
    public void setUser(User user) {
        this.user = user;
        this.user.loadSelectedIngredients();
        reapplyIngredientSelections();

        if (user instanceof User) { //RTTI - Run Time Type Identification na kontrolu typu objektu
            System.out.println("RTTI: Logged in as: " + user.getNickname());
        }
    }

    /**
     * Opatovne aplikuje vybery ingrediencii z inventara uzivatela.
     */
    private void reapplyIngredientSelections() {
        if (user != null && user.getInventory() != null) {
            Set<String> selectedIngredientNames = user.getInventory().getSelectedIngredients();
            masterList.forEach(ingredient ->
                    ingredient.setSelected(selectedIngredientNames.contains(ingredient.getName()))
            );
            foodList.refresh();
        }
    }

    /**
     * Nastavuje vsetky recepty.
     *
     * @param recipes Zoznam receptov.
     */
    public void setAllRecipes(List<Recipe> recipes) {
        this.allRecipes = recipes;
    }

    /**
     * Potvrdzuje vyber ingrediencii, aktualizuje inventar uzivatela a nacitava mozne recepty.
     */
    @FXML
    protected void handleConfirm() {
        Set<String> selectedIngredients = masterList.stream()
                .filter(Ingredient::isSelected)
                .map(Ingredient::getName)
                .collect(Collectors.toSet());
        user.updateInventory(selectedIngredients);
        user.getInventory().updateSelectedIngredients(selectedIngredients);
        List<Recipe> possibleRecipes = allRecipes.stream()
                .filter(recipe -> user.getInventory().getIngredients().containsAll(
                        recipe.getIngredients().stream().map(String::toLowerCase).collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());

        if (!possibleRecipes.isEmpty()) {
            try {
                showMessage("");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/culinarycompass/culinarycompass/RecipeSelection.fxml"));
                Parent root = loader.load();

                RecipeSelectionController recipeSelectionController = loader.getController();
                recipeSelectionController.setRecipes(possibleRecipes);
                recipeSelectionController.setUser(this.user);

                Stage stage = new Stage();
                stage.setTitle("Select a Recipe");
                stage.setScene(new Scene(root));
                stage.setWidth(715);
                stage.setHeight(340);
                stage.show();

                Stage currentStage = (Stage) foodList.getScene().getWindow();
                currentStage.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showMessage("No recipes found with the selected ingredients.");
        }
    }

    /**
     * Odhlasuje uzivatela a nacitava prihlasovaciu obrazovku.
     */
    @FXML
    protected void handleSignOut() {
        if (user != null) {
            user.saveSelectedIngredients();
            user.saveToFile(); // SERIALIZACIA
            System.out.println("User data has been saved. Serialization");
        }
        try {
            // Load login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/culinarycompass/culinarycompass/loginScreen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root, 700, 300));
            stage.show();

            Stage currentStage = (Stage) foodList.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showMessage("Failed to load the login screen.");
        }
    }

    /**
     * Zobrazuje spravu.
     *
     * @param message Text spravy.
     */
    private void showMessage(String message) {
        if (message == null || message.isEmpty()) {
            messageLabel.setText("");
            messageLabel.setManaged(false);
            messageLabel.setVisible(false);
        } else {
            messageLabel.setText(message);
            messageLabel.setManaged(true);
            messageLabel.setVisible(true);
        }
    }

    /**
     * Oznacuje vsetky potraviny ako vybrane.
     */
    @FXML
    protected void selectAllFoods() {
        Command selectAllFoodsCommand = new SelectAllFoodsCommand(masterList, foodList, user);
        selectAllFoodsCommand.execute();
    }
}
