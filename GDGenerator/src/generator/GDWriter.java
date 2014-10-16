/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

/**
 *
 * @author renzo
 */
public class GDWriter extends DataWriter {

    public GDWriter(){
        this.filename = "sndata.gd";        
    }
    
    @Override
    public void writeBegin() {

    }

    @Override
    public void writeBeginPeople() {
        this.writeLine("<PEOPLE>");
    }

    @Override
    public void writePerson(String pid, String name, String age, String location) {
        String line = pid + "-" + name + "-" + age + "-" + location;
        this.writeLine(line);
    }

    @Override
    public void writeEndPeople() {
        this.writeLine("</PEOPLE>");
    }

    @Override
    public void writeBeginWebpages() {
        this.writeLine("<WEBPAGES>");
    }

    @Override
    public void writeWebpage(String wpid, String url, String creation) {
        String line = wpid + "-" + creation;
        this.writeLine(line);
    }

    @Override
    public void writeEndWebpages() {
        this.writeLine("</WEBPAGES>");
    }

    @Override
    public void writeBeginFriends() {
        this.writeLine("<FRIENDS>");
    }

    @Override
    public void writeFriend(String id, String pid1, String pid2) {
        String line = id + "-" + pid1 + "-" + pid2;
        this.writeLine(line);
    }

    @Override
    public void writeEndFriends() {
        this.writeLine("</FRIENDS>");
    }

    @Override
    public void writeBeginLikes() {
        this.writeLine("<LIKES>");
    }

    @Override
    public void writeLike(String id, String pid, String wpid) {
        String line = id + "-" + pid + "-" + wpid;
        this.writeLine(line);

    }

    @Override
    public void writeEndLikes() {
        this.writeLine("</LIKES>");
    }

    @Override
    public void writeEnd() {
    }
}
