
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author renzo
 */
public class GDReader {

    private TestDriver driver;
    private FileReader fr;
    private BufferedReader br;
    private String line;
    private long people;
    private long webpages;
    private long friends;
    private long likes;

    public GDReader(TestDriver _driver) {
        driver = _driver;
    }

    public boolean readFile(String filename) {
        try {
            fr = new FileReader(filename);
            //int bufSize = 12 * 1024; //8K defulat=8192 bytes
            //br = new BufferedReader(fr, bufSize);
            br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                if (line.startsWith("<PEOPLE>")) {
                    this.readPeople();
                }
                if (line.startsWith("<WEBPAGES>")) {
                    this.readWebpages();
                    continue;
                }
                if (line.startsWith("<FRIENDS>")) {
                    this.readFriends();
                    continue;
                }
                if (line.startsWith("<LIKES>")) {
                    this.readLikes();
                    continue;
                }
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public long getPeopleCounter(){
        return people;
    }
    
    public long getWebpagesCounter(){
        return webpages;
    }
    
    public long getFriendsCounter(){
        return friends;
    }
    
    public long getLikesCounter(){
        return likes;
    }
    
    public long getNodesCounter(){
        return people + webpages;
    }
    
    public long getEdgesCounter(){
        return friends + likes;
    }
    
    private void readPeople() throws IOException {
        System.out.println("Loading Person nodes ...");
        String pid = null;;
        String name = null;
        String age = null;
        String birthday = null;
        String location = null;
        String looking_to = null;
        String language = null;
        String[] attributes;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("</PEOPLE>")) {
                return;
            }
            pid = name = age = location = null;
            attributes = line.split("-");
            if (attributes[0].compareTo("?") != 0) {
                pid = attributes[0];
            }
            if (attributes[1].compareTo("?") != 0) {
                name = attributes[1];
            }
            if (attributes[2].compareTo("?") != 0) {
                age = attributes[2];
            }
            if (attributes[3].compareTo("?") != 0) {
                location = attributes[3];
            }

            driver.insertPerson(Long.parseLong(pid), name, age, location);
            people++;
        }
    }

    private void readWebpages() throws IOException {
        System.out.println("Loading Webpage nodes ...");
        String wpid = null;
        String wpurl = null;
        String wpcreation = null;
        String[] attributes;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("</WEBPAGES>")) {
                return;
            }
            wpid = wpurl = wpcreation = null;
            attributes = line.split("-");
            if (attributes[0].compareTo("?") != 0) {
                wpid = attributes[0];
            }
            if (attributes[1].compareTo("?") != 0) {
                wpcreation = attributes[1];
            }
            wpurl = "http://www.site.org/webpage" + wpid + ".html";
            driver.insertWebPage(Long.parseLong(wpid), wpurl, wpcreation);
            webpages++;
        }
    }

    private void readFriends() throws IOException {
        System.out.println("Loading Friend edges ...");
        String eid = "";
        String from = "";
        String to = "";
        String[] values;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("</FRIENDS>")) {
                return;
            }
            values = line.split("-");
            eid = values[0];
            from = values[1];
            to = values[2];
            driver.insertFriend(Long.parseLong(from), Long.parseLong(to)); //friend is undirected 
            friends++;
        }

    }

    private void readLikes() throws IOException {
        System.out.println("Loading Like edges ...");
        String eid = "";
        String from = "";
        String to = "";
        String[] values;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("</LIKES>")) {
                return;
            }
            values = line.split("-");
            eid = values[0];
            from = values[1];
            to = values[2];
            driver.insertLike(Long.parseLong(from), Long.parseLong(to)); //like is directed
            likes++;
        }

    }
}
