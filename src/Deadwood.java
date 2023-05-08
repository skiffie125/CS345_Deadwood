import java.util.Scanner;
public class Deadwood {
    public static Player[] generatePlayers(int amount) {
        Player[] players = new Player[amount];
        int startRank = 1;
        if (amount >= 7) {
            startRank = 2;
        }
        for (int i = 0; i < amount; i++) {
            players[i] = new Player(startRank, i);
        }
        return players;
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("How many players?");
        int numPlayers = s.nextInt();
        while (true) {
            if (numPlayers < 2) {
                System.out.println("Invalid number of players");
            } else {
                break;
            }
        }
        Player[] players = generatePlayers(numPlayers);
        for (int i = 0; i < numPlayers; i++) {
            System.out.println("Rank: " + players[i].getRank());
            System.out.println("ID: " + players[i].getId());
        }
    }
}