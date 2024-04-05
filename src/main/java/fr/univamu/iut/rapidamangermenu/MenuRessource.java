package fr.univamu.iut.rapidamangermenu;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

// Ressource REST pour les menus
@Path("/menu")
public class MenuRessource {

    private MenuService service;

    // Constructeur par défaut
    public MenuRessource(){}

    // Constructeur avec injection de dépendance du service de menu
    public MenuRessource(MenuRepositoryInterface menuRepo) {
        this.service = new MenuService(menuRepo);
    }

    // Constructeur avec injection de dépendance du service de menu
    public MenuRessource(MenuService service) {
        this.service = service;
    }

    private Float getDishPriceFromAPI(int dishId) {
        String jsonResponse = "julot";
        try {
            String uri = "http://51.15.238.170:8080/rapidamanger-1.0-SNAPSHOT-moi/api/dish" + dishId;
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Maintenant, response contient le contenu JSON récupéré
                jsonResponse = response.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject price = new JSONObject(jsonResponse);

        // Assurez-vous que le prix est un Float
        Float prix = price.getFloat("price");
        return prix;
    }

    // Méthode pour récupérer tous les menus
    @GET
    @Produces("application/json")
    public Response getAllMenu() {
        return Response.ok(service.getAllMenuJSON()).build();
    }

    // Méthode pour récupérer un menu en fonction de son identifiant
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getMenu(@PathParam("id") String id) {
        String result = service.getMenuJSON(id);

        // Si le menu n'a pas été trouvé, retourne une réponse NOT_FOUND
        if (result == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(result).build();
    }

    // Méthode pour supprimer un menu en fonction de son identifiant
    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public Response deleteMenu(@PathParam("id") String id) {
        String result = service.deleteMenu(id);

        // Si le menu n'a pas été trouvé, retourne une réponse NOT_FOUND
        if (result == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(result).build();
    }

    // Méthode pour créer un nouveau menu
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response createMenu(String menuJson) {
        JSONObject obj = new JSONObject(menuJson);

        // Extraction des données du JSON
        String name = obj.getString("name");
        Float price = Float.parseFloat(obj.getString("price"));
        String creator = obj.getString("creator");
        JSONArray listDishArray = obj.getJSONArray("list_dish");
        ArrayList<Integer> list_dish = new ArrayList<>();
        for (int i = 0; i < listDishArray.length(); i++) {
            list_dish.add(listDishArray.getInt(i));
        }

        // Création du menu
        String result = service.createMenu(name, price, creator, list_dish);

        // Si la création a échoué, retourne une réponse BAD_REQUEST
        if (result == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        return Response.ok(result).build();
    }

    // Méthode pour mettre à jour un menu
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateMenu(@PathParam("id") String menuId, String menuJson) {
        JSONObject newValueOfMenu = new JSONObject(menuJson);
        JSONObject currentMenu = new JSONObject(service.getMenuJSON(menuId));

        // Initialisation avec les valeurs actuelles du menu
        String name = currentMenu.getString("name");
        String creator = currentMenu.getString("creator");
        Float price = currentMenu.getFloat("price");

        // Mise à jour des valeurs du menu avec celles du JSON reçu
        if (newValueOfMenu.has("name")) {
            name = newValueOfMenu.getString("name");
        }
        if (newValueOfMenu.has("creator")) {
            creator = newValueOfMenu.getString("creator");
        }
        if (newValueOfMenu.has("price")) {
            price = newValueOfMenu.getFloat("price");
        }

        // Mise à jour du menu
        String result = service.updateMenu(menuId, String.valueOf(price), name, creator);

        // Si le menu n'a pas été trouvé ou que la mise à jour a échoué, retourne une réponse NOT_FOUND
        if (result == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(result).build();
    }

    // Méthode pour ajouter un plat à un menu
    @PUT
    @Path("{id}/add")
    @Consumes("application/json")
    public Response addDishToMenu(@PathParam("id") int id, String dish) {
        JSONObject obj = new JSONObject(dish);
        service.addDishToMenu(id,obj.getString("dishId"));

            int dishId = obj.getInt("dishId");

            // Récupérer le prix du menu à partir de l'API externe
            float dishPrice = getDishPriceFromAPI(dishId);

            // Récupérer la commande
            JSONObject currentMenu = new JSONObject(service.getMenuJSON(String.valueOf(id)));

            // Ajouter le prix du nouveau menu au prix total de la commande
            float totalPrice = currentMenu.getFloat("price") + dishPrice;

            // Mettre à jour le prix total de la commande dans l'objet JSON de la commande
            currentMenu.put("price", totalPrice);

            // Mettre à jour la commande dans le service
            String result = service.updateMenu(String.valueOf(id), String.valueOf(totalPrice), currentMenu.getString("name"),  currentMenu.getString("creator"));

            // Vérifier si la mise à jour de la commande a réussi
            if (result == null)
                return Response.status(Response.Status.NOT_FOUND).build();

            return Response.status(Response.Status.OK).entity("Menu ajouté à la commande").build();
    }


    // Méthode pour supprimer un plat d'un menu
    @PUT
    @Path("{id}/remove")
    @Consumes("application/json")
    public Response removeDishToMenu(@PathParam("id") int id, String dish) {
        JSONObject obj = new JSONObject(dish);
        service.removeDishToMenu(id, obj.getString("dishId"));

        int dishId = obj.getInt("dishId");

        // Récupérer le prix du menu à partir de l'API externe
        float dishPrice = getDishPriceFromAPI(dishId);

        // Récupérer la commande
        JSONObject currentMenu = new JSONObject(service.getMenuJSON(String.valueOf(id)));

        // Ajouter le prix du nouveau menu au prix total de la commande
        float totalPrice = currentMenu.getFloat("price") - dishPrice;

        // Mettre à jour le prix total de la commande dans l'objet JSON de la commande
        currentMenu.put("price", totalPrice);

        // Mettre à jour la commande dans le service
        String result = service.updateMenu(String.valueOf(id), String.valueOf(totalPrice), currentMenu.getString("name"),  currentMenu.getString("creator"));

        // Vérifier si la mise à jour de la commande a réussi
        if (result == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity("plat supprimé du commande").build();
    }
}
