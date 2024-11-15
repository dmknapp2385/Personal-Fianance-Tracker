
interface Observer {

    //alerts the panel that implements it that the budget has changed
    public void budgetChange();
    default void loginChange(){};
}
