
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Controller {

    //create singleton variable
    private static final Controller INSTANCE = new Controller();

    //instance variables
    private ArrayList<User> users = new ArrayList<>();
    private Optional<User> currUser;

    //private constructor, initialize currUser to empty optional
    private Controller() {
        this.currUser = Optional.empty();
    }

    //public accessor method for singleton
    public static Controller instanceOf() {
        return INSTANCE;
    }

    //method for login
    public void login(String username, String password) throws NoSuchElementException {
        User user = findUser(username);

        if (user == null) {
            throw new NoSuchElementException();
        }

        if (checkPassword(user, password)) {
            this.currUser = Optional.of(user);
        } else {
            throw new NoSuchElementException();
        }
    }

    //method logs out curr user
    public void logout() {

    }

    //method creates new user and sets to curr user
    public void resgister(String fName, String lName, String email, String usename, String password) {

    }

    //method saves all data when program quits
    public void saveData() {

    }

    //method loads all data on start up of program(if exists)
    public void loadData() {

    }

    //All methods to access currUser public methods here
    public void addExpense(Expense expense) {
        assert !currUser.isEmpty();

        User user = currUser.get();
        user.addExpense(expense);
    }

    public void deleteExpense(long id) {
        assert !currUser.isEmpty();

        User user = currUser.get();
        user.deleteExpense(id);
    }

    public void editExpense(Expense expense, long id) {
        assert !currUser.isEmpty();

        User user = currUser.get();
        user.editExpense(expense, id);
    }

    public void addBudget(Category cat, double amount) {
        assert !currUser.isEmpty();

        User user = currUser.get();
        user.addBudget(cat, amount);
    }

    public ArrayList<Expense> getByDate() {
        assert !currUser.isEmpty();

        User user = currUser.get();
        return user.getByDate();
    }

    public ArrayList<Expense> getByCategory() {
        assert !currUser.isEmpty();

        User user = currUser.get();
        return user.getByCategory();
    }

    public void addFile(String inFile) {
        assert !currUser.isEmpty();

        User user = currUser.get();
        user.addFile(inFile);
    }

    public boolean exportExpenses() {
        assert !currUser.isEmpty();

        User user = currUser.get();
        return user.exportExpenses();
    }

    public void addObserver(Observer o) {
        assert !currUser.isEmpty();

        User user = currUser.get();
        user.addObserver(o);
    }

    public void removeObserver(Observer o) {
        assert !currUser.isEmpty();

        User user = currUser.get();
        user.removeObserver(o);
    }

    public int getpercentSpending(Category cat) {
        assert !currUser.isEmpty();

        User user = currUser.get();
        return user.getPercentSpending(cat);
    }

    //method returns user with input username
    public User findUser(String username) {
        for (User user : users) {
            //needs getUsername method in user class
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    //method to check password (using encryption?? need to ask)
    private boolean checkPassword(User user, String pw) {
        return false;
    }

}
