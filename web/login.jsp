<%-- 
    Document   : login.jsp
    Created on : Mar 1, 2022, 2:47:58 PM
    Author     : idchi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel="stylesheet" type="text/css" href="./assets/css/style.css"/>
        <link rel="stylesheet" type="text/css" href="./assets//font/themify-icons/themify-icons.css"/>

    </head>

    <style>
        .login-google{
            font-size: 12px;
            display: block;
        }
        .login-content{
            height: 360px
        }
    </style>
    <body>
        <%
            String error = (String) request.getAttribute("ERROR");
            if (error == null) {
                error = "";
            }
            
String scope = "https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile";
        %>
        <section class="login-container container">
            <section class="login-content">
                <section class="login-header">
                    <p>Login</p>
                </section>

                <section class="login-main">
                    <form action="LoginController" method="post" id="login-form">
                        <input type="text" name="userID" value="" placeholder="User ID" required>
                        <input type="password" name="password" value="" placeholder="Password" required>
                        <div class="g-recaptcha flex-center" data-sitekey="6LdDps8eAAAAANQnMgTfUiSZn80QXc4MlZP-3QTZ"></div>
                        <input type="submit" name="action" value="Login" id="login-btn">
                        <a href="https://accounts.google.com/o/oauth2/auth?scope=<%= scope %>&redirect_uri=http://localhost:8080/SE160026_AssignmentPRJ/LoginGoogleServlet&response_type=code
                           &client_id=406430354020-ch4d6888d85de3vv93fuqt4fchgr6qvf.apps.googleusercontent.com&approval_prompt=force" class="login-google">Login With Google</a>  
                    </form>
                </section>
            </section>
        </section>
        <div class="msg-popup-containter" id="popup-msg" onclick="closeModal()">
            <div class="msg-popup flex-center ">
                <div class="msg-popup-icon ">
                    <i class="ti-close"></i>
                </div>
                <div class="msg-content flex-center">
                    <%= error%>
                </div>
            </div>
        </div>


        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
        <script>
            window.onload = function () {
                const loginForm = document.getElementById('login-form');
                const loginBtn = document.getElementById('login-btn');
                loginForm.addEventListener("submit", function (event) {
                    event.preventDefault();
                    const response = grecaptcha.getResponse();
                    if (response) {
                        loginForm.submit();
                    }
                });
            };
            <%
                if (error.length() > 0) {
            %>
            document.getElementById('popup-msg').style.display = "block";
            function showModal() {
                document.getElementById('popup-msg').style.display = "block";
            }
            <%
                }
            %>


            function closeModal() {
                document.getElementById('popup-msg').style.display = "none";
            }
        </script>
    </body>
</html>
