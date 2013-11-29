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
import es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.entity.data.TypeOperationContact;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchProviderException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Realiza una operacion de guardado, modificacion o borrardo de un contacto tipificado 
 * como "Otros Contactos" dentro de la aplicacion.
 * 
 * @author josejavierblecuadepedro1
 */
public class DoOperationWithOtherContactJSPControler extends GenericJSPControler  {
    
    /** Logger class  */
    private static Logger logger = Logger.getLogger(DoOperationWithOtherContactJSPControler.class);
    
    private static final String TYPE_ANGEL = "O";
    
    /** Manager de la aplicacion */
    private SNSAngelGuardFBManager snsObject;
    
    /** Atributo request de la sesion */
    private HttpServletRequest request;
    
    /** Atributo response de la sesion */
    private HttpServletResponse response;
    
    /** Tipo de operacion a realizar con el contacto */
    private TypeOperationContact typeOperation;
    
    /** Identificador del contacto */
    private String idContact;
    
    /** Nombre del contacto que se almacenara como angel */
    private String nameContact;
    
    /** Email del contacto que se almacenara como angel */
    private String emailContact;

    /**
     * Constructor de clase.
     * 
     * @param request
     * @param response
     * @throws InterDataBaseException
     * @throws InterProcessException
     * @throws InterEmailException 
     */
    public DoOperationWithOtherContactJSPControler(HttpServletRequest request, HttpServletResponse response) throws InterDataBaseException, InterProcessException, InterEmailException {
        try {
            request.setCharacterEncoding("utf-8");
            
            this.snsObject = SNSAngelGuardFBManager.getSessionInstance(request);
            this.request = request;
            this.response = response;
            
            // Verificamos la sesion de la aplicacion
            this.snsObject.logSession(this.request, this.response);
            
            // Cargamos los datos de la operacion
            loadDetailOperation();
        } catch (FileNotFoundException ex) {
            this.snsObject.getExceptionManager().initControlException(ex);
        } catch (IOException | JSONException ex) {
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

    /**
     * Obtiene el objeto request de la peticion HTTP.
     * 
     * @return HttpServletRequest
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * Obtiene el objeto response de la peticion HTTP.
     * 
     * @return HttpServletResponse
     */
    public HttpServletResponse getResponse() {
        return response;
    }
    
    /**
     * Establece el tipo de operacion a realizar con el contacto.
     * 
     * @param typeOperationRequest
     * @return TypeOperationContact
     */
    private TypeOperationContact loadTypeOperation(String typeOperationRequest){
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - loadTypeOperation: Inicio loadTypeOperation...");
        TypeOperationContact typeCurrentOperation;
        
        switch(typeOperationRequest){
            case "0": typeCurrentOperation = TypeOperationContact.NEW_CONTACT;
                break;
            case "1": typeCurrentOperation = TypeOperationContact.UPDATE_CONTACT;
                break;
            case "2": typeCurrentOperation = TypeOperationContact.DELETE_CONTACT;
                break;
            default:
                typeCurrentOperation = TypeOperationContact.NEW_CONTACT;
                break;
        }
        
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - loadTypeOperation: Tipo de operacion obtenido: " + typeCurrentOperation);
        
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - loadTypeOperation: Fin loadTypeOperation!!");
        return typeCurrentOperation;
    }
    
    /**
     * Carga todos los datos necesarios para realizar la operacion con el contacto.
     */
    private void loadDetailOperation(){
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - loadDetailOperation: Inicio loadDetailOperation...");
        
        // Cargamos el tipo de operacion
        this.typeOperation = loadTypeOperation(request.getParameter("typeOperation"));
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - loadDetailOperation: Tipo de operacion: " + this.typeOperation);
        
        if(this.typeOperation != TypeOperationContact.NEW_CONTACT){
            this.idContact = this.request.getParameter("idContact");
            logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - loadDetailOperation: idContact: " + this.idContact);
        }
        
        this.nameContact = this.request.getParameter("nameContact");
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - loadDetailOperation: nameContact: " + this.nameContact);
        
        this.emailContact = this.request.getParameter("emailContact");
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - loadDetailOperation: emailContact: " + this.emailContact);
        
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - loadDetailOperation: Fin loadDetailOperation!!");
    }

    @Override
    public void process() throws InterDataBaseException, InterProcessException, InterEmailException {
        try {
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Inicio process...");
            int resultOperation = 0;
            
            switch(this.typeOperation){
                case NEW_CONTACT:    
                    // Obtenemos la informacion en formato JSON del angel a guardar
                    JSONObject jsonNewAngel = this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getNewAngelNotFacebook(this.nameContact, this.emailContact, TYPE_ANGEL);
                    
                    // Almacenamos el nuevo angel en la base de datos
                    jsonNewAngel = this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().putNewAngelFacebook(jsonNewAngel);

                    // Actualizo la coleccion de angeles del usuario
                    this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().setCollectionAngels(jsonNewAngel, 1, "");
                    
                    // Enviamos un mail de confirmacion al angel
                    this.snsObject.getEmailObject().sendMailConfirmationAngel(jsonNewAngel, this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUidPublic());
                    
                    // Establecemos el tipo de mensaje a mostrar en la vuelta
                    resultOperation = 6;
                    
                    break;
                case UPDATE_CONTACT:
                    // Obtenemos la informacion del angel en base de datos
                    JSONObject jsonAngelUpdateSettingsDB = new JSONObject(this.snsObject.getClient().settingsAngels_getAngelsByUid(String.class, this.idContact));
                    logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Datos de base de datos: " + jsonAngelUpdateSettingsDB.toString());
                    
                    // Obtenemos el angel del objeto JSON de consulta
                    JSONObject jsonUpdateAngel = jsonAngelUpdateSettingsDB.getJSONArray("settingsAngels").getJSONObject(0);
                    logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Datos del angel sacados del array: " + jsonUpdateAngel.toString());
                
                    // Introducimos en el angel la nueva informacion
                    jsonUpdateAngel.put("nameAngel", this.nameContact);
                    jsonUpdateAngel.put("idAngel", this.emailContact);
                    
                    // Actualizamos el objeto con los datos del angel adjuntandole todas sus relaciones con los filtros que tenia actualmente
                    jsonUpdateAngel = this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getAngelInformationWithFilters(jsonUpdateAngel);
                    
                    // Actualizamos en base de datos el angel con su informacion actualizada
                    try{
                        this.snsObject.getClient().settingsAngels_setAngelByUid(String.class, jsonUpdateAngel.getString("uidAngel"), jsonUpdateAngel);
                    } catch(UniformInterfaceException e){
                        int code = e.getResponse().getStatus();
                        
                        if(code >= 200 && code < 400){
                            logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Angel actualizado...");
                            
                            resultOperation = 6;
                        } else{
                            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Excepcion capturada UniformInterfaceException: " + e.getMessage());
                            
                            // Volvemos a la pagina de configuracion con mensaje de error
                            resultOperation = 8;
                        }
                    }
                    break;
                case DELETE_CONTACT:    
                    // Obtenemos la informacion del angel en base de datos
                    JSONObject jsonAngelDeleteSettingsDB = new JSONObject(this.snsObject.getClient().settingsAngels_getAngelsByUid(String.class, this.idContact));
                    logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Datos de base de datos: " + jsonAngelDeleteSettingsDB.toString());
                    
                    // Obtenemos el angel del objeto JSON de consulta
                    JSONObject jsonDeleteAngel = jsonAngelDeleteSettingsDB.getJSONArray("settingsAngels").getJSONObject(0);
                    logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Datos del angel sacados del array: " + jsonDeleteAngel.toString());
                
                    // Eliminamos las posibles relaciones con los filtros de la aplicacion del angel
                    this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().deleteAngelFiltersRelationship(jsonDeleteAngel);

                    // Eliminamos el angel de base de datos
                    this.snsObject.getClient().settingsAngels_delAngelByUid(jsonDeleteAngel.getString("uidAngel"));

                    if(jsonDeleteAngel.getString("confirmAngel").equals("1")){
                        this.snsObject.getEmailObject().sendMailDeleteAngel(jsonDeleteAngel, this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUidPublic());
                    }
                    
                    // Establecemos el tipo de mensaje a mostrar en la vuelta
                    resultOperation = 5;
                    
                    break;
            }
            
            // Volvemos a la pagina de configuracion
            response.sendRedirect(request.getContextPath() + "/settingsSNSAngelGuard.jsp?newConection=1&ok=" + resultOperation);
            
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Fin process!!");
            
        } catch (JSONException | IOException | NoSuchProviderException | MessagingException | UniformInterfaceException ex) {
            this.snsObject.getExceptionManager().initControlException(ex);
        } 
    }
    
}
