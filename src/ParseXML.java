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
    
    public Board readBoard(Document d) {
        Element root = d.getDocumentElement();
        // Get all sets ('scenes')
        NodeList sets = d.getElementsByTagName("set");
        int setLen = sets.getLength();
        Location[] locations = new Location[setLen];
        // For each set
        for (int i = 0; i < setLen; i++) {
            Node set = sets.item(i);
            String setName = set.getAttributes().getNamedItem("name").getNodeValue();
            Location setLoc = new Location(setName);
            locations[i] = setLoc;
            NodeList data = set.getChildNodes();
            int numSubNodes = data.getLength();
            // For each field in that set
            for (int j = 0; j < numSubNodes; j++) {
                Node sub = data.item(j);
                if (sub.getNodeName().equals("neighbors")) {
                    // How do we get the data out of this? 
                } else if (sub.getNodeName().equals("parts")) {
                    System.out.println("Parts");
                }
            }
            
        }
        return null;
    }

    public Card[] readCards() {
        return null;
    }
}