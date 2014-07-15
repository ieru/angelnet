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
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Clase controladora de la pantalla de introduccion y aceptacion del angel de Facebook.
 * En ella, el angel debera introducir manualmente el email donde desea recibir las
 * notificaciones de la aplicacion.
 * 
 * @author josejavierblecuadepedro1
 */
public class SaveEmailFacebookContactJSPControler extends GenericJSPControler {
    
    /** Logger Class */
    private static Logger logger = Logger.getLogger(SaveEmailFacebookContactJSPControler.class);
    
    /** URL a la imagen de carga entre p?ginas */
    public final String PATH_IMAGE_LOADING = "../SNSAngelGuardFB/resources/legalAccepted/load.gif";

    /** Clase Manager de la aplicacion */
    private SNSAngelGuardFBManager snsObject;

    /** Atributo request de la sesion */
    private HttpServletRequest request;

    /** Atributo response de la sesion */
    private HttpServletResponse response;
    
    /** Identificador del angel */
    private String uidAngel;
    
    /** Identificador del usuario a quien pertenece el angel (Inicialmente vendra cifrada) */
    private String uidPublicUser;
    
    /** Informacion en formato JSON del angel cargada de base de datos */
    private JSONObject jsonAngel;
    
    /** Nombre del angel que saldra por pantalla */
    private String nameAngelValue;
    
    /** Mensaje de autorizacion para el envio de notificaciones */
    private String notificationMsg;

    /** Mensaje de email vacio */
    private String emptyEmailMsg;
    
    /** Mensaje de email no valido */
    private String notValidEmailMsg;
    
    /** Mensaje de carga para el loader */
    private String loaderSave;
    
    /** Mensaje de espera para el loader */
    private String loaderWait;

    /**
     * Obtiene el identificador en base de datos del angel.
     * 
     * @return String 
     */
    public String getUidAngel() {
        return uidAngel;
    }

    /**
     * Obtiene el identificador publico en base de datos del usuario propietario
     * del angel.
     * 
     * @return String 
     */
    public String getUidPublicUser() {
        return uidPublicUser;
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
     * Obtiene toda la informacion del angel referido.
     * 
     * @return JSONObject 
     */
    public JSONObject getJsonAngel() {
        return jsonAngel;
    }

    /**
     * Obtiene el nombre del angel al que se hace referencia.
     * 
     * @return String 
     */
    public String getNameAngelValue() {
        return nameAngelValue;
    }
    
    /**
     * Obtiene el mensaje para el envio de notificaciones.
     * 
     * @return String 
     */
    public String getNotificationMsg() {
        return notificationMsg;
    }

    /**
     * Obtiene el mensaje de email vacio.
     * 
     * @return String 
     */
    public String getEmptyEmailMsg() {
        return emptyEmailMsg;
    }

    /**
     * Obtiene el mensaje de email no valido.
     * 
     * @return String 
     */
    public String getNotValidEmailMsg() {
        return notValidEmailMsg;
    }

    /**
     * Obtiene el mensaje de carga para el loader.
     * 
     * @return String 
     */
    public String getLoaderSave() {
        return loaderSave;
    }

    /**
     * Obtiene el mensaje de espera para el loader.
     * 
     * @return String 
     */
    public String getLoaderWait() {
        return loaderWait;
    }
    
    /**
     * Constructor de clase.
     * 
     * @param request
     * @param response 
     */
    public SaveEmailFacebookContactJSPControler(HttpServletRequest request, HttpServletResponse response) throws InterDataBaseException, InterProcessException, InterEmailException {
        try {
            this.snsObject = SNSAngelGuardFBManager.getSessionInstance(request);
            this.request = request;
            this.response = response;
            this.nameAngelValue = null;
            
            // Obtenemos el identificador del angel
            this.uidAngel = this.request.getParameter("uidAngel");
            
            // Obtenemos la clave publica del usuario de la aplicacion
            this.uidPublicUser = this.request.getParameter("uidPublicUser");
        } catch (FileNotFoundException ex) {
            this.snsObject.getExceptionManager().initControlException(ex);
        } catch (IOException ex) {
            this.snsObject.getExceptionManager().initControlException(ex);
        }
    }

    @Override
    public void process() throws InterDataBaseException, InterProcessException, InterEmailException {
        try {
            // Obtener informacion del usuario de la aplicacion por su uidPublic
            JSONObject jsonUser = this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getJsonUserByUidPublic(snsObject, this.uidPublicUser);
            
            // Cargamos al usuario de la aplicacion
            this.snsObject.getUserSettingsDaoManager().loadUserConnected(jsonUser);
            
            // Obtenemos los recursos de idioma asociados al usuario de la aplicacion
            this.snsObject.getLocaleSettingsDaoManager().loadLocaleSettingsOffLine();
            
            // Obtenemos los mensajes de error
            String[] arrayWarnings = this.getSnsObject().getLocaleSettingsDaoManager().getLocaleSettingsDao().getWarnings().split(";");
            this.emptyEmailMsg = arrayWarnings[10];
            this.notValidEmailMsg = arrayWarnings[11];

            // Cargamos los mensajes de espera
            this.loaderSave = arrayWarnings[3];
            this.loaderWait = arrayWarnings[4];
            
            // Obtenemos la informacion del angel en base de datos
            JSONObject jsonAngelDB = new JSONObject(this.snsObject.getClient().settingsAngels_getAngelsByUid(String.class, this.uidAngel));
            
            if (jsonAngelDB.getJSONArray("settingsAngels") != null && jsonAngelDB.getJSONArray("settingsAngels").length() > 0) {
                this.jsonAngel = jsonAngelDB.getJSONArray("settingsAngels").getJSONObject(0);
                this.nameAngelValue = this.jsonAngel.getString("nameAngel");

                // Obtenemos el mensaje de autorizacion para el envio de notificaciones
                this.notificationMsg = this.snsObject.getEmailObject().getStrBodyMailConfirmation(this.uidPublicUser, this.uidAngel);

                if (!this.jsonAngel.toString().equals("{}")) {
                    if (this.jsonAngel.getString("confirmAngel").equals("1")) {
                        // Mensaje de Aviso ("El usuario ya est? confirmado")
                        response.sendRedirect(request.getContextPath() + "/informationMessage.jsp?par1=1&par2=" + this.uidPublicUser);
                    }
                } else {
                    // Mensaje de Error ("El angel indicado ha sido borrado por el usuario")
                    response.sendRedirect(request.getContextPath() + "/informationMessage.jsp?par1=2&par2=" + this.uidPublicUser);
                }
            } else {
                // Mensaje de Error ("El angel indicado ha sido borrado por el usuario")
                response.sendRedirect(request.getContextPath() + "/informationMessage.jsp?par1=2&par2=" + this.uidPublicUser);
            }
        } catch (JSONException | UnsupportedEncodingException ex) {
            this.snsObject.getExceptionManager().initControlException(ex);
        } catch (IOException ex) {
            this.snsObject.getExceptionManager().initControlException(ex);
        }
    }
}