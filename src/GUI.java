import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.JOptionPane;

/*
 * This is a very long file, our appologies
 * It contains all of our GUI Methods
 */



public class GUI extends JFrame {
    
    //Fields 
    // JLabels
    JLabel boardlabel;
    JLabel[] playerPieces;
    JLabel mLabel;
    JLabel currentPlayersDisplay;
    JLabel[] playersDisplay;
    JLabel[] cardsAtScenes;
    JLabel[][] shotCounters;

    //JButtons
    JButton bWork;
    JButton bAct;
    JButton bRehearse;
    JButton bMove;
    JButton bUpgrade;
    JButton bRole;
    JButton bEndTurn;

    // JLayered Pane
    JLayeredPane bPane;

    //Size refrences
    int belowBoard;
    int rightBoard;
    int diceHeight;
    int diceWidth;

    // JComboBox
    JComboBox<String> cMove;
    JComboBox<String> cRole;
    JComboBox<Integer> cUpgrade;

    JTextArea output;

    //CBox action Listeners
    boardMouseListener lMove;
    boardMouseListener lRole;
    boardMouseListener lUpgrade;
    
    //Reference to the game
    ProgressManager game;
    
    
    // Constructor
    //This sets up the basics of the gui
    public GUI(ProgressManager game) {
        // Set the title of the JFrame
        super("Deadwood");
        super.setLayout(null);
        // Set the exit option for the JFrame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.game = game;
        // Create the JLayeredPane to hold the display, cards, dice and buttons
        bPane = getLayeredPane();
        bPane.setLayout(null);
        // Create the deadwood board
        boardlabel = new JLabel();
        ImageIcon icon = new ImageIcon("images/board.jpg");
        boardlabel.setIcon(icon);
        boardlabel.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
        // Add the board to the lowest layer
        bPane.add(boardlabel, bPane.DEFAULT_LAYER);
        
        // Set the size of the GUI
        setSize(icon.getIconWidth()+250,icon.getIconHeight() + 200);
        bPane.setSize(icon.getIconWidth()+250,icon.getIconHeight() + 200);
        Color background = new Color(254, 244, 235);
        bPane.setOpaque(true);
        super.getContentPane().setBackground(background);

        // Create the Menu for action buttons
        belowBoard = icon.getIconHeight();
        rightBoard = icon.getIconWidth();
        mLabel = new JLabel("MENU");
        mLabel.setBounds(icon.getIconWidth()+40,0,120,20);
        bPane.add(mLabel,2);

        background = new Color(197, 220, 164);

        // Create Action buttons
        bEndTurn = new JButton("END TURN");
        bEndTurn.setBackground(background);
        bEndTurn.setBounds(icon.getIconWidth()+30, 60,120, 20);
        bEndTurn.addMouseListener(new boardMouseListener());
        bEndTurn.setVisible(true);
        
        bWork = new JButton("WORK");
        bWork.setBackground(background);
        bWork.setBounds(icon.getIconWidth()+30, 90,120, 20);
        bWork.addMouseListener(new boardMouseListener());
        bWork.setVisible(true);
        
        bMove = new JButton("MOVE");
        bMove.setBackground(background);
        bMove.setBounds(icon.getIconWidth()+30,120,120, 20);
        bMove.addMouseListener(new boardMouseListener());
        bMove.setVisible(true);

        bUpgrade = new JButton("UPGRADE");
        bUpgrade.setBackground(background);
        bUpgrade.setBounds(icon.getIconWidth()+30,150,120, 20);
        bUpgrade.addMouseListener(new boardMouseListener());
        bUpgrade.setVisible(true);

        bRole = new JButton("TAKE ROLE");
        bRole.setBackground(background);
        bRole.setBounds(icon.getIconWidth()+30,180,120, 20);
        bRole.addMouseListener(new boardMouseListener());
        bRole.setVisible(true);

        bRehearse = new JButton("REHEARSE");
        bRehearse.setBackground(background);
        bRehearse.setBounds(icon.getIconWidth()+30,210,120, 20);
        bRehearse.addMouseListener(new boardMouseListener());
        bRehearse.setVisible(false);

        bAct = new JButton("ACT");
        bAct.setBackground(background);
        bAct.setBounds(icon.getIconWidth()+30,240,120, 20);
        bAct.addMouseListener(new boardMouseListener());
        bAct.setVisible(false);
        
        // Place the action buttons in the top layer
        bPane.add(bWork,2);
        bPane.add(bRehearse,2);
        bPane.add(bMove, 2);
        bPane.add(bEndTurn, 2);
        bPane.add(bAct, 2);
        bPane.add(bRole, 2);
        bPane.add(bUpgrade, 2);


        // Conditionally visible comboBoxes
        cMove = new JComboBox<String>();
        cMove.setBounds(icon.getIconWidth()+ 30, 210, 120, 20);
        cMove.setBackground(background);
        bPane.add(cMove, 2);
        cMove.setVisible(false);
        lMove = new boardMouseListener();
        cMove.addActionListener(lMove);

        cRole = new JComboBox<String>();
        cRole.setBounds(icon.getIconWidth() + 30, 210, 120, 20);
        cRole.setBackground(background);
        bPane.add(cRole, 2);
        cRole.setVisible(false);
        lRole = new boardMouseListener();
        cRole.addActionListener(lRole);

        cUpgrade = new JComboBox<Integer>();
        cUpgrade.setBounds(icon.getIconWidth() + 30, 210, 120, 20);
        cUpgrade.setBackground(background);
        bPane.add(cUpgrade, 2);
        cUpgrade.setVisible(false);
        lUpgrade = new boardMouseListener();
        cUpgrade.addActionListener(lUpgrade);

        // Game state output
        JLabel oLabel = new JLabel("GAME OUTPUT");
        oLabel.setBounds(icon.getIconWidth()+40,580,400,20);
        output = new JTextArea(100, 100);
        bPane.setVisible(true);
        bPane.add(oLabel, 2);
        output.setBounds(icon.getIconWidth() + 60, 600, 180, 20);
        output.setVisible(true);
        bPane.add(output, 2);
        bPane.validate();
    }


