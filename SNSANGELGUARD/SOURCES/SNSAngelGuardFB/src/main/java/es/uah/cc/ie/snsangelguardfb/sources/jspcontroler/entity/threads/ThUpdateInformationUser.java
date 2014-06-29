/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.entity.threads;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.sun.jersey.api.client.UniformInterfaceException;
import es.uah.cc.ie.snsangelguardfb.SNSAngelGuardFBManager;
import es.uah.cc.ie.snsangelguardfb.exception.InterDataBaseException;
import es.uah.cc.ie.snsangelguardfb.exception.InterEmailException;
import es.uah.cc.ie.snsangelguardfb.exception.InterProcessException;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.openide.util.Exceptions;

/**
 * Hilo que actualiza la informacion del usuario de Facebook tras guardar su 
 * configuracion de la aplicacion.
 * 
 * @author josejavierblecuadepedro1
 */
public class ThUpdateInformationUser extends Thread {
    
    /** Logger Class */
    private static Logger logger = Logger.getLogger(ThUpdateInformationUser.class);
    
    /** Clase manager principal */
    private SNSAngelGuardFBManager snsObject;

    /**
     * Constructor de clase.
     * 
     * @param snsObject 
     */
    public ThUpdateInformationUser(SNSAngelGuardFBManager snsObject) {
        this.snsObject = snsObject;
    }
    
    @Override
    public void run() {
        logger.info("ThUpdateInformationUser - run: Inicio run...");
        try {
            logger.info("ThUpdateInformationUser - run: Actualizando informaci?n...");
            snsObject.getUserSettingsDaoManager().getUserInfo(false);
            logger.info("ThUpdateInformationUser - run: Informaci?n del usuario correctamente actualizada!!");
        } catch (UniformInterfaceException | IOException | JSONException | MySQLIntegrityConstraintViolationException | InterDataBaseException | InterProcessException | InterEmailException ex) {
            logger.info("ThUpdateInformationUser - run: Se ha producido una excepcion del tipo " + ex.getClass() + ": " + ex.getMessage());
            Exceptions.printStackTrace(ex);
        }
        
        logger.info("ThUpdateInformationUser - run: Fin run!!");
    }
}
