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
import java.security.NoSuchProviderException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Elimina un angel seleccionado previamente.
 * 
 * @author josejavierblecuadepedro1
 */
public class DeleteAngelSelectedJSPControler extends GenericJSPControler {
    
        /** Logger Class */
    private static Logger logger = Logger.getLogger(DeleteAngelSelectedJSPControler.class);

    /** Clase Manager de la aplicacion */
    private SNSAngelGuardFBManager snsObject;

    /** Atributo request de la sesion */
    private HttpServletRequest request;

    /** Atributo response de la sesion */
    private HttpServletResponse response;
    
    /** Tipo de angel */
    private String typeAngel;
    
    /** Identificador del angel(ID de Facebook o su correo electronico) */
    private String idAngel;

    /**
     * Constructor de clase.
     * 
     * @param request
     * @param response 
     */
    public DeleteAngelSelectedJSPControler(HttpServletRequest request, HttpServletResponse response) throws InterDataBaseException, InterProcessException, InterEmailException {
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
        try {
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Inicio process...");

            request.setCharacterEncoding("UTF-8");
            HttpSession sesion = request.getSession(false);

            // Recuperamos el identificador del angel
            this.idAngel = request.getParameter("idAngel");

            // Recuperamos el tipo del angel
            this.typeAngel = request.getParameter("typeAngel");

            // Obtenemos la sesion de la aplicacion
            this.snsObject.logSession(request, response);

            if (this.typeAngel.equals("F")) {
                // Obtenemos la informacion del angel en base de datos
                JSONObject jsonAngelDB = new JSONObject(this.snsObject.getClient().settingsAngels_getAngelsByUidFacebook(String.class, this.idAngel));
                logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Datos de base de datos: " + jsonAngelDB.toString());
                JSONObject jsonAngelFacebook = jsonAngelDB.getJSONArray("settingsAngels").getJSONObject(0);
                logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Datos del angel sacados del array: " + jsonAngelFacebook.toString());
                
                // Eliminamos las posibles relaciones con los filtros de la aplicacion del angel
                this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().deleteAngelFiltersRelationship(jsonAngelFacebook);

                // Eliminamos el angel de base de datos
                this.snsObject.getClient().settingsAngels_delAngelByUid(jsonAngelFacebook.getString("uidAngel"));

                if(jsonAngelFacebook.getString("confirmAngel").equals("1")){
                    this.snsObject.getEmailObject().sendMailDeleteAngel(jsonAngelFacebook, this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUidPublic());
                }
                
                // Volvemos a la pagina de configuracion
                response.sendRedirect(request.getContextPath() + "/settingsSNSAngelGuard.jsp?newConection=1&ok=1");
            }
            
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Fin process...");
        } catch (IOException | JSONException | NoSuchProviderException | MessagingException | UniformInterfaceException ex) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid()+ " - process: Excepcion capturada " + ex.getClass().getName() + ": " + ex.getMessage());
            this.snsObject.getExceptionManager().initControlException(ex);
        } 
    } 
}