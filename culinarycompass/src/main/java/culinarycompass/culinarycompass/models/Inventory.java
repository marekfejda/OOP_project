package culinarycompass.culinarycompass.models;

import culinarycompass.culinarycompass.interfaces.object2Str;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Inventory implements Serializable, object2Str {

    private final Set<String> ingredients = new HashSet<>();
    private final Set<String> selectedIngredients = new HashSet<>();

    public void addIngredient(String ingredient) {
        ingredients.add(ingredient.toLowerCase());
    }

    public Set<String> getIngredients() {
        return ingredients;
    }

    public void clearInventory() {
        ingredients.clear();
    }

    public void updateSelectedIngredients(Set<String> selectedIngredientNames) {
        selectedIngredients.clear();
        selectedIngredients.addAll(selectedIngredientNames);
    }

    public Set<String> getSelectedIngredients() {
        return new HashSet<>(selectedIngredients);
    }

    @Override
    public String object2Str() {
        return "Inventory{" +
                "ingredients=" + ingredients +
                '}';
    }

    @Override
    public String toString() {
        return object2Str();
    }
}
