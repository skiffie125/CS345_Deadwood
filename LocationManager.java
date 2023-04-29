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
        return false;
    }
    public boolean checkRoleStatus(Scene s, Role r){
        return false;
    }
    public boolean checkMove(Location start, Location end, Player p){
        return false;
    }
    public boolean checkLocation(Location l, Player p){
        return false;
    }
}