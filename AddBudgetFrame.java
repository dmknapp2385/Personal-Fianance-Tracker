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

@SuppressWarnings("serial")
public class AddBudgetFrame extends JFrame{
	
	private JTextField amountField = new JTextField("");
    private JPanel mainPanel = new JPanel();
    private JLabel errorTxt = new JLabel("");
    private JButton btn;
    private Category cat;

    public AddBudgetFrame(Category cat) {
    	this.cat = cat;
        this.setTitle("Personal Finance Tracker : Add Budget");
        setUp();
        this.btn.setText("Add");
        this.btn.setActionCommand("add");
    }

    
    private void setUp() {
    	 //Color color = new Color(217, 214, 176);
         //this.setBackground(color);

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
                         View.controller.addBudget(cat, amountD);
                      

                     }
                     closeFrame();

                 } catch (Exception exception) {
                     errorTxt.setText("Data not properly formatted, use proper amount");
                 }
             }
         });

         mainPanel.add(errorTxt);
    }
    
    
    private void closeFrame() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

}
