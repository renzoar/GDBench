/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 *
 * @author renzo
 */
public class CSVWriter extends DataWriter{
    
    Writer people_writer = null;
    Writer webpages_writer = null;
    Writer friends_writer = null;
    Writer likes_writer = null;    
       
    public CSVWriter(){
        
    }
    
   @Override
    public void writeBegin() {

    }

    @Override
    public void writeBeginPeople() {
        try {
            people_writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("people.csv"), "UTF-8"));
            people_writer.write("id,name,age,location\n" );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    @Override
    public void writePerson(String pid, String name, String age, String location) {
        String line = pid + "," + name + "," + age + "," + location + "\n";
        try {
            this.people_writer.write(line);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }        
    }

    @Override
    public void writeEndPeople() {
       try {
            people_writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }        
    }

    @Override
    public void writeBeginWebpages() {
        try {
            webpages_writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("webpages.csv"), "UTF-8"));
            webpages_writer.write("id,url,creation\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    @Override
    public void writeWebpage(String wpid, String url, String creation) {
        String line = wpid + "," + url + "," + creation + "\n";
        try {
            this.webpages_writer.write(line);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }        
    }

    @Override
    public void writeEndWebpages() {
       try {
            webpages_writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }        
    }

    @Override
    public void writeBeginFriends() {
        try {
            friends_writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("friends.csv"), "UTF-8"));
            friends_writer.write("id,personId1,personId2\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        
    }

    @Override
    public void writeFriend(String id, String pid1, String pid2) {
        String line = id + "," + pid1 + "," + pid2 + "\n";
        try {
            this.friends_writer.write(line);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }        
    }

    @Override
    public void writeEndFriends() {
       try {
            friends_writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }        
    }

    @Override
    public void writeBeginLikes() {
        try {
            likes_writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("likes.csv"), "UTF-8"));
            likes_writer.write("id,personId,webpageId\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        
    }

    @Override
    public void writeLike(String id, String pid, String wpid) {
        String line = id + "," + pid + "," + wpid + "\n";
        try {
            this.likes_writer.write(line);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }        

    }

    @Override
    public void writeEndLikes() {
       try {
            likes_writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }        
    }

    @Override
    public void writeEnd() {
    }
    
}
