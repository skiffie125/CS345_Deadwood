import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.JOptionPane;
public class GUI extends JFrame {
    // JLabels
    JLabel boardlabel;
    JLabel cardlabel;
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

    JTextField output;

    //CBox action Listeners
    boardMouseListener lMove;
    boardMouseListener lRole;
    boardMouseListener lUpgrade;

    ProgressManager game;
    // Constructor
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
        
        //bPane.add(boardlabel, 3);
        
        
        // Set the size of the GUI
        setSize(icon.getIconWidth()+250,icon.getIconHeight() + 200);
        // Add a scene card to this room
        /*cardlabel = new JLabel();
        ImageIcon cIcon = new ImageIcon("images/01.png");
        cardlabel.setIcon(cIcon);
        cardlabel.setBounds(20,65,cIcon.getIconWidth()+2,cIcon.getIconHeight());
        cardlabel.setOpaque(true);
        // Add the card to the lower layer
        bPane.add(cardlabel, 1);*/
        // Add a dice to represent a player.
        // Role for Crusty the prospector. The x and y co-ordiantes are taken from

        //playerlabel = new JLabel();
        //ImageIcon pIcon = new ImageIcon("images/r2.png");
        //playerlabel.setIcon(pIcon);
        //playerlabel.setBounds(114,227,pIcon.getIconWidth(),pIcon.getIconHeight());
        //playerlabel.setBounds(114,227,46,46);
        //playerlabel.setVisible(true);
        //bPane.add(playerlabel,3);
        // Create the Menu for action buttons
        belowBoard = icon.getIconHeight();
        rightBoard = icon.getIconWidth();
        mLabel = new JLabel("MENU");
        mLabel.setBounds(icon.getIconWidth()+40,0,120,20);
        bPane.add(mLabel,2);

        // Create Action buttons
        bEndTurn = new JButton("END TURN");
        bEndTurn.setBackground(Color.white);
        bEndTurn.setBounds(icon.getIconWidth()+30, 60,120, 20);
        bEndTurn.addMouseListener(new boardMouseListener());
        bEndTurn.setVisible(true);
        
        bWork = new JButton("WORK");
        bWork.setBackground(Color.white);
        bWork.setBounds(icon.getIconWidth()+30, 90,120, 20);
        bWork.addMouseListener(new boardMouseListener());
        bWork.setVisible(true);
        
        bMove = new JButton("MOVE");
        bMove.setBackground(Color.white);
        bMove.setBounds(icon.getIconWidth()+30,120,120, 20);
        bMove.addMouseListener(new boardMouseListener());
        bMove.setVisible(true);

        bUpgrade = new JButton("UPGRADE");
        bUpgrade.setBackground(Color.white);
        bUpgrade.setBounds(icon.getIconWidth()+30,150,120, 20);
        bUpgrade.addMouseListener(new boardMouseListener());
        bUpgrade.setVisible(true);

        bRole = new JButton("TAKE ROLE");
        bRole.setBackground(Color.white);
        bRole.setBounds(icon.getIconWidth()+30,180,120, 20);
        bRole.addMouseListener(new boardMouseListener());
        bRole.setVisible(true);

        bRehearse = new JButton("REHEARSE");
        bRehearse.setBackground(Color.white);
        bRehearse.setBounds(icon.getIconWidth()+30,210,120, 20);
        bRehearse.addMouseListener(new boardMouseListener());
        bRehearse.setVisible(false);

        bAct = new JButton("ACT");
        bAct.setBackground(Color.white);
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
        // TODO: Adjust locations of these
        cMove = new JComboBox<String>();
        cMove.setBounds(icon.getIconWidth()+ 30, 210, 120, 20);
        bPane.add(cMove, 2);
        cMove.setVisible(false);
        lMove = new boardMouseListener();
        cMove.addActionListener(lMove);

        cRole = new JComboBox<String>();
        cRole.setBounds(icon.getIconWidth() + 30, 210, 120, 20);
        bPane.add(cRole, 2);
        cRole.setVisible(false);
        lRole = new boardMouseListener();
        cRole.addActionListener(lRole);

        cUpgrade = new JComboBox<Integer>();
        cRole.setBounds(icon.getIconWidth() + 30, 210, 120, 20);
        bPane.add(cUpgrade, 2);
        cUpgrade.setVisible(false);
        lUpgrade = new boardMouseListener();
        cUpgrade.addActionListener(lUpgrade);

