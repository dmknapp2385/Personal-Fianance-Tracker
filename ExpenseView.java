
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ExpenseView extends JPanel implements Observer {
    private JTextField fromField;
    private JTextField toField;


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
        JLabel from = new JLabel("From: ");
        this.fromField = new JTextField("YYYY-MM-DD");
        JLabel to = new JLabel("To: ");
        this.toField = new JTextField("YYYY-MM-DD");
        buttonPanel.add(from);
        buttonPanel.add(to);
        JButton searchBtn = new JButton("Search");
        JButton addBtn = new JButton("+");
        searchBtn.setPreferredSize(new Dimension(100,25));
        addBtn.setPreferredSize(new Dimension(35,25));

        searchBtn.setActionCommand("search");
        addBtn.setActionCommand("add");
        buttonPanel.add(searchBtn);
        buttonPanel.add(addBtn);
        searchBtn.addActionListener(new ButtonActionListener());
        addBtn.addActionListener(new ButtonActionListener());




    }

    private class ButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
           String command = e.getActionCommand();
           if(command.equals("search")){

           }else{
            
           }
        }
    }

    @Override
    public void budgetChange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    public void loginChange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
