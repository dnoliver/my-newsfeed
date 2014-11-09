/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import db.ObjectDataBase;
import helpers.PathParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author dnoliver
 */
@WebServlet(name = "ModelServlet", urlPatterns = {"/model/*"})
public class ModelServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    PathParser path;
    ObjectDataBase db;
    String res;

    db = new ObjectDataBase();
    path = new PathParser(request.getPathInfo());
    
    res = db.get(path.resource(), path.id()).toString();

    try (PrintWriter out = response.getWriter()) {   
      response.setStatus(200);
      response.setContentType("application/json;charset=UTF-8");
      out.print(res);
      out.close();
    } catch (IOException ex) {
        Logger.getLogger(ModelServlet.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    PathParser path;
    JSONParser parser;
    JSONObject json;
    ObjectDataBase db = new ObjectDataBase();
    path = new PathParser(request.getPathInfo());
    parser = new JSONParser();
    
    try(BufferedReader reader = request.getReader()) {
      json = (JSONObject) parser.parse(reader.readLine());
      db.post(path.resource(),json);
      response.setContentType("application/json;charset=UTF-8");
      response.setStatus(200);
      PrintWriter writer = response.getWriter();
      writer.print(json.toString());
      writer.close();
    } catch (IOException | ParseException ex) {
      Logger.getLogger(ModelServlet.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public void doPut(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    PathParser path;
    JSONObject json;
    JSONParser parser;
    ObjectDataBase db = new ObjectDataBase();

    parser = new JSONParser();
    path = new PathParser(request.getPathInfo());
    
    try(BufferedReader reader = request.getReader()) {
      json = (JSONObject) parser.parse(reader.readLine());
      db.put(path.resource(),json);
      response.setContentType("application/json;charset=UTF-8");
      response.setStatus(200);
      PrintWriter writer = response.getWriter();
      writer.print(json.toString());
      writer.close();
    } catch (IOException | ParseException ex) {
      Logger.getLogger(ModelServlet.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  @Override
  public String getServletInfo() {
    return "Model Servlet";
  }
}
