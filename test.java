
import java.time.LocalDate;

class test {
    public static void main(String[] args) {
        String amount = "35.6";
        Double d = Double.parseDouble(amount);
        System.out.println(d);
        Category cat = Category.valueOf("FOOD");
        String category = cat.toString().toLowerCase();
        System.out.println(cat.ordinal());
        System.out.println(cat);
        Expense e = new Expense(50, LocalDate.of(2024,6,6), "Something", Category.ENTERTAINMENT);
        System.out.println(e);
        
    }
}