/*
 * File Name: AddBudgetFrame.java
 * Authors: Paulina Aguirre (paulinaa3), David Herring (dherring), Chitrangada Juneja(cj21), Elle Knapp (dmknapp2385)
 * Description: This class creates a pop up GUI for editing budgets. 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class AddBudgetFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JTextField amountField = new JTextField("");
    private JPanel mainPanel = new JPanel();
    private JLabel errorTxt = new JLabel("");
    private JButton btn;
    private Category cat;

    
    /**
     * description:
     * 	creates a frame, allowing user to add budgets
     * @param cat - Category, used to determine where to add budget
     */
    public AddBudgetFrame(Category cat) {
    	this.cat = cat;
        this.setTitle("｡˚✧ Personal Finance Tracker: Add Budget ✧˚｡");
        setUp();
        this.btn.setText("Add");
        this.btn.setActionCommand("add");
    }

    
    /**
     * description:
     * 	sets up the budget frame and determines what the user enters for
     * 	the budget. if the user enters 0, the budget is deleted, if the 
     * 	user enters a number, the budget is added/edited. also does error
     * 	checking to ensure data is properly formatted
     */
    private void setUp() {
         this.setSize(500, 500);
         this.add(mainPanel, BorderLayout.CENTER);
         mainPanel.setLayout(new FlowLayout());
         mainPanel.setBackground(new Color(244,243,239));

         Dimension textFieldDim = new Dimension(175, 25);
         JLabel amount = new JLabel("Amount: ");

         this.amountField.setPreferredSize(textFieldDim);
         mainPanel.add(amount);
         mainPanel.add(amountField);

         //addButton
         this.btn = new JButton("");
         mainPanel.add(btn);
         btn.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 //reset error text
                 errorTxt.setText("");
                 String command = e.getActionCommand();
                 String amount = amountField.getText();
                 try {
                     Double amountD = Double.parseDouble(amount.trim());
                     if (command.equals("add")) {
                    	 if (amountD == 0.0) {
                    		 View.controller.removeBudget(cat);
                    	 }
                    	 else {
                    		 View.controller.addBudget(cat, amountD); 
                    	 }
                     }
                     closeFrame();

                 } catch (Exception exception) {
                     errorTxt.setText("Data not properly formatted, use proper amount");
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
