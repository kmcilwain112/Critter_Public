
package Critter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;


/**
 *
 * @author kmcil
 */
public class UserModel {
  
    
    public static boolean login(User user){
        try{
            Connection connection = DatabaseConnection.getConnection();
            
            String query = "select id, username, password from user where username = ? ";
            PreparedStatement statement = connection.prepareStatement(query);
            
            statement.setString(1, user.getUsername());
            ResultSet results = statement.executeQuery();
            
            String password = "";
            if (results.next()){
                password = results.getString("password");               
            }
            
            results.close();
            statement.close();
            connection.close();
            
            return !password.isEmpty() && user.getPassword().equals(password);
                   
        }
        catch (Exception ex){
            System.out.println(ex);
            return false;
        }
    }
    
    public static User getUser(String username){
        User user = null;
        try{
            Connection connection = DatabaseConnection.getConnection();
            String query = "select id, username, filename, password from user where username = ? ";
            PreparedStatement statement = connection.prepareStatement(query);
            
            statement.setString(1, username);
            ResultSet results = statement.executeQuery();
            
            while(results.next()){
                int id = results.getInt("id");
                String password = results.getString("password");
                String filename = results.getString("filename");
                
                user = new User(id, username, password, filename);
            }
            
            results.close();
            statement.close();
            connection.close();
        }
        catch (Exception ex){
            System.out.println();
        }
        
        return user;
    }
    public static ArrayList<User> getUsers(String followerUsername){
        ArrayList<User> users = new ArrayList<>();
        try{
           Connection connection = DatabaseConnection.getConnection();
            
            String query = " select username, filename from user " ;
           
            PreparedStatement statement = connection.prepareStatement(query);
            
            ResultSet results = statement.executeQuery();
            
            while(results.next()){
                
                String username = results.getString("username");
                String filename = results.getString("filename");
                
                User user = new User(0, username, "", filename);
                
                users.add(user);
                if(user.getUsername().contains(followerUsername)){
                users.remove(user);}
            }
            
            results.close();
            statement.close();
            connection.close(); 
        }
        catch (Exception ex){
            System.out.println(ex);
        }
        return users;
    }
    public static void addUser(User user){
        try{
            Connection connection = DatabaseConnection.getConnection();
            String query = "insert into user (username, password) values (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);            
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.execute();
            statement.close();
            connection.close();            
        }
        catch(Exception ex){
            System.out.println();
        }
    }
 
    
    public static void updateUser (User user){
        try{
            Connection connection = DatabaseConnection.getConnection();
            String query = "update user set username = ? , password = ? where id = ? ";
            PreparedStatement statement = connection.prepareStatement(query);
            
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.execute();
            statement.close();
            connection.close();
        }
        catch(Exception ex){
            System.out.println();
        }
            
    }
    public static ArrayList<Tweet> getFollowedTweets(String username){
        ArrayList<Tweet> followedTweets = new ArrayList<>();
        
        try{
           
           Connection connection = DatabaseConnection.getConnection();
            String query = " "
                    + " select t.id, t.text, t.timestamp, t.filename, ui.username, ui.filename as avi, t.filename from tweet t "
                    + "left join following f on t.user_id = f.followed_id "
                    + "left join user u on u.id = f.follower_id "
                    + "left join user ui on ui.id = f.followed_id "
                    + "where u.username = ? and f.follower_id = u.id ";
            PreparedStatement statement = connection.prepareStatement(query);
            
            statement.setString(1, username);
            
            ResultSet results = statement.executeQuery();            
            
            while(results.next()){
                
                int id = results.getInt("id");
                String text = results.getString("text");
                Timestamp timestamp = results.getTimestamp("timestamp");
                String followedUser = results.getString("username");
                String filename = results.getString("filename");
                String avi = results.getString("avi");
                
                Tweet chirp = new Tweet(id, text, timestamp, followedUser, filename, avi);
                
                followedTweets.add(chirp);
                
                
            }
            
            results.close();
            statement.close();
            connection.close(); 
        }
        catch (Exception ex){
            System.out.println(ex);
        }
        return followedTweets;
    }
    public static String getFollowed(String username, String whoToFollow){
       String showbtn = "";
        
        try{
           
           Connection connection = DatabaseConnection.getConnection();
            String query = " "
                    + " select f.follower_id, f.followed_id from following f "
                   + "left join user u on u.id = f.follower_id "
                    + "left join user ui on ui.id = f.followed_id "
                    + "where u.username = ? and ui.username = ? ";
            PreparedStatement statement = connection.prepareStatement(query);
            
            statement.setString(1, username);
            statement.setString(2, whoToFollow);
            
            ResultSet results = statement.executeQuery();            
            Integer follower = 0;
            Integer followed = 0;
            while(results.next()){ 
                follower = results.getInt("follower_id");                               
                followed = results.getInt("followed_id");
            }
            
            results.close();
            statement.close();
            connection.close(); 
            
            if (follower == 0 || followed == 0){
                showbtn = "follow";
            } else{
                showbtn = "unfollow";
            }
            
        }
        catch (Exception ex){
            System.out.println(ex);         
        }
       return showbtn;
    }
    public static ArrayList<Tweet> getTweets(String username){
        ArrayList<Tweet> tweets = new ArrayList<>();
        
        try{
           
           Connection connection = DatabaseConnection.getConnection();
            String query = " "
                    + " select u.username, t.id, t.text, t.timestamp, t.filename, u.filename as avi"
                    + " from tweet as t "
                    +  "left join user as u on u.id = t.user_id "
                    + "where u.username = ?  ";
            PreparedStatement statement = connection.prepareStatement(query);
            
            statement.setString(1, username);
            
            ResultSet results = statement.executeQuery();            
            
            while(results.next()){
                
                int id = results.getInt("id");
                String text = results.getString("text");
                Timestamp timestamp = results.getTimestamp("timestamp");
                String filename = results.getString("filename");
                String avi = results.getString("avi");
                Tweet chirp = new Tweet(id, text, timestamp, username, filename, avi);
                
                tweets.add(chirp);
                
                
            }
            
            results.close();
            statement.close();
            connection.close(); 
        }
        catch (Exception ex){
            System.out.println(ex);
        }
        return tweets;
    }
    public static void unFollow(String username, String whoToFollow){
        try{
           
           Connection connection = DatabaseConnection.getConnection();
            String query = " "
                    + " DELETE f FROM `following` AS f "+
                       " LEFT JOIN user as u on (f.follower_id = u.id) "+
                       " LEFT JOIN user as ui on (f.followed_id = ui.id) " +
                       " WHERE u.username = ? " +
                       " and ui.username = ? ";
            PreparedStatement statement = connection.prepareStatement(query);
            
            statement.setString(1, username);
            statement.setString(2, whoToFollow);
            
            ResultSet results = statement.executeQuery();
            
            results.close();
            statement.close();
            connection.close(); 
        }
        catch (Exception ex){
            System.out.println(ex);
        }
       
    }
    public static void Follow(int follower, int followed){
        try{
           
           Connection connection = DatabaseConnection.getConnection();
            String query = " INSERT INTO following (follower_id, followed_id) values (?, ?) ";
            PreparedStatement statement = connection.prepareStatement(query);          
            statement.setInt(1, follower);
            statement.setInt(2, followed);           
            statement.execute();           
            statement.close();
            connection.close(); 
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }
    
    
}
