/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package routers;
import static java.lang.System.in;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dnoliver
 */
public class RouterRegistry {
    static RouterRegistry instance = null;
    List<Router> routers;
    
    static public RouterRegistry getInstance(){
        if(instance == null){
            instance = new RouterRegistry();
        }
        
        return instance;
    }
    
    public RouterRegistry(){
        this.routers = new ArrayList<Router>();
    }
    
    public void addRouter(Router router){
        routers.add(router);
    }
    
    public void handle(HttpServletRequest request, HttpServletResponse response){
        System.out.println("RouterRegistry:handle()");
        for(Router router : routers){
            router.handle(request, response);
        }
    }
}
