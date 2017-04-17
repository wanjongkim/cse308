/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import beans.AccountDAO;
import beans.UserSessionBean;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Shawn
 */
public class Login extends HttpServlet {

    @EJB
    AccountDAO acc;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String homepage = "index";
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean logInSuccessful = acc.validateAccountInformation(username, password);
        if(logInSuccessful) {
            HttpSession sess = request.getSession();
            UserSessionBean user = (UserSessionBean) sess.getAttribute("user");
            if(user == null) {
                user = new UserSessionBean();
                sess.setAttribute("user", user);
            }
            user.setLoggedIn(true);
            response.sendRedirect(homepage);
        }
        else {
            //display error
        }
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
