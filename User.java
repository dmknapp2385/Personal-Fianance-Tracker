
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.NoSuchElementException;

public class User {

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
    private Observer window;

    //constructor
    public User(String first, String last, String email, String password, String username) {
        this.first = first;
        this.last = last;
        this.email = email;
        this.password = password;
        this.username = username;
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
    public void editExpense(int id, Calendar date, int amount, Category cat) throws NoSuchElementException {
        Expense expense = find(id);
        if (expense == null) {
            throw new NoSuchElementException();
        }
        expense.setAmount(amount);
        expense.setCategory(cat);
        expense.setDate(date);
        alert();
    }

    public void deleteExpense(int id) throws NoSuchElementException {
        Expense expense = find(id);
        if (expense == null) {
            throw new NoSuchElementException();
        }
        expense.remove(expense);

        //TODO:
        //use category to remove from category list
        if (expense.getCategory == Category.FOOD) {
            food.remove(expense);
        }
    }

    //add budget amount
    public void addBudget(Category cat, int amount) {
        if (budget.containsKey(cat)) {
            budget.replace(cat, amount);
        } else {
            budget.put(cat, amount)
        
        );
        }
        alert();
    }

    //get expenses based on date ranges
    public ArrayList<Expense> getByDate() {
        return new ArrayList<>();
    }

    //get expenses based on categry
    public ArrayList<Expense> getByCategor() {
        return new ArrayList<>() /
    }

    //helper method to find expense by id
    private Expense find(int id) {
        for (Expense e : expenses) {
            if (e.id == id) {
                return e;
            }
        }

        return null;
    }

    //function checks if current budget is above set budget and alerts window to
    //change
    private void alert() {
        //TODO:
        //Loop through all categories and check to budget amount
        //alert observer
    }

}
