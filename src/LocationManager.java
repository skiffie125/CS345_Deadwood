public class LocationManager{
    private Board board;
    private Location[] locations;

    public LocationManager(Location[] locations){
        // board = new Board();
        // need call a method on board to set it up here
        locations = locations;
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