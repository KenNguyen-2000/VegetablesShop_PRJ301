<%-- 
    Document   : user
    Created on : Mar 1, 2022, 4:13:20 PM
    Author     : idchi
--%>

<%@page import="sample.product.ProductDAO"%>
<%@page import="sample.product.ProductDAO"%>
<%@page import="java.util.List"%>
<%@page import="sample.product.ProductDTO"%>
<%@page import="sample.user.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Welcome Page</title>
        <link rel="stylesheet" type="text/css" href="./assets/css/style.css"/>
    </head>
    <body>
        <%
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            if (loginUser == null || !loginUser.getRoleID().equals("US")) {
                response.sendRedirect("login.jsp");
                return;
            }

        %>
        <section class="user-info-container container">
            <div class="user-info-content">
                <div class="user-info-header">
                    Welcome <%= loginUser.getFullName()%>!
                </div>

                <div class="user-info-body row">
                    <div class="body-left col-lg-3">
                        <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSteItzPyeDKBxyWiOA8xrPZXIlxOYv1b1VVg&usqp=CAU" alt="user img">
                    </div>
                    <div class="body-right col-lg-9">
                        <table class="table table-user-info">
                            <tbody>
                                <tr>
                                    <td>UserID:</td>
                                    <td><%= loginUser.getUserID()%></td>
                                </tr>
                                <tr>
                                    <td>Full Name</td>
                                    <td><%= loginUser.getFullName()%></td>
                                </tr>
                                <tr>
                                    <td>Role ID</td>
                                    <td><%= loginUser.getRoleID()%></td>
                                </tr>
                                <tr>
                                    <td>Address</td>
                                    <td><%= loginUser.getAddress()%></td>
                                </tr>
                                <tr>
                                    <td>Phone</td>
                                    <td><%= loginUser.getPhone()%></td>
                                </tr>
                                <tr>
                                    <td>Email</td>
                                    <td><%= loginUser.getEmail()%></td>
                                </tr>
                                <tr>
                                    <td>Password</td>
                                    <td><%= "***"%></td>
                                </tr>
                            </tbody>
                        </table>
                        <a href="MainController?action=SearchProduct&searchProduct=" class="btn btn-primary">Shopping</a>
                        <a href="MainController?action=Logout" class="btn btn-primary">Logout</a>
                    </div>
                    <div class="clear"></div>
                </div>

                <div class="user-info-footer">

                </div>
            </div>
        </section>
    </body>
</html>
