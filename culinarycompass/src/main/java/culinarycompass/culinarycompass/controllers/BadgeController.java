package culinarycompass.culinarycompass.controllers;

import culinarycompass.culinarycompass.models.Recipe;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public abstract class BadgeController {
    private static final int BADGE_SIZE_PX = 50;

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

    private void setBadgeSize(ImageView... badges) {
        for (ImageView badge : badges) {
            badge.setFitWidth(BADGE_SIZE_PX);
            badge.setPreserveRatio(true);
        }
    }
}
