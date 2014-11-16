/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

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
import models.Model;
import models.ModelFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author dnoliver
 */
@WebServlet(name = "ModelServlet", urlPatterns = {"/model/*"})
public class ModelServlet extends HttpServlet {

  /*
   * select
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    PathParser path;
    Model model;

    path = new PathParser(request.getPathInfo());
    model = ModelFactory.Create(ModelFactory.TypeFor(path.resource()));
    model.set("id", path.id());
    model.select();
    
    try {   
      PrintWriter out = response.getWriter();
      response.setStatus(200);
      response.setContentType("application/json;charset=UTF-8");
      out.print(model.toJSONString());
      out.close();
    } catch (IOException ex) {
      Logger.getLogger(ModelServlet.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /*
   * create
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    PathParser path;
    JSONParser parser;
    JSONObject json;
    Model model;
    
    path = new PathParser(request.getPathInfo());
    model = ModelFactory.Create(ModelFactory.TypeFor(path.resource()));
    parser = new JSONParser();
    
    try{
      BufferedReader reader = request.getReader();
      json = (JSONObject) parser.parse(reader.readLine());
      model.set(json);
      model.save();
      response.setContentType("application/json;charset=UTF-8");
      response.setStatus(200);
      PrintWriter writer = response.getWriter();
      writer.print(json.toString());
      writer.close();
    } catch (IOException ex) {
      Logger.getLogger(ModelServlet.class.getName()).log(Level.SEVERE, null, ex);
    }
    catch (ParseException ex) {
      Logger.getLogger(ModelServlet.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /*
   * update
   */
  @Override
  public void doPut(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    PathParser path;
    JSONObject json;
    JSONParser parser;
    Model model;

    parser = new JSONParser();
    path = new PathParser(request.getPathInfo());
    model = ModelFactory.Create(ModelFactory.TypeFor(path.resource()));
    
    try{
      BufferedReader reader = request.getReader();
      json = (JSONObject) parser.parse(reader.readLine());
      model.set(json);
      model.update();
      response.setContentType("application/json;charset=UTF-8");
      response.setStatus(200);
      PrintWriter writer = response.getWriter();
      writer.print(json.toString());
      writer.close();
    } catch (IOException ex) {
      Logger.getLogger(ModelServlet.class.getName()).log(Level.SEVERE, null, ex);
    }
    catch( ParseException ex ){
      Logger.getLogger(ModelServlet.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  @Override
  public String getServletInfo() {
    return "Model Servlet";
  }
}
