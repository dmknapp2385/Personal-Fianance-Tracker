/*
 * File Name: Controller.java
 * Authors: Paulina Aguirre (paulinaa3), David Herring (dherring), Chitrangada Juneja(cj21), Elle Knapp (dmknapp2385)
 * Description: This class serves as the controller which handles interactions between the different view and model classes.  
 */

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Controller {

    //create singleton variable
    private static final Controller INSTANCE = new Controller();

    //instance variables
    private ArrayList<User> users = new ArrayList<>();
    private Optional<User> currUser;
    ArrayList<Observer> observers = new ArrayList<>();

    /**
     * description:
     * 	this method initializes currUser to empty optional
     */
    private Controller() {
        this.currUser = Optional.empty(); 
    }

    /**
     * description:
     * 	public accessor method for singleton
     * @return Controller - singleton instance of Controller class
     */
    public static Controller instanceOf() {
        return INSTANCE;
    }

    /**
     * description:
     * 	allows the user to attempt to login, if no account is
     * 	created, it won't allow you to login, but if the account exists, it
     * 	checks whether or not the password is correct and sets currUser to the
     * 	user if the password is correct
     * @param username - String, used to login
     * @param password - String, used to login
     * @throws NoSuchElementException if user isn't found
     */ 
    public void login(String username, String password) throws NoSuchElementException {
        User user = findUser(username);
 
        if (user == null) {
            throw new NoSuchElementException();
        }

        if (checkPassword(user, password)) {
            this.currUser = Optional.of(user);
            //remove old observers if any
            currUser.get().removeAllObservers();
            //add observers to curr user
            for (Observer o : observers) {
                currUser.get().addObserver(o);
            }
            userLogin();

        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * description:	
     * 	allows the user to logout, sets currUser to optional empty
     */
    public void logout() {
        this.currUser = Optional.empty();
    }

    /**
     * description:
     * 	allows the user to register an account, it checks whether or
     * 	not an account already exists, it will not allow a new account to be
     * 	created. if no account already exists, then it salts and encrypts the
     * 	password before creating the user for security
     * @param fName - String, used to create account
     * @param lName - String, used to create account
     * @param email - String, used to create account
     * @param username - String, used to create account
     * @param password - String, used to create account
     * @throws IllegalArgumentException if user already exists 
     */
    public void register(String fName, String lName, String email, String username, String password) throws IllegalArgumentException {
        //only creating account, if user doesn't already exist
        User user = this.findUser(username);

        if (user != null) {
            throw new IllegalArgumentException();
        }
        //creates password with salt
        String salt = this.createSalt();
        String encryptedPassword = this.encryptPassword(password, salt);
        User newUser = new User(fName, lName, email, username, encryptedPassword, salt);
        //add observers to user
        for (Observer o : observers) {
            newUser.addObserver(o);
        }

        users.add(newUser);
        this.currUser = Optional.of(newUser);
        userLogin();
    }

    /**
     * description: 
     * 	allows for data persistence, saves all of the data when
     * 	program quits
     */
    public void saveData() {
        try (ObjectOutputStream userOutputStream = new ObjectOutputStream(new FileOutputStream("users.dat"))) {
            for (User user : this.users) {
                userOutputStream.writeObject(user);
            }
            userOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * description:
     * 	loads existing data on start up of program, if there is any
     */
    public void loadData() {
        ArrayList<User> loadedUsers = new ArrayList<>();
        try (ObjectInputStream userInputStream = new ObjectInputStream(new FileInputStream("users.dat"))) {
            User user = (User) (userInputStream.readObject());
            while (user != null) {
                loadedUsers.add(user);
                user = (User) (userInputStream.readObject());
            }
            userInputStream.close();
        } catch (EOFException e) {
        // end of file
        } catch (IOException | ClassNotFoundException e) {
        	System.out.println("No stored users");
        }
        if (loadedUsers.size() > 0) {
            this.users = new ArrayList<>(loadedUsers);
        }
    }

    /**
     * description:
     * 	allows user to add an expense
     * @pre currUser != Optional.Empty()
     * @post will add an expense
     * @param expense - Expense, used to add expense
     */
    public void addExpense(Expense expense) {
        assert !currUser.isEmpty();

        User user = currUser.get();

        user.addExpense(expense);

    }

    /**
     * description:
     * 	allows user to delete an expense
     * @pre currUser != Optional.Empty()
     * @post will delete an expense
     * @param id - long, used for identification
     */
    public void deleteExpense(long id) {
        assert !currUser.isEmpty();

        User user = currUser.get();
        user.deleteExpense(id);

    }

    /**
     * description:
     * 	allows user to edit their expense
     * @pre currUser != Optional.Empty()
     * @post will edit an expense
     * @param expense - Expense, used to edit expense
     * @param id - long, used to find expense
     */
    public void editExpense(Expense expense, long id) {
        assert !currUser.isEmpty();

        User user = currUser.get();
        user.editExpense(expense, id);

    }

    /**
     * description:
     * 	allows the user to get an expense
     * @pre currUser != Optional.Empty()
     * @post will get an expense
     * @param id - long, used for identification
     * @return an expense
     */
    public Expense getExpense(long id) {
        assert !currUser.isEmpty();
        return currUser.get().getExpense(id);
    }

    /**
     * description:
     * 	allows the user to get all of their expenses
     * @pre currUser != Optional.Empty()
     * @post will get all expenses
     * @return an array list (user returns a copy, so encapsulation is
     * protected), of all expenses
     */
    public ArrayList<Expense> getAllExpenses() {
        assert !currUser.isEmpty();

        return currUser.get().getAllExpenses();
    }

    /**
     * description:
     * 	allows the user to add a budget in their preferred category
     * @pre currUser != Optional.Empty()
     * @post will add budget
     * @param cat - Category enum, used to determine where to add budget
     * @param amount - double, used to add budget amount
     */
    public void addBudget(Category cat, double amount) {
        assert !currUser.isEmpty();
        User user = currUser.get();
        user.addBudget(cat, amount);
    }
    
    
    /**
     * description:
     * 	allows the user to remove a budget
     * @pre currUser != Optional.Empty()
     * @post will remove budget
     * @param cat - Category enum, used to remove budget
     */
    public void removeBudget(Category cat) {
    	assert !currUser.isEmpty();
    	User user = currUser.get();
        user.removeBudget(cat);
    }

    /**
     * description: 
     * 	allows the user to get their expenses sorted by date and
     * 	category
     * @pre c != null
     * @pre low != null && low < high
     * @pre high != null && high > low
     * @pre currUser != Optional.Empty()
     * @post will get expenses by date and category
     * @param c - Category enum, used to find expenses by category
     * @param low - LocalDate, used as the earlier date for expenses
     * @param high - LocalDate, used as the later date for expenses
     * @return an array list sorted by date and category
     */
    public ArrayList<Expense> getByDateCategory(Category c, LocalDate low, LocalDate high) {
        assert !currUser.isEmpty();

        User user = currUser.get();
        return user.getByDateCategory(c, low, high);
    }

    /**
     * description:
     * 	allows the user to get their expenses sorted by date
    *  @pre low != null && low < high
     * @pre high != null && high > low
     * @pre currUser != Optional.Empty()
     * @post will get expenses by date
     * @return an array list sorted by date
     */
    public ArrayList<Expense> getByDate(LocalDate low, LocalDate high) {
        assert !currUser.isEmpty();

        User user = currUser.get();
        return user.getByDateCategory(low, high);
    }

    /**
     * description: 
     * 	allows the user to get their total expenses in a category by
     * 	date
     * @pre category != null
     * @pre startDate != null && startDate < endDate
     * @pre endDate != null && endDate > startDate
     * @pre currUser != Optional.Empty()
     * @post will get total expense by category and by date
     * @param category - Category, the category of expenses
     * @param startDate - LocalDate, used as low date for expenses
     * @param endDate - LocalDate, used as high date for expenses
     * @return a double representing the total expenses in the category between
     * the selected dates
     */
    public double getTotalExpensesByCategoryByDate(Category category, LocalDate startDate, LocalDate endDate) {
        assert !currUser.isEmpty();

        User user = currUser.get();
        return user.getTotalExpensesByCategoryByDate(category, startDate, endDate);
    }

    /**
     * description: 
     * 	allows the user to get the percent of their budget they have
     * 	used in a category by dates
     * @pre category != null
     * @pre startDate != null && startDate < endDate
     * @pre endDate != null && endDate > startDate
     * @pre currUser != Optional.Empty()
     * @post will get expenses by category percent and by date
     * @param category - Category, the category of expenses
     * @param startDate - LocalDate, used as low date for expenses
     * @param endDate - LocalDate, used as high date for expenses
     * @return a Optional<double> representing the percent of the budget spent
     * in the category between the selected dates returns Optional.empty() if
     * the budget is not set
     */
    public Optional<Double> getExpensesByCategoryPercentByDate(Category category, LocalDate startDate, LocalDate endDate) {
        assert !currUser.isEmpty();

        User user = currUser.get();
        return user.getExpensesByCategoryPercentByDate(category, startDate, endDate);
    }

    /**
     * description:
     * 	allows the user to get their expenses sorted by category
     * @pre c != null
     * @post will get expenses by category
     * @param c - Category enum, used for sorting
     * @return an array list sorted by category
     */
    public ArrayList<Expense> getByCategory(Category c) {
        assert !currUser.isEmpty();

        User user = currUser.get();
        return user.getByDateCategory(c);
    }

    /**
     * description:
     * 	uploads file of expenses to user
     * @param inFile - String, used to add file
     * @return String, confirmation whether file was added
     * @throws FileNotFoundException
     */
    public String addFile(String inFile) throws FileNotFoundException {
        assert !currUser.isEmpty();

        User user = currUser.get();
        return user.addFile(inFile);
    }

    /**
     * description:
     * 	exports current users expenses as a csv file
     * @pre currUser != Optional.empty()
     * @post will export expenses
     * @return boolean, whether or not expenses were exported
     */
    public boolean exportExpenses() {
        assert !currUser.isEmpty();

        User user = currUser.get();
        return user.exportExpenses();
    }

    /**
     * description:
     * 	add observers to controller store
     * @pre o != null
     * @post will add an observer
     * @param o - Observer, used to add observer
     */
    public void addObserver(Observer o) {
        observers.add(o);
    }


    /**
     * description:
     * 	returns user with input username
     * @pre username != null
     * @post will find user
     * @param username - String, used to find user
     * @return User, the found user, would return null if 
     * user is not found, which is immediately caught and
     * dealt with in this class
     */
    private User findUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * description:
     * 	checks if login password is correct
     * @param user - User, used to find user
     * @param pw - String, used to compare to inputed password
     * @return boolean, whether or not the password is correct
     */
    private boolean checkPassword(User user, String pw) {
        String encrypted = this.encryptPassword(pw, user.getSalt());
        return user.getPassword().equals(encrypted);
    }

    /**
     * description:
     * 	salts the users password and encrypts it
     * @param pw - String, used to encrypt users password
     * @param salt - used to add salt for security reasons
     * @return String, encrypted passwords
     */
    private String encryptPassword(String pw, String salt) {
        String currPassword = pw + salt;
        String encryptedPassword = "";
        try {
            MessageDigest message = MessageDigest.getInstance("SHA-256");
            message.update(currPassword.getBytes());
            byte[] bytes = message.digest();
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                str.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            encryptedPassword = str.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptedPassword;
    }

    /**
     * description:
     * 	creates salt for the password, for security
     * @return String, salt
     */
    private String createSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String saltString = new String(salt);
        return saltString;

    }

    /**
     * description:
     * 	alerts of login change
     */
    private void userLogin() {
        this.currUser.get().alertLogin();
        this.currUser.get().alertBudget();
        this.currUser.get().alertExpense();
    }

    /**
     * description:
     * 	allows user to get budget by category
     * @pre category != null
     * @pre currUser != Optional.Empty()
     * @post will get budget by category
     * @param category - Category, used to find budget for inputed category
     * @return Optional<Double>, budget for specified category
     */
    public Optional<Double> getBudgetByCategory(Category category) {
        User user = currUser.get();
        Optional<Double> budget = user.getBudgetByCategory(category);
        return budget;
    }


    /**
     * description: 
     * 	gets username
     * @pre currUser != Optional.Empty()
     * @post will get user details
     * @return String, username, returns null if no current user, 
     * which is immediately caught and dealt with in this class
     */
    public String getUserDetails() {
        if (currUser.isEmpty()) {
            return null;
        }

        User user = currUser.get();
        String name = user.getUsername();
        return name;
    }
    
    /**
     * description: 
     * 	gets first name
     * @pre currUser != Optional.Empty()
     * @post will get first name of user
     * @return String, first name returns null if no current user, 
     * which is immediately caught and dealt with in this class
     */
    public String getFirstName() {
        if (currUser.isEmpty()) {
            return null;
        }

        User user = currUser.get();
        String name = user.getFirstName();
        return name;
    }
}