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
    // JLayered Pane
    JLayeredPane bPane;
    int belowBoard;
    int rightBoard;
    // JComboBox
    JComboBox<String> cMove;
    JComboBox<Role> cRole;
    JComboBox<Integer> cUpgrade;

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
        bWork = new JButton("WORK");
        bWork.setBackground(Color.white);
        bWork.setBounds(icon.getIconWidth()+30, 60,100, 20);
        bWork.addMouseListener(new boardMouseListener());
        bRehearse = new JButton("REHEARSE");
        bRehearse.setBackground(Color.white);
        bRehearse.setBounds(icon.getIconWidth()+30,90,100, 20);
        bRehearse.addMouseListener(new boardMouseListener());
        bMove = new JButton("MOVE");
        bMove.setBackground(Color.white);
        bMove.setBounds(icon.getIconWidth()+30,120,100, 20);
        bMove.addMouseListener(new boardMouseListener());
        // Place the action buttons in the top layer
        bPane.add(bWork,2);
        bPane.add(bRehearse,2);
        bPane.add(bMove, 2);

        // Conditionally visible buttons
        cMove = new JComboBox<String>();
        cMove.setBounds(icon.getIconWidth()+ 30, 150, 100, 20);
        bPane.add(cMove, 2);
        cMove.setVisible(false);
        cMove.setSelectedIndex(-1);
        cMove.addActionListener(new boardMouseListener());

        cRole = new JComboBox<Role>();
        // TODO: Adjust locations of these
        cRole.setBounds(icon.getIconWidth() + 30, 150, 100, 20);
        bPane.add(cRole, 2);
        cRole.setVisible(false);

        cUpgrade = new JComboBox<Integer>();
        cRole.setBounds(icon.getIconWidth() + 30, 150, 100, 20);
        bPane.add(cUpgrade, 2);
        cUpgrade.setVisible(false);
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
            //ImageIcon pIcon = new ImageIcon("images/r2.png");
            playerPieces[i] = new JLabel();
            playerPieces[i].setIcon(pIcon);
            playerPieces[i].setBounds(rightBoard -150 +((i)%2)*50 ,255 + ((i)/2)*50 ,pIcon.getIconWidth(),pIcon.getIconHeight());
            //playerPieces[i].setBounds(114+i *46,belowBoard -20,46,46);
            playerPieces[i].setVisible(true);
            bPane.add(playerPieces[i],0);
        }
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
            cRole.addItem(roles[i]);
        }
    }
    public void updateUpgradeBox(Integer[] contents) {
        cRole.removeAllItems();
        for (int i = 0; i < contents.length; i++) {
            cUpgrade.addItem(contents[i]);
        }
        
    }
    // This class implements Mouse Events
    class boardMouseListener implements MouseListener,ActionListener{
        // Button interaction
        public void mouseClicked(MouseEvent e) {
            if (e.getSource()== bWork){
                //playerlabel.setVisible(true);
                bMove.setVisible(false);
                bRehearse.setVisible(false);

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
            }
            else if (e.getSource()== bMove){
                System.out.println("Move is selected");
                updateNeighborsBox(game.getNeighbors(game.getCurPlayer()));
                cMove.setVisible(true);
            } 
        }
        // ComboBox interaction 
        public void actionPerformed(ActionEvent e) {
            JComboBox src = (JComboBox)e.getSource();
            if (src == cMove) {
                String dst = (String)src.getSelectedItem();
                System.out.println(dst);
            } else if (src == cRole) {

            } else if (src == cUpgrade) {

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