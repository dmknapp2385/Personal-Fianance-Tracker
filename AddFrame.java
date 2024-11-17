
import java.awt.BorderLayout;
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

    //constructor one, empty textfields
    public AddFrame() {
        setup();

    }

    //construct two, fill textfields with expense that was clicked.
    public AddFrame(Expense e) {
        setup();
        //fill in text fields with data in current expense e
    }

    //method initiaizes frame with empty fields and button text
    private void setup() {
        this.setPreferredSize(new Dimension(500, 500));
        this.setLayout(new BorderLayout());
        this.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new FlowLayout());

        JLabel description = new JLabel("Descrition: ");
        JLabel date = new JLabel("Date: ");
        JLabel amount = new JLabel("Amount: ");
        JLabel catLabel = new JLabel("Category");

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
        JButton btn = new JButton("");
        mainPanel.add(btn);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //reset error text
                errorTxt.setText("");
                String command = e.getActionCommand();
                //get textfield input
                String date = dateField.getText();
                String description = dField.getText();
                String amount = dField.getText();
                String category = (String) categories.getSelectedItem();
                Category cat = Category.valueOf(category.toUpperCase());

                //try to convert date
                try {
                    String[] yrMoDay = date.split("-");
                    Integer year = Integer.parseInt(yrMoDay[0]);
                    Integer month = Integer.parseInt(yrMoDay[1]);
                    Integer day = Integer.parseInt(yrMoDay[2]);
                    LocalDate locDate = LocalDate.of(year, month, day);

                    //creat new expense
                    Expense addEx = new Expense(Double.parseDouble(amount), locDate, description, cat);
                    View.controller.addExpense(addEx);
                } catch (Exception exception) {
                    errorTxt.setText("Data not properly formatted, use YYYY-MM-DD or set both to blank to sort by category only");
                }
            }
        });

        mainPanel.add(errorTxt);

    }

}
