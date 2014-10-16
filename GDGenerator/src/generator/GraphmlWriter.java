/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

/**
 *
 * @author renzo
 */
public class GraphmlWriter extends DataWriter {

    public GraphmlWriter() {
        this.filename = "sndata.graphml";
    }

    @Override
    public void writeBegin() {
        this.writeLine("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        this.writeLine("\n<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\"");
        this.writeLine("\n\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
        this.writeLine("\n\txsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns");
        this.writeLine("\n\thttp://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\">");
        this.writeLine("\n\t<graph id=\"G\">");
    }

    @Override
    public void writeBeginPeople() {
    }

    @Override
    public void writePerson(String pid, String name, String age, String location) {
        String line = "\n\t<node type=\"person\" id=\"" + pid + "\" name=\"" + name + "\"";

        if (age.compareTo("?") != 0) {
            line += " age=\"" + age + "\"";
        }

        if (location.compareTo("?") != 0) {
            line += " location=\"" + location + "\"";
        }

        line += "/>";
        this.writeLine(line);
    }

    @Override
    public void writeEndPeople() {
    }

    @Override
    public void writeBeginWebpages() {
    }

    @Override
    public void writeWebpage(String wpid, String url, String creation) {
        String line;
        if (creation.compareTo("?") == 0) {
            line = "\n\t <node type=\"webpage\" id=\"" + wpid + "\" url=\"" + url + "\"/>";
        } else {
            line = "\n\t <node type=\"webpage\" id=\"" + wpid + "\" url=\"" + url + "\" creation=\"" + creation + "\"/>";
        }
        this.writeLine(line);
    }

    @Override
    public void writeEndWebpages() {
    }

    @Override
    public void writeBeginFriends() {
    }

    @Override
    public void writeFriend(String id, String pid1, String pid2) {
        String line = "\n\t<edge type=\"friend\" id=\"" + id + "\" directed=\"false\" source=\"" + pid1 + "\" target=\"" + pid2 + "\"/>";
        this.writeLine(line);
    }

    @Override
    public void writeEndFriends() {
    }

    @Override
    public void writeBeginLikes() {
    }

    @Override
    public void writeLike(String id, String pid, String wpid) {
        String line = "\n\t<edge type=\"like\" id=\"" + id + "\" directed=\"true\" source=\"" + pid + "\" target=\"" + wpid + "\"/>";
        this.writeLine(line);

    }

    @Override
    public void writeEndLikes() {
    }

    @Override
    public void writeEnd() {
        this.writeLine("\n\t</graph>");
        this.writeLine("\n</graphml>");
    }
}
