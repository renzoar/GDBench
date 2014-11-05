package generator;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import objects.Path;
import objects.Person;
import objects.Webpage;
import rmat.Edge;
import rmat.RMat;

/**
 *
 * @author renzo
 */
public class Generator {

    private long people_number = 0;
    private long webpages_number = 0;
    private int queries_number = 0;
    public long people_counter = 0;
    public long webpages_counter = 0;
    public long friends_counter = 0;
    public long friends_number = 0;
    public long likes_counter = 0;
    public long likes_number = 0;
    public long edges_counter = 0;
    public long nodes_counter = 0;
    public long samples_number = 0;
    public long samples_counter = 0;
    ArrayList<Long> people_ids = new ArrayList<Long>();
    ArrayList<Long> people_ids2 = new ArrayList<Long>();
    ArrayList<String> people_names = new ArrayList<String>();
    ArrayList<Long> webpage_ids = new ArrayList<Long>();
    ArrayList<String> friends = new ArrayList<String>();
    ArrayList<String> likes = new ArrayList<String>();
    ArrayList<Path> paths = new ArrayList<Path>();
    ArrayList<QueryInstance> queries = new ArrayList<QueryInstance>();
    Data data = new Data();
    private static PrintWriter pw = null;
    private static Writer writer = null;
    String line;
    DataWriter datawriter;
    public boolean powerLawFriend;
    public boolean powerLawLike;

    public Generator(long number_of_people, long number_of_pages, int number_of_queries, DataWriter _datawriter, boolean powerLawFriend, boolean powerLawLike) {
        this.people_number = number_of_people;
        this.webpages_number = number_of_pages;
        this.queries_number = number_of_queries;
        this.datawriter = _datawriter;
        this.powerLawFriend = powerLawFriend;
        this.powerLawLike = powerLawLike;

    }

    public void run() {

        datawriter.open();

        datawriter.writeBegin();

        System.out.println("\nGenerating person nodes ... ");
        datawriter.writeBeginPeople();
        this.generatePeople();
        datawriter.writeEndPeople();

        System.out.println("\nGenerating Webpage nodes ... ");
        datawriter.writeBeginWebpages();
        this.generateWebPages();
        datawriter.writeEndWebpages();

        System.out.println("\nGenerating friend edges ... ");
        datawriter.writeBeginFriends();
        this.generateFriends();
        datawriter.writeEndFriends();

        System.out.println("\nGenerating like edges ... ");
        datawriter.writeBeginLikes();
        this.generateLikes();
        datawriter.writeEndLikes();

        datawriter.writeEnd();

        datawriter.close();
        System.out.println("\n");
    }

