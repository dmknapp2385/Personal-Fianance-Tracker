
import javax.swing.JPanel;

public class Dashboard extends JPanel implements Observer{

    public Dashboard(){
        View.controller.addObserver(this);

        
    }

    private void setup(){
        //set up the page here and call this function from
    }

    @Override
    public void budgetChange() {
        //only change alert box here with budget change
        //update expense labels
        // need method in the user to get last 10 expesense
        
    }
    
    @Override
    public void loginChange() {
        setup();
    }
}