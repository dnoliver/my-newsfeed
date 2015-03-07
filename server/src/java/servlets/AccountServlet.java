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
import models.ModelFactory;
import models.ModelType;
import models.User;

/**
 *
 * @author dnoliver
 */
@WebServlet(name = "AccountServlet", urlPatterns = {"/account"})
public class AccountServlet extends HttpServlet {
  
  /**
   * Query user
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException 
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try (PrintWriter out = response.getWriter()) {
      HttpSession session = request.getSession(false);
      if(session != null && session.getAttribute("user") != null){
        User user = (User) session.getAttribute("user");
        user.select();
        
        response.setStatus(200);
        response.setContentType("application/json;charset=UTF-8");
        out.print(user.toJSONString());
      }
      else {
        response.setStatus(400);
        response.setContentType("application/json;charset=UTF-8");
        out.print("{\"error\":\"user not found\"}");
      }
      out.close();
    }
  }

  /**
   * Login
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException 
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    User user = (User) ModelFactory.Create(ModelType.USER);
    user.set("id", request.getParameter("user"));
    user.set("password", request.getParameter("password"));
    
    if(user.execute("login")){
      HttpSession session = request.getSession(true);
      session.setAttribute("user",user);
      response.setStatus(200);
    }
    else {
      response.setStatus(400);
    }
  }
  
  /**
   * Logout
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException 
   */
  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    HttpSession session = request.getSession(false);

    if(session != null){
      session.invalidate();
      response.setStatus(200);
    }
    else {
      response.setStatus(400);
    }
  }

  @Override
  public String getServletInfo() {
    return "Account Servlet";
  }
}
