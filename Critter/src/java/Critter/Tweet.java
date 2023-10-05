
package Critter;

import java.io.Serializable;
import java.sql.Timestamp;


public class Tweet implements Serializable {

    public String getFilename() {
        return filename;
    }
    private int id;
    private String text;
    private Timestamp timestamp;
    private int user_id;
    private String username;
    private String filename;
    private String avi;
    
    public Tweet(int id, String text,Timestamp timestamp, int user_id){
        this.id=id;
        this.text=text;
        this.timestamp=timestamp;
        this.user_id=user_id;
    }
    public Tweet(int id, String text,Timestamp timestamp, String username){
        this.id=id;
        this.text=text;
        this.timestamp=timestamp;
        this.username=username;
    }
    public Tweet(int id, String text,Timestamp timestamp, String username, String filename, String avi){
        this.id=id;
        this.text=text;
        this.timestamp=timestamp;
        this.username=username;
        this.filename=filename;
        this.avi=avi;
    }
    
    

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
    
    

    public String getText() {
        return text;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getAvi() {
        return avi;
    }
    
    
}
