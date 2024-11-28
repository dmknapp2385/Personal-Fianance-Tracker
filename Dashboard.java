
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDate;
import java.util.ArrayList;



public class Dashboard extends JPanel implements Observer{
	private JScrollPane expenseScrollPane;
	private JPanel expensePanel;
	private JLabel welcomeLabel;

    public Dashboard(){
        View.controller.addObserver(this);
        setup();
       
    }

    private void setup(){
    	
    	this.setLayout(new BorderLayout());
    	
    	JPanel welcomePanel= new JPanel();
    	welcomePanel.setLayout(new FlowLayout(FlowLayout.CENTER)); //center alignment
    	welcomeLabel = new JLabel();
    	String name=View.controller.getUserDetails();
    	if(name!=null) {
    		welcomeLabel.setText("Welcome, "+name+" to your Dashboard!");
    		
    	}
    	else {
    		welcomeLabel.setText("Welcome to your Dashboard!");
    	}
    	
    	welcomeLabel.setFont(new Font ("Arial", Font.BOLD, 16));
    	welcomePanel.add(welcomeLabel);
    	
    	expensePanel= new JPanel();
    	expensePanel.setLayout(new BorderLayout());
    	JLabel expensesLabel= new JLabel("Your Last 10 expenses: ");
    	expensesLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    	expensePanel.add(expensesLabel, BorderLayout.NORTH);
    	
    	
    	add(welcomePanel, BorderLayout.NORTH);
    	add(new JScrollPane(expensePanel), BorderLayout.CENTER);
    	
    	showTenExpenses(View.controller.sortedTenExpenses());
    	
    	
    	
    	
    }
    private void showTenExpenses(ArrayList <Expense> sortedExpenses) {
    	SwingUtilities.invokeLater(()->{
    		expensePanel.removeAll();
    		if (sortedExpenses==null || sortedExpenses.isEmpty()) {
    			JLabel noExpensesLabel= new JLabel("No expenses yet to display");
        		noExpensesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        		noExpensesLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        		expensePanel.add(noExpensesLabel,BorderLayout.CENTER);
        	
        	}
    		else {
    			String [] columns= {"Date","Description","Category", "Amount"};
    			Object[][] data= new Object [sortedExpenses.size()][4];
            	
            	for ( int i=0; i<sortedExpenses.size();i++) {
            		Expense  e= sortedExpenses.get(i);
            		
            		data[i][0]= e.getDate();
            		data[i][1]=e.getDescription();
            		data[i][2]=e.getCategory();
            		data[i][3]=e.getAmount();
            	}
            	JTable expensesTable= new JTable(data, columns);
            	
            	JScrollPane scrollPane= new JScrollPane(expensesTable);
            	expensePanel.add(scrollPane, BorderLayout.CENTER);
            	
        	
    			
    		}
    		expensePanel.revalidate();
    		expensePanel.repaint();
    		
    		
    		
    	});
    }
    

    @Override
    public void budgetChange() {
        //only change alert box here with budget change
        //update expense labels
        // need method in the user to get last 10 expesense
    	
    	showTenExpenses(View.controller.sortedTenExpenses());
    	
        
    }
    
    @Override
    public void loginChange() {
    	showTenExpenses(View.controller.sortedTenExpenses());
    }
    
    
}