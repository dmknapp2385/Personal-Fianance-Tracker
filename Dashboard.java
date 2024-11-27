
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;



public class Dashboard extends JPanel implements Observer{

    public Dashboard(){
        View.controller.addObserver(this);
        setup();
       
    }

    private void setup(){
    	
    	this.setLayout(new BorderLayout());
    	
    	JPanel welcomePanel= new JPanel();
    	welcomePanel.setLayout(new FlowLayout(FlowLayout.CENTER)); //center alignment
    	JLabel welcomeLabel = new JLabel();
    	String name=View.controller.getUserDetails();
    	if(name!=null) {
    		welcomeLabel.setText("Welcome, "+name+" to your Dashboard!");
    		
    	}
    	else {
    		welcomeLabel.setText("Welcome to your Dashboard!");
    	}
    	
    	welcomeLabel.setFont(new Font ("Arial", Font.BOLD, 16));
    	welcomePanel.add(welcomeLabel);
    	
    	//displaying expenses
    	
    	JPanel expensesPanel= new JPanel();
    	expensesPanel.setLayout(new BorderLayout());
    	JLabel expensesLabel= new JLabel("Your Last 10 expenses: ");
    	expensesLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    	expensesPanel.add(expensesLabel, BorderLayout.NORTH);
    	
    	//Tabular display
    	String [] columns= {"Date","Description","Category", "Amount"};
    	ArrayList <Expense> displayExpenses= View.controller.sortedTenExpenses();
    	if (displayExpenses==null || displayExpenses.isEmpty()) {
    		JLabel noExpensesLabel= new JLabel("No expenses yet to display");
    		noExpensesLabel.setHorizontalAlignment(SwingConstants.CENTER);
    		noExpensesLabel.setFont(new Font("Arial", Font.ITALIC, 14));
    		expensesPanel.add(noExpensesLabel,BorderLayout.CENTER);
    		
    		
    	}
    	else {
    		Object[][] data= new Object [displayExpenses.size()][4];
        	
        	for ( int i=0; i<displayExpenses.size();i++) {
        		Expense  e= displayExpenses.get(i);
        		
        		data[i][0]= e.getDate();
        		data[i][1]=e.getDescription();
        		data[i][2]=e.getCategory();
        		data[i][3]=e.getAmount();
        	}
        	JTable expensesTable= new JTable(data, columns);
        	
        	JScrollPane scrollPane= new JScrollPane(expensesTable);
        	expensesPanel.add(scrollPane, BorderLayout.CENTER);
        	
    	}
    	add(welcomePanel, BorderLayout.NORTH);
    	add(expensesPanel, BorderLayout.CENTER);
    	
    	
    }

    @Override
    public void budgetChange() {
        //only change alert box here with budget change
        //update expense labels
        // need method in the user to get last 10 expesense
    	
    	this.setup();
    	
        
    }
    
    @Override
    public void loginChange() {
       this.setup();
    }
    
    @Override
    public void expenseChange() {
    	this.setup();
    	
    }
}