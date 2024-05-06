package culinarycompass.culinarycompass.controllers;

import culinarycompass.culinarycompass.models.Ingredient;
import culinarycompass.culinarycompass.models.Inventory;
import culinarycompass.culinarycompass.models.Recipe;
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
    private Inventory inventory = new Inventory();


    @FXML
    protected void initialize() {
        messageLabel.setManaged(false);
        messageLabel.setVisible(false);
        masterList.addAll(
                new Ingredient("Apples"), new Ingredient("Bread"), new Ingredient("Cheese"),
                new Ingredient("Milk"), new Ingredient("Eggs"), new Ingredient("Tomatoes"),
                new Ingredient("Potatoes"), new Ingredient("Carrots"), new Ingredient("Chicken"),
                new Ingredient("Beef"), new Ingredient("Pasta"), new Ingredient("Rice"),
                new Ingredient("Lettuce"), new Ingredient("Bananas"), new Ingredient("Oranges"),
                new Ingredient("Yogurt"), new Ingredient("Butter"), new Ingredient("Flour"),
                new Ingredient("Sugar"), new Ingredient("Salt"), new Ingredient("Pepper"),
                new Ingredient("Garlic"), new Ingredient("Onions"), new Ingredient("Lemons"),
                new Ingredient("Limes"), new Ingredient("Cucumbers"), new Ingredient("Bell Peppers"),
                new Ingredient("Broccoli"), new Ingredient("Mushrooms"), new Ingredient("Spinach"),
                new Ingredient("Grapes"), new Ingredient("Strawberries"), new Ingredient("Blueberries"),
                new Ingredient("Almonds"), new Ingredient("Peanuts"), new Ingredient("Olive Oil"),
                new Ingredient("Vinegar"), new Ingredient("Soy Sauce"), new Ingredient("Honey"),
                new Ingredient("Maple Syrup")
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

    private void populateAllRecipes() {
        try {
            this.allRecipes = Recipe.loadRecipesFromFile("recipes.txt");
        } catch (IOException e) {
            e.printStackTrace();
            // You could log this exception or provide some fallback behavior here
            // For example, initializing allRecipes as an empty list to avoid NullPointerException
            this.allRecipes = new ArrayList<>();
        }
    }


    public void setUser(User user) {
        this.user = user;
//        System.out.println("User set: " + user);

        // Load the user's selected ingredients
        this.user.loadSelectedIngredients();

        // Reapply ingredient selections now that user and their selections are guaranteed to be set
        reapplyIngredientSelections();
    }


    private void reapplyIngredientSelections() {
        if (user != null && user.getInventory() != null) {
            Set<String> selectedIngredientNames = user.getInventory().getSelectedIngredients();

            masterList.forEach(ingredient ->
                    ingredient.setSelected(selectedIngredientNames.contains(ingredient.getName()))
            );

            // Refresh the ListView to update the UI
            foodList.refresh();
        }
    }


    public void setAllRecipes(List<Recipe> recipes) {
        this.allRecipes = recipes;
    }

    @FXML
    protected void handleConfirm() {
        Set<String> selectedIngredients = masterList.stream()
                .filter(Ingredient::isSelected)
                .map(Ingredient::getName)
                .collect(Collectors.toSet());
        user.updateInventory(selectedIngredients);
        user.getInventory().updateSelectedIngredients(selectedIngredients);
        // Reintroduce the original recipe matching logic here
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

                // Close the current window
                Stage currentStage = (Stage) foodList.getScene().getWindow();
                currentStage.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showMessage("No recipes found with the selected ingredients.");
        }
    }


    @FXML
    protected void handleSignOut() {
        if (user != null) {
            user.saveSelectedIngredients(); // Save the selected ingredients
        }
        try {
            // Load login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/culinarycompass/culinarycompass/loginScreen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root, 700, 300));
            stage.show();

            // Close the current window (the food selection screen)
            Stage currentStage = (Stage) foodList.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showMessage("Failed to load the login screen.");
        }
    }


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

    @FXML
    protected void selectAllFoods() {
        // Loop through the masterList and set all ingredients to selected
        masterList.forEach(ingredient -> ingredient.setSelected(true));

        // Refresh the ListView to update the UI
        foodList.refresh();
        user.saveSelectedIngredients();
    }
}
