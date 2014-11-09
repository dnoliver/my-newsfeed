/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import db.DataBaseAction;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author dnoliver
 */
@WebServlet(name = "LogoutServlet", urlPatterns = {"/command/logout"})
public class LogoutServlet extends HttpServlet {
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
    HttpSession session = request.getSession(false);

    if(session != null){
      DataBaseAction db = new DataBaseAction();
      db.removeSession(session.getId());
      session.invalidate();
      
      Cookie[] cookies = request.getCookies();
      if(cookies != null){
        for(Cookie cookie : cookies){
          if("session".equals(cookie.getName())){
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
          }
        }
      }
      
      response.sendRedirect("/ubp/Login.jsp");
    }
    else {
      /** TODO: handle else */
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
      return "Logout Servlet";
  }
}
