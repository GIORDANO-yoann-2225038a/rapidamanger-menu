package fr.univamu.iut.rapidamangermenu;

import java.util.*;

// Interface définissant les méthodes pour interagir avec un dépôt de menus
public interface MenuRepositoryInterface {

    // Méthode pour fermer la connexion au dépôt de menus
    public void close();

    // Méthode pour récupérer un menu en fonction de son identifiant
    public Menu getMenu(String id );

    // Méthode pour récupérer tous les menus
    public ArrayList<Menu> getAllMenu();

    // Méthode pour supprimer un menu en fonction de son identifiant
    public boolean deleteMenu(String id);

    // Méthode pour créer un nouveau menu avec un nom, un prix, un créateur et une liste de plats
    public String createMenu(String name, Float price, String creator, ArrayList<Integer> list_dish);

    // Méthode pour mettre à jour les informations d'un menu en fonction de son identifiant, son nom et son créateur
    public boolean updateMenu(String id_menu, String price, String name, String creator);

    // Méthode pour ajouter un plat à un menu en fonction de l'identifiant du menu et de l'identifiant du plat
    void addDishToMenu(int menuId, String dishMenu);

    // Méthode pour supprimer un plat d'un menu en fonction de l'identifiant du menu et de l'identifiant du plat
    void removeDishToMenu(int menuId, String dishId);
}
