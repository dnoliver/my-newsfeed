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
public class NewsfeedRouter extends Router {
    public NewsfeedRouter(){
        super();
       
        models.put("4","{\"id\":4,\"type\":\"Newsfeed\",\"title\":\"NewsFeed\",\"visible\":true,\"available\":true}");
        models.put("5","{\"id\":5,\"type\":\"Newsfeed\",\"title\":\"NewsFeed\",\"visible\":true,\"available\":true}");
        models.put("6","{\"id\":6,\"type\":\"Newsfeed\",\"title\":\"NewsFeed\",\"visible\":true,\"available\":true}");
    }
    
    @Override
    public boolean canHandle(HttpServletRequest request) {
        return request.getPathInfo().matches("/model/newsfeed");
    }
}
