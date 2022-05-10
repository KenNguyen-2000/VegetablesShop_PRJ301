<%-- 
    Document   : admin
    Created on : Mar 1, 2022, 4:13:25 PM
    Author     : idchi
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="sample.product.Catagory"%>
<%@page import="sample.product.ProductError"%>
<%@page import="sample.product.ProductDAO"%>
<%@page import="sample.product.ProductDTO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.List"%>
<%@page import="sample.user.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Manage Page</title>
        <link rel="stylesheet" type="text/css" href="./assets/css/style.css"/>
        <link rel="stylesheet" type="text/css" href="./assets//font/themify-icons/themify-icons.css"/>
        <style>
            .product-info{
                border: none;
            }

            .pm-catagoryID select{
                background-color: #CCCCCC;
                text-align: center;
                border: 2px solid #000;
            }

            a.get-all-btn{
                border-radius: 3px;
                border: 1px solid rgba(0, 0, 0, 0.3);
                padding: 4px 4px;
                padding-bottom: 7px;
                background-color: rgb(239, 239, 239);
                color: black;
            }

            a.get-all-btn:hover{
                color: #fff;
                background-color: #000;
                cursor: pointer;
            }

            .pm-edit input:hover{
                background-color: #212529;
                color: #fff;
                cursor: pointer;
            }
        </style>
    </head>
    <body>
        <%  String msg = (String) request.getAttribute("MESSAGE");
            if (msg == null) {
                msg = "";
            }

            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            if (loginUser == null || !loginUser.getRoleID().equals("AD")) {
                response.sendRedirect("login.jsp");
                return;
            }

            String search = request.getParameter("searchProduct");
            if (search == null) {
                search = "";
            }

            String error = (String) request.getAttribute("ERROR");
            if (error == null) {
                error = "";
            }
        %>  
        <!-- ******************************************************************************************** -->

        <header class="shopping-header">
            <div class="shopping-nav">
                <a href="admin.jsp" id="home-btn">Home</a>
                <div class="nav-right">
                    <a href="#" class="add nav-btn js-insert-btn"><i class="ti-bag"></i> Insert</a>
                    <a href="admin.jsp" class="profile nav-btn"><i class="ti-user"></i> Profile</a>
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
                    <a href="MainController?action=SearchProduct&searchProduct=" class="get-all-btn" >Get all products</a>
                </div>
            </form>
            <div>

            </div>
        </div>

        <div class="product-manage-container">
            <div class="info-bar product-manage-bar" style="margin-bottom: 20px;">
                <div class="pm-num text-center">No</div>
                <div class="pm-img text-center">Image</div>
                <div class="pm-name text-center">Product Name</div>
                <div class="pm-price text-center">Price</div>
                <div class="pm-quantity text-center">Quantity(kg)</div>
                <div class="pm-catagoryID text-center">Catagory</div>
                <div class="pm-importD  text-center">Import Date</div>
                <div class="pm-usingD  text-center">Using Date</div>
                <div class="pm-remove text-center">Delete</div>
                <div class="pm-edit text-center">Update</div>
            </div>
            <%
                List<ProductDTO> listProduct = (List<ProductDTO>) request.getAttribute("LIST_PRODUCT");
                List<Catagory> listCatagory = (List<Catagory>) request.getAttribute("LIST_CATAGORY");
                if (listProduct != null) {
                    if (listProduct.size() > 0) {
                        int count = 1;
                        String catagoryName = "";
                        for (ProductDTO product : listProduct) {
                            catagoryName = ProductDAO.getCatagoryName(product.getCatagoryID());
            %>
            <form action="MainController">

                <div class="cart-content product-content">
                    <div class="pm-num text-center"><%= count++%></div>
                    <div class="pm-img text-center"><img src="<%= product.getImage()%>" alt="Product Image"></div>
                    <div class="pm-name text-center"><input type="text" name="productName" value="<%= product.getProductName()%>" minlength="4" maxlength="30" style="width: 100%;"></div>
                    <div class="pm-price flex-center text-center"><input type="text" name="price" value="<%= product.getPrice()%>"style="width: 30%;"></div>
                    <div class="pm-quantity text-center"><input type="number" name="quantity" value="<%= product.getQuantity()%>" min="1" max="1000">
                    </div>

                    <div class="pm-catagoryID text-center">
                        <select name="cmpCatagoryID" class="text-center" style="width: 70%; height: 100%;">
                            <option value="<%= product.getCatagoryID()%>"><%=  catagoryName%></option>

                            <%
                                for (Catagory catagory : listCatagory) {
                            %>
                            <option value="<%= catagory.getCatagoryID()%>"><%= catagory.getCatagoryName()%></option>
                            <%
                                }
                            %>                        
                        </select>
                    </div>
                    <div class="pm-importD text-center"><input type="date" name="importDate" value="<%= product.getImportDate()%>" style="width: 90%;"></div>
                    <div class="pm-usingD text-center"><input type="date" name="usingDate" value="<%= product.getUsingDate()%>" style="width: 90%;"></div>
                    <div class="pm-remove text-center"><a href="MainController?action=Delete&productID=<%= product.getProductID()%>&searchProduct=<%= search%>" class="nav-btn delete-btn">Delete</a></div>
                    <div class="pm-edit text-center"><input type="submit" name="action" class="nav-btn" value="Update"></div>
                    <input type="hidden" name="productID" value="<%= product.getProductID()%>"/>
                    <input type="hidden" name="searchProduct" value="<%= search%>"/>

                </div>
            </form>
            <%
                        }
                    }
                }
            %>
        </div>
        <div class="insert-modal-container" id="js-modal-container">
            <form action="MainController" method="POST" class="insert-modal flex-center">
                <div class="insert-modal-content">
                    <div class="col-lg-5 flex-center">
                        <p>Product ID:</p>
                    </div>
                    <div class="col-lg-5 flex-center"><input type="text" name="productID" value="" minlength="2" maxlength="5" required></div>
                    <div class="col-lg-5 flex-center">
                        <p>Product Name:</p>
                    </div>
                    <div class="col-lg-5 flex-center"><input type="text" name="productName" value="" minlength="4" maxlength="30" required></div>
                    <div class="col-lg-5 flex-center" style="overflow: hidden;">
                        <p>Product Image(url):</p>
                    </div>
                    <div class="col-lg-5 flex-center"><input type="url" name="image" value="" required></div>
                    <div class="col-lg-5 flex-center">
                        <p>Price($):</p>
                    </div>
                    <div class="col-lg-5 flex-center"><input type="text" name="price" value="" required></div>
                    <div class="col-lg-5 flex-center">
                        <p>Quantity(kg):</p>
                    </div>
                    <div class="col-lg-5 flex-center"><input type="number" name="quantity" value="" min="1" max="1000" required></div>
                    <div class="col-lg-5 flex-center">
                        <p>Catagory:</p>
                    </div>
                    <div class="col-lg-5 flex-center">
                        <select name="cmbCatagoryID" class="text-center" style="width: 100%; height: 100%;">
                            <%
                                for (Catagory catagory : listCatagory) {
                            %>
                            <option value="<%= catagory.getCatagoryID()%>"><%= catagory.getCatagoryName()%></option>
                            <%
                                }
                            %> 
                        </select>
                    </div>
                    <div class="col-lg-5 flex-center">
                        <p>Import Date:</p>
                    </div>
                    <div class="col-lg-5 flex-center"><input type="date" name="importDate" value="" required></div>
                    <div class="col-lg-5 flex-center">
                        <p>Using Date:</p>
                    </div>
                    <div class="col-lg-5 flex-center"><input type="date" name="usingDate" value="" required></div>
                    <div class="insert flex-center" style="width: 100%; justify-content: center;"><input type="submit" name="action" id="insert-btn" value="Insert"></div>
                </div>
            </form>
        </div>
        <%
        %>


        <div class="msg-popup-containter" id="popup-msg" onclick="closeModal()">
            <div class="msg-popup flex-center ">
                <div class="msg-popup-icon ">
                    <%                        if (error.length() > 0) {
                            msg = error;
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
                <div class="msg-content" style="display: flex; flex-direction: column; align-items: center">
                    <h3><%= msg%></h3>
                    <%
                        ProductError productError = (ProductError) request.getAttribute("PRODUCT_ERROR");
                        if (productError == null) {
                            productError = new ProductError();
                        } else {
                    %>
                    <div><%= productError.getProductIDError()%></div>
                    <div><%= productError.getPriceError()%></div>
                    <div><%= productError.getImportDateError()%></div>
                    <div><%= productError.getUsingDateError()%></div>                                            
                    <%
                        }
                    %>
                </div>
            </div>



            <script>
                const insertModal = document.querySelector('.insert-modal');
                const modal = document.getElementById('js-modal-container');
                const insertBtn = document.querySelector('.js-insert-btn');
                var deleteBtn = document.querySelectorAll("a.delete-btn");

                for (var i = 0; i < deleteBtn.length; i++) {
                    deleteBtn[i].addEventListener('click', function (event) {
                        if (!confirm("Are you sure you want to delete?")) {
                            event.preventDefault();
                        }
                    });
                }

                insertBtn.addEventListener('click', showInsertModal);
                insertModal.addEventListener('click', function (event) {
                    event.stopPropagation();
                });

                function showInsertModal() {
                    modal.style.display = "block";
                }

                modal.addEventListener('click', function () {
                    modal.style.display = "none";
                });
                <%
                    if (msg.length() > 0) {
                %>
                console.log("<%=msg%>");
                document.getElementById('popup-msg').style.display = "block";
                function showModal() {
                    document.getElementById('popup-msg').style.display = "block";
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
