
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
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
            newBoard.getCastingOffice().setPlayers(numPlayers);
            for (int i = 0; i < newBoard.getScenes().length; i++) {
                newBoard.getScenes()[i].setPlayers(numPlayers);
            }
            for (int i = 0; i < newBoard.getScenes().length; i++) {
                newBoard.getScenes()[i].setRehearsals(numPlayers);
            }
            lm.setBoard(newBoard);
        }
        return;
    }

        //Method to take a turn
    //Preconditions: It is the players turn
    //Postcondition: Turn is over 
    public boolean takeATurn(Player player){
        System.out.println("Move, Upgrade, Take role, Work, or End turn?");
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
                case "Current":
                    System.out.println("Active player: Player " + player.getId() + ", Location: " + player.getLocation().getName() + ", Rank: " + player.getRank());
                    System.out.println("Move, Upgrade, Take role or End turn?");
                    next = v.getValidComand();
                    switch (next){
                        case "Upgrade":
                            System.out.println("What rank would you like to upgrade to?");
                            newrank = v.getParameter(5);
                            player.upgrade(newrank, bank, lm);
                            break;
                        case "Move":
                            System.out.println("Here are your options:");
                            current = player.getLocation();
                            currentNeighbors = current.getNeighbors();
                            int numNeighbors = currentNeighbors.length;
                            for(int i = 0; i < numNeighbors; i++){
                                System.out.println("["+ i +"] " + currentNeighbors[i].getName());
                            }
                            System.out.println("Please type the coresponding number");
                            index = v.getParameter(numNeighbors);
                            System.out.println(index + " " + currentNeighbors[index].getName());
                            player.move(currentNeighbors[index], lm);
                            System.out.println("Upgrade, Take role or End turn?");
                            next = v.getValidComand();
                            switch (next){
                                case "Upgrade":
                                    System.out.println("What rank would you like to upgrade to?");
                                    newrank = v.getParameter(5);
                                    player.upgrade(newrank, bank, lm);
                                    break;
                                case "Take A Role":
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
                        case "Take A Role":
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
                    System.out.println("Here are your options:");
                    current = player.getLocation();
                    currentNeighbors = current.getNeighbors();
                    int numNeighbors = currentNeighbors.length;
                    for(int i = 0; i < numNeighbors; i++){
                        System.out.println("["+ i +"] " + currentNeighbors[i].getName());
                    }
                    System.out.println("Please type the coresponding number");
                    index = v.getParameter(numNeighbors);
                    player.move(currentNeighbors[index], lm);
                    System.out.println("Upgrade, Take role or End turn?");
                    next = v.getValidComand();
                    switch (next){
                        case "Upgrade":
                            System.out.println("What rank would you like to upgrade to?");
                            newrank = v.getParameter(5);
                            player.upgrade(newrank, bank, lm);
                            break;
                        case "Take role":
                            Location curLoc = player.getLocation();
                            if (curLoc.getName().equals("trailer") || curLoc.getName().equals("office")) {
                                System.out.println("No roles available at current location");
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
                            index = v.getParameter(validRoles.length);
                            Role role = validRoles[index];
                            player.takeRole(curScene, role, lm, player);
                            System.out.println("Your role: " + player.getRole().getDescription());
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
                // Upgrade and then choose to move or end turn
                case "Upgrade":
                    System.out.println("What rank would you like to upgrade to?");
                    newrank = v.getParameter(5);
                    player.upgrade(newrank+1, bank, lm);
                    System.out.println("Move or End turn?");
                    next = v.getValidComand();
                    switch (next){
                        case "Move":
                            System.out.println("Here are your options:");
                            current = player.getLocation();
                            currentNeighbors = current.getNeighbors();

                            for(int i = 0; currentNeighbors[i] != null; i++){
                                System.out.println("["+ i +"] " + currentNeighbors[i].getName());
                                }
                            System.out.println("Please type the coresponding number");
                            index = v.getParameter(5);
                            player.move(currentNeighbors[index], lm);
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
                    current = player.getLocation();
                    if (current.getName().equals("trailer") || current.getName().equals("office")) {
                        System.out.println("No roles available at current location. Try again on your next turn");
                        break;
                    }
                    System.out.println("What role would you like to take?");
                    Scene[] scenes = lm.getBoard().getScenes();
                    Scene curScene = null;
                    for (int i = 0; i < scenes.length; i++) {
                        if (current.getName().equals(scenes[i].getName())) {
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
                    index = v.getParameter(validRoles.length);
                    Role role = validRoles[index];
                    player.takeRole(curScene, role, lm, player);
                    System.out.println("Your role: " + player.getRole().getDescription());
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
                            //need some way to either get current scene or change location to scene?
                            //player.rehearse(player.getLocation(), lm);
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
    }

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
            }
            if(lm.ScenesWrapped() == 9){
                //time to end the day.
                if(daysPlayed + 1 == totalDays){
                    //end game time
                    endGame();
                } else{
                    daysPlayed++;
                    System.out.println("End of Day "+ daysPlayed);
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

}