public class Location{
    private String name;
    private Location[] neighbors;
    private int[] players;

    //constructors
    public Location() {

    }
    public Location(String name){
        this.name = name;
    }

    //getters and Setters 
    public void setName(String newName){
        name = newName;
    }

    public String getName(){
        return name;
    }

    public void setNeighbors(Location[] newNeighbors){
        neighbors = newNeighbors;
    }

    public Location[] getNeighbors(){
        return neighbors;
    }
    public void setPlayer(int player, int value){
        players[player] = value;
    }
    public int[] getPlayers(){
        return players;
    }

    //class methods
    public boolean checkIfNeighbor(Location l){
        boolean result = false;
        for(int i = 0; i < neighbors.length; i++){
            if (l == neighbors[i]){
                result = true;
            }
        }
        return result;
    }

}