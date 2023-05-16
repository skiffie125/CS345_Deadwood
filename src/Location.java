public class Location{
    private String name;
    private Location[] neighbors;
    private int[] players;
    private int[] dimensions;

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
    public void setDimensions(int[] dim) {
        dimensions = dim;
    }
    public int[] getDimensions() {
        return dimensions;
    }

    public void setPlayers(int numPlayers) {
        players = new int[numPlayers];
    }

    // is location l1 a neighbor of l
    public boolean checkIfNeighbor(Location l){
        boolean result = false;
        String destName = l.getName();
        for (int i = 0; i < neighbors.length; i++) {
            if(neighbors[i].getName().equals(destName)) {
                result = true;
                break;
            }
        }
        return result;
    }


}