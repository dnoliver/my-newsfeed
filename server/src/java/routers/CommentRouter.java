/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package routers;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author dnoliver
 */
public class CommentRouter extends Router{
    public CommentRouter(){
        super();
    }
    
    @Override
    public boolean canHandle(HttpServletRequest request) {
        return 
            request.getPathInfo().matches("/model/comment") ||
            request.getPathInfo().matches("/model/comment/[0-9]*");
    }
}
