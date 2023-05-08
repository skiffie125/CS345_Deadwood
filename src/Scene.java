public class Scene extends Location{
    private String name;
    private Location[] neighbors;
    private int[] players;
    private int shotCountersLeft;
    private Card card;
    private boolean faceUp;
    private Role[] offCardRoles;
    private int[] rehearsals;

    //Constructors 
    public Scene(){

    }
    public Scene(String newName){
        
    }

    //Getters and setters 
    public void setShotCounters(int n){
        shotCountersLeft = n;
    }
    public void removeAShotCounter(){
        shotCountersLeft--;
    }
    public int getShotCountersLeft(){
        return shotCountersLeft;
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

    //class methods

    public void WrapScene(){

    }
    public void reset(){
        
    }

}