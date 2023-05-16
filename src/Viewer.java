import java.util.Scanner;
public class Viewer {
    // Command constants
    static final String[] commands = {"Current", "Locate all", "Move", "Work", "Upgrade",
        "Rehearse", "Act", "End turn", "End game"};

    static Scanner s = new Scanner(System.in);

    public Viewer() {
    }

    public String getValidComand() {
        String result = null;
        //Scanner s = new Scanner(System.in);
        result = s.nextLine();
        //while (s.hasNextLine()) {
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
                case "Take role":
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
        return result;

    }
    public int getParameter(int max){
        int result;
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
        s1.close();
        return result;
    }
        
    public String showOutput() {
        return "";
    }
}