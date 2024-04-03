package fr.univamu.iut.rapidamangermenu;


import fr.univamu.iut.rapidamangermenu.MenuRepositoryDB;
import fr.univamu.iut.rapidamangermenu.MenuRessource;
import fr.univamu.iut.rapidamangermenu.MenuService;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class RapidamangerApplication extends Application {

    @Override
    public Set<Object> getSingletons() {
        Set<Object> set = new HashSet<>();

        MenuService menuService = null;
        String bd_info = "jdbc:mariadb://mysql-yoann.alwaysdata.net/yoann_rapidamanger";
        String bd_user = "yoann";
        String mdp = "Yoann-04300";
        Connection dbConnection = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            dbConnection = DriverManager.getConnection(bd_info, bd_user, mdp);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);

        }

        MenuRepositoryDB dbMenu = new MenuRepositoryDB(dbConnection);
        menuService = new MenuService(dbMenu);


        // Création de la ressource en lui passant paramètre les services à exécuter en fonction
        // des différents endpoints proposés (i.e. requêtes HTTP acceptées)
        set.add(new MenuRessource(menuService));

        return set;
    }
}