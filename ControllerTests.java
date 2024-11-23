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
	

	
	@Test
	void testSingleton() {
		Controller controller2 = Controller.instanceOf();
		assertEquals(controller, controller2);
	}
	
	
    @Test
    void testLogin() {
    	controller.register(fName, lName, email, username, password);
        assertDoesNotThrow(() -> controller.login("paulinaa3", "password1"));
//        
//    	controller.register(fName2, lName2, email2, username2, password2);
//        assertDoesNotThrow(() -> controller.login("kabney1", "password2"));
    	
    	//controller.register(fName2, lName2, email2, username2, password2);
    	
    	//controller.register(fName, lName, email, username, password);
    	//controller.login(username, password);
        
    }
    
    
    @Test
    void testInvalidLogin() {
        controller.register("stormy", "aguirre", "stormy@arizone.edu", "stormy", "password3");
        assertThrows(NoSuchElementException.class, () -> controller.login("stormy", "password123"));
    }
    
    
    @Test
    void testAddBudget() {
    	//controller.login(username, password);
    	Category cat = Category.UTILITIES;
    	double amount = 200.00;
    	controller.addBudget(cat, amount);
    	
    	Optional<Double> budget = controller.findUser("paulinaa3").getBudgetByCategory(cat);
        assertTrue(budget.isPresent());
        assertEquals(amount, budget.get());
    }
  
    
    @Test
    void testGetByCategory() {
    	//controller.login(username, password);
    	Category cat = Category.ENTERTAINMENT;
    	ArrayList<Expense> expected = controller.getByCategory(cat);
    	ArrayList<Expense> test = new ArrayList<>();
    	test.add(expense);
    	assertEquals(expected.size(), 2);
    	
    }
    
    
    @Test 
    void testGetPercentSpending() {
    	controller.logout();
    	controller.register(fName2, lName2, email2, username2, password2);
    	controller.login(username2, password2);
    	controller.addExpense(expense);
    	
    	Category cat = Category.ENTERTAINMENT;
    	int amount = controller.getpercentSpending(cat);
    	assertEquals(amount, 0);
    }
    
    
   
    @Test 
    void testExportExpenses() {
    	controller.logout();
    	controller.login(username, password);
    	assertTrue(controller.exportExpenses());
    }
    
    
    
    @Test
    void testAddFile() throws FileNotFoundException {
    	//controller.login(username2, password2);
    	controller.logout();
    	//controller.register(fName2, lName2, email2, username2, password2);
    	controller.login(username2, password2);
    	
    	String file = "users.dat";
    	controller.addFile(file);
    
    }
    
    
    @Test 
    void testSaveAndLoadData() {
    	//TODO
    }
    
  
    
    
    
    @Test
    public void testGetByDate() {
        ArrayList<Expense> expenses = controller.getByDate(LocalDate.of(2024, 9, 18), LocalDate.of(2024, 11, 25));
        assertEquals(1, expenses.size(), 2);
        
        ArrayList<Expense> expenses2 = controller.getByDate(LocalDate.of(2024, 9, 18), LocalDate.of(2024, 10, 25));
        assertEquals(1, expenses2.size(), 1);
    }
    
    
    
	
	@Test
	void testAddExpense() {
		//controller.login(username, password);
        assertEquals(0, controller.getAllExpenses().size());
		controller.addExpense(expense);
		
        assertEquals(1, controller.getAllExpenses().size());
        assertEquals(expense,controller.getAllExpenses().get(0));
        
        controller.addExpense(expense2);
        assertEquals(2, controller.getAllExpenses().size());
        assertEquals(expense2, controller.getAllExpenses().get(1));
	}
	
	
	
	@Test
	void testEditExpense() {
		//controller.login(username, password);
        controller.addExpense(expense);
        controller.editExpense(expense2, expense.getId());

        Expense result = controller.getExpense(expense.getId());
        assertEquals(100.00, result.getAmount());
        assertEquals("water", result.getDescription());
        controller.deleteExpense(expense.getId());
	}
	
	
	
    @Test
    public void testDeleteExpense() {
    	assertEquals(controller.getAllExpenses().size(), 2);
        controller.addExpense(expense);
        assertEquals(controller.getAllExpenses().size(), 3);
        controller.deleteExpense(expense.getId());
        assertEquals(controller.getAllExpenses().size(), 2);
    }
    
    
    


}
