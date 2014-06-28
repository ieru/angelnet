/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.entity.data;

import es.uah.cc.ie.snsangelguardfb.SNSAngelGuardFBManager;
import es.uah.cc.ie.snsangelguardfb.exception.InterDataBaseException;
import es.uah.cc.ie.snsangelguardfb.exception.InterEmailException;
import es.uah.cc.ie.snsangelguardfb.exception.InterProcessException;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.openide.util.Exceptions;

/**
 *
 * @author josejavierblecuadepedro1
 */
public class ThProcessCheckFilter extends Thread {
    
    /** Logger Class */
    private static Logger logger = Logger.getLogger(ThProcessCheckFilter.class);
    
    /** Clase manager principal */
    private SNSAngelGuardFBManager snsObject;
    
    /** Objeto HttpServletRequest de la llamada */
    private HttpServletRequest request;
    
    /** JSON con los datos del angel */
    private JSONObject jsonAngel;
    
    /** Flag que indica si el angel ha aceptado la aplicacion */
    private boolean isAceptApp;
    
    /** Tipo de angel */
    private String typeAngel;
    
    /** JSON con los datos del usuario de la aplicacion instanciado */
    private JSONObject jsonUser;

    /**
     * Constructor de clase.
     *
     * @param snsObject Clase manager principal.
     * @param request HttpServletRequest de la llamada 
     * @param jsonAngel JSON con los datos del angel.
     * @param isAceptApp Flag que indica si el angel ha aceptado la aplicacion.
     * @param typeAngel Tipo de angel.
     * @param jsonUser JSON con los datos del usuario de la aplicacion instanciado.
     */
    public ThProcessCheckFilter(SNSAngelGuardFBManager snsObject, HttpServletRequest request, JSONObject jsonAngel, boolean isAceptApp, String typeAngel, JSONObject jsonUser) {
        this.snsObject = snsObject;
        this.request = request;
        this.jsonAngel = jsonAngel;
        this.isAceptApp = isAceptApp;
        this.typeAngel = typeAngel;
        this.jsonUser = jsonUser;
    }
    
    @Override
    public void run(){
        try {
            logger.info("ThProcessCheckFilter - run: Inicio run...");
            if (isAceptApp) {
                logger.info("ThProcessCheckFilter - run: El angel ha aceptado la aplicacion: Inicio...");
                jsonAngel.put("acceptAngel", "1");                

                // Guardamos la configuracion del angel
                logger.info("ThProcessCheckFilter - run: - run: Guardando los datos del angel...");
                snsObject.getAngelsUtilities().setJsonAngel(jsonAngel);
                logger.info("ThProcessCheckFilter - run: Datos del angel guardados correctamente!!");

                // Se realiza el primer chequeo de informaci?n
                logger.info("ThProcessCheckFilter - run: Realizando el primer chequeo de informacion para el angel: " + jsonAngel.getString("uidAngel"));
                snsObject.getGenericFilter().firstCheckAngelConfirmation(request, jsonAngel);
                logger.info("ThProcessCheckFilter - run: Primer chequeo de informacion para el angel " + jsonAngel.getString("uidAngel") + " realizado correctamente!!");

                // Guardamos la informaci?n de configuraci?n
                logger.info("ThProcessCheckFilter - run: Guardando la informacion de configuracion del usuario: " + jsonUser.getString("uid"));
                snsObject.getUserSettingsDaoManager().checkAngelConfirmation(jsonUser);
                logger.info("ThProcessCheckFilter - run: Informacion de configuracion del usuario " + jsonUser.getString("uid") + " correctamente almacenada!!");
                logger.info("ThProcessCheckFilter - run: El angel ha aceptado la aplicacion: Fin!!");
            } else {
                logger.info("ThProcessCheckFilter - run:El angel no ha aceptado la aplicacion: Inicio...");
                // Eliminamos las posibles relaciones con los filtros de la aplicacion del angel
                logger.info("ThProcessCheckFilter - run: Eliminando las posibles relaciones con los filtros de la aplicacion del angel: " + jsonAngel.getString("uidAngel"));
                snsObject.getUserSettingsDaoManager().getUserSettingsDAO().deleteAngelFiltersRelationship(jsonAngel);
                logger.info("ThProcessCheckFilter - run: Relaciones con los filtros de la aplicacion del angel " + jsonAngel.getString("uidAngel") + " correctamente eliminadas!!");

                // Eliminamos el angel de base de datos
                logger.info("ThProcessCheckFilter - run: Eliminando el angel " + jsonAngel.getString("uidAngel") + " de la aplicacion...");
                snsObject.getClient().settingsAngels_delAngelByUid(jsonAngel.getString("uidAngel"));
                logger.info("ThProcessCheckFilter - run: Angel " + jsonAngel.getString("uidAngel") + " eliminado correctamente de la aplicacion!!");
                
                logger.info("ThProcessCheckFilter - run: El angel no ha aceptado la aplicacion: Fin!!");
            }
            
            logger.info("ThProcessCheckFilter - run: Fin run...");
        } catch (JSONException | InterDataBaseException | InterProcessException | InterEmailException ex) {
            logger.info("ThProcessCheckFilter - run: Se ha producido una excepcion del tipo " + ex.getClass() + ": " + ex.getMessage());
            Exceptions.printStackTrace(ex);
        }
    }
}
