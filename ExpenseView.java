
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class ExpenseView extends JPanel implements Observer {

    private JTextField fromField;
    private JTextField toField;
    private JPanel expensePanel;

    public ExpenseView() {
        View.controller.addObserver(this);

        this.setLayout(new BorderLayout());

        //button panel
        JPanel buttonPanel = new JPanel();
        this.add(buttonPanel, BorderLayout.NORTH);

        //add category dropdown box
        JLabel catFilter = new JLabel("Filter by category ");
        buttonPanel.add(catFilter);
        String[] categories = {"All", "Food", "Transportation", "Entertainment", "Utilities", "Miscellaneous"};
        JComboBox<String> catDropdown = new JComboBox<>(categories);
        buttonPanel.add(catDropdown);

        //add date filter
        Dimension textDimension = new Dimension(85, 25);
        JLabel from = new JLabel("From: ");
        this.fromField = new JTextField("YYYY-MM-DD");
        fromField.setPreferredSize(textDimension);
        JLabel to = new JLabel("To: ");
        this.toField = new JTextField("YYYY-MM-DD");
        toField.setPreferredSize(textDimension);
        buttonPanel.add(from);
        buttonPanel.add(fromField);
        buttonPanel.add(to);
        buttonPanel.add(toField);

        //add buttons
        JButton searchBtn = new JButton("Search");
        JButton addBtn = new JButton("+");
        searchBtn.setPreferredSize(new Dimension(100, 25));
        addBtn.setPreferredSize(new Dimension(42, 25));

        searchBtn.setActionCommand("search");
        addBtn.setActionCommand("add");
        buttonPanel.add(searchBtn);
        buttonPanel.add(addBtn);
        searchBtn.addActionListener(new ButtonActionListener());
        addBtn.addActionListener(new ButtonActionListener());

        //add scroll panel for expense list
        this.expensePanel = new JPanel(new GridLayout(15, 1, 0, 5));
        JScrollPane scroll = new JScrollPane(expensePanel);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(scroll, BorderLayout.CENTER);
    }

    private class ButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("search")) {
                //show expenses sorted by category and date range

            } else if (command.equals("add")) {
                JFrame popup = new AddFrame();
                popup.setVisible(true);
            } else {
                //get expense id
                Long id = Long.parseLong(command.split(":")[1]);
                Expense expense = View.controller.getExpense(id);
                JFrame editpopup = new AddFrame(expense);
                editpopup.setVisible(true);
            }
        }
    }

    @Override
    public void budgetChange() {
        System.out.println("iNside budgetchange expense view");
        expensePanel.removeAll();
        showAllExpenses();
    }

    @Override
    public void loginChange() {
        showAllExpenses();
    }

    private void showAllExpenses() {
        ArrayList<Expense> expenses = View.controller.getAllExpenses();
        System.out.println(expenses);
        
        for (Expense e : expenses) {
            //create button with expense and edit button with expense id
            JButton btn = new JButton(e.toString());
            btn.setPreferredSize(new Dimension(200, 28));
            btn.addActionListener(new ButtonActionListener());
            btn.setActionCommand("edit:" + e.getId());
            expensePanel.add(btn);

        }
    }

    //show all expenses by category/date range
    private void showAllExpenses(ArrayList<Expense> expenses) {

        //get expesense and then show
    }
}
