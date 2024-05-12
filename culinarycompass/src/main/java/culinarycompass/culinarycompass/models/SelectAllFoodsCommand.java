package culinarycompass.culinarycompass.models;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 * Prikaz na vyber vsetkych potravin.
 * Tato trieda implementuje prikaz na oznacenie vsetkych potravin zo zoznamu ako vybrane.
 */
public class SelectAllFoodsCommand implements culinarycompass.culinarycompass.interfaces.Command {
    private final ObservableList<Ingredient> masterList;
    private final ListView<Ingredient> foodList;
    private final User user;

    /**
     * Konstruktor triedy SelectAllFoodsCommand.
     *
     * @param masterList Zoznam vsetkych prisad.
     * @param foodList Zoznam prisad na zobrazenie.
     * @param user Pouzivatel, ktory vykonava tento prikaz.
     */
    public SelectAllFoodsCommand(ObservableList<Ingredient> masterList, ListView<Ingredient> foodList, User user) {
        this.masterList = masterList;
        this.foodList = foodList;
        this.user = user;
    }

    /**
     * Oznaci vsetky prisady v masterList ako vybrane a obnovi zobrazenie.
     * Taktiez ulozi vybrane prisady pre pouzivatela.
     */
    @Override
    public void execute() {
        masterList.forEach(ingredient -> ingredient.setSelected(true));
        foodList.refresh();
        user.saveSelectedIngredients();
    }
}
