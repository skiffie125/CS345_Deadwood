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
        
        JFrame frame = new JFrame("Deadwood");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //add stuff 
        bPane = new JLayeredPane();
        

        boardLabel = new JLabel();
        ImageIcon boardIcon = new ImageIcon("images/board.jpg");
        boardLabel.setIcon(boardIcon);
        boardLabel.setBounds(0,0,boardIcon.getIconWidth(), boardIcon.getIconHeight());
        
        bPane.setPreferredSize(new Dimension(boardIcon.getIconWidth(), boardIcon.getIconHeight()));
        bPane.setBorder(BorderFactory.createTitledBorder("Board"));
        bPane.add(boardLabel, new Integer(1));
        //bPane.addMouseListener(new MouseListener());

        frame.pack();
        frame.setVisible(true);


        /*super("Deadwood");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create background
        bPane = getLayeredPane();

        // Create board
        

        // send it to the background
        

        setSize(boardIcon.getIconWidth()+200, boardIcon.getIconHeight());*/
    }
}