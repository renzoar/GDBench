package generator;

/**
 *
 * @author renzo
 */
public class N3Writer extends DataWriter {

    public N3Writer() {
        this.filename = "sndata.n3";
    }

    @Override
    public void writeBegin() {
        this.writeLine("@prefix data: <http://sn.org/data/> .");
        this.writeLine("@prefix vocp: <http://sn.org/voc/person#> .");
        this.writeLine("@prefix vocw: <http://sn.org/voc/webpage#> .");
        this.writeLine("@prefix voc: <http://sn.org/voc/> .");
    }

    @Override
    public void writeBeginPeople() {
    }

    @Override
    public void writePerson(String pid, String name, String age, String location) {
        String line;
        String person_uri = "data:" + "person" + pid;

        line = person_uri + " vocp:id \"" + pid + "\" .";
        this.writeLine(line);

        line = person_uri + " vocp:name \"" + name + "\" .";
        this.writeLine(line);

        if (age.compareTo("?") != 0) {
            line = person_uri + " vocp:age \"" + age + "\" .";
            this.writeLine(line);
        }

        if (location.compareTo("?") != 0) {
            line = person_uri + " vocp:location \"" + location + "\" .";
            this.writeLine(line);
        }

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
        String wp_uri = "data:" + "webpage" + wpid;

        line = wp_uri + " vocw:id \"" + wpid + "\" .";
        this.writeLine(line);

        line = wp_uri + " vocw:url \"" + url + "\" .";
        this.writeLine(line);

        if (creation.compareTo("?") != 0) {
            line = wp_uri + " vocw:creation \"" + creation + "\" .";
            this.writeLine(line);
        }
    }

    @Override
    public void writeEndWebpages() {
    }

    @Override
    public void writeBeginFriends() {
    }

    @Override
    public void writeFriend(String id, String pid1, String pid2) {
        String line;
        String uri1 = "data:" + "person" + pid1;
        String uri2 = "data:" + "person" + pid2;

        line = uri1 + " voc:friend " + uri2 + " .";
        this.writeLine(line);

        line = uri2 + " voc:friend " + uri1 + " .";
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
        String uri1 = "data:" + "person" + pid;
        String uri2 = "data:" + "webpage" + wpid;
        String line = uri1 + " voc:like " + uri2 + " .";
        this.writeLine(line);

    }

    @Override
    public void writeEndLikes() {
    }

    @Override
    public void writeEnd() {
    }
}
