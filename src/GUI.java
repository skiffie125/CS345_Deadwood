import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.JOptionPane;
public class GUI extends JFrame{
    // Fields
    // Labels
    JLabel boardLabel;

    // Buttons
    JButton bAct;
    JButton bRehearse;
    JButton bMove;

    // Pane
    JLayeredPane bPane;

    // Methods
    public GUI() {
        super("Deadwood");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create background
        bPane = getLayeredPane();

        // Create board
        boardLabel = newJlabel();
        ImageIcon boardIcon = new ImageIcon("images/baord.jpg");
        boardLabel.setIcon(boardIcon);
        boardlabel.setBounds(0,0,boardIcon.getIconWidth(), boardIcon.getIconHeight());

        // send it to the background
        bPane.add(boardLabel, new Integer(0));

        setSize(boardIcon.getIconWidth()+200, boardIcon.getIconHeight());
    }
}