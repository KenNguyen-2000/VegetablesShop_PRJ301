<%-- 
    Document   : viewCart
    Created on : Mar 3, 2022, 3:12:10 PM
    Author     : idchi
--%>

<%@page import="sample.product.ProductDAO"%>
<%@page import="sample.product.ProductDTO"%>
<%@page import="sample.product.Cart"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart Page</title>
        <link rel="stylesheet" type="text/css" href="./assets/css/style.css"/>
        <link rel="stylesheet" type="text/css" href="./assets//font/themify-icons/themify-icons.css"/>  
    </head>
    <body> 
        <header class="shopping-header">
            <div class="shopping-nav">
                <a href="user.jsp" id="home-btn">Home</a>
                <div class="nav-right">
                    <a href="MainController?action=SearchProduct&searchProduct=" class="add nav-btn"><i class="ti-bag"></i> Add More</a>
                    <a href="user.jsp" class="profile nav-btn"><i class="ti-user"></i> Profile</a>
                </div>
            </div>
        </header>

        <section class="shopping-brief">
            <div>
                <h1>KienNT Shopping Website</h1>
            </div>
        </section>
        <%
            Cart cart = (Cart) session.getAttribute("CART");
            if (cart != null) {
        %>
        <div class="view-container">
            <div class="info-bar">
                <div class="num-bar text-center">No</div>
                <div class="description-bar text-center">
                    <div class="img-bar">Image</div>
                    <div class="name-bar">Product Name</div>
                </div>
                <div class="price-bar text-center">Price</div>
                <div class="quantity-bar text-center">Quantity(kg)</div>
                <div class="total-bar text-center">Total</div>
                <div class="remove-bar text-center">Remove</div>
                <div class="edit-bar text-center">Edit</div>
            </div>
            <%
                int count = 1;
                double total = 0;
                for (ProductDTO product : cart.getCart().values()) {
                    total += product.getPrice() * product.getQuantity();
                    int maxQuantity = ProductDAO.getMaxQuantity(product.getProductID());
                    if (product.getQuantity() > maxQuantity) {
                        product.setQuantity(maxQuantity);
                    }
            %>
            <form action="MainController">
                <div class="cart-content">
                    <div class="num-bar text-center"><%= count++%></div>
                    <div class="description-bar text-center">
                        <div class="img-bar"><img src="<%= product.getImage()%>" alt="Ảnh sản phẩm"></div>
                        <div class="name-bar"><%= product.getProductName()%></div>
                    </div>
                    <div class="price-bar flex-center text-center"><%= product.getPrice()%> <i class="ti-money"></i></div>
                    <div class="quantity-bar text-center"><input type="number" name="quantity" value="<%= product.getQuantity()%>" min="1" max="<%= maxQuantity%>" style="width: 40%">
                    </div>
                    <div class="total-bar flex-center text-center"><%= product.getPrice() * product.getQuantity()%> <i class="ti-money"></i></div>
                    <div class="remove-bar text-center"><input type="submit" name="action" class="nav-btn remove-btn flex-center" value="Remove"></div>
                    <div class="edit-bar text-center"><input type="submit" name="action" class="nav-btn" value="Edit"></div>
                    <input type="hidden" name="productID" value="<%= product.getProductID()%>"/>
                </div>
            </form>
            <%
                }
            %>
            <%
                if (cart.getCart().isEmpty()) {
            %>
            <div class="empty-cart flex-center">
                <div>
                    <p>Your cart still have nothing</p>
                </div>
                <div style="margin-bottom: 5px;">
                    <i class="ti-shopping-cart"></i>
                </div>
                <div>
                    <a href="MainController?action=SearchProduct&searchProduct=">Let's buy something</a>
                </div>
            </div>
            <%
                }
            %>
            <div class="cart-footer flex-center">
                <div class="footer-total">
                    Total: <%= total%>$
                </div>
                <div><a href="MainController?action=CheckOut&total=<%= total%>" class="nav-btn">Make Order</a></div>
            </div>
        </div>
        <%
        } else {
        %>
        <div class="empty-cart flex-center">
            <div>
                <p>Your cart still have nothing</p>
            </div>
            <div style="margin-bottom: 5px;">
                <i class="ti-shopping-cart"></i>
            </div>
            <div>
                <a href="MainController?action=SearchProduct&searchProduct=">Let's buy something</a>
            </div>
        </div>
        <%
            }
        %>

        <script>
            var removeBtn = document.querySelectorAll("input.remove-btn");

            for (var i = 0; i < removeBtn.length; i++) {
                removeBtn[i].addEventListener('click', function (event) {
                    if (!confirm("Are you sure you want to delete?")) {
                        event.preventDefault();
                    }
                });
            }
        </script>
    </body>
</html>
