public class Scene extends Location{

    private int shotCountersMax;
    private int shotCountersLeft;
    private Card card;
    private boolean faceUp;
    private Role[] offCardRoles;
    private int[] rehearsals;
    private int[] dimensions;
    private int[][] shotDimensions;
    int index;

    //Constructors 
    public Scene(String name) {
        super(name);
    }
    //Getters and setters 
    public void setShotCountersLeft(int n){
        shotCountersLeft = n;
    }
    public void removeAShotCounter(){
        shotCountersLeft--;
    }
    public int getShotCountersLeft(){
        return shotCountersLeft;
    }
    public void setShotCountersMax(int n){
        shotCountersMax = n;
    }
     public int getShotCountersMax(){
        return shotCountersMax;
    }
    public int getIndex(){
        return index;
    }
    public void setIndex(int n){
        index = n;
    }
    
    public void setCard(Card newCard){
        card = newCard;
    }
    public Card getCard(){
        return card;
    }

    public void flipCard(boolean flip){
        faceUp = flip;
    }
    public boolean cardFaceUp(){
        return faceUp;
    }
    public Role[] getRoles() {
        return offCardRoles;
    }
    public void setRoles(Role[] roles){
        offCardRoles = roles;
    }
    public Role[] getOffCardRoles(){
        return offCardRoles;
    }

    public void setAllRehearsals(int[] a){
        rehearsals = a;
    }
    public void addRehearsal(int player){
        rehearsals[player]++;
    }
    public int[] getAllRehearsals(){
        return rehearsals;
    }
    public int getRehearsal(int player){
        return rehearsals[player];
    }
    public void setRehearsals(int numPlayers) {
        rehearsals = new int[numPlayers];
    }
    public void setDimensions(int[] dim) {
        dimensions = dim;
    }
    public int[] getDimensions() {
        return dimensions;
    }

    public void setShotDimensions(int[][] dim) {
        shotDimensions = dim;
    }

    public void setShotDimension(int index, int[] dim) {
        this.shotDimensions[index] = dim;
    }

    public void setEachShotDimension(int index, int x, int y, int h, int w) {
        this.shotDimensions[index][0] = x;
        this.shotDimensions[index][1] = y;
        this.shotDimensions[index][2] = h;
        this.shotDimensions[index][3] = w;
    }

    // Returns dimensions of all shot counters
    public int[][] getShotDimensions() {
        return shotDimensions;
    }

    // Returns the dimensions of a specific shot counter
    public int[] getShotCounterDimensions(int index) {
        return shotDimensions[index];
    }
  

    //class methods
    // resets for each new day
    public void reset(Card newCard){
        card = newCard;
        shotCountersLeft = shotCountersMax; 
        faceUp = false;
        for(int i = 0; i < offCardRoles.length; i++){
            offCardRoles[i].setPlayer(null);
        }
        int[] players = this.getPlayers();
        for(int i = 0; i< players.length; i++){
            rehearsals[i] = 0;
            this.setPlayer(i, 0);
        }

    }

}