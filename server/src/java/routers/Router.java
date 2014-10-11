/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package routers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dnoliver
 */
public class Router implements AbstractRouter{
    
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) {
        if(this.canHandle(request)){    
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
        
    }

    @Override
    public void handlePut(HttpServletRequest request, HttpServletResponse response) {
        
    }
}
