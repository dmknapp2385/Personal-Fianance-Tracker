
import java.time.LocalDate;

class test {

    public static void main(String[] args) {

        Expense expense = new Expense(15.99, LocalDate.of(2024, 9, 18), "fun", Category.ENTERTAINMENT);
        Expense expense2 = new Expense(100.00, LocalDate.of(2024, 11, 25), "water", Category.UTILITIES);
        Controller controller = Controller.instanceOf();
        
        controller.register("elle", "knapp", "dmknapp23852@gmail.com", "dmknapp2385", "apssword");

        controller.addExpense(expense);
        System.out.println(expense.getId());
        Expense result = controller.getExpense(expense.getId());
        System.out.println(expense.getDescription());
        System.out.println(result.getDescription());

    }
}
