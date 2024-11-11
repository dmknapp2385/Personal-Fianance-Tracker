import java.util.ArrayList;
import java.util.Optional;

public class Controller {
	private ArrayList<User> users;
	private Optional<User> currUser;
	
	
	
	public Controller() {
		
	}
	
	
	public void findUser(String username) {
		for (User user: users) {
			//needs getUsername method in user class
			if (user.getUsername().equals(username)) {
				currUser = Optional.of(user);
			}
		}
	}
	
	
}