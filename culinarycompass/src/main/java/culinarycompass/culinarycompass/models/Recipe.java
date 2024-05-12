package culinarycompass.culinarycompass.models;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import culinarycompass.culinarycompass.interfaces.Likeable;
import culinarycompass.culinarycompass.interfaces.object2Str;

/**
 * Trieda Recipe predstavuje recept a obsahuje súvisiace informácie.
 */
public class Recipe implements Likeable, object2Str {
    private int id;
    private String name;
    private List<String> ingredients;
    private String timeEstimation;
    private int portions;
    private String recipeText;
    private boolean isGlutenFree;
    private boolean isVegetarian;
    private int likes;

    /**
     * Konštruktor, ktorý vytvára prázdny recept.
     */
    public Recipe() {
        this.ingredients = new ArrayList<>();
    }

    /**
     * Nastaví identifikačné číslo receptu.
     *
     * @param id Identifikačné číslo.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Nastaví názov receptu.
     *
     * @param name Názov receptu.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Nastaví ingrediencie potrebné pre recept.
     *
     * @param ingredients Zoznam ingrediencií.
     */
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Nastaví časový odhad prípravy.
     *
     * @param timeEstimation Časový odhad.
     */
    public void setTimeEstimation(String timeEstimation) {
        this.timeEstimation = timeEstimation;
    }

    /**
     * Nastaví počet porcií receptu.
     *
     * @param portions Počet porcií.
     */
    public void setPortions(int portions) {
        this.portions = portions;
    }

    /**
     * Nastaví text receptu.
     *
     * @param recipeText Textový popis postupu prípravy receptu.
     */
    public void setRecipeText(String recipeText) {
        this.recipeText = recipeText;
    }

    /**
     * Vráti identifikačné číslo receptu.
     *
     * @return Identifikačné číslo.
     */
    public int getId() {
        return id;
    }

    /**
     * Vráti názov receptu.
     *
     * @return Názov receptu.
     */
    public String getName() {
        return name;
    }

    /**
     * Vráti zoznam ingrediencií.
     *
     * @return Zoznam ingrediencií.
     */
    public List<String> getIngredients() {
        return ingredients;
    }

    /**
     * Vráti časový odhad prípravy receptu.
     *
     * @return Časový odhad prípravy.
     */
    public String getTimeEstimation() {
        return timeEstimation;
    }

    /**
     * Vráti počet porcií, ktoré recept pripraví.
     *
     * @return Počet porcií.
     */
    public int getPortions() {
        return portions;
    }

    /**
     * Vráti textový popis receptu.
     *
     * @return Textový popis receptu.
     */
    public String getRecipeText() {
        return recipeText;
    }

    /**
     * Skontroluje, či je recept bezlepkový.
     *
     * @return true, ak je recept bezlepkový; inak false.
     */
    public boolean isGlutenFree() {
        return isGlutenFree;
    }

    /**
     * Nastaví, či je recept bezlepkový.
     *
     * @param glutenFree True, ak je bezlepkový; false, ak nie.
     */
    public void setGlutenFree(boolean glutenFree) {
        isGlutenFree = glutenFree;
    }

    /**
     * Skontroluje, či je recept vegetariánsky.
     *
     * @return true, ak je recept vegetariánsky; inak false.
     */
    public boolean isVegetarian() {
        return isVegetarian;
    }

    /**
     * Nastaví, či je recept vegetariánsky.
     *
     * @param vegetarian True, ak je vegetariánsky; false, ak nie.
     */
    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    /**
     * Vráti počet lajkov receptu.
     *
     * @return Počet lajkov.
     */
    public int getLikes() {
        return likes;
    }

    /**
     * Nastaví počet lajkov receptu.
     *
     * @param likes Počet lajkov.
     */
    public void setLikes(int likes) {
        this.likes = likes;
    }

    /**
     * Zvýši počet lajkov receptu o jeden.
     */
    @Override
    public void incrementLikes() {
        this.likes++;
    }

    /**
     * Zníži počet lajkov receptu o jeden, ak je počet lajkov väčší ako nula.
     */
    @Override
    public void decrementLikes() {
        if (this.likes > 0) {
            this.likes--;
        }
    }

    /**
     * Načíta recepty zo súboru.
     *
     * @param filePath Cesta k súboru s receptami.
     * @return Zoznam receptov.
     * @throws IOException Ak sa nepodarí prečítať súbor.
     */
    public static List<Recipe> loadRecipesFromFile(String filePath) throws IOException {
        List<Recipe> recipes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if ("---".equals(line.trim())) {
                    Recipe recipe = new Recipe();
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("ID:")) {
                            recipe.setId(Integer.parseInt(line.substring(4).trim()));
                        } else if (line.startsWith("Likes:")) {
                            recipe.setLikes(Integer.parseInt(line.substring(6).trim()));
                        } else if (line.startsWith("Name:")) {
                            recipe.setName(line.substring(5).trim());
                        } else if (line.startsWith("Ingredients:")) {
                            recipe.setIngredients(Arrays.asList(line.substring(12).trim().split(",\\s*")));
                        } else if (line.startsWith("TimeEstimation:")) {
                            recipe.setTimeEstimation(line.substring(15).trim());
                        } else if (line.startsWith("Portions:")) {
                            recipe.setPortions(Integer.parseInt(line.substring(9).trim().split(" ")[0]));
                        } else if (line.startsWith("IsGlutenFree:")) {
                            recipe.setGlutenFree(Boolean.parseBoolean(line.substring(13).trim()));
                        } else if (line.startsWith("IsVegetarian:")) {
                            recipe.setVegetarian(Boolean.parseBoolean(line.substring(13).trim()));
                        } else if (line.startsWith("Recipe:")) {
                            StringBuilder recipeTextBuilder = new StringBuilder(line.substring(7).trim());
                            while ((line = reader.readLine()) != null && !line.trim().equals("---")) {
                                recipeTextBuilder.append(System.lineSeparator()).append(line);
                            }
                            recipe.setRecipeText(recipeTextBuilder.toString());
                            break;
                        }
                    }
                    recipes.add(recipe);
                }
            }
        }
        return recipes;
    }

    /**
     * Prevedie recept na textovú reprezentáciu.
     *
     * @return Textová reprezentácia receptu.
     */
    @Override
    public String object2Str() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", timeEstimation='" + timeEstimation + '\'' +
                ", portions=" + portions +
                ", recipeText='" + recipeText + '\'' +
                ", isGlutenFree=" + isGlutenFree +
                ", isVegetarian=" + isVegetarian +
                '}';
    }

    /**
     * Vráti textovú reprezentáciu receptu.
     *
     * @return String reprezentujúci recept.
     */
    @Override
    public String toString() {
        return object2Str();
    }
}
