
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ExpenseView extends JPanel implements Observer {

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
        JLabel to = new JLabel("To: ");
        buttonPanel.add(from);
        buttonPanel.add(to);




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
