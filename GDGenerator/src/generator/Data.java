package generator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Data {

    public ArrayList<String> mens_names;
    public ArrayList<String> womens_names;    
    ArrayList<String> lastnames;
    ArrayList<String> locations;
    ArrayList<String> languages;
    ArrayList<String> sexes;
    ArrayList<String> birthdays;
    Random rand = new Random(9999999);    

    public Data() {
        mens_names = loadNames("mens_names");
        womens_names = loadNames("womens_names");
        lastnames = loadNames("lastnames");
        locations = loadFile("locations");
        languages = loadFile("languages");
    }

    private ArrayList<String> loadNames(String name) {
        ArrayList<String> names = new ArrayList<String>();
        java.net.URL url = getClass().getResource("/dic/" + name);

        try {
            String sCadena;

            java.io.InputStream is = url.openStream();
            java.io.InputStreamReader isr = new java.io.InputStreamReader(is, "UTF-8");
            java.io.BufferedReader bf = new java.io.BufferedReader(isr);

            while ((sCadena = bf.readLine()) != null) {
                if (sCadena != null && !sCadena.equals("")) {
                    names.add(sCadena.split(" ")[0]);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return names;
    }
    

    private ArrayList<String> loadFile(String name) {
        ArrayList<String> s = new ArrayList<String>();

        java.net.URL url = getClass().getResource("/dic/" + name);

        try {
            java.io.InputStream is = url.openStream();
            java.io.InputStreamReader isr = new java.io.InputStreamReader(is, "UTF-8");
            java.io.BufferedReader reader = new java.io.BufferedReader(isr);

            String line = reader.readLine();

            while (line != null) {
                s.add(line.toUpperCase());
                line = reader.readLine();
            }

            return s;
        } catch (java.io.IOException e) {
            return s;
        }
    }

    public String getRandomName() {
        String name = "";
        if(rand.nextBoolean()){
            name = mens_names.get((int) (mens_names.size() * rand.nextDouble()));
        }else{
            name = womens_names.get((int) (womens_names.size() * rand.nextDouble()));
        }
        name = name + " " + lastnames.get((int) (lastnames.size() * rand.nextDouble()));
        return name;
    }

    public String getRandomLocation() {
        return locations.get((int) (rand.nextDouble() * locations.size() - 1));
    }

    public String getRandomLanguage() {
        return languages.get((int) (rand.nextDouble() * languages.size() - 1));
    }

    public String getRandomSex() {
        if(rand.nextBoolean()){
            return "Male";
        }else{
            return "Female";
        }
    }

    public String getRandomBirthday() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String sdate = formato.format(date);
        return sdate;
    }

    public String getRandomDate() {
        Date cdate = new Date(System.currentTimeMillis());
        int year = 2000 + (int)(rand.nextDouble() * 10);
        int month = 1 + (int)(rand.nextDouble() * 12);
        int day = 1+ (int)(rand.nextDouble() * 28);
        int days = (int)(rand.nextDouble() * 365);
        String date = year + "/" + month + "/" + day;
        return date;
    }
}
