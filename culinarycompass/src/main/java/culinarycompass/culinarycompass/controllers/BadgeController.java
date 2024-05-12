package culinarycompass.culinarycompass.controllers;

import culinarycompass.culinarycompass.models.Recipe;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

/**
 * Abstraktná trieda poskytujúca funkcionalitu pre zobrazenie odznakov
 * (napr. bezlepkové, vegánske) pre jednotlivé recepty.
 */
public abstract class BadgeController {
    private static final int BADGE_SIZE_PX = 50;

    /**
     * Konfiguruje zobrazenie odznakov na základe špecifikácií receptu.
     *
     * @param recipe             Recept, pre ktorý sa majú zobraziť odznaky.
     * @param glutenFreeBadge    Obrazok pre bezlepkový odznak.
     * @param notGlutenFreeBadge Obrazok pre nebezlepkový odznak.
     * @param veganBadge         Obrazok pre vegánsky odznak.
     * @param notVeganBadge      Obrazok pre nevegánsky odznak.
     */
    protected void configureBadges(Recipe recipe, ImageView glutenFreeBadge, ImageView notGlutenFreeBadge,
                                   ImageView veganBadge, ImageView notVeganBadge) {
        setBadgeSize(glutenFreeBadge, notGlutenFreeBadge, veganBadge, notVeganBadge);

        if (recipe.isGlutenFree()) {
            glutenFreeBadge.setImage(new Image(getClass().getResourceAsStream(
                    "/culinarycompass/culinarycompass/badges/gluten-free.png")));
            glutenFreeBadge.setVisible(true);
            notGlutenFreeBadge.setVisible(false);
        } else {
            notGlutenFreeBadge.setImage(new Image(getClass().getResourceAsStream(
                    "/culinarycompass/culinarycompass/badges/gluten-free-NOT.png")));
            notGlutenFreeBadge.setVisible(true);
            glutenFreeBadge.setVisible(false);
        }

        if (recipe.isVegetarian()) {
            veganBadge.setImage(new Image(getClass().getResourceAsStream(
                    "/culinarycompass/culinarycompass/badges/vegan.png")));
            veganBadge.setVisible(true);
            notVeganBadge.setVisible(false);
        } else {
            notVeganBadge.setImage(new Image(getClass().getResourceAsStream(
                    "/culinarycompass/culinarycompass/badges/vegan-NOT.png")));
            notVeganBadge.setVisible(true);
            veganBadge.setVisible(false);
        }
    }

    /**
     * Nastaví veľkosť odznakov na konštantnú hodnotu.
     *
     * @param badges Pole obsahujúce všetky odznaky, ktorým treba nastaviť veľkosť.
     */
    private void setBadgeSize(ImageView... badges) {
        for (ImageView badge : badges) {
            badge.setFitWidth(BADGE_SIZE_PX);
            badge.setPreserveRatio(true);
        }
    }
}
