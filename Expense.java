
import java.time.LocalDate;

public class Expense {

    private long id;
    private double amount;
    private LocalDate date;
    private String description;
    private Category category;

    // Constructor
    public Expense(double amount, LocalDate date, String description, Category category) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
        this.id = System.currentTimeMillis();
    }

    //setters
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
    public long getId() {
        return this.id;
    }

    public double getAmount() {
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
