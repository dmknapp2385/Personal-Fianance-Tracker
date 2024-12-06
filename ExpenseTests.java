/*
 * File Name: ExpenseTests.java
 * Authors: Paulina Aguirre (paulinaa3), David Herring (dherring), Chitrangada Juneja(cj21), Elle Knapp (dmknapp2385)
 * Description: This class tests the functionality of the Expense class. 
 */

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class ExpenseTests {
	double amount1 = 23.87;
	LocalDate date1 = LocalDate.of(2024, 10, 25);
	String description1 = "Dinner";
	Category category1 = Category.FOOD;
	Expense expense1 = new Expense(amount1, date1, description1, category1);
	
	double amount2 = 400.99;
	LocalDate date2 = LocalDate.of(2024, 11, 15);
	String description2 = "Concert";
	Category category2 = Category.ENTERTAINMENT;
	Expense expense2 = new Expense(amount2, date2, description2, category2);
	
	Expense copyExpense = new Expense(expense1);
	 
	
	@Test
	void testCopy() {
		assertEquals(expense1.getId(), copyExpense.getId());
	}
	
	@Test
	void testAmount() {
		assertEquals(expense1.getAmount(), 23.87);
		expense1.setAmount(57.33);
		assertEquals(expense1.getAmount(), 57.33);
		assertEquals(expense2.getAmount(), 400.99);
		expense1.setAmount(57.33);
		assertEquals(expense1.getAmount(), 57.33);
		
	}
	
	@Test
	void testDate() {
		LocalDate date = LocalDate.of(2024, 10, 25);
		assertEquals(expense1.getDate(), date);
		LocalDate newDate = LocalDate.of(2024, 10, 28);
		expense1.setDate(newDate);
		assertEquals(expense1.getDate(), newDate);
		LocalDate date2 = LocalDate.of(2024, 11, 15);
		assertEquals(expense2.getDate(), date2);
		LocalDate newDate2 = LocalDate.of(2024, 11, 18);
		expense1.setDate(newDate2);
		assertEquals(expense1.getDate(), newDate2);
		 
	}
	
	@Test
	void testDescription() {
		String description = "dinner";
		assertEquals(expense1.getDescription(), description);
		String newDescription = "FANCY Dinner";
		expense1.setDescription(newDescription);
		assertEquals(expense1.getDescription(), "fancy dinner");
		String description2 = "concert";
		assertEquals(expense2.getDescription(), description2);
		String newDescription2 = "fun conCERt";
		expense1.setDescription(newDescription2);
		assertEquals(expense1.getDescription(), "fun concert");
		
	}
	
	@Test
	void testCategory() {
		Category category = Category.FOOD;
		assertEquals(expense1.getCategory(), category);
		Category newCategory = Category.UTILITIES;
		expense1.setCategory(newCategory);
		assertEquals(expense1.getCategory(), newCategory);
		Category category2 = Category.ENTERTAINMENT;
		assertEquals(expense2.getCategory(), category2);
		Category newCategory2 = Category.TRANSPORTATION;
		expense1.setCategory(newCategory2);
		assertEquals(expense1.getCategory(), newCategory2);
		
	}
	
	@Test
	void testCompareTo() {
		assertEquals(expense1.compareTo(copyExpense), 0);
		assertEquals(expense1.compareTo(expense2), -1);
	}
	
	@Test
	void testToString() {
		assertEquals(expense1.toString(), "2024-10-25, FOOD, 23.87, dinner");
		assertEquals(expense2.toString(), "2024-11-15, ENTERTAINMENT, 400.99, concert");
		
	}

}
