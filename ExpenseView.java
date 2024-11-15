
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ExpenseView extends JPanel implements Observer {

    public ExpenseView() {
        JLabel test = new JLabel("This is the expense page");
        this.add(test);
    }

    @Override
    public void budgetChange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
