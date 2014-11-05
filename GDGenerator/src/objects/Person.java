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
        rand = new Random(ID);
        this.name = data.getRandomName();

        rand.nextBoolean();
        
        if(rand.nextBoolean()){
            Double a = Math.ceil(rand.nextDouble() * 100);
            Long v = a.longValue();
            this.age = v.toString();
        }
        
        if(rand.nextBoolean()){
            this.location = data.getRandomLocation();
        }
        
    }
}
