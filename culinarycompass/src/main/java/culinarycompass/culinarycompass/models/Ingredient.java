package culinarycompass.culinarycompass.models;

import culinarycompass.culinarycompass.interfaces.object2Str;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Ingredient implements object2Str {
    private final StringProperty name = new SimpleStringProperty(this, "name");
    private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected", false);

    public Ingredient(String name) {
        this.name.set(name);
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public String getName() {
        return name.get();
    }

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    @Override
    public String object2Str() {
        return getName();
    }

    @Override
    public String toString() {
        return object2Str();
    }
}
