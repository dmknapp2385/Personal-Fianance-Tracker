
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class Dashboard extends JPanel implements Observer {
	
    private static final long serialVersionUID = 1L;
    private JPanel expensePanel;
    private JLabel welcomeLabel;
    JLabel expensesLabel;
	
	/**
	 * description: Constructor that sets up the 
	 * dashboard's default view
	 */

    public Dashboard() {
        View.controller.addObserver(this);
        setup();
    }
    
    /**
     * description: function that sets up the base view for the user once logged in. 
     * sets up panels and labels accordingly, to show any expenses if present
     * for current user logged in. 
     */

    private void setup() {
        this.setLayout(new BorderLayout());

        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new FlowLayout(FlowLayout.CENTER)); //center alignment
        welcomeLabel = new JLabel("");
        String name = View.controller.getUserDetails();
        if (name != null) {
            welcomeLabel.setText("<html><span style='white-space:nowrap;'>౨ৎ౨ৎ౨ৎ <b>Welcome to your Dashboard, " + name + "</b> ౨ৎ౨ৎ౨ৎ</span></html>");
        } else {
            welcomeLabel.setText("<html><span style='white-space:nowrap;'>౨ৎ౨ৎ౨ৎ <b>Welcome to your Dashboard</b> ౨ৎ౨ৎ౨ৎ</span></html>");
        }

        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        welcomePanel.add(welcomeLabel);

        expensePanel = new JPanel();
        expensePanel.setLayout(new BorderLayout());
        expensesLabel = new JLabel("Your last 10 expenses: ");
        expensesLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        expensePanel.add(expensesLabel, BorderLayout.NORTH);

        add(welcomePanel, BorderLayout.NORTH);
        add(new JScrollPane(expensePanel), BorderLayout.CENTER);
    }
    
    /** 
     * description:provides a comprehensive look for the user's expenses.
     * private function runnable that displays the expenses so far incurred;
     * displays the most recent 10 expense inputs by the user. uses a JTable to show
     * the following info about an expense: date, description,
     * category, amount, and the percent of the monthly budget it accounts for. shows 
     * an alert if expense causes 80% over budget.
     * updates accordingly. 
     */

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
            
            expensesLabel = new JLabel("Your last 10 expenses: ");
            expensesLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            expensePanel.add(expensesLabel, BorderLayout.NORTH);

            if (sub == null || sub.isEmpty()) {
                JLabel noExpensesLabel = new JLabel("No expenses yet to display");
                noExpensesLabel.setHorizontalAlignment(SwingConstants.CENTER);
                noExpensesLabel.setFont(new Font("Arial", Font.ITALIC, 14));
                expensePanel.add(noExpensesLabel, BorderLayout.CENTER);

            } else {
                String[] columns = {"Date", "Description", "Category", "Amount", "% of Monthly Budget"};
                Object[][] data = new Object[sub.size()][5];

                for (int i = 0; i < sub.size(); i++) {
                    Expense e = sub.get(i);

                    data[i][0] = e.getDate();
                    data[i][1] = e.getDescription();
                    data[i][2] = e.getCategory();
                    data[i][3] = String.format("$%.2f", e.getAmount());

                    Optional<Double>bud = View.controller.getBudgetByCategory(e.getCategory());
                    if (!bud.isEmpty()) {
                        int intVal = (int) Math.round(e.getAmount()/bud.get() * 100);
                        data[i][4] = intVal;
                    } else {
                        data[i][4] = -1;
                    }

                }
                Color color = new Color(244,243,239);
                
                JTable expensesTable = new JTable(data, columns);
                expensesTable.setBackground(color);
                expensesTable.getTableHeader().setBackground(color);
                expensesTable.setShowGrid(false);

                JScrollPane scrollPane = new JScrollPane(expensesTable);
                scrollPane.getViewport().setBackground(color);
                expensesTable.getColumnModel().getColumn(4).setCellRenderer(new BudgetStatusRenderer());
                
                expensePanel.add(scrollPane, BorderLayout.CENTER);
            }
            expensePanel.revalidate();
            expensePanel.repaint();

        });
    }
   /**
    * description: observer function called when change in budget detected
    */
    @Override
    public void budgetChange() {
        //only change alert box here with budget change
        //update expense labels
        // need method in the user to get last 10 expenses
    	
        showTenExpenses();

    }
    /**
     * description: observer function called when change in user's login details
     * is detected.
     */

    @Override
    public void loginChange() {
        String name = View.controller.getFirstName();
        welcomeLabel.setText("<html><span style='white-space:nowrap;'>౨ৎ౨ৎ౨ৎ <b>Welcome to your Dashboard, " + name + "</b> ౨ৎ౨ৎ౨ৎ</span></html>");
     
        showTenExpenses();

    }
    /**
     * description: observer function called when change in an expense is detected.
     */

    @Override
    public void expenseChange() {
    	
        showTenExpenses();

    }
    /**
     * description: private inner class used for the budget column's 
     * display. if over 80%, changes color of cell in the table to reflect that.
     */

    private class BudgetStatusRenderer extends DefaultTableCellRenderer {
        @Override
        /**
         * description: function that returns the rendered cell as per the 
         * specifications. 
         * @param:table - the JTable
         * @param:value - the value to assign to the cell at [row, column]
         * @param:isSelected - true if cell is selected
         * @param:hasFocus - true if cell has focus
         * @param:row - the row of the cell to render
         * @param:column - the column of the cell to render
         * 
         * @return:the default table cell renderer
         */
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (value instanceof Integer) {
                int intValue = (Integer) value;
                if (intValue >= 80) {
                    c.setForeground(Color.RED); // Change color to red for values >= 80
                    ((JLabel) c).setText(intValue + "%");
                } else if (intValue == -1) {
                	c.setForeground(Color.BLACK);
                	((JLabel) c).setText("N/A");
            	} else {
                    c.setForeground(Color.BLACK); // Default color for other values
                    ((JLabel) c).setText(intValue + "%");
                }
            }
            return c;
        }
    }
}