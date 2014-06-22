/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.entity;

import es.uah.cc.ie.snsangelguardfb.SNSAngelGuardFBManager;
import es.uah.cc.ie.snsangelguardfb.exception.InterDataBaseException;
import es.uah.cc.ie.snsangelguardfb.exception.InterEmailException;
import es.uah.cc.ie.snsangelguardfb.exception.InterProcessException;
import es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.GenericJSPControler;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Controlador JSP para almacenar angeles seleccionados en la aplicacion.
 * 
 * @author josejavierblecuadepedro1
 */
public class SaveNewAngelJSPControler extends GenericJSPControler {
    
    /** Logger Class */
    private static Logger logger = Logger.getLogger(SaveNewAngelJSPControler.class);

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
    
    public SaveNewAngelJSPControler(HttpServletRequest request, HttpServletResponse response) throws InterDataBaseException, InterProcessException, InterEmailException {
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

            /**
             * Recuperamos el identificador del angel
             */
            this.idAngel = request.getParameter("idAngel");

            this.typeAngel = request.getParameter("typeAngel");

            this.snsObject.logSession(request, response);



            if (this.typeAngel.equals("F")) {
                // Obtenemos la informacion de Facebook del usuario
                String[] newAngelFacebook = this.snsObject.getAngelsUtilities().getAngelDates(this.idAngel);

                // Obtengo en formato JSON la informacion del angel
                JSONObject jsonNewAngelFacebook = this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getNewAngelFacebook(newAngelFacebook);

                // Almacenamos el nuevo angel en la base de datos
                jsonNewAngelFacebook = this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().putNewAngelFacebook(jsonNewAngelFacebook);

                // Actualizo la coleccion de angeles del usuario
                this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().setCollectionAngels(jsonNewAngelFacebook);

                // Volvemos a la pagina de configuracion
                response.sendRedirect(request.getContextPath() + "/settingsSNSAngelGuard.jsp?newConection=1&ok=1&typeAngel=F&idFacebookAngel=" + jsonNewAngelFacebook.getString("idFacebook")
                        + "&uidAngel=" + jsonNewAngelFacebook.getString("uidAngel")
                        + "&uidPublic=" + this.getSnsObject().getUserSettingsDaoManager().getUserSettingsDAO().getUidPublic());

            }
            
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Fin process...");
        } catch (IOException | JSONException ex) {
            this.snsObject.getExceptionManager().initControlException(ex);
        }
    }
    
}
