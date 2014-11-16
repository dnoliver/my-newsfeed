/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import db.DataBaseQuery;
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
    DataBaseQuery dbquery;
    
    dbquery = new DataBaseQuery();
    query = new QueryParser(request.getPathInfo());
    
    dbquery.set("table", query.resource());
    dbquery.set("filter", query.filter());
    dbquery.set("value", query.value());
    
    try {   
      PrintWriter out = response.getWriter();
      dbquery.execute();
      response.setContentType("application/json;charset=UTF-8");
      response.setStatus(200);
      out.print(dbquery.toJSONString());
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
