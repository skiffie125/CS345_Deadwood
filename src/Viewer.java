import java.util.Scanner;
public class Viewer {
    // Command constants
    static final String[] commands = {"Current", "Locate all", "Move", "Work", "Upgrade",
        "Rehearse", "Act", "End turn", "End game"};
    
    // Variable Fields
    //private String buffer; <- with the current implementation of playText this can be eliminated
    private ProgressManager game;

    // Methods
    // public Viewer(String buffer, ProgressManager game) {
    public Viewer(ProgressManager game) {
        this.game = game;
    }
    public Viewer() {
    }

    // Do we want this just to get the command or should we have it act on said commands too?
    // Evidently i vote act on them
    // But its an easy enough refactor
    // -JB
    public void playText() {
        // If we do go the "this acts on the commands too" route
        // Buffer can be eliminated as a field
        String result = null;
        Scanner s = new Scanner(System.in);
        Player[] players = game.getPlayers();
        while (true) {
            result = s.nextLine();
            //buffer += result;
            if (result.equals("End game")) {
                System.out.println("Game ended!");
                break;
            }
            switch (result) {
                case "Current":
                    System.out.println("Current!");
                    break;
                case "Locate all":
                    int numPlayers = players.length;
                    for (int i = 0; i < numPlayers; i++) {
                        System.out.println("Current player: Player " + players[i].getId());
                        System.out.println("Player location: " + players[i].getLocation());
                    }
                    break;
                case "Move":
                    System.out.println("Move");
                    break;
                case "Work":
                    System.out.println("Work");
                    break;
                case "Upgrade":
                    System.out.println("Upgrade");
                    break;
                case "Rehearse":
                    System.out.println("Rehearse");
                    break;
                case "Act":
                    System.out.println("Act");
                    break;
                case "End turn":
                    System.out.println("End turn");
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
                }
        }
        s.close();
        return;
    }


    public String getValidComand() {
        String result = null;
        Scanner s = new Scanner(System.in);
        result = s.nextLine();
        switch (result){
            case "Current":
                break;
            case "Locate all":
                break;
            case "Move":
                break;
            case "Work":
                break;
            case "Upgrade":
                break;
            case "Rehearse":
                break;
            case "Act":
                break;
            case "End turn":
                break;
            case "End game":
                break;
            default:
                System.out.println("Invalid command, please try again");
                result = getValidComand();
                break;
        }
        s.close();
        return result;

    }
    public int getParameter(int max){
        int result;
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        Scanner s1 = new Scanner(input);
        if(!s1.hasNextInt()){
            System.out.println("Invalid option, please try again");
            result = getParameter(max);
        } else{
            result = s1.nextInt();
            if (result > max || result < 0){
                System.out.println("Invalid option, please try again");
                result = getParameter(max);
            }
        }
        return result;
    }
        
    public String showOutput() {
        return "";
    }
}