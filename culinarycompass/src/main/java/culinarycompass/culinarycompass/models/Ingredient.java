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
     * Vytvorí novú ingredienciu so zadaným menom.
     *
     * @param name Názov ingrediencie.
     */
    public Ingredient(String name) {
        this.name.set(name);
    }

    /**
     * Vráti vlastnosť indikujúcu, či je ingrediencia vybraná.
     *
     * @return BooleanProperty, ktorý indikuje vybraný stav.
     */
    public BooleanProperty selectedProperty() {
        return selected;
    }

    /**
     * Získa názov ingrediencie.
     *
     * @return Názov ingrediencie.
     */
    public String getName() {
        return name.get();
    }

    /**
     * Skontroluje, či je ingrediencia vybraná.
     *
     * @return true, ak je ingrediencia vybraná; inak false.
     */
    public boolean isSelected() {
        return selected.get();
    }

    /**
     * Nastaví, či je ingrediencia vybraná.
     *
     * @param selected True, ak je vybraná; false, ak nie.
     */
    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    /**
     * Prevedie ingredienciu na textovú reprezentáciu.
     *
     * @return Textová reprezentácia ingrediencie.
     */
    @Override
    public String object2Str() {
        return getName();
    }

    /**
     * Vráti textovú reprezentáciu ingrediencie.
     *
     * @return String reprezentujúci ingredienciu.
     */
    @Override
    public String toString() {
        return object2Str();
    }
}
