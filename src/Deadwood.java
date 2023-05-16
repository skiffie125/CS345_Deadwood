import java.util.Scanner;
public class Deadwood {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("How many players?");
        int numPlayers = s.nextInt();
        ProgressManager game = new ProgressManager(null, null, null);
        game.setUpGame(numPlayers);
        game.setUpDay(numPlayers);
        int currentPlayer = 0;
        System.out.println("It is Player"+ currentPlayer+ "'s turn");
        while(game.takeATurn(game.getPlayer(currentPlayer))){
            currentPlayer++;
            if(currentPlayer == numPlayers){
                currentPlayer = 0;
            }
            System.out.println();
            System.out.println("It is Player"+ currentPlayer+ "'s turn");
        }
        // for (int i = 0; i < game.getPlayers().length; i++) {
        //     Player current = game.getPlayer(i);
        //     boolean turn = true;
        // }
    }
}