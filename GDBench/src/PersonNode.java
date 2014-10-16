

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author renzo
 */
public class PersonNode implements Comparable<PersonNode>{
    long id;
    String name;
    Long cAmigos;

    public PersonNode(long id, String name, long cAmigos) {
        this.id = id;
        this.name = name;
        this.cAmigos = cAmigos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getcAmigos() {
        return cAmigos;
    }

    public void setcAmigos(long cAmigos) {
        this.cAmigos = cAmigos;
    }

    

    @Override
    public int compareTo(PersonNode o) {
        return cAmigos.compareTo(o.getcAmigos());
    }
    
    
}
