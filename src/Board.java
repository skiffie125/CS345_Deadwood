import java.util.Random;
import java.lang.Math;

public class Board {
    // Fields
    private Scene[] scenes;
    private Location trailer;
    private Location castingOffice;
    private int cardsLeft;
    private Card[] deck;
    //private Location[] locations;

    public Board(Scene[] scenes, Location trailer, Location castingOffice, Location[] locations,
     Card[] deck) {
        this.scenes = scenes;
        this.trailer = trailer;
        this.castingOffice = castingOffice;
        this. cardsLeft = 40;
        this.deck = deck;
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
        cardsLeft--;
        if (cardsLeft >= 0){
            deck[cardIndex] = deck[cardsLeft];
            deck[cardsLeft] = temp;
        } else{
            reset();
        }
        
        return temp;
    }

    // Reset deck
    public void reset() {
        cardsLeft = 40;
        return;
    }
}