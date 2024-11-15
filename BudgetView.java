
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BudgetView extends JPanel implements Observer{
    public BudgetView(){
        JLabel test = new JLabel("This is the budget page");
        this.add(test);
    }

    @Override
    public void budgetChange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}