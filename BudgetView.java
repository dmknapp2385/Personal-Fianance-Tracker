
import java.awt.BorderLayout;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class BudgetView extends JPanel implements Observer{
	private JTable budgetTable;
	private DefaultTableModel tableModel;
	
	
	
	
    public BudgetView(){
    	
        View.controller.addObserver(this);
        this.setLayout(new BorderLayout());
        
        JScrollPane scrollPane= new JScrollPane(budgetTable);
        add(scrollPane, BorderLayout.CENTER);
        
        
        checkAlerts(); // function to check if gone over 80%
        setVisible(true);
        

       
        
    }
    private void addBudgetRow(Category category) {
    	
    	// already calculated the percent in the internal function
    	// we use that as well as the budgets assigned for this
        Optional<Double> budget = View.controller.getBudgetByCategory(category);
        
        JLabel categoryLabel= new JLabel(category.toString());
        JLabel budgetLabel= new JLabel(budget.isPresent() ? String.format("$%.2f",  budget.get()) :"No budget set yet" );
        
        Optional <Double> catPercent= View.controller.getExpensesByCategoryPercent(category);
        
        //String percentage= catPercent.isPresent() ? String.format()
        
        
        
        
    }
    
    
    
    
    
    
    private void checkAlerts() {
    	
    }
    
    
    
    

    @Override
    public void budgetChange() {
    	// Create a setup() method for the layout
        // controller.addBudget()
    }

    @Override
    public void loginChange() {
    	
        
    }
}