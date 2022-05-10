/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import sample.user.UserDAO;
import sample.user.UserDTO;

/**
 *
 * @author idchi
 */
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    static Logger log = Logger.getLogger(LoginController.class);

    private static final String ERROR = "login.jsp";
    private static final String AD = "AD";
    private static final String ADMIN_PAGE = "admin.jsp";
    private static final String US = "US";
    private static final String USER_PAGE = "user.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        log.debug("Start debugging");
        log.info("Entering Controller");
        String url = ERROR;
        try {
            String userID = request.getParameter("userID");
            String password = request.getParameter("password");
            UserDAO uDao = new UserDAO();
            UserDTO loginUser = uDao.checkLogin(userID, password);

            //xac thuc
            if (loginUser != null) {
                HttpSession session = request.getSession();
                String roleID = loginUser.getRoleID();
                //phan quyen
                if (AD.equals(roleID)) {
                    session.setAttribute("LOGIN_USER", loginUser);
                    log.info("This is an Info");
                    url = ADMIN_PAGE;
                } else if (US.equals(roleID)) {
                    session.setAttribute("LOGIN_USER", loginUser);
                    log.warn("This is a Warn");
                    url = USER_PAGE;
                } else {
                    request.setAttribute("ERROR", "Your roleID is not supported!");
                }
            } else {
                request.setAttribute("ERROR", "Incorrect userID or password!");
            }
            log.info("Exiting Controller");
        } catch (SQLException e) {
            log.error("Error at LoginController: " + e.toString());
            log("Error at LoginController: " + e.toString());
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
