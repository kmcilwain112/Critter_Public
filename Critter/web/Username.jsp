<%-- 
    Document   : username
    Created on : Apr 13, 2023, 5:33:22 PM
    Author     : kmcil
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<script type="text/javascript">
    function likeCounter() {
        
        var result = document.getElementById("result");
        var up = result.innerHTML;
        up++;
        result.innerHTML = up;       
    }
    function submitFollowForm(){
       document.getElementById("followForm").submit(); 
    }
    function submitunFollowForm(){
       document.getElementById("unfollow").submit(); 
    }
</script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link href="<c:url value="/CSS/UsernameStyle.css" />" rel="stylesheet">
        <title>${whoToFollow}'s Profile</title>       
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
        <h2> ${whoToFollow}! </h2>        
            <c:choose>
                     <c:when test="${filename != null}">
                         <img id="avi" src="GetImage?username=${whoToFollow}" width="100" height="100"/><br>
                     </c:when> 
            <c:otherwise>
                <img id="avi" src='<c:url value="/files/default.jpg"></c:url>' width="100" height="100"/><br>    
            </c:otherwise>
            </c:choose>              
                <br>
        <c:choose>          
                <c:when test="${btncondition == 'follow'}">        
        <form name="followForm" action="usernameControl" method="POST">
            <input type="hidden" name="action" value="follow">
            <input type="hidden" name="whoToFollow" value="${whoToFollow}">
            <input type="hidden" name="username" value="${username}">
            <input type="submit" id="follow" value="Follow" class="btn btn-primary"><br>            
        </form> 
                </c:when>
                <c:otherwise>
        <form name="unfollowForm" action="usernameControl" method="POST">
          <input type="hidden" name="action" value="unfollow">
          <input type="hidden" name="whoToFollow" value="${whoToFollow}">
          <input type="hidden" name="username" value="${username}">
          <input type="submit" value="Unfollow" id="unfollow" class="btn btn-primary"><br>
        </form>                           
            </c:otherwise>
            </c:choose>  
           <br>        
        </nav>
            <div id="content">
            <div class="container">          
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
                     <img id="avi" src="GetImage?username=${chirp.username}" width="50" height="50"/>                  
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
