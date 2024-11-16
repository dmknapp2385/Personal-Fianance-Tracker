
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class Controller {

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
            for (Observer o : observers) {

            }

        } else {
            throw new NoSuchElementException();
        }
    }

    //method logs out curr user
    public void logout() {

    }

    //method creates new user and sets to curr user
    public void register(String fName, String lName, String email, String username, String password) {
        //only creating account, if user doesn't already exist
        User user = this.findUser(username);
        if (user == null) {
            String encryptedPassword = this.encryptPassword(password);
            User newUser = new User(fName, lName, email, username, encryptedPassword);
            this.currUser = Optional.of(newUser);
        } else {
            this.currUser = Optional.of(user);
        }
        //should there be a message stating the account already exists?

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

    public ArrayList<Expense> getByDate(LocalDate low, LocalDate high) {

        assert !currUser.isEmpty();

        User user = currUser.get();
        return user.getByDate(low, high);
    }

    public ArrayList<Expense> getByCategory(Category c) {
        assert !currUser.isEmpty();

        User user = currUser.get();
        return user.getByCategory(c);
    }

    //upload file of expenses to user
    //throws file not found exception
    public void addFile(String inFile) throws FileNotFoundException {
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

    public boolean checkPassword(User user, String pw) {
        String encrypted = this.encryptPassword(pw);
        return user.getPassword().equals(encrypted);
    }

    //method to check password (using encryption?? need to ask)
    //encrpt pw
    private String encryptPassword(String pw) {
        String encryptedPassword = "";
        try {
            MessageDigest message = MessageDigest.getInstance("MD5");
            message.update(pw.getBytes());
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

}
