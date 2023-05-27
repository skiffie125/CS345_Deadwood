import java.util.Scanner;

import javax.swing.JOptionPane;
public class Deadwood {
    public static void main(String[] args) {
        // Scanner s = new Scanner(System.in);
        // System.out.println("How many players?");
        // int numPlayers = s.nextInt();
        // ProgressManager game = new ProgressManager(null, null, null);
        // game.setUpGame(numPlayers);
        // game.setUpDay(numPlayers);
        // int currentPlayer = 0;
        // System.out.println("It is Player"+ currentPlayer+ "'s turn");
        // //GUI view = new GUI();
        // while(game.takeATurn(game.getPlayer(currentPlayer))){
        //     currentPlayer++;
        //     if(currentPlayer == numPlayers){
        //         currentPlayer = 0;
        //     }
        //     System.out.println();
        //     System.out.println("It is Player"+ currentPlayer+ "'s turn");
        // }
        
        // GUI Testing
        //GUI gui = new GUI();
        // Take input from the user about number of players
        ProgressManager game = new ProgressManager(null, null, null);
        GUI board = new GUI(game);
        game.setGui(board);
        board.setVisible(true);
        int numPlayers = Integer.parseInt(JOptionPane.showInputDialog(board, "How many players?"));
        game.setUpGame(numPlayers);
    }
}