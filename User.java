
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class User implements Serializable{
    private static final long serialVersionUID = 1L;
	private String first;
    private String last;
    private String email;
    private String username;
    private String password;
    private ArrayList<Expense> expenses = new ArrayList<>();
    private ArrayList<Expense> food = new ArrayList<>();
    private ArrayList<Expense> transporation = new ArrayList<>();
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
        alert();
    }

    //edit expense
    //throw NoSuchElementException of none found
    public void editExpense(Expense e, long id) throws NoSuchElementException {
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

    public void deleteExpense(long id) throws NoSuchElementException {
        Expense expense = find(id);
        // if (expense == null) {
        //     throw new NoSuchElementException();
        // }
        // expense.remove(expense);

        // //TODO:
        // //use category to remove from category list
        // if (expense.getCategory == Category.FOOD) {
        //     food.remove(expense);
        // }
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
    public ArrayList<Expense> getByDate() {
        return new ArrayList<>();
    }

    //get expenses based on categry
    public ArrayList<Expense> getByCategory() {
        return new ArrayList<>();
    }

    //helper method to find expense by id
    private Expense find(long id) {
        // for (Expense e : expenses) {
        //     if (e.id == id) {
        //         return e;
        //     }
        // }

        return null;
    }

    //function takes file as imput at adds all expenses
    public void addFile(String inFile) {
        //TODO use scanner to read all lines. Create a string
        // representing the expenses that were not added due to incorrect input
        alert();

    }

    //method create csv file, return true if successful, false if not
    public boolean exportExpenses() {
        //TODO use FileWriter to create text file in date,category,amount, info
        return false;
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
