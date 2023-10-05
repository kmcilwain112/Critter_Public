<%-- 
    Document   : Feed
    Created on : Apr 18, 2023, 9:14:16 AM
    Author     : kmcil
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<!-- https://www.javaguides.net/2020/01/add-bootstrap-to-jsp-page.html-->
<script type="text/javascript">
    function submitForm(){
       document.getElementById("whoToFollowForm").submit(); 
    }
</script>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link rel=”stylesheet” href=”https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css”/>
        <link rel=”stylesheet” href=”https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css” />
        <link href="<c:url value="/CSS/Feed.css" />" rel="stylesheet">
        <title>Critter</title>
    </head>
    <body>
        
        <h1>Critter</h1>
        <img id= "logo" src='<c:url value="/files/CritterLogo.jpg"></c:url>'/>
        <br>
        <br>
        <form action="CritterControl" method="post" id="backToFeed">
           <input type="hidden" name="action" value="backToFeed">
        <input form="backToFeed" class="btn btn-primary homebtn" name="whoToFollow" type="submit" value="Home">
        </form>
        <br>
        <!-- https://stackoverflow.com/questions/6791238/send-post-request-on-click-of-href-in-jsp -->
        <div class="wrapper">
        <nav id="Sidebar">
        <form class="CurrUser" name="profile" method="POST" action="CritterControl">
        <input type="hidden" name="username" value="${username}">
        <input id="profilebtn" type="hidden" name="action" value="Profile">
              
        <c:choose>
                     <c:when test="${filename != null}">
                         <img id="avi" src="GetImage?username=${username}" width="100" height="100"/><br>
                     </c:when> 
            <c:otherwise>
                <img id="avi" src='<c:url value="/files/default.jpg"></c:url>' width="100" height="100"/><br>    
            </c:otherwise>
            </c:choose>
                <br>
                <h3>Hi ${username}!</h3> 
                <br>
        <input type="submit" class="btn btn-primary" value="View Profile">
        </form>
        
        <br>
        
        <br>
        
        <div>   
            <ul class="list-unstyled components">
                <h4>Users</h4><br>
            <li class="active">
                
            <c:forEach var="user" items="${users}"> 
                <form action="CritterControl" method="post" id="whoToFollowForm">
                    <input type="hidden" name="action" value="whoToFollowAction">
                    <input type="hidden" name="username" value=${username}>
                    <input name="whoToFollow" type="hidden" value="${user.username}">
                    <li><h4><c:out value="${user.username}" /></h4><br>
                        <input id="likebtn" class="btn btn-primary" type="submit" value ="View Profile">
                        <br>
                        <br>
                    <c:choose>
                     <c:when test="${user.filename != null}">
                         <img id="avi" src="GetImage?username=${user.username}" width="100" height="100"/><br>
                     </c:when> 
            <c:otherwise>
                <img id="avi" src='<c:url value="/files/default.jpg"></c:url>' width="100" height="100"/><br>    
            </c:otherwise>
            </c:choose>
                    </li> <br> 
                </form>
                    <br>
            </c:forEach> 
                <br>
         </ul>
       </div>
        
        </nav>
        <div id="content">
            <div class="container">
                <div class="padcontain">
           <h4>Chirps</h4>
           <br>
           <% int incrmnt = 1; %>
           <c:forEach var="chirp" items="${followedTweets}">
              
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
                         <img id="avi" src="GetImage?username=${chirp.username}" width="100" height="100"/><br>
                     </c:when> 
                <c:otherwise>
                <img id="avi" src='<c:url value="/files/default.jpg"></c:url>' width="100" height="100"/><br>    
                </c:otherwise>
                </c:choose>                  
                   </td>                  
                   <td></td>
                   <td><c:out value="${chirp.text}" /></td>
                   <td> <c:if test="${chirp.filename != null}">
                   <td> <img src="GetTweetImage?username=${chirp.username}" width="100" height="100"/></td>
                   </c:if></td>
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
            
           </div>   
    </body>
</html>
