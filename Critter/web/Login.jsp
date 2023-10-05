<%-- 
    Document   : Login
    Created on : Apr 17, 2023, 9:23:16 AM
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
        <link href="<c:url value="/CSS/Login.css" />" rel="stylesheet">
        <title>Login</title>
    </head>
    <body>
        <h1>Critter</h1>
        <img id= "logo" src='<c:url value="/files/CritterLogo.jpg"></c:url>'/>
        <br>
        <br>
        <div class="container">
        <h3>Login</h3>
        <div class="card">
        <div class="card-body">
        <p>${message}</p>
        <form action="Login" method="post">
            <div class="form-group row">
            <label for="username" class="col-sm-2 col-form-label">Username</label>
            <div class="col-sm-7">
            <input type="text" class="form-control" name="username" placeholder="Enter username""/><br>
            </div>
            </div>
            <div class="form-group row">
            <label for="password" class="col-sm-2 col-form-label">Password</label>
            <div class="col-sm-7">
            <input type="password" name="password" class="form-control" name="password" placeholder="Enter password"/><br>
            </div>
            </div>
            <input type="hidden" name="action" value="login"/>
            <input type="submit" class="btn btn-primary" value="Login"/>          
        </form>
        </div>
        </div>
        </div>
        </div>
        </div>
        <br>
        <br>
        <div class="container">
        <h3>Join Critter</h3>
        <div class="card">
        <div class="card-body">
        <form action="Login" method="post">
            <div class="form-group row">
            <label for="username" class="col-sm-2 col-form-label">Username</label>
            <div class="col-sm-7">
            <input type="text"  class="form-control" name="username" placeholder="Enter username"/><br>
             </div>
             </div>
            <div class="form-group row">
            <label for="password" class="col-sm-2 col-form-label">Password</label>
            <div class="col-sm-7">
            <input type="password" class="form-control" name="password" placeholder="Enter password"/><br>
            </div>
            </div>   
            <input type="hidden" name="action" value="register"/>                 
            <input type="submit"  class="btn btn-primary " value="Register"/>      
        </form>
         </div>
        </div>
       </div>
      </div>
     </div>
    </body>
</html>
