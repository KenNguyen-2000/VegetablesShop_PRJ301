<%-- 
    Document   : shopping
    Created on : Mar 3, 2022, 1:03:31 PM
    Author     : idchi
--%>

<%@page import="sample.product.Catagory"%>
<%@page import="sample.product.ProductDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="sample.product.ProductDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shopping Page</title>
        <link rel="stylesheet" type="text/css" href="./assets/css/style.css"/>
        <link rel="stylesheet" type="text/css" href="./assets//font/themify-icons/themify-icons.css"/>
        <style>
            .product-info{
                border: none;
                width: 100%;
                text-align: center;

            </style>
        </head>
        <body>
            <%
                String pattern = "dd-MM-yyyy";
                SimpleDateFormat fm = new SimpleDateFormat(pattern);
                String search = request.getParameter("searchProduct");
                if (search == null) {
                    search = "";
                }
                
                List<Catagory> listCatagory = (List<Catagory>) request.getAttribute("LIST_CATAGORY");

                String msg = (String) request.getAttribute("MESSAGE");
                if (msg == null) {
                    msg = "";
                }

                String error = (String) request.getAttribute("ERROR");
                if (error == null) {
                    error = "";
                }
            %>
            <header class="shopping-header">
                <div class="shopping-nav">
                    <a href="user.jsp" id="home-btn">Home</a>
                    <div class="nav-right">
                        <a href="MainController?action=View" class="profile nav-btn"><i class="ti-shopping-cart"></i> View Cart</a>
                        <a href="user.jsp" class="profile nav-btn"><i class="ti-user"></i> Profile</a>
                    </div>
                </div>
            </header>

            <section class="shopping-brief">
                <div>
                    <h1>KienNT Shopping Website</h1>
                </div>
            </section>


            <div class="shopping-search">
                <div class="col-lg-3" style="float: left;"></div>

                <form action="MainController" class="shopping-search-right col-lg-9" style="float: right;">
                    <div>
                        <input type="text" name="searchProduct" id="search-place" value="<%= search%>" placeholder="Search">
                        <input type="submit" name="action" id="search-btn" value="SearchProduct">
                    </div>

                </form>
            </div>

            <section class="shopping-body">

                <div class="shopping-content-left col-lg-3" style="float: left;">
                    <div class="catagory">
                        <div class="catagory-heading">Catagory</div>
                        <div class="catagory-item">   
                            <%
                                if (listCatagory != null) {
                                    for (Catagory catagory : listCatagory) {
                            %>
                            <a href="MainController?action=SearchProduct&searchProduct=<%= catagory.getCatagoryName()%>"><%= catagory.getCatagoryName()%></a>
                            <%
                                    }
                                }
                            %>
                            <a href="MainController?action=SearchProduct&searchProduct=">Get all products</a>
                        </div>
                    </div>
                </div>

                <div class="shopping-content-right col-lg-9" style="float: right;">
                    <%
                        List<ProductDTO> listProduct = (List<ProductDTO>) request.getAttribute("LIST_PRODUCT");

                        if (listProduct != null) {
                            if (listProduct.size() > 0) {
                                for (ProductDTO product : listProduct) {
                    %>
                    <form action="MainController" method="POST" class="shopping-item-container col-4" id="my-form">
                        <div class="shopping-card">
                            <img src="<%= product.getImage()%>" alt="Product Image">
                            <div class="card-body">
                                <h5><input class="product-info" type="text" name="productName" value="<%= product.getProductName()%>"/></h5>
                                <p ><input class="product-info" type="hidden" name="price" value="<%= product.getPrice()%>"/><%= product.getPrice()%>$</p>
                                <p>NSS:<%= fm.format(product.getImportDate())%></p>
                                <p>HSD:<%= fm.format(product.getUsingDate())%></p>
                            </div>
                            <div class="card-footer">
                                <input type="submit" name="action" id="add-btn" onclick="showModal()" value="AddToCart">
                                <input type="hidden" name="searchProduct" value="<%=search%>"/>
                                <input type="hidden" name="productID" value="<%= product.getProductID()%>"/>
                                <input type="hidden" name="image" value="<%= product.getImage()%>"/>
                            </div>
                        </div>
                    </form>
                    <%
                                }
                            }
                        }
                    %>
                </div>
                <div class="clear"></div>       
            </section>

            <div class="msg-popup-containter" id="popup-msg" onclick="closeModal()">
                <div class="msg-popup flex-center ">
                    <div class="msg-popup-icon ">
                        <%
                            if (error.length() > 0) {
                                msg=error;
                        %>
                        <i class="ti-close"></i>
                        <%
                        } else {
                        %>
                        <i class="ti-check"></i>
                        <%
                            }
                        %>

                    </div>
                    <div class="msg-content flex-center">
                        <%= msg%>
                    </div>
                </div>
            </div>

            <script>

                <%
                    if (msg.length() > 0) {
                %>
                document.getElementById('popup-msg').style.display = "block";
                function showModal() {
                    document.getElementById('popup-msg').style.display = "block";
                    setTimeout(function (){
                        document.getElementById('popup-msg').style.display = "none";
                    }, 2000);
                }

                function closeModal() {
                    document.getElementById('popup-msg').style.display = "none";
                }
                <%
                    }
                %>


            </script>


        </body>
    </html>
