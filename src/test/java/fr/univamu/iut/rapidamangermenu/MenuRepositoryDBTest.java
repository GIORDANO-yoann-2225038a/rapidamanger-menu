package fr.univamu.iut.rapidamangermenu;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuRepositoryDBTest {
/*
    static MenuRepositoryDB menuRepo;

    @BeforeAll
    public static void init() {
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
        menuRepo = new MenuRepositoryDB(dbConnection);
    }

    @Test
    void getMenu() {
        Menu result = menuRepo.getMenu("1");

        assertEquals("Menu 1", result.getName());
        assertEquals(5.99f, result.getPrice());
        assertEquals("Creator 1", result.getCreator());
    }

    @Test
    void getAllMenu() {
        ArrayList<Menu> result = menuRepo.getAllMenu();

        assertEquals(2, result.size());
        assertEquals("Menu 1", result.get(0).getName());
        assertEquals("Menu 2", result.get(1).getName());
    }

    @Test
    public void createMenuTest() {
        ArrayList<Integer> listeEntiers = new ArrayList<>();
        listeEntiers.add(14);
        listeEntiers.add(45);
        // Créer un menu factice pour le test
        Menu menu = new Menu("menu test ", 17, 14F,"2002","tony", listeEntiers);

        // Appeler la méthode createMenu de votre repository
        repository.createMenu(menu);

        // Vérifier que le menu a été correctement créé en vérifiant son existence dans la base de données
        Menu retrievedMenu = repository.getMenu("menu test ", 17, 14F,"2002","tony", listeEntiers);
        assertNotNull(retrievedMenu);
        assertEquals("menu test ", retrievedMenu.getName());
        // Assurez-vous de nettoyer les données de test après le test si nécessaire
    }


    @Test
    void updateMenu() {
        menuRepo.updateMenu("2", "Updated Menu", "Updated Creator");

        assertEquals("Updated Menu", menuRepo.getMenu("2").getName());
        assertEquals("Updated Creator", menuRepo.getMenu("2").getCreator());
    }

    @Test
    void deleteMenu() {
        menuRepo.deleteMenu("2");

        assertEquals(1, menuRepo.getAllMenu().size());
    }
/*
    @Test
    void addDishToMenu() {
        menuRepo.addDishToMenu(1, "3");

        assertTrue(menuRepo.getMenu("1").getListDish().contains(3));
    }

    @Test
    void removeDishToMenu() {
        menuRepo.removeDishToMenu(1, "2");

        assertFalse(menuRepo.getMenu("1").getListDish().contains(2));
    }

 */

}
