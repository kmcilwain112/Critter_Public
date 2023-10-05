<%-- 
    Document   : CreateTweet
    Created on : Apr 20, 2023, 1:15:15 PM
    Author     : kmcil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create A Tweet</title>
    </head>
    <body>
        <h2>Hi ${username}! </h2>
        <h3>Create A Chirp!</h3>
        <form action="Upload" method="post" enctype="multipart/form-data">
            <div>
                <textarea id="text" name="text" rows="5" cols="33">
                              
                </textarea>               
            </div>
            <div id="data">
                <label>Add A Pic!</label><br>
                <input type="file" accept="image/*" name="file">               
            </div>
            <div>
                <input type="hidden" name="action" value="createtweet">               
            </div>            
            <div id="buttons">
                <label>&nbsp;</label>
                <input type="submit" value="Chirp!"><br>
            </div>
        </form>
    </body>
</html>
