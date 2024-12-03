
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

public class User implements Serializable {
    // instance variables

    private static final long serialVersionUID = 1L;
    private String first;
    private String last;
    private String email;
    private String username;
    private String password;
    private String salt;
    private ArrayList<Expense> expenses = new ArrayList<>();
    private ArrayList<Expense> food = new ArrayList<>();
    private ArrayList<Expense> transportation = new ArrayList<>();
    private ArrayList<Expense> entertainment = new ArrayList<>();
    private ArrayList<Expense> utilities = new ArrayList<>();
    private ArrayList<Expense> misc = new ArrayList<>();
    private HashMap<Category, Double> budget = new HashMap<>();
    private transient ArrayList<Observer> observers = new ArrayList<>();

    /**
     * description: instantiates a user object
     *
     * @param first - String, first name of user
     * @param last - String, last name of user
     * @param email - String, email address of user
     * @param username - String, username of user
     * @param password - String, encrypted password of user
     * @param salt - String, salt used in password encryption
     */
    public User(String first, String last, String email, String username, String password, String salt) {
        this.first = first;
        this.last = last;
        this.email = email;
        this.password = password;
        this.username = username;
        this.salt = salt;
    }

    //getters
    /**
     * description: gets username
     *
     * @return String, returns the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * description: gets encrypted password
     *
     * @return String, returns the encrypted password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * description: gets salt used in encrypting password
     *
     * @return String, returns salt used in encrypting password
     */
    public String getSalt() {
        return this.salt;
    }

    /**
     * description: gets an array list of copies of all expenses
     *
     * @return ArrayList<Expense>, returns an array list of copies of all
     * expenses
     */
    public ArrayList<Expense> getAllExpenses() {
        ArrayList<Expense> copy = new ArrayList<>();
        for (Expense expense : this.expenses) {
            copy.add(new Expense(expense));
        }
        return copy;
    }

    // TODO: Should this throw a NoSuchElementException instead of returning null?
    // TODO: Compare with find below--do we need both?
    /**
     * description: gets a copy of an expense by id
     *
     * @param id - long, id of expense to be returned
     * @return Expense, returns a copy of an expense by id, returns null if
     * expense does not match
     */
    public Expense getExpense(long id) {
        for (Expense e : expenses) {
            if (e.getId() == id) {
                return new Expense(e);
            }
        }
        return null;
    }

