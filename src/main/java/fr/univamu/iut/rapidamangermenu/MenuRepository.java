package fr.univamu.iut.rapidamangermenu;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Cette classe représente un repository pour la gestion des menus.
 * Elle implémente l'interface MenuRepositoryInterface et fournit les méthodes
 * nécessaires pour interagir avec la base de données pour la gestion des menus.
 */
public class MenuRepository implements MenuRepositoryInterface, Closeable {

    /**
     * Connexion à la base de données.
     */
    protected Connection dbConnection;

    /**
     * Méthode pour fermer la connexion à la base de données.
     */
    @Override
    public void close() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    /**
     * Méthode pour récupérer un menu en fonction de son identifiant.
     * @param id L'identifiant du menu à récupérer.
     * @return Le menu correspondant à l'identifiant spécifié, ou null s'il n'existe pas.
     */
    @Override
    public Menu getMenu(String id) {
        return null;
    }

    /**
     * Méthode pour récupérer tous les menus.
     * @return La liste de tous les menus, ou null si aucun menu n'existe.
     */
    @Override
    public ArrayList<Menu> getAllMenu() {
        return null;
    }

    /**
     * Méthode pour supprimer un menu en fonction de son identifiant.
     * @param id L'identifiant du menu à supprimer.
     * @return true si le menu a été supprimé avec succès, false sinon.
     */
    @Override
    public boolean deleteMenu(String id) {
        return false;
    }

    /**
     * Méthode pour créer un nouveau menu.
     * @param name Le nom du menu à créer.
     * @param price Le prix du menu à créer.
     * @param creator Le créateur du menu à créer.
     * @param list_dish La liste des plats composant le menu à créer.
     * @return L'identifiant du nouveau menu créé, ou null en cas d'échec.
     */
    @Override
    public String createMenu(String name, Float price, String creator, ArrayList<Integer> list_dish) {
        return null;
    }

    /**
     * Méthode pour mettre à jour les informations d'un menu.
     * @param id_menu L'identifiant du menu à mettre à jour.
     * @param price Le nouveau prix du menu.
     * @param name Le nouveau nom du menu.
     * @param creator Le nouveau créateur du menu.
     * @return true si la mise à jour a été effectuée avec succès, false sinon.
     */
    @Override
    public boolean updateMenu(String id_menu, String price, String name, String creator) {
        return false;
    }

    /**
     * Méthode pour ajouter un plat à un menu.
     * @param menuId L'identifiant du menu auquel ajouter le plat.
     * @param dishMenu L'identifiant du plat à ajouter.
     */
    public void addDishToMenu(int menuId, String dishMenu) {
        // Non implémenté dans cette classe
    }

    /**
     * Méthode pour supprimer un plat d'un menu.
     * @param menuId L'identifiant du menu duquel supprimer le plat.
     * @param dishId L'identifiant du plat à supprimer.
     */
    public void removeDishToMenu(int menuId, String dishId) {
        // Non implémenté dans cette classe
    }

}
