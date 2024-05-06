package culinarycompass.culinarycompass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class User {
    private final String id;
    private final String nickname;
    private final String password;
    private Inventory inventory;

    public User(String id, String nickname, String password) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.inventory = new Inventory();
    }

    public String getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void updateInventory(Set<String> ingredientNames) {
        inventory.clearInventory();
        ingredientNames.forEach(inventory::addIngredient);
    }

    public void saveSelectedIngredients() {
        try (PrintWriter out = new PrintWriter(new FileOutputStream("user_" + id + "_" + nickname + "_ingredients.txt"))) {
            for (String ingredient : inventory.getSelectedIngredients()) {
                out.println(ingredient);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadSelectedIngredients() {
        File file = new File("user_" + id + "_" + nickname + "_ingredients.txt");
//        System.out.println("Loading selections from: " + file.getAbsolutePath()); // For debugging
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                Set<String> loadedIngredients = new HashSet<>();
                while (scanner.hasNextLine()) {
                    String ingredientName = scanner.nextLine();
                    loadedIngredients.add(ingredientName);
                }
                inventory.updateSelectedIngredients(loadedIngredients);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}