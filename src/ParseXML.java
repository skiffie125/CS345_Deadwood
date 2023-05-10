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
        //TODO: Convert the appropriate locations(i.e. not the office or trailer) into scenes
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
        //Get dimensions
        NodeList dimList = d.getElementsByTagName("area");
        int dimLength = dimList.getLength();
        int[][] rawDimensions = new int[dimLength][4];
        for (int i = 0; i < dimLength; i++) {
            rawDimensions[i] = getDimensions(dimList.item(i));
        }
        int [][] sceneDim = getSceneDimensions(rawDimensions);
        sceneDimensionJoin(sets, sceneDim);
        trailer.setDimensions(sceneDim[10]);
        office.setDimensions(sceneDim[11]);
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

    private Scene getScene(Node set) {
        String name = set.getAttributes().getNamedItem("name").getNodeValue();
        Scene scene = new Scene(name);
        return scene;
    }

    // Get all dimensions from given Node
    private int[] getDimensions(Node dim) {
        String xStr = dim.getAttributes().getNamedItem("x").getNodeValue();
        String yStr = dim.getAttributes().getNamedItem("y").getNodeValue();
        String hStr = dim.getAttributes().getNamedItem("h").getNodeValue();
        String wStr = dim.getAttributes().getNamedItem("w").getNodeValue();
        int x = Integer.parseInt(xStr);
        int y = Integer.parseInt(yStr);
        int h = Integer.parseInt(hStr);
        int w = Integer.parseInt(wStr);
        int[] dimArray = {x, y, h, w};
        return dimArray;
    }

    // Get only those dimensions that map to scenes
    private int[][] getSceneDimensions(int[][] origin) {
        int len = origin.length;
        int[][] returnArray = new int[12][4];
        int returnLoc = 0;
        for (int i = 0; i < len; i++) {
            if ( i ==0 || i == 8 || i == 16 || i == 21 || i == 29 || i == 37
            || i == 41 || i == 46 || i == 52 || i == 56 || i == 61 || i == 62 ) {
                for (int j = 0; j < 4; j++) {
                    returnArray[returnLoc][j] = origin[i][j];
                }
                returnLoc++;
            }
        }
        return returnArray;
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

    // Couple scenes with their dimensions
    private void sceneDimensionJoin(Location[] origin, int[][] dimensions) {
        int originLen = origin.length;
        for (int i = 0; i < originLen; i++) {
            origin[i].setDimensions(dimensions[i]);
        }
    }

    // Couple scenes with their neighbors
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