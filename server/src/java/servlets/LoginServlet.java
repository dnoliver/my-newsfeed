/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Session;
import models.User;
import javax.servlet.http.Cookie;
import models.ModelFactory;
import models.ModelType;
/**
 *
 * @author dnoliver
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/command/login"})
public class LoginServlet extends HttpServlet {

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
    String userName = request.getParameter("user");
    String userPassword = request.getParameter("password");
    User user = (User) ModelFactory.Create(ModelType.USER);
    
    user.set("id", userName);
    user.set("password", userPassword);
    
    if(user.login()){
      HttpSession httpSession = request.getSession(true);
      Session session = (Session) ModelFactory.Create(ModelType.SESSION);
      Cookie cookie = new Cookie("session", httpSession.getId());
      
      session.set("id", httpSession.getId());
      session.set("owner",userName);
      session.save();
      cookie.setPath("/");
      response.addCookie(cookie);
      response.sendRedirect("/ubp/Main.jsp");
    }
    else {
      response.sendRedirect("/ubp/Login.jsp");
    }
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
      return "Login Servlet";
  }
}