    //This is a method that creates the player displays and pieces and is called in progress manager after the players are created

    public void createPlayerLabels(Player[] players){
        playersDisplay = new JLabel[players.length];
        playerPieces = new JLabel[players.length];
        for(int i = 0; i < playersDisplay.length; i++){

            //Player displays (the info shown)
            playersDisplay[i] = new JLabel("<html>Player " + players[i].getName()+
            "<br/>Rank " + players[i].getRank()
            + "<br/>Cash " + game.getBank().getDollars(players[i].getId())
            + "<br/>Credit " + game.getBank().getCredits(players[i].getId())
            + "<html>");
            playersDisplay[i].setBounds( i* 170 +5, belowBoard -50, 170,200);
            bPane.validate();
            playersDisplay[i].setVisible(true);
            bPane.add(playersDisplay[i], bPane.MODAL_LAYER);

            //Player pieces (dice)
            String s = "images/dice/" + players[i].getName().toLowerCase().charAt(0)+ players[i].getRank()+".png";
            ImageIcon pIcon = new ImageIcon(s);
            diceHeight = pIcon.getIconHeight();
            diceWidth = pIcon.getIconWidth(); 
            playerPieces[i] = new JLabel();
            playerPieces[i].setIcon(pIcon);
            playerPieces[i].setBounds(rightBoard -150 +((i)%2)*50 ,255 + ((i)/2)*50 ,pIcon.getIconWidth(),pIcon.getIconHeight());
            playerPieces[i].setVisible(true);
            bPane.add(playerPieces[i], bPane.MODAL_LAYER);
        }
        //adds a display near menu to tell who's turn it currently is 
        currentPlayersDisplay = new JLabel("Player "+ players[0].getName());
        currentPlayersDisplay.setBounds(rightBoard+40,30,100,20);
        bPane.add(currentPlayersDisplay,2);
        bPane.validate();
    }

