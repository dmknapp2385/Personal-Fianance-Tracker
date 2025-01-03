/*
 * File Name: AddFrame.java
 * Authors: Paulina Aguirre (paulinaa3), David Herring (dherring), Chitrangada Juneja(cj21), Elle Knapp (dmknapp2385)
 * Description: This class creates a pop up GUI for creating or editing expenses. 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddFrame extends JFrame {

    private static final long serialVersionUID = 1L;
	private JTextField dField = new JTextField("");
    private JTextField dateField = new JTextField("");
    private JTextField amountField = new JTextField("");
	private JComboBox<String> categories;
    private JPanel mainPanel = new JPanel();
    private Long expenseId;
    private JLabel errorTxt = new JLabel("");
    private JButton btn;

    //constructor one, empty text fields
    /**
     * description:
     * 	creates a frame, allowing users to edit expenses
     */
    public AddFrame() {
        //Set frame styles
        this.setTitle("｡˚✧ Personal Finance Tracker: Add Expense ✧˚｡");
        setup();
        this.btn.setText("Add");
        this.btn.setActionCommand("add");
        this.dateField.setText("YYYY-MM-DD");

    }


    /**
     * description:
     * 	another constructor that allows the text to be filled with expenses
     * 	that were clicked
     * @param e - Expense, used to add/edit/delete an expense
     */
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
        deletebtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                View.controller.deleteExpense(expenseId);
                closeFrame();
            }
        });
        mainPanel.add(deletebtn);
    }

    
    //method initializes frame with empty fields and button text
    /**
     * description:
     * 	sets up the expense frame and determines what the user enters for
     * 	it. also does error checking to ensure the user is inputting valid
     * 	formats
     */
    private void setup() {
        Color color = new Color(217, 214, 176);
        this.setBackground(color);
        this.setSize(500, 500);
        this.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new FlowLayout());
        mainPanel.setBackground(new Color(244,243,239));

        Dimension textFieldDim = new Dimension(175, 25);
        JLabel description = new JLabel("Description: ");
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
                        View.controller.addExpense(addEx);

                    } else {
                        View.controller.editExpense(addEx, expenseId);
                    } 
                    closeFrame();

                } catch (Exception exception) {
                    errorTxt.setText("Data not properly formatted, use YYYY-MM-DD and proper amount");
                }
            }
        });

        mainPanel.add(errorTxt);

    }


    /**
     * description:
     * 	allows the user to be able to close the frame
     */
    private void closeFrame() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

}
