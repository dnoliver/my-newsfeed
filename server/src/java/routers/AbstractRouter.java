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
public interface AbstractRouter {
    void handle(HttpServletRequest request, HttpServletResponse response);
    void handleGet(HttpServletRequest request, HttpServletResponse response);
    void handlePut(HttpServletRequest request, HttpServletResponse response);
    boolean canHandle(HttpServletRequest request);
}
