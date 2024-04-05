package fr.univamu.iut.rapidamangermenu;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
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

    /**
     * Récupère tous les menus au format JSON.
     * @return Une chaîne JSON représentant tous les menus.
     */
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

    /**
     * Récupère un menu au format JSON en fonction de son identifiant.
     * @param id L'identifiant du menu à récupérer.
     * @return Une chaîne JSON représentant le menu, ou null s'il n'existe pas.
     */
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

    /**
     * Supprime un menu en fonction de son identifiant.
     * @param id L'identifiant du menu à supprimer.
     * @return Une chaîne JSON représentant l'identifiant du menu supprimé, ou null si le menu n'a pas été trouvé.
     */
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

    /**
     * Crée un nouveau menu avec les informations fournies.
     * @param name Le nom du nouveau menu.
     * @param price Le prix du nouveau menu.
     * @param creator Le créateur du nouveau menu.
     * @param list_dish La liste des plats du nouveau menu.
     * @return Une chaîne JSON représentant l'identifiant du nouveau menu créé.
     */
    public String createMenu(String name, Float price, String creator, ArrayList<Integer> list_dish) {
        try (Jsonb jsonb = JsonbBuilder.create()) {
            String idOfNewMenu = menuRepo.createMenu(name, price, creator, list_dish);
            return jsonb.toJson(Collections.singletonMap("id", Integer.parseInt(idOfNewMenu)));
        } catch (Exception e) {
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }

    /**
     * Met à jour un menu avec les nouvelles informations fournies.
     * @param id_menu L'identifiant du menu à mettre à jour.
     * @param price Le nouveau prix du menu.
     * @param name Le nouveau nom du menu.
     * @param creator Le nouveau créateur du menu.
     * @return Une chaîne JSON représentant l'identifiant du menu mis à jour, ou null si le menu n'a pas été trouvé.
     */
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

    /**
     * Ajoute un plat à un menu.
     * @param menuId L'identifiant du menu auquel ajouter le plat.
     * @param dishMenu L'identifiant du plat à ajouter.
     */
    public void addDishToMenu(int menuId, String dishMenu) {
        menuRepo.addDishToMenu(menuId, dishMenu);
    }

    /**
     * Supprime un plat d'un menu.
     * @param menuId L'identifiant du menu duquel supprimer le plat.
     * @param dishId L'identifiant du plat à supprimer.
     */
    public void removeDishToMenu(int menuId, String dishId) {
        menuRepo.removeDishToMenu(menuId, dishId);
    }
}
