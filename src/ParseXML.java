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
        // Get & set all neighbors
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
        //Get & set all dimensions
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
        // Convert all sets from Locations to Scenes
        Scene[] scenes = new Scene[setLen];
        for (int i = 0; i < setLen; i++) {
            scenes[i] = new Scene(sets[i].getName());
            scenes[i].setNeighbors(sets[i].getNeighbors());
            scenes[i].setDimensions(sets[i].getDimensions());
        }
        for (int i = 0; i < setLen; i++) {
            Location[] neigh = scenes[i].getNeighbors();
            System.out.println("Name of scene: " + scenes[i].getName());
            for (int j = 0; j < neigh.length; j++) {
                System.out.println("Neighbor" + j + ":" + neigh[j].getName());
            }
            int[] dim = scenes[i].getDimensions();
            for (int j = 0; j < dim.length; j++) {
                System.out.println("Dimension" + j + ":" + dim[j]);
            }
        }
        // Get & set shot counters of takes
        NodeList takes = d.getElementsByTagName("take");
        int takesLen = takes.getLength();
        int[] allShotCounters = new int[takesLen];
        for (int i = 0; i < takesLen; i++) {
            allShotCounters[i] = getShotCounter(takes.item(i));
        }
        int[] parsedShots = parseShotCounters(allShotCounters);
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

    // get all 'takes' fields
    private int getShotCounter(Node take) {
        return Integer.parseInt(take.getAttributes().getNamedItem("number").getNodeValue());
    }

    // Manually set office neighbors bc i cant figure out the bug
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

    private int[] parseShotCounters(int[] origin) {
        int len = origin.length;
        int[] returnArray = new int[10];
        int retLoc = 0;
        for (int i = 0; i < len; i++) {
            if ( i ==0 || i == 3 || i == 6 || i == 8 || i == 8 || i == 11
            || i == 14 || i == 15 || i == 17 || i == 19 || i == 20) {
                returnArray[retLoc] = origin[i];
                retLoc++;
            }
        }
        return returnArray;
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