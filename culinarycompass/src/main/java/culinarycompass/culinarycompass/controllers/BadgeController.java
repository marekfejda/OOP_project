package culinarycompass.culinarycompass.controllers;

import culinarycompass.culinarycompass.models.Recipe;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

/**
 * Abstraktna trieda poskytujuca funkcionalitu pre zobrazenie odznakov
 * (napr. bezlepkove, veganske) pre jednotlive recepty.
 */
public abstract class BadgeController {
    private static final int BADGE_SIZE_PX = 50;

    /**
     * Konfiguruje zobrazenie odznakov na zaklade specifikacii receptu.
     *
     * @param recipe             Recept, pre ktory sa maju zobrazit odznaky.
     * @param glutenFreeBadge    Obrazok pre bezlepkovy odznak.
     * @param notGlutenFreeBadge Obrazok pre nebezlepkovy odznak.
     * @param veganBadge         Obrazok pre vegansky odznak.
     * @param notVeganBadge      Obrazok pre nevegansky odznak.
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
     * Nastavi velkost odznakov na konstantnu hodnotu.
     *
     * @param badges Pole obsahujuce vsetky odznaky, ktorym treba nastavit velkost.
     */
    private void setBadgeSize(ImageView... badges) {
        for (ImageView badge : badges) {
            badge.setFitWidth(BADGE_SIZE_PX);
            badge.setPreserveRatio(true);
        }
    }
}
