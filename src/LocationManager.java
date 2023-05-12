public class LocationManager{
    private Board board;
    private Location[] locations;

    public LocationManager(Location[] locations){
        // board = new Board();
        // need call a method on board to set it up here
        locations = locations;
    }
    public LocationManager(){
        
    }
    
    public void setBoard(Board newBoard){
        board=newBoard;
    }
    public Board getBoard(){
        return this.board;
    }

    public boolean checkCardStatus(Scene s){
        return s.cardFaceUp();
    }
    public boolean checkRoleStatus(Scene s, Role r){
        boolean onScene =false;
        boolean roleAvalible = false;
        if (r.getPlayer() == null){
            roleAvalible = true;
        }
        Role[] offCardRoles = s.getOffCardRoles();
        Role[] onCardRoles = s.getCard().getOnCardRoles();
        for(int i = 0; i < offCardRoles.length; i++){
            if (r == offCardRoles[i]){
                onScene = true;
            }
        }
        for(int i = 0; i < onCardRoles.length; i++){
            if (r == onCardRoles[i]){
                onScene = true;
            }
        }
        return onScene && roleAvalible;
    }
    public boolean checkMove(Location start, Location end, int playerID){
        boolean result = false;
        if (start.checkIfNeighbor(end) && end.checkIfNeighbor(start)){
            int[] players = start.getPlayers();
            if (players[playerID] == 1){
                result = true;
            }
        }
        return result;
    }

    //does this location have the player 
    public boolean checkLocation(Location l, int playerID){
        int[] players = l.getPlayers();
        if (players[playerID] == 1){
            return true;
        } else{
            return false;
        }

        
    }
}