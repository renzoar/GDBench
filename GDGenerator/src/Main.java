
import generator.*;
import java.io.FileWriter;
import java.io.PrintWriter;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author renzo
 */
public class Main {

    private static void printInfo() {
        System.out.println("GDGenerator allows the generation of syntetic graph data following a social network structure.");
        System.out.println("The data includes people and Webpages which are represented as nodes.");
        System.out.println("A person node has attributes like id (long,mandatory), name (string,mandatory), age (int), sex(string), birthday (date), language (string) and looking_to(string).");
        System.out.println("A Webpage node has two attributes, url (string) and title (string), both mandatory.");
        System.out.println("Edges are used to represent the relationships friend (undirected edge between people) and like (directed edge between a person and a Webpage)");
        System.out.println("The application generates a file with graph data (sndata.*), and a file with values for queries (testdata.xml)");
        System.out.println();
        System.out.println("Mandatory parameters:");
        System.out.println("-d  Percentage of nodes people, other nodes will be web pages (20 to 80, default 50%)");
        System.out.println("-n  Number of nodes (it results in 50% of people nodes and 50% of Webpage nodes)");
        System.out.println();
//        System.out.println("Optional parameters:");
//        System.out.println("-q  Number of samples in testdata (100 by default) ");
//        System.out.println();
        System.out.println("-f  Data format of the data file: gd = graph data (default), gml = Graphml, n3 = Notation3-RDF");
        System.out.println();
        System.out.println("-p  Power law uses, 1 if Friend and like distribution use power low(default), 2 Only Friend, 3 Only Like, 4 Nobody.");
        System.out.println();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        long nodes = 1000;
        int samples = 100; //default number of samples
        String format = "gd";
        int distribution =1; // Powerlaw default
        int d=50;
        if (args.length == 0) {
            printInfo();
            return;
        }

        try {
            for (int i = 0; i < args.length; i += 2) {
                if (args[i].equals("-n")) {
                    nodes = Long.parseLong(args[i + 1]);
                } else if (args[i].equals("-q")) {
                    samples = Integer.parseInt(args[i + 1]);
                } else if (args[i].equals("-f")) {
                    format = String.valueOf(args[i + 1]);
                } else if (args[i].equals("-d")) {
                    d = Integer.parseInt(args[i + 1]);
                }
                else if (args[i].equals("-p")) {
                    distribution =-1;
                    distribution = Integer.parseInt(args[i + 1]);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: Invalid parameters");
            return;
        }

        if (nodes == 0) {
            System.out.println("The number of nodes is mandatory and must be greater than cero.");
            return;
        }

        if (samples <= 0) {
            System.out.println("The number of queries must be greater than cero");
            return;
        }
        if (samples*4 > nodes) {
            System.out.println("The number of queries*4 must be minor than nodes");
            return;
        }
        if (distribution>4 || distribution<1) {
            System.out.println("Distribution only works between 1 and 4");
            return;
        }
        if (d<20 || d>80) {
            System.out.println("Percentage of nodes people, only works between 20% and 80%");
            return;
        }
        


        int people = (int)(nodes * d/100);
        int pages = (int)(nodes * (100-d)/100);

        DataWriter datawriter;
        if (format.compareTo("gml") == 0) {
            datawriter = new GraphmlWriter();
        }else if(format.compareTo("n3") == 0){
            datawriter = new N3Writer();
        } else {
            //default: formato comprimido GD
            datawriter = new GDWriter();
        }
        Generator generator;
        if(distribution==1)
            generator = new Generator(people, pages, samples, datawriter,true,true);
        else if (distribution ==2)
            generator = new Generator(people, pages, samples, datawriter,true,false);
        else if (distribution ==3)
            generator = new Generator(people, pages, samples, datawriter,false,true);
        else
            generator = new Generator(people, pages, samples, datawriter,false,false);
        long ctime = System.currentTimeMillis();
        generator.run();
        long dtime = System.currentTimeMillis() - ctime;
        

        String text = "";
        text += "\nSummary of data generation\n";
        text += "- Number of Person nodes: " + generator.people_counter + "\n";
        text += "- Number of Webpage nodes: " + generator.webpages_counter + "\n";
        text += "- Number of Friend edges: " + generator.friends_counter + "\n";
        text += "- Number of Like edges: " + generator.likes_counter + "\n";
        text += "- Total number of nodes: " + generator.nodes_counter + "\n";
        text += "- Total number of edges: " + generator.edges_counter + "\n";
        text += "- Execution time: " + dtime + " ms\n";
        //text += "- Number of samples in testdata: " + samples + "\n";

        System.out.println(text);

        try {
            FileWriter file = new FileWriter("GDGeneratorReport.txt");
            PrintWriter writer = new PrintWriter(file);
            writer.println(text);
            writer.close();
        } catch (Exception e) {
            System.out.println("Unable to write generation report");
            System.out.println(e.getMessage());
        }

    }
}