    //Another method called by progress manager 
    //Creates shot counters and card labels 
    public void setUpDay(){
        cardsAtScenes = new JLabel[game.getLocationManager().getBoard().getScenes().length];
        shotCounters = new JLabel[game.getLocationManager().getBoard().getScenes().length][];
        for(int i = 0; i< cardsAtScenes.length; i++){
            
            //Card Icons
            ImageIcon pIcon = new ImageIcon("images/CardBack-small.jpg");
            cardsAtScenes[i] = new JLabel();
            cardsAtScenes[i].setIcon(pIcon);
            cardsAtScenes[i].setBounds(game.getLocationManager().getBoard().getScenes()[i].getDimensions()[0],
            game.getLocationManager().getBoard().getScenes()[i].getDimensions()[1], pIcon.getIconWidth(),pIcon.getIconHeight());
            cardsAtScenes[i].setVisible(true);
            bPane.add(cardsAtScenes[i], bPane.PALETTE_LAYER);

            //Shot counters 
            shotCounters[i] = new JLabel[game.getLocationManager().getBoard().getScenes()[i].getShotCountersMax()];
            System.out.println(game.getLocationManager().getBoard().getScenes()[i].getShotCountersMax());
            for(int j =0; j < game.getLocationManager().getBoard().getScenes()[i].getShotCountersMax(); j++){
                ImageIcon sIcon = new ImageIcon("images/shot.png");
                shotCounters[i][j] = new JLabel();
                shotCounters[i][j].setIcon(sIcon);
                int[][] shotPlaces = game.getLocationManager().getBoard().getScenes()[i].getShotDimensions();
                if (shotPlaces != null){
                    if (shotPlaces[j] != null){
                        shotCounters[i][j].setBounds(shotPlaces[j][0], shotPlaces[j][1],sIcon.getIconWidth(),sIcon.getIconHeight());
                        shotCounters[i][j].setVisible(true);
                        bPane.add(shotCounters[i][j], bPane.PALETTE_LAYER);
                    } else {
                        if (j == 1) {
                            //Set saloon 1
                            shotCounters[i][j].setBounds(626, 216, 47, 47);
                            shotCounters[i][j].setVisible(true);
                            bPane.add(shotCounters[i][j], bPane.PALETTE_LAYER);
                        } else {
                            //set saloon 2
                            shotCounters[i][j].setBounds(679, 216, 47, 47);
                            shotCounters[i][j].setVisible(true);
                            bPane.add(shotCounters[i][j], bPane.PALETTE_LAYER);
                        }
                    }
                }
            }
        } 
        bPane.validate();
    }

    //This updates the player display to show new data 
    public void updatePlayerDisplay(int p) {
        Player[] players = game.getPlayers();
        Scene[] scenes = game.getLocationManager().getBoard().getScenes();
        Scene curScene = null;
        for (int i = 0; i < scenes.length; i++) {
            if (players[p].getLocation().getName().equals(scenes[i].getName())) {
                curScene = scenes[i];
            }
        }
        if (curScene != null) {
            bPane.remove(playersDisplay[p]);
            playersDisplay[p] = new JLabel("<html>Player " + players[p].getName()+
            "<br/>Rank " + players[p].getRank()
            + "<br/>Cash " + game.getBank().getDollars(players[p].getId())
            + "<br/>Credit " + game.getBank().getCredits(players[p].getId())
            + "<br/>Rehearsal tokens " +curScene.getRehearsal(p) 
            + "<html>");
            playersDisplay[p].setBounds( p* 170 +5, belowBoard -50, 170,200);
            playersDisplay[p].setVisible(true);
            bPane.add(playersDisplay[p],3);
        } else {
            bPane.remove(playersDisplay[p]);
            playersDisplay[p] = new JLabel("<html>Player " + players[p].getName()+
            "<br/>Rank " + players[p].getRank()
            + "<br/>Cash " + game.getBank().getDollars(players[p].getId())
            + "<br/>Credit " + game.getBank().getCredits(players[p].getId())
            + "<html>");
            playersDisplay[p].setBounds( p* 170 +5, belowBoard -50, 170,200);
            playersDisplay[p].setVisible(true);
            bPane.add(playersDisplay[p],3);
        }
    }

