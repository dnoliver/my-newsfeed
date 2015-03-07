/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.query;

import db.QueryParameter;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author dnoliver
 */
@WebServlet(name = "NewsfeedQueryServlet", urlPatterns = {"/query/newsfeed"})
public class NewsfeedQueryServlet extends QueryServlet {

  @Override
  public List<Map<String, Object>> processRequest(HttpServletRequest request) {
    String query = "{call getNewsfeedsForUser(?)}";
    List<QueryParameter> params = new LinkedList();
    params.add(QueryParameter.Create(request.getParameter("user"), Types.VARCHAR, 1));
    return this.db.executeProcedure(query, params);
  }
 
  @Override
  public String getServletInfo() {
    return "Short description";
  }
}
