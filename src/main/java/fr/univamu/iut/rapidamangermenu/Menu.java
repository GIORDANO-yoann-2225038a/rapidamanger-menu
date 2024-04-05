package fr.univamu.iut.rapidamangermenu;

import java.util.ArrayList;

public class Menu {

    protected String name; // Nom du menu
    protected Integer id_menu; // Identifiant du menu
    protected Float price; // Prix du menu
    protected String last_update; // Date de la dernière mise à jour
    protected String creator; // Créateur du menu
    protected ArrayList<Integer> list_dish; // Liste des plats composant le menu

    // Constructeur par défaut
    public Menu() {
    }

    // Constructeur avec paramètres
    public Menu(String name, Integer id_menu, Float price, String last_update, String creator, ArrayList<Integer> list_dish) {
        this.name = name;
        this.id_menu = id_menu;
        this.price = price;
        this.last_update = last_update;
        this.creator = creator;
        this.list_dish = list_dish;
    }

    // Getters et setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId_menu() {
        return id_menu;
    }

    public void setId_menu(Integer id_menu) {
        this.id_menu = id_menu;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public ArrayList<Integer> getList_dish() {
        return list_dish;
    }

    public void setList_dish(ArrayList<Integer> list_dish) {
        this.list_dish = list_dish;
    }

    // Méthode toString pour l'affichage des informations du menu
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
