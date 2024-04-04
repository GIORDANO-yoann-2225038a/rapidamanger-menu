package fr.univamu.iut.rapidamangermenu;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;

public class DishRepositoryAPI {
/*
    String url;

    public DishRepositoryAPI(String url) {
        this.url = url;
    }



    @Override
    public void close() {
    }



    @Override
    public DishMenu getDish(int dishId) {
        DishMenu myDish = null;

        Client client = ClientBuilder.newClient();
        WebTarget menuResource = client.target(url);
        WebTarget menuEndpoint = menuResource.path("dish/" + dishId);
        Response response = menuEndpoint.request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() == 200)
            myDish = response.readEntity(DishMenu.class);

        client.close();
        return myDish;
    }

    @Override
    public ArrayList<DishMenu> getAllMenus() {
        return null;
    }
/*
    @Override
    public boolean updateMenu1(int menuId, String productName, float price) {
        return false;
    }



    @Override
    public DishMenu getDishMenu(int dishId) {
        return null;
    }

 */

}

