package fr.univamu.iut.rapidamangermenu;

import java.util.*;
public interface MenuRepositoryInterface {

    public void close();

    public Menu getMenu(String id );

    public ArrayList<Menu> getAllMenu();

    public boolean deleteMenu (String id );

    public String createMenu (String name, Integer id_menu,Float price, String last_update, String creator, ArrayList<Integer> list_dish);


/*
    public boolean UpdateMenu (String name, Integer last_update, String user_name, Integer id_dish);


*/
}
