
import javax.swing.JPanel;

public class Dashboard extends JPanel implements Observer{

    public Dashboard(){
        View.controller.addObserver(this);

        
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