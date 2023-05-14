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

   
    //Moves from current location to Location given in parameters 
    public boolean move(Location location, LocationManager lm){
        boolean result = false;
        if (lm.checkMove(currentLocation, location, id)){
            currentLocation.setPlayer(id,0);
            //to have them all be the same objects we grab them from location manager 
            Scene[] scenes = lm.getBoard().getScenes();
            for (int i = 0; i < scenes.length; i++) {
                if (location.getName().equals(scenes[i].getName())) {
                    location = scenes[i];
                    
                }
            }
            if (location.getName().equals(lm.getBoard().getCastingOffice().getName())) {
                    location = lm.getBoard().getCastingOffice();
                    
            }
            if (location.getName().equals(lm.getBoard().getTrailer().getName())) {
                    location = lm.getBoard().getTrailer();
                    
            }
            location.setPlayer(id,1);
            currentLocation = location;
        }
        return result;
    }
    // Upgrade to new rank 
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
        return result;
    }
    
    //Take role r 
    public boolean takeRole(Scene s, Role r, LocationManager lm, Player p){
        boolean result = false;
        if (lm.checkRoleStatus(s,r)) {
            if (r.getMinRank() <= rank) {
                result = true;
                r.setPlayer(p);
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

    //add a rehearsal to Scene s
    public boolean rehearse(Scene s, LocationManager lm){
        boolean result = false;
        if(lm.checkLocation(s, id)){
            result = true;
            s.addRehearsal(id);
        }
        return result;
    }

    //act on scene s with role r
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
            System.out.println("Roll: "+ roll + " Budget: " + s.getCard().getBudget());
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
        for (int i = 0; i < n -1; i++){
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