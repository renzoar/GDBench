package objects;

import java.util.ArrayList;
import java.util.Random;

public class Triangle {

    Random rand = new Random(9999999);
    public long a, b;
    ArrayList<Long> a_friends, b_friends;

    public Triangle(long a, long b) {
        this.a = a;
        this.b = b;

        a_friends = new ArrayList<Long>();
        b_friends = new ArrayList<Long>();
    }

    public Triangle(long nodesSize) {
        this.a = (long) (rand.nextDouble() * nodesSize);
        this.b = (long) (rand.nextDouble() * nodesSize);
    }

    public boolean add(long id, long id_friend) {
        if (id == a) {
            if (b_friends.contains(id_friend)) {
                return true;
            } else {
                a_friends.add(id_friend);
            }
        } else if (id == b) {
            if (a_friends.contains(id_friend)) {
                return true;
            } else {
                b_friends.add(id_friend);
            }
        }

        return false;
    }
}
