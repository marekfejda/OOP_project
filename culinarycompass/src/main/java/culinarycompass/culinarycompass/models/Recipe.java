package culinarycompass.culinarycompass.models;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import culinarycompass.culinarycompass.interfaces.Likeable;
import culinarycompass.culinarycompass.interfaces.object2Str;

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

    public Recipe() {
        this.ingredients = new ArrayList<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setTimeEstimation(String timeEstimation) {
        this.timeEstimation = timeEstimation;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public void setRecipeText(String recipeText) {
        this.recipeText = recipeText;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public String getTimeEstimation() {
        return timeEstimation;
    }

    public int getPortions() {
        return portions;
    }

    public String getRecipeText() {
        return recipeText;
    }

    public boolean isGlutenFree() {
        return isGlutenFree;
    }

    public void setGlutenFree(boolean glutenFree) {
        isGlutenFree = glutenFree;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public void incrementLikes() {
        this.likes++;
    }

    @Override
    public void decrementLikes() {
        if (this.likes > 0) {
            this.likes--;
        }
    }

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

    @Override
    public String toString() {
        return object2Str();
    }
}
