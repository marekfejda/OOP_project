package culinarycompass.culinarycompass.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Inventory implements Serializable {

    private final Set<String> ingredients = new HashSet<>();
    private final Set<String> selectedIngredients = new HashSet<>();

    public void addIngredient(String ingredient) {
        ingredients.add(ingredient.toLowerCase()); // Store all ingredients in lowercase to avoid case sensitivity issues
    }

    public boolean hasIngredient(String ingredient) {
        return ingredients.contains(ingredient.toLowerCase());
    }

    public void removeIngredient(String ingredient) {
        ingredients.remove(ingredient.toLowerCase());
    }

    public Set<String> getIngredients() {
        return ingredients;
    }

    public void clearInventory() {
        ingredients.clear();
    }

    // This method could be used to save the current state of inventory if needed
    public void saveInventory() {
        // Implement saving logic to file or database
    }

    // This method could be used to load the inventory's state when the application starts
    public void loadInventory() {
        // Implement loading logic from file or database
    }

    public void updateSelectedIngredients(Set<String> selectedIngredientNames) {
        selectedIngredients.clear();
        selectedIngredients.addAll(selectedIngredientNames);
    }

    public Set<String> getSelectedIngredients() {
        return new HashSet<>(selectedIngredients);
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "ingredients=" + ingredients +
                '}';
    }
}
