package culinarycompass.culinarycompass.models;

import culinarycompass.culinarycompass.interfaces.object2Str;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Trieda Ingredient predstavuje jednu surovinu v receptoch.
 */
public class Ingredient implements object2Str {
    private final StringProperty name = new SimpleStringProperty(this, "name");
    private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected", false);

    /**
     * Vytvori novu ingredienciu so zadanym menom.
     *
     * @param name Nazov ingrediencie.
     */
    public Ingredient(String name) {
        this.name.set(name);
    }

    /**
     * Vrati vlastnost indikujucu, ci je ingrediencia vybrana.
     *
     * @return BooleanProperty, ktory indikuje vybrany stav.
     */
    public BooleanProperty selectedProperty() {
        return selected;
    }

    /**
     * Ziska nazov ingrediencie.
     *
     * @return Nazov ingrediencie.
     */
    public String getName() {
        return name.get();
    }

    /**
     * Skontroluje, ci je ingrediencia vybrana.
     *
     * @return true, ak je ingrediencia vybrana; inak false.
     */
    public boolean isSelected() {
        return selected.get();
    }

    /**
     * Nastavi, ci je ingrediencia vybrana.
     *
     * @param selected True, ak je vybrana; false, ak nie.
     */
    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    /**
     * Prevedie ingredienciu na textovu reprezentaciu.
     *
     * @return Textova reprezentacia ingrediencie.
     */
    @Override
    public String object2Str() {
        return getName();
    }

    /**
     * Vrati textovu reprezentaciu ingrediencie.
     *
     * @return String reprezentujuci ingredienciu.
     */
    @Override
    public String toString() {
        return object2Str();
    }
}
