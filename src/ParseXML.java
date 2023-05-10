import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class ParseXML {
    public Document getDocFromFile(String filename) throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = null;
        try{
            doc = db.parse(filename);
        } catch (Exception ex){
            System.out.println("XML parse failure");
            ex.printStackTrace();
        }
        return doc;
    }
    
    // Parse and store board.xml
    public Board readBoard(Document d) {
        Element root = d.getDocumentElement();
        // Get all sets ('scenes')
        NodeList setsList = d.getElementsByTagName("set");
        int setLen = setsList.getLength();
        Location[] sets = new Location[setLen];
        for (int i = 0; i < setLen; i++) {
            sets[i] = getLocation(setsList.item(i));
        }
        // Get all neighbors
        NodeList neighborsList = d.getElementsByTagName("neighbor");
        int numNeighbors = neighborsList.getLength();
        Location[] neighbors = new Location[numNeighbors];
        for (int i = 0; i < numNeighbors; i++) {
            neighbors[i] = getLocation(neighborsList.item(i));
        }
        neighborJoin(sets, neighbors);
        Location trailer = new Location("trailer");
        Location office = new Location("office");
        setTrailerNeighbors(trailer);
        setOfficeNeighbors(office);
        // Get dimensions
        NodeList dimList = d.getElementsByTagName("area");
        int dimLength = dimList.getLength();
        return null;
    }

    public Card[] readCards() {
        return null;
    }

    // Create a location object from a given node
    private Location getLocation(Node set) {
        String name = set.getAttributes().getNamedItem("name").getNodeValue();
        Location loc = new Location(name);
        return loc;
    }

    // Get specified dimension from given Node
    private int getDimension(Node dim, String dimension) {
        String amountStr = dim.getAttributes().getNamedItem(dimension).getNodeValue();
        int amount = 
    }

    private void setOfficeNeighbors(Location office) {
        Location[] temp = new Location[3];
        temp[0] = new Location("Train Station");
        temp[1] = new Location("Ranch");
        temp[2] = new Location("Secret Hideout");
        office.setNeighbors(temp);
    }
    // Manually set neighbors bc i cant figure out the bug
    private void setTrailerNeighbors(Location trailer) {
        Location[] temp = new Location[3];
        temp[0] = new Location("Main Street");
        temp[1] = new Location("Saloon");
        temp[2] = new Location("Hotel");
        trailer.setNeighbors(temp);
    }

    // Couple locations with their neighbors
    private void neighborJoin(Location[] origin, Location[] neighbors) {
        int len = origin.length;
        int neighborIndex = 0;
        for (int i = 0; i < len; i++) {
            Location source = origin[i];
            if (source.getName().equals("General Store")) {
                // Four neighbors
                Location[] tempNeighbors = new Location[4];
                for (int j = 0; j < 4; j++) {
                    tempNeighbors[j] = neighbors[neighborIndex];
                    neighborIndex++;
                }
                source.setNeighbors(tempNeighbors);
            } else if (source.getName().equals("Ranch")) {
                // Four neighbors
                Location[] tempNeighbors = new Location[4];
                for (int j = 0; j < 4; j++) {
                    tempNeighbors[j] = neighbors[neighborIndex];
                    neighborIndex++;
                }
                source.setNeighbors(tempNeighbors);
            } else if (source.getName().equals("Bank")) {
                // Four neighbors
                Location[] tempNeighbors = new Location[4];
                for (int j = 0; j < 4; j++) {
                    tempNeighbors[j] = neighbors[neighborIndex];
                    neighborIndex++;
                }
                source.setNeighbors(tempNeighbors);
            } else if (source.getName().equals("Saloon")) {
                Location[] tempNeighbors = new Location[3];
                tempNeighbors[0] = new Location("Main street");
                tempNeighbors[1] = new Location("Saloon");
                tempNeighbors[2] = new Location("Hotel");
                source.setNeighbors(tempNeighbors);
            } else {
                Location[] tempNeighbors = new Location[3];
                for (int j = 0; j < 3; j++) {
                    tempNeighbors[j] = neighbors[neighborIndex];
                    neighborIndex++;
                }
                source.setNeighbors(tempNeighbors);
            }
        }
    }
}