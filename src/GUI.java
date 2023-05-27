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
    JLabel playerlabel;
    JLabel mLabel;
    //JButtons
    JButton bAct;
    JButton bRehearse;
    JButton bMove;
    // JLayered Pane
    JLayeredPane bPane;
    // Constructor
    public GUI() {
        // Set the title of the JFrame
        super("Deadwood");
        // Set the exit option for the JFrame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Create the JLayeredPane to hold the display, cards, dice and buttons
        bPane = getLayeredPane();
        // Create the deadwood board
        boardlabel = new JLabel();
        ImageIcon icon = new ImageIcon("images/board.jpg");
        boardlabel.setIcon(icon);
        boardlabel.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
        // Add the board to the lowest layer
        bPane.add(boardlabel, 0);
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
        playerlabel = new JLabel();
        ImageIcon pIcon = new ImageIcon("images/r2.png");
        playerlabel.setIcon(pIcon);
        //playerlabel.setBounds(114,227,pIcon.getIconWidth(),pIcon.getIconHeight());
        playerlabel.setBounds(114,227,46,46);
        playerlabel.setVisible(false);
        bPane.add(playerlabel,3);
        // Create the Menu for action buttons
        mLabel = new JLabel("MENU");
        mLabel.setBounds(icon.getIconWidth()+40,0,100,20);
        bPane.add(mLabel,2);
        // Create Action buttons
        bAct = new JButton("ACT");
        bAct.setBackground(Color.white);
        bAct.setBounds(icon.getIconWidth()+10, 30,100, 20);
        bAct.addMouseListener(new boardMouseListener());
        bRehearse = new JButton("REHEARSE");
        bRehearse.setBackground(Color.white);
        bRehearse.setBounds(icon.getIconWidth()+10,60,100, 20);
        bRehearse.addMouseListener(new boardMouseListener());
        bMove = new JButton("MOVE");
        bMove.setBackground(Color.white);
        bMove.setBounds(icon.getIconWidth()+10,90,100, 20);
        bMove.addMouseListener(new boardMouseListener());
        // Place the action buttons in the top layer
        bPane.add(bAct,2);
        bPane.add(bRehearse,2);
        bPane.add(bMove, 2);
    }
    // This class implements Mouse Events
    class boardMouseListener implements MouseListener{
        // Code for the different button clicks
        public void mouseClicked(MouseEvent e) {
            if (e.getSource()== bAct){
                playerlabel.setVisible(true);
                System.out.println("Acting is Selected\n");
            }
            else if (e.getSource()== bRehearse){
                System.out.println("Rehearse is Selected\n");
            }
            else if (e.getSource()== bMove){
                System.out.println("Move is Selected\n");
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