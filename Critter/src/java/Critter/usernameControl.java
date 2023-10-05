/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Critter;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author kmcil
 */
@WebServlet(name = "usernameControl", urlPatterns = {"/usernameControl"})
public class usernameControl extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
            
            if (!Login.ensureUserIsLoggedIn(request)){
                request.setAttribute("message", "you must login");
                response.sendRedirect("Login");
                return;    
            }
            if (action == null){
                action = "backToFeed";
            }
            if (action.equalsIgnoreCase("backToFeed")){
                
                    HttpSession session = request.getSession();
                    session.setAttribute("username", session.getAttribute("username"));
                    ArrayList<User> users = UserModel.getUsers(session.getAttribute("username").toString());
                    ArrayList<Tweet> followedTweets = UserModel.getFollowedTweets(session.getAttribute("username").toString());
                    request.setAttribute("users", users);
                    request.setAttribute("followedTweets", followedTweets);
                    String url = "/Feed.jsp";
                    getServletContext().getRequestDispatcher(url).forward(request, response);
            } 
                 else if (action.equalsIgnoreCase("updateUser")){
                String id = request.getParameter("id");
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                    if(id == null || username == null || password == null){
                        String error = "id or username or password is missing!";
                        request.setAttribute("error", error);
                        String url = "/error.jsp";
                        getServletContext().getRequestDispatcher(url).forward(request, response);
                    }
                    
            }
                   
            else if (action.equalsIgnoreCase("whoToFollowAction")){
                String whoToFollow = request.getParameter("whoToFollow");
                User critterUser = UserModel.getUser(whoToFollow);
                request.setAttribute("filename",critterUser.getFilename());
                request.setAttribute("whoToFollow", critterUser.getUsername());
                HttpSession session = request.getSession();                 
                request.setAttribute("username",session.getAttribute("username"));                             
                session.setAttribute("btncondition", UserModel.getFollowed(session.getAttribute("username").toString(), whoToFollow ));                      
                ArrayList<Tweet> tweets = UserModel.getTweets(whoToFollow);
                request.setAttribute("tweets",tweets);
                request.setAttribute("whoToFollow", whoToFollow);
                String url = "/username.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            }
            else if (action.equalsIgnoreCase("follow")){
                String whoToFollow = request.getParameter("whoToFollow");
                int followedID = UserModel.getUser(whoToFollow).getId();
                String username = request.getParameter("username");
                int followerID = UserModel.getUser(username).getId();
                UserModel.Follow(followerID, followedID);
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                ArrayList<User> users = UserModel.getUsers(username);
                ArrayList<Tweet> followedTweets = UserModel.getFollowedTweets(username);
                request.setAttribute("users", users);
                request.setAttribute("followedTweets", followedTweets);               
                response.sendRedirect("/Feed.jsp");
            }
            else if (action.equalsIgnoreCase("unfollow")){
                String whoToFollow = request.getParameter("whoToFollow");
                Integer followedID = UserModel.getUser(whoToFollow).getId();
                String username = request.getParameter("username");
                Integer followerID = UserModel.getUser(username).getId();
                UserModel.Follow(followerID, followedID);
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                ArrayList<User> users = UserModel.getUsers(username);
                ArrayList<Tweet> followedTweets = UserModel.getFollowedTweets(username);
                request.setAttribute("users", users);
                request.setAttribute("followedTweets", followedTweets);
                String url = "/Feed.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            }     
            
    }
    private void exceptionPage(Exception ex, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String error = ex.toString();
        request.setAttribute("error", error);
        String url = "/error.jsp";
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }
    
    public static String toHexString(byte[] hash){
        BigInteger number = new BigInteger(1,hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        
        while(hexString.length() < 32){
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
