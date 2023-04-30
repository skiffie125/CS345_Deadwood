public class Role {
    // Fields
    String description;
    int minRank;
    Player player;

    // Methods
    public Role(String description, int minRank, Player player) {
        this.description = description;
        this.minRank = minRank;
        this.player = player;
    }

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

    public boolean take(Player player) {
        return true;
    }

    public Player getPlayer() {
        return this.player;
    }

}