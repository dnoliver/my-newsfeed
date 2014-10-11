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
public class UserRouter extends Router{
    
    @Override
    public boolean canHandle(HttpServletRequest request){
        return request.getPathInfo().matches("/model/user");
    }
    
}
