/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.snsangelguardfb.sources.dao.entity;

import bsh.ParseException;
import com.sun.jersey.api.client.UniformInterfaceException;
import es.uah.cc.ie.snsangelguardfb.exception.InterDataBaseException;
import es.uah.cc.ie.snsangelguardfb.exception.InterEmailException;
import es.uah.cc.ie.snsangelguardfb.exception.InterProcessException;
import es.uah.cc.ie.snsangelguardfb.SNSAngelGuardFBManager;
import es.uah.cc.ie.snsangelguardfb.facebookclient.data.FriendsFacebook;
import es.uah.cc.ie.snsangelguardfb.sources.dao.UserSettingsDaoManager;
import es.uah.cc.ie.snsangelguardfb.sources.utilities.JSONUtilities;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.openide.util.Exceptions;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.UrlBase64;
import org.codehaus.jettison.json.JSONArray;

/**
 * Gestiona toda la informacion de la tabla userSettings para la configuracion
 * de la aplicacion para un determinado usuario.
 *
 * @author tote
 */
public class UserSettingsDAO {
    
    /** Valor por defecto para el activado del filtro */
    private final static String FILTER_DEFAULT_ACTIVE_VALUE = "0";
    
    /** Valor por defecto para los angeles de un filtro */
    private final static String FILTER_DEFAULT_ANGELS_VALUE = "";
    
    /** Valor por defecto para la frecuencia de un filtro */
    private final static String FILTER_DEFAULT_FREC_VALUE = "3";
   
    /** Clave unica privada para la generacion del cifrado de la clave publica de acceso */
    private static final String CF_64 = "JJMAGICFBNANYANG";
    
    /** Imagen estandar para contactos no pertenecientes a Facebook */
    private static final String IMG_STANDAR_ANGEL = "../SNSAngelGuardFB/resources/perfilStandar.gif";
    
    /** Logger Class */
    private static Logger logger = Logger.getLogger(UserSettingsDAO.class);

    /** Clase Manager de la aplicacion */
    private SNSAngelGuardFBManager snsObject;
    
    /** Manager del objeto UserSettingsDAO */ 
    private UserSettingsDaoManager manager;
    
    /** Identificador ?nico en base de datos */
    private String uid;
    
    /** Nombre del usuario */
    private String userName;
    
    /** Email del usuario */
    private String userEmail;
    
    /** Indicador de aceptacion del acuerdo legal */
    private Boolean legalAccepted;
    
    /** Fecha que muestra el ultimo acceso del usuario a la aplicacion */
    private Date lastCheck;
    
    /** Identificador publico cifrado de acceso a la aplicacion */
    private String uidPublic;
    
    /** Indicador de activacion de la aplicacion */
    private Boolean appActivated;
    
    /** Token de acceso a Facebook */
    private String userSession;
    
    /** Identificador del idioma en base de datos. Relacionado con la tabla locale\_settings */
    private String localeSettings;
    
    /** Fecha del ultimo backup de informacion offline */
    private Date backupCheck;
    
    /** Mapa de filtros configurados */
    private Map<String, UserSettings_SettingsFilterDAO> filterDaoMap;
    
    /** Campo que almacena los angeles seleccionados en la aplicacion separados por el caracter ; */
    private String angelsSelected = "";

    /**
     * Constructor sin parametros.
     */
    public UserSettingsDAO(SNSAngelGuardFBManager snsObject) {
        this.snsObject = snsObject;
        this.manager = this.snsObject.getUserSettingsDaoManager();
        this.filterDaoMap = new HashMap();
    }

    /**
     * Constructor de clase. Inicializara el identificador del usuario, su nombre, su email y el objeto manager de la aplicacion.
     * 
     * @param uid
     * @param userName
     * @param userEmail
     * @param snsObject 
     */
    public UserSettingsDAO(String uid, String userName, String userEmail, SNSAngelGuardFBManager snsObject) {
        this.uid = uid;
        this.userName = userName;
        this.userEmail = userEmail;
        this.snsObject = snsObject;
        this.manager = this.snsObject.getUserSettingsDaoManager();
        this.filterDaoMap = new HashMap();
    }

    /**
     * Constructor de clase. Recibir? todos los atributos por par?metro y cargar?, en base a estos, todos los filtros del usuario mediante el metodo loadFullFuntionalityFilter.
     * 
     * @param manager
     * @param uid
     * @param userName
     * @param userEmail
     * @param legalAccepted
     * @param lastCheck
     * @param uidPublic
     * @param appActivated
     * @param userSession
     * @param backupCheck
     * @param snsObject 
     */
    public UserSettingsDAO(UserSettingsDaoManager manager, String uid, String userName, String userEmail, Boolean legalAccepted, Date lastCheck, String uidPublic, Boolean appActivated, String userSession, Date backupCheck, SNSAngelGuardFBManager snsObject) throws InterDataBaseException, InterProcessException, InterEmailException {
        this.manager = manager;
        this.uid = uid;
        this.userName = userName;
        this.userEmail = userEmail;
        this.legalAccepted = legalAccepted;
        this.lastCheck = lastCheck;
        this.uidPublic = uidPublic;
        this.appActivated = appActivated;
        this.userSession = userSession;
        this.backupCheck = backupCheck;
        this.snsObject = snsObject;

        // Inicializamos los filtros
        initFiltersMap();
        
        // Cargamos todos los filtros con la informacion de la base de datos
        loadFullFuntionalityFilter();
    }

    /**
     * @return the uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * @param uid the uid to set
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the userEmail
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * @param userEmail the userEmail to set
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * @return the legalAccepted
     */
    public Boolean isLegalAccepted() {
        return legalAccepted;
    }

    /**
     * @param legalAccepted the legalAccepted to set
     */
    public void setLegalAccepted(Boolean legalAccepted) {
        this.legalAccepted = legalAccepted;
    }

    /**
     * @return the lastCheck
     */
    public Date getLastCheck() {
        return lastCheck;
    }

    /**
     * @param lastCheck the lastCheck to set
     */
    public void setLastCheck(Date lastCheck) {
        this.lastCheck = lastCheck;
    }

    /**
     * @return the uidPublicLocal
     */
    public String getUidPublic() {
        return uidPublic;
    }

    /**
     * @param uidPublicLocal the uidPublicLocal to set
     */
    public void setUidPublic(String uidPublic) {
        this.uidPublic = uidPublic;
    }

    /**
     * @return the appActivated
     */
    public Boolean isAppActivated() {
        return appActivated;
    }

    /**
     * @param appActivated the appActivated to set
     */
    public void setAppActivated(Boolean appActivated) {
        this.appActivated = appActivated;
    }

    /**
     * @return the userSession
     */
    public String getUserSession() {
        return userSession;
    }

    /**
     * @param userSession the userSession to set
     */
    public void setUserSession(String userSession) {
        this.userSession = userSession;
    }

    /**
     * @return the localeSettings
     */
    public String getLocaleSettings() {
        return localeSettings;
    }

    /**
     * @param localeSettings the localeSettings to set
     */
    public void setLocaleSettings(String localeSettings) {
        this.localeSettings = localeSettings;
    }

    /**
     * @return the backupCheck
     */
    public Date getBackupCheck() {
        return backupCheck;
    }

    /**
     * @param backupCheck the backupCheck to set
     */
    public void setBackupCheck(Date backupCheck) {
        this.backupCheck = backupCheck;
    }

    /**
     * @return the snsObject
     */
    public SNSAngelGuardFBManager getSnsObject() {
        return snsObject;
    }

    /**
     * @param snsObject the snsObject to set
     */
    public void setSnsObject(SNSAngelGuardFBManager snsObject) {
        this.snsObject = snsObject;
    }

    /**
     * Obtiene el identificador de idioma de la tabla locale\_settings dependiendo del idioma del perfil de Facebook del usuario. Pordr? lanzar excepciones del tipo JSONException.
     *
     * @param locale Codigo de idioma de Facebook.
     * @return Identificador del indice de la base de datos SNS para el idioma.
     * @throws JSONException
     */
    public String getIdLocale(String locale) throws JSONException {
        logger.info(this.uid + " - getIdLocale: Inicio getIdLocale...");
        if (locale.substring(0, 2).equals("es")) {
            this.setLocaleSettings("00000002");
            logger.info(this.uid + " - getIdLocale: Fin getIdLocale...");
            return "00000002";
        } else {
            this.setLocaleSettings("00000001");
            logger.info(this.uid + " - getIdLocale: Fin getIdLocale...");
            return "00000001";
        }

    }

    /**
     * Obtiene en un objeto JSON todos los recursos de idioma para un perfil de
     * Facebook.
     *
     * @param codLocale Identificador de base de datos del idioma concreto.
     * @return JSONObject
     */
    public JSONObject getJSONLocale(String codLocale) {
        logger.info(this.uid + " - getJSONLocale: Inicio getJSONLocale...");
        String locale = this.snsObject.getClient().localeSettings_getLocaleSettingsByUid(String.class, codLocale);
        JSONObject jsonLocale = null;

        if (!locale.equals("{}") && !locale.contains("error_code")) {
            try {
                jsonLocale = new JSONObject(locale);
                jsonLocale.put("userSettingsCollection", "");
                return jsonLocale;
            } catch (JSONException ex) {
                Exceptions.printStackTrace(ex);
                return jsonLocale;
            }
        }
        logger.info(this.uid + " - getJSONLocale: Fin getJSONLocale...");
        return jsonLocale;
    }

    /**
     * @return the angelsSelected
     */
    public String getAngelsSelected() {
        return angelsSelected;
    }

    /**
     * @param angelsSelected the angelsSelected to set
     */
    public void setAngelsSelected(String angelsSelected) {
        this.angelsSelected = angelsSelected;
    }

    /**
     * Mapa de filtros.
     * 
     * @return Map<String, UserSettings_SettingsFilterDAO> 
     */
    public Map<String, UserSettings_SettingsFilterDAO> getFilterDaoMap() {
        return filterDaoMap;
    }

