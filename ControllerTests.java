
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ControllerTests {

    Controller controller = Controller.instanceOf();
    String fName = "paulina";
    String lName = "aguirre";
    String email = "paulinaa3@arizona.edu";
    String username = "paulinaa3";
    String password = "password1";

    String fName2 = "kiana";
    String lName2 = "abney";
    String email2 = "kabney1@arizona.edu";
    String username2 = "kabney1";
    String password2 = "password2";

    Expense expense = new Expense(15.99, LocalDate.of(2024, 9, 18), "fun", Category.ENTERTAINMENT);
    Expense expense2 = new Expense(100.00, LocalDate.of(2024, 11, 25), "water", Category.UTILITIES);

    private void setUpUser() {
        controller.register(fName, lName, email, username, password);
    }

    @Test
    void testSingleton() {
        Controller controller2 = Controller.instanceOf();
        assertEquals(controller, controller2);
    }

    @Test
    void testLogin() {
        controller.register(fName, lName, email, username, password);
        assertDoesNotThrow(() -> controller.login("paulinaa3", "password1"));
        controller.logout();

        controller.register(fName2, lName2, email2, username2, password2);
        assertDoesNotThrow(() -> controller.login("kabney1", "password2"));
        controller.logout();

        //controller.register(fName2, lName2, email2, username2, password2);
        //controller.register(fName, lName, email, username, password);
        //controller.login(username, password);
    }

    @Test
    void testInvalidLogin() {
        controller.register("stormy", "aguirre", "stormy@arizone.edu", "stormy", "password3");
        assertThrows(NoSuchElementException.class, () -> controller.login("stormy", "password123"));
        controller.logout();
    }

    @Test
    void testAddBudget() {
        controller.login(username, password); //paulina
        Category cat = Category.UTILITIES;
        double amount = 200.00;
        controller.addBudget(cat, amount);

        Optional<Double> budget = controller.findUser("paulinaa3").getBudgetByCategory(cat);
        assertTrue(budget.isPresent());
        assertEquals(amount, budget.get());
        controller.logout();
    }

    @Test
    void testGetByCategory() {
        //controller.logout();
        controller.login(username, password); //paulina
        controller.addExpense(expense); //entertainment
        Category cat = Category.ENTERTAINMENT;
        ArrayList<Expense> expected = controller.getByCategory(cat);
        ArrayList<Expense> test = new ArrayList<>();
        test.add(expense);
        assertEquals(expected.size(), 1);
        controller.logout();

    }

    @Test
    void testGetPercentSpending() {
        //controller.logout();
        //controller.register(fName, lName, email, username, password);
        controller.login(username, password);
        controller.addExpense(expense);
        controller.addExpense(expense2);

        Category cat = Category.ENTERTAINMENT;
        double amount = controller.getpercentSpending(cat);
        assertEquals(amount, 13.785671, 0.000001);
        controller.logout();
    }

    @Test
    void testExportExpenses() {
        setUpUser();
        controller.addExpense(expense);
        assertTrue(controller.exportExpenses());
    }

    @Test
    void testAddFileThowException() {
        setUpUser();
        String file = "idontexists.txt";
        assertThrows(FileNotFoundException, () -> {
            controller.addFile(file);
        });

    }

    @Test
    void testAddFile() throws FileNotFoundException {
        setUpUser();
        String fName = "expenses.txt";
        assertEquals("", controller.addFile(fName));
    }

//    
//    @Test 
//    void testSaveAndLoadData() {
//    	//TODO
//    }
    @Test
    public void testGetByDate() {
        setUpUser();
        controller.addExpense(expense);
        controller.addExpense(expense2);
        ArrayList<Expense> expenses = controller.getByDate(LocalDate.of(2024, 9, 18), LocalDate.of(2024, 11, 25));
        assertEquals(2, expenses.size());

        ArrayList<Expense> expenses2 = controller.getByDate(LocalDate.of(2024, 9, 18), LocalDate.of(2024, 10, 25));
        assertEquals(1, expenses2.size());
    }

    @Test
    public void testGetByCategory() {
        setUpUser();
        controller.addExpense(expense);
        controller.addExpense(expense2);
        ArrayList<Expense> expenses = controller.getByCategory(expense.getCategory());
        assertEquals(expense.getDescription(), expenses.get(0).getDescription());
        ArrayList<Expense> expenses2 = controller.getByCategory(expense2.getCategory());
        assertEquals(expense2.getDescription(), expenses2.get(0).getDescription());

    }

    @Test
    public void testGetByDateCategory() {
        setUpUser();
        controller.addExpense(expense);
        controller.addExpense(expense2);
        ArrayList<Expense> expenses = controller.getbyDateCategory(expense.getCategory(), LocalDate.of(2024, 9, 18), LocalDate.of(2024, 11, 25));
        assertEquals(expense.getDescription(), expenses.get(0).getDescription());
        ArrayList<Expense> expenses2 = controller.getbyDateCategory(expense2.getCategory(), LocalDate.of(2024, 9, 18), LocalDate.of(2024, 11, 23));
        assertEquals(0, expenses2.size());

    }

    @Test
    void testAddExpense() {
        setUpUser();
        assertEquals(0, controller.getAllExpenses().size());
        controller.addExpense(expense);

        assertEquals(1, controller.getAllExpenses().size());
        assertEquals(expense, controller.getAllExpenses().get(0));

        controller.addExpense(expense2);
        assertEquals(2, controller.getAllExpenses().size());
        assertEquals(expense2, controller.getAllExpenses().get(1));
    }

    @Test
    void testEditExpense() {
        setUpUser();
        controller.addExpense(expense);
        controller.editExpense(expense2, expense.getId());

        Expense result = controller.getExpense(expense.getId());
        assertEquals(100.00, result.getAmount(), .0001);
        assertEquals("water", result.getDescription());
    }

    @Test
    public void testDeleteExpense() {
        setUpUser();
        controller.addExpense(expense);
        assertEquals(1, controller.getAllExpenses().size());
        controller.deleteExpense(expense.getId());
        assertEquals(0, controller.getAllExpenses().size());
    }

}
