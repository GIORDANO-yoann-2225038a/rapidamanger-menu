package fr.univamu.iut.rapidamangermenu;

import java.util.ArrayList;

/**
 * Cette interface définit les méthodes permettant d'accéder aux données des plats dans le système de gestion des menus.
 * Les implémentations de cette interface fournissent des fonctionnalités pour récupérer des plats, mettre à jour des plats,
 * et effectuer d'autres opérations liées aux plats.
 */
public interface DishRepositoryInterface {

    /**
     * Cette méthode est commentée car elle n'a pas de contenu.
     * Elle est probablement destinée à effectuer des opérations de nettoyage ou de fermeture de ressources.
     */
    void close();

    /**
     * Méthode pour récupérer un plat en fonction de son identifiant.
     * @param dishId L'identifiant du plat à récupérer.
     * @return Le plat correspondant à l'identifiant spécifié.
     */
    DishMenu getDish(int dishId);

    /**
     * Méthode pour récupérer tous les plats.
     * @return Une liste contenant tous les plats disponibles dans le système.
     */
    ArrayList<DishMenu> getAllDish();

    /**
     * Méthode pour mettre à jour les informations d'un plat.
     * @param menuId L'identifiant du menu auquel le plat est associé.
     * @param menuName Le nouveau nom du plat.
     * @param price Le nouveau prix du plat.
     * @return true si la mise à jour du plat a réussi, sinon false.
     */
    boolean updateDish(int menuId, String menuName, float price);

    /**
     * Cette méthode est commentée car elle n'a pas de contenu.
     * Elle semble être une redondance de la méthode getDish(int dishId) qui est déjà définie.
     * @param dishId L'identifiant du plat.
     * @return Une instance de DishMenu associée à l'identifiant spécifié.
     */
    DishMenu getDishMenu(int dishId);
}

