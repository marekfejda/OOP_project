package culinarycompass.culinarycompass.models;

import culinarycompass.culinarycompass.interfaces.object2Str;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Trieda Inventory reprezentuje zoznam vybranych ingrediencii.
 */
public class Inventory implements Serializable, object2Str {

    private final Set<String> ingredients = new HashSet<>();
    private final Set<String> selectedIngredients = new HashSet<>();

    /**
     * Prida ingredienciu do inventara.
     *
     * @param ingredient Nazov ingrediencie.
     */
    public void addIngredient(String ingredient) {
        ingredients.add(ingredient.toLowerCase());
    }

    /**
     * Vrati vsetky ingrediencie v inventari.
     *
     * @return Sada nazvov ingrediencii.
     */
    public Set<String> getIngredients() {
        return ingredients;
    }

    /**
     * Vymaze vsetky ingrediencie z inventara.
     */
    public void clearInventory() {
        ingredients.clear();
    }

    /**
     * Aktualizuje vyber ingrediencii.
     *
     * @param selectedIngredientNames Zoznam nazvov vybranych ingrediencii.
     */
    public void updateSelectedIngredients(Set<String> selectedIngredientNames) {
        selectedIngredients.clear();
        selectedIngredients.addAll(selectedIngredientNames);
    }

    /**
     * Vrati vsetky vybrane ingrediencie.
     *
     * @return Sada vybranych ingrediencii.
     */
    public Set<String> getSelectedIngredients() {
        return new HashSet<>(selectedIngredients);
    }

    /**
     * Prevedie zasoby na textovu reprezentaciu.
     *
     * @return Textova reprezentacia zasob.
     */
    @Override
    public String object2Str() {
        return "Inventory{" +
                "ingredients=" + ingredients +
                '}';
    }

    /**
     * Vrati textovu reprezentaciu zasob.
     *
     * @return String reprezentujuci zasoby.
     */
    @Override
    public String toString() {
        return object2Str();
    }
}
