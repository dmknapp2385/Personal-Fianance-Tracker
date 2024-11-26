
import java.io.FileNotFoundException;

class test {

    public static void main(String[] args) {
        User user = new User("elle", "knapp", "dmknapp23852@gmail.com", "dmknapp2385", "apssword");

        try {
            String errors = user.addFile("expenses.txt");
            System.out.println(errors);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

        System.out.println(user.getAllExpenses());

        user.exportExpenses();
    }
}
