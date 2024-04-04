package fr.univamu.iut.rapidamangermenu;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class MenuService {

    protected MenuRepositoryInterface menuRepo;

    public MenuService(MenuRepositoryInterface menuRepo) {
        this.menuRepo = menuRepo;
    }

    public String getAllMenuJSON() {
        ArrayList<Menu> allMenu = menuRepo.getAllMenu();
        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allMenu);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return result;

    }

    public String getMenuJSON(String id) {
        String result = null;
        Menu myMenu = menuRepo.getMenu(id);

        // si le menu a été trouvé
        if( myMenu != null ) {

            // création du json et conversion du menu
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myMenu);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }


    public String deleteMenu(String id) {
        String result = null;

        // si le plat a été trouvé
        if( menuRepo.deleteMenu(id) ) {
            JSONObject deletedMenu = new JSONObject();
            deletedMenu.put("id", Integer.parseInt(id));
            result = deletedMenu.toString();
        }
        return result;
    }

    public String createMenu(String name, Float price, String creator, ArrayList<Integer> list_dish) {
        try (Jsonb jsonb = JsonbBuilder.create()) {
            String idOfNewMenu = menuRepo.createMenu(name, price, creator, list_dish);
            return jsonb.toJson(Collections.singletonMap("id", Integer.parseInt(idOfNewMenu)));
        } catch (Exception e) {
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }

    public String updateMenu(String id_menu, String name, String creator) {
        String result = null;

        // si le plat a été trouvé
        if( menuRepo.updateMenu(id_menu, name, creator)) {
            JSONObject updatedMenu = new JSONObject();
            updatedMenu.put("id_menu", Integer.parseInt(id_menu));
            result = updatedMenu.toString();
        }

        return result;
    }

    public void addDishToMenu(int menuId, String dishMenu) {
        menuRepo.addDishToMenu(menuId, dishMenu);
    }

    public void removeDishToMenu(int menuId, String dishId) {
        menuRepo.removeDishToMenu(menuId, dishId);
    }


}
