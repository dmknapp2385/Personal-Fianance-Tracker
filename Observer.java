/*
 * File Name: Login.java
 * Authors: Paulina Aguirre (paulinaa3), David Herring (dherring), Chitrangada Juneja(cj21), Elle Knapp (dmknapp2385)
 * Description: This class creates an Observer interface that is implemented in the different view classes. 
 */

interface Observer {
	
    /**
     * description:
     * 	alerts panel that implements it that the a user has logged in
     */
    public void loginChange();

	/**
	 * description:
	 * 	alerts the panel that implements it that a budget has changed
	 */
    public void budgetChange();
        
    /**
     * description:
     * 	alerts panel that implements it that an expense has changed
     */
    public void expenseChange();
}
