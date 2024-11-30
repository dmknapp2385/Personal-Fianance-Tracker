
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics; 				// pie chart exp
import java.awt.Graphics2D;				// pie chart exp
import java.awt.RenderingHints;			// pie chart exp
import java.awt.event.ActionEvent; 		// pie chart exp
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Optional;

import javax.swing.GroupLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class FinanceView extends JPanel implements Observer{ 
	private final String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	
	private LocalDate date;
	private int selectedMonth;
	private String selectedMonthText;
	private int selectedYear;
	private LocalDate lowDate;
	private LocalDate highDate;
	
	private JLabel subtitle;
	
	private JComboBox<String> monthDropdown;
	private JComboBox<Integer> yearDropdown;

    private JTextField month;
    private JTextField year;
    private JLabel error;
    private JLabel totalAmt;
    
    private JLabel foodExpenses;
    private JLabel foodBudget;
    private JLabel foodPercent;
    
    private JLabel transportationExpenses;
    private JLabel transportationBudget;
    private JLabel transportationPercent;
    
    private JLabel entertainmentExpenses;
    private JLabel entertainmentBudget;
    private JLabel entertainmentPercent;
    
    private JLabel utilitiesExpenses;
    private JLabel utilitiesBudget;
    private JLabel utilitiesPercent;
    
    private JLabel miscellaneousExpenses;
    private JLabel miscellaneousBudget;
    private JLabel miscellaneousPercent;
    
    private ArrayList<JLabel> displayLabels = new ArrayList<>();
    
    private JLabel foodCategoryExpenses;
    private JLabel transportationCategoryExpenses;
    private JLabel entertainmentCategoryExpenses;
    private JLabel utilitiesCategoryExpenses;
    private JLabel miscellaneousCategoryExpenses;
       
    public FinanceView() {
    	View.controller.addObserver(this);
    	this.date = LocalDate.now();
    	this.selectedMonth = this.date.getMonthValue();
    	this.selectedMonthText = this.monthNames[this.selectedMonth - 1];
    	this.selectedYear = this.date.getYear();
    	this.lowDate = setLowDate(this.selectedMonth, this.selectedYear);
    	this.highDate = setHighDate(this.selectedMonth, this.selectedYear);
    }
    	    
	public void setup() {
        //set layout for panel
        this.setLayout(new BorderLayout());
        this.setSize(600, 550);
        this.setLayout(new BorderLayout());
        Color color = new Color(244, 243, 239);
        this.setBackground(color);
        
        //create dimension for textfields and buttons
//        Dimension fieldDimension = new Dimension(100, 28);
        Dimension btDimension = new Dimension(125, 25);

        // Header Panel
        JPanel header = new JPanel();
        this.add(header, BorderLayout.NORTH);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(new EmptyBorder(15, 5, 8, 15)); 
        header.setBackground(color);
        
        
        //create header text label
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titlePanel.setBackground(color);
        header.add(titlePanel);
        
        JLabel title = new JLabel("Finance Report");
        title.setFont(new Font("Calibri", Font.BOLD, 20));
        titlePanel.add(title);
        
        this.subtitle = new JLabel();
        this.subtitle.setText(this.selectedMonthText + " " + this.selectedYear);
        this.subtitle.setFont(new Font("Calibri", Font.PLAIN, 16));
        titlePanel.add(this.subtitle);
        
        header.add(Box.createVerticalStrut(10));
        
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.X_AXIS));
        selectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        selectionPanel.setBackground(color);
        header.add(selectionPanel);
        
        //create header text label
        JLabel monthSelectorTxt = new JLabel("Month: ");
        selectionPanel.add(monthSelectorTxt);
        
        this.monthDropdown = new JComboBox<>(this.monthNames);
        this.monthDropdown.setSelectedIndex(this.selectedMonth - 1);
        selectionPanel.add(monthDropdown);
        monthDropdown.setActionCommand("Select");
        monthDropdown.addActionListener(new ButtonActionListener());
        
        JLabel yearSelectorTxt = new JLabel("         Year: ");
        selectionPanel.add(yearSelectorTxt);
        
        ArrayList<Integer> yearList = new ArrayList<>();
        for (int year = this.selectedYear; year >= 2000; year--) {
        	yearList.add(year);
        }
        Integer [] yearArray = yearList.toArray(new Integer[yearList.size()]);
        this.yearDropdown = new JComboBox<>(yearArray);
        selectionPanel.add(yearDropdown);
        yearDropdown.setActionCommand("Select");
        yearDropdown.addActionListener(new ButtonActionListener());

        header.add(Box.createVerticalStrut(10));
        
        JPanel totalExpensePanel = new JPanel();
        totalExpensePanel.setLayout(new BoxLayout(totalExpensePanel, BoxLayout.X_AXIS));
        totalExpensePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        totalExpensePanel.setBackground(color);
        header.add(totalExpensePanel);
        
        // create total expenses
        JLabel totalTxt = new JLabel("Total Expenses: ");
        totalTxt.setFont(new Font("Calibri", Font.BOLD, 16));
        totalExpensePanel.add(totalTxt);
        
        this.totalAmt = new JLabel();
        this.totalAmt.setBorder(new EmptyBorder(15, 0, 15, 15));
        this.totalAmt.setFont(new Font("Calibri", Font.BOLD, 16));
        this.totalAmt.setText("N/A");	// TODO: get value and add it in eventListener
        totalExpensePanel.add(totalAmt);
        

        //Create new panel and add to center
        JPanel centerP = new JPanel();
        centerP = new JPanel();
        centerP.setBackground(color);
        this.add(centerP, BorderLayout.CENTER);
        GroupLayout layout = new GroupLayout(centerP);
        centerP.setLayout(layout);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

             
        // create food expense information
        JLabel foodTxt = new JLabel("Food");
        foodTxt.setFont(new Font("Calibri", Font.BOLD, 12));
        centerP.add(foodTxt);
        
        JLabel foodExpensesTxt = new JLabel("Expenses: ");
        centerP.add(foodExpensesTxt);
        
        this.foodExpenses = new JLabel();
        this.foodExpenses.setText("$0.00");	// TODO: get value and add it in eventListener
        centerP.add(this.foodExpenses);
        this.displayLabels.add(this.foodExpenses);
        
        JLabel foodBudgetTxt = new JLabel("Budget: ");
        centerP.add(foodBudgetTxt);
        
        this.foodBudget = new JLabel();
        this.foodBudget.setText("$0.00");	// TODO: get value and add it in eventListener
        centerP.add(this.foodBudget);
        this.displayLabels.add(this.foodBudget);
        
        JLabel foodPercentTxt = new JLabel("% of Budget: ");
        centerP.add(foodPercentTxt);
        
        this.foodPercent = new JLabel();
        this.foodPercent.setText("0%");	// TODO: get value and add it in eventListener
        centerP.add(foodPercent);
        this.displayLabels.add(this.foodPercent);
        
        // create transportation expense information
        JLabel transportationTxt = new JLabel("Transportation");
        transportationTxt.setBorder(new EmptyBorder(15, 0, 0, 0));
        transportationTxt.setFont(new Font("Calibri", Font.BOLD, 12));
        
        JLabel transportationExpensesTxt = new JLabel("Expenses: ");
        centerP.add(transportationExpensesTxt);
        
        this.transportationExpenses = new JLabel();
        this.transportationExpenses.setText("$0.00");	// TODO: get value and add it in eventListener
        centerP.add(this.transportationExpenses);
        this.displayLabels.add(this.transportationExpenses);
        
        JLabel transportationBudgetTxt = new JLabel("Budget: ");
        centerP.add(transportationBudgetTxt);
        
        this.transportationBudget = new JLabel();
        this.transportationBudget.setText("$0.00");	// TODO: get value and add it in eventListener
        centerP.add(this.transportationBudget);
        this.displayLabels.add(this.transportationBudget);
        
        JLabel transportationPercentTxt = new JLabel("% of Budget: ");
        centerP.add(transportationPercentTxt);
        
        this.transportationPercent = new JLabel();
        this.transportationPercent.setText("0%");	// TODO: get value and add it in eventListener
        centerP.add(this.transportationPercent);
        this.displayLabels.add(this.transportationPercent);

        // create entertainment expense information
        JLabel entertainmentTxt = new JLabel("Entertainment      ");
        entertainmentTxt.setBorder(new EmptyBorder(15, 0, 0, 0));
        entertainmentTxt.setFont(new Font("Calibri", Font.BOLD, 12));
        
        JLabel entertainmentExpensesTxt = new JLabel("Expenses: ");
        centerP.add(entertainmentExpensesTxt);
        
        this.entertainmentExpenses = new JLabel();
        this.entertainmentExpenses.setText("$0.00");	// TODO: get value and add it in eventListener
        centerP.add(this.entertainmentExpenses);
        this.displayLabels.add(this.entertainmentExpenses);
        
        JLabel entertainmentBudgetTxt = new JLabel("Budget: ");
        centerP.add(entertainmentBudgetTxt);
        
        this.entertainmentBudget = new JLabel();
        this.entertainmentBudget.setText("$0.00");	// TODO: get value and add it in eventListener
        centerP.add(this.entertainmentBudget);
        this.displayLabels.add(this.entertainmentBudget);
        
        JLabel entertainmentPercentTxt = new JLabel("% of Budget: ");
        centerP.add(entertainmentPercentTxt);
        
        this.entertainmentPercent = new JLabel();
        this.entertainmentPercent.setText("0%");	// TODO: get value and add it in eventListener
        centerP.add(this.entertainmentPercent);
        this.displayLabels.add(this.entertainmentPercent);
        
        // create utilities expense information
        JLabel utilitiesTxt = new JLabel("Utilities");
        utilitiesTxt.setBorder(new EmptyBorder(15, 0, 0, 0));
        utilitiesTxt.setFont(new Font("Calibri", Font.BOLD, 12));
        
        JLabel utilitiesExpensesTxt = new JLabel("Expenses: ");
        centerP.add(utilitiesExpensesTxt);
        
        this.utilitiesExpenses = new JLabel();
        this.utilitiesExpenses.setText("$0.00");	// TODO: get value and add it in eventListener
        centerP.add(this.utilitiesExpenses);
        this.displayLabels.add(this.utilitiesExpenses);
        
        JLabel utilitiesBudgetTxt = new JLabel("Budget: ");
        centerP.add(utilitiesBudgetTxt);
        
        this.utilitiesBudget = new JLabel();
        this.utilitiesBudget.setText("$0.00");	// TODO: get value and add it in eventListener
        centerP.add(this.utilitiesBudget);
        this.displayLabels.add(this.utilitiesBudget);
        
        JLabel utilitiesPercentTxt = new JLabel("% of Budget: ");
        centerP.add(utilitiesPercentTxt);
        
        this.utilitiesPercent = new JLabel();
        this.utilitiesPercent.setText("0%");	// TODO: get value and add it in eventListener
        centerP.add(this.utilitiesPercent);
        this.displayLabels.add(this.utilitiesPercent);
        
        // create miscellaneous expense information
        JLabel miscellaneousTxt = new JLabel("Miscellaneous");
        miscellaneousTxt.setBorder(new EmptyBorder(15, 0, 0, 0));
        miscellaneousTxt.setFont(new Font("Calibri", Font.BOLD, 12));
        
        JLabel miscellaneousExpensesTxt = new JLabel("Expenses: ");
        centerP.add(miscellaneousExpensesTxt);
        
        this.miscellaneousExpenses = new JLabel();
        this.miscellaneousExpenses.setText("$0.00");	// TODO: get value and add it in eventListener
        centerP.add(this.miscellaneousExpenses);
        this.displayLabels.add(this.miscellaneousExpenses);
        
        JLabel miscellaneousBudgetTxt = new JLabel("Budget: ");
        centerP.add(miscellaneousBudgetTxt);
        
        this.miscellaneousBudget = new JLabel();
        this.miscellaneousBudget.setText("$0.00");	// TODO: get value and add it in eventListener
        centerP.add(this.miscellaneousBudget);
        this.displayLabels.add(this.miscellaneousBudget);
        
        JLabel miscellaneousPercentTxt = new JLabel("% of Budget: ");
        centerP.add(miscellaneousPercentTxt);
        
        this.miscellaneousPercent = new JLabel();
        miscellaneousPercent.setText("0%");	// TODO: get value and add it in eventListener
        centerP.add(this.miscellaneousPercent);
        this.displayLabels.add(this.miscellaneousPercent);

        
        //layout components
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(foodTxt, 125, 125, 125)
                                .addComponent(foodExpensesTxt)
                                .addComponent(foodBudgetTxt)
                                .addComponent(foodPercentTxt)
		                        .addComponent(transportationTxt)
		                        .addComponent(transportationExpensesTxt)
		                        .addComponent(transportationBudgetTxt)
		                        .addComponent(transportationPercentTxt))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(foodExpenses, 125, 125, 125)
                                .addComponent(foodBudget)
                                .addComponent(foodPercent)
                                .addComponent(transportationExpenses)
                                .addComponent(transportationBudget)
                                .addComponent(transportationPercent))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
		                        .addComponent(entertainmentTxt, 125, 125, 125)
		                        .addComponent(entertainmentExpensesTxt)
		                        .addComponent(entertainmentBudgetTxt)
		                        .addComponent(entertainmentPercentTxt)
		                        .addComponent(utilitiesTxt)
		                        .addComponent(utilitiesExpensesTxt)
		                        .addComponent(utilitiesBudgetTxt)
		                        .addComponent(utilitiesPercentTxt))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)                  		
                        		.addComponent(entertainmentExpenses, 125, 125, 125)
                                .addComponent(entertainmentBudget)
                                .addComponent(entertainmentPercent)
                                .addComponent(utilitiesExpenses)
                                .addComponent(utilitiesBudget)
                                .addComponent(utilitiesPercent))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(miscellaneousTxt, 125, 125, 125)
                                .addComponent(miscellaneousExpensesTxt)
		                        .addComponent(miscellaneousBudgetTxt)
		                        .addComponent(miscellaneousPercentTxt))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(miscellaneousExpenses, 125, 125, 125)
                                .addComponent(miscellaneousBudget)
                                .addComponent(miscellaneousPercent))
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        		.addComponent(foodTxt)
                        		.addComponent(entertainmentTxt)
                        		.addComponent(miscellaneousTxt)) 
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(foodExpensesTxt)
                                .addComponent(foodExpenses)
                                .addComponent(entertainmentExpensesTxt)
                                .addComponent(entertainmentExpenses)
                                .addComponent(miscellaneousExpensesTxt)
                                .addComponent(miscellaneousExpenses))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(foodBudgetTxt)
                                .addComponent(foodBudget)
		                        .addComponent(entertainmentBudgetTxt)
		                        .addComponent(entertainmentBudget)
		                        .addComponent(miscellaneousBudgetTxt)
		                        .addComponent(miscellaneousBudget))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(foodPercentTxt)
                                .addComponent(foodPercent)
                                .addComponent(entertainmentPercentTxt)
                                .addComponent(entertainmentPercent)
                                .addComponent(miscellaneousPercentTxt)
                                .addComponent(miscellaneousPercent))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        		.addComponent(transportationTxt)
                        		.addComponent(utilitiesTxt))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(transportationExpensesTxt)
                                .addComponent(transportationExpenses)
                                .addComponent(utilitiesExpensesTxt)
                                .addComponent(utilitiesExpenses))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(transportationBudgetTxt)
                                .addComponent(transportationBudget)
                                .addComponent(utilitiesBudgetTxt)
                                .addComponent(utilitiesBudget))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(transportationPercentTxt)
                                .addComponent(transportationPercent)
		                        .addComponent(utilitiesPercentTxt)
		                        .addComponent(utilitiesPercent))
        );
        
        
        // Done through here
        // Create bottom panel for pie chart and related text
        JPanel bottomP = new JPanel();
        bottomP.setBackground(color);
        bottomP.setLayout(new BorderLayout());
        this.add(bottomP, BorderLayout.SOUTH);
        
        // Create text panel for pie chart
        JPanel pieTextPanel = new JPanel();
        pieTextPanel.setBackground(color);
        GroupLayout pieTextLayout = new GroupLayout(pieTextPanel);
        pieTextPanel.setLayout(pieTextLayout);
        
        pieTextLayout.setAutoCreateContainerGaps(true);
        pieTextLayout.setAutoCreateGaps(true);
        
        bottomP.add(pieTextPanel, BorderLayout.WEST);
                
        // create food expense information
        JLabel pieTxt = new JLabel("Expenses by Category");
        pieTxt.setFont(new Font("Calibri", Font.BOLD, 12));
        
        JLabel foodCategoryTxt = new JLabel("Food: ");
        this.foodCategoryExpenses = new JLabel();
        this.foodCategoryExpenses.setText("$0.00");	// TODO: get value and add it in eventListener
        pieTextPanel.add(foodCategoryTxt);
        pieTextPanel.add(foodCategoryExpenses);
        
        JLabel transportationCategoryTxt = new JLabel("Transportation: ");
        this.transportationCategoryExpenses = new JLabel();
        this.transportationCategoryExpenses.setText("$0.00");	// TODO: get value and add it in eventListener
        pieTextPanel.add(transportationCategoryTxt);
        pieTextPanel.add(transportationCategoryExpenses);
        
        JLabel entertainmentCategoryTxt = new JLabel("Entertainment: ");
        this.entertainmentCategoryExpenses = new JLabel();
        this.entertainmentCategoryExpenses.setText("$0.00");	// TODO: get value and add it in eventListener
        pieTextPanel.add(entertainmentCategoryTxt);
        pieTextPanel.add(entertainmentCategoryExpenses);
        
        JLabel utilitiesCategoryTxt = new JLabel("Utilities: ");
        this.utilitiesCategoryExpenses = new JLabel();
        this.utilitiesCategoryExpenses.setText("$0.00");	// TODO: get value and add it in eventListener
        pieTextPanel.add(utilitiesCategoryTxt);
        pieTextPanel.add(utilitiesCategoryExpenses);
        
        JLabel miscellaneousCategoryTxt = new JLabel("Miscellaneous: ");
        this.miscellaneousCategoryExpenses = new JLabel();
        this.miscellaneousCategoryExpenses.setText("$0.00");	// TODO: get value and add it in eventListener
        pieTextPanel.add(miscellaneousCategoryTxt);
        pieTextPanel.add(miscellaneousCategoryExpenses);
                
        
        // Create the pie chart panel with dummy data
        JPanel pieChartPanel = new JPanel();
        pieChartPanel.setBackground(color);
        
        double[] values = {20, 10, 25, 20, 25};
        Color[] colors = {Color.decode("#cdb4db"), Color.decode("#ffc8dd"), Color.decode("#ffafcc"), Color.decode("#bde0fe"), Color.decode("#a2d2ff")};
        PieChartPanel pieChart = new PieChartPanel(values, colors);

        pieChart.setPreferredSize(new Dimension(500, 200));
        pieChart.setBackground(color);
        pieChartPanel.add(pieChart);

        // Add the pie chart panel to the bottom panel
        bottomP.add(pieChart, BorderLayout.EAST);
        
        pieTextLayout.setHorizontalGroup(
        		pieTextLayout.createSequentialGroup()
                        .addGroup(pieTextLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        		.addComponent(pieTxt)
                        		.addComponent(foodCategoryTxt)
                        		.addComponent(transportationCategoryTxt)
                        		.addComponent(entertainmentCategoryTxt)
                        		.addComponent(utilitiesCategoryTxt)
                        		.addComponent(miscellaneousCategoryTxt)
                        		)
                        .addGroup(pieTextLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        		.addComponent(foodCategoryExpenses)
                        		.addComponent(transportationCategoryExpenses)
                        		.addComponent(entertainmentCategoryExpenses)
                        		.addComponent(utilitiesCategoryExpenses)
                        		.addComponent(miscellaneousCategoryExpenses)
                        		)
        );
        
        pieTextLayout.setVerticalGroup(
        		pieTextLayout.createSequentialGroup()
                        .addGroup(pieTextLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        		.addComponent(pieTxt)
                        		)
                        .addGroup(pieTextLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        		.addComponent(foodCategoryTxt)
                        		.addComponent(foodCategoryExpenses)
                        		)
                        .addGroup(pieTextLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        		.addComponent(transportationCategoryTxt)
                        		.addComponent(transportationCategoryExpenses)
                        		)
                        .addGroup(pieTextLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        		.addComponent(entertainmentCategoryTxt)
                        		.addComponent(entertainmentCategoryExpenses)
                        		)
                        .addGroup(pieTextLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        		.addComponent(utilitiesCategoryTxt)
                        		.addComponent(utilitiesCategoryExpenses)
                        		)
                        .addGroup(pieTextLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        		.addComponent(miscellaneousCategoryTxt)
                        		.addComponent(miscellaneousCategoryExpenses)
                        		)
        );
        
