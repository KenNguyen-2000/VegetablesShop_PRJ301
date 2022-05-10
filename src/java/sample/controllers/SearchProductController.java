/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.product.Catagory;
import sample.product.ProductDAO;
import sample.product.ProductDTO;
import sample.user.UserDTO;

/**
 *
 * @author idchi
 */
@WebServlet(name = "SearchProductController", urlPatterns = {"/SearchProductController"})
public class SearchProductController extends HttpServlet {

    private static final String ERROR = "shopping.jsp";
    private static final String US_SUCCESS = "shopping.jsp";
    private static final String AD_SUCCESS = "productManage.jsp";
    private static final String US = "US";
    private static final String AD = "AD";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String search = request.getParameter("searchProduct");
            if (search != null) {
                ProductDAO productDao = new ProductDAO();
                HttpSession session = request.getSession();
                UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
                String role = loginUser.getRoleID();
                List<ProductDTO> listProduct = productDao.getListProduct(search, role);
                List<Catagory> listCatagory = productDao.getAllCatagory();
                if (listProduct.size() > 0) {
                    if (US.equals(role)) {
                        request.setAttribute("LIST_PRODUCT", listProduct);
                        request.setAttribute("LIST_CATAGORY", listCatagory);
                        url = US_SUCCESS;
                    } else if (AD.equals(role)) {
                        request.setAttribute("LIST_PRODUCT", listProduct);
                        request.setAttribute("LIST_CATAGORY", listCatagory);
                        url = AD_SUCCESS;
                    }

                }
            }
        } catch (SQLException e) {
            log("Error at SearchProductController: " + e.toString());
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
