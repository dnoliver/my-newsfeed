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
    
    public UserRouter(){
        super();
        this.models.put("1","{\"id\":\"1\",\"type\":\"User\",\"name\":\"User\",\"category\":\"student\",\"connected\":\"true\"}");
        this.models.put("2","{\"id\":\"2\",\"type\":\"User\",\"name\":\"User\",\"category\":\"student\",\"connected\":\"true\"}");
        this.models.put("3","{\"id\":\"3\",\"type\":\"User\",\"name\":\"User\",\"category\":\"student\",\"connected\":\"true\"}");
    }
    
    @Override
    public boolean canHandle(HttpServletRequest request){
        return request.getPathInfo().matches("/model/user");
    }
    
}
