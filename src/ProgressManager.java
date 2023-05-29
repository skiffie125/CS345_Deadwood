
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import javax.swing.JOptionPane;
public class ProgressManager {
    // Fields
    private Player[] players;
    private LocationManager lm;
    private Bank bank;
    private int daysPlayed;
    private int totalDays;
    private GUI gui;
    private Player curPlayer;

    // Methods
    public ProgressManager(Player[] players, LocationManager lm, Bank bank) {
        this.players = players;
        this.lm = lm;
        this.bank = bank;
    }

    public void setPlayers(Player[] player) {
        this.players = player;
        return;
    }

    public Player[] getPlayers() {
        return this.players;
    }
    public LocationManager getLocationManager() {
        return this.lm;
    } 

    // get player with specified ID
    public Player getPlayer(int id) {
        return this.players[id];
    }

    public void setCurPlayer(int id) {
        this.curPlayer = this.players[id];
    }

    public Player getCurPlayer() {
        return this.curPlayer;
    }

    public void iterCurPlayer() {
        int cur = (this.curPlayer.getId() +1) % (this.players.length);
        this.setCurPlayer(cur);
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

    public void setGui(GUI g) {
        this.gui = g;
    }

    public GUI getGui() {
        return this.gui;
    }

    // Set up board for play
    public void setUpGame(int numPlayers) {
         if(numPlayers > 8 || numPlayers < 2){
            JOptionPane.showMessageDialog(null, "Incorrect number of players! Please relaunch game.");
        } else{
            //sets up feilds 
            this.totalDays = totalDays(numPlayers);
            players = new Player[numPlayers];
            Bank b = new Bank(numPlayers);
            bank = b;
            LocationManager l = new LocationManager();
            lm = l; 
            totalDays = 4;
            for (int i = 0; i < numPlayers; i++) {
                Player p = new Player(1, i);
                    players[i] =p;
                    p.setLocation(null);
                    p.setRole(null);
                    p.setID(i);
                    bank.setDollars(i, 0);
                    bank.setCredits(i, 0);
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
            gui.createPlayerLabels(players);
            //set up location manager and board from XML files  
            ParseXML parser = new ParseXML();
            Document startBoard = null;
            Document startCards = null;
            try{
                startBoard = parser.getDocFromFile("board.xml");
                startCards = parser.getDocFromFile("cards.xml");
            }catch(ParserConfigurationException e){
                System.out.print("board and cards not set up correctly");
            }
            Board newBoard = parser.readBoard(startBoard);
            Card[] newDeck = parser.readCards(startCards);
            newBoard.setCards(newDeck);
            
            newBoard.getTrailer().setPlayers(numPlayers);
            Location[] neighbors = newBoard.getTrailer().getNeighbors();
            setCurPlayer(0);
            newBoard.getCastingOffice().setPlayers(numPlayers);
            for (int i = 0; i < newBoard.getScenes().length; i++) {
                newBoard.getScenes()[i].setPlayers(numPlayers);
                newBoard.getScenes()[i].setIndex(i);
            }
            for (int i = 0; i < newBoard.getScenes().length; i++) {
                newBoard.getScenes()[i].setRehearsals(numPlayers);
            }
            lm.setBoard(newBoard);
            gui.setUpDay();
        }
        return;
    }

        //Method to take a turn
    //Preconditions: It is the players turn
    //Postcondition: Turn is over 
    /*public boolean takeATurn(Player player){
        System.out.println("Current, Locate all, Move, Upgrade, Take role, Work, or End turn?");
        boolean gameContinues = true;
        Viewer v = new Viewer();
        String result = v.getValidComand();
        String next;
        int newrank;
        Location current;
        Scene currentScene;
        Role[] totalRoles;
        Location[] currentNeighbors;
        int index;
        if (result.equals("End turn")) {
            System.out.println("Turn ended");
        } else{
            switch (result) {
                //get information and then have an action
                case "Locate all":
                    for (int i = 0; i < players.length; i++) {
                        System.out.println("Player " + i + " Location: " + players[i].getLocation().getName());
                    }
                    System.out.println("Move, Upgrade, Take role, Work or End turn?");
                    next = v.getValidComand();
                    switch (next){
                        case "Upgrade":
                            upgradePM(player);
                            break;
                        case "Move":
                            movePM(player);
                            System.out.println("Upgrade, Take role or End turn?");
                            next = v.getValidComand();
                            switch (next){
                                case "Upgrade":
                                    upgradePM( player);
                                    break;
                                case "Take role":
                                    takeARolePM(player);
                                    break;
                                case "End turn":
                                    System.out.println("Turn ended");
                                    break;
                                case "End Game":
                                    endGame();
                                    gameContinues = false;
                                    break;
                                default:
                                     System.out.println("Sorry that's not any of the options, try again on your next turn");
                                    break;
                            }
                            break;
                        case "Take role":
                            takeARolePM(player);
                            break;
                        case "Work":
                            System.out.println("Act or Rehearse?");
                            next = v.getValidComand();
                            switch (next){
                                case "Act":
                                    if(lm.checkLocation(player.getLocation(), player.getId())){
                                        if(player.act(lm.LocationToScene(player.getLocation()), player.getRole(), player, bank)){
                                            wrapScene(lm.LocationToScene(player.getLocation()));
                                        } 
                                    } else{
                                        System.out.println("Location Error");
                                    }
                                break;
                        case "Rehearse":
                            player.rehearse(lm.LocationToScene(player.getLocation()),  lm);
                            break;
                        case "End Game":
                            endGame();
                            gameContinues = false;
                            break;
                        default:
                            System.out.println("Sorry that's not any of the options, try again on your next turn");
                            break;
                    }
                    break;
                        case "End turn":true
                            System.out.println("Turn ended");
                            break;
                        case "End Game":
                            endGame();
                            gameContinues = false;
                            break;
                        default:
                            System.out.println("Sorry that's not any of the options, try again on your next turn");
                            break;
                    }
                    break;
                case "Current":
                    System.out.println("Active player: Player " + player.getId() + ", Location: " + player.getLocation().getName() + ", Rank: " + player.getRank());
                    System.out.println("Dollars: "+ bank.getDollars(player.getId()) + " Credits: " + bank.getCredits(player.getId()));
                    System.out.println("Move, Upgrade, Take role or End turn?");
                    next = v.getValidComand();
                    switch (next){
                        case "Upgrade":
                            upgradePM(player);
                            break;
                        case "Move":
                            movePM(player);
                            System.out.println("Upgrade, Take role or End turn?");
                            next = v.getValidComand();
                            switch (next){
                                case "Upgrade":
                                    upgradePM( player);
                                    break;
                                case "Take role":
                                    takeARolePM(player);
                                    break;
                                case "End turn":
                                    System.out.println("Turn ended");
                                    break;
                                case "End Game":
                                    endGame();
                                    gameContinues = false;
                                    break;
                                default:
                                     System.out.println("Sorry that's not any of the options, try again on your next turn");
                                    break;
                            }
                            break;
                        case "Take role":
                            takeARolePM(player);
                            break;
                        case "End turn":
                            System.out.println("Turn ended");
                            break;
                        case "End Game":
                            endGame();
                            gameContinues = false;
                            break;
                        default:
                            System.out.println("Sorry that's not any of the options, try again on your next turn");
                            break;
                    }
                    break;
                // Move and then choose from take a role upgrade or end turn
                case "Move":
                    movePM(player);
                    System.out.println("Upgrade, Take role or End turn?");
                    next = v.getValidComand();
                    switch (next){
                        case "Upgrade":
                            System.out.println("What rank would you like to upgrade to?");
                            newrank = v.getParameter(5);
                            player.upgrade(newrank, bank, lm);
                            break;
                        case "Take role":
                            takeARolePM(player);
                            break;
                        case "End turn":
                            System.out.println("Turn ended");
                            break;
                        case "End Game":
                            endGame();
                            gameContinues = false;
                            break;
                        default:
                            System.out.println("Sorry that's notruet any of the options, try again on your next turn");
                            break;
                    }
                    break;
                // Upgrade and then choose to move or end turn
                case "Upgrade":
                    upgradePM(player);
                    System.out.println("Move or End turn?");
                    next = v.getValidComand();
                    switch (next){
                        case "Move":
                            movePM(player);
                            break;
                        case "End turn":
                            System.out.println("Turn ended");
                            break;
                        case "End Game":
                            endGame();
                            gameContinues = false;
                            break;
                        default:
                            System.out.println("Sorry that's not any of the options, try again on your next turn");
                            break;
                    }
                    break;
                // take a role 
                case "Take role":
                    takeARolePM(player);
                    break;
                case "Work":
                    System.out.println("Act or Rehearse?");
                    next = v.getValidComand();
                    switch (next){
                        case "Act":
                            if(lm.checkLocation(player.getLocation(), player.getId()) && lm.LocationToScene(player.getLocation()) != null){
                                if(player.act(lm.LocationToScene(player.getLocation()), player.getRole(), player, bank)){
                                    wrapScene(lm.LocationToScene(player.getLocation()));
                                    if(lm.ScenesWrapped() == 9){
                                        gameContinues = false;
                                    }
                                } 
                            } else{
                                System.out.println("Location Error");
                            }
                            break;
                        case "Rehearse":
                            if(lm.LocationToScene(player.getLocation()) != null){
                                player.rehearse(lm.LocationToScene(player.getLocation()),  lm);
                            } else {
                                System.out.println("You need to be on set to rehearse, try moving on your next turn");
                            }
                            
                            break;
                        case "End Game":
                            endGame();
                            gameContinues = false;
                            break;
                        default:
                            System.out.println("Sorry that's not any of the options, try again on your next turn");
                            break;
                    }
                    break;

                case "End game":
                    endGame();
                    gameContinues = false;
                    break;
                default:
                    System.out.println("Sorry that's not any of the options, lets try again");
                    takeATurn(player);
                    break;
            }
        }
            
        return gameContinues;
    }*/

    // Wrap Scene s
    public void wrapScene(Scene s){
        if(s.getShotCountersLeft() == 0){
            //check if an on card
            System.out.println("wrapping scene " + s.getName());
            int numOnCardPlayers = 0;
            Role[] onCardRoles = s.getCard().getOnCardRoles();
            for (int i = 0; i<onCardRoles.length; i++){
                if(onCardRoles[i].getPlayer() != null){
                    if(lm.checkLocation(s, onCardRoles[i].getPlayer().getId())){
                        numOnCardPlayers++;
                    }
                }
            }
            if(numOnCardPlayers>0){
                System.out.println("Bonuses, yay!");
                //go through the on card roles by rank and give dice 
                Dice d = new Dice();
                int[] payout = d.rollN(s.getCard().getBudget());
                int payoutIndex = 0;
                
                while(payoutIndex<payout.length){
                    //becuase all roles are sorted from lowest to highest already we just go through them backwards!
                    for (int i = onCardRoles.length - 1; i >= 0; i--){
                        if(onCardRoles[i].getPlayer() != null){
                            if(lm.checkLocation(s, onCardRoles[i].getPlayer().getId())){
                                if(payoutIndex<payout.length){
                                    bank.pay(onCardRoles[i].getPlayer().getId(), payout[payoutIndex] ,0);
                                    payoutIndex++;
                                }
                            }
                        }
                    }
                }
                //off card roles bonuses 
                Role[] offCardRoles = s.getOffCardRoles();
                for(int i = 0; i < offCardRoles.length; i++){
                    if (offCardRoles[i].getPlayer() != null){
                        bank.add(offCardRoles[i].getPlayer().getId(), offCardRoles[i].getMinRank(), 0);
                    }
                }
            }else{
                System.out.println("No bonuses, sorry");
            }
            if(lm.ScenesWrapped() == 9){
                //time to end the day.
                if(daysPlayed + 1 == totalDays){
                    //end game time
                    endGame();
                } else{
                    daysPlayed++;

                    System.out.println();
                    System.out.println("End of Day "+ daysPlayed);
                    System.out.println();
                    setUpDay(players.length);
                }
            }
        }
    }

    //End the game 
    public void endGame(){
        System.out.println("Ending the Game");
        calculateScore();
    }

    // Prepare players for start of day
    public void setUpDay(int numPlayers) {
        Location castingOffice = lm.getBoard().getCastingOffice();
        Location trailer = lm.getBoard().getTrailer();
        for (int i = 0; i < numPlayers; i++) {
            players[i].setLocation(lm.getBoard().getTrailer());
                players[i].setRole(null);
                castingOffice.setPlayer(i,0);
                trailer.setPlayer(i,1);
                players[i].setLocation(trailer);
                
        }
        Scene[] scenes = lm.getBoard().getScenes();
        for(int j = 0; j<scenes.length; j++){
            scenes[j].reset(lm.getBoard().pop());
        }
        //untested
        gui.setUpDay();
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
        int[] score = new int[players.length];
        for(int i = 0; i < players.length; i++){
            if(players[i] != null){
                score[i] = bank.getCredits(i) + bank.getDollars(i) + players[i].getRank()*5;
            }
        }
        int winner =0;
        int tie = 0;
        // find max score
        for(int i = 0; i < players.length; i++){
            if(score[winner]< score[i]){
                winner = i;
            }
        }
        //find if tie
        for(int i = 0; i < players.length; i++){
            if(score[winner] == score[i]){
                tie++;
            }
        }
        if(tie == 1){
            System.out.println("Congratualtions Player " + winner + ", You win the game");
        } else{
            //why did you have to tie, i don't like you
            System.out.println("Looks like we have a Tie! The players that tied are: ");
            for(int i = 0; i < players.length; i++){
                if(score[winner] == score[i]){
                    System.out.println("Player" + i);
                }
            }
        }

        return 0;
    }
    /*
    Methods interior methods for take a turn
     */

    public boolean actPM(Player player){
        boolean result = false;
        if(lm.checkLocation(player.getLocation(), player.getId()) && lm.LocationToScene(player.getLocation()) != null){
            if(player.act(lm.LocationToScene(player.getLocation()), player.getRole(), player, bank)){
                result = true;
                wrapScene(lm.LocationToScene(player.getLocation()));
            } 
        } else{
            //replace with GUI based error message
            System.out.println("Location Error");
        }

        
        return result;
    }
    public boolean rehearsePM(Player player){
        boolean result = false;
        if(lm.LocationToScene(player.getLocation()) != null){
            result = player.rehearse(lm.LocationToScene(player.getLocation()),  lm);
        } else {
            // replace with GUI based error message 
            System.out.println("You need to be on set to rehearse, try moving on your next turn");
        }
        return result;
    }

    public boolean takeARolePM(Player player, Role newRole){
        /*Viewer v = new Viewer();
        Location curLoc = player.getLocation();
        if (curLoc.getName().equals("trailer") || curLoc.getName().equals("office")) {
            System.out.println("No roles available at current location");
            return;
        }
        System.out.println("What role would you like to take?");
        Scene[] scenes = lm.getBoard().getScenes();
        Scene curScene = null;
        for (int i = 0; i < scenes.length; i++) {
            if (curLoc.getName().equals(scenes[i].getName())) {
                curScene = scenes[i];
            }
        }
        Role[] validRoles = new Role[curScene.getOffCardRoles().length + curScene.getCard().getOnCardRoles().length];
        for (int i = 0; i < curScene.getOffCardRoles().length; i++){
            validRoles[i] = curScene.getOffCardRoles()[i];
        }
        for (int i = curScene.getOffCardRoles().length; i < validRoles.length; i++){
            validRoles[i] = curScene.getCard().getOnCardRoles()[i- curScene.getOffCardRoles().length];
        }
        System.out.println("Here are your options:");
        for (int i = 0; i < validRoles.length; i++) {
            System.out.println("[" + i + "]" + validRoles[i].getDescription() + " Minimum rank: " + validRoles[i].getMinRank());
        }
        System.out.println("Enter the corresponding number:");
        int index = v.getParameter(validRoles.length);
        Role role = validRoles[index];*/
        boolean result = false; 
        if (player.takeRole(lm.LocationToScene(player.getLocation()), newRole, lm, player)){
            result = true;
        } return result;

        
    }

    public boolean upgradePM(Player player, Integer rank){
        /*Viewer v = new Viewer();
        System.out.println("Your current rank: " + player.getRank());
        System.out.println("Your current funds are " + bank.getDollars(player.getId()) + " dollars and " + bank.getCredits(player.getId()) + " credits");
        System.out.println("What rank would you like to upgrade to?");
        int newrank = v.getParameter(5);*/
        return player.upgrade(rank, bank, lm);
    }


    public boolean movePM(Player player, Location newLocation){
        /*Viewer v = new Viewer();
        System.out.println("You are currently at "+ player.getLocation().getName());
        System.out.println("Here are your options:");
        Location current = player.getLocation();
        Location[] currentNeighbors = current.getNeighbors();
        int numNeighbors = currentNeighbors.length;
        for(int i = 0; i < numNeighbors; i++){
            System.out.println("["+ i +"] " + currentNeighbors[i].getName());
        }
        System.out.println("Please type the coresponding number");
        int index = v.getParameter(numNeighbors);*/
        return player.move(newLocation, lm);
    }

    public Location[] getNeighbors(Player player) {
        return player.getLocation().getNeighbors();
    }

    public Role[] getRoles(Player player){
        Location curLoc = player.getLocation();
        if (curLoc.getName().equals("trailer") || curLoc.getName().equals("office")) {
            //Replace with GUI Error
            System.out.println("No roles available at current location");
            return null;
        }
        Scene[] scenes = lm.getBoard().getScenes();
        Scene curScene = null;
        for (int i = 0; i < scenes.length; i++) {
            if (curLoc.getName().equals(scenes[i].getName())) {
                curScene = scenes[i];
            }
        }
        Role[] validRoles = new Role[curScene.getOffCardRoles().length + curScene.getCard().getOnCardRoles().length];
        for (int i = 0; i < curScene.getOffCardRoles().length; i++){
            validRoles[i] = curScene.getOffCardRoles()[i];
        }
        for (int i = curScene.getOffCardRoles().length; i < validRoles.length; i++){
            validRoles[i] = curScene.getCard().getOnCardRoles()[i- curScene.getOffCardRoles().length];
        }
        return validRoles;
    }

    public Integer[] getUpgradeLevels(){
        Integer[] upgrades = {2, 3, 4, 5, 6};
        return upgrades;
    }

}