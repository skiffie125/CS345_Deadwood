import java.util.Random;

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

   

    public boolean move(Location location, LocationManager lm){
        // check current location is accurate
        // check location given is a neighbor 
        // if both true move and return true
        // else stay and return false
        /*Location[] neighbors = currentLocation.getNeighbors();
        int len = neighbors.length;
        
        for (int i = 0; i < len; i++) {
            if (neighbors[i].getName().equals(location.getName())) {
                return true;
            }
        }
        currentLocation = location;
        */
        boolean result = false;
        if(location.getPlayers() == null){
            System.out.println("location players is null, this is bad");
        } else{
            System.out.println("location players is not null, this is good");
        }
        if (lm.checkMove(currentLocation, location, id)){
            currentLocation.setPlayer(id,0);
            Scene[] scenes = lm.getBoard().getScenes();
            //Scene dest = null;
            for (int i = 0; i < scenes.length; i++) {
                if (location.getName().equals(scenes[i].getName())) {
                    location = scenes[i];
                    
                }
            }
            //location.setPlayer(id,1);
            location.setPlayer(id,1);
            currentLocation = location;
        }
        return result;
    }

    public boolean upgrade(int newRank, Bank b, LocationManager lm){
        int dollars;
        int credits;
        Location castingOffice = lm.getBoard().getCastingOffice();
        boolean result = false;
        if(lm.checkLocation(castingOffice, id)){
            switch(newRank){
                case 2:
                    dollars = 4;
                    credits = 5;
                    break;
                case 3:
                    dollars = 10;
                    credits = 10;
                    break;
                case 4:
                    dollars = 18;
                    credits = 15;
                    break;
                case 5:
                    dollars = 28;
                    credits = 20;
                    break;
                case 6:
                    dollars = 40;
                    credits = 25;
                    break;
                default:
                    System.out.print("Sorry Not a Valid Rank");
                    dollars = 0;
                    credits = 0;
                    newRank = rank;
                    break; 
            }
            if (b.verifyWithdraw(id,dollars,credits)){
                b.pay(id,dollars,credits);
                rank=newRank;
                result = true;
            } else{
                 System.out.println("Insuffient funds");          
            }
        } else {
            System.out.println("Wrong Location");
        }
        
        // check location is casting office
        //check they have enough money for desired rank
        // if both true
            // upgrade rank and subtract money return true
        // else 
            // don't upgrade and keep money, return false
        return result;
    }

    public boolean takeRole(Scene s, Role r, LocationManager lm, Player p){
        //check if they are at scene
        //check if the role is open
        //check if the role is within their rank 
        //if all true, take role 
            //end turn and return true
        //else 
            //end turn and return false 
        boolean result = false;
        if (lm.checkRoleStatus(s,r)) {
            if (r.getMinRank() <= rank) {
                result = true;
                take(r);
            } else {
                System.out.println("Rank too low");
            }
        }
        return result;
    }

    public void take(Role r) {
        currentRole = r;
    }

    public boolean rehearse(Scene s, LocationManager lm){
        boolean result = false;
        if(lm.checkLocation(s, id)){
            result = true;
            s.addRehearsal(id);
        }
        return result;
        // check they are the scence 
        // add a resheal for that player to the scene
    }

    public boolean act(Scene s, Role r, Player p, Bank b){
        boolean result = false;
        boolean onCardRole = true;
        Role[] offCardRoles =  s.getOffCardRoles();
        for(int i = 0; i< offCardRoles.length; i++){
            if(r == offCardRoles[i]){
                onCardRole = false;
            }
        }
        if(r.getPlayer() == p){
            Dice d = new Dice();
            int roll = d.roll();
            roll += s.getRehearsal(id);
           // need to figure out where budget is
            if(roll >= s.getCard().getBudget()){
                s.removeAShotCounter();
                if(onCardRole){
                    b.add(id, 0, 2);
                } else{
                    b.add(id, 1, 1);
                }
                result = true;
            } else {
                if(!onCardRole){
                    b.add(id, 1, 0);
                }
            }
        }
        if (result){
            System.out.println("You did a great job, you succeded");
        } else{
            System.out.println("Things didn't go your way today, you failed");
        }
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
        return result;
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