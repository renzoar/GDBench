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
    public String creation = "?";
    Random rand = new Random(9999999);

    public Webpage(long ID, Data data) {
        this.id = ID;

        if ((int) (rand.nextDouble() * 100) <= 60) {
            this.creation = data.getRandomDate();
        }

    }
}
