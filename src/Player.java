import java.util.Random;
import java.util.Scanner;

public class Player {
    private Role currentRole;
    private Location currentLocation;
    private int rank;
    private int id;
    private int dollars;
    private int credits;

    //Constructors
    public Player(int rank, int id) {
        this.rank = rank;
        this.id = id;
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
    public void setID(int newId){
        id = newId;
    }
    public int getId(){
        return id;
    }
    public void setDollars(int amount) {
        dollars = amount;
    }
    public int getDollars() {
        return dollars;
    }
    public void setCredits(int amount) {
        credits = amount;
    }
    public int getCredits() {
        return credits;
    }

    //actual class methods 

    //Method to take a turn
    //Preconditions: It is the players turn
    //Postcondition: Turn is over 
    public void takeATurn(){
        /*
        take user input from choices: 
        Move, Upgrade, Take a role, Work, End Turn
        
        if Move:
            go to move method 
            take user input from choices: 
            End Turn, Upgrade, Take a role
            go to coresponding method or end turn

        if Upgrade:
            get desired rank  
            go to upgrade method
            take user input from choices:
            Move, End Turn
            go to coresponding method or end turn
        
        if Take a role:
            show roles on current scence
            take user input for which role 
            go to take role method

        if Work
            go work method 
        if end turn:
            termintate
         */
         String result = null;
         Scanner s = new Scanner(System.in);
         System.out.println("Move, Upgrade, Take role, Work, or End turn?");
         while (true) {
            result = s.nextLine();
            if (result.equals("End turn")) {
                System.out.println("Turn ended");
                break;
            }
            switch (result) {
                case "Move":
                    System.out.println("Enter destination: ");
                    String destName = s.nextLine();
                    // Need to get location from string
                    // while(!move()) {
                    //     System.out.println("Invalid destination. New destination: ");
                    //     dest = s.nextLine();
                    // }
                    // currentLocation = dest;
                    break;
                case "Upgrade":
                    break;
                case "Take role":
                    break;
                case "Work":
                    break;
            }
         }
    }

    public boolean move(Location location){
        // check current location is accurate
        // check location given is a neighbor 
        // if both true move and return true
        // else stay and return false
        Location[] neighbors = currentLocation.getNeighbors();
        int len = neighbors.length;
        for (int i = 0; i < len; i++) {
            if (neighbors[i].getName().equals(location.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean upgrade(int newRank){
        // check location is casting office
        //check they have enough money for desired rank
        // if both true
            // upgrade rank and subtract money return true
        // else 
            // don't upgrade and keep money, return false
        return false;
    }

    public boolean takeRole(Scene s, Role r){
        //check if they are at scene
        //check if the role is open
        //check if the role is within their rank 
        //if all true, take role 
            //end turn and return true
        //else 
            //end turn and return false 
            if (currentRole != null) {
                System.out.println("Invalid: Player already has a role");
                return false;
            }
        return false;
    }

    public void rehearse(Scene s){
        // check they are the scence 
        // add a resheal for that player to the scene
    }

    public void work(){
        //check that they are on a scene 
        //get user input if they want to act or rehearse
        // if rehearse 
            //go to rehearse method
        // if act 
            //check they have a current role
            //go to act method
            //check if they wrapped scene
                //if true, go to wrap scene
                //else end turn
    }

    public boolean act(Scene S, Role r){
        // check have taken that role
        // if true
            // get bonuses from reheasals
            // roll dice 
            // if successful
                // give money
                //check if you wrapped scence
                    // if so go to wrap scene
                //return true
            //else
                // give any money and return false
        return false;
    }

    public void wrapScene(Scene s){
        //check if all shot counters are removed
        //get all on card players
        // if no on card players, no bonuses
        // else 
            // rolln(budget)
            //distribute dollars based on rank
            // give off card bonuses 
        //check if that was the 2nd to last scene
        // if it was end game
        // else end turn 
    }

    public void endGame(){

    }

}

class Dice{
    public Dice(){

    }
    //roll a dice 
    public int roll(){
        Random r = new Random();
        return r.nextInt(6)+1;
    }
    
    //roll n dice and then put them in order, largest first 
    public int[] rollN(int n){
        int[] a = new int[n];
         Random r = new Random();
        //fill with random dice rolls
        for (int i = 0; i < n; i++){
            a[i]= r.nextInt(6)+1;
        }
        //sorting
        for (int i = 0; i < n; i++){
            int value = a[i+1];
            int currentIndex = i;
            while(currentIndex >= 0 && value>a[currentIndex]){
                swap(a, currentIndex, currentIndex + 1 );
                currentIndex--;
            }
        }
        
        return a;
    }
    //swap method for sorting
    private void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}