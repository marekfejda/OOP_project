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
 * Trieda predstavujuca pouzivatela s jeho identifikacnymi udajmi a inventarom.
 * Tato trieda umoznuje spravu inventara a ukladanie/nacitavanie prisad z/do suboru.
 */
public class User implements Serializable {
    private final String id;
    private final String nickname;
    private final String password;
    private Inventory inventory;

    /**
     * Konstruktor triedy User.
     *
     * @param id Identifikacne cislo pouzivatela.
     * @param nickname Prezyvka pouzivatela.
     * @param password Heslo pouzivatela.
     */
    public User(String id, String nickname, String password) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.inventory = new Inventory();
    }

    /**
     * Vrati identifikacne cislo pouzivatela.
     *
     * @return ID pouzivatela.
     */
    public String getId() {
        return id;
    }

    /**
     * Vrati prezyvku pouzivatela.
     *
     * @return Prezyvka pouzivatela.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Vrati heslo pouzivatela.
     *
     * @return Heslo pouzivatela.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Vrati inventar pouzivatela.
     *
     * @return Inventar pouzivatela.
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Aktualizuje inventar pouzivatela so zadanymi prisadami.
     *
     * @param ingredientNames Mnozina nazvov prisad na aktualizaciu.
     */
    public void updateInventory(Set<String> ingredientNames) {
        inventory.clearInventory();
        ingredientNames.forEach(inventory::addIngredient);
    }

    /**
     * Ulozi vybrane prisady pouzivatela do textoveho suboru.
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
     * Nacita vybrane prisady pouzivatela zo suboru.
     * Aktualizuje inventar pouzivatela s nacitanymi udajmi.
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
     * Ulozi inventar pouzivatela do serializovaneho suboru.
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
     * Pomocna trieda na spajanie retazcov s oddelovacim znakom.
     */
    public static class MergeToString<T> {
        /**
         * Spoji polozky do jedneho retazca s pouzitim daneho oddelovaca.
         *
         * @param splitChar Oddelovaci znak medzi polozkami.
         * @param items Polozky na spajanie.
         * @return Spojeny retazec.
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
