
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BudgetView extends JPanel implements Observer{
    public BudgetView(){
        View.controller.addObserver(this);

        JLabel test = new JLabel("This is the budget page");
        this.add(test);
    }

    @Override
    public void budgetChange() {
        
    }

    @Override
    public void loginChange() {
        
    }
}