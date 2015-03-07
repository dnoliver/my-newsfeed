/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.query;

import servlets.query.AbstractQueryServlet;
import db.DataBase;
import db.QueryParameter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Model;
import models.ModelFactory;
import org.json.simple.JSONValue;

/**
 *
 * @author dnoliver
 */
@WebServlet(name = "QueryServlet", urlPatterns = {"/query"})
public class QueryServlet extends HttpServlet implements AbstractQueryServlet{

  DataBase db;
  
  @Override
  public void init(){
    this.db = new DataBase();
  }
  
  @Override
  public List<Map<String, Object>> processRequest(HttpServletRequest request) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      PrintWriter out = response.getWriter();
      response.setContentType("application/json;charset=UTF-8");
      response.setStatus(200);
      out.print(JSONValue.toJSONString(this.processRequest(request)));
      out.close();
    } catch (IOException ex) {
      Logger.getLogger(NewsfeedQueryServlet.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  @Override
  public String getServletInfo() {
    return "Query Servlet";
  }
}
