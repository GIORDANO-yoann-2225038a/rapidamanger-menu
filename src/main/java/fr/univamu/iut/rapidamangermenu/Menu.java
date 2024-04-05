package fr.univamu.iut.rapidamangermenu;

import java.util.ArrayList;

/**
 * Cette classe représente un menu dans le système de gestion des menus.
 * Un menu est composé d'un nom, d'un identifiant unique, d'un prix, d'une date de dernière mise à jour,
 * d'un créateur et d'une liste de plats composant le menu.
 */
public class Menu {

    /**
     * Nom du menu.
     */
    protected String name;

    /**
     * Identifiant du menu.
     */
    protected Integer id_menu;

    /**
     * Prix du menu.
     */
    protected Float price;

    /**
     * Date de la dernière mise à jour du menu.
     */
    protected String last_update;

    /**
     * Créateur du menu.
     */
    protected String creator;

    /**
     * Liste des identifiants des plats composant le menu.
     */
    protected ArrayList<Integer> list_dish;

    /**
     * Constructeur par défaut.
     */
    public Menu() {
    }

    /**
     * Constructeur avec paramètres pour initialiser un menu.
     * @param name Le nom du menu.
     * @param id_menu L'identifiant unique du menu.
     * @param price Le prix du menu.
     * @param last_update La date de la dernière mise à jour du menu.
     * @param creator Le créateur du menu.
     * @param list_dish La liste des identifiants des plats composant le menu.
     */
    public Menu(String name, Integer id_menu, Float price, String last_update, String creator, ArrayList<Integer> list_dish) {
        this.name = name;
        this.id_menu = id_menu;
        this.price = price;
        this.last_update = last_update;
        this.creator = creator;
        this.list_dish = list_dish;
    }

    // Getters et setters

    /**
     * Obtient le nom du menu.
     * @return Le nom du menu.
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le nom du menu.
     * @param name Le nom du menu.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtient l'identifiant du menu.
     * @return L'identifiant du menu.
     */
    public Integer getId_menu() {
        return id_menu;
    }

    /**
     * Définit l'identifiant du menu.
     * @param id_menu L'identifiant du menu.
     */
    public void setId_menu(Integer id_menu) {
        this.id_menu = id_menu;
    }

    /**
     * Obtient le prix du menu.
     * @return Le prix du menu.
     */
    public Float getPrice() {
        return price;
    }

    /**
     * Définit le prix du menu.
     * @param price Le prix du menu.
     */
    public void setPrice(Float price) {
        this.price = price;
    }

    /**
     * Obtient la date de la dernière mise à jour du menu.
     * @return La date de la dernière mise à jour du menu.
     */
    public String getLast_update() {
        return last_update;
    }

    /**
     * Définit la date de la dernière mise à jour du menu.
     * @param last_update La date de la dernière mise à jour du menu.
     */
    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    /**
     * Obtient le créateur du menu.
     * @return Le créateur du menu.
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Définit le créateur du menu.
     * @param creator Le créateur du menu.
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * Obtient la liste des identifiants des plats composant le menu.
     * @return La liste des identifiants des plats composant le menu.
     */
    public ArrayList<Integer> getList_dish() {
        return list_dish;
    }

    /**
     * Définit la liste des identifiants des plats composant le menu.
     * @param list_dish La liste des identifiants des plats composant le menu.
     */
    public void setList_dish(ArrayList<Integer> list_dish) {
        this.list_dish = list_dish;
    }

    /**
     * Méthode toString pour l'affichage des informations du menu.
     * @return Une représentation textuelle du menu.
     */
    @Override
    public String toString() {
        return "Menu{" +
                "name='" + name + '\'' +
                ", id_menu=" + id_menu +
                ", price=" + price +
                ", last_update='" + last_update + '\'' +
                ", creator='" + creator + '\'' +
                ", list_dish=" + list_dish +
                '}';
    }
}
