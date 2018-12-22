package linear.algebra.solver;

public class Interface {

    public static void main(String args[]) {
        Menu menu = new Menu();
        boolean finish = false;
        int options;
        do {
            options = menu.showMainMenu();
            finish = menu.showSubmenu(options);
        } while (!finish);
        System.out.println("Thank You!");
    }
}
