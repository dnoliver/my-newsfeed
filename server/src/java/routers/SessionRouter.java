/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package routers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
public class SessionRouter extends Router {
    
    Map<String,String> sessions;
    
    public SessionRouter() {
        sessions = new HashMap();
    }
    
    @Override
    public boolean canHandle(HttpServletRequest request){
        return request.getPathInfo().matches("/session/[0-9]*");
    }
    
    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response) {
        Matcher m = Pattern.compile("\\d+").matcher(request.getPathInfo());
        m.find();
        String id = m.group();
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {   
            out.print(sessions.get(id));
        } catch (IOException ex) {
            Logger.getLogger(SessionRouter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void handlePut(HttpServletRequest request, HttpServletResponse response) {
        Matcher m = Pattern.compile("\\d+").matcher(request.getPathInfo());
        m.find();
       
        try(BufferedReader reader = request.getReader()) {
            sessions.put(m.group(), reader.readLine());
        } catch (IOException ex) {
            Logger.getLogger(SessionRouter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
