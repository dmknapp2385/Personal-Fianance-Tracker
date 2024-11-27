
import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class ExpenseView extends JPanel implements Observer {

    private JTextField fromField;
    private JTextField toField;
    private JPanel expensePanel;
    private JComboBox<String> catDropdown;
    private JLabel error;
    private JLabel error2;
    private JTextField fileName;

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
        JLabel from = new JLabel("From (YYYY-MM-DD): ");
        this.fromField = new JTextField("");
        fromField.setPreferredSize(textDimension);
        JLabel to = new JLabel("To (YYYY-MM-DD): ");
        this.toField = new JTextField("");
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
        error.setForeground(Color.RED);
        error.setBorder(new EmptyBorder(5, 10, 5, 5));
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

        //add bottom import and export buttons
        JPanel importPanel = new JPanel();
        this.add(importPanel, BorderLayout.SOUTH);
        JLabel file = new JLabel("enter name of text file to import: ");
        this.fileName = new JTextField();
        fileName.setPreferredSize(new Dimension(150, 25));
        importPanel.add(file);
        importPanel.add(fileName);

        JButton importBtn = new JButton("Import");
        importBtn.setActionCommand("import");
        JButton exportBtn = new JButton("Export All");
        exportBtn.setActionCommand("export");
        importBtn.addActionListener(new ButtonActionListener());
        exportBtn.addActionListener(new ButtonActionListener());
        importPanel.add(importBtn);
        importPanel.add(exportBtn);

        this.error2 = new JLabel("");
        error2.setForeground(Color.RED);
        error2.setBorder(new EmptyBorder(5, 15, 5, 5));
        importPanel.add(error2);

    }

    private class ButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            //reset error2 text
            error2.setText("");
            error2.setForeground(Color.RED);

            //get action command
            String command = e.getActionCommand();

            if (command.equals("search")) {
                String category = (String) catDropdown.getSelectedItem();
                String toDate = toField.getText();
                String fromDate = fromField.getText();

                //parse date range if entered
                if (!(toDate.equals("") || fromDate.equals(""))) {
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

                        //remove error text if any
                        error.setText("");

                    } catch (Exception exception) {
                        error.setText("Invalid Date Range");
                    }

                } else {
                    //only search by category
                    if (!category.equals("All")) {
                        Category cat = Category.valueOf(category.toUpperCase());
                        showAllExpenses(View.controller.getByCategory(cat));
                    } else {
                        showAllExpenses(View.controller.getAllExpenses());
                    }

                    //remove error text if any
                    error.setText("");
                }

            } else if (command.equals("add")) {
                JFrame popup = new AddFrame();
                popup.setVisible(true);
            } else if (command.equals("import")) {
                String filename = fileName.getText().trim();
                try {
                    String notAdded = View.controller.addFile(filename);
                    //ensure error and text field are blank
                    error2.setText(notAdded);
                    fileName.setText("");
                    //TODO: add alert in user so no need to call showAll Expenses
                    showAllExpenses(View.controller.getAllExpenses());
                } catch (Exception fe) {
                    error2.setText("File Not Found");
                    error2.setForeground(Color.RED);
                }
            } else if (command.equals("export")) {
                if (View.controller.exportExpenses()) {
                    error2.setText("Expenses Exported!");
                    error2.setForeground(Color.BLUE);
                } else {
                    error2.setText("Trouble Exporting Expenses");
                    error2.setForeground(Color.RED);
                }
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
        this.toField.setText("");
        this.fromField.setText("");
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
        this.revalidate();
        this.repaint();
    }
}
