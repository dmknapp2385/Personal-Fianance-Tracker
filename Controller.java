
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Controller {
	
	
//	public static void main(String[] args) {
//		String salt = createSalt();
//		String pass = encryptPassword("hihihih", salt);
//		System.out.println(pass);
//	}

    //create singleton variable
    private static final Controller INSTANCE = new Controller();

    //instance variables
    private ArrayList<User> users = new ArrayList<>();
    private Optional<User> currUser;

    ArrayList<Observer> observers = new ArrayList<>();

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

    //method logs out curr user
    public void logout() {
        this.currUser = Optional.empty();
    }

    //method creates new user and sets to curr user
    //thows IllegalArgument Exception if useralready exits
    public void register(String fName, String lName, String email, String username, String password) throws IllegalArgumentException {
        //only creating account, if user doesn't already exist
        User user = this.findUser(username);

        if (user != null) {
            throw new IllegalArgumentException();
        }

        //String encryptedPassword = this.encryptPassword(password);
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

    //method saves all data when program quits
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

    //method loads all data on start up of program(if exists)
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
            e.printStackTrace();
        }
        if (loadedUsers.size() > 0) {
            this.users = new ArrayList<>(loadedUsers);
        }
    }

    //All methods to access currUser public methods here
    public void addExpense(Expense expense) {
        assert !currUser.isEmpty();

        User user = currUser.get();

        user.addExpense(expense);
    }

    //deletes expense with id
    public void deleteExpense(long id) {
        assert !currUser.isEmpty();

        User user = currUser.get();
        user.deleteExpense(id);
    }

    //edits expense with id
    public void editExpense(Expense expense, long id) {
        assert !currUser.isEmpty();

        User user = currUser.get();
        user.editExpense(expense, id);
    }

    //gets expense by id
    public Expense getExpense(long id) {
        assert !currUser.isEmpty();
        return currUser.get().getExpense(id);
    }

    //get all expenses
    public ArrayList<Expense> getAllExpenses() {
        assert !currUser.isEmpty();

        return currUser.get().getAllExpenses();
    }

    //adds a budge amount for category
    public void addBudget(Category cat, double amount) {
        assert !currUser.isEmpty();
        User user = currUser.get();
        user.addBudget(cat, amount);
    }

    //get expenses sorted by date and category
    public ArrayList<Expense> getbyDateCategory(Category c, LocalDate low, LocalDate high) {
        assert !currUser.isEmpty();

        User user = currUser.get();
        return user.getByDateCategory(c, low, high);
    }

    //returns an arraylist of expenses sorted by date range low through high
    public ArrayList<Expense> getByDate(LocalDate low, LocalDate high) {

        assert !currUser.isEmpty();

        User user = currUser.get();
        return user.getByDateCategory(low, high);
    }

    //returns an array list of expenses sorted by category
    public ArrayList<Expense> getByCategory(Category c) {
        assert !currUser.isEmpty();

        User user = currUser.get();
        return user.getByDateCategory(c);
    }

    //upload file of expenses to user
    //throws file not found exception
    public String addFile(String inFile) throws FileNotFoundException {
        assert !currUser.isEmpty();

        User user = currUser.get();
        return user.addFile(inFile);
    }

    //exports current users expenses as a csv file
    public boolean exportExpenses() {
        assert !currUser.isEmpty();

        User user = currUser.get();
        return user.exportExpenses();
    }

    //add observers to controller store
    public void addObserver(Observer o) {
        observers.add(o);
    }

    //gets percentage of current month spending by category
    public double getpercentSpending(Category cat) {
        assert !currUser.isEmpty();

        User user = currUser.get();
        return user.getPercentSpending(cat);
    }

    //method returns user with input username
    public User findUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    
 
    
    //Checks if login password is equal to stored user password
    public boolean checkPassword(User user, String pw) {
        String encrypted = this.encryptPassword(pw, user.getSalt());
        return user.getPassword().equals(encrypted);
    }

    
    //encrypts password with salt
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
    
    
    private String createSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String saltString = new String(salt);
        return saltString;
    	
    }

    //Method alerts current user to loginchange
    private void userLogin() {
        this.currUser.get().alertLogin();
    }
    
    public Optional <Double> getBudgetByCategory(Category category){
    	User user=currUser.get();
    	Optional <Double> budget= user.getBudgetByCategory(category);
    	return budget;
    }

	

    
    
	public Optional<Double> getExpensesByCategoryPercent(Category category) {
		User user=currUser.get();
		Optional<Double> catPercent=user.getExpensesByCategoryPercent(category);
		return catPercent;
	
	}
}