    //This method is called when a player enters the location for the first time
    //It flips the card and shows the info
    public void uncoverCard(Scene s){
        System.out.println("Uncovering card");
        ImageIcon pIcon = new ImageIcon("images/cards/"+s.getCard().getImg());
        cardsAtScenes[s.getIndex()].setIcon(pIcon);
    }

    // This shows another shot counter
    public void markOffShotCounter(Scene s){
        shotCounters[s.getIndex()][s.getShotCountersLeft()].setVisible(true);
    }

    //This method is called when a day ends and we want to reset 
    public void resetDay(){
        ImageIcon pIcon = new ImageIcon("images/CardBack-small.jpg");
        for(int i = 0; i< cardsAtScenes.length; i++){
            cardsAtScenes[i].setIcon(pIcon);
            for(int j =0; j < game.getLocationManager().getBoard().getScenes()[i].getShotCountersMax(); j++){
                shotCounters[i][j].setVisible(false);
            }
        }
        for( int i = 0; i < game.getPlayers().length; i++){
            playerPieces[i].setVisible(false);
            playerPieces[i].setLocation(rightBoard -150 +((i)%2)*50 ,255 + ((i)/2)*50);
            playerPieces[i].validate();
            playerPieces[i].setVisible(true);
        }
    }

    //This methos reports the winners and losers of the game 
    public void reportOutcome( String s){
        JOptionPane.showMessageDialog(bPane, "<html> Game Ended <br/>" + s + "<html>");
    }


    //This method updates the combo boks that shows te current neighbors
    public void updateNeighborsBox(Location[] contents) {
        cMove.removeAllItems();
        for (int i = 0; i < contents.length; i++) {
            cMove.addItem(contents[i].getName());
        }
    }

    //This allows us to get a string and return the coresponding location
    private Location stringToLocation(String s) {
        if (s == null){
            return null;
        } else if (s.equals("trailer")) {
            return game.getLocationManager().getBoard().getTrailer();
        } else if (s.equals("office")) {
            return game.getLocationManager().getBoard().getCastingOffice();
        } else {
            Location[] loc = game.getLocationManager().getBoard().getScenes();
            for (int i = 0; i < loc.length; i++) {
                if (s.equals(loc[i].getName())) {
                    return loc[i];
                }
            }
        }
        return null;
    }

    //This method updates the roles avalible in the combo box of where the player currently is 
    public void updateRolesBox() {
        cRole.removeAllItems();
        Location curLoc = game.getCurPlayer().getLocation();
        System.out.println("Current location" + curLoc.getName());
        if (curLoc.getName().equals("trailer") || curLoc.getName().equals("office")) {
            System.out.println("No valid roles");
            return;
        }
        Scene[] scenes = game.getLocationManager().getBoard().getScenes();
        Scene curScene = null;
        for (int i = 0; i < scenes.length; i++) {
            if (curLoc.getName().equals(scenes[i].getName())) {
                curScene = scenes[i];
            }
        }
        Role[] validRoles = new Role[curScene.getOffCardRoles().length + curScene.getCard().getOnCardRoles().length];
        for (int i = 0; i < curScene.getOffCardRoles().length; i++) {
            validRoles[i] = curScene.getOffCardRoles()[i];
        }
        for (int i = curScene.getOffCardRoles().length; i < validRoles.length; i++) {
            validRoles[i] = curScene.getCard().getOnCardRoles()[i- curScene.getOffCardRoles().length];
        }
        for (int i = 0; i < validRoles.length; i++) {
            cRole.addItem(validRoles[i].getDescription());
        }
    }

    //this allows us to get the role object from the name passed in as a string
    private Role stringToRole(String s) {
        Location curLoc = game.getCurPlayer().getLocation();
        System.out.println("Current location" + curLoc.getName());
        if (curLoc.getName().equals("trailer") || curLoc.getName().equals("office")) {
            System.out.println("No valid roles");
            return null;
        }
        Scene[] scenes = game.getLocationManager().getBoard().getScenes();
        Scene curScene = null;
        for (int i = 0; i < scenes.length; i++) {
            if (curLoc.getName().equals(scenes[i].getName())) {
                curScene = scenes[i];
            }
        }
        Role[] validRoles = new Role[curScene.getOffCardRoles().length + curScene.getCard().getOnCardRoles().length];
        for (int i = 0; i < curScene.getOffCardRoles().length; i++) {
            validRoles[i] = curScene.getOffCardRoles()[i];
        }
        for (int i = curScene.getOffCardRoles().length; i < validRoles.length; i++) {
            validRoles[i] = curScene.getCard().getOnCardRoles()[i- curScene.getOffCardRoles().length];
        }
        for (int i = 0; i < validRoles.length; i++) {
            if (s.equals(validRoles[i].getDescription())) {
                return validRoles[i];
            }
        }
        return null;
    }

