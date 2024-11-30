
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class View extends JFrame implements Observer {

    //singleton class for controller
    public static final Controller controller = Controller.instanceOf();
    private JMenuBar mbar;
    private CardLayout cards;

    public View() {
    	View.controller.loadData();
    	
        View.controller.addObserver(this);
        View.controller.loadData();
        //Set frame styles
        this.setTitle("Personal Finance Tracker");
        Color color = new Color(244,243,239); 
        this.setBackground(color);
        this.setSize(900, 750);

        //create Menu bar
        mbar = new JMenuBar();

        JMenu menu = new JMenu("My Profile");

        JMenuItem dashboardItem = new JMenuItem("Dashboard");
        JMenuItem expenseItem = new JMenuItem("Expenses");
        JMenuItem budgetItem = new JMenuItem("Budget");
        JMenuItem financeItem = new JMenuItem("Finance Report");
        JMenuItem logout = new JMenuItem("Logout");

        menu.add(dashboardItem);
        menu.add(expenseItem);
        menu.add(budgetItem);
        menu.add(financeItem);
        menu.add(logout);

        menu.setBorder(BorderFactory.createEmptyBorder(15, 5, 5, 15));

        mbar.add(menu);

        //add action listeners and commands
        dashboardItem.setActionCommand("Dashboard");
        dashboardItem.addActionListener(new MenuBarListener());
        expenseItem.setActionCommand("Expenses");
        expenseItem.addActionListener(new MenuBarListener());
        budgetItem.setActionCommand("Budget");
        budgetItem.addActionListener(new MenuBarListener());
        financeItem.setActionCommand("Finance");
        financeItem.addActionListener(new MenuBarListener());
        logout.setActionCommand("Logout");
        logout.addActionListener(new MenuBarListener());

        //create cardlayout to be main layout for Frame
        this.cards = new CardLayout();
        this.setLayout(cards);

        //create login card
        Login loginPane = new Login();
        this.add(loginPane, "Login");
        cards.show(this.getContentPane(), "Login");

        //create dahsboard card
        Dashboard dashPanel = new Dashboard();
        this.add(dashPanel, "Dashboard");

        //create ExpenseView card
        ExpenseView expenseView = new ExpenseView();
        this.add(expenseView, "Expenses");

        //create Budget View
        BudgetView budgetView = new BudgetView();
        this.add(budgetView, "Budget");

        //create Finance View
        FinanceView financeView = new FinanceView();
        this.add(financeView, "Finance");

        //adding a window listener for closing the app
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                View.controller.saveData();
                System.exit(0);
            }
        });
    }

    //method switches the card view
    private void changeCards(String card) {
        if (!card.equals("Logout")) {
            cards.show(this.getContentPane(), card);
        } else {
            //remove menu bar and return to login panel
            this.setJMenuBar(null);
            cards.show(this.getContentPane(), "Login");
            View.controller.logout();
        }
    }

    @Override
    public void budgetChange() {

    }

    @Override
    public void loginChange() {
        this.setJMenuBar(mbar);
        //show dashboard panel
        cards.show(this.getContentPane(), "Dashboard");
    }

    private class MenuBarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            changeCards(command);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                View view = new View();
                view.setVisible(true);
            }
        });

    }

}
