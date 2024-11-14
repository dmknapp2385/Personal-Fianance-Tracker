
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class View extends JFrame implements Observer {

    //singleton class for controller
    public static final Controller controller = Controller.instanceOf();
    private ArrayList<String> panes = new ArrayList<>();
    private JMenuBar mbar;

    public View() {
        //Set frame styles
        this.setTitle("Personal Finance Tracker");
        Color color = new Color(217, 214, 176);
        this.setBackground(color);
        this.setSize(800, 700);

        //create Menu bar
        mbar = new JMenuBar();

        JMenu menu = new JMenu("My Profile");

        JMenuItem expenseItem = new JMenuItem("Expenses");
        JMenuItem budgetItem = new JMenuItem("Budget");
        JMenuItem financeItem = new JMenuItem("Finance Report");
        JMenuItem logout = new JMenuItem("Logout");

        menu.add(expenseItem);
        menu.add(budgetItem);
        menu.add(financeItem);
        menu.add(logout);

        menu.setBorder(BorderFactory.createEmptyBorder(15, 5, 5, 15));

        mbar.add(menu);

        //create cardlayout to be main layout for Frame
        CardLayout cards = new CardLayout();
        this.setLayout(cards);

        //create login card
        Login loginPane = new Login();
        this.add(loginPane, "Login");
        panes.add("Login");
        cards.show(this.getContentPane(), "Login");

        //adding a window listener for closing the app
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        View view = new View();
        view.setVisible(true);

    }

    @Override
    public void budgetChange() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'budgetChange'");
    }

    //Method sets menu bar when called
    public void setJMenuBar() {
        this.setJMenuBar(mbar);
    }
}
