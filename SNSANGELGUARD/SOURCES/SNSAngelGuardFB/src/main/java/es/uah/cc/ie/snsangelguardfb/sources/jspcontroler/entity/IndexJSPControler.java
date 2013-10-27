/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.entity;

import es.uah.cc.ie.snsangelguardfb.exception.InterDataBaseException;
import es.uah.cc.ie.snsangelguardfb.exception.InterEmailException;
import es.uah.cc.ie.snsangelguardfb.exception.InterProcessException;
import es.uah.cc.ie.snsangelguardfb.SNSAngelGuardFBManager;
import es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.GenericJSPControler;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 * Clase de control de la pagina index.jsp.
 * 
 * @author tote
 */
public class IndexJSPControler extends GenericJSPControler{

    /** Logger class  */
    private static Logger logger = Logger.getLogger(IndexJSPControler.class);
    
    /** Manager de la aplicacion */
    private SNSAngelGuardFBManager snsObject;
    
    /** Atributo request de la sesion */
    private HttpServletRequest request;
    
    /** Atributo response de la sesion */
    private HttpServletResponse response;
    
    /**
     * Obtiene el objeto de la peticion HTTP.
     * 
     * @return HttpServletRequest
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * Obtiene la respuesta a la peticion HTTP.
     * 
     * @return HttpServletResponse 
     */
    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * Obtiene el objeto de sesion de la aplicacion.
     * 
     * @return SNSAngelGuardFBManager 
     */
    public SNSAngelGuardFBManager getSnsObject() {
        return snsObject;
    }

    /**
     * Constructor de clase.
     * 
     * @param request
     * @param response 
     */
    public IndexJSPControler(HttpServletRequest request, HttpServletResponse response) throws InterDataBaseException, InterProcessException, InterEmailException {
        try {
            HttpSession session = request.getSession(true);
            this.snsObject = SNSAngelGuardFBManager.getSessionInstance(request);
            this.request = request;
            this.response = response;
        } catch (FileNotFoundException ex) {
            this.snsObject.getExceptionManager().initControlException(ex);
        } catch (IOException ex) {
            this.snsObject.getExceptionManager().initControlException(ex);
        }
    }

    /**
     * Este metodo iniciara sesion en Facebook, y determinara la pagina siguiente en funcion de si es un 
     * usuario nuevo de la aplicacion o no. Podr? lanzar excepciones del tipo InterDataBaseException, InterProcessException o InterEmailException.
     * 
     * @throws InterDataBaseException
     * @throws InterProcessException
     * @throws InterEmailException 
     */
    @Override
    public void process() throws InterDataBaseException, InterProcessException, InterEmailException{ }
}