/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sample.product.ProductDAO;
import sample.product.ProductDTO;
import sample.product.ProductError;

/**
 *
 * @author idchi
 */
@WebServlet(name = "UpdateController", urlPatterns = {"/UpdateController"})
public class UpdateController extends HttpServlet {

    private static final String ERROR = "SearchProductController";
    private static final String SUCCESS = "SearchProductController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        try {
            boolean check = false;
            boolean checkValidation = true;
            String productID = request.getParameter("productID");
            String productName = request.getParameter("productName");
            String catagoryID = request.getParameter("cmpCatagoryID");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            Date importDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("importDate"));
            Date usingDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("usingDate"));
            ProductDTO product = new ProductDTO(productID, productName, url, price, quantity, catagoryID, importDate, usingDate);
            ProductDAO pDao = new ProductDAO();
            ProductError productError = new ProductError();
            Date currentDate = fm.parse(fm.format(new Date()));
            if (price <= 0 || price >= 1000) {
                checkValidation = false;
                productError.setPriceError("Price must greater than 0 and lower than 1000!");
            }
            if (importDate.compareTo(currentDate) > 0) {
                checkValidation = false;
                productError.setImportDateError("ImportDate should be present or the past");
            }
            if (usingDate.compareTo(currentDate) < 0) {
                checkValidation = false;
                productError.setUsingDateError("UsingDate should be present or the future");
            }
            if (checkValidation) {
                check = pDao.updateProduct(product);
                if (check) {
                    request.setAttribute("MESSAGE", "Update " + productName + " successfully!");
                    url = SUCCESS;
                }
            }else{
                request.setAttribute("ERROR", "Update "+productName + " failed!");
                request.setAttribute("PRODUCT_ERROR", productError);
            }
        }catch(NumberFormatException e){
            request.setAttribute("ERROR", "Price must be a number!");
            log("Error at UpdateController: " + e.toString());
        }
        catch ( SQLException | ParseException e) {
            log("Error at UpdateController: " + e.toString());
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
