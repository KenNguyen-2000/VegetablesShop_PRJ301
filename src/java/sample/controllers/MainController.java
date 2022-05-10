/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author idchi
 */
public class MainController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String DELETE = "Delete";
    private static final String DELETE_CONTROLLER = "DeleteController";
    private static final String UPDATE = "Update";
    private static final String UPDATE_CONTROLLER = "UpdateController";
    private static final String LOGOUT = "Logout";
    private static final String LOGOUT_CONTROLLER = "LogoutController";
    private static final String SEARCH_PRODUCT = "SearchProduct";
    private static final String SEARCH_PRODUCT_CONTROLLER = "SearchProductController";
    private static final String ADD_TO_CART = "AddToCart";
    private static final String ADD_TO_CART_CONTROLLER = "AddToCartController";
    private static final String VIEW = "View";
    private static final String VIEW_CONTROLLER = "viewCart.jsp";
    private static final String REMOVE = "Remove";
    private static final String REMOVE_CONTROLLER = "RemoveController";
    private static final String EDIT = "Edit";
    private static final String EDIT_CONTROLLER = "EditController";
    private static final String INSERT = "Insert";
    private static final String INSERT_CONTROLLER = "InsertController";
    private static final String CHECK_OUT = "CheckOut";
    private static final String CHECK_OUT_CONTROLLER = "CheckOutController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String action = request.getParameter("action");
            if (DELETE.equals(action)) {
                url = DELETE_CONTROLLER;
            }else if (UPDATE.equals(action)) {
                url = UPDATE_CONTROLLER;
            }else if (LOGOUT.equals(action)) {
                url = LOGOUT_CONTROLLER;
            }else if (SEARCH_PRODUCT.equals(action)) {
                url = SEARCH_PRODUCT_CONTROLLER;
            }else if (ADD_TO_CART.equals(action)) {
                url = ADD_TO_CART_CONTROLLER;
            }else if (VIEW.equals(action)) {
                url = VIEW_CONTROLLER;
            }else if (REMOVE.equals(action)) {
                url = REMOVE_CONTROLLER;
            }else if (EDIT.equals(action)) {
                url = EDIT_CONTROLLER;
            }else if (INSERT.equals(action)) {
                url = INSERT_CONTROLLER;
            }else if (CHECK_OUT.equals(action)) {
                url = CHECK_OUT_CONTROLLER;
            }
        } catch (Exception e) {
            log("Error at MainController: " + e.toString());
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
