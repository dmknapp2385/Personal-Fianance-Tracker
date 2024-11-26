
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics; 		// pie chart exp
import java.awt.Graphics2D;		// pie chart exp
import java.awt.RenderingHints;	// pie chart exp
import java.awt.geom.Arc2D; 	// pie chart exp
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class FinanceView extends JPanel implements Observer{    
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
    
    
    private JLabel foodCategoryExpenses;
    private JLabel transportationCategoryExpenses;
    private JLabel entertainmentCategoryExpenses;
    private JLabel utilitiesCategoryExpenses;
    private JLabel miscellaneousCategoryExpenses;
       
    public FinanceView() {
    	View.controller.addObserver(this);
    }
    	
	public void setup() {
        //set layout for panel
        this.setLayout(new BorderLayout());
        this.setSize(600, 550);
        this.setLayout(new BorderLayout());
        Color color = new Color(217, 214, 176);
        this.setBackground(color);

        //create header text label
        JLabel title = new JLabel("Finance Report");
        title.setFont(new Font("Calibri", Font.BOLD, 20));
        title.setBorder(new EmptyBorder(15, 5, 8, 15));
        this.add(title, BorderLayout.NORTH);

        //Create new panel and add to center
        JPanel centerP = new JPanel();
        centerP.setBackground(color);
        this.add(centerP, BorderLayout.CENTER);
        GroupLayout layout = new GroupLayout(centerP);
        centerP.setLayout(layout);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        //create dimension for textfields and buttons
        Dimension fieldDimension = new Dimension(200, 28);
        Dimension btDimension = new Dimension(125, 25);

        //create text fields and labels for month and year selection
        //month
        JLabel monthTxt = new JLabel("Month: ");
        this.month = new JTextField();
        month.setPreferredSize(fieldDimension);
        month.setBorder(new BevelBorder(BevelBorder.LOWERED));
        centerP.add(monthTxt);
        centerP.add(month);

        //password
        JLabel yearTxt = new JLabel("Year: ");
        this.year = new JPasswordField();
        year.setBorder(new BevelBorder(BevelBorder.LOWERED));
        year.setPreferredSize(fieldDimension);

        //login button
        JButton submitBtn = new JButton("Login");
        submitBtn.setPreferredSize(btDimension);
        submitBtn.setBorder(new BevelBorder(BevelBorder.RAISED));
        submitBtn.setActionCommand("Submit");
        centerP.add(submitBtn);

        //create error text field
        this.error = new JLabel("");
        error.setForeground(Color.RED);
        error.setBorder(new EmptyBorder(15, 0, 15, 15));
        error.setText("Nothing to report.");
        centerP.add(error);
        
        // create total expenses
        JLabel totalTxt = new JLabel("Total Expenses: ");
        totalTxt.setFont(new Font("Calibri", Font.BOLD, 16));
        this.totalAmt = new JLabel();
        this.totalAmt.setBorder(new EmptyBorder(15, 0, 15, 15));
        this.totalAmt.setFont(new Font("Calibri", Font.BOLD, 16));
        this.totalAmt.setText("$0.00");	// TODO: get value and add it in eventListener
        centerP.add(totalTxt);
        centerP.add(totalAmt);
        
        // create food expense information
        JLabel foodTxt = new JLabel("Food");
        foodTxt.setFont(new Font("Calibri", Font.BOLD, 12));
        
        JLabel foodExpensesTxt = new JLabel("Expenses: ");
        this.foodExpenses = new JLabel();
        this.foodExpenses.setText("$0.00");	// TODO: get value and add it in eventListener
        centerP.add(foodExpensesTxt);
        centerP.add(foodExpenses);
        
        JLabel foodBudgetTxt = new JLabel("Budget: ");
        this.foodBudget = new JLabel();
        this.foodBudget.setText("$0.00");	// TODO: get value and add it in eventListener
        centerP.add(foodBudgetTxt);
        centerP.add(foodBudget);
        
        JLabel foodPercentTxt = new JLabel("% of Budget: ");
        this.foodPercent = new JLabel();
        this.foodPercent.setText("0%");	// TODO: get value and add it in eventListener
        centerP.add(foodPercentTxt);
        centerP.add(foodPercent);
        
        // create transportation expense information
        JLabel transportationTxt = new JLabel("Transportation");
        transportationTxt.setBorder(new EmptyBorder(15, 0, 0, 0));
        transportationTxt.setFont(new Font("Calibri", Font.BOLD, 12));
        
        JLabel transportationExpensesTxt = new JLabel("Expenses: ");
        this.transportationExpenses = new JLabel();
        this.transportationExpenses.setText("$0.00");	// TODO: get value and add it in eventListener
        centerP.add(transportationExpensesTxt);
        centerP.add(transportationExpenses);
        
        JLabel transportationBudgetTxt = new JLabel("Budget: ");
        this.transportationBudget = new JLabel();
        this.transportationBudget.setText("$0.00");	// TODO: get value and add it in eventListener
        centerP.add(transportationBudgetTxt);
        centerP.add(transportationBudget);
        
        JLabel transportationPercentTxt = new JLabel("% of Budget: ");
        this.transportationPercent = new JLabel();
        this.transportationPercent.setText("0%");	// TODO: get value and add it in eventListener
        centerP.add(transportationPercentTxt);
        centerP.add(transportationPercent);

        // create entertainment expense information
        JLabel entertainmentTxt = new JLabel("Entertainment      ");
        entertainmentTxt.setBorder(new EmptyBorder(15, 0, 0, 0));
        entertainmentTxt.setFont(new Font("Calibri", Font.BOLD, 12));
        
        JLabel entertainmentExpensesTxt = new JLabel("Expenses: ");
        this.entertainmentExpenses = new JLabel();
        this.entertainmentExpenses.setText("$0.00");	// TODO: get value and add it in eventListener
        centerP.add(entertainmentExpensesTxt);
        centerP.add(entertainmentExpenses);
        
        JLabel entertainmentBudgetTxt = new JLabel("Budget: ");
        this.entertainmentBudget = new JLabel();
        this.entertainmentBudget.setText("$0.00");	// TODO: get value and add it in eventListener
        centerP.add(entertainmentBudgetTxt);
        centerP.add(entertainmentBudget);
        
        JLabel entertainmentPercentTxt = new JLabel("% of Budget: ");
        this.entertainmentPercent = new JLabel();
        this.entertainmentPercent.setText("0%");	// TODO: get value and add it in eventListener
        centerP.add(entertainmentPercentTxt);
        centerP.add(entertainmentPercent);
        
        // create utilities expense information
        JLabel utilitiesTxt = new JLabel("Utilities");
        utilitiesTxt.setBorder(new EmptyBorder(15, 0, 0, 0));
        utilitiesTxt.setFont(new Font("Calibri", Font.BOLD, 12));
        
        JLabel utilitiesExpensesTxt = new JLabel("Expenses: ");
        this.utilitiesExpenses = new JLabel();
        this.utilitiesExpenses.setText("$0.00");	// TODO: get value and add it in eventListener
        centerP.add(utilitiesExpensesTxt);
        centerP.add(utilitiesExpenses);
        
        JLabel utilitiesBudgetTxt = new JLabel("Budget: ");
        this.utilitiesBudget = new JLabel();
        this.utilitiesBudget.setText("$0.00");	// TODO: get value and add it in eventListener
        centerP.add(utilitiesBudgetTxt);
        centerP.add(utilitiesBudget);
        
        JLabel utilitiesPercentTxt = new JLabel("% of Budget: ");
        this.utilitiesPercent = new JLabel();
        this.utilitiesPercent.setText("0%");	// TODO: get value and add it in eventListener
        centerP.add(utilitiesPercentTxt);
        centerP.add(utilitiesPercent);
        
     // create miscellaneous expense information
        JLabel miscellaneousTxt = new JLabel("Miscellaneous");
        miscellaneousTxt.setBorder(new EmptyBorder(15, 0, 0, 0));
        miscellaneousTxt.setFont(new Font("Calibri", Font.BOLD, 12));
        
        JLabel miscellaneousExpensesTxt = new JLabel("Expenses: ");
        this.miscellaneousExpenses = new JLabel();
        this.miscellaneousExpenses.setText("$0.00");	// TODO: get value and add it in eventListener
        centerP.add(miscellaneousExpensesTxt);
        centerP.add(miscellaneousExpenses);
        
        JLabel miscellaneousBudgetTxt = new JLabel("Budget: ");
        this.miscellaneousBudget = new JLabel();
        this.miscellaneousBudget.setText("$0.00");	// TODO: get value and add it in eventListener
        centerP.add(miscellaneousBudgetTxt);
        centerP.add(miscellaneousBudget);
        
        JLabel miscellaneousPercentTxt = new JLabel("% of Budget: ");
        this.miscellaneousPercent = new JLabel();
        miscellaneousPercent.setText("0%");	// TODO: get value and add it in eventListener
        centerP.add(miscellaneousPercentTxt);
        centerP.add(miscellaneousPercent);

        
        //layout components
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(monthTxt)
                                .addComponent(totalTxt)
                                .addComponent(foodTxt)
                                .addComponent(foodExpensesTxt)
                                .addComponent(foodBudgetTxt)
                                .addComponent(foodPercentTxt)
		                        .addComponent(transportationTxt)
		                        .addComponent(transportationExpensesTxt)
		                        .addComponent(transportationBudgetTxt)
		                        .addComponent(transportationPercentTxt))
//		                        .addComponent(pieChart))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(month, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(error)
                                .addComponent(totalAmt)
                                .addComponent(foodExpenses)
                                .addComponent(foodBudget)
                                .addComponent(foodPercent)
                                .addComponent(transportationExpenses)
                                .addComponent(transportationBudget)
                                .addComponent(transportationPercent))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(yearTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                        .addComponent(entertainmentTxt)
		                        .addComponent(entertainmentExpensesTxt)
		                        .addComponent(entertainmentBudgetTxt)
		                        .addComponent(entertainmentPercentTxt)
		                        .addComponent(utilitiesTxt)
		                        .addComponent(utilitiesExpensesTxt)
		                        .addComponent(utilitiesBudgetTxt)
		                        .addComponent(utilitiesPercentTxt))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        		.addComponent(year, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)                   		
                        		.addComponent(entertainmentExpenses)
                                .addComponent(entertainmentBudget)
                                .addComponent(entertainmentPercent)
                                .addComponent(utilitiesExpenses)
                                .addComponent(utilitiesBudget)
                                .addComponent(utilitiesPercent))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(submitBtn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(miscellaneousTxt)
                                .addComponent(miscellaneousExpensesTxt)
		                        .addComponent(miscellaneousBudgetTxt)
		                        .addComponent(miscellaneousPercentTxt))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(miscellaneousExpenses)
                                .addComponent(miscellaneousBudget)
                                .addComponent(miscellaneousPercent))
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(monthTxt)
                                .addComponent(month)
                                .addComponent(yearTxt)
                                .addComponent(year)
                                .addComponent(submitBtn))
                        .addComponent(error)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(totalTxt)
                                .addComponent(totalAmt))
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
//                        .addComponent(pieChart)
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
        
//        // Create the panel for pie chart 
//        JPanel chartPanel = new JPanel();
//        chartPanel.setBackground(color);
//        chartPanel.setPreferredSize(new Dimension(250, 200));
//        bottomP.add(chartPanel, BorderLayout.EAST);
        
        
//        GroupLayout pieLayout = new GroupLayout(bottomP);
//        
//        pieLayout.setAutoCreateContainerGaps(true);
//        pieLayout.setAutoCreateGaps(true);
        
        
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
        
        
        
        
        
        
        submitBtn.addActionListener(new ButtonActionListener());
    }

    
 
    private class ButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("Submit")) {
                System.out.println(month.getText());
                System.out.println(year.getText());
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
    	this.setup();
        // throw new UnsupportedOperationException("Not supported yet.");
    	// repaint the panel or just updates fields without calling setup
    	// look in showAllExpenses ExpenseView
    }

    @Override
    public void loginChange() {
    	this.setup();
        //throw new UnsupportedOperationException("Not supported yet.");
        // Create setup method that contains all of the code, call from here
    }
}

