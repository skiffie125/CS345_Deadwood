public class Viewer {
    // Command constants
    static final String[] commands = {"Current", "Locate all", "Move", "Work", "Upgrade",
        "Rehearse", "Act", "End turn", "End game"};
    
    // Variable Fields
    private String buffer;
    private ProgressManager game;

    // Methods
    public Viewer(String buffer, ProgressManager game) {
        // Need to decide what form the commands will take
        // but they dont need to be supplied by constructor
        this.buffer = buffer;
        this.game = game;
    }

    public void getCommand() {
        return;
    }

    public String showOutput() {
        return "";
    }
}