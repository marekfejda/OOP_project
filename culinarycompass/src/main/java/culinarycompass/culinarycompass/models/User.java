package culinarycompass.culinarycompass.models;

import java.io.Serializable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Trieda predstavujúca používateľa s jeho identifikačnými údajmi a inventárom.
 * Táto trieda umožňuje správu inventára a ukladanie/načítavanie prísad z/do súboru.
 */
public class User implements Serializable {
    private final String id;
    private final String nickname;
    private final String password;
    private Inventory inventory;

    /**
     * Konštruktor triedy User.
     *
     * @param id Identifikačné číslo používateľa.
     * @param nickname Prezývka používateľa.
     * @param password Heslo používateľa.
     */
    public User(String id, String nickname, String password) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.inventory = new Inventory();
    }

    /**
     * Vráti identifikačné číslo používateľa.
     *
     * @return ID používateľa.
     */
    public String getId() {
        return id;
    }

    /**
     * Vráti prezývku používateľa.
     *
     * @return Prezývka používateľa.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Vráti heslo používateľa.
     *
     * @return Heslo používateľa.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Vráti inventár používateľa.
     *
     * @return Inventár používateľa.
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Aktualizuje inventár používateľa so zadanými prísadami.
     *
     * @param ingredientNames Množina názvov prísad na aktualizáciu.
     */
    public void updateInventory(Set<String> ingredientNames) {
        inventory.clearInventory();
        ingredientNames.forEach(inventory::addIngredient);
    }

    /**
     * Uloží vybrané prísady používateľa do textového súboru.
     */
    public void saveSelectedIngredients() {
        String fileName = new MergeToString<String>().concatenate("_", "user", id, nickname, "ingredients.txt");
        try (PrintWriter out = new PrintWriter(new FileOutputStream(fileName))) {
            for (String ingredient : inventory.getSelectedIngredients()) {
                out.println(ingredient);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Načíta vybrané prísady používateľa zo súboru.
     * Aktualizuje inventár používateľa s načítanými údajmi.
     */
    public void loadSelectedIngredients() {
        String fileName = new MergeToString<String>().concatenate("_", "user", id, nickname, "ingredients.txt");
        File file = new File(fileName);
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

    /**
     * Uloží inventár používateľa do serializovaného súboru.
     */
    public void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("inventory_serialized_user" + id + ".ser"))) {
            out.writeObject(this.inventory);
        } catch (IOException e) {
            System.err.println("Error saving inventory data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Pomocná trieda na spájanie reťazcov s oddelovacím znakom.
     */
    public static class MergeToString<T> {
        /**
         * Spojí položky do jedného reťazca s použitím daného oddeľovača.
         *
         * @param splitChar Oddelovací znak medzi položkami.
         * @param items Položky na spájanie.
         * @return Spojený reťazec.
         */
        public String concatenate(String splitChar, T... items) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < items.length; i++) {
                sb.append(items[i].toString());
                if (i < items.length - 1) {
                    sb.append(splitChar);
                }
            }
            return sb.toString();
        }
    }
}
