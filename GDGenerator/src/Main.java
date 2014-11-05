
import generator.*;
import java.io.FileWriter;
import java.io.IOException;
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
        System.out.println("The data schema includes people and webpages which are represented as nodes.");
        System.out.println("A person node has attributes id (long), name (string), age (int,optional) and location (string,optional).");
        System.out.println("A webpage node has attributes id (long), url (string) and creation (date,optional).");
        System.out.println("Edges are used to represent the relationships friend (undirected edge between people) and like (directed edge between a person and a webpage)");
        System.out.println("The user is able to select between normal and powerlaw distributions for edges friend and like.");
        System.out.println("The application creates a file sndata.* in any of the following formats: plain format (*.gd), GrapML (*.gml) and Notation3-RDF (*.n3)");
        System.out.println();
        System.out.println("Mandatory parameters:");
        System.out.println("-n  Number of nodes (people + webpage)");
        System.out.println("Optional parameters:");
        System.out.println("-p  Integer indicating the percentage of nodes people (the rest will be web pages)(default 50%)");
        System.out.println("-f  Data format of the data file: gd = graph data (default), gml = GraphML, n3 = Notation3-RDF");
        System.out.println("-d  Statistical distibutions for edges friend/like: 1 = powerlaw/powerlaw (default), 2 = powerlaw/normal, 3 = normal/powerlaw, 4 = normal/normal)");
        System.out.println();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        long nodes = 1000;
        String format = "gd";
        int d = 1; // Powerlaw default
        int p = 50;
        if (args.length == 0) {
            printInfo();
            return;
        }

        try {
            for (int i = 0; i < args.length; i += 2) {
                if (args[i].equals("-n")) {
                    nodes = Long.parseLong(args[i + 1]);
                } else if (args[i].equals("-p")) {
                    p = Integer.parseInt(args[i + 1]);
                } else if (args[i].equals("-d")) {
                    d = Integer.parseInt(args[i + 1]);
                } else if (args[i].equals("-f")) {
                    format = String.valueOf(args[i + 1]);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid parameters");
            return;
        }

        if (nodes == 0) {
            System.out.println("The number of nodes is mandatory and must be greater than cero.");
            return;
        }

        if (d > 4 || d < 1) {
            System.out.println("Distribution only works between 1 and 4");
            return;
        }
        if (p < 20 || p > 80) {
            System.out.println("Percentage of nodes people, only works between 20% and 80%");
            return;
        }

        int people = (int) (nodes * p / 100);
        int pages = (int) (nodes * (100 - p) / 100);

        DataWriter datawriter;
        if (format.compareTo("gml") == 0) {
            datawriter = new GraphmlWriter();
        } else if (format.compareTo("n3") == 0) {
            datawriter = new N3Writer();
        } else {
            datawriter = new GDWriter();
        }
        Generator generator;
        if (d == 1) {
            generator = new Generator(people, pages, 1, datawriter, true, true);
        } else if (d == 2) {
            generator = new Generator(people, pages, 1, datawriter, true, false);
        } else if (d == 3) {
            generator = new Generator(people, pages, 1, datawriter, false, true);
        } else {
            generator = new Generator(people, pages, 1, datawriter, false, false);
        }
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

        System.out.println(text);

        try {
            FileWriter file = new FileWriter("GDGeneratorReport.txt");
            PrintWriter writer = new PrintWriter(file);
            writer.println(text);
            writer.close();
        } catch (IOException ex) {
            System.out.println("Unable to write generation report");
            System.out.println(ex.getMessage());
        }

    }
}
