public class Role {
    // Fields
    private String description;
    private int minRank;
    private Player player;
    private String line;
    private int[] dimensions;

    // Constructors
    public Role(String description, int minRank, Player player) {
        this.description = description;
        this.minRank = minRank;
        this.player = player;
    }

    public Role(String description, int minRank) {
        this.description = description;
        this.minRank = minRank;
    }
    
    //Setters and Getters 
    public void setDescription(String desc) {
        this.description = desc;
    }

    public String getDescription() {
        return this.description;
    }

    public void setMinRank(int rank) {
        this.minRank = rank;
    }

    public int getMinRank() {
        return this.minRank;
    }

    public void setLine(String newLine) {
        this.line = newLine;
    }

    public String getLine() {
        return line;
    }

    public Player getPlayer() {
        return this.player;
    }
     public void setPlayer(Player p) {
        player = p;
    }
    public int[] getDimensions() {
        return dimensions;
    }
    public void setDimensions(int[] dimensions) {
        this.dimensions = dimensions;
    }
}