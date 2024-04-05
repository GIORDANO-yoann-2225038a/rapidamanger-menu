package fr.univamu.iut.rapidamangermenu;

import org.json.JSONObject;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;
import java.sql.Timestamp;
import java.util.Date;

public class MenuRepositoryDB implements MenuRepositoryInterface, Cloneable {

    protected Connection dbConnection;

    // Constructeur prenant les informations de connexion à la base de données
    public MenuRepositoryDB(String infoConnection, String user, String pwd ) throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection( infoConnection, user, pwd ) ;
    }

    // Constructeur prenant une connexion existante à la base de données
    public MenuRepositoryDB(Connection connection) {
        this.dbConnection = connection;
    }

    // Méthode pour fermer la connexion à la base de données
    @Override
    public void close() {
        try {
            dbConnection.close();
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    // Méthode pour récupérer un menu en fonction de son identifiant
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

    // Méthode pour récupérer tous les menus
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

                ArrayList<Integer> list_dish = new ArrayList<>();

                String recupdish = "SELECT * FROM compos_menu WHERE id_menu=?";
                try (PreparedStatement ps1 = dbConnection.prepareStatement(recupdish)) {
                    ps1.setInt(1, id_menu);
                    ResultSet result1 = ps1.executeQuery();

                    while (result1.next()) {
                        int id_dish = result1.getInt("id_dish");
                        list_dish.add(id_dish);
                    }
                }

                Menu currentMenu = new Menu(name, id_menu, price, last_update, creator, list_dish);
                listMenu.add(currentMenu);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listMenu;
    }

    // Méthode pour supprimer un menu en fonction de son identifiant
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

    // Méthode pour créer un nouveau menu
    @Override
    public String createMenu(String name, Float price, String creator, ArrayList<Integer> list_dish) {
        String queryMenu = "INSERT INTO menu(name, price, last_update, creator) VALUES (?, ?, NOW(), ?)";
        String queryComposMenu = "INSERT INTO compos_menu(id_menu, id_dish) VALUES (?, ?)";
        int newId = -1;

        try (PreparedStatement ps = dbConnection.prepareStatement(queryMenu, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setFloat(2, price);
            ps.setString(3, creator);

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

    // Méthode pour mettre à jour les informations d'un menu
    @Override
    public boolean updateMenu(String id_menu, String price, String name, String creator) {
        String queryMenu = "UPDATE menu SET name=?, price=?, creator=?, last_update=? WHERE id_menu=?";
        int nbRowModified = 0;

        // Construction et exécution d'une requête préparée pour mettre à jour le menu
        try (PreparedStatement psMenu = dbConnection.prepareStatement(queryMenu)) {
            psMenu.setString(1, name);
            psMenu.setString(2, price);
            psMenu.setString(3, creator);
            psMenu.setTimestamp(4, new Timestamp(new Date().getTime()));
            psMenu.setString(5, id_menu);
            nbRowModified += psMenu.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (nbRowModified != 0);
    }

    // Méthode pour ajouter un plat à un menu
    @Override
    public void addDishToMenu(int menuId, String dishId) {
        String query = "INSERT INTO compos_menu (id_menu, id_dish) VALUES (?, ?)";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, menuId);
            ps.setString(2, dishId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Méthode pour supprimer un plat d'un menu
    @Override
    public void removeDishToMenu(int menuId, String dishId) {
        String query = "DELETE FROM compos_menu WHERE id_menu=? AND id_dish=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, menuId);
            ps.setString(2, dishId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
