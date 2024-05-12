package culinarycompass.culinarycompass.models;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import culinarycompass.culinarycompass.interfaces.Likeable;
import culinarycompass.culinarycompass.interfaces.object2Str;

/**
 * Trieda Recipe predstavuje recept a obsahuje suvisiace informacie.
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
     * Konstruktor, ktory vytvara prazdny recept.
     */
    public Recipe() {
        this.ingredients = new ArrayList<>();
    }

    /**
     * Nastavi identifikacne cislo receptu.
     *
     * @param id Identifikacne cislo.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Nastavi nazov receptu.
     *
     * @param name Nazov receptu.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Nastavi ingrediencie potrebne pre recept.
     *
     * @param ingredients Zoznam ingrediencii.
     */
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Nastavi casovy odhad pripravy.
     *
     * @param timeEstimation Casovy odhad.
     */
    public void setTimeEstimation(String timeEstimation) {
        this.timeEstimation = timeEstimation;
    }

    /**
     * Nastavi pocet porcii receptu.
     *
     * @param portions Pocet porcii.
     */
    public void setPortions(int portions) {
        this.portions = portions;
    }

    /**
     * Nastavi text receptu.
     *
     * @param recipeText Textovy popis postupu pripravy receptu.
     */
    public void setRecipeText(String recipeText) {
        this.recipeText = recipeText;
    }

    /**
     * Vrati identifikacne cislo receptu.
     *
     * @return Identifikacne cislo.
     */
    public int getId() {
        return id;
    }

    /**
     * Vrati nazov receptu.
     *
     * @return Nazov receptu.
     */
    public String getName() {
        return name;
    }

    /**
     * Vrati zoznam ingrediencii.
     *
     * @return Zoznam ingrediencii.
     */
    public List<String> getIngredients() {
        return ingredients;
    }

    /**
     * Vrati casovy odhad pripravy receptu.
     *
     * @return Casovy odhad pripravy.
     */
    public String getTimeEstimation() {
        return timeEstimation;
    }

    /**
     * Vrati pocet porcii, ktore recept pripravi.
     *
     * @return Pocet porcii.
     */
    public int getPortions() {
        return portions;
    }

    /**
     * Vrati textovy popis receptu.
     *
     * @return Textovy popis receptu.
     */
    public String getRecipeText() {
        return recipeText;
    }

    /**
     * Skontroluje, ci je recept bezlepkovy.
     *
     * @return true, ak je recept bezlepkovy; inak false.
     */
    public boolean isGlutenFree() {
        return isGlutenFree;
    }

    /**
     * Nastavi, ci je recept bezlepkovy.
     *
     * @param glutenFree True, ak je bezlepkovy; false, ak nie.
     */
    public void setGlutenFree(boolean glutenFree) {
        isGlutenFree = glutenFree;
    }

    /**
     * Skontroluje, ci je recept vegetariansky.
     *
     * @return true, ak je recept vegetariansky; inak false.
     */
    public boolean isVegetarian() {
        return isVegetarian;
    }

    /**
     * Nastavi, ci je recept vegetariansky.
     *
     * @param vegetarian True, ak je vegetariansky; false, ak nie.
     */
    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    /**
     * Vrati pocet lajkov receptu.
     *
     * @return Pocet lajkov.
     */
    public int getLikes() {
        return likes;
    }

    /**
     * Nastavi pocet lajkov receptu.
     *
     * @param likes Pocet lajkov.
     */
    public void setLikes(int likes) {
        this.likes = likes;
    }

    /**
     * Zvysi pocet lajkov receptu o jeden.
     */
    @Override
    public void incrementLikes() {
        this.likes++;
    }

    /**
     * Znizi pocet lajkov receptu o jeden, ak je pocet lajkov vacsi ako nula.
     */
    @Override
    public void decrementLikes() {
        if (this.likes > 0) {
            this.likes--;
        }
    }

    /**
     * Nacita recepty zo suboru.
     *
     * @param filePath Cesta k suboru s receptami.
     * @return Zoznam receptov.
     * @throws IOException Ak sa nepodari precitat subor.
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
     * Prevedie recept na textovu reprezentaciu.
     *
     * @return Textova reprezentacia receptu.
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
     * Vrati textovu reprezentaciu receptu.
     *
     * @return String reprezentujuci recept.
     */
    @Override
    public String toString() {
        return object2Str();
    }
}
