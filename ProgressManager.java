public class ProgressManager {
    // Fields
    private Player[] players;
    private LocationManager lm;
    private Bank bank;
    private int daysPlayed;
    private int totalDays;

    // Methods
    public ProgressManager(Player[] players, LocationManager lm, Bank bank, int totalDays) {
        this.players = players;
        this.lm = lm;
        this.bank = bank;
        this.totalDays = totalDays;
    }

    public void setPlayers(Player[] player) {
        return;
    }

    public Player[] getPlayers() {
        return this.players;
    }

    // get player with specified ID
    public Player getPlayer(int id) {
        return this.players[id];
    }

    public void setDaysPlayed(int days) {
        return;
    }

    public int getDaysPlayed() {
        return this.daysPlayed;
    }

    public void setTotalDays() {
        return;
    }

    public int getTotalDays() {
        return this.totalDays;
    }

    // Set up board for play
    public void setUpGame(int numPlayers) {
        return;
    }

    // Prepare players for start of day
    public void setUpDay(int numPlayers) {
        return;
    }

    // Calculate score for all players
    public int calculateScore() {
        return 0;
    }

    // Returns who wins, who loses, etc.
    public String reportOutcome() {
        return "";
    }
}