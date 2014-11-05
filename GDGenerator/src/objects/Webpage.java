/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import generator.Data;
import java.util.Random;

/**
 *
 * @author renzo
 */
public class Webpage {

    public long id;
    public String url;
    public String creation = "?";
    Random rand = new Random(9999999);

    public Webpage(long ID, Data data) {
        rand = new Random(ID);
        this.id = ID;
        this.url = "http://www.site.org/webpage" + ID + ".html";
        rand.nextBoolean();
        if (rand.nextBoolean()) {
            this.creation = data.getRandomDate();
        }

    }
}
