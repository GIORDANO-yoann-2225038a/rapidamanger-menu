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
    public String createMenu (String name, Integer id_menu,Float price, String last_update, String id_creator, ArrayList<Integer> list_dish) {
        return null;
    }

    /*
    @Override
    public boolean UpdateMenu (Integer id_menu, String name, Integer last_update, String user_name) {
        return false;
    }



     */

}