    /**
     * Obtiene una cadena de texto cifrada en Base64 utilizando el algoritmo AES a partir de la cadena de entrada. Podr? lanzar excepciones del tipo org.bouncycastle.crypto.DataLengthException,
     * java.lang.IllegalStateException o org.bouncycastle.crypto.InvalidCipherTextException.
     *
     * @param textoEnClaro Texto en claro que deseamos cifrar
     * @param clave Clave a utilizar en el proceso de cifrado
     * @return Cadena con el texto cifrado, codificada en Base64
     * @throws org.bouncycastle.crypto.DataLengthException
     * @throws java.lang.IllegalStateException
     * @throws org.bouncycastle.crypto.InvalidCipherTextException
     */
    public static String cifrar(String textoEnClaro)
            throws DataLengthException,
            IllegalStateException,
            InvalidCipherTextException {
        // Obtenemos los valores binarios del texto en claro y la clave
        byte[] textoEnClaroB = textoEnClaro.getBytes();
        byte[] claveB = CF_64.getBytes();
        // Instanciamos un motor de cifrado para el algoritmo AES
        BlockCipher engine = new AESEngine();
        // Creamos el cifrador con padding y en modo CBC
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(engine));
        // Inicializamos el cifrador en modo cifrado
        cipher.init(true, new KeyParameter(claveB));
        // Instanciamos el vector de bytes para almacenar el criptograma
        byte[] criptograma = new byte[cipher.getOutputSize(textoEnClaroB.length)];
        // Procesa todos los bytes del texto en claro
        int temp = cipher.processBytes(textoEnClaroB, 0, textoEnClaroB.length, criptograma, 0);
        // Finaliza el proceso de cifrado
        cipher.doFinal(criptograma, temp);
        // Codifica el criptograma en Base64
        String criptogramaB64 = new String(UrlBase64.encode(criptograma));
        return criptogramaB64;
    }

    /**
     * Descifra una cadena de texto cifrada en Base64 utilizando el algoritmo AES. Podr? lanzar excepciones del tipo org.bouncycastle.crypto.DataLengthException,
     * java.lang.IllegalStateException o org.bouncycastle.crypto.InvalidCipherTextException.
     * 
     * @param criptogramaB64 Criptograma con el texto cifrado, codificado en Base64
     * @param clave Clave a utilizar en el proceso de descifrado
     * @return Texto en claro resultante del proceso de descifrado
     * @throws org.bouncycastle.crypto.DataLengthException
     * @throws java.lang.IllegalStateException
     * @throws org.bouncycastle.crypto.InvalidCipherTextException
     */
    public static String descifrar(String criptogramaB64)
            throws DataLengthException,
            IllegalStateException,
            InvalidCipherTextException {
        // Obtenemos los valores binarios del criptograma y la clave
        byte[] criptogramaB = UrlBase64.decode(criptogramaB64.getBytes());
        byte[] claveB = CF_64.getBytes();
        // Instanciamos un motor de cifrado para el algoritmo AES
        BlockCipher engine = new AESEngine();
        // Creamos el cifrador con padding y en modo CBC
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(engine));
        // Inicializamos el cifrador en modo cifrado
        cipher.init(false, new KeyParameter(claveB));
        // Instanciamos el vector de bytes para almacenar el texto en claro
        byte[] textoEnClaro = new byte[cipher.getOutputSize(criptogramaB.length)];
        // Procesa todos los bytes del criptograma
        int temp = cipher.processBytes(criptogramaB, 0, criptogramaB.length, textoEnClaro, 0);
        // Finaliza el proceso de descifrado
        cipher.doFinal(textoEnClaro, temp);
        return new String(textoEnClaro);
    }
    
    /**
     * Inicializa y enlaza en base de datos, una instancia para cada filtro
     * definido al usuario de la aplicacion.
     *
     * @throws JSONException
     */
    private void initNewFilters() throws JSONException, InterDataBaseException, InterProcessException, InterEmailException{
        logger.info(this.uid + " - initNewFilters: Fin initNewFilters...");
        Iterator<String> itKeyFilters = this.snsObject.getConfigurationManager().getListActiveFilters().iterator();
        String keyFilter;

        while (itKeyFilters.hasNext()) {
            keyFilter = itKeyFilters.next();
            
            // Introducimos el filtro en el mapa si no existe
            if(this.filterDaoMap.get(keyFilter) == null){
                this.filterDaoMap.put(keyFilter, new UserSettings_SettingsFilterDAO(this.manager, keyFilter));
                this.filterDaoMap.get(keyFilter).setActive(FILTER_DEFAULT_ACTIVE_VALUE);
                this.filterDaoMap.get(keyFilter).setFrec(FILTER_DEFAULT_FREC_VALUE);
            }

            try{
                this.filterDaoMap.get(keyFilter).saveNewFilter();
            } catch(UniformInterfaceException e) {
                logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getNewInstanceUS: Excepcion capturada Exception: " + e.getMessage());
                this.snsObject.getExceptionManager().initControlException(e);  
            }
        }

        logger.info(this.uid + " - initNewFilters: Fin initNewFilters...");
    }

    /**
     * Crea un objeto JSONObject a partir de los atributos de la clase y lo persiste en base de datos mediante el servicio web userSettings\_setNewEntityUserSettings, despu?s lo retorna como resultado.
     * Puede lanzar excepciones de los tipos JSONException, DataLengthException, IllegalStateException, InvalidCipherTextException, UnsupportedEncodingException, 
     * UniformInterfaceException, IOException, NoSuchProviderException, MessagingException, InterDataBaseException, InterProcessException o InterEmailException.
     * 
     * @param request
     * @param response
     * @return
     * @throws JSONException
     * @throws DataLengthException
     * @throws IllegalStateException
     * @throws InvalidCipherTextException
     * @throws UnsupportedEncodingException
     * @throws UniformInterfaceException
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws MessagingException
     * @throws InterDataBaseException
     * @throws InterProcessException
     * @throws InterEmailException 
     */
    public JSONObject getNewInstanceUS(HttpServletRequest request, HttpServletResponse response) throws JSONException, DataLengthException, IllegalStateException, InvalidCipherTextException, UnsupportedEncodingException, UniformInterfaceException, IOException, NoSuchProviderException, MessagingException, InterDataBaseException, InterProcessException, InterEmailException {
        logger.info(this.uid + " - getNewInstanceUS: Inicio getNewInstanceUS...");
        Long uidLong = (new Double(this.getUid())).longValue();
        String uidPublicLocal = "";
        HttpSession sesion = request.getSession(false);
        this.setLegalAccepted(true);

        JSONObject newInstance = new JSONObject();
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getNewInstanceUS: UID convertida a Long: " + uidLong);
        newInstance.put("uid", uidLong);
        newInstance.put("userName", this.getUserName());
        newInstance.put("userEmail", this.getUserEmail());
        if (this.isLegalAccepted()) {
            newInstance.put("legalAccepted", "1");
        } else {
            newInstance.put("legalAccepted", "0");
        }
        newInstance.put("lastCheck", "");
        newInstance.put("backupCheck", "");
        uidPublicLocal = UserSettingsDAO.cifrar(uidLong.toString());
        this.setUidPublic(uidPublicLocal);
        newInstance.put("uidPublic", this.getUidPublic());
        if (this.isAppActivated()) {
            newInstance.put("appActivated", "1");
        } else {
            newInstance.put("appActivated", "0");
        }
        newInstance.put("userSession", this.getUserSession());
        JSONObject jsonLocale = getJSONLocale(this.getLocaleSettings());
        JSONObject codeLocale = new JSONObject();
        
        codeLocale.put("uri", jsonLocale.get("uri"));
        newInstance.put("localeSettings", codeLocale);
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getNewInstanceUS: Nueva objeto a base de datos: " + newInstance.toString());
        try {
            this.uid = this.snsObject.getClient().userSettings_setNewEntityUserSettings(String.class, newInstance);
            logger.info(this.uid + " - getNewInstanceUS: Inicializamos en base de datos los filtros...");
            initNewFilters();
            logger.info(this.uid + " - getNewInstanceUS: Filtros correctamente inicializados!!");
            logger.info(this.uid + " - getNewInstanceUS: Fin getNewInstanceUS...");
        } catch (UniformInterfaceException | JSONException e) {
             logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getNewInstanceUS: Excepcion capturada Exception: " + e.getMessage());
            this.snsObject.getExceptionManager().initControlException(e);         
        }
        
        return newInstance;
    }

    /**
     * Devuelve un array de un String.
     *
     * @param activeFilter
     * @return String[]
     */
    public String[] getArrayActiveFilter(String activeFilter){
        return activeFilter.split(";");
    }

    /**
     * Persiste en base de datos los angeles definidos por un usuario. Puede lanzar excepciones del tipo throws JSONException, NoSuchProviderException, MessagingException, UniformInterfaceException o IOException.
     * 
     * @param activeFilter
     * @param newConnection
     * @throws JSONException
     * @throws NoSuchProviderException
     * @throws MessagingException
     * @throws UniformInterfaceException
     * @throws IOException 
     */
    public void setAngelsUserSettings(String activeFilter, boolean newConnection) throws JSONException, NoSuchProviderException, MessagingException, UniformInterfaceException, IOException, InterDataBaseException, InterProcessException, InterEmailException {
        logger.info(this.uid + " - setAngelsUserSettings: Inicio setAngelsUserSettings...");
      
        logger.info(this.uid + " - setAngelsUserSettings: Borrando las relaciones del angel...");
        this.deleteAngelsRelationship();
        logger.info(this.uid + " - setAngelsUserSettings: Relaciones del angel correctamente eliminadas...");
        
        logger.info(this.uid + " - setAngelsUserSettings: Estableciendo nuevas relaciones para el angel...");
        this.getNewAngels();
        logger.info(this.uid + " - setAngelsUserSettings: Nuevas relaciones del angel correctamente establecidas!!");

        // Actualizamos los filtros
        logger.info(this.uid + " - setAngelsUserSettings: Actualizando los filtros del usuario...");
        this.updateFilters();
        logger.info(this.uid + " - setAngelsUserSettings: Los filtros del usuario han sido actualizados!!");
        logger.info(this.uid + " - setAngelsUserSettings: Fin setAngelsUserSettings...");
    }

    /**
     * Actualiza la informacion de un determinado filtro. Puede lanzar excepciones del tipo
     * JSONException, UnsupportedEncodingException, UniformInterfaceException, IOException, NoSuchProviderException o MessagingException.
     *
     * @param desFilter
     * @param activeFilter
     * @throws JSONException
     * @throws UnsupportedEncodingException
     * @throws UniformInterfaceException
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws MessagingException
     */
    @Deprecated
    public void setAngelsUserSettingsByFilter(String desFilter, String activeFilter) throws JSONException, UnsupportedEncodingException, UniformInterfaceException, IOException, NoSuchProviderException, MessagingException, InterDataBaseException, InterProcessException, InterEmailException {
        logger.info(this.uid + " - setAngelsUserSettingsByFilter: Inicio setAngelsUserSettingsByFilter...");
        //this.putNewAngels(false);
        this.deleteAngelsRelationship();
        //this.deleteOlderAngels();
        this.getNewAngels();
        this.updateFilters();
        logger.info(this.uid + " - setAngelsUserSettingsByFilter: Fin setAngelsUserSettingsByFilter...");
    }

     public JSONObject getJsonAngelWithUserSettingsRelationship(JSONObject jsonAngel) throws JSONException{
         logger.info(this.uid + " - getJsonAngelWithFilterRelationship: Inicio getJsonAngelWithFilterRelationship...");
         JSONObject jsonCollection;
         JSONArray aux;
         JSONObject jsonUri;
         Long uidLong = (new Double(this.getUid())).longValue();

         try {
             jsonCollection = jsonAngel.getJSONObject("userSettingsCollection");
             aux = jsonCollection.getJSONArray("userSettings");
             jsonUri = new JSONObject();

             jsonUri.put("uri", this.snsObject.getConfigurationManager().getConfigHostApplication() + 
                     "SNSdataBaseIntegratorServer/resources/settingsAngelss/" + jsonAngel.getString("uidAngel") + 
                     "/userSettingsCollection/" + uidLong + "/");
             aux.put(jsonUri);
             jsonCollection.put("userSettings", aux);

             jsonAngel.put("userSettingsCollection", jsonCollection);
         } catch (JSONException e) {
             // Si no existen mas angeles relacionados.
             jsonUri = new JSONObject();
             jsonUri.put("uri", this.snsObject.getConfigurationManager().getConfigHostApplication() + 
                     "SNSdataBaseIntegratorServer/resources/settingsAngelss/" + jsonAngel.getString("uidAngel") + 
                     "/userSettingsCollection/" + uidLong + "/");

             JSONObject jsonUriUserSettings = new JSONObject();
             jsonUriUserSettings.put("userSettings", jsonUri);

             jsonAngel.put("userSettingsCollection", jsonUriUserSettings);
         }


         return jsonAngel;
    }
    
    /**
     * Actualiza los angeles de un usuario. Puede lanzar excepciones del tipo JSONException.
     *
     * @param jsonAngel
     * @param modo
     * @param des
     * @throws JSONException
     */
    public void setCollectionAngels(JSONObject jsonAngel) throws JSONException {
        logger.info(this.uid + " - setCollectionAngels: Inicio setCollectionAngels...");
        jsonAngel = getJsonAngelWithUserSettingsRelationship(jsonAngel);
        
        try{
            this.snsObject.getClient().userSettings_setNewAngelsCollectionByUid(String.class, jsonAngel.getString("uidAngel"), jsonAngel);
        } catch(UniformInterfaceException ex){
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - setCollectionAngels: Excepcion capturada UniformInterfaceException: " + ex.getMessage());
        }    
        logger.info(this.uid + " - setCollectionAngels: Fin setCollectionAngels...");
    }

    /**
     * Obtiene los nuevos angeles para un determinado filtro. Puede lanzar excepciones del tipo JSONException.
     *
     * @throws JSONException
     */
    public void getNewAngels() throws JSONException {
        logger.info(this.uid + " - getNewAngels: Inicio getNewAngels...");
        String respuesta = "";
        JSONObject jsonRespuesta = null;
        JSONObject jsonAngel = null;
        JSONArray jsonArrayAngels = null;
        Long uidLong = (new Double(this.getUid())).longValue();
        respuesta = this.snsObject.getClient().settingsAngels_getAngelsByPropUid(String.class, "\"" + uidLong + "\"");
        jsonRespuesta = new JSONObject(respuesta);
        jsonArrayAngels = this.snsObject.getJsonUtilities().getJSONArray(jsonRespuesta.getString("settingsAngels"));

        for (int i = 0; i < jsonArrayAngels.length(); i++) {
            jsonAngel = jsonArrayAngels.getJSONObject(i);
            setCollectionAngels(jsonAngel);
        }
        logger.info(this.uid + " - getNewAngels: Fin getNewAngels...");
    }

    /**
     * Elimina las relaciones existentes entre un angel eliminado de la configuracion
     * del usuario y los diferentes filtros existentes. Puede lanzar excepciones del tipo JSONException.
     *
     * @throws JSONException
     */
    public void deleteAngelsRelationship() throws JSONException {
        logger.info(this.uid + " - deleteAngelsRelationship: Inicio deleteAngelsRelationship...");
        
        // Formateamos el identificador del usuario actual de la aplicacion
        Long uidLong = (new Double(this.getUid())).longValue();
        
        // Realizamos la consulta en base de datos para obtener los angeles para un determinado usuario
        String respuesta = this.snsObject.getClient().settingsAngels_getAngelsByPropUid(String.class, "\"" + uidLong + "\"");
        JSONObject jsonRespuesta = new JSONObject(respuesta);
        
        // Obtenemos los angeles definidos para el usuario en formato JSONArray
        JSONArray jsonArrayAngels = this.snsObject.getJsonUtilities().getJSONArray(jsonRespuesta.getString("settingsAngels"));

        // Por cada angel definido, eliminamos sus relaciones
        for (int i = 0; i < jsonArrayAngels.length(); i++) {
            JSONObject jsonAngel = jsonArrayAngels.getJSONObject(i);

            // Eliminamos las relaciones del angel
            deleteAngelFiltersRelationship(jsonAngel);
        }
        logger.info(this.uid + " - deleteAngelsRelationship: Fin deleteAngelsRelationship...");
    }

    /**
     * Elimina las relaciones de un angel con respecto a los filtros de la aplicacion.
     * 
     * @throws JSONException 
     */
    public void deleteAngelFiltersRelationship(JSONObject jsonAngel) throws JSONException {
        logger.info(this.uid + " - deleteAngelFiltersRelationship: Inicio deleteAngelFiltersRelationship...");

        if (!jsonAngel.getString("uidAngel").equals("")) {
            
            // Eliminamos la relacion entre el angel y los filtros
            Iterator<String> itKeysFilter = this.snsObject.getConfigurationManager().getListActiveFilters().iterator();
            
            while(itKeysFilter.hasNext()){
                jsonAngel = this.filterDaoMap.get(itKeysFilter.next()).deleteAngelFromFilterAngelCollection(jsonAngel);
            }
            
            // Eliminamos la relacion entre el angel y el usuario de la aplicacion
            jsonAngel = deleteAngelFromUserSettingsAngelCollection(jsonAngel);

            try {
                logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - deleteAngelFiltersRelationship: Relacion eliminada: " + jsonAngel.toString());
                this.snsObject.getClient().userSettings_setNewAngelsCollectionByUid(String.class, jsonAngel.getString("uidAngel"), jsonAngel);
            } catch (JSONException | UniformInterfaceException e) {
                logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - deleteAngelFiltersRelationship: Excepcion capturada Exception: " + e.getMessage());
            }

        }
        logger.info(this.uid + " - deleteAngelFiltersRelationship: Fin deleteAngelFiltersRelationship...");
    }
    
    /**
     * Borra del objeto angel la relacion que le unia a un usuario de la aplicacion.
     *
     * @param jsonAngel JSONObject con los datos del angel
     * @return JSONObject del angel con la relacion con el usuario de la aplicacion eliminada
     * @throws JSONException
     */
    public JSONObject deleteAngelFromUserSettingsAngelCollection(JSONObject jsonAngel) throws JSONException {
        logger.info(this.uid + " - deleteAngelFromUserSettingsAngelCollection: Inicio deleteAngelFromUserSettingsAngelCollection...");

        JSONObject jsonUri = new JSONObject();
        jsonUri.put("uri", this.snsObject.getConfigurationManager().getConfigHostApplication() + 
                "SNSdataBaseIntegratorServer/resources/settingsAngelss/" + jsonAngel.getString("uidAngel") + 
                "/userSettingsCollection/");
        
        jsonAngel.put("userSettingsCollection", jsonUri);

        logger.info(this.uid + " - deleteAngelFromUserSettingsAngelCollection: Fin deleteAngelFromUserSettingsAngelCollection...");
        return jsonAngel;
    }

    /**
     * Inicializa como no activo los angeles seleccionados nuevos en base de
     * datos. Puede lanzar excepciones del tipo JSONException.
     *
     * @throws JSONException
     */
    public void setNotActiveAngels() throws JSONException {
        logger.info(this.uid + " - setNotActiveAngels: Inicio setNotActiveAngels...");
        String respuesta = "";
        JSONObject jsonRespuesta = null;
        JSONObject jsonAngel = null;
        JSONArray jsonArrayAngels = null;
        Long uidLong = (new Double(this.getUid())).longValue();
        respuesta = this.snsObject.getClient().settingsAngels_getAngelsByPropUid(String.class, "\"" + uidLong + "\"");
        jsonRespuesta = new JSONObject(respuesta);
        jsonArrayAngels = snsObject.getJsonUtilities().getJSONArray(jsonRespuesta.getString("settingsAngels"));

        for (int i = 0; i < jsonArrayAngels.length(); i++) {
            jsonAngel = jsonArrayAngels.getJSONObject(i);
            if (!isActiveAngel(jsonAngel)) {
                jsonAngel.put("acceptAngel", "0");
                try {
                    this.snsObject.getClient().userSettings_setNewAngelsCollectionByUid(String.class, jsonAngel.getString("uidAngel"), jsonAngel);
                } catch (Exception e) {
                    logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - deleteAngelsRelationship: Excepcion capturada Exception: " + e.getMessage());
                }
            }
        }
        logger.info(this.uid + " - setNotActiveAngels: Fin setNotActiveAngels...");
    }

    /**
     * Comprueba si un angel esta activo para empezar a enviarle informes. Si est? activo retornar? el valor true, y false en cualquier otro caso. Puede lanzar excepciones del tipo JSONException.
     * 
     * @param jsonAngel
     * @return
     * @throws JSONException
     */
    public boolean isActiveAngel(JSONObject jsonAngel) throws JSONException {
        logger.info(this.uid + " - isActiveAngel: Inicio isActiveAngel...");
        boolean active = true;
        JSONObject jsonSettings = jsonAngel.getJSONObject("userSettingsCollection");

        if (jsonSettings.getString("userSettings").equals("")) {
            active = false;
        }

        logger.info(this.uid + " - isActiveAngel: Fin isActiveAngel...");
        return active;
    }

    /**
     * Comprueba si un angel esta seleccionado por un usuario. Devolver? true en caso de encontrarse seleccionado, false en caso contrario.
     * 
     * @param angelsSelected
     * @param angel
     * @return
     */
    public boolean isSelectedArray(String angelsSelected, String angel) {
        logger.info(this.uid + " - isSelectedArray: Inicio isSelectedArray...");
        boolean seleccionado = false;

        if (!angelsSelected.equals("") && angelsSelected != null) {
            String[] arrayAngels = angelsSelected.split((";"));

            for (int i = 0; i < arrayAngels.length; i++) {
                if (angel.equals(arrayAngels[i])) {
                    seleccionado = true;
                    break;
                }
            }
        }
        logger.info(this.uid + " - isSelectedArray: Fin isSelectedArray...");
        return seleccionado;
    }

    /**
     * Obtiene un JSONArray con los datos de los angeles definidos en la aplicacion. Puede lanzar excepciones del tipo JSONException.
     *
     * @param arrayAngels
     * @return
     * @throws JSONException
     */
    public JSONArray getAngels(String[][] arrayAngels) throws JSONException {
        logger.info(this.uid + " - getAngels: Inicio getAngels...");
        String[] arrayAngelsSelected = this.getAngelsSelected().split(";");
        JSONArray jsonAngelsFilter = new JSONArray();
        JSONObject jsonDatesAngel = null;
        Long uidLong = (new Double(this.getUid())).longValue();

        for (int i = 0; i < arrayAngelsSelected.length; i++) {
            for (int j = 0; j < arrayAngels.length; j++) {
                if (arrayAngelsSelected[i].equals(arrayAngels[j][0])) {
                    jsonDatesAngel = new JSONObject();
                    jsonDatesAngel.put("idAngel", arrayAngels[j][0]);
                    jsonDatesAngel.put("nameAngel", arrayAngels[j][1]);
                    jsonDatesAngel.put("imgAngel", arrayAngels[j][2]);
                    jsonDatesAngel.put("typeAngel", arrayAngels[j][3]);
                    jsonDatesAngel.put("acceptAngel", "0");
                    jsonDatesAngel.put("userPropAngel", uidLong);
                    jsonDatesAngel.put("confirmAngel", "0");

                    jsonAngelsFilter.put(jsonDatesAngel);
                }
            }
        }
        logger.info(this.uid + " - getAngels: Fin getAngels...");
        return jsonAngelsFilter;
    }

    /**
     * Obtiene, en formato JSON, los datos de un nuevo angel no perteneciente a Facebook 
     * para ser insertado en base de datos.
     * 
     * @param nameAngel
     * @param emailAngel
     * @param typeAngel
     * @return
     * @throws JSONException 
     */
    public JSONObject getNewAngelNotFacebook(String nameAngel, String emailAngel, String typeAngel) throws JSONException {
        logger.info(this.uid + " - getNewAngelNotFacebook: Inicio getNewAngelNotFacebook...");
        JSONObject jsonDetailAngel;
        Long uidLong = (new Double(this.getUid())).longValue();

        jsonDetailAngel = new JSONObject();
        jsonDetailAngel.put("idAngel", emailAngel);
        jsonDetailAngel.put("nameAngel", nameAngel);
        jsonDetailAngel.put("imgAngel", IMG_STANDAR_ANGEL);
        jsonDetailAngel.put("typeAngel", typeAngel);
        jsonDetailAngel.put("acceptAngel", "0");
        jsonDetailAngel.put("userPropAngel", uidLong);
        jsonDetailAngel.put("confirmAngel", "0");
        jsonDetailAngel.put("idFacebook", "");
        
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getNewAngelNotFacebook: Datos del nuevo angel: " + jsonDetailAngel.toString());
        
        logger.info(this.uid + " - getNewAngelNotFacebook: Fin getNewAngelNotFacebook...");
        return jsonDetailAngel;
    }
    
    /**
     * Obtiene, en formato JSON, los datos de un nuevo angel perteneciente a Facebook 
     * para ser insertado en base de datos.
     * 
     * @param datesAngel
     * @return
     * @throws JSONException 
     */
    public JSONObject getNewAngelFacebook(String[] datesAngel) throws JSONException {
        logger.info(this.uid + " - getNewAngelFacebook: Inicio getNewAngelFacebook...");
        JSONObject jsonDetailFacebookAngel;
        Long uidLong = (new Double(this.getUid())).longValue();

        jsonDetailFacebookAngel = new JSONObject();
        jsonDetailFacebookAngel.put("idAngel", datesAngel[0]);
        jsonDetailFacebookAngel.put("nameAngel", datesAngel[1]);
        jsonDetailFacebookAngel.put("imgAngel", datesAngel[2]);
        jsonDetailFacebookAngel.put("typeAngel", datesAngel[3]);
        jsonDetailFacebookAngel.put("acceptAngel", "0");
        jsonDetailFacebookAngel.put("userPropAngel", uidLong);
        jsonDetailFacebookAngel.put("confirmAngel", "0");
        jsonDetailFacebookAngel.put("idFacebook", datesAngel[0]);
        
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getNewAngelFacebook: Datos del nuevo angel: " + jsonDetailFacebookAngel.toString());
        
        logger.info(this.uid + " - getNewAngelFacebook: Fin getAngels...");
        return jsonDetailFacebookAngel;
    }

    /**
     * Obtiene los angeles seleccionados para cada filtro definido.
     *
     * @param arrayAngels
     * @param angelsFilter
     */
    public void gelAngelsUserFilter(String[][] arrayAngels, String angelsFilter) {
        logger.info(this.uid + " - gelAngelsUserFilter: Inicio gelAngelsUserFilter...");
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - gelAngelsUserFilter: Angeles definidos en los filtros: " + angelsFilter);
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - gelAngelsUserFilter: Numero de angeles definidos: " + arrayAngels.length);

        try{
            if (!angelsFilter.equals("") && angelsFilter != null) {
                String[] arrayAngelsFilter = angelsFilter.split(";");

                for (int i = 0; i < arrayAngelsFilter.length; i++) {
                    for (int j = 0; j < arrayAngels.length; j++) {
                        if (arrayAngelsFilter[i].equals(arrayAngels[j][0])) {
                            if (!isSelectedArray(this.angelsSelected, arrayAngelsFilter[i])) {
                                if (this.getAngelsSelected().equals("")) {
                                    this.setAngelsSelected(arrayAngelsFilter[i] + ";");
                                } else {
                                    this.setAngelsSelected(this.getAngelsSelected() + arrayAngelsFilter[i] + ";");
                                }
                            }
                        }
                    }
                }
            }
        }catch(NullPointerException e){
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - gelAngelsUserFilter: Excepcion capturada NullPointerException: " + e.getMessage());
        }
        logger.info(this.uid + " - gelAngelsUserFilter: Fin gelAngelsUserFilter...");
    }

    /**
     * Almacena en base de datos los nuevos angeles para un nuevo usuario de la
     * aplicacion.
     *
     * @param angels
     */
    public void putNewAngelsNewUser(JSONArray angels){
        logger.info(this.uid + " - putNewAngelsNewUser: Inicio putNewAngelsNewUser...");
        try {
            for (int i = 0; i < angels.length(); i++) {
                this.snsObject.getClient().settingsAngels_setNewAngel(String.class, angels.getJSONObject(i));
                JSONObject newAngel = findAngel(angels.getJSONObject(i));
                
                if(!newAngel.getString("typeAngel").equals("F")){
                    snsObject.getEmailObject().sendMailConfirmationAngel(newAngel, this.getUidPublic());
                }
            }
        } catch (UnsupportedEncodingException ex) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - putNewAngelsNewUser: Excepcion capturada UnsupportedEncodingException: " + ex.getMessage());
        } catch (UniformInterfaceException ex) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - putNewAngelsNewUser: Excepcion capturada UniformInterfaceException: " + ex.getMessage());
        } catch (IOException ex) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - putNewAngelsNewUser: Excepcion capturada IOException: " + ex.getMessage());
        } catch (java.security.NoSuchProviderException ex) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - putNewAngelsNewUser: Excepcion capturada java.security.NoSuchProviderException: " + ex.getMessage());
        } catch (MessagingException ex) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - putNewAngelsNewUser: Excepcion capturada MessagingException: " + ex.getMessage());
        } catch (JSONException ex) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - putNewAngelsNewUser: Excepcion capturada JSONException: " + ex.getMessage());
        }
        logger.info(this.uid + " - putNewAngelsNewUser: Fin putNewAngelsNewUser...");
    }

    public JSONObject putNewAngelFacebook(JSONObject jsonNewAngel) {
        try {
            logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - putNewAngelsUser: Nuevo angel...");
            this.snsObject.getClient().settingsAngels_setNewAngel(String.class, jsonNewAngel);
            jsonNewAngel = findAngel(jsonNewAngel);
            if (!jsonNewAngel.toString().equals("{}")) {
                if (!jsonNewAngel.getString("typeAngel").equals("F")) {
                    snsObject.getEmailObject().sendMailConfirmationAngel(jsonNewAngel, this.getUidPublic());
                }
            }
        } catch (JSONException ex) {
            Exceptions.printStackTrace(ex);
        } catch (UnsupportedEncodingException ex) {
            Exceptions.printStackTrace(ex);
        } catch (UniformInterfaceException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (java.security.NoSuchProviderException ex) {
            Exceptions.printStackTrace(ex);
        } catch (MessagingException ex) {
            Exceptions.printStackTrace(ex);
        }

        return jsonNewAngel;
    }
    
    /**
     * Inserta los nuevos angeles en la base de datos para un usuario ya existente.
     *
     * @param jsonRespuesta
     * @param angels
     */
    public void putNewAngelsUser(JSONObject jsonRespuesta, JSONArray angels){
        logger.info(this.uid + " - putNewAngelsUser: Fin putNewAngelsUser...");
        JSONObject jsonAngel = null;
        JSONObject jsonAngelDB = null;
        JSONArray jsonArrayDB = null;
        boolean isAngelNew = false;

        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - putNewAngelsUser: No es una nueva conexi?n...");
        try {
            jsonArrayDB = snsObject.getJsonUtilities().getJSONArray(jsonRespuesta.getString("settingsAngels"));

            logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - putNewAngelsUser: Angeles actuales: " + jsonArrayDB.toString());

            for (int i = 0; i < angels.length(); i++) {
                isAngelNew = true;
                // Angel nuevo
                jsonAngel = angels.getJSONObject(i);
                for (int j = 0; j < jsonArrayDB.length(); j++) {
                    logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - putNewAngelsUser: Buscando " + j);
                    // Angel en la BD
                    jsonAngelDB = jsonArrayDB.getJSONObject(j);
                    if (jsonAngelDB.getString("idAngel").equals(jsonAngel.getString("idAngel"))) {
                        isAngelNew = false;
                        break;
                    }
                }
                if (isAngelNew) {
                    logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - putNewAngelsUser: Nuevo angel...");
                    this.snsObject.getClient().settingsAngels_setNewAngel(String.class, jsonAngel);
                    jsonAngel = findAngel(jsonAngel);
                    if (!jsonAngel.toString().equals("{}")) {
                        try {
                            try {
                                if (!jsonAngel.getString("typeAngel").equals("F")) {
                                    snsObject.getEmailObject().sendMailConfirmationAngel(jsonAngel, this.getUidPublic());
                                }
                            } catch (UnsupportedEncodingException ex) {
                                Exceptions.printStackTrace(ex);
                            } catch (UniformInterfaceException | MessagingException ex) {
                                Exceptions.printStackTrace(ex);
                            } catch (IOException ex) {
                                Exceptions.printStackTrace(ex);
                            }
                        } catch (java.security.NoSuchProviderException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                    }
                }
            }
            setToDelOlderAngels(angels);
            logger.info(this.uid + " - putNewAngelsUser: Fin putNewAngelsUser...");
        } catch (JSONException ex) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - putNewAngelsUser: Excepcion capturada JSONException: " + ex.getMessage());
        }

    }

    /**
     * Inserta los nuevos angeles en la base de datos y los devuelve como retorno en un JSONArray. Puede lanzar excepciones del tipo
     * JSONException, UnsupportedEncodingException, UniformInterfaceException, IOException, NoSuchProviderException o MessagingException.
     *
     * @param inicio
     * @return
     * @throws JSONException
     * @throws UnsupportedEncodingException
     * @throws UniformInterfaceException
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws MessagingException
     */
    public JSONArray putNewAngels(boolean inicio) throws JSONException, UnsupportedEncodingException, UniformInterfaceException, IOException, NoSuchProviderException, MessagingException {
        logger.info(this.uid + " - putNewAngels: Inicio putNewAngels...");
        
        Iterator<String> desFilterIt = this.snsObject.getConfigurationManager().getListActiveFilters().iterator();
        String keyFilter;
        
        while(desFilterIt.hasNext()){
            keyFilter = desFilterIt.next();
            
            gelAngelsUserFilter(this.snsObject.getAngelsUtilities().getArrayAngelsSelected(), this.filterDaoMap.get(keyFilter).getAngels());
        }

        JSONArray angels = this.getAngels(this.snsObject.getAngelsUtilities().getArrayAngelsSelected());
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - putNewAngels: Nuevos angeles: " + angels.toString());

        String respuesta = "";
        JSONObject jsonRespuesta = null;

        Long uidLong = (new Double(this.getUid())).longValue();
        respuesta = this.snsObject.getClient().settingsAngels_getAngelsByPropUid(String.class, "\"" + uidLong + "\"");
        jsonRespuesta = new JSONObject(respuesta);
        
        if (!existAnyAngel(jsonRespuesta, "settingsAngels")) {
            putNewAngelsNewUser(angels);
        } else {
            putNewAngelsUser(jsonRespuesta, angels);
        }
        logger.info(this.uid + " - putNewAngels: Fin putNewAngels...");
        return angels;
    }

    /**
     * Indica si un angel esta almacenado en base de datos, devolviendo el JSONObject con la informacion de la base de datos
     * o un nuevo JSONObject vacio que indica que el usuario no ha sido encontrado. Puede lanzar excepciones del tipo JSONException.
     *
     * @param jsonAngel
     * @return
     * @throws JSONException
     */
    public JSONObject findAngel(JSONObject jsonAngel) throws JSONException {
        logger.info(this.uid + " - findAngel: Fin findAngel...");
        Long uidLong = (new Double(this.getUid())).longValue();
        String respuesta = this.snsObject.getClient().settingsAngels_getAngelsByPropUid(String.class, "\"" + uidLong + "\"");
        JSONObject jsonRespuesta = new JSONObject(respuesta);
        JSONArray jsonArrayDB = snsObject.getJsonUtilities().getJSONArray(jsonRespuesta.getString("settingsAngels"));
        JSONObject jsonAngelDB = null;
        boolean encontrado = false;
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - findAngel: Angeles actuales tras el nuevo angel: " + jsonArrayDB.toString());

        for (int i = 0; i < jsonArrayDB.length(); i++) {
            logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - findAngel: Buscando " + i);
            // Angel en la BD
            jsonAngelDB = jsonArrayDB.getJSONObject(i);
            if (jsonAngelDB.getString("idAngel").equals(jsonAngel.getString("idAngel"))) {
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - findAngel: Angel encontrado...");
            logger.info(this.uid + " - findAngel: Fin findAngel...");
            return jsonAngelDB;
        } else {
            logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - findAngel: Angel no encontrado...");
            logger.info(this.uid + " - findAngel: Fin findAngel...");
            return new JSONObject();
        }
    }

    /**
     * Prepara la lista de angeles no seleccionados para ser borrados en base de datos. Puede lanzar excepciones del tipo JSONException.
     * 
     * @param angels
     * @throws JSONException
     */
    public void setToDelOlderAngels(JSONArray angels) throws JSONException {
        logger.info(this.uid + " - setToDelOlderAngels: Inicio setToDelOlderAngels...");
        Long uidLong = (new Double(this.getUid())).longValue();
        String respuesta = this.snsObject.getClient().settingsAngels_getAngelsByPropUid(String.class, "\"" + uidLong + "\"");
        JSONObject jsonRespuesta = new JSONObject(respuesta);
        JSONObject jsonAngel = null;
        JSONObject jsonAngelDB = null;
        JSONArray jsonArrayDB = snsObject.getJsonUtilities().getJSONArray(jsonRespuesta.getString("settingsAngels"));
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - setToDelOlderAngels: Angeles actuales a borrar: " + jsonArrayDB.toString());
        boolean isNotSelected = true;

        for (int i = 0; i < jsonArrayDB.length(); i++) {
            isNotSelected = true;
            // Angel nuevo
            jsonAngelDB = jsonArrayDB.getJSONObject(i);
            for (int j = 0; j < angels.length(); j++) {
                // Angel en la BD
                jsonAngel = angels.getJSONObject(j);
                logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - setToDelOlderAngels: Buscando para borrar " + jsonAngel.toString());
                if (jsonAngelDB.getString("typeAngel").equals("F") || jsonAngel.getString("idAngel").equals(jsonAngelDB.getString("idAngel"))) {
                    isNotSelected = false;
                    break;
                }
            }
            if (isNotSelected) {

                jsonAngelDB.put("acceptAngel", "0");
                jsonAngelDB.put("confirmAngel", "1");
                logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - setToDelOlderAngels: Angel no seleccionado: " + jsonAngelDB.toString());
                try {
                    this.snsObject.getClient().settingsAngels_setAngelByUid(String.class, jsonAngelDB.getString("uidAngel"), jsonAngelDB);
                } catch (JSONException | UniformInterfaceException e) {
                    logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - setToDelOlderAngels: Angel actualizado...");
                    logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - setToDelOlderAngels: Excepcion capturada Exception: " + e.getMessage());
                }
            }
        }
        logger.info(this.uid + " - setToDelOlderAngels: Fin setToDelOlderAngels...");
    }

    /**
     * Elimina los angeles eliminados desde la aplicacion. Puede lanzar excepciones del tipo NoSuchProviderException, MessagingException, UniformInterfaceException o IOException.
     *
     * @throws NoSuchProviderException
     * @throws MessagingException
     * @throws UniformInterfaceException
     * @throws IOException
     */
    public void deleteOlderAngels() throws NoSuchProviderException, MessagingException, UniformInterfaceException, IOException {
        logger.info(this.uid + " - deleteOlderAngels: Inicio deleteOlderAngels...");
        try {
            Long uidLong = (new Double(this.getUid())).longValue();
            String respuesta = this.snsObject.getClient().settingsAngels_getAngelsByPropUid(String.class, "\"" + uidLong + "\"");
            JSONObject jsonRespuesta = new JSONObject(respuesta);
            JSONObject jsonAngelDB = null;
            if (jsonRespuesta.getString("settingsAngels") != null) {
                JSONArray jsonArrayDB = snsObject.getJsonUtilities().getJSONArray(jsonRespuesta.getString("settingsAngels"));
                for (int i = 0; i < jsonArrayDB.length(); i++) {
                    jsonAngelDB = jsonArrayDB.getJSONObject(i);
                    logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - deleteOlderAngels: Angel a borrar. Inicio " + i + ": " + jsonAngelDB.toString());
                    if (jsonAngelDB.getString("acceptAngel").equals("0") && jsonAngelDB.getString("confirmAngel").equals("1")) {
                        // Borrar Angel TEST RESTFUL
                        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - deleteOlderAngels: Angel a borrar: " + jsonAngelDB.toString());
                        try {
                            this.snsObject.getClient().settingsAngels_delAngelByUid(jsonAngelDB.getString("uidAngel"));
                            logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - deleteOlderAngels: Angel borrado: " + jsonAngelDB.getString("uidAngel"));
                            // Enviar correo de confirmaci??n angel borrado
                            snsObject.getEmailObject().sendMailDeleteAngel(jsonAngelDB, this.getUidPublic());
                        } catch (Exception e) {
                            logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - deleteOlderAngels: Excepcion con angel: " + jsonAngelDB.getString("uidAngel"));
                            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - deleteOlderAngels: Excepcion capturada Exception: " + e.getMessage());
                        }
                    }
                }
            }
        } catch (JSONException ex) {
            logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - deleteOlderAngels: No existe ninguna relaci?n para borrar...");
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - deleteOlderAngels: Excepcion capturada JSONException: " + ex.getMessage());
        }
        logger.info(this.uid + " - deleteOlderAngels: Fin deleteOlderAngels...");
    }

    /**
     * Almacena los angeles seleccionado para un determinado filtro. Puede lanzar excepciones del tipo JSONException.
     *
     * @param angelsFilter
     * @param des
     * @throws JSONException
     */
    public void putAngelsFilter(JSONArray angelsFilter, String des) throws JSONException {
        logger.info(this.uid + " - putAngelsFilter: Inicio putAngelsFilter...");
        String respuesta = "";
        JSONObject jsonRespuesta = null;
        JSONObject jsonAngel = null;
        JSONArray jsonArrayAngels = null;
        Long uidLong = (new Double(this.getUid())).longValue();
        respuesta = this.snsObject.getClient().settingsAngels_getAngelsByPropUid(String.class, "\"" + uidLong + "\"");
        jsonRespuesta = new JSONObject(respuesta);
        jsonArrayAngels = this.snsObject.getJsonUtilities().getJSONArray(jsonRespuesta.getString("settingsAngels"));

        for (int j = 0; j < angelsFilter.length(); j++) {
            for (int i = 0; i < jsonArrayAngels.length(); i++) {
                jsonAngel = jsonArrayAngels.getJSONObject(i);
                if(jsonAngel.getString("typeAngel").equals("F")){
                    if (angelsFilter.getJSONObject(j).getString("idAngel").equals(jsonAngel.getString("idFacebook"))) {
                        this.filterDaoMap.get(des).putAngelInCollectionFilter(jsonAngel);
                    }
                }else if (angelsFilter.getJSONObject(j).getString("idAngel").equals(jsonAngel.getString("idAngel"))) {
                    this.filterDaoMap.get(des).putAngelInCollectionFilter(jsonAngel);
                }
            }
        }
        logger.info(this.uid + " - putAngelsFilter: Fin putAngelsFilter...");
    }
    
    /**
     * Actualiza las relaciones entre un filtro y los angeles definidos.
     * 
     * @param jsonArrayAngels Angeles de un filtro.
     * @param des Identificador del filtro
     * @throws JSONException 
     */
    public void updateOfflineAngelsFilter(JSONArray jsonArrayAngels, String des) throws JSONException {
        JSONObject jsonAngel;

        for (int i = 0; i < jsonArrayAngels.length(); i++) {
            jsonAngel = jsonArrayAngels.getJSONObject(i);

            this.filterDaoMap.get(des).putAngelInCollectionFilter(jsonAngel);

        }
    }
    
    /**
     * Obtiene un angel con todas sus relaciones actualizadas.
     * 
     * @param jsonAngel
     * @return
     * @throws JSONException 
     */
    public JSONObject getAngelInformationWithFilters(JSONObject jsonAngel) throws JSONException{
        logger.info(this.uid + " - getAngelInformationWithFilters: Inicio getAngelInformationWithFilters...");
        
        Iterator<String> itKeysFilter = this.snsObject.getConfigurationManager().getListActiveFilters().iterator();
        
        while(itKeysFilter.hasNext()) {
            this.filterDaoMap.get(itKeysFilter.next()).getJsonAngelWithFilterRelationship(jsonAngel);
        }
        
        logger.info(this.uid + " - getAngelInformationWithFilters: Fin getAngelInformationWithFilters!!");
        return jsonAngel;
    }
   

    /**
     * Actualiza en base de datos los filtros almacenados. Puede lanzar excepciones del tipo JSONException.
     * 
     * @return JSONObject
     * @throws JSONException
     */
    public JSONObject updateFilters() throws JSONException, InterDataBaseException, InterProcessException, InterEmailException {
        logger.info(this.uid + " - putNewInstanceFilter: Inicio putNewInstanceFilter... ");
        JSONObject newInstanceFilter = null;
        JSONArray jsonAngFilter;
        Long uidLong = (new Double(this.getUid())).longValue();

        Iterator<String> it = this.snsObject.getConfigurationManager().getListActiveFilters().iterator();
        
        String keyFilter;
        
        while (it.hasNext()) {
            keyFilter = it.next();
            logger.info(this.uid + " - putNewInstanceFilter: Actualizando filtro " + keyFilter + "...");
            
            // Obtenemos el objeto JSON del filtro
            String resultFilter = this.manager.getSnsObject().getClient().settingsFilter_getFiltersByIdFilter(String.class, this.filterDaoMap.get(keyFilter).getUid().toString());
            JSONObject resultNewInstanceFilter = new JSONObject(resultFilter);
            
            // Seteamos los valores a guardar
            resultNewInstanceFilter.put("activeFilter", this.filterDaoMap.get(keyFilter).getActive());
            resultNewInstanceFilter.put("frecFilter", this.filterDaoMap.get(keyFilter).getFrec());
            newInstanceFilter = this.filterDaoMap.get(keyFilter).getFilterWithRelationshipWithUserSettings(resultNewInstanceFilter, this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid());
            
            try {
                // Actualizamos la informacion del filtro en base de datos
                this.snsObject.getClient().settingsFilter_updateFilterByIdFilter(String.class, newInstanceFilter.getString("idFilter"), newInstanceFilter, "0");
            } catch (UniformInterfaceException e) {
                logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - putNewInstanceFilter: Excepcion capturada UniformInterfaceException: " + e.getMessage());
                
                // Analizamos la excepcion y seguimos si es una 2XX
                this.snsObject.getExceptionManager().initControlException(e);
                
                // Obtenemos los angeles del filtro
                jsonAngFilter = this.filterDaoMap.get(keyFilter).getAngelsFilter(this.snsObject.getAngelsUtilities().getArrayAngelsSelected());
                
                // Los actualizamos en base de datos
                putAngelsFilter(jsonAngFilter, keyFilter);
            }
            
            logger.info(this.uid + " - putNewInstanceFilter: Filtro " + keyFilter + " correctamente actualizado!!");
        }
        
        logger.info(this.uid + " - putNewInstanceFilter: Fin putNewInstanceFilter!!");
        return newInstanceFilter;
    }

    /**
     * Formatea en un String los angeles seleccionados en la aplicacion. Puede lanzar excepciones del tipo JSONException.
     *
     * @param jsonAngels
     * @param desTypeAngel
     * @param des
     * @return
     * @throws JSONException
     */
    public String formatJsonLstAngel(JSONArray jsonAngels, String desTypeAngel, String des) throws JSONException {
        logger.info(this.uid + " - formatJsonLstAngel: Inicio formatJsonLstAngel...");
        JSONObject jsonAngel = null;
        JSONObject aux = null;
        String angels = "";

        for (int i = 0; i < jsonAngels.length(); i++) {
            jsonAngel = jsonAngels.getJSONObject(i);

            if (jsonAngel.getString("typeAngel").equals(desTypeAngel)) {
                aux = new JSONObject();
                aux.put("idAngel" + des, jsonAngel.getString("uidAngel"));
                aux.put("nameAngel" + des, jsonAngel.getString("nameAngel"));
                aux.put("emailAngel" + des, jsonAngel.getString("idAngel"));
                if (i == 0) {
                    angels = aux.toString() + ";";
                } else {
                    angels += aux.toString() + ";";
                }
            }
        }

        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - formatJsonLstAngel: Angeles definidos anteriormente para el filtro " + des + ": " + angels);

        logger.info(this.uid + " - formatJsonLstAngel: Fin formatJsonLstAngel...");
        return angels;
    }

    /**
     * Indica si hay algun angel definido para los diferentes tipos de angeles de
     * la aplicacion. Enviar? el valor true en caso afirmativo y false en caso contrario.
     *
     * @param jsonAngels
     * @param typeTable
     * @return
     */
    public boolean existAnyAngel(JSONObject jsonAngels, String typeTable){
        logger.info(this.uid + " - existAnyAngel: Inicio existAnyAngel...");
        boolean result = false;

        try{
            String strAngels = jsonAngels.getString(typeTable);

            if(strAngels != null){
                result = true;
            }
        }catch(Exception e){
            logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - existAnyAngel: No hay angeles definidos...");
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - existAnyAngel: Excepcion capturada Exception: " + e.getMessage());
        }
        logger.info(this.uid + " - existAnyAngel: Fin existAnyAngel...");
        return result;
    }

    /**
     * Obtiene todos los angeles definidos por un usuario para sus diferentes filtros. Puede lanzar excepciones del tipo JSONException.
     * 
     * @param desTypeAngel
     * @return
     * @throws JSONException
     */
    public String getAngelsUser(String desTypeAngel) throws JSONException {
        logger.info(this.uid + " - getAngelsUser: Inicio getAngelsUser para el tipo: " + desTypeAngel);
        Long uidLong = (new Double(this.getUid())).longValue();
        String strListAngels = this.snsObject.getClient().settingsAngels_getAngelsByPropUid(String.class, "\"" + uidLong + "\"");
        String angels = "";

        if (!strListAngels.equals("") && !strListAngels.equals("{}") && existAnyAngel(new JSONObject(strListAngels),"settingsAngels")) {
            
            JSONObject jsonAngels = new JSONObject(strListAngels);
            JSONArray jsonArrayAngels = this.snsObject.getJsonUtilities().getJSONArray(jsonAngels.getString("settingsAngels"));
            
            switch (desTypeAngel) {
                case "G":
                    angels = formatJsonLstAngel(jsonArrayAngels, desTypeAngel, "GoogleSelected");
                    logger.info(this.uid + " - getAngelsUser: Angeles del tipo " + desTypeAngel + ": " + angels);
                    break;
                case "O":
                    angels = formatJsonLstAngel(jsonArrayAngels, desTypeAngel, "Ed");
                    logger.info(this.uid + " - getAngelsUser: Angeles del tipo " + desTypeAngel + ": " + angels);
                    break;
                case "F":
                    JSONObject jsonAngel;
                    
                    for (int i = 0; i < jsonArrayAngels.length(); i++) {
                        jsonAngel = jsonArrayAngels.getJSONObject(i);
                        logger.info(this.uid + " - getAngelsUser: Angeles del tipo " + desTypeAngel + " obtenido: " + jsonAngel.toString());

                        if (jsonAngel.getString("typeAngel").equals(desTypeAngel)) {
                            if (i == 0) {
                                angels = jsonAngel.getString("idFacebook") + ";";
                            } else {
                                angels += jsonAngel.getString("idFacebook") + ";";
                            }
                        }
                    }
                    logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getAngelsUser: Angeles definidos del tipo " + desTypeAngel + ": " + angels);
                    break;
            }
        }
        
        logger.info(this.uid + " - getAngelsUser: Fin getAngelsUser para el tipo: " + desTypeAngel);
        return angels;
    }

    /**
     * Obtiene de un String de base de datos un JSONArray de objetos JSON. Puede lanzar excepciones del tipo JSONException.
     * 
     * @param strValue
     * @return
     * @throws JSONException
     */
    public JSONArray getJSONArray(String strValue) throws JSONException {
        logger.info(this.uid + " - getJSONArray: Inicio getJSONArray...");
        JSONArray jsonArray = null;

        if ((!strValue.equals("{}")) && (!strValue.contains("error_code"))) {
            if (!strValue.substring(0, 1).equals("[") && !strValue.substring(strValue.length() - 1, strValue.length()).equals("]")) {
                String auxValue = "[" + strValue + "]";
                strValue = auxValue;
            }

            jsonArray = new JSONArray(strValue);
        } else {
            jsonArray = new JSONArray();
        }

        logger.info(this.uid + " - getJSONArray: Fin getJSONArray...");
        return jsonArray;
    }

    private void initFiltersMap(){
        // Inicializamos el map de los filtros
        this.filterDaoMap = new HashMap();
        
        Iterator<String> itFilter = this.snsObject.getConfigurationManager().getListActiveFilters().iterator();
        String desFilter;
        
        while(itFilter.hasNext()){
            // Obtenemos la key del filtro
            desFilter = itFilter.next();
            
            // Inicializamos el filtro en el map
            this.filterDaoMap.put(desFilter, new UserSettings_SettingsFilterDAO(this.manager, desFilter));
        }
    }
    
    public JSONArray getFiltersUserFromDB() throws InterDataBaseException, InterProcessException, InterEmailException{
        logger.info(this.uid + " - getFiltersUserFromDB: Inicio getFiltersUserFromDB...");
        JSONArray resultFilters = new JSONArray();
        
        try {
            logger.info(this.uid + " - getFiltersUserFromDB: Accediendo a base de datos para obtener los filtros...");
            JSONObject jsonFiltersUserSettings = new JSONObject(this.snsObject.getClient().settingsFilter_getFiltersByUserSettingsUid(String.class, this.uid.toString()));
            
            resultFilters = jsonFiltersUserSettings.getJSONArray("settingsFilter");
            logger.info(this.uid + " - getFiltersUserFromDB: Filtros obtenidos!!");
        } catch (JSONException | NullPointerException e) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getFiltersUserFromDB: Se ha producido un error al obtener los filtros del usuario: " + e.getCause());
            this.snsObject.getExceptionManager().initControlException(e);
        }
        
        logger.info(this.uid + " - getFiltersUserFromDB: Fin getFiltersUserFromDB!!");
        return resultFilters;
    }
    
    /**
     * Carga toda la funcionalidad de la aplicacion para los filtros definidos.
     * 
     */
    private void loadFullFuntionalityFilter() throws InterDataBaseException, InterProcessException, InterEmailException{
        logger.info(this.uid + " - loadFullFuntionalityFilter: Inicio loadFullFuntionalityFilter...");
        
        JSONArray arrayFilters = getFiltersUserFromDB();
        
        Iterator<String> itFilter = this.snsObject.getConfigurationManager().getListActiveFilters().iterator();
        String desFilter;
        
        while(itFilter.hasNext()){
            // Obtenemos la key del filtro
            desFilter = itFilter.next();
            try {
                // Cargamos el filtro
                loadFilter(desFilter, arrayFilters);
            } catch (JSONException | ParseException | NullPointerException | java.text.ParseException ex) {
                logger.info(this.uid + " - loadFullFuntionalityFilter: Se ha producido un error al cargar los filtros...");
                this.snsObject.getExceptionManager().initControlException(ex);
            }
        }
        
        logger.info(this.uid + " - loadFullFuntionalityFilter: Fin loadFullFuntionalityFilter...");
    }

    /**
     * Carga desde base de datos todas las caracteristicas de un determinado filtro.
     *
     * @param des Identificador de filtro.
     * @throws JSONException
     */
    public void loadFilter(String des, JSONArray arrayFilters) throws JSONException, ParseException, java.text.ParseException {
        logger.info(this.uid+ " - loadFilter: Inicio loadFilter para " + des);
        JSONObject jsonFilter;
        JSONArray jsonArrayAngels;

        for(int i = 0; i < arrayFilters.length(); i++){
            jsonFilter = arrayFilters.getJSONObject(i);
            
            if(jsonFilter.getString("typeFilter").equals(des)){

                logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - loadFilter: Angeles para el filtro " + des + " cargados");
                jsonArrayAngels = (new JSONObject(this.snsObject.getClient().settingsFilter_getAngelsCollectionByIdFilter(String.class, jsonFilter.getString("idFilter")))).getJSONArray("settingsAngels");
                logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - loadFilter: Array de angeles: " + jsonArrayAngels.toString());
        
                 // Creamos el filtro y lo metemos en el mapa
                UserSettings_SettingsFilterDAO filter = new UserSettings_SettingsFilterDAO(this.manager, des);
                this.filterDaoMap.put(des, filter);

                // Cargamos el resto de caracteristicas del filtro
                this.filterDaoMap.get(des).loadSettingsFilter(jsonFilter, jsonArrayAngels);
            }
        }

        logger.info(this.uid + " - loadFilter: Fin loadFilter para " + des);
    }

    /**
     * Actualiza un filtro con la nueva informacion y lo retorna al sistema. Puede lanzar excepciones del tipo JSONException.
     *
     * @param desFilter Identificador de filtro.
     * @return
     * @throws JSONException
     */
    public JSONObject updateFilter(String desFilter) throws JSONException {
        logger.info(this.uid + " - updateFilter: Inicio updateFilter para " + desFilter);
        JSONObject jsonFilter ;

        // Obtenemos los angeles del filtro antes de resetearlos
        JSONObject jsonRespuesta = new JSONObject(this.snsObject.getClient().settingsAngels_getAngelsByPropUid(String.class, "\"" + this.uid + "\""));
        JSONArray jsonArrayAngels = this.snsObject.getJsonUtilities().getJSONArray(jsonRespuesta.getString("settingsAngels"));
        
        // Obtenemos el objeto JSON del filtro
        String resultFilter = this.manager.getSnsObject().getClient().settingsFilter_getFiltersByIdFilter(String.class, this.filterDaoMap.get(desFilter).getUid().toString());
        jsonFilter = new JSONObject(resultFilter);
        
        // Aniadimos la relacion con el usuario de la aplicacion
        jsonFilter = this.filterDaoMap.get(desFilter).getFilterWithRelationshipWithUserSettings(jsonFilter, this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid());
        
        // Reseteamos los angeles del filtro
        jsonFilter = this.filterDaoMap.get(desFilter).resetAngelsToFilter(jsonFilter);
        
        try {
            this.snsObject.getClient().settingsFilter_updateFilterByIdFilter(String.class, jsonFilter.getString("idFilter"), jsonFilter, "1");
        } catch (JSONException | UniformInterfaceException e) {
            logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateFilter: Filtro " + desFilter + " actualizado");
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateFilter: Excepcion capturada Exception: " + e.getMessage());
            
            // Actualizamos los angeles del filtro
            updateOfflineAngelsFilter(jsonArrayAngels, desFilter);
        }
        jsonFilter = new JSONObject(this.snsObject.getClient().settingsFilter_getFiltersByIdFilter(String.class, jsonFilter.getString("idFilter")));

        logger.info(this.uid + " - updateFilter: Fin updateFilter para " + desFilter);
        return jsonFilter;
    }

    /**
     * Gets user ID of the current user logged in.
     * @param request
     * @param response
     */
    public void updateUserID(HttpServletRequest request, HttpServletResponse response) throws UniformInterfaceException, IOException {
        logger.info(this.uid + " - updateUserID: Inicio updateUserID...");
        HttpSession session = request.getSession(false);
        String format = "";

        try {
            for (int j = 0; j < 10; j++) {
                format = SNSAngelGuardFBManager.getFacebookClient().users_getLoggedInUser(String.class, request, "format=json");

                if (!format.contains("error_code") && !format.contains("{}")) {
                    break;
                } else if (j == 9) {
                    logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateUserID: El formato del atributo UID no es permitido...");
                }
            }
            try {
                if (Double.parseDouble(format) > 0) {
                    this.uid = format;
                    SNSAngelGuardFBManager.getLogger().info(this.uid + ": UID ACCEPTED: " + format);
                }
            } catch (Exception e) {
                logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateUserID: Formato no aceptado...");
                logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateUserID: Excepcion capturada Exception: " + e.getMessage());
                this.uid = null;
            }

        } catch (Exception ex) {
            logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateUserID: Error creando UID");
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateUserID: Excepcion capturada Exception: " + ex.getMessage());
        }
        logger.info(this.uid + " - updateUserID: Fin updateUserID...");
    }

    /**
     * Actualiza la fecha en la que un usuario se conecta a la aplicacion y realiza
     * un guardado de informacion. Puede lanzar excepciones del tipo UniformInterfaceException, IOException o JSONException.
     *
     * @throws UniformInterfaceException
     * @throws IOException
     * @throws JSONException
     */
    public void updateLastCheckUS() throws UniformInterfaceException, IOException, JSONException {
        logger.info(this.uid + " - updateLastCheckUS: Inicio updateLastCheckUS...");
        try {
            Long uidLong = (new Double(this.getUid())).longValue();
            this.snsObject.getClient().userSettings_setUpdateTime(String.class, uidLong.toString(), "1", this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUserSession());
        } catch (UniformInterfaceException e) {
            logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateLastCheckUS: Fecha LastCheck actualizada...");
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateLastCheckUS: Excepcion capturada UniformInterfaceException: " + e.getMessage());
        }
        logger.info(this.uid + " - updateLastCheckUS: Fin updateLastCheckUS...");
    }

    /**
     * Actualiza la fecha en la que se realiza el ultimo backup de informacion del usuario.
     */
    public void updateBackUpCheckUS() {
        logger.info(this.uid + " - updateBackUpCheckUS: Inicio updateBackUpCheckUS...");
        try {
            Long uidLong = (new Double(this.getUid())).longValue();
            this.snsObject.getClient().userSettings_setUpdateTime(String.class, uidLong.toString(), "0", this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUserSession());
        } catch (UniformInterfaceException e) {
            logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateLastCheckUS: Fecha BackUpCheck actualizada...");
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateLastCheckUS: Excepcion capturada UniformInterfaceException: " + e.getMessage());
        } 
        logger.info(this.uid + " - updateBackUpCheckUS: Fin updateBackUpCheckUS...");
    }

    /**
     * Obtiene toda la informacion de la base de datos de todos los usuarios de la aplicacion y la retorna en un objeto JSONArray.
     * Su utilidad basica radica en los procesos offline de informacion. Puede lanzar excepciones del tipo JSONException.
     *
     * @param snsObjectManager
     * @return
     * @throws JSONException
     */
    public JSONArray getEntitiesUserSettings(SNSAngelGuardFBManager snsObjectManager) throws JSONException {
        logger.info("Proceso OffLine - getEntitiesUserSettings: Inicio getEntitiesUserSettings...");
        JSONObject jsonUsers = null;
        JSONArray arrayUsers = null;
        String usersClient = snsObjectManager.getClient().userSettings_getEntities(String.class);
        logger.debug("Proceso OffLine - getEntitiesUserSettings: Entidades de la aplicaci?n: " + usersClient);

        if (!usersClient.equals("{}") && (!usersClient.contains("error_code"))) {
            logger.debug("Proceso OffLine - getEntitiesUserSettings: Existen usuarios en la aplicaci?n...");
            try {
                jsonUsers = new JSONObject(usersClient);
                arrayUsers = snsObjectManager.getJsonUtilities().getJSONArray(jsonUsers.getString("userSettings"));
            } catch (Exception e) {
                logger.debug("Proceso OffLine - getEntitiesUserSettings: No existen usuarios en la aplicaci?n...");
                logger.error("Proceso OffLine - getEntitiesUserSettings: Excepcion capturada Exception: " + e.getMessage());
            }
        }

        logger.info("Proceso OffLine - getEntitiesUserSettings: Fin getEntitiesUserSettings...");
        return arrayUsers;
    }

    /**
     * Obtiene una entidad de la tabla UserSettings a partir de su uidPublica y lo retorna como un JSONObject. Puede lanzar excepciones del tipo
     * InterDataBaseException, InterProcessException o InterEmailException.
     * 
     * @param snsObjectOff
     * @param uidPublic
     * @return
     * @throws JSONException
     * @throws UnsupportedEncodingException
     */
    public JSONObject getJsonUserByUidPublic(SNSAngelGuardFBManager snsObjectOff, String uidPublic) throws InterDataBaseException, InterProcessException, InterEmailException{
        JSONObject jsonGeneral = null;
        JSONObject jsonUser = null;
        try {
            logger.info("Proceso OffLine - getJsonUserByUidPublic: Inicio getJsonUserByUidPublic...");
            String respuesta = snsObjectOff.getClient().userSettings_getUserByUidPublic(String.class, "\"" + uidPublic + "\"");
            jsonGeneral = new JSONObject(respuesta);

            logger.info("Proceso OffLine - getJsonUserByUidPublic: Fin getJsonUserByUidPublic...");
            jsonUser = JSONUtilities.getJSONObject(jsonGeneral.getString("userSettings"));
        } catch (JSONException e) {
            logger.error("Proceso OffLine - getJsonUserByUidPublic: Excepcion capturada JSONException: " + e.getMessage());
            this.snsObject.getExceptionManager().initControlException(e);
        } catch (UniformInterfaceException ex) {
            logger.error("Proceso OffLine - getJsonUserByUidPublic: Excepcion capturada UniformInterfaceException: " + ex.getMessage());
            this.snsObject.getExceptionManager().initControlException(ex);
        } catch (UnsupportedEncodingException ex) {
            logger.error("Proceso OffLine - getJsonUserByUidPublic: Excepcion capturada UnsupportedEncodingException: " + ex.getMessage());
            this.snsObject.getExceptionManager().initControlException(ex);
        }

        return jsonUser;
    }
    
            /**
     * Encuentra de entre los ?ngeles definidos para un filtro, si est? definido para ?ste el angel actual.
     * 
     * @param desFilter
     * @param currentAngel
     * @return boolean 
     */
    public boolean isActiveFilterForAngel(String desFilter, String currentAngel){
        boolean angelFound = false;
         
        String angels = this.filterDaoMap.get(desFilter).getAngels();
        
        if(angels != null){
            if (!angels.equals("")) {
                
                String[] angelsSel = angels.split(";");
                
                for(String angelEmail: angelsSel){
                    if(angelEmail.equals(currentAngel)){
                        logger.info(this.uid + " - checkFilter: Angel a quien se le enviar? la notificaci?n del filtro " + desFilter + ": " + angelEmail);
                         angelFound = true;
                         break;
                    }
                }
               
            }
        }
        
        return angelFound;
    }
    
        /**
     * Obtiene todos los amigos de un usuario en Facebook y los almacena en la base de datos SocialNetwork.
     * Podr? lanzar excepciones del tipo UniformInterfaceException, IOException, InterDataBaseException, InterProcessException o InterEmailException.
     * 
     * @throws UniformInterfaceException
     * @throws IOException
     */
    public void getUserFriends() throws UniformInterfaceException, IOException, InterDataBaseException, InterProcessException, InterEmailException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getUserFriends: Inicio getUserFriends...");

        //Se obtienen los amigos del usuario
        List<String> myFriends = this.snsObject.getFacebookRestClient().executeForList("friends.get", String.class);

        for(int j = 0; j < myFriends.size(); j++){
            try {
                this.getNewFriend(myFriends.get(j));
            } catch (JSONException ex) {
                logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getUserFriends: Excepcion capturada JSONException: " + ex.getMessage());
                logger.fatal(ex);
                this.snsObject.getExceptionManager().initControlException(ex);
            }
        }
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getUserFriends: Fin getUserFriends...");
    }
    
        /**
     * Obtiene de Facebook todos los datos necesarios para almacenar un nuevo amigo en la base de datos. 
     * Podr? lanzar excepciones del tipo UniformInterfaceException, IOException, JSONException, InterDataBaseException, InterProcessException o InterEmailException.
     *
     * @param friendUid Identificador del amigo del usuario en Facebook.
     * @throws UniformInterfaceException
     * @throws IOException
     * @throws JSONException
     */
    public void getNewFriend(String friendUid) throws UniformInterfaceException, IOException, JSONException, InterDataBaseException, InterProcessException, InterEmailException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getNewFriend: Inicio getNewFriend para el amigo: " + friendUid);

        String query = "SELECT uid,name,birthday_date,pic_square FROM user WHERE uid=" + friendUid;
        
        List<FriendsFacebook> friendsFacebookList = this.snsObject.getFacebookQueryClient().executeQuery(query, FriendsFacebook.class);

        if (!friendsFacebookList.isEmpty()) {
            JSONObject jsonFriendFacebook = friendsFacebookList.get(0).toJson();
            try {
                jsonFriendFacebook.put("userBirthday", this.snsObject.getDateTimeUtilities().formatearFecha(friendsFacebookList.get(0).getBirthdayDate()));
                
                if (isNewInFriendsFacebook(jsonFriendFacebook)) {
                    try{
                        this.snsObject.getClient().userFacebook_setNewFriendFacebook(String.class, jsonFriendFacebook);
                        updateRelationshipNewFriend(jsonFriendFacebook);
                    } catch (UniformInterfaceException e){
                        logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getNewFriend: No se ha podido actualizar la informacion del usuario: " + jsonFriendFacebook.toString());
                        logger.fatal(e);
                    }
                } else if(isNewFriend(jsonFriendFacebook)){
                    updateRelationshipNewFriend(jsonFriendFacebook);
                } else
                    updateDatesFriend(jsonFriendFacebook);
            } catch (JSONException ex) {
                logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getNewFriend: Excepcion capturada JSONException: " + ex.getMessage());
                logger.fatal(ex);
            }
        }

        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getNewFriend: Fin getNewFriend para el amigo: " + friendUid);
    }
    
    /**
     * Comprueba si un amigo de un usuario ya esta almacenado en la coleccion de amigos de la base de datos SocialNetwork.
     * Podr? lanzar excepciones del tipo JSONException.
     *
     * @param friend Datos del amigo de Facebook.
     * @return boolean
     * @throws JSONException
     */
    public boolean isNewFriend(JSONObject friend) throws JSONException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isNewFriend: Inicio isNewFriend para el amigo: " + friend.getString("userUid"));
        try {
            Long uidLong = (new Double(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid())).longValue();
            String result = this.snsObject.getClient().userFacebook_getFriendsFacebookByUid(String.class, uidLong.toString(), friend.getString("userUid"));

            if(result.contains("userFacebookCollection/" + uidLong.toString())){
                logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isNewFriend: El amigo " + friend.getString("userUid") + " existe en la coleccion de amigos del usuario...");
                return false;
            }else{
                logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isNewFriend: El amigo " + friend.getString("userUid") + " no existe en la coleccion de amigos del usuario...");
                return true;
            }
        } catch (NumberFormatException | JSONException | UniformInterfaceException e) {
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isNewFriend: El amigo " + friend.getString("userUid") + " no existe en la base de datos...");
            return true;
        }
    }
    
        /**
     * Comprueba si un amigo del usuario es nuevo dentro de la base de datos. Podr? lanzar excepciones del tipo JSONException.
     *
     * @param friend Datos del nuevo amigo.
     * @return boolean
     * @throws JSONException
     */
    public boolean isNewInFriendsFacebook(JSONObject friend) throws JSONException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isNewInFriendsFacebook: Inicio isNewInFriendsFacebook para el amigo: " + friend.getString("userUid"));
        try {
            this.snsObject.getClient().userFacebook_isNewFriendsFacebookByUid(String.class, friend.getString("userUid"));
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isNewInFriendsFacebook: El amigo " + friend.getString("userUid") + " existe en la base de datos...");
            return false;
        } catch (JSONException | UniformInterfaceException e) {
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isNewInFriendsFacebook: El amigo " + friend.getString("userUid") + " no existe en la base de datos...");
            return true;
        }
    }
    
        /**
     * Relaciona un nuevo amigo con el conjunto de amigos del usuario en la base de datos SocialNetwork. 
     * Podra lanzar excepciones del tipo JSONException, InterDataBaseException, InterProcessException o InterEmailException.
     *
     * @param jsonFriendFacebook Nuevo amigo de Facebook.
     * @throws JSONException
     */
    public void updateRelationshipNewFriend(JSONObject jsonFriendFacebook) throws JSONException, InterDataBaseException, InterProcessException, InterEmailException{
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateRelationshipNewFriend: Inicio updateRelationshipNewFriend...");
        try {
            setCollectionFriendsFacebook(jsonFriendFacebook);
            logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateRelationshipNewFriend: Nuevo amigo " + jsonFriendFacebook.getString("userUid") + " relacionado en base de datos con " + this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid());
        } catch (UniformInterfaceException e) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateRelationshipNewFriend: Excepcion capturada UniformInterfaceException: " + e.getMessage());
            logger.fatal(e);
            this.snsObject.getExceptionManager().initControlException(e);
        }
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateRelationshipNewFriend: Fin updateRelationshipNewFriend...");
    }
    
        /**
     * Obtiene un array con todas las relacciones de un usuario de Facebook con
     * los usuarios de la aplicacion. Devolvera un array con todas las URIs de 
     * los usuarios de la aplicacion ya preparadas para ser insertadas en la base
     * de datos.
     * 
     * @param jsonFriend
     * @return JSONArray
     */
    private JSONArray getRelationshipFriendsFacebook(JSONObject jsonFriend) {
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getRelationshipFriendsFacebook: Inicio getRelationshipFriendsFacebook...");

        JSONArray jsonArrayRelationship = new JSONArray();

        try {
            String collection = this.snsObject.getClient().userFacebook_isNewFriendsFacebookByUid(String.class, jsonFriend.getString("userUid"));
            JSONObject jsonAux = new JSONObject(collection);
            JSONObject jsonCollection = jsonAux.getJSONObject("userFacebookCollection");

            try {
                //Si es un array
                jsonArrayRelationship = jsonCollection.getJSONArray("userFacebook");

                // Convertimos todas las URI del array al formato permitido
                jsonArrayRelationship = convertURIServerToURIUserFacebook(jsonArrayRelationship, jsonFriend.getString("userUid"));

            } catch (JSONException ex) {
                if (jsonCollection.length() == 1) {
                    // Si unicamente hay un usuario enlazado con este friendFacebook              
                    JSONObject jsonUserFacebook = jsonCollection.getJSONObject("userFacebook");
                    jsonArrayRelationship.put(jsonUserFacebook);

                    // Convertimos todas las URI del array al formato permitido
                    jsonArrayRelationship = convertURIServerToURIUserFacebook(jsonArrayRelationship, jsonFriend.getString("userUid"));
                }
            }
        } catch (JSONException ex) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getRelationshipFriendsFacebook: Excepcion capturada JSONException: " + ex.getMessage());
        }

        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getRelationshipFriendsFacebook: Fin getRelationshipFriendsFacebook...");
        return jsonArrayRelationship;
    }
    
    /**
     * Incluye a un amigo en la relacion con un usuario de Facebook.
     *
     * @param jsonFriend Datos de un amigo de Facebook.
     */
    public void setCollectionFriendsFacebook(JSONObject jsonFriend) {
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - setCollectionFriendsFacebook: Inicio setCollectionFriendsFacebook...");

        Long uidLong = (new Double(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid())).longValue();

        try {
            JSONArray jsonArrayRelationship = getRelationshipFriendsFacebook(jsonFriend);
            JSONObject jsonUri = new JSONObject();
            JSONObject jsonCollection = new JSONObject();

            // Introducimos la nueva URI del friendFacebook que no estaba relacionado con el usuario de Facebook
            jsonUri.put("uri", this.snsObject.getConfigurationManager().getConfigHostApplication() + "SNSdataBaseIntegratorServer/resources/friendsFacebooks/" + jsonFriend.getString("userUid") + "/userFacebookCollection/" + uidLong + "/");
            jsonArrayRelationship.put(jsonUri);

            jsonCollection.put("userFacebook", jsonArrayRelationship);
            jsonFriend.put("userFacebookCollection", jsonCollection);

            this.snsObject.getClient().userFacebook_setFriendsFacebookByUid(String.class, jsonFriend, jsonFriend.getString("userUid"));
        } catch (JSONException ex) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - setCollectionFriendsFacebook: Excepcion capturada JSONException: " + ex.getMessage());
        }
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - setCollectionFriendsFacebook: Fin setCollectionFriendsFacebook...");
    }
    
    /**
     * Establece las relaciones con otros usuarios de la aplicacion de un contacto de 
     * Facebook.
     * 
     * @param jsonFriend
     * @param jsonArrayRelationship 
     */
    public void updateCollectionFriendsFacebook(JSONObject jsonFriend, JSONArray jsonArrayRelationship) {
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateCollectionFriendsFacebook: Inicio updateCollectionFriendsFacebook...");

        try {
            JSONObject jsonUriUserFacebook = new JSONObject();
            jsonUriUserFacebook.put("userFacebook", jsonArrayRelationship);
            jsonFriend.put("userFacebookCollection", jsonUriUserFacebook);

            this.snsObject.getClient().userFacebook_setFriendsFacebookByUid(String.class, jsonFriend, jsonFriend.getString("userUid"));

        } catch (JSONException ex) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateCollectionFriendsFacebook: Excepcion capturada JSONException: " + ex.getMessage());
        }
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateCollectionFriendsFacebook: Fin updateCollectionFriendsFacebook...");
    }



    /**
     * Actualiza los datos de un amigo del usuario en base de datos.
     * Podra lanzar excepciones del tipo JSONException, InterDataBaseException, InterProcessException o InterEmailException.
     * 
     * @param jsonFriend
     * @throws JSONException
     * @throws InterDataBaseException
     * @throws InterProcessException
     * @throws InterEmailException 
     */
    public void updateFriend(JSONObject jsonFriend) throws JSONException, InterDataBaseException, InterProcessException, InterEmailException{
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateFriend: Inicio updateFriend para amigo " + jsonFriend.getString("userUid"));
        
        try {
            this.snsObject.getClient().userFacebook_setFriendsFacebookByUid(String.class, jsonFriend, jsonFriend.getString("userUid"));
        } catch (UniformInterfaceException ex) {
            if(ex.getResponse().getStatus() >= 200 && ex.getResponse().getStatus() < 300){
                logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateFriend: La informacion del amigo " + jsonFriend.getString("userUid") + " ha sido actualizada!!");
            }else{
                logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateFriend: No se ha podido actualizar la informacion del amigo: " + jsonFriend.getString("userUid"));
                logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateFriend: Excepcion capturada UniformInterfaceException: " + ex.getMessage());
                this.snsObject.getExceptionManager().initControlException(ex);
            }
        }
        
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateFriend: Fin updateFriend para amigo " + jsonFriend.getString("userUid"));
    }
    /**
     * Actualiza los datos de un amigo del usuario en la base de datos SocialNetwork. 
     * Podra lanzar excepciones del tipo JSONException, InterDataBaseException, InterProcessException o InterEmailException.
     *
     * @param jsonFriendFacebook Amigo de Facebook.
     * @throws JSONException
     */
    public void updateDatesFriend(JSONObject jsonFriend) throws JSONException, InterDataBaseException, InterProcessException, InterEmailException{
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateDatesFriend: Inicio updateDatesFriend...");

        // Recuperamos las relaciones con los usuarios de la aplicacion
        JSONArray jsonArrayRelationship = getRelationshipFriendsFacebook(jsonFriend);
        
        // Actualizamos el usuario de Facebook en la base de datos
        this.updateFriend(jsonFriend);
        try {     
            // Reestablecemos las relaciones del usuario de Facebook
            updateCollectionFriendsFacebook(jsonFriend, jsonArrayRelationship);
        } catch (UniformInterfaceException ex) {
            if (ex.getResponse().getStatus() >= 200 && ex.getResponse().getStatus() < 300) {
                logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateDatesFriend: Las relaciones con el amigo " + jsonFriend.getString("userUid") + " han sido actualizadas!!");
            } else {
                logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateDatesFriend: No se ha podido actualizar la informacion del amigo: " + jsonFriend.getString("userUid"));
                logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateDatesFriend: Excepcion capturada UniformInterfaceException: " + ex.getMessage());
                this.snsObject.getExceptionManager().initControlException(ex);
            }
        }

        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateDatesFriend: Fin updateDatesFriend...");
    }
    
        /**
     * Convierte un array de objetos JSON con URIs obtenidas desde servidor en
     * un array de objetos JSON con URIs preparadas para volver a ser relacionadas. Podr? lanzar excepciones del tipo JSONException.
     *
     * @param jsonArrayUserFacebook Array JSON de objetos de servidor.
     * @param strUidFriend UID del friendFacebook con la que se va a asociar.
     * @return Array JSON con los objetos preparados para ser relacionados.
     * @throws JSONException
     */
    public JSONArray convertURIServerToURIUserFacebook(JSONArray jsonArrayUserFacebook, String strUidFriend) throws JSONException{
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - convertURIServerToURIUserFacebook: Inicio convertURIServerToURIUserFacebook para amigo: " + strUidFriend);
        JSONArray jsonArrayNewUserFacebook = new JSONArray();

        for(int i = 0; i < jsonArrayUserFacebook.length(); i++){
            JSONObject jsonUriUserFacebook = jsonArrayUserFacebook.getJSONObject(i);
            String strUri = getURIConverted(jsonUriUserFacebook.getString("uri"), strUidFriend);

            jsonUriUserFacebook.put("uri", strUri);
            jsonArrayNewUserFacebook.put(jsonUriUserFacebook);
        }

        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - convertURIServerToURIUserFacebook: Fin convertURIServerToURIUserFacebook para amigo: " + strUidFriend);
        return jsonArrayNewUserFacebook;
    }
    
    
    /**
     * Convierte una URI obtenida para un friendFacebook desde servidor al
     * formato correcto para volver a ser introducida en la relacion.
     *
     * @param strURI URI obtenida de servidor.
     * @param strUidFriend UID del friendFacebook con la que se va a asociar.
     * @return URI asociada al friendFacebook.
     */
    public String getURIConverted(String strURI, String strUidFriend){
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getURIConverted: Reemplazando valor URI: " + strURI);
        strURI = strURI.replace("friendsFacebooks/userFacebookCollection/", "friendsFacebooks/" + strUidFriend + "/userFacebookCollection/");
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getURIConverted: Nuevo valor URI: " + strURI);

        return strURI;
    }
}
