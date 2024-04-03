package culinarycompass.culinarycompass;

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
}