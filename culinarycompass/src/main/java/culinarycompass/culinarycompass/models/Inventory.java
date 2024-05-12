package culinarycompass.culinarycompass.models;

import culinarycompass.culinarycompass.interfaces.object2Str;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Trieda Inventory reprezentuje zoznam vybraných ingrediencií.
 */
public class Inventory implements Serializable, object2Str {

    private final Set<String> ingredients = new HashSet<>();
    private final Set<String> selectedIngredients = new HashSet<>();

    /**
     * Pridá ingredienciu do inventára.
     *
     * @param ingredient Názov ingrediencie.
     */
    public void addIngredient(String ingredient) {
        ingredients.add(ingredient.toLowerCase());
    }

    /**
     * Vráti všetky ingrediencie v inventári.
     *
     * @return Sada názvov ingrediencií.
     */
    public Set<String> getIngredients() {
        return ingredients;
    }

    /**
     * Vymaže všetky ingrediencie z inventára.
     */
    public void clearInventory() {
        ingredients.clear();
    }

    /**
     * Aktualizuje výber ingrediencií.
     *
     * @param selectedIngredientNames Zoznam názvov vybraných ingrediencií.
     */
    public void updateSelectedIngredients(Set<String> selectedIngredientNames) {
        selectedIngredients.clear();
        selectedIngredients.addAll(selectedIngredientNames);
    }

    /**
     * Vráti všetky vybrané ingrediencie.
     *
     * @return Sada vybraných ingrediencií.
     */
    public Set<String> getSelectedIngredients() {
        return new HashSet<>(selectedIngredients);
    }

    /**
     * Prevedie zásoby na textovú reprezentáciu.
     *
     * @return Textová reprezentácia zásob.
     */
    @Override
    public String object2Str() {
        return "Inventory{" +
                "ingredients=" + ingredients +
                '}';
    }

    /**
     * Vráti textovú reprezentáciu zásob.
     *
     * @return String reprezentujúci zásoby.
     */
    @Override
    public String toString() {
        return object2Str();
    }
}
