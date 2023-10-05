<%-- 
    Document   : Profile
    Created on : Apr 19, 2023, 2:14:00 PM
    Author     : kmcil
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link href="<c:url value="/CSS/Profile.css" />" rel="stylesheet">
        <title>Profile</title>
    </head>
    <body>
        <h1 id="logolabel">Critter</h1>
        <img id= "logo" src='<c:url value="/files/CritterLogo.jpg"></c:url>'/>
        <br>
        <br>
        <form action="CritterControl" method="post" id="backToFeed">
           <input type="hidden" name="action" value="backToFeed">
        <input form="backToFeed" class="btn btn-primary homebtn" name="whoToFollow" type="submit" value="Home">
        </form>
        <br>
        <div class="wrapper">
        <nav id="Sidebar"> 
            <form class="uploadpropic" action="Upload" method="post" enctype="multipart/form-data">
            <c:choose>
                     <c:when test="${filename != null}">
                         <img id="avi" src="GetImage?username=${username}" width="100" height="100"/>
                     </c:when> 
            <c:otherwise>
                <img id="avi" src='<c:url value="/files/default.jpg"></c:url>' width="100" height="100"/>    
            </c:otherwise>
            </c:choose>
                <br>
                <br>
        
      
            <label for="file">Upload a Profile Pic</label><br>
            <div class="form-group row" id="data">                               
                <div class="col-sm-7">
                <input class="form-control" type="file" accept="image/*" name="file">
                </div>
            </div><br>
            <div id="buttons">
                <label>&nbsp;</label>
                <input class="btn btn-primary" type="submit" value="Upload"><br>
            </div>
        </form>
        
           <br>        
        </nav>
            <div id="content">
            <div class="container">
                
        <form name="chirp" action="Upload" method="post" enctype="multipart/form-data">
            <h3>Hi ${username}, Create A Chirp!</h3>
            <div class="form-group">
                <textarea class="form-control" id="text" name="text" rows="5" cols="75">
                              
                </textarea>               
            </div>
            <div id="data">
                <label>Add A Pic!</label><br>
                <input type="file" accept="image/*" name="file">               
            </div>
            <div>
                <input type="hidden" name="action" value="createtweet">               
            </div>
            <br>            
            <div class="chirpbtn">
                <label>&nbsp;</label>
                <input class="btn btn-primary" type="submit" value="Chirp!"><br>
            </div>
        </form>
           <h4>Chirps</h4>
           <br>
           <% int incrmnt = 1; %>
           <c:forEach var="chirp" items="${tweets}">
              
           <table>
               <tr>
               <th><c:out value="${chirp.username}" /></th>
               <th></th>
               <th></th>
               <th></th>
               <th></th>
               </tr>                           
                <tr>
                   <td class="tavi"> 
                     <c:choose>
                     <c:when test="${chirp.avi != null}">
                         <img id="avi" src="GetImage?username=${chirp.username}" width="100" height="100"/>
                     </c:when> 
                    <c:otherwise>
                <img id="avi" src='<c:url value="/files/default.jpg"></c:url>' width="100" height="100"/>    
                </c:otherwise>
                </c:choose>                 
                   </td>                  
                   <td></td>
                   <td><c:out value="${chirp.text}" /></td>
                   <c:if test="${chirp.filename != null}">
                   <td> <img src="GetTweetImage?username=${chirp.username}" width="100" height="100"/></td>
                   </c:if>
                   <td> </td>
                </tr>               
                <tr >            
                    <td></td>
                    <td> <p></p></td>
                    <td></td>
                    <td> </td>
                    <td> </td>
                </tr>
                <tr >
                    <td class="trow"> <p></p></td>
                    <td class="trow"> <p></p></td>
                    <td class="trow"> <c:out value="${chirp.timestamp}" /> </td>
                    <td class="trow"> <button name="likebtn" id="likebtn_<%=incrmnt%>" class="btn btn-primary">Like</button>
                    </td>
                    <td class="trow"> <p id="result_<%=incrmnt%>">0</p></td>
                </tr>                                              
                </table>
                    <br>
                    <% incrmnt++; %>
                    </c:forEach> 
                    <script>
                        //https://stackoverflow.com/questions/46956587/only-first-button-opens-in-jsp-js-foreach-loop
                        var buttons = document.getElementsByName("likebtn");
                        for (var i=0;i<buttons.length;i++) {
                        buttons[i].addEventListener("click", clickHandler);
                        }
                        function clickHandler(event) {
                            var buttonId = event.target.id;	
                            var rowId = buttonId.split("_")[1];
                            var rowData = document.getElementById('result_' + rowId);
                            var up = rowData.innerHTML;
                            up++;
                            rowData.innerHTML = up;
                        }
                        
               </script>
                </div>                                   
                </div>
        </div>
    </body>
</html>
