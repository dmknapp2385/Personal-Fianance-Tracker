
interface Observer {

    //alerts the panel that implements it that the budget has changed
    public void budgetChange();

    //alerts panel that implemts it that the a user has logged in
    public void loginChange();
    
    public default void expenseChange() {};
}
