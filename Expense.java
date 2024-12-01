
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;

public class Expense implements Serializable {

    private static final long serialVersionUID = 1L;
	private long id;
    private double amount;
    private LocalDate date;
    private String description;
    private Category category;

    /**
     * description:
     * 	this method constructs an Expense object
     * @param amount - double, used to calculate expense amount
     * @param date - LocalDate, used for date
     * @param description - String, used for expense description
     * @param category - Category enum, used for expense category
     */
    public Expense(double amount, LocalDate date, String description, Category category) {
        this.amount = amount;
        this.date = date;
        this.description = description.toLowerCase();
        this.category = category;
        this.id = System.currentTimeMillis();
    } 

    /**
     * description:
     * 	this method constructs an Expense object by copying an existing Expense object
     * @param expense - Expense, Expense object to be copied
     */
    public Expense(Expense expense) {
        this.amount = expense.amount;
        this.date = expense.date;
        this.description = expense.description;
        this.category = expense.category;
        this.id = expense.id;
    } 
    
    /**
     * description:
     * 	setter for amount instance variable, sets amount
     * @param amount - double, used to set expense amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    
    
    /**
     * description:
     * 	setter for date instance variable, sets date
     * @param date - LocalDate, used to set date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    
    /**
     * description:
     * 	setter for description instance variable, sets description
     * 	for the expense
     * @param description - String, used to create expense description
     */
    public void setDescription(String description) {
        this.description = description.toLowerCase();
    }

    /**
     * description:
     * 	setter for category instance variable, sets category
     * @param category - Category enum, used to create category for expense
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * description:
     * 	getter for the ID
     * @return ID, long
     */
    public long getId() {
        return this.id;
    }

    /**
     * description:
     * 	getter for amount
     * @return amount, double
     */
    public double getAmount() {
        return this.amount;
    }

    /**
     * description:
     * 	getter for immutable date object
     * @return date, LocalDate
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * description:
     * 	getter for description
     * @return description, String
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * description:
     * 	getter for category
     * @return category, Category enum
     */
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
    
    public static final Comparator <Expense> sortByDate(){
    	return new Comparator<Expense>(){

			@Override
			public int compare(Expense e1, Expense e2) {
				return e1.getDate().compareTo(e2.getDate());
			}
    	};
    }
    
    
    
    
}
