package oracle.coherence.web;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

import com.telefonica.coco.lib.security.domain.OrganizationalRoleUserDetails;
import com.telefonica.coco.security.domain.PrincipalBase;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.springframework.session.MapSession;

@WebServlet(name = "Monitor", urlPatterns = { "/monitor" })
public class Monitor extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        
        String nombreCache = request.getParameter("cache");

        if ((nombreCache == null) || (nombreCache.equals(""))) {
            nombreCache = "CACHE_SCRTYCTX";

        } 
        String error = "";
        try {
            NamedCache cache = CacheFactory.getCache(nombreCache);
            cache.put("TEST1976", "CACHE FUNCIONANDO!!!!", 10000);
        } catch (Exception e) {
            // TODO: Add catch code
            error = e.getMessage();
        }

        String valortest = "";
        MapSession valor;
        try {
            NamedCache cache = CacheFactory.getCache(nombreCache);
            valortest = (String) cache.get("TEST1976");
            cache.remove("TEST1976");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head><title>" + nombreCache + "</title></head>");
            out.println("<body>");
            if ((valortest != null) && (!valortest.equals(""))) {
                String key = request.getParameter("key");
                if ((key != null) && (!key.equals(""))) {
                    valor = (MapSession) cache.get(key);
                    out.println("<p>KEY:" + key + "</p>");
                    MapSession mapasesion = (MapSession)cache.get(key);
                    Set<String> clavessesion = mapasesion.getAttributeNames();
                    Iterator<String> csi = clavessesion.iterator();
                    while(csi.hasNext()) {
                        String clavesesion = csi.next();
                        out.println("<p><b> </b>" + clavesesion + " = "  + mapasesion.getAttribute(clavesesion) +  "</P>");
                    }
                } else {
                    
                        try {
                    Iterator<String> ksi = cache.keySet().iterator();
                    while(ksi.hasNext()) {
                        
                            Object o = ksi.next();
                            String keyi = "";
                            if (o instanceof PrincipalBase)
                                keyi =  "[PrincipalBase]"+((PrincipalBase)o).getOrgRoleId() + " " + 
                                        ((PrincipalBase)o).getPartyId() + " " +
                                        ((PrincipalBase)o).getProvenience() + " " +
                                        ((PrincipalBase)o).getScope();
                                    
                            else
                                keyi = (String)o;
                            out.println("<p><b>KEY:</b> " + keyi + "</p>");
                        
                            if (cache.get(o) instanceof MapSession)   
                            {
                                MapSession mapasesion = (MapSession) cache.get(o);

                                Set<String> clavessesion = mapasesion.getAttributeNames();
                                Iterator<String> csi = clavessesion.iterator();
                                while (csi.hasNext()) {
                                    String clavesesion = csi.next();
                                    out.println("<p><b>VALUE</b>" + clavesesion + " = " +
                                                mapasesion.getAttribute(clavesesion) + "</P>");

                                }
                            }
                        else {
                                OrganizationalRoleUserDetails userDetails = (OrganizationalRoleUserDetails)cache.get(o);
                                String code = userDetails.getCode();
                                
                                out.println("<p><b>VALUE</b>" + code + "</P>");
                            }
                        
                    }
                    } catch (Exception e) {
                        // TODO: Add catch code
                        System.out.println(e.toString());
                    }
                    
                    /*
                    try {
                    Iterator<PrincipalBase> ksi = cache.keySet().iterator();
                    while(ksi.hasNext()) {
                    
                        try {
                                
                                PrincipalBase keyi = ksi.next();
                                out.println("<p><b>KEY:</b> " + keyi.getPartyId() + "</p>");
                                System.out.println("2.................");
                                out.println(cache.get(keyi));

                            } catch (Exception e) {
                                // TODO: Add catch code
                                System.err.println(e.toString());
                            }
                    
                    }
                    } catch (Exception e) {
                    // TODO: Add catch code
                    e.printStackTrace();
                    }
*/
                }
            } else {
                out.println("<p>ERROR DE ACCESO A CACHE:" + error + "</p>");
            }

           
            out.println("</body></html>");
            out.close();
        } catch (Exception e) {
            // TODO: Add catch code
            error = e.getMessage();
            e.printStackTrace();
        }

    }
}
