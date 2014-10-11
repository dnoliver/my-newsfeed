/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package routers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dnoliver
 */
public class Router implements AbstractRouter{
    
    Map<String,String> models;
    
    public Router() {
        models = new HashMap();
    }
    
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) {
        if(this.canHandle(request)){    
            System.out.println(request.getMethod() + ":" + request.getPathInfo());
            
            switch(request.getMethod()){
                case "GET": this.handleGet(request,response);
                    break;
                case "PUT": this.handlePut(request, response);
                    break;
            }
        }
    }    

    @Override
    public boolean canHandle(HttpServletRequest request) {
        return false;
    }

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response) {
        Matcher m = Pattern.compile("\\d+").matcher(request.getPathInfo());
        
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {   
            if(m.find()){
                out.print(models.get(m.group()));
            }
            else {
                out.print(Arrays.toString(models.values().toArray()));
            }
        } catch (IOException ex) {
            Logger.getLogger(SessionRouter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void handlePut(HttpServletRequest request, HttpServletResponse response) {
        Matcher m = Pattern.compile("\\d+").matcher(request.getPathInfo());
        m.find();
        String json;
       
        try(BufferedReader reader = request.getReader()) {
            json = reader.readLine();
            System.out.println(json);
            models.put(m.group(), json);
        } catch (IOException ex) {
            Logger.getLogger(SessionRouter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
