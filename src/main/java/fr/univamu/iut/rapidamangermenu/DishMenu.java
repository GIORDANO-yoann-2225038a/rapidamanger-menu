package fr.univamu.iut.rapidamangermenu;

public class DishMenu {
    private int dishId;

    private int menuId;



    public DishMenu() {}

    public DishMenu(int menuId, int dishId) {
        this.dishId = dishId;
        this.menuId = menuId;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return "MenuCommande{" +
                "dishId=" + dishId +
                ", menuId=" + menuId +
                '}';
    }

}
