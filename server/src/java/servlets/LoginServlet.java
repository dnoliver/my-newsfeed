/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import db.DataBaseAction;
import javax.servlet.http.Cookie;
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
    String user = request.getParameter("user");
    String password = request.getParameter("password");

    DataBaseAction db = new DataBaseAction();

    if(db.login(user, password)){
      HttpSession session = request.getSession(true);
      Cookie cookie = new Cookie("session", session.getId());
      cookie.setPath("/");
      response.addCookie(cookie);
      db.createSession(session.getId(), user);
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
  }// </editor-fold>
}
