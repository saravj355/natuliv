/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import controller.Auth;
import dao.UserDao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;


/**
 *
 * @author sarav
 */
@WebServlet(name = "Register", urlPatterns = {"/signUp"})
public class Register extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String name = request.getParameter("name");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
                
        //checks empty inputs
        if(name.isEmpty() || lastName.isEmpty() || email.isEmpty() || 
           password.isEmpty() || confirmPassword.isEmpty())
        {
           request.setAttribute("errorMessage", "Por favor llene todos los campos.");
           getServletContext().getRequestDispatcher("/register.jsp").forward(request,response);           
           return;
        }
        
        //Checks if password is different
        if(!password.equals(confirmPassword)){
             request.setAttribute("errorMessage", "La contraseña no coincide");
             getServletContext().
                   getRequestDispatcher("/register.jsp")
                   .forward(request,response); 
             return;
        }
        
        UserDao userDao = new UserDao();
        
        User user = userDao.findUserByEmail(email);
        //Checks if email already exists
        if (user != null){
            request.setAttribute("errorMessage", "Este correo ya está asociado a una cuenta");
            getServletContext().
                   getRequestDispatcher("/register.jsp")
                   .forward(request,response);   
         return;
        }
                   
        // user instance
        // creates user
        User newUser = Auth.register(name, lastName, email, password);
        
        // redirect to menu page when user is created
        if(newUser != null){
            request.setAttribute("name", newUser.getName());
            getServletContext().
                   getRequestDispatcher("/webapp/main.jsp")
                   .forward(request,response); 
             return;
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