    private void generatePeople() {
        Person person;
        long interval;
        if (queries_number < people_number) {
            interval = (long) people_number / queries_number;
        } else {
            interval = 1;
        }
        //long count = 0;
        try {
            for (long i = 1; i <= people_number; i++) {
                person = new Person(i, data);
                datawriter.writePerson(String.valueOf(i), person.name, person.age, person.location);
                nodes_counter++;
                people_counter++;
                this.printCounter(people_counter, people_number);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void generateWebPages() {
        long wpid;
        String wp_url;
        Webpage webpage;
        long interval;
        if (queries_number < webpages_number) {
            interval = (long) webpages_number / queries_number;
        } else {
            interval = 1;
        }
        //long count = 0;
        try {
            for (long i = 1; i <= webpages_number; i++) {
                wpid = people_number + i;
                webpage = new Webpage(wpid, data);
                datawriter.writeWebpage(String.valueOf(wpid), webpage.url, webpage.creation);
                nodes_counter++;
                webpages_counter++;
                this.printCounter(webpages_counter, webpages_number);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void generateFriends() {
        int[] nodes = new int[(int) people_number + 1];
        long id_person1;
        long id_person1Temp = -1;
        long id_person2;
        long count = 0;

        RMat rmat = new RMat((int) people_number, false, powerLawFriend);
        friends_number = rmat.edgesNumber();
        HashMap pathMap = new HashMap(1000000);
        Edge edge = rmat.nextEdge();

        while (edge != null) {
            id_person1 = edge.getSourceNode();
            id_person2 = edge.getTargetNode();
            nodes[(int) id_person1]++;
            nodes[(int) id_person2]++;
            edges_counter++;
            friends_counter++;
            datawriter.writeFriend(String.valueOf(edges_counter), String.valueOf(id_person1), String.valueOf(id_person2));
            this.printCounter(friends_counter, friends_number);

            edge = rmat.nextEdge();
        }

    }

    private void generateLikes() {
        String text = "";
        String backspaces;
        long count = 0;
        long id_person;
        long id_personTemp = 0;
        long id_webpage;
        long offset;

        RMat rmat = new RMat((int) people_number, true, powerLawLike);
        likes_number = rmat.edgesNumber();
        long interval_likes;
        if (queries_number < likes_number) {
            interval_likes = (long) (likes_number / queries_number) - 1;
        } else {
            interval_likes = 1;
        }

        Edge edge = rmat.nextEdge();
        while (edge != null) {
            //person like sourceNode
            edges_counter++;
            likes_counter++;
            //distribuyo los likes 
            id_person = edge.getSourceNode();
            id_webpage = (edge.getTargetNode() * webpages_number) / people_number + people_number;
            datawriter.writeLike(String.valueOf(edges_counter), String.valueOf(id_person), String.valueOf(id_webpage));
            this.printCounter(likes_counter, likes_number);

            edge = rmat.nextEdge();
        }

    }

    //unused method (but useful for future versions)
    private void generateTestData() {
        String text;
        Iterator it;
        String ids, id1, id2;
        samples_counter = 0;
        samples_number = queries_number * 6 + queries_number * 4;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("testdata.xml"), "UTF-8"));

            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n<testdata>");

            //People IDs
            it = people_ids.iterator();
            for (int i = 0; i < this.queries_number; i++) {
                if (!it.hasNext()) {
                    //Collections.shuffle(people_ids);
                    it = people_ids.iterator();
                }
                text = "\n  <data type='personID' value1='" + it.next() + "'/>";
                writer.write(text);
                samples_counter++;
                this.printCounter(samples_counter, samples_number);
            }

            //People IDs
            it = people_ids2.iterator();
            for (int i = 0; i < this.queries_number * 4; i++) {
                if (!it.hasNext()) {
                    //Collections.shuffle(people_ids);
                    it = people_ids2.iterator();
                }
                text = "\n  <data type='personID2' value1='" + it.next() + "'/>";
                writer.write(text);
                samples_counter++;
                this.printCounter(samples_counter, samples_number);
            }

            //People names
            it = people_names.iterator();
            for (int i = 0; i < this.queries_number; i++) {
                if (!it.hasNext()) {
                    it = people_names.iterator();
                }
                text = "\n  <data type='personName' value1='" + it.next() + "'/>";
                writer.write(text);
                samples_counter++;
                this.printCounter(samples_counter, samples_number);
            }

            //Webpage IDs
            it = webpage_ids.iterator();
            for (int i = 0; i < this.queries_number; i++) {
                if (!it.hasNext()) {
                    it = webpage_ids.iterator();
                }
                text = "\n  <data type='webpageID' value1='" + it.next() + "'/>";
                writer.write(text);
                samples_counter++;
                this.printCounter(samples_counter, samples_number);
            }

            //Likes 
            it = likes.iterator();
            for (int i = 0; i < this.queries_number; i++) {
                if (!it.hasNext()) {
                    it = likes.iterator();
                }
                ids = (String) it.next();
                id1 = ids.substring(0, ids.indexOf("-"));
                id2 = ids.substring(ids.indexOf("-") + 1, ids.length());
                text = "\n  <data type='like' value1='" + id1 + "' value2='" + id2 + "'/>";
                writer.write(text);
                samples_counter++;
                this.printCounter(samples_counter, samples_number);
            }

            //Friends
            it = friends.iterator();
            for (int i = 0; i < this.queries_number; i++) {
                if (!it.hasNext()) {
                    it = friends.iterator();
                }
                ids = (String) it.next();
                id1 = ids.substring(0, ids.indexOf("-"));
                id2 = ids.substring(ids.indexOf("-") + 1, ids.length());
                text = "\n  <data type='friend' value1='" + id1 + "' value2='" + id2 + "'/>";
                writer.write(text);
                samples_counter++;
                this.printCounter(samples_counter, samples_number);
            }

            //Paths
            Path path;
            it = paths.iterator();

            for (int i = 0; i < this.queries_number; i++) {
                if (!it.hasNext()) {
                    it = paths.iterator();
                }
                path = (Path) it.next();

                text = "\n  <data type='path' value1='" + path.getSourceNode() + "' value2='" + path.getTargetNode() + "' size='" + path.getPathSize() + "'/>";
                writer.write(text);
                samples_counter++;
                this.printCounter(samples_counter, samples_number);
            }

            writer.write("\n</testdata>");

            writer.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void printCounter(long counter, long final_value) {
        String last_counter = String.valueOf(counter - 1);
        String current_text = last_counter + " of " + final_value;
        String backspaces = "";
        if (counter > 1) {
            for (int b = 0; b < current_text.length(); b++) {
                backspaces = backspaces + "\b";
            }
        }
        String new_text = backspaces + counter + " of " + final_value;
        System.out.print(new_text);
    }
}
