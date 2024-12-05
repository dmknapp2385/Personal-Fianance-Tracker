/*
 * File Name: UserTests.java
 * Authors: Paulina Aguirre (paulinaa3), David Herring (dherring), Chitrangada Juneja(cj21), Elle Knapp (dmknapp2385)
 * Description: This class tests the functionality of the User class. 
 */

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class UserTest {
	
	//set up to avoid duplicate code
	String fName= "Harry";
	String lName="Potter";
	String email= "hp@hoggywarts.com";
	String username= "the Chosen One";
	String password= "hedwig";
	String salt= "salt";
	User user= new User(fName, lName, email, username, password,salt);
	User user2= new User("Stormy", "Aguirre", "storm@gmail.com", "storm", "password", "salt");
	
	double amount1 = 23.87;
	LocalDate date1 = LocalDate.of(2024, 10, 25);
	String description1 = "three broomsticks";
	Category category1 = Category.FOOD;
	Expense expense1 = new Expense(amount1, date1, description1, category1);
	
	double amount2 = 500.0;
	LocalDate date2 = LocalDate.of(2024, 11, 15);
	String description2 = "concert";
	Category category2 = Category.ENTERTAINMENT;
	Expense expense2 = new Expense(amount2, date2, description2, category2);
	
	double amount3=5000.0;
	LocalDate date3 = LocalDate.of(2024, 11, 21);
	String description3= "ron's car fixup";
	Category category3= Category.TRANSPORTATION;
	Expense expense3= new Expense(amount3, date3, description3, category3);
	
	double amount4 = 257.9;
	LocalDate date4 = LocalDate.of(2024, 11, 24);
	String description4 ="leaky cauldron bill";
	Category category4 = Category.UTILITIES;
	Expense expense4 = new Expense(amount4, date4, description4, category4);
	
	double amount5 = 90.0;
	LocalDate date5 = LocalDate.of(2024, 11, 25);
	String description5 = "quills and robes";
	Category category5 = Category.MISCELLANEOUS;
	Expense expense5 = new Expense(amount5, date5, description5, category5);
	
	LocalDate lower= LocalDate.of(2024, 10, 25);
	LocalDate upper= LocalDate.of(2024, 11, 21);
	
	
	

	@Test
	void testConstructor() {
		assertEquals("the Chosen One", user.getUsername());
		assertEquals("hedwig", user.getPassword());
		assertEquals("salt", user.getSalt());
	}
	
	
	@Test
	void testFirstName() {
		assertEquals("Harry", user.getFirstName());
	}
	
	
	@Test
	void testAddExpenses() {
		this.expenseHelper();
		long id1=expense1.getId();
		ArrayList <Expense> allExpenses= user.getAllExpenses();
		assertEquals ("three broomsticks", allExpenses.get(0).getDescription());
		assertEquals (Category.ENTERTAINMENT, allExpenses.get(1).getCategory());
		user.addExpense(expense3);
		user.addExpense(expense4);
		user.addExpense(expense5);
		ArrayList <Expense> expensesUpdated= user.getAllExpenses();
		assertEquals(5, expensesUpdated.size());
		Expense e1=user.getExpense(id1);
		assertEquals(expense1.getDescription(), e1.getDescription());
		
	}
	
	
	@Test
	void testDeleteExpenseFood() {
		this.expenseHelper();
		long id=expense1.getId();
		user.deleteExpense(id);
		assertEquals(1, user.getAllExpenses().size());
	}
	
	
	@Test
	void testDeleteExpenseEntertainment() {
		user.addExpense(expense2);
		long id=expense2.getId();
		user.deleteExpense(id);
		assertEquals(0, user.getAllExpenses().size());
	}
	
	@Test
	void testDeleteExpenseTransport() {
		user.addExpense(expense3);
		long id=expense3.getId();
		user.deleteExpense(id);
		assertEquals(0, user.getAllExpenses().size());
	}
	
	
	@Test
	void testDeleteExpenseUtilities() {
		user.addExpense(expense4);
		long id =expense4.getId();
		user.deleteExpense(id);
		assertEquals(0, user.getAllExpenses().size());
	}
	
	
	@Test
	void testDeleteExpenseMisc() {
		user.addExpense(expense5);
		long id=expense5.getId();
		user.deleteExpense(id);
		assertEquals(0, user.getAllExpenses().size());
	}
	
	
	@Test
	void testEditExpenseEntertainment() {
		this.expenseHelper();
		long id1=expense1.getId();
		category1= Category.ENTERTAINMENT;
		Expense expense1Copy = new Expense(amount1, date1, description1, category1);
		user.editExpense(expense1Copy, id1);
		
	}	
		

	@Test
	void testEditExpenseFood() {
		this.expenseHelper();
		long id1=expense1.getId();
		double amount1 = 23.87;
		LocalDate date1 = LocalDate.of(2024, 10, 25);
		String description1 = "three broomsticks with weasley";
		Category category1 = Category.FOOD;
		Expense expense1Copy = new Expense(amount1, date1, description1, category1);
		user.editExpense(expense1Copy, id1);
	}
	
	
	@Test 
	void testEditExpenseUtilities() {
		this.expenseHelper();
		long id2= expense2.getId();
		double amount2 = 400.99;
		LocalDate date2 = LocalDate.of(2024, 11, 15);
		String description2 = "concert food";
		Category category2 = Category.UTILITIES;
		Expense expense2Copy = new Expense(amount2, date2, description2, category2);
		user.editExpense(expense2Copy, id2);
		
	}
	
	
	@Test 
	void testEditExpenseMisc() {
		this.expenseHelper();
		long id3=expense3.getId();
		double amount3 = 400.99;
		LocalDate date3 = LocalDate.of(2024, 11, 15);
		String description3 = "concert food";
		Category category3 = Category.MISCELLANEOUS;
		Expense expense3Copy = new Expense(amount3, date3, description3, category3);
		user.editExpense(expense3Copy, id3);
	}
	
	
	@Test
	void testEditExpenseTransport() {
		this.expenseHelper();
		double amount4 = 400.99;
		long id4=expense4.getId();
		LocalDate date4 = LocalDate.of(2024, 11, 15);
		String description4 = "concert food";
		Category category4 = Category.TRANSPORTATION;
		Expense expense4Copy = new Expense(amount4, date4, description4, category4);
		user.editExpense(expense4Copy, id4);
	}
	
		

    //helper to avoid duplicate code
	private void expenseHelper() {
		user.addExpense(expense1);
		user.addExpense(expense2);
	}
		

	@Test
	void testBudget() {
		user.addExpense(expense1);
		user.addExpense(expense2);
		user.addExpense(expense3);
		user.addExpense(expense4);
		user.addExpense(expense5);
		user.addBudget(Category.FOOD, 100.0); 
		user.addBudget(Category.ENTERTAINMENT, 29.7);
		user.removeBudget(Category.ENTERTAINMENT);
		user.addBudget(Category.TRANSPORTATION, 2000.0);
		user.addBudget(Category.TRANSPORTATION, 20.98);
	}
	
	
	@Test
	void testDatesAndBudget() {
		this.dateHelper();
		ArrayList<Expense> dateExpenses= user.getByDateCategory(lower, upper);
		assertEquals(3, dateExpenses.size());
		ArrayList<Expense> foodExpenses= user.getByDateCategory(Category.FOOD);
		ArrayList<Expense> eExpenses= user.getByDateCategory(Category.ENTERTAINMENT);
		ArrayList<Expense> mExpenses= user.getByDateCategory(Category.MISCELLANEOUS);
		ArrayList<Expense> Utilityxpenses= user.getByDateCategory(Category.UTILITIES);
		ArrayList<Expense> tExpenses= user.getByDateCategory(Category.TRANSPORTATION);
		int sizeAll= foodExpenses.size()+eExpenses.size()+mExpenses.size()+Utilityxpenses.size()+tExpenses.size();
		assertEquals(5, sizeAll);
	}
	
	
	@Test
	void testDatesFood() {
		this.dateHelper();
		ArrayList<Expense> ExpensesDate= user.getByDateCategory(Category.FOOD,lower, upper);
		assertEquals(1, ExpensesDate.size());
     }
	
	
	@Test
	void testDatesTransport() {
		this.dateHelper();
		double transport= user.getTotalExpensesByCategory(Category.TRANSPORTATION);
		assertEquals(5000.0, transport);
	}
	
	
	@Test 
	void testExpenseFood() {
		this.dateHelper();
		double amount1 = 25.00;
		LocalDate date1 = LocalDate.of(2024, 10, 28);
		String description1 = "three broomsticks with weasley";
		Category category1 = Category.FOOD;
		Expense expense1Copy = new Expense(amount1, date1, description1, category1);
		user.addExpense(expense1Copy);
		double foodAmt= user.getTotalExpensesByCategoryByDate(category1, lower, upper);
		assertEquals(48.870000000000005, foodAmt);
	}
		
	
	@Test
	void testBudgetMisc() {
		this.dateHelper();
		Optional <Double> mBudget= user.getBudgetByCategory(Category.MISCELLANEOUS);
		assertEquals(Optional.empty(), mBudget);
		Optional <Double> mBudgetPercent= user.getExpensesByCategoryPercent(Category.MISCELLANEOUS);
		assertEquals(Optional.empty(), mBudgetPercent);
	}


	@Test
	void testBudgetEntertainment() {
		this.dateHelper();
		Optional <Double> eBudget=user.getBudgetByCategory(Category.ENTERTAINMENT);
		assertEquals(Optional.of(2000.0), eBudget);
		Optional <Double> eBudgetPercent=user.getExpensesByCategoryPercent(Category.ENTERTAINMENT);
		assertEquals(Optional.of(25.0), eBudgetPercent);
	}
	
	
	@Test
	void testBudgetFood() {
		this.dateHelper();
		Optional<Double> fBudgetPercent=user.getExpensesByCategoryPercentByDate(category1, lower, upper);
		assertEquals(Optional.empty(), fBudgetPercent);
		user.addBudget(Category.FOOD, 50.0);
		Optional<Double> fBudgetPercentTwo=user.getExpensesByCategoryPercentByDate(category1, lower, upper);
		assertEquals(Optional.of(47.74), fBudgetPercentTwo);
	}

	
	//helper to avoid duplicate code
	private void dateHelper() {
		user.addExpense(expense1);
		user.addExpense(expense2);
		user.addExpense(expense3);
		user.addExpense(expense4);
		user.addExpense(expense5);
		user.addBudget(Category.ENTERTAINMENT, 2000.0);
	}
	
	
	@Test
	void testExceptions() {
		user2.getExpense(0);
        assertThrows(NoSuchElementException.class, () -> {
            user2.editExpense(expense1, 0);
        });
        assertThrows(NoSuchElementException.class, () -> {
        	user2.deleteExpense(0);
        });
	}
	
	
	@Test
	void testPercentSpend() {
		NoSuchElementException exception= assertThrows(NoSuchElementException.class,() ->{
			user.getPercentSpending(Category.FOOD);
		});
		assertEquals("No expenses yet!", exception.getMessage());
		user.addExpense(expense1);
		user.addExpense(expense2);
		user.addExpense(expense3);
		user.addExpense(expense4);
		user.addExpense(expense5);
		double total= user.getPercentSpending(Category.FOOD);
		double scale= Math.pow(10, 2);
		total= Math.floor(total*scale)/scale;
		assertEquals(0.4, total);	
	}
	
	
	@Test
	void testExport() {
		user.addExpense(expense1);
		user.addExpense(expense2);
		user.addExpense(expense3);
		user.addExpense(expense4);
		user.addExpense(expense5);
		boolean check= user.exportExpenses();
		assertEquals(true, check);
		
	}
	
	
	@Test
	void testFile() {
		try {
			user.addFile("Expenses.txt");
			ArrayList <Expense> expenses= user.getAllExpenses();
			assertEquals(3, expenses.size());
			
		} catch (FileNotFoundException e) {
			System.out.println("File doesnt exist!");
			e.printStackTrace();
		} 	
	}
	
	
	@Test
	void testString() {
		String info=user.toString();
		assertEquals("Harry Potter's email: hp@hoggywarts.com, username: the Chosen One, password: hedwig", info);
	}
}