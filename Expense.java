import java.time.LocalDate;

public class Expense {
	
	private double amount;
	private LocalDate date;
	private String description;
	private Categories category;	// TODO: waiting for enum class
	
	// Constructor
	public Expense(double amount, LocalDate date, String description, Categories category) {
		this.amount = amount;
		this.date = date;
		this.description = description;
		this.category = category;
	}
	
	// Copy constructor
	public Expense(Expense expense) {
		this.amount = expense.amount;
		this.date = expense.date;
		this.description = expense.description;
		this.category = expense.category;
	}
	
	
	// Setters
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public void getDate(LocalDate date) {
		this.date = date;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setCategory(Categories category) {
		this.category = category;
	}
	
	
	// Getters
	public double setAmount() {
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

	@Override
	public String toString() {
		return this.date + ", " +
				this.category + ", " +
				this.amount + ", " +
				this.description;
	}
}