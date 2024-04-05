package fr.univamu.iut.rapidamangermenu;

import java.util.*;

// Interface définissant les méthodes pour interagir avec un dépôt de menus
/**
 * Interface définissant les méthodes pour interagir avec un dépôt de menus.
 */
public interface MenuRepositoryInterface {

    /**
     * Méthode pour fermer la connexion au dépôt de menus.
     */
    public void close();

    /**
     * Méthode pour récupérer un menu en fonction de son identifiant.
     * @param id L'identifiant du menu à récupérer.
     * @return Le menu correspondant à l'identifiant spécifié.
     */
    public Menu getMenu(String id);

    /**
     * Méthode pour récupérer tous les menus.
     * @return Une liste contenant tous les menus disponibles.
     */
    public ArrayList<Menu> getAllMenu();

    /**
     * Méthode pour supprimer un menu en fonction de son identifiant.
     * @param id L'identifiant du menu à supprimer.
     * @return true si la suppression a réussi, sinon false.
     */
    public boolean deleteMenu(String id);

    /**
     * Méthode pour créer un nouveau menu.
     * @param name Le nom du nouveau menu.
     * @param price Le prix du nouveau menu.
     * @param creator Le créateur du nouveau menu.
     * @param list_dish La liste des plats composant le nouveau menu.
     * @return L'identifiant du nouveau menu créé.
     */
    public String createMenu(String name, Float price, String creator, ArrayList<Integer> list_dish);

    /**
     * Méthode pour mettre à jour les informations d'un menu.
     * @param id_menu L'identifiant du menu à mettre à jour.
     * @param price Le nouveau prix du menu.
     * @param name Le nouveau nom du menu.
     * @param creator Le nouveau créateur du menu.
     * @return true si la mise à jour a réussi, sinon false.
     */
    public boolean updateMenu(String id_menu, String price, String name, String creator);

    /**
     * Méthode pour ajouter un plat à un menu.
     * @param menuId L'identifiant du menu auquel ajouter le plat.
     * @param dishMenu L'identifiant du plat à ajouter au menu.
     */
    void addDishToMenu(int menuId, String dishMenu);

    /**
     * Méthode pour supprimer un plat d'un menu.
     * @param menuId L'identifiant du menu duquel supprimer le plat.
     * @param dishId L'identifiant du plat à supprimer du menu.
     */
    void removeDishToMenu(int menuId, String dishId);
}
