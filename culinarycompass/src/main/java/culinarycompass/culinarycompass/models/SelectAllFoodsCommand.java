package culinarycompass.culinarycompass.models;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 * Príkaz na výber všetkých potravín.
 * Táto trieda implementuje príkaz na označenie všetkých potravín zo zoznamu ako vybrané.
 */
public class SelectAllFoodsCommand implements culinarycompass.culinarycompass.interfaces.Command {
    private final ObservableList<Ingredient> masterList;
    private final ListView<Ingredient> foodList;
    private final User user;

    /**
     * Konštruktor triedy SelectAllFoodsCommand.
     *
     * @param masterList Zoznam všetkých prísad.
     * @param foodList Zoznam prísad na zobrazenie.
     * @param user Používateľ, ktorý vykonáva tento príkaz.
     */
    public SelectAllFoodsCommand(ObservableList<Ingredient> masterList, ListView<Ingredient> foodList, User user) {
        this.masterList = masterList;
        this.foodList = foodList;
        this.user = user;
    }

    /**
     * Označí všetky prísady v masterList ako vybrané a obnoví zobrazenie.
     * Taktiež uloží vybrané prísady pre používateľa.
     */
    @Override
    public void execute() {
        masterList.forEach(ingredient -> ingredient.setSelected(true));
        foodList.refresh();
        user.saveSelectedIngredients();
    }
}
