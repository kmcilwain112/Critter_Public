package Critter;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author EricC
 */
@MultipartConfig(maxFileSize = 1000000)
public class Upload extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        
        String action = request.getParameter("action");
       
        
        if(action == null){
            String url = "/Feed.jsp";
            getServletContext().getRequestDispatcher(url).forward(request, response);
        } else if(action.equalsIgnoreCase("profile")){

        // https://www.codejava.net/coding/upload-files-to-database-servlet-jsp-mysql
        InputStream inputStream = null; // input stream of the upload file
        String fileName = "";
        // obtains the upload file part in this multipart request
        Part filePart = request.getPart("file");
        if (filePart != null) {
            fileName = extractFileName(filePart);
            // obtains input stream of the upload file
            inputStream = filePart.getInputStream();
        }

        try {
            HttpSession session = request.getSession();
            String username = session.getAttribute("username").toString();

            Connection connection = DatabaseConnection.getConnection();
            String preparedSQL = "update user set image = ?, filename = ? "
                    + " where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(preparedSQL);

            // index starts at 1?
            preparedStatement.setBlob(1, inputStream);
            preparedStatement.setString(2, fileName);
            preparedStatement.setString(3, username);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            String url = "/Profile";
            getServletContext().getRequestDispatcher(url).forward(request, response);

        } catch (SQLException ex) {
            request.setAttribute("error", ex.toString());
            String url = "/error.jsp";
            getServletContext().getRequestDispatcher(url).forward(request, response);
        } catch (ClassNotFoundException ex) {
            request.setAttribute("error", ex.toString());
            String url = "/error.jsp";
            getServletContext().getRequestDispatcher(url).forward(request, response);
        
        }
     }
        else if(action.equalsIgnoreCase("createtweet")){
                
                HttpSession session = request.getSession();
                String username = session.getAttribute("username").toString();
                int user_id = UserModel.getUser(username).getId();
                String text = request.getParameter("text");
                
                 // https://www.codejava.net/coding/upload-files-to-database-servlet-jsp-mysql
                 InputStream inputStream = null; // input stream of the upload file
                 String fileName = "";

                 // obtains the upload file part in this multipart request
                 Part filePart = request.getPart("file");
                 if (filePart != null) {
                     fileName = extractFileName(filePart);
                     // obtains input stream of the upload file
                     inputStream = filePart.getInputStream();
                 }

        try {
            
            Connection connection = DatabaseConnection.getConnection();
            String preparedSQL = "insert into tweet (text, user_id, filename, image) values (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(preparedSQL);

            // index starts at 1?
            preparedStatement.setString(1, text);
            preparedStatement.setInt(2, user_id);
            preparedStatement.setString(3, fileName);
            preparedStatement.setBlob(4, inputStream);
            
            

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
                            
                
                User user = UserModel.getUser(username);
                request.setAttribute("username",username);
                ArrayList<Tweet> tweets = UserModel.getTweets(username);
                request.setAttribute("tweets",tweets);
                request.setAttribute("filename", user.getFilename());
                String url = "/Profile.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);

        } catch (SQLException ex) {
            request.setAttribute("error", ex.toString());
            String url = "/error.jsp";
            getServletContext().getRequestDispatcher(url).forward(request, response);
        } catch (ClassNotFoundException ex) {
            request.setAttribute("error", ex.toString());
            String url = "/error.jsp";
            getServletContext().getRequestDispatcher(url).forward(request, response);
        
        }
        
        }
  }

    //https://www.codejava.net/java-ee/servlet/java-file-upload-example-with-servlet-30-api
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
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
