
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 *
 * @author renzo
 */
public class Main {

    public static void main(String[] args) {
        boolean D = true;
        boolean Q = true;
        int q = 0;
        int i = 100;
        String o = "s";
        int r = 3;
        String t = "r";

        try {

            File file = new File("gdbench.conf");
            if (!file.exists()) {
                System.out.println("The GDBench configuration file \"gdbench.conf\" does not exist.");
                if (!createConfigFile()) {
                    return;
                }
                System.out.println("A default \"gdbench.conf\" file was created.");
            }

            System.out.println("Reading configuration File ...");
            FileInputStream fstream = new FileInputStream("gdbench.conf");
            DataInputStream input = new DataInputStream(fstream);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
            D = Boolean.valueOf(buffer.readLine().split("=")[1]);
            Q = Boolean.valueOf(buffer.readLine().split("=")[1]);
            q = Integer.valueOf(buffer.readLine().split("=")[1]);
            i = Integer.valueOf(buffer.readLine().split("=")[1]);
            o = buffer.readLine().split("=")[1];
            r = Integer.valueOf(buffer.readLine().split("=")[1]);
            t = buffer.readLine().split("=")[1];
            input.close();
        } catch (Exception e) { //Catch de excepciones
            System.out.println("Unable to read the configuration file - Error in class " + e.getClass().getName());
            return;
        }

        if (q < 0 || q > 13) {
            System.out.println("Error in parameter #Query");
            return;
        }

        if (i < 0) {
            System.out.println("Error in parameter #Instances");
            return;
        }

        if (o.compareTo("s") != 0 && o.compareTo("r") != 0) {
            System.out.println("Error in parameter #ExecutionOrder");
            return;
        }

        if (r < 1) {
            System.out.println("Error in parameter #Repetitions");
            return;
        }

        int td = 1;
        if (t.compareTo("r") == 0) {
            td = 1;
        } else if (t.compareTo("i") == 0) {
            td = 2;
        } else if (t.compareTo("m") == 0) {
            td = 3;
        } else {
            System.out.println("Error in parameter #TestData");
            return;

        }

        MyTestDriver testdriver = new MyTestDriver();
        if (D == true) {
            testdriver.runDataLoading();
        }

        if (Q == true) {
            if (q == 0) {
                testdriver.runQueryTest(i, o, r, td);
            } else {
                testdriver.runQueryTestByQuery(q, i, o, r, td);
            }
        }
    }

    private static boolean createConfigFile() {
        try {
            FileWriter file = new FileWriter("gdbench.conf");
            PrintWriter writer = new PrintWriter(file);
            writer.println("DataLoading=true");
            writer.println("QueryTest=true");
            writer.println("Query=0");
            writer.println("Instances=100");
            writer.println("ExecutionOrder=s");
            writer.println("Repetitions=3");
            writer.println("TestData=r");
            writer.println("");
            writer.println("--- Parameters ---");
            writer.println("DataLoading=true|false : Run data loading test.");
            writer.println("QueryTest=true|false   : Run query test for all queries.");
            writer.println("Query=#                : Run query test for query type # (1 <= # <= 13). If # = 0 then run query test for all queries.");
            writer.println("Instances=#            : Defines the number # of instances for query to be executed.");
            writer.println("ExecutionOrder=s|r     : Defines the query execution order (s = sequential, r = random).");
            writer.println("Repetitions=#          : Defines the number # of times that each instance query will be executed.");
            writer.println("TestData=r|i|m          : Defines the method for test data selection (r = random, i = interval, m = media-based)");
            writer.close();
            return true;
        } catch (IOException e) {
            System.out.println("Unable to create configuration file - Error in class " + e.getClass().getName());
            return false;
        }
    }
}
