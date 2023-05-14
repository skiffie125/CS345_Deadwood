public class Location{
    private String name;
    private Location[] neighbors;
    private int[] players;
    private int[] dimensions;
    private Scene scene;

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
    public Scene getScene() {
        return scene;
    }
    public void setPlayers(int numPlayers) {
        players = new int[numPlayers];
    }

    // is location l1 a neighbor of l
    public boolean checkIfNeighbor(Location l){
        boolean result = false;
        String destName = l.getName();
        System.out.println("Requested destination: " + l.getName());
        System.out.println("Number of neighbors: " + neighbors.length);
        for (int i = 0; i < neighbors.length; i++) {
            System.out.println("Neighbor " + i + " name: " + neighbors[i].getName());
            if(neighbors[i].getName().equals(destName)) {
                System.out.println("Neighbor check passed");
                result = true;
                break;
            }
        }
        return result;
    }


}