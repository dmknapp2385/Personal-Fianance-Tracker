
import javax.swing.JPanel;

public class Dashboard extends JPanel implements Observer{

    public Dashboard(){
        View.controller.addObserver(this);

        
    }

    @Override
    public void budgetChange() {
        
    }
    
    @Override
    public void loginChange() {
    }
}