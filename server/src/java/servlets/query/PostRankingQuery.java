/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.query;

import db.QueryParameter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dnoliver
 */
@WebServlet(name = "PostRankingQuery", urlPatterns = {"/query/ranking"})
public class PostRankingQuery extends QueryServlet {

  @Override
  public List<Map<String, Object>> processRequest(HttpServletRequest request) {
    String query = "{call getPostsRanking(?,?,?,?,?)}";
    List<QueryParameter> params = new LinkedList();
    params.add(QueryParameter.Create(request.getParameter("user"), Types.VARCHAR, 1));
    params.add(QueryParameter.Create(request.getParameter("subject"), Types.VARCHAR, 2));
    params.add(QueryParameter.Create(request.getParameter("career"), Types.VARCHAR, 3));
    params.add(QueryParameter.Create(request.getParameter("from"), Types.DATE, 4));
    params.add(QueryParameter.Create(request.getParameter("to"), Types.DATE, 5));
    return this.db.executeProcedure(query, params);
  }

  @Override
  public String getServletInfo() {
    return "Short description";
  }
}
