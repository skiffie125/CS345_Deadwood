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
        int setLen = sets.length;
        for (int i = 0; i < setLen; i++) {
            Node set = sets.item(i);
            String setName = set.getAttributes().getNamedItem("name").getNodeValue();
            System.out.println("Name: " + setName);
        }
        return null;
    }

    public Card[] readCards() {
        return null;
    }
}