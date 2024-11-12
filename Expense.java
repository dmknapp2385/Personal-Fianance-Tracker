
import java.time.LocalDate;

public class Expense {

    private final long id;
    private double amount;
    private LocalDate date;
    private String description;
    private Categories category;

    // Constructor
    public Expense(int id, double amount, LocalDate date, String description, Categories category) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
        this.id = System.currentTimeMillis();
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

    public void setCategory(Categories category) {
        this.category = category;
    }

    // Getters
    public double getAmount() {
        return this.amount;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public String getDescription() {
        return this.description;
    }

    public Categories getCategory() {
        return this.category;
    }

    public long getId(){
        return this.id;
    }

    @Override
    public String toString() {
        return this.date + ", "
                + this.category + ", "
                + this.amount + ", "
                + this.description;
    }
}
