<%-- 
    Document   : oderDetail
    Created on : Mar 11, 2022, 8:47:11 PM
    Author     : idchi
--%>

<%@page import="java.util.List"%>
<%@page import="sample.product.ProductDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Oder Detail Page</title>
        <link rel="stylesheet" type="text/css" href="./assets/css/style.css"/>
        <link rel="stylesheet" type="text/css" href="./assets//font/themify-icons/themify-icons.css"/>
    </head>
    <body>


        <header class="shopping-header">
            <div class="shopping-nav">
                <a href="user.jsp" id="home-btn">Home</a>
                <div class="nav-right">
                    <a href="MainController?action=SearchProduct&searchProduct=" class="add nav-btn"><i class="ti-bag"></i>Shopping</a>
                    <a href="user.jsp" class="profile nav-btn"><i class="ti-user"></i> Profile</a>
                </div>
            </div>
        </header>

        <section class="shopping-brief">
            <div>
                <h1>Chợ rau củ quả KienNT</h1>
            </div>
        </section>


        <div class="view-container">
            <div class="info-bar">
                <div class="text-center" style="width: 100%; font-size: 32px">Your Order</div>

            </div>
            <%
                List<ProductDTO> orderDetailList = (List<ProductDTO>) request.getAttribute("ORDER_DETAIL_LIST");
                int count = 1;
                double total = 0;
                if (orderDetailList != null) {
                    for (ProductDTO product : orderDetailList) {
                        total += product.getPrice() * product.getQuantity();
            %>
            <div class="cart-content">
                <div class="num-bar text-center"><%= count++%></div>
                <div class="description-bar text-center">
                    <div class="img-bar"><img src="<%= product.getImage()%>" alt=""></div>
                    <div class="name-bar"><%= product.getProductName()%></div>
                </div>
                <div class="price-bar flex-center text-center"><%= product.getPrice()%> <i class="ti-money"></i></div>
                <div class="quantity-bar text-center"><%= product.getQuantity()%> kg
                </div>
                <div class="total-bar flex-center text-center">Total: <%= total%><i class="ti-money"></i></div>
            </div>

            <%
                    }
%>
            <div class="cart-footer flex-center">
                <div class="footer-total flex-center" style="width: 100%;">
                    Total: <%= total %> <i class="ti-money"></i>
                </div>
            </div>
            <%
                }

            %>
            
        </div>
</html>
