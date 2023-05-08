public class LocationManager{
    private Board board;

    public LocationManager(){
        // board = new Board();
        // need call a method on board to set it up here
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
        return false;
    }
    public boolean checkMove(Location start, Location end, Player p){
        return false;
    }
    public boolean checkLocation(Location l, Player p){
        return false;
    }
}