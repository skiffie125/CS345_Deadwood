public class Scene extends Location{
    private String name;
    private Location[] neighbors;
    private int[] players;
    private int shotCountersMax;
    private int shotCountersLeft;
    private Card card;
    private boolean faceUp;
    private Role[] offCardRoles;
    private int[] rehearsals;
    private int[] dimensions;

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
    public void setDimensions(int[] dim) {
        dimensions = dim;
    }
    public int[] getDimensions() {
        return dimensions;
    }

    //class methods
    public void reset(Card newCard){
        card = newCard;
        shotCountersLeft = shotCountersMax; 
        faceUp = false;

    }

}