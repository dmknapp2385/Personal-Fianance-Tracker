
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class Dashboard extends JPanel implements Observer {
    private static final long serialVersionUID = 1L;
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
            welcomeLabel.setText("<html><span style='white-space:nowrap;'>౨ৎ౨ৎ౨ৎ <b>Welcome to your Dashboard, " + name + "!</b> ౨ৎ౨ৎ౨ৎ</span></html>");
        } else {
            welcomeLabel.setText("<html><span style='white-space:nowrap;'>౨ৎ౨ৎ౨ৎ <b>Welcome to your Dashboard, !</b> ౨ৎ౨ৎ౨ৎ</span></html>");
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
                String[] columns = {"Date", "Description", "Category", "Amount", "Budget Status"};
                Object[][] data = new Object[sub.size()][5];

                for (int i = 0; i < sub.size(); i++) {
                    Expense e = sub.get(i);

                    data[i][0] = e.getDate();
                    data[i][1] = e.getDescription();
                    data[i][2] = e.getCategory();
                    data[i][3] = String.format("$%.2f", e.getAmount());

                    Optional<Double> val = View.controller.getExpensesByCategoryPercent(e.getCategory());
                    if (!val.isEmpty()) {
                        int intVal = (int) Math.round(val.get());
                        data[i][4] = intVal;

                    } else {
                        data[i][4] = 0;
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

    @Override
    public void budgetChange() {
        //only change alert box here with budget change
        //update expense labels
        // need method in the user to get last 10 expenses
        showTenExpenses();

    }

    @Override
    public void loginChange() {
        String name = View.controller.getFirstName();
        welcomeLabel.setText("<html><span style='white-space:nowrap;'>౨ৎ౨ৎ౨ৎ <b>Welcome to your Dashboard, " + name + "</b> ౨ৎ౨ৎ౨ৎ</span></html>");
        showTenExpenses();

    }

    @Override
    public void expenseChange() {

        showTenExpenses();

    }

    private class BudgetStatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (value instanceof Integer) {
                int intValue = (Integer) value;
                if (intValue >= 80) {
                    c.setForeground(Color.RED); // Change color to red for values >= 80
                    ((JLabel) c).setText(intValue + "%");
                } else {
                    c.setForeground(Color.BLACK); // Default color for other values
                    ((JLabel) c).setText(intValue + "%");
                }
            }
            return c;
        }
    }
}
