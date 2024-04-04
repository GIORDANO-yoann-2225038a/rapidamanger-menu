package fr.univamu.iut.rapidamangermenu;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class MenuRepository  implements MenuRepositoryInterface, Closeable{
    protected Connection dbConnection;
    @Override
    public void close() {
        try{
            dbConnection.close();
        }
        catch (SQLException e){
            System.err.print(e.getMessage());
        }
    }

    @Override
    public Menu getMenu(String id ) {
        return null;
    }
    @Override
    public ArrayList<Menu> getAllMenu() {
        return null;
    }

    @Override
    public boolean deleteMenu (String id ) {
        return false;
    }

    @Override
    public String createMenu (String name,Float price, String last_update, String creator, ArrayList<Integer> list_dish) {
        return null;
    }

    @Override
    public boolean updateMenu (String id_menu, String name, String creator) {
        return false;
    }

    /*
    public void addDishToMenu(int menuId, DishMenu dishMenu) {

    }


    public void removeDishToMenu(int menuId, DishMenu dishId) {

    }

     */



}
