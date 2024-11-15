
import javax.swing.JLabel;
import javax.swing.JPanel;

public class  FianceView extends JPanel implements Observer{

    public FianceView(){
        JLabel test = new JLabel("This is the finance page");
        this.add(test);
    }

    @Override
    public void budgetChange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}