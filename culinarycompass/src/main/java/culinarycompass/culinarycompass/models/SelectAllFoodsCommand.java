package culinarycompass.culinarycompass.models;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class SelectAllFoodsCommand implements culinarycompass.culinarycompass.interfaces.Command {
    private final ObservableList<Ingredient> masterList;
    private final ListView<Ingredient> foodList;
    private final User user;

    public SelectAllFoodsCommand(ObservableList<Ingredient> masterList, ListView<Ingredient> foodList, User user) {
        this.masterList = masterList;
        this.foodList = foodList;
        this.user = user;
    }

    @Override
    public void execute() {
        masterList.forEach(ingredient -> ingredient.setSelected(true));
        foodList.refresh();
        user.saveSelectedIngredients();
    }
}