    /**
     * description: gets a copy of an expense by id
     *
     * @param id - long, id of expense to be returned
     * @return Expense, returns the expense by id, returns null if expense does
     * not match
     */
    private Expense find(long id) {
        for (Expense e : expenses) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    // advanced getters
    /**
     * description: get an array list of copies of expenses between two given
     * dates from a given category
     *
     * @param cat - Category, category of expenses to be returned
     * @param lowerRangeDate - LocalDate, first chronological date which is
     * lower bound of search range
     * @param upperRangeDate - LocalDate, last chronological date which is upper
     * bound of search range
     * @return ArrayList<Expense>, returns an array list of copies of expenses
     * between two given dates from a given category
     */
    public ArrayList<Expense> getByDateCategory(Category cat, LocalDate lowerRangeDate, LocalDate upperRangeDate) {
        ArrayList<Expense> filtedByCat = getByDateCategory(cat);
        return getByDateCategory(lowerRangeDate, upperRangeDate, filtedByCat);
    }

    /**
     * description: get an array list of copies of expenses between two given
     * dates from a supplied list
     *
     * @param cat - Category, category of expenses to be returned
     * @param lowerRangeDate - LocalDate, first chronological date which is
     * lower bound of search range
     * @param upperRangeDate - LocalDate, last chronological date which is upper
     * bound of search range
     * @param catExpense - ArrayList<Expense>, list of expenses filtered by
     * category
     * @return ArrayList<Expense>, returns an array list of copies of expenses
     * between two given dates from a supplied list
     */
    private ArrayList<Expense> getByDateCategory(LocalDate lowerRangeDate, LocalDate upperRangeDate, ArrayList<Expense> catExpenses) {
        ArrayList<Expense> dateRangeExpenses = new ArrayList<Expense>();
        for (Expense e : catExpenses) {
            LocalDate currDate = e.getDate();
            // only add if it is within the range. compareTo gives :
            //0 (Zero) if both the dates represent the same calendar date.
            //Positive integer if the specified date is later than the otherDate.
            //Negative integer if the specified date is earlier than the otherDate.
            if (currDate.compareTo(lowerRangeDate) >= 0 && currDate.compareTo(upperRangeDate) <= 0) {
                dateRangeExpenses.add(new Expense(e));
            }
        }
        return dateRangeExpenses;
    }

    /**
     * description: get an array list of copies of expenses between two given
     * dates
     *
     * @param lowerRangeDate - LocalDate, first chronological date which is
     * lower bound of search range
     * @param upperRangeDate - LocalDate, last chronological date which is upper
     * bound of search range
     * @return ArrayList<Expense>, returns an array list of copies of expenses
     * between two given dates from a supplied list
     */
    public ArrayList<Expense> getByDateCategory(LocalDate lowerRangeDate, LocalDate upperRangeDate) {
        ArrayList<Expense> dateRangeExpenses = new ArrayList<Expense>();
        for (Expense e : expenses) {
            LocalDate currDate = e.getDate();
            // only add if it is within the range. compareTo gives :
            //0 (Zero) if both the dates represent the same calendar date.
            //Positive integer if the specified date is later than the otherDate.
            //Negative integer if the specified date is earlier than the otherDate.
            if (currDate.compareTo(lowerRangeDate) >= 0 && currDate.compareTo(upperRangeDate) <= 0) {
                dateRangeExpenses.add(new Expense(e));
            }
        }
        return dateRangeExpenses;
    }

    /**
     * description: get an array list of copies of expenses from a given
     * category
     *
     * @param cat - Category, category of expenses to be returned
     * @return ArrayList<Expense>, returns an array list of copies of expenses
     * from a given category
     */
    public ArrayList<Expense> getByDateCategory(Category c) {
        ArrayList<Expense> toCopy = null;
        switch (c) {
            case FOOD:
                toCopy = new ArrayList<Expense>(this.food);
                break;
            case TRANSPORTATION:
                toCopy = new ArrayList<Expense>(this.transportation);
                break;
            case ENTERTAINMENT:
                toCopy = new ArrayList<Expense>(this.entertainment);
                break;
            case UTILITIES:
                toCopy = new ArrayList<Expense>(this.utilities);
                break;
            default:
                toCopy = new ArrayList<Expense>(this.misc);
                break;
        }
        ArrayList<Expense> copy = new ArrayList<>();
        for (Expense e : toCopy) {
            copy.add(new Expense(e));
        }
        return copy;
    }

    /**
     * description: get the sum of all expenses in a category
     *
     * @param category - Category, category of expenses to be returned
     * @return double, returns the sum of all expenses in a category
     */
    public double getTotalExpensesByCategory(Category category) {
        ArrayList<Expense> cat = getByDateCategory(category);
        double totalExpense = 0.0;
        for (Expense expense : cat) {
            totalExpense += expense.getAmount();
        }
        return totalExpense;
    }

    /**
     * description: get the sum of all expenses in a category between two dates
     *
     * @param category - Category, category of expenses to be returned
     * @param lowerRangeDate - LocalDate, first chronological date which is
     * lower bound of search range
     * @param upperRangeDate - LocalDate, last chronological date which is upper
     * bound of search range
     * @return double, returns the sum of all expenses in a category between two
     * dates
     */
    public double getTotalExpensesByCategoryByDate(Category category, LocalDate lowerRangeDate, LocalDate upperRangeDate) {
        ArrayList<Expense> cat = getByDateCategory(category, lowerRangeDate, upperRangeDate);
        double totalExpense = 0.0;
        for (Expense expense : cat) {
            totalExpense += expense.getAmount();
        }
        return totalExpense;
    }

    /**
     * description: get the budget of a given category
     *
     * @param category - Category, category of budget to be returned
     * @return Optional<Double>, returns the budget of a given category,
     * Optional.empty() if budget is not set
     */
    public Optional<Double> getBudgetByCategory(Category category) {
        if (this.budget.containsKey(category)) {
            return Optional.of(this.budget.get(category));
        }
        return Optional.empty();
    }

    /**
     * description: get the percent of a budget that has been reached by
     * expenses in that category
     *
     * @param category - Category, category of budget to be returned
     * @return Optional<Double>, returns the percent (between 0 and 100) of a
     * budget that has been reached by expenses in that category,
     * Optional.empty() if budget is not set
     */
    public Optional<Double> getExpensesByCategoryPercent(Category category) {
        double getExpense = getTotalExpensesByCategory(category);
        Optional<Double> getBudget = getBudgetByCategory(category);
        if (getBudget.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of((getExpense / getBudget.get()) * 100);
    }

    /**
     * description: get the percent of a budget that has been reached by
     * expenses in that category between two dates
     *
     * @param category - Category, category of budget to be returned
     * @param lowerRangeDate - LocalDate, first chronological date which is
     * lower bound of search range
     * @param upperRangeDate - LocalDate, last chronological date which is upper
     * bound of search range
     * @return Optional<Double>, returns the percent (between 0 and 100) of a
     * budget that has been reached by expenses in that category between two
     * dates, Optional.empty() if budget is not set
     */
    public Optional<Double> getExpensesByCategoryPercentByDate(Category category, LocalDate lowerRangeDate, LocalDate upperRangeDate) {
        double getExpense = getTotalExpensesByCategoryByDate(category, lowerRangeDate, upperRangeDate);
        Optional<Double> getBudget = getBudgetByCategory(category);
        if (getBudget.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of((getExpense / getBudget.get()) * 100);
    }

    /**
     * description: get the percent of overall spending that has been spent in a
     * given category
     *
     * @param category - Category, category of spending to be returned
     * @return double, returns the percent (between 0 and 100) of overall
     * spending that has been spent in a given category
     */
    public double getPercentSpending(Category category) {
        double getExpense = getTotalExpensesByCategory(category);
        double getTotal = 0.0;
        for (Expense expense : this.expenses) {
            getTotal += expense.getAmount();
        }
        if (getTotal == 0) {
            throw new NoSuchElementException("No expenses yet!");
        }
        return (getExpense / getTotal) * 100;
    }

    // additional methods
    // TODO: Check functionality on this--do we want to maintain id unless the category changes?
    /**
     * description: edits an existing expense
     *
     * @param e - Expense, used to update an existing expense
     * @param id - long, id of an existing expense to be edited
     * @throws NoSuchElementException if expense is not found
     */
    public void editExpense(Expense e, long id) throws NoSuchElementException {
        Expense expense = find(id);
        if (expense == null) {
            throw new NoSuchElementException();
        }
        if (expense.getCategory() != e.getCategory()) {
            delete(id);
            add(new Expense(e));
        } else {
            expense.setAmount(e.getAmount());
            expense.setCategory(e.getCategory());
            expense.setDate(e.getDate());
            expense.setDescription(e.getDescription());
        }
        alertBudget();
        alertExpense();
    }

    /**
     * description: Add a copy of an expense to the expense list and appropriate
     * category list
     *
     * @param expense - Expense, expense to be copied and added
     */
    public void addExpense(Expense expense) {
        this.add(expense);
        alertBudget();
        alertExpense();

    }

    /**
     * description: deletes an expense
     *
     * @param id - long, id of an existing expense to be deleted
     * @throws NoSuchElementException if expense is not found
     */
    public void deleteExpense(long id) throws NoSuchElementException {
        this.delete(id);
        alertBudget();
        alertExpense();
    }

    // TODO: Compare with addExpense above--do we need both?
    /**
     * description: Add a copy of an expense to the expense list and appropriate
     * category list
     *
     * @param expense - Expense, expense to be copied and added
     */
    private void add(Expense expense) {
        Expense copyExpense = new Expense(expense);
        this.expenses.add(copyExpense);
        Category c = copyExpense.getCategory();
        switch (c) {
            case FOOD:
                this.food.add(copyExpense);
                break;
            case TRANSPORTATION:
                this.transportation.add(copyExpense);
                break;
            case ENTERTAINMENT:
                this.entertainment.add(copyExpense);
                break;
            case UTILITIES:
                this.utilities.add(copyExpense);
                break;
            default:
                this.misc.add(copyExpense);
                break;
        }
    }

    // TODO: Compare with deleteExpense below--do we need both?
    /**
     * description: deletes an expense
     *
     * @param id - long, id of an existing expense to be deleted
     * @throws NoSuchElementException if expense is not found
     */
    private void delete(long id) throws NoSuchElementException {
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
    }

    /**
     * description: adds or updates a budget for a category
     *
     * @param cat - Category, name of category that will have a new budget
     * @param amount - double, amount of budget
     */
    public void addBudget(Category cat, double amount) {
        if (budget.containsKey(cat)) {
            budget.replace(cat, amount);
        } else {
            budget.put(cat, amount);
        }
        alertBudget();
    }

    // TODO: This does not appear to be used anywhere
    /**
     * description: removes a budget for a category
     *
     * @param cat - Category, name of category that will have budget removed
     */
    public void removeBudget(Category cat) {
        if (budget.containsKey(cat)) {
            budget.remove(cat);
        }
        alertBudget();
    }

    /**
     * description: takes file as input at adds all expenses
     *
     * @param inFile - String, name of file to import information from
     * @return String, a string containing an incorrectly formatted inputs found
     * in the file
     * @throws FileNotFoundException
     */
    public String addFile(String inFile) throws FileNotFoundException {
        //Create error string for lines not read
        String incorrectInputs = "";

        Scanner scanner = new Scanner(new File(inFile));

        //read each line in file
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
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
                //catch any errors in line input
                incorrectInputs += "Could not read line: " + line;
            }
        }
        alertBudget();
        alertExpense();
        scanner.close();
        return incorrectInputs;
    }

    /**
     * description: creates a csv file containing a list of expenses
     *
     * @return boolean, true if successful, false if not
     */
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
                String line = String.format("%d-%02d-%02d,%s,%.2f,%s\n",
                        year, month, day, e.getCategory().toString().toLowerCase(), e.getAmount(), e.getDescription());
                writer.write(line);
            }
            writer.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * description: alerts observers of a budget change
     */
    public void alertBudget() {
        for (Observer o : observers) {
            o.budgetChange();
        }
    }

    /**
     * description: alerts observers of a login
     */
    public void alertLogin() {
        for (Observer o : observers) {
            o.loginChange();
        }
    }

    /**
     * description: alerts observers of an expense change
     */
    public void alertExpense() {
        for (Observer o : observers) {
            o.expenseChange();
        }
    }

    /**
     * description: adds an observer to the list of observers
     *
     * @param o - Observer, the Observer to be added
     */
    public void addObserver(Observer o) {
        observers.add(o);
    }

    /**
     * description: removes all observers from the Observer list; if list is
     * null instantiates a new empty list
     */
    public void removeAllObservers() {
        if (this.observers == null) {
            this.observers = new ArrayList<>();
        } else {
            observers.clear();
        }
    }

    /**
     * description: gets a descriptive String for a user
     *
     * @return String - returns a descriptive String for a user
     */
    public String toString() {
        String output = String.format("%s %s's email: %s, username: %s, password: %s", this.first, this.last, this.email, this.username, this.password);
        return output;
    }
}