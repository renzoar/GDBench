package generator;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

/**
 *
 * @author renzo
 */
public abstract class DataWriter {

    FileWriter file = null;
    PrintWriter pw = null;
    Writer writer = null;
    long nodes = 0;
    long edges = 0;
    String filename = "data";
    
    public DataWriter(){
        
    }

    public void setFilename(String _filename){
        this.filename = _filename;
    }
    
    public void open() {
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.filename), "UTF-8"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    public void close() {
        try {
            writer.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    public void writeLine(String line) {
        try {
            writer.write(line + "\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

    }

    abstract void writeBegin();

    abstract void writeBeginPeople();

    abstract void writePerson(String pid, String name, String age, String location);

    abstract void writeEndPeople();

    abstract void writeBeginWebpages();

    abstract void writeWebpage(String wpid, String url, String creation);

    abstract void writeEndWebpages();

    abstract void writeBeginFriends();

    abstract void writeFriend(String id, String pid1, String pid2);

    abstract void writeEndFriends();

    abstract void writeLike(String id, String pid, String wpid);

    abstract void writeBeginLikes();

    abstract void writeEndLikes();

    abstract void writeEnd();
    
}
