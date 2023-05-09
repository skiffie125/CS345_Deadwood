import java.util.Scanner;
public class ProgressManager {
    // Fields
    private Player[] players;
    private LocationManager lm;
    private Bank bank;
    private int daysPlayed;
    private int totalDays;

    // Methods
    public ProgressManager(Player[] players, LocationManager lm, Bank bank) {
        this.players = players;
        this.lm = lm;
        this.bank = bank;
        this.totalDays = totalDays(players.length);
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
        // This wasnt compiling, commented out so i can do some Viewer tests - JB
        // System.out.println("Welcome to Deadwood");
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
                    Player p = new Player(1, i);
                    players[i] =p;
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

            //set up location manager and board here 
        }
        return;
    }

        //Method to take a turn
    //Preconditions: It is the players turn
    //Postcondition: Turn is over 
    public void takeATurn(Player player){
        /*
        take user input from choices: 
        Move, Upgrade, Take a role, Work, End Turn
        
        if Move:
            go to move method 
            take user input from choices: 
            End Turn, Upgrade, Take a role
            go to coresponding method or end turn

        if Upgrade:
            get desired rank  
            go to upgrade method
            take user input from choices:
            Move, End Turn
            go to coresponding method or end turn
        
        if Take a role:
            show roles on current scence
            take user input for which role 
            go to take role method

        if Work
            go work method 
        if end turn:
            termintate
         */
        Viewer v = new Viewer();
         String result = v.getValidComand();
         //Scanner s = new Scanner(System.in);
         System.out.println("Move, Upgrade, Take role, Work, or End turn?");
         while (true) {
            //result = s.nextLine();
            if (result.equals("End turn")) {
                System.out.println("Turn ended");
                break;
            }
            switch (result) {
                case "Move":
                    //System.out.println("Enter destination: ");
                    System.out.println("Here are your options:");
                    Location current = player.getLocation();
                    Location[] currentNeighbors = current.getNeighbors();

                    for(int i = 0; currentNeighbors[i] != null; i++){
                        System.out.println("["+ i +"] " + currentNeighbors[i].getName());
                    }
                    System.out.println("Please type the coresponding number");
                    int index = v.getParameter(5);
                    player.move(currentNeighbors[index]);
                    //String destName = s.nextLine();
                    // Need to get location from string
                    // while(!move()) {
                    //     System.out.println("Invalid destination. New destination: ");
                    //     dest = s.nextLine();
                    // }
                    // currentLocation = dest;
                    break;
                case "Upgrade":
                    break;
                case "Take role":
                    break;
                case "Work":
                    break;
            }
         }
    }

    public void wrapScene(Scene s){
        //check if all shot counters are removed
        //get all on card players
        // if no on card players, no bonuses
        // else 
            // rolln(budget)
            //distribute dollars based on rank
            // give off card bonuses 
        //check if that was the 2nd to last scene
        // if it was end game
        // else end turn 
    }

    public void endGame(){

    }

    // Prepare players for start of day
    public void setUpDay(int numPlayers) {
        return;
    }

    // Calculate the length of game
    public int totalDays(int numPlayers) {
        if (numPlayers >= 3) {
            return 3;
        }
        return 4;
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