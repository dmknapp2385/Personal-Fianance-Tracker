
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class User implements Serializable{
    private static final long serialVersionUID = 1L;
	private String first;
    private String last;
    private String email;
    private String username;
    private String password;
    private ArrayList<Expense> expenses = new ArrayList<>();
    private ArrayList<Expense> food = new ArrayList<>();
    private ArrayList<Expense> transportation = new ArrayList<>();
    private ArrayList<Expense> entertainment = new ArrayList<>();
    private ArrayList<Expense> utilities = new ArrayList<>();
    private ArrayList<Expense> misc = new ArrayList<>();
    private HashMap<Expense, Long> budget = new HashMap<>();
    private ArrayList<Observer> observers;

    //constructor
    public User(String first, String last, String email, String password, String username) {
        this.first = first;
        this.last = last;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    //getters
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    //Add expense
    public void addExpense(Expense expense) {
        this.expenses.add(expense);

        //TODO:
        //add expense to list with category
      
        Category c = expense.getCategory();
        switch (c) {
        case FOOD:
            this.food.add(expense);
            break;
        case TRANSPORTATION:
            this.transportation.add(expense);
            break;
        case ENTERTAINMENT:
            this.entertainment.add(expense);
            break;
        case UTILITIES:
            this.utilities.add(expense);
            break;
        default:
            this.misc.add(expense);
            break;
    }
    
    alert();
        
        	
     
        
    }

    //edit expense
    //throw NoSuchElementException of none found
    public void editExpense(Expense e, int id) throws NoSuchElementException {
        Expense expense = find(id);
        if (expense == null) {
            throw new NoSuchElementException();
        }

        //create new expense object to avoid escaping reference
        expense.setAmount(e.getAmount());
        expense.setCategory(e.getCategory());
        expense.setDate(e.getDate());
        alert();
    }

    public void deleteExpense(int id) throws NoSuchElementException {
        Expense expense = find(id);
        if (expense == null) {
             throw new NoSuchElementException();
         }
        
        this.expenses.remove(expense);
        
        Category c= expense.getCategory();
        switch (c) {
        case FOOD:
            this.food.remove(expense);
            break;
        case TRANSPORTATION:
            this.transportation.remove(expense);
            break;
        case ENTERTAINMENT:
            this.entertainment.remove(expense);
            break;
        case UTILITIES:
            this.utilities.remove(expense);
            break;
        default:
            this.misc.remove(expense);
            break;
    }
    
    alert();
       
    }

    //add budget amount
    // public void addBudget(Category cat, int amount) {
    //     if (budget.containsKey(cat)) {
    //         budget.replace(cat, amount);
    //     } else {
    //         budget.put(cat, amount);
    //     }
    //     alert();
    // }
    //get expenses based on date ranges
    public ArrayList<Expense> getByDate(LocalDate lowerRangeDate, LocalDate upperRangeDate) {
    	
    	ArrayList<Expense> dateRangeExpenses= new ArrayList<Expense>();
    	for (Expense e: expenses) {
    		LocalDate currDate= e.getDate();
    		// only add if it is within the range. compareTo gives :
    		//0 (Zero) if both the dates represent the same calendar date.
    		//Positive integer if the specified date is later than the otherDate.
    		//Negative integer if the specified date is earlier than the otherDate.
    		 if (currDate.compareTo(lowerRangeDate)>=0 && currDate.compareTo(upperRangeDate)<=0) {
    			 
    			 dateRangeExpenses.add(e);
    		}
    		
    	}
    	return dateRangeExpenses; 
    }

    //get expenses based on categry
    public ArrayList<Expense> getByCategory(Category c) {
    	switch (c) {
        case FOOD:
            return new ArrayList<Expense> (this.food);
            
        case TRANSPORTATION:
        	return new ArrayList<Expense> (this.transportation);
            
        case ENTERTAINMENT:
        	return new ArrayList<Expense> (this.entertainment);
            
        case UTILITIES:
        	return new ArrayList<Expense> (this.utilities);
            
        default:
        	return new ArrayList<Expense> (this.misc);
            
    	}
    
   
    }

    //helper method to find expense by id
    private Expense find(int id) {
        for (Expense e : expenses) {
          if (e.getId() == id) {
              return e;
           }
         }

        return null;
    }

    //function takes file as imput at adds all expenses
    public String addFile(String inFile) {
        //TODO use scanner to read all lines. Create a string
        // representing the expenses that were not added due to incorrect input
       String incorrectInputs;
       
       try(BufferedReader reader= new BufferedReader(new FileReader(inFile))){
    	   
       } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       
       
       
       
       
       
    }

    //method returns a csv file with current expenses
    public void exportExpenses() {
        //TODO use FileWriter to create text file in date,category,amount, info

    }

    //function checks if current budget is above set budget and alerts window to
    //change
    private void alert() {
        //TODO:
        //Loop through all categories and check to budget amount
        //alert observer
    	
    	
    }

    //method adds observer 
    public void addObserver(Observer o) {
        observers.add(o);
    }

    //method removes observer
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    //Methods to get % of spending by category and current month
    // public int getPercentSpending(Category cat) {
    //     return 0;
    // }
}
