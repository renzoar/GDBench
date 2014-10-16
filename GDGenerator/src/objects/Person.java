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
public class Person {

    public long id;
    public String name = "?";
    public String age = "?";
    public String location = "?";
    Random rand = new Random(9999999);    
    
    public Person(long ID, Data data) {
        this.id = ID;
        this.name = data.getRandomName();

        if ((int) (rand.nextDouble() * 100) <= 60) {
            this.age = String.valueOf((int) (rand.nextDouble() * 70) + 10);
        }

        if ((int) (rand.nextDouble() * 100) <= 30) {
            this.location = data.getRandomLocation();
        }
    }
}
