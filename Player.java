
public class Player {
    private Role currentRole;
    private Location currentLocation;
    private int rank;
    private int ID;

    //Constructors
    public Player(){

    }
    //Getters and Setters For Fields 
    public void setRole(Role role){
        currentRole = role;
    }
    public Role getRole(){
        return currentRole;
    }
    public void setLocation (Location location){
        currentLocation = location;
    }
    public Location getLocation(){
        return currentLocation;
    }
    public void setRank(int newRank){
        rank = newRank;
    }
    public int getRank(){
        return rank;
    }
    public void setID(int newID){
        ID = newID;
    }
    public int getID(){
        return ID;
    }


    //actual class methods 
    public void takeATurn(){
        
    }

    public boolean move(Location location){
        return false;
    }

    public boolean upgrade(){
        return false;
    }

    public boolean takeRole(Scene s, Role r){
        return false;
    }

    public void rehearse(Scene s){

    }

    public void Work(){

    }

    public boolean Act(Scene S, Role r){
        return false;
    }

    public void WrapScene(Scene s){

    }

    public void EndGame(){

    }

}

class Dice{
    public Dice(){

    }
    public int roll(){
        return 0;
    }
    public int[] rollN(int n){
        int[] a = new int[n];
        return a;
    }
}