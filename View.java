
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class View extends JFrame {

    //singleton class for controller
    public static final Controller controller = Controller.instanceOf();
    private ArrayList<JPanel> panels = new ArrayList<>();

    public View() {

        //create login panel
        Login loginPanel = new Login();
        panels.add(loginPanel);
        this.add(loginPanel);

    }

    public static void main(String[] args) {
        View view = new View();
        view.setVisible(true);
 
   }
}