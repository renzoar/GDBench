
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author renzo
 */
public class GraphmlReader extends DefaultHandler {

    private long id;
    private String name;
    private String age;
    private String birthday;
    private String location;
    private String looking_to;
    private String language;
    private String url;
    private String creation;
    private String source;
    private String target;
    private TestDriver driver;
    private long people;
    private long webpages;
    private long friends;
    private long likes;

    public GraphmlReader(TestDriver _driver) {
        driver = _driver;
    }

    public String getTestDataInfo() {
        String text;
        Long totn = people + webpages;
        Long tote = friends + likes;
        text =
                "- Nodes Person read: " + people + "\n"
                + "- Nodes Webpage read:" + webpages + "\n"
                + "- Edges friend read: " + friends + "\n"
                + "- Edges Like read: " + likes + "\n"
                + "- Total nodes read: " + totn + "\n"
                + "- Total edges read: " + tote + "\n";
        return text;
    }

    public void printInfo() {
        System.out.println("- Nodes Person read: " + people);
        System.out.println("- Nodes Webpage read: " + webpages);
        System.out.println("- Edges friend read: " + friends);
        System.out.println("- Edges Like read: " + likes);
        Long totn = people + webpages;
        System.out.println("- Total nodes read: " + totn);
        Long tote = friends + likes;
        System.out.println("- Total edges read: " + tote);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.compareTo("node") == 0) {
            String type = attributes.getValue("type");
            id = Long.parseLong(attributes.getValue("id"));
            if (type.equals("person")) {
                name = attributes.getValue("name");
                age = attributes.getValue("age");
                birthday = attributes.getValue("birthday");
                location = attributes.getValue("location");
                looking_to = attributes.getValue("looking_to");
                language = attributes.getValue("language");
                driver.insertPerson(id, name, age, location);
                people++;
            } else if (type.equals("webpage")) {
                url = attributes.getValue("url");
                creation = attributes.getValue("creation");
                driver.insertWebPage(id, url, creation);
                webpages++;
            }
        } else if (qName.compareTo("edge") == 0) {
            id = Long.parseLong(attributes.getValue("id"));
            String type = attributes.getValue("type");
            if (type.equals("friend")) {
                source = attributes.getValue("source");
                target = attributes.getValue("target");
                driver.insertFriend(Long.parseLong(source), Long.parseLong(target));
                friends++;
                driver.insertFriend(Long.parseLong(target), Long.parseLong(source)); //friend is undirected 
                friends++;
            } else if (type.equals("like")) {
                source = attributes.getValue("source");
                target = attributes.getValue("target");
                driver.insertLike(Long.parseLong(source), Long.parseLong(target)); //like is directed
                likes++;
            }
        }
    }
}
