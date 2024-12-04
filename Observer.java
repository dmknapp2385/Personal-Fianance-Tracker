
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
