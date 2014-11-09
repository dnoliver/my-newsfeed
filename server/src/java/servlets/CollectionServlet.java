/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import db.ObjectDataBase;
import helpers.PathParser;
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
@WebServlet(name = "CollectionServlet", urlPatterns = {"/collection/*"})
public class CollectionServlet extends HttpServlet {

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
    PathParser path;
    ObjectDataBase db;
    String res;

    db = new ObjectDataBase();
    path = new PathParser(request.getPathInfo());

    try (PrintWriter out = response.getWriter()) {
      response.setContentType("application/json;charset=UTF-8");
      response.setStatus(200);
      res = db.get(path.resource()).toString();
      out.print(res);
    }
    catch (IOException ex) {
      Logger.getLogger(CollectionServlet.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Collection Servlet";
  }
}
