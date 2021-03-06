<%@ page import="org.zkoss.zk.ui.util.Clients" %><%--
  Created by IntelliJ IDEA.
  User: meneses
  Date: 6/9/19
  Time: 8:06 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>SGB Login</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style type="text/css">
        .login-form {
            opacity: 0.9;
            width: 340px;
            margin: 50px auto;
        }
        .login-form form {
            margin-bottom: 15px;
            background: #f7f7f7;
            box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
            padding: 30px;
        }
        .login-form h2 {
            margin: 0 0 15px;
        }
        .form-control, .btn {
            min-height: 38px;
            border-radius: 2px;
        }
        .btn {
            font-size: 15px;
            font-weight: bold;
        }
        .imglogin {
            text-align: center;
            margin: 8px 0 8px 0;
        }
        img.avatar {
            width: 40%;
            border-radius: 50%;
        }
        body{

            background-size: cover;
        }
    </style>
</head>
<body background="img/bg1.jpg" >
<div class="login-form">
    <form action="j_spring_security_check" method="post">

        <div class="imglogin">
            <img src="img/logounilurio.jpg" alt="Avatar" class="avatar">
            <h3>Sistema de Gestão Bibliotecário</h3>
        </div>
        <div class="form-group">
            <input  type="text" class="form-control" placeholder="Username" required="required" name="j_username">
        </div>
        <div class="form-group">
            <input type="password"  class="form-control" placeholder="Password" required="required" name="j_password">
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block">Log in</button>
        </div>

        <%
            int error = 0;
            int blocked = 0;
            try {
                error = Integer.parseInt(request.getParameter("login_error"));
            }catch (Exception ex){}
            try{
                blocked = Integer.parseInt(request.getParameter("blocked"));
            }catch (Exception e){}


//                Clients.showNotification("Dados incorrectos","error",null,null,0,true);

            if (error ==1){
        %>
        <h4 style="color: #d40904">   Por favor, verifique se digitou correctamente os dados.</h4>
        <%}%>

        <%if (blocked==1){%>
        <h4 style="color: #d40904">   Utilizador Bloqueiado!</h4>
        <%}%>

    </form>

</div>
</body>
</html>
