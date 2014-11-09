/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import db.ObjectDataBase;
import helpers.QueryParser;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dnoliver
 */
@WebServlet(name = "QueryServlet", urlPatterns = {"/query/*"})
public class QueryServlet extends HttpServlet {


  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    QueryParser query;
    ObjectDataBase db;
    String res;
    
    db = new ObjectDataBase();
    query = new QueryParser(request.getPathInfo());
    
    try (PrintWriter out = response.getWriter()) {   
      res = db.query(query.resource(), query.filter(), query.value()).toString();
      response.setContentType("application/json;charset=UTF-8");
      response.setStatus(200);
      out.print(res);
      out.close();
    } catch (IOException ex) {
      Logger.getLogger(QueryServlet.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Query Servlet";
  }

}
