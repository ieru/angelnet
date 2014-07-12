/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.entity;

import com.sun.jersey.api.client.UniformInterfaceException;
import es.uah.cc.ie.snsangelguardfb.SNSAngelGuardFBManager;
import es.uah.cc.ie.snsangelguardfb.exception.InterDataBaseException;
import es.uah.cc.ie.snsangelguardfb.exception.InterEmailException;
import es.uah.cc.ie.snsangelguardfb.exception.InterProcessException;
import es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.GenericJSPControler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 * Actualiza todos los amigos de Facebook de un usuario y los recarga en la
 * pantalla de configuracion de angeles.
 *
 * @author josejavierblecuadepedro1
 */
public class UpdateFacebookFriendsOnlineJSPControler extends GenericJSPControler  {

    /** Logger Class */
    private static Logger logger = Logger.getLogger(DeleteAngelSelectedJSPControler.class);

    /** Clase Manager de la aplicacion */
    private SNSAngelGuardFBManager snsObject;

    /** Atributo request de la sesion */
    private HttpServletRequest request;

    /** Atributo response de la sesion */
    private HttpServletResponse response;

    public UpdateFacebookFriendsOnlineJSPControler(HttpServletRequest request, HttpServletResponse response) throws InterDataBaseException, InterProcessException, InterEmailException {
        try {
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
     * Obtiene el objeto manager de la aplicacion.
     * 
     * @return SNSAngelGuardFBManager 
     */
    public SNSAngelGuardFBManager getSnsObject() {
        return snsObject;
    }
    
    
    @Override
    public void process() throws InterDataBaseException, InterProcessException, InterEmailException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Inicio process...");
        try {
            request.setCharacterEncoding("UTF-8");
            HttpSession sesion = request.getSession(false);

            // Actualizamos los amigos
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Actualizando amigos en Facebook...");
            this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUserFriends();
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Amigos de Facebook correctamente actualizados!!");

            // Volvemos a la pagina de configuracion
            response.sendRedirect(request.getContextPath() + "/settingsSNSAngelGuard.jsp?newConection=1&ok=" + 15);
        } catch (UnsupportedEncodingException | UniformInterfaceException  ex) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid()+ " - process: Excepcion capturada " + ex.getClass().getName() + ": " + ex.getMessage());
            try {
                // Volvemos a la pagina de configuracion con error
                response.sendRedirect(request.getContextPath() + "/settingsSNSAngelGuard.jsp?newConection=1&ok=" + 8);
            } catch (IOException ex1) {
                logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid()+ " - process: Excepcion capturada " + ex.getClass().getName() + ": " + ex.getMessage());
            this.snsObject.getExceptionManager().initControlException(ex1);
            }
        } catch (IOException ex) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid()+ " - process: Excepcion capturada " + ex.getClass().getName() + ": " + ex.getMessage());
            try {
                // Volvemos a la pagina de configuracion con error
                response.sendRedirect(request.getContextPath() + "/settingsSNSAngelGuard.jsp?newConection=1&ok=" + 8);
            } catch (IOException ex1) {
                logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid()+ " - process: Excepcion capturada " + ex.getClass().getName() + ": " + ex.getMessage());
                this.snsObject.getExceptionManager().initControlException(ex1);
            }
        }
    }
    
}
