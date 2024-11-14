
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Controller {

    //create singleton variable
    private static final Controller INSTANCE = new Controller();
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
