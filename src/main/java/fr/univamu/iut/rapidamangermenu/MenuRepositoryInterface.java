package fr.univamu.iut.rapidamangermenu;

import java.util.*;
public interface MenuRepositoryInterface {

    public void close();

    public Menu getMenu(String id );

    public ArrayList<Menu> getAllMenu();

    public boolean deleteMenu (String id );

    public String createMenu (String name, Float price, String creator, ArrayList<Integer> list_dish);

    public boolean updateMenu (String id_menu, String name, String creator);


    void addDishToMenu(int menuId, String dishMenu);


    void removeDishToMenu(int menuId, String dishId);





}
