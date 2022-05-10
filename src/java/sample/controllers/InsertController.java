/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@WebServlet(name = "InsertController", urlPatterns = {"/InsertController"})
public class InsertController extends HttpServlet {

    private static final String ERROR = "MainController?action=SearchProduct&searchProduct=";
    private static final String SUCCESS = "MainController?action=SearchProduct&searchProduct=";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        ProductError productError = new ProductError();
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String productID = request.getParameter("productID");
            String productName = request.getParameter("productName");
            String catagoryID = request.getParameter("cmbCatagoryID");
            String image = request.getParameter("image");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            Date importDate = fm.parse(request.getParameter("importDate"));
            Date usingDate = fm.parse(request.getParameter("usingDate"));
            ProductDAO pDao = new ProductDAO();
            boolean checkValidation = true;
            Date currentDate = fm.parse(fm.format(new Date()));
            boolean checkDupplicated = pDao.checkDupplicated(productID);
            String catagoryName = pDao.getCatagoryName(catagoryID);
            if (checkDupplicated) {
                productError.setProductIDError("Product ID is dupplicated!");
                checkValidation = false;
            }
            if(price <= 0 || price >1000){
                productError.setPriceError("Price must greater than 0 and lower than 1000!");
                checkValidation = false;
            }
            if (importDate.compareTo(currentDate) > 0) {
                productError.setImportDateError("Import date must be present or the past!");
                checkValidation = false;
            }

            if (usingDate.compareTo(currentDate) < 0) {
                productError.setUsingDateError("Using date must be preset or future!");
                checkValidation = false;
            }

            if (checkValidation) {
                ProductDTO product = new ProductDTO(productID, productName, image, price, quantity, catagoryID, importDate, usingDate);
                boolean check = pDao.insertProduct(product);
                if (check) {
                    request.setAttribute("MESSAGE", "Insert " + productName +" into " + catagoryName +" successfully!");
                    url = SUCCESS;
                }
            } else {
                request.setAttribute("ERROR", "Insert " + productName +" into "+catagoryName+" failed!");
                request.setAttribute("PRODUCT_ERROR", productError);
            }

        } catch (SQLException | ParseException e) {
            log("Error at InsertController: " + e.toString());
        }catch(NumberFormatException e){
            request.setAttribute("ERROR", "Price must be a number!");
            log("Error at InsertController: " + e.toString());
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
