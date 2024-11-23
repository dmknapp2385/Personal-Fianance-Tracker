
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class ExpenseView extends JPanel implements Observer {

    private JTextField fromField;
    private JTextField toField;
    private JPanel expensePanel;
    private JTextArea expenseArea;
    private JComboBox<String> catDropdown;
    private JLabel error;

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
        this.catDropdown = new JComboBox<>(categories);
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

        //add error label
        this.error = new JLabel("");
        buttonPanel.add(error);

        //add scroll panel for expense list
        // this.expenseArea = new JTextArea();
        // expenseArea.setEditable(false);
        // JScrollPane scroll = new JScrollPane(expenseArea);
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
                String category = (String) catDropdown.getSelectedItem();
                String toDate = toField.getText();
                String fromDate = fromField.getText();

                //parse date range if entered
                if (!(toDate.equals("YYYY-MM-DD") || toDate.equals(""))) {
                    try {
                        String[] yrMoDay = toDate.split("-");
                        Integer year = Integer.parseInt(yrMoDay[0].trim());
                        Integer month = Integer.parseInt(yrMoDay[1].trim());
                        Integer day = Integer.parseInt(yrMoDay[2].trim());
                        LocalDate tLocalDate = LocalDate.of(year, month, day);
                        yrMoDay = fromDate.split("-");
                        year = Integer.parseInt(yrMoDay[0].trim());
                        month = Integer.parseInt(yrMoDay[1].trim());
                        day = Integer.parseInt(yrMoDay[2].trim());
                        LocalDate fLocalDate = LocalDate.of(year, month, day);
                        //check to is date after from
                        if (toDate.compareTo(fromDate) < 0) {
                            throw new IllegalArgumentException();
                        }

                        //search by category and date if category is not all
                        if (!category.equals("All")) {
                            Category cat = Category.valueOf(category.toUpperCase());
                            showAllExpenses(View.controller.getbyDateCategory(cat, fLocalDate, tLocalDate));
                        } else {
                            //only search by date range
                            showAllExpenses(View.controller.getByDate(fLocalDate, tLocalDate));
                        }
                    } catch (Exception exception) {
                        error.setText("Invalid Date Range");
                    }

                } else {
                    //only search by category
                    if (!category.equals("All")) {
                        Category cat = Category.valueOf(category.toUpperCase());
                        showAllExpenses(View.controller.getByCategory(cat));
                    }
                }

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
        //set filter components to zero and get all expenses
        this.catDropdown.setSelectedIndex(0);
        this.toField.setText("YYYY-MM-DD");
        this.fromField.setText("YYYY-MM-DD");
        showAllExpenses(View.controller.getAllExpenses());
    }

    @Override
    public void loginChange() {
        showAllExpenses(View.controller.getAllExpenses());
    }

    //show all expenses by category/date range
    private void showAllExpenses(ArrayList<Expense> expenses) {
        System.out.println("Inside showallexpenses: " + expenses);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                expensePanel.removeAll();
            }
        });

        for (Expense e : expenses) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    //create button with expense and edit button with expense id
                    JButton btn = new JButton(e.toString());
                    btn.setPreferredSize(new Dimension(200, 28));
                    btn.addActionListener(new ButtonActionListener());
                    btn.setActionCommand("edit:" + e.getId());
                    expensePanel.add(btn);
                }

            });
        }
    }
}
