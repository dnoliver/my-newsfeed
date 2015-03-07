/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.query;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author dnoliver
 */
public interface AbstractQueryServlet {
  List<Map<String, Object>> processRequest(HttpServletRequest request);
}
