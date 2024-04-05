package fr.univamu.iut.rapidamangermenu;

/**
 * Cette classe représente l'association entre un plat et un menu dans le système de gestion des menus.
 * Chaque instance de cette classe contient les identifiants du plat et du menu associés.
 */
public class DishMenu {
    /**
     * Identifiant du plat.
     */
    private int dishId;

    /**
     * Identifiant du menu.
     */
    private int menuId;

    /**
     * Constructeur par défaut.
     */
    public DishMenu() {}

    /**
     * Constructeur avec les deux identifiants.
     * @param menuId L'identifiant du menu.
     * @param dishId L'identifiant du plat.
     */
    public DishMenu(int menuId, int dishId) {
        this.dishId = dishId;
        this.menuId = menuId;
    }

    /**
     * Obtient l'identifiant du plat.
     * @return L'identifiant du plat.
     */
    public int getDishId() {
        return dishId;
    }

    /**
     * Définit l'identifiant du plat.
     * @param dishId L'identifiant du plat.
     */
    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    /**
     * Obtient l'identifiant du menu.
     * @return L'identifiant du menu.
     */
    public int getMenuId() {
        return menuId;
    }

    /**
     * Définit l'identifiant du menu.
     * @param menuId L'identifiant du menu.
     */
    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    /**
     * Méthode toString pour afficher les informations de l'objet.
     * @return Une représentation textuelle de l'association entre le plat et le menu.
     */
    @Override
    public String toString() {
        return "DishMenu{" +
                "dishId=" + dishId +
                ", menuId=" + menuId +
                '}';
    }
}
