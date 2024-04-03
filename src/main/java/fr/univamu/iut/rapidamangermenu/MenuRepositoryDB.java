package fr.univamu.iut.rapidamangermenu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuRepositoryDB implements MenuRepositoryInterface, Cloneable{

    protected Connection dbConnection;


    public MenuRepositoryDB(String infoConnection, String user, String pwd ) throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection( infoConnection, user, pwd ) ;
    }

    public MenuRepositoryDB(Connection connection) {
        this.dbConnection = connection;
    }
    @Override
    public void close() {
        try{
            dbConnection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Menu getMenu(String id) {
        Menu selectedMenu = null;

        String query = "SELECT * " +
                "FROM menu " +
                "LEFT JOIN compos_menu ON menu.id_menu = compos_menu.id_menu " +
                "WHERE menu.id_menu = ?";

        try {
            try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
                ps.setString(1, id);
                ResultSet result = ps.executeQuery();

                if (result.next()) {
                    String name = result.getString("name");
                    Integer id_menu = result.getInt("id_menu");
                    Float price = result.getFloat("price");
                    String last_update = result.getString("last_update");
                    String id_creator = result.getString("id_creator");

                    ArrayList<Integer> list_dish = new ArrayList<>();
                    do {
                        int id_dish = result.getInt("id_dish");
                        int number = result.getInt("number");
                        for (int i = 0; i < number; i++) {
                            list_dish.add(id_dish);
                        }
                    } while (result.next());

                    selectedMenu = new Menu(name, id_menu, price, last_update, id_creator, list_dish);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedMenu;
    }






    @Override
    public ArrayList<Menu> getAllMenu() {
        ArrayList<Menu> listMenu = new ArrayList<>();

        String query = "SELECT * FROM menu";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ResultSet result = ps.executeQuery();

            while (result.next()) {
                String name = result.getString("name");
                Integer id_menu = result.getInt("id_menu");
                Float price = result.getFloat("price");
                String last_update = result.getString("last_update");
                String id_creator = result.getString("id_creator");

                ArrayList<Integer> listDish = new ArrayList<>();

                String recupdish = "SELECT * FROM compos_menu WHERE id_menu=?";
                try (PreparedStatement ps1 = dbConnection.prepareStatement(recupdish)) {
                    ps1.setInt(1, id_menu);
                    ResultSet result1 = ps1.executeQuery();

                    while (result1.next()) {
                        int id_dish = result1.getInt("id_dish");
                        int number = result1.getInt("number");
                        for (int i = 0; i < number; i++) {
                            listDish.add(id_dish);
                        }
                    }
                }

                Menu currentMenu = new Menu(name, id_menu, price, last_update, id_creator, listDish);
                listMenu.add(currentMenu);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listMenu;
    }



    @Override
    public boolean deleteMenu(String id) {
        String deleteMenuQuery = "DELETE FROM menu WHERE id_menu=?";
        String deleteComposMenuQuery = "DELETE FROM compos_menu WHERE id_menu=?";
        int nbRowModified = 0;

        // construction et exécution d'une requête préparée pour supprimer le menu
        try (PreparedStatement psDeleteMenu = dbConnection.prepareStatement(deleteMenuQuery);
             PreparedStatement psDeleteComposMenu = dbConnection.prepareStatement(deleteComposMenuQuery)) {
            // Supprimer d'abord les entrées dans la table compos_menu liées à ce menu
            psDeleteComposMenu.setString(1, id);
            psDeleteComposMenu.executeUpdate();

            // Ensuite, supprimer le menu lui-même
            psDeleteMenu.setString(1, id);
            nbRowModified = psDeleteMenu.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (nbRowModified != 0);
    }


    @Override
    public String createMenu(String name, Integer id_menu, Float price, String last_update, String id_creator, ArrayList<Integer> list_dish) {
        String queryMenu = "INSERT INTO menu(name, id_menu, price, last_update, id_creator) VALUES (?, ?, ?, ?, ?)";
        String queryComposMenu = "INSERT INTO compos_menu(id_menu, id_dish, number) VALUES (?, ?, ?)";
        int newId = -1;

        try (PreparedStatement ps = dbConnection.prepareStatement(queryMenu, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setInt(2, id_menu);
            ps.setFloat(3, price);
            ps.setString(4, last_update);
            ps.setString(5, id_creator);

            int nbRowModified = ps.executeUpdate();

            if (nbRowModified == 0) {
                throw new SQLException("Insertion du menu a échoué, aucune ligne modifiée.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    newId = rs.getInt(1); // Récupérer l'ID généré
                } else {
                    throw new SQLException("Insertion du menu a échoué, aucun ID généré.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la création du menu : " + e.getMessage());
        }

        // Maintenant, insérez les entrées dans la table compos_menu
        try (PreparedStatement ps = dbConnection.prepareStatement(queryComposMenu)) {
            for (Integer dishId : list_dish) {
                ps.setInt(1, newId); // ID du nouveau menu
                ps.setInt(2, dishId); // ID du plat
                ps.setInt(3, 1); // Nombre (à modifier selon vos besoins)

                ps.addBatch(); // Ajouter la requête à une batch pour l'exécuter une fois
            }
            // Exécuter toutes les requêtes en une seule transaction
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion des éléments de menu : " + e.getMessage());
        }

        return String.valueOf(newId);
    }




}
