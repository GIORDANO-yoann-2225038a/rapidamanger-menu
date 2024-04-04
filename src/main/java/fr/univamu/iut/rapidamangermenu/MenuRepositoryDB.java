package fr.univamu.iut.rapidamangermenu;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class MenuRepositoryDB implements MenuRepositoryInterface, Cloneable {

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
        try {
            dbConnection.close();
        } catch(SQLException e) {
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
                    String creator = result.getString("creator");

                    ArrayList<Integer> list_dish = new ArrayList<>();
                    do {
                        int id_dish = result.getInt("id_dish");
                        list_dish.add(id_dish);
                    } while (result.next());

                    selectedMenu = new Menu(name, id_menu, price, last_update, creator, list_dish);
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
                int id_menu = result.getInt("id_menu");
                Float price = result.getFloat("price");
                String last_update = result.getString("last_update");
                String creator = result.getString("creator");

                ArrayList<Integer> listDish = new ArrayList<>();

                String recupdish = "SELECT * FROM compos_menu WHERE id_menu=?";
                try (PreparedStatement ps1 = dbConnection.prepareStatement(recupdish)) {
                    ps1.setInt(1, id_menu);
                    ResultSet result1 = ps1.executeQuery();

                    while (result1.next()) {
                        int id_dish = result1.getInt("id_dish");
                        listDish.add(id_dish);
                    }
                }

                Menu currentMenu = new Menu(name, id_menu, price, last_update, creator, listDish);
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

        try (PreparedStatement psDeleteMenu = dbConnection.prepareStatement(deleteMenuQuery);
             PreparedStatement psDeleteComposMenu = dbConnection.prepareStatement(deleteComposMenuQuery)) {
            psDeleteComposMenu.setString(1, id);
            psDeleteComposMenu.executeUpdate();

            psDeleteMenu.setString(1, id);
            nbRowModified = psDeleteMenu.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (nbRowModified != 0);
    }

    @Override
    public String createMenu(String name, Float price, String last_update, String creator, ArrayList<Integer> list_dish) {
        String queryMenu = "INSERT INTO menu(name, price, last_update, creator) VALUES (?, ?, ?, ?)";
        String queryComposMenu = "INSERT INTO compos_menu( id_dish) VALUES (?)";
        int newId = -1;

        try (PreparedStatement ps = dbConnection.prepareStatement(queryMenu, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setFloat(2, price);
            ps.setString(3, last_update);
            ps.setString(4, creator);

            int nbRowModified = ps.executeUpdate();

            if (nbRowModified == 0) {
                throw new SQLException("Insertion du menu a échoué, aucune ligne modifiée.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    newId = rs.getInt(1);
                } else {
                    throw new SQLException("Insertion du menu a échoué, aucun ID généré.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la création du menu : " + e.getMessage());
        }

        try (PreparedStatement ps = dbConnection.prepareStatement(queryComposMenu)) {
            for (Integer dishId : list_dish) {
                ps.setInt(1, newId);
                ps.setInt(2, dishId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion des éléments de menu : " + e.getMessage());
        }

        return String.valueOf(newId);
    }


    @Override
    public boolean updateMenu(String id_menu, String name, String creator) {
        String queryMenu = "UPDATE menu SET name=?, creator=? WHERE id_menu=?";

        int nbRowModified = 0;

        // Construction et exécution d'une requête préparée pour mettre à jour le menu
        try (PreparedStatement psMenu = dbConnection.prepareStatement(queryMenu)) {
            psMenu.setString(1, name);
            psMenu.setString(2, creator);
            psMenu.setString(3, id_menu);
            nbRowModified += psMenu.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Construction et exécution de requêtes préparées pour mettre à jour les plats associés existant

        return (nbRowModified != 0);
    }

/*
    @Override
    public void addDishToMenu(int menuId, DishMenu dishMenu) {
        String query = "INSERT INTO compos_menu (list_dish, id_menu) VALUES (?, ?)";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, menuId);
            ps.setInt(2, dishMenu.getDishId());


            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void removeDishToMenu(int menuId, DishMenu dishId) {
        String query = "DELETE FROM compos_menu WHERE list_dish=? AND id_menu=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, menuId);
            ps.setInt(2, dishId.getDishId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

 */

}
