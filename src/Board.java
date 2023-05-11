import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import javax.swing.text.View;
import java.util.Random;

public class Board {
    // Fields
    private Scene[] scenes;
    private Location trailer;
    private Location castingOffice;
    private int cardsLeft;
    private Card[] deck;
    private Location[] locations;

    public Board(Scene[] scenes, Location trailer, Location castingOffice, Location[] locations,
    int cardsLeft, Card[] deck) {
        this.scenes = scenes;
        this.trailer = trailer;
        this.castingOffice = castingOffice;
        this. cardsLeft = cardsLeft;
        this.deck = deck;
        this.locations = locations;
    }

    public void setScene(int index, Scene scene) {
        this.scenes[index]= scene;
        return;
    }

    // Returns scene at specified index
    public Scene getScene(int index) {
        return this.scenes[index];
    }

    public Scene[] getScenes() {
        return this.scenes;
    }

    public void setTrailer(Location loc) {
        this.trailer = loc;
        return;
    }

    public Location getTrailer() {
        return this.trailer;
    }

    public void setCastingOffice(Location loc) {
        this.castingOffice = loc;
        return;
    }

    public Location getCastingOffice() {
        return this.castingOffice;
    }

    public void setCardsLeft(int amount) {
        this.cardsLeft = amount;
        return;
    }

    public int getCardsLeft() {
        return this.cardsLeft;
    }
    
    public void setCards(Card[] cards) {
        this.deck = cards;
        return;
    }

    public Card[] getCards() {
        return this.deck;
    }


    // Get a random card out of deck
    public Card pop() {
        Random r = new Random();
        int cardIndex =  r.nextInt(cardsLeft);
        Card temp = deck[cardIndex];
        deck[cardIndex] = deck[cardsLeft -1];
        deck[cardsLeft -1] = temp;
        return temp;
    }

    // Reset deck
    public void reset() {
        cardsLeft = 40;
        return;
    }
}

// Moving this to its own class so we dont have to instantiate a board to use the functionality
// Plus board needs some info from this in order to be created
// Inner class for XML parsing functionality
// class ParseXML {
//     // Generate document from XML files
//     //fully copy pasted from from given files

//     public Document getDocFromFile(String filename) 
//     throws ParserConfigurationException {
//         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//         DocumentBuilder db = dbf.newDocumentBuilder();
//         Document doc = null;
//         try{
//             doc = db.parse(filename);
//         } catch (Exception ex){
//             System.out.println("XML parse failure");
//             ex.printStackTrace();
//         }
//         return doc;
//     }

//     // Reads data from document and stores it 

//     //No idea if this works

//     // Commented out to do viewer testing. Also no clue if it works
//     public void readCardData(Document d, Card[] deck) {
//         // Element root = d.getDocumentElement();
//         // NodeList cards = root.getElementsByTagName("card");
//         // for (int i=0; i<cards.getLength();i++){
//         //     Node card = card.item(i);
//         //     String name = card.getAttributes().getNamedItem("name").getNodeValue();
//         //     deck[i].setName(name);
//         //     String img = card.getAttributes().getNamedItem("img").getNodeValue();
//         //     deck[i].setName(img);
//         //     int budget = card.getAttributes().getNamedItem("budget").getNodeValue();
//         //     deck[i].setBudget(budget);

//         //     NodeList children = card.getChildNodes();
//         //     for (int j=0; j< children.getLength(); j++){
//         //         Node sub = children.item(j);

//         //         //need to fill the rest of this in 
//         //     }

//         // }


//     }

//     public void readBoardData(Document d, Scene[] scenes, Location castingOffice, Location trailer) {

//     }
// }