package fr.univamu.iut.rapidamangermenu;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

// Service pour la manipulation des menus
public class MenuService {

    protected MenuRepositoryInterface menuRepo;

    // Constructeur prenant en paramètre un repository de menu
    public MenuService(MenuRepositoryInterface menuRepo) {
        this.menuRepo = menuRepo;
    }

    // Méthode pour récupérer tous les menus au format JSON
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

    // Méthode pour récupérer un menu au format JSON en fonction de son identifiant
    public String getMenuJSON(String id) {
        String result = null;
        Menu myMenu = menuRepo.getMenu(id);

        // si le menu a été trouvé, le convertit en JSON
        if( myMenu != null ) {
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myMenu);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    // Méthode pour supprimer un menu en fonction de son identifiant
    public String deleteMenu(String id) {
        String result = null;

        // si le menu a été supprimé, retourne son identifiant au format JSON
        if( menuRepo.deleteMenu(id) ) {
            JSONObject deletedMenu = new JSONObject();
            deletedMenu.put("id", Integer.parseInt(id));
            result = deletedMenu.toString();
        }
        return result;
    }

    // Méthode pour créer un nouveau menu avec les informations fournies
    public String createMenu(String name, Float price, String creator, ArrayList<Integer> list_dish) {
        try (Jsonb jsonb = JsonbBuilder.create()) {
            String idOfNewMenu = menuRepo.createMenu(name, price, creator, list_dish);
            return jsonb.toJson(Collections.singletonMap("id", Integer.parseInt(idOfNewMenu)));
        } catch (Exception e) {
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }

    // Méthode pour mettre à jour un menu avec les nouvelles informations fournies
    public String updateMenu(String id_menu, String price, String name,  String creator) {
        String result = null;

        // si le menu a été mis à jour, retourne son identifiant au format JSON
        if( menuRepo.updateMenu(id_menu, price, name, creator)) {
            JSONObject updatedMenu = new JSONObject();
            updatedMenu.put("id_menu", Integer.parseInt(id_menu));
            result = updatedMenu.toString();
        }

        return result;
    }

    // Méthode pour ajouter un plat à un menu
    public void addDishToMenu(int menuId, String dishMenu) {
        menuRepo.addDishToMenu(menuId, dishMenu);
    }

    // Méthode pour supprimer un plat d'un menu
    public void removeDishToMenu(int menuId, String dishId) {
        menuRepo.removeDishToMenu(menuId, dishId);
    }
}
