import java.util.Scanner;
import org.w3c.dom.Document;
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
        // XML parsing
        ParseXML parser = new ParseXML();
        Board board = null;
        // parse board
        try {
            Document doc = parser.getDocFromFile("board.xml");
            board = parser.readBoard(doc);
        } catch (Exception e) {
            System.out.println("Board parsing error: " + e);
        }
        // parse card
        try {
            Document doc = parser.getDocFromFile("cards.xml");
            Card[] deck = parser.readCards(doc);
        } catch (Exception e) {
            System.out.println("Card parsing error: " + e);
        }

        }
        // Document doc = parser.getDocFromFile("board.xml");
        // Board board = parser.readBoard(doc);
        // Set up game components
        // card
        // locations
        // location manager
        //ProgressManager game = new ProgressManager(players, locMan, bank);
        // roles
        // scenes
        //Viewer view = new Viewer(game);
        
}