package fr.univamu.iut.rapidamangermenu;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class MenuRepository implements MenuRepositoryInterface, Closeable {

    protected Connection dbConnection; // Connexion à la base de données

    // Méthode pour fermer la connexion à la base de données
    @Override
    public void close() {
        try {
            dbConnection.close(); // Fermeture de la connexion
        } catch (SQLException e) {
            System.err.print(e.getMessage()); // Gestion des exceptions en cas d'erreur lors de la fermeture
        }
    }

    // Méthodes de l'interface MenuRepositoryInterface

    // Méthode pour récupérer un menu en fonction de son identifiant
    @Override
    public Menu getMenu(String id) {
        return null; // Renvoie null car non implémenté dans cette classe
    }

    // Méthode pour récupérer tous les menus
    @Override
    public ArrayList<Menu> getAllMenu() {
        return null; // Renvoie null car non implémenté dans cette classe
    }

    // Méthode pour supprimer un menu en fonction de son identifiant
    @Override
    public boolean deleteMenu(String id) {
        return false; // Renvoie false car non implémenté dans cette classe
    }

    // Méthode pour créer un nouveau menu
    @Override
    public String createMenu(String name, Float price, String creator, ArrayList<Integer> list_dish) {
        return null; // Renvoie null car non implémenté dans cette classe
    }

    // Méthode pour mettre à jour les informations d'un menu
    @Override
    public boolean updateMenu(String id_menu, String price, String name, String creator) {
        return false; // Renvoie false car non implémenté dans cette classe
    }

    // Méthodes spécifiques pour ajouter et supprimer un plat d'un menu
    public void addDishToMenu(int menuId, String dishMenu) {
        // Non implémenté dans cette classe
    }

    public void removeDishToMenu(int menuId, String dishId) {
        // Non implémenté dans cette classe
    }

}
