public class LocationManager{
    private Board board;


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

    //check that this role is untaken and is on this scene
    public boolean checkRoleStatus(Scene s, Role r){
        boolean onScene =false;
        boolean roleAvalible = false;
        if (r.getPlayer() == null && s.getShotCountersLeft()>0){
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

    //would a player be able to make this move
    public boolean checkMove(Location start, Location end, int playerID){

        boolean result = false;
        if (start.checkIfNeighbor(end)){
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

    public int ScenesWrapped(){
        int result =  0;
        Scene[] s =board.getScenes();
        for (int i = 0; i < s.length; i++){
            if(s[i].getShotCountersLeft()==0){
                result++;
            }
        }
        return result;
    }
    
    public Scene LocationToScene(Location l){
        Scene s = null;
        Scene[] allScenes =board.getScenes();
        for(int i = 0; i < allScenes.length; i++){
            if(l.getName().equals(allScenes[i].getName())){
                s = allScenes[i];
            }
        }
        return s;
    }
}