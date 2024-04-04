package fr.univamu.iut.rapidamangermenu;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


@Path("/menu")
public class MenuRessource {

    private MenuService service;


    public MenuRessource(){}

    public MenuRessource(MenuRepositoryInterface menuRepo) {
        this.service = new MenuService(menuRepo);
    }

    public MenuRessource(MenuService service) {
        this.service = service;
    }

    @GET
    @Produces("application/json")
    public Response getAllMenu() {
        return Response.ok(service.getAllMenuJSON()).build();

    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getMenu(@PathParam("id") String id) {

        String result = service.getMenuJSON(id);

        // si le plat n'a pas été trouvé
        if( result == null )
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(result).build();
    }


    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public Response deleteMenu(@PathParam("id") String id) {

        String result = service.deleteMenu(id);

        // si le plat n'a pas été trouvé
        if( result == null )
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(result).build();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response createMenu(String menuJson) {
        JSONObject obj = new JSONObject(menuJson);

        String name = obj.getString("name");
        Float price = Float.parseFloat(obj.getString("price"));
        String last_update = obj.getString("last_update");
        String creator = obj.getString("creator");

        // Suppose que list_dish est un tableau JSON d'entiers
        JSONArray listDishArray = obj.getJSONArray("list_dish");
        ArrayList<Integer> list_dish = new ArrayList<>();
        for (int i = 0; i < listDishArray.length(); i++) {
            list_dish.add(listDishArray.getInt(i));
        }

        String result = service.createMenu(name, price, last_update, creator, list_dish);

        // si la création a échoué
        if (result == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        return Response.ok(result).build();
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateMenu(@PathParam("id") String menuId, String menuJson) {
        JSONObject newValueOfMenu = new JSONObject(menuJson);
        JSONObject currentMenu = new JSONObject(service.getMenuJSON(menuId));

        // Initiate with the current value of the dish
        String name = currentMenu.getString("name");
        String creator = currentMenu.getString("creator");


        // Change the value present in the body of the PUT request with the new value
        if (newValueOfMenu.has("name")) {
            name = newValueOfMenu.getString("name");
        }
        if (newValueOfMenu.has("creator")) {
            creator = newValueOfMenu.getString("creator");
        }


        String result = service.updateMenu(menuId, name, creator);



        // si le plat n'a pas été trouvé ou que la modification a échoué
        if( result == null )
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(result).build();

    }


 /*
    @PUT
    @Path("{id}/add")
    @Consumes("application/json")
    public Response addDishToMenu(@PathParam("id") int id, DishMenu dishMenu) {
        service.addDishToMenu(id, dishMenu);
        return Response.status(Response.Status.OK).entity("plats ajouté à la Commande").build();
    }

    @PUT
    @Path("{id}/remove")
    @Consumes("application/json")
    public Response removeDishToMenu(@PathParam("id") int id, DishMenu dishMenu) {
        service.removeDishToMenu(id, dishMenu);
        return Response.status(Response.Status.OK).entity("plat supprimé du commande").build();
    }

  */

}