
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Dashboard extends JPanel implements Observer {

//    private JScrollPane expenseScrollPane;
    private JPanel expensePanel;
    private JLabel welcomeLabel;

    public Dashboard() {
        View.controller.addObserver(this);
        setup();

    }

    private void setup() {

        this.setLayout(new BorderLayout());

        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new FlowLayout(FlowLayout.CENTER)); //center alignment
        welcomeLabel = new JLabel("");
        String name = View.controller.getUserDetails();
        if (name != null) {
            welcomeLabel.setText("౨ৎ౨ৎ౨ৎ Welcome, " + name + " to your Dashboard! ౨ৎ౨ৎ౨ৎ");

        } else {
            welcomeLabel.setText("౨ৎ౨ৎ౨ৎ Welcome to your Dashboard! ౨ৎ౨ৎ౨ৎ");
        }

        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        welcomePanel.add(welcomeLabel);

        expensePanel = new JPanel();
        expensePanel.setLayout(new BorderLayout());
        JLabel expensesLabel = new JLabel("Your Last 10 expenses: ");
        expensesLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        expensePanel.add(expensesLabel, BorderLayout.NORTH);
        
        add(welcomePanel, BorderLayout.NORTH);
        add(new JScrollPane(expensePanel), BorderLayout.CENTER);

        /*ArrayList <Expense> sortedTenExpenses= View.controller.getAllExpenses();
        	
        		Collections.sort(sortedTenExpenses);
            	int len=10;
            	if (sortedTenExpenses.size()<10) {
            		len=sortedTenExpenses.size();
            	}
            	sortedTenExpenses= (ArrayList<Expense>) sortedTenExpenses.subList(0,len);
            	
        	
        	
        	showTenExpenses(sortedTenExpenses);
         */
    }

    private void showTenExpenses() {
        SwingUtilities.invokeLater(() -> {
            ArrayList<Expense> sortedTenExpenses = View.controller.getAllExpenses();
            Collections.sort(sortedTenExpenses);
            int len = 10;
            if (sortedTenExpenses.size() < 10) {
                len = sortedTenExpenses.size();
            }

            ArrayList<Expense> sub = new ArrayList<Expense>();
            for (int i = sortedTenExpenses.size() - 1; i >= 0; i--) {
                if (i == (sortedTenExpenses.size() - 10)) {
                    break;
                }
                sub.add(sortedTenExpenses.get(i));
            }
            expensePanel.removeAll();
            if (sub == null || sub.isEmpty()) {
                JLabel noExpensesLabel = new JLabel("No expenses yet to display");
                noExpensesLabel.setHorizontalAlignment(SwingConstants.CENTER);
                noExpensesLabel.setFont(new Font("Arial", Font.ITALIC, 14));
                expensePanel.add(noExpensesLabel, BorderLayout.CENTER);
            } else {
                String[] columns = {"Date", "Description", "Category", "Amount"};
                Object[][] data = new Object[sub.size()][4];

                for (int i = 0; i < sub.size(); i++) {
                    Expense e = sub.get(i);

                    data[i][0] = e.getDate();
                    data[i][1] = e.getDescription();
                    data[i][2] = e.getCategory();
                    data[i][3] = String.format("$%.2f", e.getAmount());
                }
                JTable expensesTable = new JTable(data, columns);
                
                Color color = new Color(244,243,239);
                expensesTable.setBackground(color);
                expensesTable.getTableHeader().setBackground(color);
                expensesTable.setShowGrid(false);

                JScrollPane scrollPane = new JScrollPane(expensesTable);
                scrollPane.getViewport().setBackground(color);
                
                expensePanel.add(scrollPane, BorderLayout.CENTER);
            }
            expensePanel.revalidate();
            expensePanel.repaint();
        });
    }

    @Override
    public void budgetChange() {

    }

    @Override
    public void loginChange() {
        String name = View.controller.getFirstName();
        welcomeLabel.setText("<html><span style='white-space:nowrap;'>౨ৎ౨ৎ౨ৎ <b>Welcome to your Dashboard, " + name + "!</b> ౨ৎ౨ৎ౨ৎ</span></html>");
        showTenExpenses();
    }

    @Override
    public void expenseChange() {
        showTenExpenses();
    }
}
