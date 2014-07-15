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
import static es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.entity.data.TypeOperationContact.NEW_CONTACT;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchProviderException;
import java.util.Iterator;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Realiza las operaciones de guardado y borrado de los angeles de google.
 * 
 * @author josejavierblecuadepedro1
 */
public class DoOperationWithGoogleContactsJSPControler extends GenericJSPControler  {

    /** Logger class  */
    private static Logger logger = Logger.getLogger(DoOperationWithGoogleContactsJSPControler.class);
    
    /** Tipo de angeles a tratar */
    private static final String TYPE_ANGEL = "G";
    
    /** Key para el acceso al nombre del angel */
    private static final String KEY_NAME_ANGEL_GOOGLE = "nameAngelGoogleSelected";
    
    /** Key para el acceso al email del angel */
    private static final String KEY_EMAIL_ANGEL_GOOGLE = "emailAngelGoogleSelected";
    
    /** Key para el acceso al id del angel */
    private static final String KEY_ID_ANGEL_GOOGLE_DEL = "idContactGoogleSelectedDel";
    
    /** Manager de la aplicacion */
    private SNSAngelGuardFBManager snsObject;
    
    /** Atributo request de la sesion */
    private HttpServletRequest request;
    
    /** Atributo response de la sesion */
    private HttpServletResponse response;
    
    /** Tipo de operacion a realizar con el contacto */
    private TypeOperationContact typeOperation;
    
    /** Array de objetos JSON que almacenara los angeles que van a ser procesados */
    private JSONArray jsonGoogleAngels;

    /**
     * Obtiene el objeto manager de la aplicacion.
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
     * @throws InterDataBaseException
     * @throws InterProcessException
     * @throws InterEmailException 
     */
    public DoOperationWithGoogleContactsJSPControler(HttpServletRequest request, HttpServletResponse response) throws InterDataBaseException, InterProcessException, InterEmailException {
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
     * Establece el tipo de operacion a realizar con el/los contactos.
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
     * Obtiene los datos necesarios para realizar la operacion obtenidos del objeto request de la peticion.
     * 
     * @throws JSONException 
     */
    private void loadDetailOperation() throws JSONException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - loadDetailOperation: Inicio loadDetailOperation...");
        
        // Cargamos el tipo de operacion
        this.typeOperation = loadTypeOperation(request.getParameter("typeOperation"));
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - loadDetailOperation: Tipo de operacion: " + this.typeOperation);
        
        // Cargamos todos los angeles en un array
        loadArrayAngels();
        
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - loadDetailOperation: Fin loadDetailOperation!!");
    }
    
    /**
     * Carga en el atributo de clase los angeles seleccionados para realizar esta operacion.
     * 
     * @throws JSONException 
     */
    private void loadArrayAngels() throws JSONException{
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - loadArrayAngels: Inicio loadArrayAngels...");
        String[] arraySelectedAngels;
        
        if(this.request.getParameter("hdAngelsGoogleSelected") != null){
            this.jsonGoogleAngels = new JSONArray();
            arraySelectedAngels = this.request.getParameter("hdAngelsGoogleSelected").split(";");
            
            for(String angel: arraySelectedAngels){
                // Obtenemos los angeles pasados por parametro y los parseamos a JSON
                JSONObject jsonAngel = new JSONObject(angel);
                
                // Introducimos el elemento en el array de angeles
                this.jsonGoogleAngels.put(jsonAngel);
            }
        }
        
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - loadArrayAngels: Fin loadArrayAngels!!");
    }
    
    @Override
    public void process() throws InterDataBaseException, InterProcessException, InterEmailException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Inicio process...");
        int resultOperation = 0;
        
        try {
            switch (this.typeOperation) {
                case NEW_CONTACT:

                    for (int i = 0; i < this.jsonGoogleAngels.length(); i++) {
                        
                        // Obtenemos el objeto actual
                        JSONObject jsonAngel = this.jsonGoogleAngels.getJSONObject(i);
                        
                        // Obtenemos la informacion en formato JSON del angel a guardar
                        JSONObject jsonNewAngel = this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getNewAngelNotFacebook(jsonAngel.getString(KEY_NAME_ANGEL_GOOGLE), 
                                jsonAngel.getString(KEY_EMAIL_ANGEL_GOOGLE), 
                                TYPE_ANGEL);

                        // Almacenamos el nuevo angel en la base de datos
                        jsonNewAngel = this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().putNewAngelFacebook(jsonNewAngel);

                        // Actualizo la coleccion de angeles del usuario
                        this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().setCollectionAngels(jsonNewAngel);
                    }
                    
                    // Establecemos el tipo de mensaje a mostrar en la vuelta
                    resultOperation = 6;
                        
                    break;
                case DELETE_CONTACT:

                    Iterator<String> itKeyFilters = this.snsObject.getConfigurationManager().getListActiveFilters().iterator();
                    String keyFilter;
                    
                    // Borramos el angel para cada filtro
                    while (itKeyFilters.hasNext()) {
                        keyFilter = itKeyFilters.next();
                        
                        this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFilterDaoMap().get(keyFilter).setAngels(request.getParameter("hdLstAngels" + keyFilter));
                    }
                    
                    for (int i = 0; i < this.jsonGoogleAngels.length(); i++) {
                        // Obtenemos el objeto actual
                        JSONObject jsonAngelToDelete = this.jsonGoogleAngels.getJSONObject(i);

                        // Borramos el angel de todos los filtros en ejecucion
                        //this.snsObject.getGenericFilter().deleteAngelForFilter(jsonAngelToDelete.getString(KEY_EMAIL_ANGEL_GOOGLE_DEL));
                        
                        // Obtenemos la informacion del angel en base de datos
                        JSONObject jsonAngelDeleteSettingsDB = new JSONObject(this.snsObject.getClient().settingsAngels_getAngelsByUid(String.class, jsonAngelToDelete.getString(KEY_ID_ANGEL_GOOGLE_DEL)));
                        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Datos de base de datos: " + jsonAngelDeleteSettingsDB.toString());

                        // Obtenemos el angel del objeto JSON de consulta
                        JSONObject jsonDeleteAngel = jsonAngelDeleteSettingsDB.getJSONArray("settingsAngels").getJSONObject(0);
                        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Datos del angel sacados del array: " + jsonDeleteAngel.toString());

                        // Eliminamos las posibles relaciones con los filtros de la aplicacion del angel
                        this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().deleteAngelFiltersRelationship(jsonDeleteAngel);

                        // Eliminamos el angel de base de datos
                        this.snsObject.getClient().settingsAngels_delAngelByUid(jsonDeleteAngel.getString("uidAngel"));
                        
                        if (jsonDeleteAngel.getString("confirmAngel").equals("1")) {
                            this.snsObject.getEmailObject().sendMailDeleteAngel(jsonDeleteAngel, this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUidPublic());
                        }
                    }
                    
                    // Establecemos el tipo de mensaje a mostrar en la vuelta
                    resultOperation = 5;
                    break;
            }

            // Volvemos a la pagina de configuracion
            response.sendRedirect(request.getContextPath() + "/settingsSNSAngelGuard.jsp?newConection=1&modal=1&ok=" + resultOperation);
            
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Fin process!!");
        } catch (JSONException | IOException | NoSuchProviderException | MessagingException | UniformInterfaceException ex) {
            this.snsObject.getExceptionManager().initControlException(ex);
        } 
    } 
}