    //Updates the upgrade box 
    public void updateUpgradeBox(Integer[] contents) {
        cUpgrade.removeAllItems();
        for (int i = 0; i < contents.length; i++) {
            cUpgrade.addItem(contents[i]);
        }
        
    }

    //This adds text to the game output box 
    public void updateOutput(String s) {
        output.setText(null);
        output.append(s);
    }
    class boardMouseListener implements MouseListener,ActionListener{
        
        // Button interactions
        //These are all of the buttons on the side that determine player actions 
        //We have used visability to show what options are avalible 

        public void mouseClicked(MouseEvent e) {
            //Work
            if (e.getSource()== bWork){
                System.out.println("Work is Selected\n");
                bMove.setVisible(false);
                bRole.setVisible(false);
                bUpgrade.setVisible(false);
                bWork.setVisible(false);
                bRehearse.setVisible(true);
                bAct.setVisible(true);

            }
            //Rehearse
            else if (e.getSource()== bRehearse){
                System.out.println("Rehearse is Selected\n");
                game.rehearsePM(game.getCurPlayer());
                bRehearse.setVisible(false);
                bAct.setVisible(false);
                updatePlayerDisplay(game.getCurPlayer().getId());
            }
            //Move
            else if (e.getSource()== bMove){
                System.out.println("Move is selected");
                cMove.removeActionListener(lMove);
                updateNeighborsBox(game.getNeighbors(game.getCurPlayer()));
                cMove.addActionListener(lMove);
                bWork.setVisible(false);
                bMove.setVisible(false);
                cMove.setVisible(true);

            } 
            //End turn
            else if (e.getSource() == bEndTurn){
                game.iterCurPlayer();
                currentPlayersDisplay.setText("Player "+ game.getCurPlayer().getName());
                bMove.setVisible(true);
                bRole.setVisible(true);
                bUpgrade.setVisible(true);
                bRehearse.setVisible(false);
                bAct.setVisible(false);
                bWork.setVisible(true);
                cRole.setVisible(false);
                cUpgrade.setVisible(false);
                cMove.setVisible(false);
            } 
            // Act
            else if( e.getSource() == bAct){
                System.out.println("Act is Selected\n");
                bRehearse.setVisible(false);
                bAct.setVisible(false);

               if(game.actPM(game.getCurPlayer())) {
                    markOffShotCounter(game.getLocationManager().LocationToScene(game.getCurPlayer().getLocation()));
                    updateOutput("Act success!");
                    updatePlayerDisplay(game.getCurPlayer().getId());
               } else {
                    updateOutput("Act failed :(");
                    updatePlayerDisplay(game.getCurPlayer().getId());
               }
            } 
            // Upgrade
            else if (e.getSource() == bUpgrade){
                System.out.println("Upgrade is Selected\n");
                cUpgrade.removeActionListener(lUpgrade);
                updateUpgradeBox(game.getUpgradeLevels());
                cUpgrade.addActionListener(lUpgrade);
                cUpgrade.setVisible(true);
                bUpgrade.setVisible(false);
                bWork.setVisible(false);
                bRole.setVisible(false);
            } 
            // Take Role
            else if (e.getSource() == bRole){
                System.out.println("take role is Selected\n");
                cRole.removeActionListener(lRole);
                updateRolesBox();
                cRole.addActionListener(lRole);
                cRole.setVisible(true);
                bMove.setVisible(false);
                bRole.setVisible(false);
                bUpgrade.setVisible(false);
                bWork.setVisible(false);
            }
        }


