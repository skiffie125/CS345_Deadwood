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

    // Reset board for new game
    public void reset() {
        return;
    }
}