package fr.univamu.iut.rapidamangermenu;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    protected String name;
    protected Integer id_menu;
    protected Float price;
    protected String last_update;
    protected Integer id_creator;
    protected ArrayList<Integer> list_dish;


    public Menu() {
    }

    public Menu(String name, Integer id_menu,Float price, String last_update,  id_creator, ArrayList<Integer> list_dish) {
        this.name = name;
        this.id_menu = id_menu;
        this.price = price;
        this.last_update = last_update;
        this.id_creator = id_creator;
        this.list_dish = list_dish;

    }

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

    public String getId_creator() {
        return id_creator;
    }

    public void setId_creator(String id_creator) {
        this.id_creator = id_creator;
    }

    public ArrayList<Integer> getList_dish() {
        return list_dish;
    }

    public void setList_dish(ArrayList<Integer> list_dish) {
        this.list_dish = list_dish;
    }

    @Override
    public String toString() {
        return "Menu{" +
                " name='" + name + '\'' +
                " id_menu='" + id_menu + '\'' +
                " price='" + price + '\'' +
                ", last_update='" + last_update + '\'' +
                ", id_creator='" + id_creator +
                ",list_dish='" + list_dish + '\'' +
                '}';

    }
}