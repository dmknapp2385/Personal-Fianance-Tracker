
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
    	
    			
    	Optional<Double> budgetOpt = View.controller.getBudgetByCategory(category);
        double budget = budgetOpt.orElse(0.0);
        double spent = View.controller.getTotalExpensesByCategory(category);
        double progress = budget > 0 ? (spent / budget) * 100 : 0;

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue((int) progress);
        progressBar.setStringPainted(true);
        progressBar.setForeground(progress >= 80 ? Color.RED : Color.GREEN);

        tableModel.addRow(new Object[]{
                category,
                String.format("%.2f", budget),
                String.format("%.2f", spent),
                progressBar
        });
    }
    	
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