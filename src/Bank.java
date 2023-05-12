public class Bank {
    // Fields
    private int[] dollars;
    private int[] credits;

    // Methods

    // Constructor
    // Take an array of players and the number thereof
    // to determine starting currency/currencies
    public Bank(int numPlayers) {
        dollars = new int[numPlayers];
        credits = new int[numPlayers];
        return;
    }

    public void setDollars(int player, int amount) {
        dollars[player] = amount;
        return;
    }

    public int getDollars(int player) {
        return this.dollars[player];
    }

    public void setCredits(int player, int amount) {
        credits[player] = amount;
        return;
    }

    public int getCredits(int player) {
        return this.credits[player];
    }

    //we should add a method here to check to make sure a player can't go into the red
    public boolean verifyWithdraw(int player, int dollarAmount, int creditAmount) {
        return (((dollars[player] - dollarAmount) > 0) || (credits[player] - creditAmount > 0));
    }

    // add given amount(s) to specified player
    public void add(int player, int dollarAmount, int creditAmount) {
        dollars[player] += dollarAmount;
        credits[player] += creditAmount; 
        return;
    }

    // subtract given amount(s) to specified player
    public void pay(int player, int dollarAmount, int creditAmount) {
        dollars[player] -= dollarAmount;
        credits[player] -= creditAmount;
        return;
    }
}