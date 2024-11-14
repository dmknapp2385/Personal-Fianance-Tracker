
import java.time.LocalDate;

public class Expense {

    private int id;
    private double amount;
    private LocalDate date;
    private String description;
    private Category category;	// TODO: waiting for enum class

    // Constructor
    public Expense(int id, double amount, LocalDate date, String description, Category category) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
    }

    // Copy constructor
    public Expense(Expense expense) {
        this.id = expense.id;
        this.amount = expense.amount;
        this.date = expense.date;
        this.description = expense.description;
        this.category = expense.category;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // Getters
    public int getAmount() {
        return this.id;
    }

    public double setAmount() {
        return this.amount;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public String getDescription() {
        return this.description;
    }

    public Category getCategory() {
        return this.category;
    }

    @Override
    public String toString() {
        return this.date + ", "
                + this.category + ", "
                + this.amount + ", "
                + this.description;
    }
}
