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
        this.players = player;
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
        this.daysPlayed = days;
        return;
    }

    public int getDaysPlayed() {
        return this.daysPlayed;
    }

    public void setTotalDays(int days) {
        this.totalDays = days;
        return;
    }

    public int getTotalDays() {
        return this.totalDays;
    }

    // Set up board for play
    public void setUpGame(int numPlayers) {
        System.out.println("Welcome to Deadwood");
        if(numPlayers > 8 || numPlayers < 2){
            System.out.println("Incorrect Number of Players; Failed in set Up Game");
        } else{
            Bank b = new Bank(numPlayers);
            bank = b;
            LocationManager l = new LocationManager();
            lm = l; 
            totalDays = 4;

            for(int i = 0; i< 8; i++){
                if (i<numPlayers){
                    Player p = new Player();
                    players[i] =p;
                    p.setRank(1);
                    p.setLocation(null);
                    p.setRole(null);
                    p.setID(i);
                    bank.setDollars(i, 0);
                    bank.setCredits(i, 0);
                } else{
                    players[i] = null;
                }
            }
            if( numPlayers == 2 || numPlayers == 3){
                totalDays = 3;
            } else if(numPlayers == 5){
                for(int i = 0; i< 5; i++){
                    bank.setCredits(i, 2);
                }
            } else if(numPlayers == 6){
                for(int i = 0; i< 5; i++){
                    bank.setCredits(i, 4);
                }
            }else if(numPlayers == 7  || numPlayers == 8){
                for(int i = 0; i< 8; i++){
                     if (i<numPlayers){
                        players[i].setRank(2);
                     }
                }
            }
        }
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