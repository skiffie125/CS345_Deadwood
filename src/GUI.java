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
    jTextField output;

    ProgressManager game;
    // Constructor
    public GUI(ProgressManager game) {
        // Set the title of the JFrame
        super("Deadwood");
        // Set the exit option for the JFrame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.game = game;
        // Create the JLayeredPane to hold the display, cards, dice and buttons
        bPane = getLayeredPane();
        // Create the deadwood board
        boardlabel = new JLabel();
        ImageIcon icon = new ImageIcon("images/board.jpg");
        boardlabel.setIcon(icon);
        boardlabel.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
        // Add the board to the lowest layer
        bPane.add(boardlabel, 3);
        // Set the size of the GUI
        setSize(icon.getIconWidth()+200,icon.getIconHeight() + 200);
        // Add a scene card to this room
        cardlabel = new JLabel();
        ImageIcon cIcon = new ImageIcon("images/01.png");
        cardlabel.setIcon(cIcon);
        cardlabel.setBounds(20,65,cIcon.getIconWidth()+2,cIcon.getIconHeight());
        cardlabel.setOpaque(true);
        // Add the card to the lower layer
        bPane.add(cardlabel, 1);
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
        mLabel.setBounds(icon.getIconWidth()+40,0,100,20);
        bPane.add(mLabel,2);

        // Create Action buttons
        bEndTurn = new JButton("END TURN");
        bEndTurn.setBackground(Color.white);
        bEndTurn.setBounds(icon.getIconWidth()+30, 60,100, 20);
        bEndTurn.addMouseListener(new boardMouseListener());
        bEndTurn.setVisible(true);
        
        bWork = new JButton("WORK");
        bWork.setBackground(Color.white);
        bWork.setBounds(icon.getIconWidth()+30, 90,100, 20);
        bWork.addMouseListener(new boardMouseListener());
        bWork.setVisible(true);
        
        bMove = new JButton("MOVE");
        bMove.setBackground(Color.white);
        bMove.setBounds(icon.getIconWidth()+30,120,100, 20);
        bMove.addMouseListener(new boardMouseListener());
        bMove.setVisible(true);

        bUpgrade = new JButton("UPGRADE");
        bUpgrade.setBackground(Color.white);
        bUpgrade.setBounds(icon.getIconWidth()+30,150,100, 20);
        bUpgrade.addMouseListener(new boardMouseListener());
        bUpgrade.setVisible(true);

        bRole = new JButton("TAKE ROLE");
        bRole.setBackground(Color.white);
        bRole.setBounds(icon.getIconWidth()+30,180,100, 20);
        bRole.addMouseListener(new boardMouseListener());
        bRole.setVisible(true);

        bRehearse = new JButton("REHEARSE");
        bRehearse.setBackground(Color.white);
        bRehearse.setBounds(icon.getIconWidth()+30,210,100, 20);
        bRehearse.addMouseListener(new boardMouseListener());
        bRehearse.setVisible(false);

        bAct = new JButton("ACT");
        bAct.setBackground(Color.white);
        bAct.setBounds(icon.getIconWidth()+30,240,100, 20);
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
        cMove.setBounds(icon.getIconWidth()+ 30, 210, 100, 20);
        bPane.add(cMove, 2);
        cMove.setVisible(false);
        cMove.setSelectedIndex(-1);
        cMove.addActionListener(new boardMouseListener());

        cRole = new JComboBox<Role>();
        cRole.setBounds(icon.getIconWidth() + 30, 210, 100, 20);
        bPane.add(cRole, 2);
        cRole.setVisible(false);

        cUpgrade = new JComboBox<Integer>();
        cRole.setBounds(icon.getIconWidth() + 30, 210, 100, 20);
        bPane.add(cUpgrade, 2);
        cUpgrade.setVisible(false);

        // Game state output
        // TODO: Adjust location and size as appropriate
        output = new jTextField(20);
        bPane.add(output, 2);
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
            bPane.add(playerPieces[i],0);
        }
        currentPlayersDisplay = new JLabel("Player "+ players[0].getName());
        currentPlayersDisplay.setBounds(rightBoard+40,30,100,20);
        bPane.add(currentPlayersDisplay,2);
    }
    public void setUpDay(){
        
    }

    public void updateNeighborsBox(Location[] contents) {
        cMove.removeAllItems();
        for (int i = 0; i < contents.length; i++) {
            cMove.addItem(contents[i].getName());
        }
        
    }
    private Location stringToLocation(String s) {
        if (s.equals("trailer")) {
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
    public void updateRolesBox(Role[] roles) {
        cRole.removeAllItems();
        for (int i = 0; i < roles.length; i++) {
            cRole.addItem(roles[i].getDescription());
        }
    }
    private Role stringToRole(String s) {
        Scene[] scenes = game.getLocationManager().getBoard().getScenes();
        for (int i = 0; i < scenes.length; i++) {
            if (s.equals(scenes[i].getDescription())) {
                return scenes[i];
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
                bRehearse.setVisible(false);
                bAct.setVisible(false);
            }
            else if (e.getSource()== bMove){
                System.out.println("Move is selected");
                updateNeighborsBox(game.getNeighbors(game.getCurPlayer()));
                
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
               } else {
                    //TODO: Show some boohoo bullshit
               }
            } else if (e.getSource() == bUpgrade){
                System.out.println("Upgrade is Selected\n");
                cUpgrade.setVisible(true);
                bUpgrade.setVisible(false);
                bWork.setVisible(false);
                bRole.setVisible(false);


            } else if (e.getSource() == bRole){
                System.out.println("take role is Selected\n");
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
                if (game.movePM(game.getCurPlayer(), dest)){
                    System.out.println("Moved");
                    int[] sceneDimensions = dest.getDimensions();
                    //Icon icon =playersDisplay[game.getCurPlayer().getId()].getIcon();
                    System.out.println("Dimensions: " + sceneDimensions[0] + " "+ sceneDimensions[1] );
                    playersDisplay[game.getCurPlayer().getId()].setBounds(sceneDimensions[0], sceneDimensions[1], diceWidth, diceHeight);
                } else{
                    System.out.println("Move failed");
                }
                System.out.println(dst);
            } else if (src == cRole) {
                String dst = (String)src.getSelectedItem();
                cRole.setVisible(false);
                Role dest = src.getSelectedItem();
                if (game.takeARolePM(game.getCurPlayer(), dest)) {
                    System.out.println("Took role: " + dest.getDescription());
                    playersDisplay[game.getCurPlayer().getId()].setBounds(dest.getDimensions()[0],
                    dest.getDimensions()[1], diceWidth, diceHeight);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid role");
                }

            } else if (src == cUpgrade) {
                String dst = (String)src.getSelectedItem();
                cUpgrade.setVisible(false);
                Integer dst = src.getSelectedItem();
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