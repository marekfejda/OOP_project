package culinarycompass.culinarycompass;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Recipe {
    private int id;
    private String name;
    private List<String> ingredients;
    private String timeEstimation;
    private int portions;
    private String recipeText;
    private boolean isGlutenFree;
    private boolean isVegetarian;

    // Constructors
    public Recipe() {
        // Default constructor
    }

    // Setters
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

    // Getters
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

    public static List<Recipe> loadRecipesFromFile(String filePath) throws IOException {
        List<Recipe> recipes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if ("---".equals(line.trim())) {
                    Recipe recipe = new Recipe();
                    while ((line = reader.readLine()) != null && !line.trim().equals("---")) {
                        if (line.startsWith("ID:")) {
                            recipe.setId(Integer.parseInt(line.substring(4).trim()));
                        } else if (line.startsWith("Name:")) {
                            recipe.setName(line.substring(5).trim());
                        } else if (line.startsWith("Ingredients:")) {
                            recipe.setIngredients(Arrays.asList(line.substring(12).trim().split(",\\s*")));
                        } else if (line.startsWith("TimeEstimation:")) {
                            recipe.setTimeEstimation(line.substring(15).trim());
                        } else if (line.startsWith("Portions:")) {
                            recipe.setPortions(Integer.parseInt(line.substring(9).trim().split(" ")[0]));
                        } else if (line.startsWith("Recipe:")) {
                            StringBuilder recipeTextBuilder = new StringBuilder(line.substring(7).trim());
                            while ((line = reader.readLine()) != null && !line.trim().equals("---")) {
                                recipeTextBuilder.append(System.lineSeparator()).append(line);
                            }
                            recipe.setRecipeText(recipeTextBuilder.toString());
                            break; // End reading the current recipe section
                        } else if (line.startsWith("IsGlutenFree:")) {
                            recipe.setGlutenFree(Boolean.parseBoolean(line.substring(13).trim()));
                        } else if (line.startsWith("IsVegetarian:")) {
                            recipe.setVegetarian(Boolean.parseBoolean(line.substring(13).trim()));
                        }
                    }
                    recipes.add(recipe);
                }
            }
        }
        return recipes;
    }

    @Override
    public String toString() {
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

    public static List<Recipe> loadAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        File file = new File("recipes.txt"); // Assuming a file named recipes.txt

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Parse the line to create a Recipe object
                // This is highly dependent on how your data is formatted
                Recipe recipe = parseRecipeLine(line);
                recipes.add(recipe);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return recipes;
    }

    private static Recipe parseRecipeLine(String line) {
        // Implement parsing logic based on your file format
        // This is just a placeholder
        Recipe recipe = new Recipe();
        // Set recipe properties based on the line content
        return recipe;
    }

    // Main method just for testing purpose
    public static void main(String[] args) {
        try {
            List<Recipe> recipes = Recipe.loadRecipesFromFile("src/recipes.txt");
            recipes.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
