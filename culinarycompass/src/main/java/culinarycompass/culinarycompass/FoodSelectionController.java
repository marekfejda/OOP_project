package culinarycompass.culinarycompass;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FoodSelectionController {

    @FXML
    private ListView<Ingredient> foodList;
    @FXML
    private TextField searchField;

    private ObservableList<Ingredient> masterList = FXCollections.observableArrayList();
    private List<Recipe> allRecipes;

    @FXML
    protected void initialize() {
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
    }

    private Inventory inventory = new Inventory();
    private User user;
    public void setUser(User user) {
        this.user = user;
        // If you want to load the user's inventory when they arrive at the food selection screen
        // loadInventory();
    }

    public void setAllRecipes(List<Recipe> recipes) {
        this.allRecipes = recipes;
    }


    public List<Recipe> printPossibleRecipes() {
        Set<String> selectedIngredients = masterList.stream()
                .filter(Ingredient::isSelected)
                .map(Ingredient::getName)
                .collect(Collectors.toSet());

        List<Recipe> possibleRecipes = allRecipes.stream()
                .filter(recipe -> user.getInventory().getIngredients().containsAll(
                        recipe.getIngredients().stream().map(String::toLowerCase).collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());

        if (!possibleRecipes.isEmpty()) {
            possibleRecipes.forEach(recipe -> {
                System.out.println("Recipe you can cook: " + recipe.getName());
                System.out.println("Ingredients: " + String.join(", ", recipe.getIngredients()));
                System.out.println("Time to cook: " + recipe.getTimeEstimation());
                System.out.println("Portions: " + recipe.getPortions());
                System.out.println("Instructions: " + recipe.getRecipeText());
                System.out.println("----------");
            });
        }

        return possibleRecipes;
    }


    @FXML
    protected void handleConfirm() {
        Set<String> selectedIngredients = masterList.stream()
                .filter(Ingredient::isSelected)
                .map(Ingredient::getName)
                .collect(Collectors.toSet());

        user.updateInventory(selectedIngredients); // Update the user's inventory with selected ingredients

        // Reintroduce the original recipe matching logic here
        List<Recipe> possibleRecipes = allRecipes.stream()
                .filter(recipe -> user.getInventory().getIngredients().containsAll(
                        recipe.getIngredients().stream().map(String::toLowerCase).collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());

        if (!possibleRecipes.isEmpty()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("RecipeSelection.fxml"));
                Parent root = loader.load();

                RecipeSelectionController recipeSelectionController = loader.getController();
                recipeSelectionController.setRecipes(possibleRecipes);

                Stage stage = new Stage();
                stage.setTitle("Select a Recipe");
                stage.setScene(new Scene(root));
                stage.show();

                // Close the current window
                Stage currentStage = (Stage) foodList.getScene().getWindow();
                currentStage.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No recipes found with the selected ingredients.");
        }
    }


}