        // Game state output
        // TODO: Adjust location and size as appropriate
        output = new JTextField(20);
        bPane.add(output, 2);
        bPane.validate();
    }

    public void createPlayerLabels(Player[] players){
        playersDisplay = new JLabel[players.length];
        playerPieces = new JLabel[players.length];
        for(int i = 0; i < playersDisplay.length; i++){
            playersDisplay[i] = new JLabel("Player " + players[i].getName()+ "\n Rank " + players[i].getRank());
            playersDisplay[i].setBounds( i* 170 +5, belowBoard +5, 170,50);
            playersDisplay[i].setVisible(true);
            bPane.add(playersDisplay[i],3);
            String s = "images/dice/" + players[i].getName().toLowerCase().charAt(0)+ players[i].getRank()+".png";
            //System.out.println(s);
            ImageIcon pIcon = new ImageIcon(s);
            diceHeight = pIcon.getIconHeight();
            diceWidth = pIcon.getIconWidth(); 
            //ImageIcon pIcon = new ImageIcon("images/r2.png");
            playerPieces[i] = new JLabel();
            playerPieces[i].setIcon(pIcon);
            playerPieces[i].setBounds(rightBoard -150 +((i)%2)*50 ,255 + ((i)/2)*50 ,pIcon.getIconWidth(),pIcon.getIconHeight());
            //playerPieces[i].setBounds(114+i *46,belowBoard -20,46,46);
            playerPieces[i].setVisible(true);
            bPane.add(playerPieces[i], bPane.MODAL_LAYER);
        }
        currentPlayersDisplay = new JLabel("Player "+ players[0].getName());
        currentPlayersDisplay.setBounds(rightBoard+40,30,100,20);
        bPane.add(currentPlayersDisplay,2);
        bPane.validate();
    }

    public void setUpDay(){
        cardsAtScenes = new JLabel[game.getLocationManager().getBoard().getScenes().length];
        shotCounters = new JLabel[game.getLocationManager().getBoard().getScenes().length][];
        for(int i = 0; i< cardsAtScenes.length; i++){
            ImageIcon pIcon = new ImageIcon("images/CardBack-small.jpg");
            cardsAtScenes[i] = new JLabel();
            cardsAtScenes[i].setIcon(pIcon);
            cardsAtScenes[i].setBounds(game.getLocationManager().getBoard().getScenes()[i].getDimensions()[0],
            game.getLocationManager().getBoard().getScenes()[i].getDimensions()[1], pIcon.getIconWidth(),pIcon.getIconHeight());
            cardsAtScenes[i].setVisible(true);
            bPane.add(cardsAtScenes[i], bPane.PALETTE_LAYER);

            shotCounters[i] = new JLabel[game.getLocationManager().getBoard().getScenes()[i].getShotCountersMax()];
            System.out.println(game.getLocationManager().getBoard().getScenes()[i].getShotCountersMax());

            for(int j =0; j < game.getLocationManager().getBoard().getScenes()[i].getShotCountersMax(); j++){
                ImageIcon sIcon = new ImageIcon("imges/shot.png");
                System.out.println(game.getLocationManager().getBoard().getScenes()[i].getName()+ " " + game.getLocationManager().getBoard().getScenes()[i].getShotCountersMax());
                shotCounters[i][j] = new JLabel();
                shotCounters[i][j].setIcon(sIcon);
                int[][] shotPlaces = game.getLocationManager().getBoard().getScenes()[i].getShotDimensions();
                if (shotPlaces != null){
                    if (shotPlaces[j] != null){
                        System.out.println("Shot counter boundaries: "  + shotPlaces[j][0] + " " + shotPlaces[j][1]);
                        shotCounters[i][j].setBounds(shotPlaces[j][0], shotPlaces[j][1],sIcon.getIconWidth(),sIcon.getIconHeight());
                        shotCounters[i][j].setVisible(true);
                        bPane.add(shotCounters[i][j], bPane.PALETTE_LAYER);
                    } else {
                        if (j == 1) {
                            System.out.println("Set Saloon 2");
                            shotCounters[i][j].setBounds(626, 216, 47, 47);
                        } else {
                            System.out.println("Set Saloon 1");
                            shotCounters[i][j].setBounds(679, 216, 47, 47);
                        }

                        //System.out.println("Girl help");
                    }
                }
            }
        }
    }
    public void uncoverCard(Scene s){
        System.out.println("Uncovering card");
        ImageIcon pIcon = new ImageIcon("images/cards/"+s.getCard().getImg());
        cardsAtScenes[s.getIndex()].setIcon(pIcon);
        bPane.add(cardsAtScenes[s.getIndex()], 2);
    }

    public void markOffShotCounter(Scene s){
        shotCounters[s.getIndex()][s.getShotCountersLeft()-1].setVisible(true);
    }

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



    public void updateNeighborsBox(Location[] contents) {
        cMove.removeAllItems();
        for (int i = 0; i < contents.length; i++) {
            cMove.addItem(contents[i].getName());
        }
    }
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
    public void updateUpgradeBox(Integer[] contents) {
        cRole.removeAllItems();
        for (int i = 0; i < contents.length; i++) {
            cUpgrade.addItem(contents[i]);
        }
        
    }
    class boardMouseListener implements MouseListener,ActionListener{
        // Button interaction
        public void mouseClicked(MouseEvent e) {
            if (e.getSource()== bWork){
                //playerlabel.setVisible(true);
                System.out.println("Work is Selected\n");
                bMove.setVisible(false);
                bRole.setVisible(false);
                bUpgrade.setVisible(false);
                bWork.setVisible(false);
                bRehearse.setVisible(true);
                bAct.setVisible(true);

                // JButton bAct = new JButton("ACT");
                // JButton bRehearse = new JButton("REHEARSE");
                // bAct.setBackground(Color.white);
                // bRehearse.setBackground(Color.white);
                // bAct.setVisible(true);
                // bRehearse.setVisible(true);
                // //bAct
            }
            else if (e.getSource()== bRehearse){
                System.out.println("Rehearse is Selected\n");
                game.rehearsePM(game.getCurPlayer());
                bRehearse.setVisible(false);
                bAct.setVisible(false);
            }
            else if (e.getSource()== bMove){
                System.out.println("Move is selected");
                cMove.removeActionListener(lMove);
                updateNeighborsBox(game.getNeighbors(game.getCurPlayer()));
                cMove.addActionListener(lMove);
                bWork.setVisible(false);
                bMove.setVisible(false);
                cMove.setVisible(true);

            } else if (e.getSource() == bEndTurn){
                game.iterCurPlayer();
                currentPlayersDisplay.setText("Player "+ game.getCurPlayer().getName());
                bMove.setVisible(true);
                bRole.setVisible(true);
                bUpgrade.setVisible(true);
                bRehearse.setVisible(false);
                bAct.setVisible(false);
                bWork.setVisible(true);
            } else if( e.getSource() == bAct){
                System.out.println("Act is Selected\n");
                bRehearse.setVisible(false);
                bAct.setVisible(false);

               if(game.actPM(game.getCurPlayer())) {
                    //TODO: Show some hooray bullshit
                    markOffShotCounter(game.getLocationManager().LocationToScene(game.getCurPlayer().getLocation()));
                    
                    System.out.println("Act success!");
               } else {
                    //TODO: Show some boohoo bullshit
                    System.out.println("Act failed :(");
               }
            } else if (e.getSource() == bUpgrade){
                System.out.println("Upgrade is Selected\n");
                cUpgrade.setVisible(true);
                updateUpgradeBox(game.getUpgradeLevels());
                bUpgrade.setVisible(false);
                bWork.setVisible(false);
                bRole.setVisible(false);


            } else if (e.getSource() == bRole){
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
        public void actionPerformed(ActionEvent e) {
            JComboBox src = (JComboBox)e.getSource();
            if (src == cMove) {
                String dst = (String)src.getSelectedItem();
                cMove.setVisible(false);
                Location dest = stringToLocation(dst);
                if (dest == null){
                    System.out.println("Move failed");
                } else if (game.movePM(game.getCurPlayer(), dest)){
                    System.out.println("Moved");
                    int[] sceneDimensions = dest.getDimensions();
                    System.out.println("Dimensions: " + sceneDimensions[0] + " "+ sceneDimensions[1] + " id: " + game.getCurPlayer().getId());
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
                        Role[] roles = s.getCard().getOnCardRoles();
                        for (int i = 0; i < roles.length; i++) {
                            int[] rDim = roles[i].getDimensions();
                            int[] sDim = s.getDimensions();
                            roles[i].setEachDimensions(rDim[0] + sDim[0], rDim[1] + sDim[1], rDim[2], rDim[3]);
                        }
                        if(s.cardFaceUp() == false){
                            s.flipCard(true);
                            uncoverCard(s);
                        }
                    }

                } else{
                    System.out.println("Move failed");
                }
                System.out.println(dst);
            } else if (src == cRole) {
                String dst = (String)src.getSelectedItem();
                cRole.setVisible(false);
                Role dest = stringToRole(dst);
                System.out.println("Selected role: " + dest.getDescription());
                if (game.takeARolePM(game.getCurPlayer(), dest)) {
                    System.out.println("Took role: " + dest.getDescription());
                    playerPieces[game.getCurPlayer().getId()].setBounds(dest.getDimensions()[0],
                    dest.getDimensions()[1], diceWidth, diceHeight);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid role");
                }

            } else if (src == cUpgrade) {
                cUpgrade.setVisible(false);
                Integer dst = (Integer) src.getSelectedItem();
                int newRank = dst.intValue();
            }
        }
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