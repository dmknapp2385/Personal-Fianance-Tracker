
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddFrame extends JFrame {

    private JTextField dField = new JTextField("");
    private JTextField dateField = new JTextField("");
    private JTextField amountField = new JTextField("");
    private JComboBox categories;
    private JPanel mainPanel = new JPanel();
    private Long expenseId;
    private JLabel errorTxt = new JLabel("");
    private JButton btn;

    //constructor one, empty textfields
    public AddFrame() {
        //Set frame styles
        this.setTitle("Personal Finance Tracker : Add Expense");
        setup();
        this.btn.setText("Add");
        this.btn.setActionCommand("add");
        this.dateField.setText("YYYY-MM-DD");

    }

    //construct two, fill textfields with expense that was clicked.
    public AddFrame(Expense e) {
        //set current expense id
        this.expenseId = e.getId();

        //Set frame styles
        this.setTitle("Personal Finance Tracker : Edit Expense");

        setup();

        //add text from current expense
        dateField.setText(e.getDate().toString());
        dField.setText(e.getDescription());
        amountField.setText(Double.toString(e.getAmount()));
        categories.setSelectedIndex(e.getCategory().ordinal());

        btn.setText("Edit");
        btn.setActionCommand("edit");

        JButton deletebtn = new JButton("Delete");
        deletebtn.setActionCommand("delete");
        mainPanel.add(deletebtn);
    }

    //method initiaizes frame with empty fields and button text
    private void setup() {
        Color color = new Color(217, 214, 176);
        this.setBackground(color);
        this.setSize(500, 500);
        this.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new FlowLayout());

        Dimension textFieldDim = new Dimension(175, 25);
        JLabel description = new JLabel("Descrition: ");
        JLabel date = new JLabel("Date: ");
        JLabel amount = new JLabel("Amount: ");
        JLabel catLabel = new JLabel("Category");

        this.dField.setPreferredSize(textFieldDim);
        this.dateField.setPreferredSize(textFieldDim);
        this.amountField.setPreferredSize(textFieldDim);

        mainPanel.add(description);
        mainPanel.add(dField);
        mainPanel.add(date);
        mainPanel.add(dateField);
        mainPanel.add(amount);
        mainPanel.add(amountField);
        mainPanel.add(catLabel);

        //add dropdowns to category
        String[] categoryList = {"Food", "Transportation", "Entertainment", "Utilities", "Miscellaneous"};
        this.categories = new JComboBox<>(categoryList);
        mainPanel.add(categories);

        //addButton
        this.btn = new JButton("");
        mainPanel.add(btn);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //reset error text
                errorTxt.setText("");
                String command = e.getActionCommand();
                //get textfield input
                String date = dateField.getText();
                String description = dField.getText();
                String amount = amountField.getText();
                String category = (String) categories.getSelectedItem();
                Category cat = Category.valueOf(category.toUpperCase());
                //try to convert date
                try {
                    String[] yrMoDay = date.split("-");
                    Integer year = Integer.parseInt(yrMoDay[0].trim());
                    Integer month = Integer.parseInt(yrMoDay[1].trim());
                    Integer day = Integer.parseInt(yrMoDay[2].trim());
                    LocalDate locDate = LocalDate.of(year, month, day);
                    Double amountD = Double.parseDouble(amount.trim());
                    Expense addEx = new Expense(amountD, locDate, description, cat);
                    if (command.equals("add")) {
                        System.out.println(addEx);
                        View.controller.addExpense(addEx);
                    } else if (command.equals("edit")) {
                        View.controller.editExpense(addEx, expenseId);
                    } else {
                        View.controller.deleteExpense(expenseId);
                    }

                    //clear text fields
                    dField.setText("");
                    dateField.setText("YYYY-MM-DD");
                    amountField.setText("");

                } catch (Exception exception) {
                    errorTxt.setText("Data not properly formatted, use YYYY-MM-DD and proper amount");
                }
            }
        });

        mainPanel.add(errorTxt);

    }

}
