/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.mail.SendMail;
import sample.order.OrderDTO;
import sample.order.OrderDAO;
import sample.order.OrderDetail;
import sample.product.Cart;
import sample.product.ProductDTO;
import sample.user.UserDTO;

/**
 *
 * @author idchi
 */
@WebServlet(name = "CheckOutController", urlPatterns = {"/CheckOutController"})
public class CheckOutController extends HttpServlet {

    private static final String ERROR = "MainController?action=SearchProduct&searchProduct=";
    private static final String SUCCESS = "orderDetail.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        try {
            double total = Double.parseDouble(request.getParameter("total"));
            HttpSession session = request.getSession();
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            Date currentDate = fm.parse(fm.format(new Date()));
            Cart cart = (Cart) session.getAttribute("CART");
            OrderDTO order = new OrderDTO();
            OrderDAO oDao = new OrderDAO();
            OrderDetail orderDetail = new OrderDetail();
            if (cart != null) {
                if (!cart.getCart().isEmpty()) {
                    order = new OrderDTO(currentDate, total, loginUser.getUserID());
                    int orderID = oDao.addOrder(order);
                    for (ProductDTO product : cart.getCart().values()) {
                        double price = product.getPrice();
                        int quantity = product.getQuantity();
                        String productID = product.getProductID();
                        orderDetail = new OrderDetail(price, quantity, orderID, productID);
                        oDao.addOrderDetail(orderDetail);
                    }
                    cart.getCart().clear();
                    List<ProductDTO> orderDetailList = oDao.getOrderDetail(orderID);
                    request.setAttribute("ORDER_DETAIL_LIST", orderDetailList);

                    // Send mail confirm
                    String message = "<!DOCTYPE html>\n"
                            + "<html lang=\"en\">\n"
                            + "\n"
                            + "<head>\n"
                            + "</head>\n"
                            + "\n"
                            + "<body>\n"
                            + "    <h3 style=\"color: blue;\">Your order has been processing.</h3>\n"
                            + "    <div>Full Name :" + loginUser.getFullName() + "</div>\n"
                            + "    <div>Phone : " + loginUser.getPhone() + "</div>\n"
                            + "    <div>Address : " + loginUser.getAddress() + "</div>\n"
                            + "    <div>OrderID : " + orderID + "</div>\n"
                            + "    <div>Total : " + total + "$</div>\n"
                            + "    <h3 style=\"color: blue;\">Thank you very much!</h3>\n"
                            + "\n"
                            + "</body>\n"
                            + "\n"
                            + "</html>";
                    SendMail.send(loginUser.getEmail(), "Your order detail", message, "ngkien299@gmail.com", "Strongpassword-345");
                    url = SUCCESS;
                } else {
                    request.setAttribute("ERROR", "There are no products in cart!");
                }
            }
        } catch (Exception e) {
            log("Error at CheckOutController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