//        submitBtn.addActionListener(new ButtonActionListener());
    }

    
 
    private class ButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("Select")) {
            	selectedMonth = monthDropdown.getSelectedIndex() + 1;
                selectedYear = (int)yearDropdown.getSelectedItem();
                updateExpenses();
            } else {
            	// TODO: Do we need this if-else structure?
            }
        }
    }
    
    private class PieChartPanel extends JPanel {
        private double[] values;
        private Color[] colors;

        public PieChartPanel(double[] values, Color[] colors) {
            this.values = values;
            this.colors = colors;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int x = width / 2;
            int y = height / 2;
            int radius = Math.min(x, y) - 10;

            double total = 0;
            for (double value : values) {
                total += value;
            }

            double startAngle = 0;
            for (int i = 0; i < values.length; i++) {
                double arcAngle = (values[i] / total) * 360;
                g2d.setColor(colors[i]);
                g2d.fill(new Arc2D.Double(x - radius, y - radius, 2 * radius, 2 * radius, startAngle, arcAngle, Arc2D.PIE));
                startAngle += arcAngle;
            }
        }

        // Methods to update data dynamically
        public void setValues(double[] values) {
            this.values = values;
            repaint();
        }

        public void setColors(Color[] colors) {
            this.colors = colors;
            repaint();
        }
    }
    
    @Override
    public void budgetChange() {
    	//this.setup();
        // throw new UnsupportedOperationException("Not supported yet.");
    	// repaint the panel or just updates fields without calling setup
    	// look in showAllExpenses ExpenseView
    }

    @Override
    public void loginChange() {
    	this.setup();
    }
    
    @Override
    public void expenseChange() {
    	this.updateExpenses();
    }
    
    private void updateExpenses() {
        this.lowDate = setLowDate(this.selectedMonth, this.selectedYear);
    	this.highDate = setHighDate(this.selectedMonth, this.selectedYear);
    	this.selectedMonthText = this.monthNames[this.selectedMonth - 1];
    	this.subtitle.setText(this.selectedMonthText + " " + this.selectedYear);

        ArrayList<Expense> exps = View.controller.getByDate(this.lowDate, this.highDate);

        if (exps.isEmpty()) {
            totalAmt.setText("No records for this month.");
            totalAmt.setForeground(Color.RED);
            this.noRecords();
        } else {
            double total = 0;
            for (Expense exp : exps) {
                total += exp.getAmount();
            }
            String formattedTotal = String.format("$%.2f", total);
            totalAmt.setText(formattedTotal);
            totalAmt.setForeground(Color.BLACK);
            
            this.updateLabels();
        }
    }
    
    private void noRecords() {
    	for (JLabel label: this.displayLabels) {
    		label.setText("");
    	}
    }
    
    
    private void updateLabels() {
    	this.noRecords();
    	
    	// food
    	Double foodExpVal = View.controller.getTotalExpensesByCategoryByDate(Category.FOOD, this.lowDate, this.highDate);
        this.foodExpenses.setText(String.format("$%.2f", foodExpVal));
    	Optional<Double> foodBudgetVal = View.controller.getBudgetByCategory(Category.FOOD);
        if(!foodBudgetVal.isEmpty()) {
        	this.foodBudget.setText(String.format("$%.2f", foodBudgetVal.get()));
        	Optional<Double> foodPercentVal = View.controller.getExpensesByCategoryPercentByDate(Category.FOOD, this.lowDate, this.highDate);
        	this.foodPercent.setText(String.format("%.1f%%", foodPercentVal.get()));
        } else {
        	this.foodBudget.setText("N/A");
        	this.foodPercent.setText("N/A");
        }
    	
        
        // transportation 
    	Double transportationExpVal = View.controller.getTotalExpensesByCategoryByDate(Category.TRANSPORTATION, this.lowDate, this.highDate);
        this.transportationExpenses.setText(String.format("$%.2f", transportationExpVal));
    	Optional<Double> transportationBudgetVal = View.controller.getBudgetByCategory(Category.TRANSPORTATION);
        if(!transportationBudgetVal.isEmpty()) {
        	this.transportationBudget.setText(String.format("$%.2f", transportationBudgetVal.get()));
        	Optional<Double> transportationPercentVal = View.controller.getExpensesByCategoryPercentByDate(Category.TRANSPORTATION, this.lowDate, this.highDate);
        	this.transportationPercent.setText(String.format("%.1f%%", transportationPercentVal.get()));
        } else {
        	this.transportationBudget.setText("N/A");
        	this.transportationPercent.setText("N/A");
        }
        
        // entertainment  
    	Double entertainmentExpVal = View.controller.getTotalExpensesByCategoryByDate(Category.ENTERTAINMENT, this.lowDate, this.highDate);
        this.entertainmentExpenses.setText(String.format("$%.2f", entertainmentExpVal));
    	Optional<Double> entertainmentBudgetVal = View.controller.getBudgetByCategory(Category.ENTERTAINMENT);
        if(!entertainmentBudgetVal.isEmpty()) {
        	this.entertainmentBudget.setText(String.format("$%.2f", entertainmentBudgetVal.get()));
        	Optional<Double> entertainmentPercentVal = View.controller.getExpensesByCategoryPercentByDate(Category.ENTERTAINMENT, this.lowDate, this.highDate);
        	this.entertainmentPercent.setText(String.format("%.1f%%", entertainmentPercentVal.get()));
        } else {
        	this.entertainmentBudget.setText("N/A");
        	this.entertainmentPercent.setText("N/A");
        }
        
        // utilities 
    	Double utilitiesExpVal = View.controller.getTotalExpensesByCategoryByDate(Category.UTILITIES, this.lowDate, this.highDate);
        this.utilitiesExpenses.setText(String.format("$%.2f", utilitiesExpVal));
    	Optional<Double> utilitiesBudgetVal = View.controller.getBudgetByCategory(Category.UTILITIES);
        if(!utilitiesBudgetVal.isEmpty()) {
        	this.utilitiesBudget.setText(String.format("$%.2f", utilitiesBudgetVal.get()));
        	Optional<Double> utilitiesPercentVal = View.controller.getExpensesByCategoryPercentByDate(Category.UTILITIES, this.lowDate, this.highDate);
        	this.utilitiesPercent.setText(String.format("%.1f%%", utilitiesPercentVal.get()));
        } else {
        	this.utilitiesBudget.setText("N/A");
        	this.utilitiesPercent.setText("N/A");
        }
        
        // miscellaneous 
    	Double miscellaneousExpVal = View.controller.getTotalExpensesByCategoryByDate(Category.MISCELLANEOUS, this.lowDate, this.highDate);
        this.miscellaneousExpenses.setText(String.format("$%.2f", miscellaneousExpVal));
    	Optional<Double> miscellaneousBudgetVal = View.controller.getBudgetByCategory(Category.MISCELLANEOUS);
        if(!miscellaneousBudgetVal.isEmpty()) {
        	this.miscellaneousBudget.setText(String.format("$%.2f", miscellaneousBudgetVal.get()));
        	Optional<Double> miscellaneousPercentVal = View.controller.getExpensesByCategoryPercentByDate(Category.MISCELLANEOUS, this.lowDate, this.highDate);
        	this.miscellaneousPercent.setText(String.format("%.1f%%", miscellaneousPercentVal.get()));
        } else {
        	this.miscellaneousBudget.setText("N/A");
        	this.miscellaneousPercent.setText("N/A");
        }
    }
    
    // TODO: Move to controller
    private LocalDate setLowDate(int month, int year) {
    	return LocalDate.of(year, month, 1);
    }
    
    private LocalDate setHighDate(int month, int year) {
    	return YearMonth.of(year, month).atEndOfMonth();
    }
}
