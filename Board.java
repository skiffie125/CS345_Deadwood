import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import javax.swing.text.View;

public class Board {
    // Fields
    private Scene[] scenes;
    private Location trailer;
    private Location castingOffice;
    private int cardsLeft;
    private Card[] deck;

    // Methods
    public Board(Scene[] scenes, Location trailer, Location castingOffice,
    int cardsLeft, Card[] deck) {
        this.scenes = scenes;
        this.trailer = trailer;
        this.castingOffice = castingOffice;
        this. cardsLeft = cardsLeft;
        this.deck = deck;
    }

    public void setScene(int index, Scene scene) {
        return;
    }

    // Returns scene at specified index
    public Scene getScene(int index) {
        return this.scenes[index];
    }

    public void setTrailer(Location loc) {
        return;
    }

    public Location getTrailer() {
        return this.trailer;
    }

    public void setCastingOffice(Location loc) {
        return;
    }

    public Location getCastingOffice() {
        return this.castingOffice;
    }

    public void setCardsLeft(int amount) {
        return;
    }

    public int getCardsLeft() {
        return this.cardsLeft;
    }
    
    public void setCards(Card[] cards) {
        return;
    }

    public Card[] getCards() {
        return this.deck;
    }

    // Get first card out of deck
    public Card pop() {
        return null;
    }

    // Reset deck
    public void reset() {
        return;
    }
}

// Inner class for XML parsing functionality
class ParseXML {
    // Generate document from XML file
    public Document getDocFromFile(String filename) 
    throws ParserConfigurationException {
        return null;
    }

    // Reads data from document and stores it 
    public void readData(Document d) {

    }
}