import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;


public class BudgetView extends JPanel implements Observer {
	
    private static final long serialVersionUID = 1L;
	private JLabel foodBudget;
    private JLabel transportBudget;
    private JLabel entertainmentBudget;
    private JLabel utilitiesBudget;
    private JLabel miscBudget;
    private JLabel budgetProgressText;
    private JProgressBar foodBar = new JProgressBar(0, 100);
    private JProgressBar transportBar = new JProgressBar(0, 100);
    private JProgressBar entertainmentBar = new JProgressBar(0, 100);
    private JProgressBar utilitiesBar = new JProgressBar(0, 100);
    private JProgressBar miscBar = new JProgressBar(0, 100);
    
	private final String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private LocalDate date;
	private int selectedMonth;
	private String selectedMonthText;
	private int selectedYear;
	private LocalDate lowDate;
	private LocalDate highDate;

    public BudgetView() {
        View.controller.addObserver(this);
        this.setUp();
    }

    private void setUp() {
        this.setLayout(new BorderLayout());
        this.setSize(600, 550);
        this.setLayout(new BorderLayout());
        Color color = new Color(244, 243, 239);
        this.setBackground(color);

        //create header text label
        JLabel title = new JLabel("Budget Management");
        title.setFont(new Font("Calibri", Font.BOLD, 20));
        this.add(title, BorderLayout.NORTH);

        JPanel centerP = new JPanel();
        centerP.setBackground(color);
        this.add(centerP, BorderLayout.CENTER);
        GroupLayout layout = new GroupLayout(centerP);
        centerP.setLayout(layout);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        //create dimension for text fields and buttons
        Dimension buttonDimension = new Dimension(125, 25);

        JLabel currBudgetText = new JLabel("Current Budgets");
        currBudgetText.setFont(new Font("Calibri", Font.BOLD, 16));
        centerP.add(currBudgetText);

        this.budgetProgressText = new JLabel();
        budgetProgressText.setFont(new Font("Calibri", Font.BOLD, 16));
        centerP.add(budgetProgressText);
        UIManager.put("ProgressBar.selectionForeground", Color.BLACK); 
        UIManager.put("ProgressBar.selectionBackground", Color.BLACK); 

        //food
        JLabel foodBudgetTxt = new JLabel("Food: ");
        this.foodBudget = new JLabel();

        this.foodBudget.setText("No budget set!");
        centerP.add(foodBudgetTxt);
        centerP.add(foodBudget);

        JButton foodButton = new JButton("   Edit   ");
        foodButton.setPreferredSize(buttonDimension);
        foodButton.setBorder(new BevelBorder(BevelBorder.RAISED));
        foodButton.setActionCommand("FoodEdit");
        centerP.add(foodButton);
        foodButton.addActionListener(new ButtonActionListener());

        JLabel foodBarTxt = new JLabel("Food Budget: ");
        centerP.add(foodBarTxt);

        this.foodBar.setValue(0);
        this.foodBar.setStringPainted(true); 
        foodBar.setUI(new javax.swing.plaf.basic.BasicProgressBarUI());
        centerP.add(this.foodBar);

        //transport
        JLabel transportBudgetTxt = new JLabel("Transportation: ");
        this.transportBudget = new JLabel();

        this.transportBudget.setText("No budget set!");
        centerP.add(transportBudgetTxt);
        centerP.add(transportBudget);

        JButton transportButton = new JButton("   Edit   ");
        transportButton.setPreferredSize(buttonDimension);
        transportButton.setBorder(new BevelBorder(BevelBorder.RAISED));
        transportButton.setActionCommand("TransportEdit");
        centerP.add(transportButton);
        transportButton.addActionListener(new ButtonActionListener());

        JLabel transportBarTxt = new JLabel("Transportation Budget: ");
        centerP.add(transportBarTxt);

        this.transportBar.setValue(0);
        this.transportBar.setStringPainted(true);
        transportBar.setUI(new javax.swing.plaf.basic.BasicProgressBarUI());
        centerP.add(this.transportBar);

        //entertainment
        JLabel entertainmentBudgetTxt = new JLabel("Entertainment: ");
        this.entertainmentBudget = new JLabel();

        this.entertainmentBudget.setText("No budget set!");
        centerP.add(entertainmentBudgetTxt);
        centerP.add(entertainmentBudget);

        JButton entertainmentButton = new JButton("   Edit   ");
        entertainmentButton.setPreferredSize(buttonDimension);
        entertainmentButton.setBorder(new BevelBorder(BevelBorder.RAISED));
        entertainmentButton.setActionCommand("EntertainmentEdit");
        centerP.add(entertainmentButton);
        entertainmentButton.addActionListener(new ButtonActionListener());

        JLabel entertainmentBarTxt = new JLabel("Entertainment Budget: ");
        centerP.add(entertainmentBarTxt);

        this.entertainmentBar.setValue(0);
        this.entertainmentBar.setStringPainted(true);
        entertainmentBar.setUI(new javax.swing.plaf.basic.BasicProgressBarUI());
        centerP.add(this.entertainmentBar);

        //utilities
        JLabel utilitiesBudgetTxt = new JLabel("Utilities: ");
        this.utilitiesBudget = new JLabel();

        this.utilitiesBudget.setText("No budget set!");
        centerP.add(utilitiesBudgetTxt);
        centerP.add(utilitiesBudget);

        JButton utilitiesButton = new JButton("   Edit   ");
        utilitiesButton.setPreferredSize(buttonDimension);
        utilitiesButton.setBorder(new BevelBorder(BevelBorder.RAISED));
        utilitiesButton.setActionCommand("UtilitiesEdit");
        centerP.add(utilitiesButton);
        utilitiesButton.addActionListener(new ButtonActionListener());

        JLabel utilitiesBarTxt = new JLabel("Utilities Budget: ");
        centerP.add(utilitiesBarTxt);

        this.utilitiesBar.setValue(0);
        this.utilitiesBar.setStringPainted(true);
        utilitiesBar.setUI(new javax.swing.plaf.basic.BasicProgressBarUI());
        centerP.add(this.utilitiesBar);

        //misc
        JLabel miscBudgetTxt = new JLabel("Miscellaneous: ");
        this.miscBudget = new JLabel();

        this.miscBudget.setText("No budget set!");
        centerP.add(miscBudgetTxt);
        centerP.add(miscBudget);

        JButton miscButton = new JButton("   Edit   ");
        miscButton.setPreferredSize(buttonDimension);
        miscButton.setBorder(new BevelBorder(BevelBorder.RAISED));
        miscButton.setActionCommand("MiscEdit");
        centerP.add(miscButton);
        miscButton.addActionListener(new ButtonActionListener());

        JLabel miscBarTxt = new JLabel("Miscellaneous Budget: ");
        centerP.add(miscBarTxt);

        this.miscBar.setValue(0);
        this.miscBar.setStringPainted(true);
        miscBar.setUI(new javax.swing.plaf.basic.BasicProgressBarUI());
        centerP.add(this.miscBar);

        //layout components
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(title)
                                .addComponent(currBudgetText)
                                .addComponent(foodBudgetTxt)
                                .addComponent(transportBudgetTxt)
                                .addComponent(entertainmentBudgetTxt)
                                .addComponent(utilitiesBudgetTxt)
                                .addComponent(miscBudgetTxt)
                                .addComponent(budgetProgressText)
                                .addComponent(foodBarTxt)
                                .addComponent(transportBarTxt)
                                .addComponent(entertainmentBarTxt)
                                .addComponent(utilitiesBarTxt)
                                .addComponent(miscBarTxt)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(foodBudget)
                                .addComponent(transportBudget)
                                .addComponent(entertainmentBudget)
                                .addComponent(utilitiesBudget)
                                .addComponent(miscBudget)
                                .addComponent(foodBar)
                                .addComponent(transportBar)
                                .addComponent(entertainmentBar)
                                .addComponent(utilitiesBar)
                                .addComponent(miscBar)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(foodButton)
                                .addComponent(transportButton)
                                .addComponent(entertainmentButton)
                                .addComponent(utilitiesButton)
                                .addComponent(miscButton)
                        )
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(title)
                        )
                        .addGap(20)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(currBudgetText)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(foodBudgetTxt)
                                .addComponent(foodBudget)
                                .addComponent(foodButton)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(transportBudgetTxt)
                                .addComponent(transportBudget)
                                .addComponent(transportButton)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(entertainmentBudgetTxt)
                                .addComponent(entertainmentBudget)
                                .addComponent(entertainmentButton)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(utilitiesBudgetTxt)
                                .addComponent(utilitiesBudget)
                                .addComponent(utilitiesButton)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(miscBudgetTxt)
                                .addComponent(miscBudget)
                                .addComponent(miscButton)
                        )
                        .addGap(70)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(budgetProgressText)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(foodBarTxt)
                                .addComponent(foodBar)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(transportBarTxt)
                                .addComponent(transportBar)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(entertainmentBarTxt)
                                .addComponent(entertainmentBar)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(utilitiesBarTxt)
                                .addComponent(utilitiesBar)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(miscBarTxt)
                                .addComponent(miscBar)
                        )
        );

    }
 
    private void updateBudget() {
        SwingUtilities.invokeLater(() -> {
            Optional<Double> food = View.controller.getBudgetByCategory(Category.FOOD);
            Optional<Double> transport = View.controller.getBudgetByCategory(Category.TRANSPORTATION);
            Optional<Double> entertainment = View.controller.getBudgetByCategory(Category.ENTERTAINMENT);
            Optional<Double> utilities = View.controller.getBudgetByCategory(Category.UTILITIES);
            Optional<Double> misc = View.controller.getBudgetByCategory(Category.MISCELLANEOUS);
            updateBudgetHelper(food, foodBudget);
            updateBudgetHelper(transport, transportBudget);
            updateBudgetHelper(entertainment, entertainmentBudget);
            updateBudgetHelper(utilities, utilitiesBudget);
            updateBudgetHelper(misc, miscBudget);
        });
    }
    
    private void updateBudgetHelper(Optional<Double> val, JLabel label) {
    	if (val.isEmpty()) {
    		label.setText("No budget set!");
    	}
    	else if (val.get() == 0) {
    		label.setText("No budget set!");
    	}
    	else {
    		label.setText(String.format("$%.2f", val.get()));
    	}
    }
    
    
    private void setDates() {
    	this.date = LocalDate.now();
    	this.selectedMonth = this.date.getMonthValue();
    	this.selectedMonthText = this.monthNames[this.selectedMonth - 1];
    	this.selectedYear = this.date.getYear();
    	this.lowDate = setLowDate(this.selectedMonth, this.selectedYear);
    	this.highDate = setHighDate(this.selectedMonth, this.selectedYear);
    	this.budgetProgressText.setText(selectedMonthText + " Budget Progress");
    	
    }
    
    /**
     * description:
     * 	updates the lowDate to the first day of the selected month
     */
    private LocalDate setLowDate(int month, int year) {
    	return LocalDate.of(year, month, 1);
    }
    
    /**
     * description:
     * 	updates the highDate to the last day of the selected month
     */
    private LocalDate setHighDate(int month, int year) {
    	return YearMonth.of(year, month).atEndOfMonth();
    }

    private void updateProgressBars() {
        SwingUtilities.invokeLater(() -> {
            Optional<Double> food = View.controller.getExpensesByCategoryPercentByDate(Category.FOOD, this.lowDate, this.highDate);
            Optional<Double> transport = View.controller.getExpensesByCategoryPercentByDate(Category.TRANSPORTATION, this.lowDate, this.highDate);
            Optional<Double> entertainment = View.controller.getExpensesByCategoryPercentByDate(Category.ENTERTAINMENT, this.lowDate, this.highDate);
            Optional<Double> utilities = View.controller.getExpensesByCategoryPercentByDate(Category.UTILITIES, this.lowDate, this.highDate);
            Optional<Double> misc = View.controller.getExpensesByCategoryPercentByDate(Category.MISCELLANEOUS, this.lowDate, this.highDate);
            updateProgressHelper(food, foodBar);
            updateProgressHelper(transport, transportBar);
            updateProgressHelper(entertainment, entertainmentBar);
            updateProgressHelper(utilities, utilitiesBar);
            updateProgressHelper(misc, miscBar);
        });

    }

    private void updateProgressHelper(Optional<Double> val, JProgressBar bar) {
        SwingUtilities.invokeLater(() -> {
            if (val.isEmpty()) {
                bar.setValue(0);
                bar.setString("N/A");
                return;
            }
            int intValue = (int) Math.round(val.get());
            bar.setValue(intValue);
            if (intValue >= 80) {
                bar.setForeground(Color.RED);
                bar.setString("WARNING: " + intValue + "% Used");
            } else {
                bar.setForeground(Color.GREEN);
                bar.setString(intValue + "% Used");
            }
        });

    }
    


    private class ButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            Category cat = null;
            JFrame popup;
            switch (command) {
                case "FoodEdit":
                    cat = Category.FOOD;
                    break;
                case "TransportEdit":
                    cat = Category.TRANSPORTATION;
                    break;
                case "EntertainmentEdit":
                    cat = Category.ENTERTAINMENT;
                    break;
                case "UtilitiesEdit":
                    cat = Category.UTILITIES;
                    break;
                case "MiscEdit":
                    cat = Category.MISCELLANEOUS;
                    break;
                default:
                    break;

            }
            popup = new AddBudgetFrame(cat);
            popup.setVisible(true);
        }

    }

    @Override
    public void loginChange() {
    	this.setDates();
        this.updateBudget();
        this.updateProgressBars();

    }

    @Override
    public void budgetChange() {
    	this.setDates();
        this.updateBudget();
        this.updateProgressBars();
    }

    @Override
    public void expenseChange() {
    	this.setDates();
        this.updateProgressBars();
    }

}
