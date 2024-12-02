import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {
	User user;
	Expense expense1 ;
	Expense expense2 ;

	Expense expense3;
	
	Expense expense4;

	Expense expense5;
	
	
	
	
	@BeforeEach
	void setUp() {
		String fName= "Harry";
		String lName="Potter";
		
		String email= "hp@hoggywarts.com";
		String username= "the Chosen One";
		String password= "hedwig";
		String salt= "salt";
		
		double amount1 = 23.87;
		LocalDate date1 = LocalDate.of(2024, 10, 25);
		String description1 = "three broomsticks";
		Category category1 = Category.FOOD;
		expense1 = new Expense(amount1, date1, description1, category1);
		
		double amount2 = 400.99;
		LocalDate date2 = LocalDate.of(2024, 11, 15);
		String description2 = "concert";
		Category category2 = Category.ENTERTAINMENT;
		expense2 = new Expense(amount2, date2, description2, category2);
		
		double amount3=5000.0;
		LocalDate date3 = LocalDate.of(2024, 11, 21);
		String description3= "ron's car fixup";
		Category category3= Category.TRANSPORTATION;
		expense3= new Expense(amount3, date3, description3, category3);
		
		
		
		double amount4=257.9;
		LocalDate date4 = LocalDate.of(2024, 11, 24);
		String description4="leaky cauldron bill";
		Category category4= Category.UTILITIES;
		 expense4= new Expense(amount4, date4, description4, category4);
		
		double amount5=90.0;
		LocalDate date5=LocalDate.of(2024, 11, 25);
		String description5= "quills and robes";
		Category category5=Category.MISCELLANEOUS;
		 expense5= new Expense(amount5, date5, description5, category5);
		
		
		
		
		 user= new User(fName, lName, email, username, password,salt);

		
	}
	
	

	@Test
	void testConstructor() {
		assertEquals ("the Chosen One", user.getUsername());
		assertEquals ("hedwig", user.getPassword());
		assertEquals("salt", user.getSalt());
		
	
	}
	
	@Test
	void testExpenses() {
		user.addExpense(expense1);
		user.addExpense(expense2);
		long id1=expense1.getId();
		long id2= expense2.getId();
		long id3=expense3.getId();
		long id4=expense4.getId();
		long id5= expense5.getId();
		
		
		//added the expenses
		
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
		
		
		
		double amount =500;
		
		user.deleteExpense(id1);
		user.deleteExpense(id2);
		user.deleteExpense(id3);
		user.deleteExpense(id4);
		user.deleteExpense(id5);
		
		ArrayList <Expense> expensesDelete= user.getAllExpenses();
		assertEquals(0, expensesDelete.size());
		user.addExpense(expense1);
		user.addExpense(expense2);
		user.addExpense(expense3);
		user.addExpense(expense4);
		user.addExpense(expense5);
		
		
		
	
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
		
		
	}
	
	
	
	
	@Test
	void testDates() {
		user.addExpense(expense1);
		user.addExpense(expense2);
		user.addExpense(expense3);
		user.addExpense(expense4);
		user.addExpense(expense5);
		
		user.addBudget(Category.ENTERTAINMENT, 2000.0);
		LocalDate lower= LocalDate.of(2024, 10, 25);
		LocalDate upper= LocalDate.of(2024, 11, 21);
		//now we get the 3 expenses
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
			String incorrect= user.addFile("Expenses.txt");
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
