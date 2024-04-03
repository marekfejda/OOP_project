package culinarycompass.culinarycompass;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Recipe {
    private int id;
    private String name;
    private List<String> ingredients;
    private String timeEstimation;
    private int portions;
    private String recipeText;

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

    // Load Recipes from file
    public static List<Recipe> loadRecipesFromFile(String filePath) throws IOException {
        List<Recipe> recipes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if ("---".equals(line.trim())) { // Start of a new recipe
                    Recipe recipe = new Recipe();
                    while ((line = reader.readLine()) != null && !line.trim().equals("---")) {
                        if (line.startsWith("ID:")) {
                            recipe.setId(Integer.parseInt(line.substring(4).trim()));
                        } else if (line.startsWith("Name:")) {
                            recipe.setName(line.substring(5).trim());
                        } else if (line.startsWith("Ingredients:")) {
                            String ingredientsLine = line.substring(12).trim();
                            recipe.setIngredients(Arrays.asList(ingredientsLine.split(",\\s*")));
                        } else if (line.startsWith("TimeEstimation:")) {
                            recipe.setTimeEstimation(line.substring(15).trim());
                        } else if (line.startsWith("Portions:")) {
                            recipe.setPortions(Integer.parseInt(line.substring(9).trim().split(" ")[0])); // Just get the number
                        } else if (line.startsWith("Recipe:")) {
                            StringBuilder recipeTextBuilder = new StringBuilder(line.substring(7).trim());
                            // Read all lines until the next recipe or end of file
                            while ((line = reader.readLine()) != null && !line.trim().equals("---")) {
                                recipeTextBuilder.append(System.lineSeparator()).append(line);
                            }
                            recipe.setRecipeText(recipeTextBuilder.toString());
                            // break the inner while loop to avoid reading the next recipe ID
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
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", timeEstimation='" + timeEstimation + '\'' +
                ", portions=" + portions +
                ", recipeText='" + recipeText + '\'' +
                '}';
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
