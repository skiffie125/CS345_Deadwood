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
    // wow this is so inefficient i did this SO wrong
    public Board readBoard(Document d) {
        Element root = d.getDocumentElement();
        // Get all sets ('scenes')
        NodeList setsList = d.getElementsByTagName("set");
        int setLen = setsList.getLength();
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
        // Get & set shot counters of takes
        NodeList takes = d.getElementsByTagName("take");
        int takesLen = takes.getLength();
        int[] allShotCounters = new int[takesLen];
        for (int i = 0; i < takesLen; i++) {
            allShotCounters[i] = getShotCounter(takes.item(i));
        }
        int[] parsedShots = parseShotCounters(allShotCounters);
        for (int i = 0; i < setLen; i++) {
            scenes[i].setShotCountersMax(parsedShots[i]);
        }
        // Get all roles
        NodeList parts = d.getElementsByTagName("part");
        int partsLen = parts.getLength();
        Role[] roles = new Role[partsLen];
        for (int i = 0; i < partsLen; i++) {
            roles[i] = getRole(parts.item(i));
        }
        // Get & set all lines
        NodeList linesList = d.getElementsByTagName("line");
        int lineLen = linesList.getLength();
        String[] lines = new String[lineLen];
        for (int i = 0; i < lineLen; i++) {
            roles[i].setLine(linesList.item(i).getTextContent());
        }
        // Bind all roles to their appropriate scene
        sceneRoleJoin(scenes, roles);
        Board board = new Board(scenes, trailer, office, sets, 0, null);
        return board;
    }

    public Card[] readCards(Document d) {
        Element root = d.getDocumentElement();
        // get all cards
        NodeList cardList = root.getElementsByTagName("card");
        int numCards = cardList.getLength();
        Card[] deck = new Card[numCards];
        for (int i = 0; i < numCards; i++) {
            Node card = cardList.item(i);
            deck[i] = getCard(card);
            NodeList children = card.getChildNodes();
            int numChild = children.getLength();
            for (int j = 0; j < numChild; j++) {
                Node sub = children.item(j);
                String nodeName = sub.getNodeName();
                if (nodeName.equals("scene")) {
                    int sceneNum = Integer.parseInt(sub.getAttributes().getNamedItem("number").getNodeValue());
                    String desc = sub.getTextContent();
                    deck[i].setSceneNumber(sceneNum);
                    System.out.println(deck[i].getSceneNumber());
                    deck[i].setDescription(desc);
                    System.out.println(deck[i].getDescription());
                }
                if (nodeName.equals("part")) {
                    String partName = sub.getAttributes().getNamedItem("name").getNodeValue();
                    int level = Integer.parseInt(sub.getAttributes().getNamedItem("level").getNodeValue());
                    Role part = new Role(partName, level);
                    String line = sub.getTextContent();
                    part.setLine(line);
                    NodeList subChildren = sub.getChildNodes();
                    int numSubChild = subChildren.getLength();
                    System.out.println(part.getDescription());
                    for (int k = 0; k < numSubChild; k++) {
                        Node subSub = subChildren.item(k);
                        String subSubName = subSub.getNodeName();
                        if (subSubName.equals("area")) {
                            int[] dimensions = getDimensions(subSub);
                            part.setDimensions(dimensions);
                        }
                    }
                }
            }
        }
        return null;
    }

    // Create a location object from a given node
    private Location getLocation(Node set) {
        String name = set.getAttributes().getNamedItem("name").getNodeValue();
        Location loc = new Location(name);
        return loc;
    }

    // Create a card object from a given node
    private Card getCard(Node card) {
        String name = card.getAttributes().getNamedItem("name").getNodeValue();
        String img = card.getAttributes().getNamedItem("img").getNodeValue();
        int budget = Integer.parseInt(card.getAttributes().getNamedItem("budget").getNodeValue());
        Card crd = new Card(name);
        // for some reason the name is null unless its set again
        crd.setName(name);
        crd.setImg(img);
        crd.setBudget(budget);
        return crd;
    }

    // Create a role object from a given node
    private Role getRole(Node part) {
        String name = part.getAttributes().getNamedItem("name").getNodeValue();
        int rank = Integer.parseInt(part.getAttributes().getNamedItem("level").getNodeValue());
        Role rol = new Role(name, rank);
        return rol;
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
    // couple roles with their scenes
    private void sceneRoleJoin(Scene[] scenes, Role[] roles) {
        int len = scenes.length;
        int rolesIndex = 0;
        for (int i = 0; i < len; i++) {
            if (i == 0 || i == 1 || i == 3 || i == 4) {
                Role[] tempRoles = new Role[4];
                for (int j = 0; j < 4; j++) {
                    tempRoles[j] = roles[rolesIndex];
                    rolesIndex++;
                }
                scenes[i].setRoles(tempRoles);
            } else if (i == 7) {
                Role[] tempRoles = new Role[3];
                for (int j = 0; j < 3; j++) {
                    tempRoles[j] = roles[rolesIndex];
                    rolesIndex++;
                }
                scenes[i].setRoles(tempRoles);
            } else {
                Role[] tempRoles = new Role[2];
                for (int j = 0; j < 2; j++) {
                    tempRoles[j] = roles[rolesIndex];
                    rolesIndex++;
                }
                scenes[i].setRoles(tempRoles);
            }
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