        // ComboBox interaction
        // This is where something is selected from one from one of the dropdowns and we use that input to carry throug the game 
        public void actionPerformed(ActionEvent e) {
            JComboBox src = (JComboBox)e.getSource();
            //Move 
            if (src == cMove) {
                String dst = (String)src.getSelectedItem();
                cMove.setVisible(false);
                Location dest = stringToLocation(dst);
                if (dest == null){
                    updateOutput("Invalid move");
                    System.out.println("Move failed");
                //Calls game to see if valid and update model
                } else if (game.movePM(game.getCurPlayer(), dest)){
                    System.out.println("Moved");
                    int[] sceneDimensions = dest.getDimensions();
                    playerPieces[game.getCurPlayer().getId()].setVisible(false);
                    if (game.getLocationManager().LocationToScene(dest) != null){
                        playerPieces[game.getCurPlayer().getId()].setLocation(sceneDimensions[0] + game.getCurPlayer().getId()*25, sceneDimensions[1] +120);
                    }
                    else{
                        playerPieces[game.getCurPlayer().getId()].setLocation(sceneDimensions[0] + ((game.getCurPlayer().getId())%2)*50, sceneDimensions[1] + (game.getCurPlayer().getId()/2)*50);
                    }
                    playerPieces[game.getCurPlayer().getId()].validate();
                    playerPieces[game.getCurPlayer().getId()].setVisible(true);
                    bPane.validate(); 
                    if(game.getLocationManager().LocationToScene(dest) != null){
                        Scene s = game.getLocationManager().LocationToScene(dest);
                        //fliping card
                        if(s.cardFaceUp() == false){
                            Role[] roles = s.getCard().getOnCardRoles();
                            for (int i = 0; i < roles.length; i++) {
                                int[] rDim = roles[i].getDimensions();
                                int[] sDim = s.getDimensions();
                                roles[i].setEachDimensions(rDim[0] + sDim[0], rDim[1] + sDim[1], rDim[2], rDim[3]);
                            }
                            s.flipCard(true);
                            uncoverCard(s);
                        }
                    }
                    updatePlayerDisplay(game.getCurPlayer().getId());
                } else{
                    updateOutput("Invalid move");
                    System.out.println("Move failed");
                }
                System.out.println(dst);

            //Upgrade
            }  else if (src == cUpgrade) {
               
                Integer dst = (Integer) src.getSelectedItem(); 
                cUpgrade.setVisible(false);
                int newRank = dst.intValue();
                if(game.upgradePM(game.getCurPlayer(), newRank)){
                    System.out.println("Rank updated");
                    String s = "images/dice/" + game.getCurPlayer().getName().toLowerCase().charAt(0)+ game.getCurPlayer().getRank()+".png";
                    ImageIcon sIcon = new ImageIcon(s);
                    playerPieces[game.getCurPlayer().getId()].setIcon(sIcon);
                    updatePlayerDisplay(game.getCurPlayer().getId());
                    updateOutput("Upgrade successful");
                } else {
                    updateOutput("Upgrade unsuccessful");
                    System.out.println("Rank not updated");
                }
            // Take role
            }else if (src == cRole) {
                String dst = (String)src.getSelectedItem();
                
                if(dst == null){
                    //do nothing
                    cRole.setVisible(false);
                }else{
                    cRole.setVisible(false);
                    Role dest = stringToRole(dst);
                    if (game.takeARolePM(game.getCurPlayer(), dest)) {
                        String s = "Took role: " + dest.getDescription();
                        updateOutput(s);
                        System.out.println("Took role: " + dest.getDescription());
                        playerPieces[game.getCurPlayer().getId()].setBounds(dest.getDimensions()[0],
                        dest.getDimensions()[1], diceWidth, diceHeight);
                    } else {
                        updateOutput("Invalid role");
                    }
                }
                

            }
        }

        //As these are apart of the interface we need to have them, though we do not use them 
        public void mousePressed(MouseEvent e) {
        }
        public void mouseReleased(MouseEvent e) {
        }
        public void mouseEntered(MouseEvent e) {
        }
        public void mouseExited(MouseEvent e) {
        }
    }

}