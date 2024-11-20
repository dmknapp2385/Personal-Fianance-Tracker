
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

public class User implements Serializable {

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
    private HashMap<Category, Double> budget = new HashMap<>();
    private ArrayList<Observer> observers = new ArrayList<>();


    public User(String first, String last, String email, String username, String password) {

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

    //get all expenses
    public ArrayList<Expense> getAllExpenses() {
        return new ArrayList<Expense>(expenses);
    }

    //Add expense to expense list and category list
    public void addExpense(Expense expense) {
        this.expenses.add(expense);

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

        alertBudget();

    }

    //edit expense
    //throw NoSuchElementException of none found
    public void editExpense(Expense e, long id) throws NoSuchElementException {
        Expense expense = find(id);
        if (expense == null) {
            throw new NoSuchElementException();
        }

        //update current expense with updates
        expense.setAmount(e.getAmount());
        expense.setCategory(e.getCategory());
        expense.setDate(e.getDate());
        expense.setDescription(e.getDescription());

        alertBudget();
    }

    //delete expense by id
    public void deleteExpense(long id) throws NoSuchElementException {
        Expense expense = find(id);
        if (expense == null) {
            throw new NoSuchElementException();
        }

        this.expenses.remove(expense);

        Category c = expense.getCategory();
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

        alertBudget();

    }

    //gets expense by id
    public Expense getExpense(long id) {

        for (Expense e : expenses) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    //add budget amount
    public void addBudget(Category cat, double amount) {
        if (budget.containsKey(cat)) {
            budget.replace(cat, amount);
        } else {
            budget.put(cat, amount);
        }

        alertBudget();
    }
    
    
    public void removeBudget(Category cat) {
    	if (budget.containsKey(cat)) {
    		budget.remove(cat);
    	}
    	alertBudget();
    }

    //get expenses based on date ranges
    public ArrayList<Expense> getByDate(LocalDate lowerRangeDate, LocalDate upperRangeDate) {

        ArrayList<Expense> dateRangeExpenses = new ArrayList<Expense>();
        for (Expense e : expenses) {
            LocalDate currDate = e.getDate();
            // only add if it is within the range. compareTo gives :
            //0 (Zero) if both the dates represent the same calendar date.
            //Positive integer if the specified date is later than the otherDate.
            //Negative integer if the specified date is earlier than the otherDate.
            if (currDate.compareTo(lowerRangeDate) >= 0 && currDate.compareTo(upperRangeDate) <= 0) {
                dateRangeExpenses.add(e);
            }

        }
        return dateRangeExpenses;
    }

    //get expenses based on categry
    public ArrayList<Expense> getByCategory(Category c) {
        switch (c) {
            case FOOD:
                return new ArrayList<Expense>(this.food);

            case TRANSPORTATION:
                return new ArrayList<Expense>(this.transportation);

            case ENTERTAINMENT:
                return new ArrayList<Expense>(this.entertainment);

            case UTILITIES:
                return new ArrayList<Expense>(this.utilities);

            default:
                return new ArrayList<Expense>(this.misc);

        }

    }

    //helper method to find expense by id
    private Expense find(long id) {
        for (Expense e : expenses) {
            if (e.getId() == id) {
                return e;
            }
        }

        return null;
    }

    //function takes file as imput at adds all expenses
    //throws file not found exception
    public String addFile(String inFile) throws FileNotFoundException {

        //Create error string for lines not read
        String incorrectInputs = "";

        Scanner scanner = new Scanner(new File(inFile));

        //read each line in file
        while (scanner.hasNext()) {
            String line = scanner.next();
            try {
                String[] details = line.split(",");

                //get date
                String dateStr = details[0];
                String[] yrMo = dateStr.split("-");
                Integer year = Integer.parseInt(yrMo[0]);
                Integer month = Integer.parseInt(yrMo[1]);
                Integer day = Integer.parseInt(yrMo[2]);
                LocalDate date = LocalDate.of(year, month, day);

                //category
                Category cat = Category.valueOf(details[1].toUpperCase());

                //amount
                Double amount = Double.parseDouble(details[2]);

                //add expense
                this.addExpense(new Expense(amount, date, details[3], cat));
            } catch (Exception e) {
                //catch any erros in line input
                incorrectInputs += "Could not read line: " + line;
            }

        }

        return incorrectInputs;
    }

    //method create csv file, return true if successful, false if not
    public boolean exportExpenses() {
        File f = new File("exports.txt");
        if (f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
        } catch (IOException e) {
            System.out.println("Could not create file");
        }
        try {
            f.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter("exports.txt"));
            for (Expense e : expenses) {
                LocalDate date = e.getDate();
                Integer year = date.getYear();
                Integer month = date.getMonthValue();
                Integer day = date.getDayOfMonth();
                String line = String.format("%d-%02d-%02d,%s,%.2f,%s",
                        year, month, day, e.getCategory().toString().toLowerCase(), e.getAmount(), e.getDescription());
                writer.write(line);
            }
            writer.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //function checks if current budget is above set budget and alerts window to
    //change
    private void alertBudget() {
        for (Observer o : observers) {
            o.budgetChange();
        }
    }

    //method alerts observers to user login
    public void alertLogin() {
        for (Observer o : observers) {
            o.loginChange();
        }
    }

    //method adds observer 
    public void addObserver(Observer o) {
        observers.add(o);
    }

    //method removes observer
    public void removeAllObservers() {
        observers.clear();
    }

    //Methods to get % of spending by category and current month
    public int getPercentSpending(Category cat) {
        return 0;
    }
    
    
    public double getTotalExpensesByCategory(Category category) {
    	ArrayList<Expense> cat = getByCategory(category);
    	double totalExpense = 0.0;
    	for (Expense expense: cat) {
    		totalExpense += expense.getAmount();
    	}
    	return totalExpense;
    	
    }
    
    public Optional<Double> getBudgetByCategory(Category category) {
    	if (this.budget.containsKey(category)) {
    		return Optional.of(this.budget.get(category));
    	}
    	return Optional.empty();
    	}
    
    public Optional<Double> getExpensesByCategoryPercent(Category category) {
    	double getExpense = getTotalExpensesByCategory(category);
    	Optional<Double> getBudget = getBudgetByCategory(category);
    	if (getBudget.isEmpty()) {
    		return Optional.empty();
    	}
    	return Optional.of((getExpense/getBudget.get()) * 100);
    }
    
    
    public double getExpenseShareByCategory(Category category) {
    	double getExpense = getTotalExpensesByCategory(category);
    	double getTotal = 0.0;
    	for (Expense expense: this.expenses) {
    		getTotal += expense.getAmount();
    	}
    	if (getTotal == 0) {
    		throw new NoSuchElementException("No expenses yet!");
    	}
    	return (getExpense/getTotal) * 100;
    }
    


    public String toString() {
        String output = String.format("%s %s email: %s, username: %s, password: %s", this.first, this.last, this.email, this.username, this.password);
        return output;
    }
}
