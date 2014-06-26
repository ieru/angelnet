/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.entity;

import es.uah.cc.ie.snsangelguardfb.SNSAngelGuardFBManager;
import es.uah.cc.ie.snsangelguardfb.exception.CodeException;
import es.uah.cc.ie.snsangelguardfb.exception.InterDataBaseException;
import es.uah.cc.ie.snsangelguardfb.exception.InterEmailException;
import es.uah.cc.ie.snsangelguardfb.exception.InterProcessException;
import es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.GenericJSPControler;
import es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.resources.SettingsSNSAngelGuardJSPControlerResourcesAngels;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Clase que controla el flujo de informacion en la pagina settingsSNSAngelGuard_Angels.jsp
 * 
 * @author josejavierblecuadepedro1
 */
public class SettingsSNSAngelGuardJSPControler_Angels extends GenericJSPControler {
    
    /** URL a la imagen de carga entre p?ginas */
    public final String PATH_IMAGE_LOADING = "https://snsangelguard.com/SNSAngelGuardFB/resources/load.gif";
    
    /** Indicador de filtro activo */
    private final String KEY_JSON_ACTIVE_FILTER = "hdActive";
    
    /** Indicador de frecuencia del filtro */
    private final String KEY_JSON_FREC_FILTER = "hdFrec";
    
    /** Indicador de los angeles del filtro */
    private final String KEY_JSON_ANGELS_FILTER = "hdLstAngels";
    
    /** Logger Class */
    private static Logger logger = Logger.getLogger(SettingsSNSAngelGuardJSPControler.class);
    
    /** Manager principal de la aplicacion */
    private SNSAngelGuardFBManager snsObject;
   
    /** Recursos de idioma de la pagina */
    private SettingsSNSAngelGuardJSPControlerResourcesAngels jspResources;
    
    /** Atributo request de la sesion */
    private HttpServletRequest request;
    
    /** Atributo response de la sesion */
    private HttpServletResponse response;
   
    /** Angeles elegidos */
    private String hdAngels;
    
    /** Angeles elegidos de Otros Contactos */
    private String hdAngelsEd;
    
    /** Angeles elegidos de contactos de Google */
    private String hdAngelsGoogleSelected;
    
    /** Objeto JSON que contiene la informacion de todos los filtros */
    private JSONObject jsonFiltersInfo;
    
    /** Array que contiene la lista de filtros activos */
    private String listActiveFilters;
    
    /** Lista de angeles auxiliar */
    private String hdAngelsAux;

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        SettingsSNSAngelGuardJSPControler_Angels.logger = logger;
    }

    public SNSAngelGuardFBManager getSnsObject() {
        return snsObject;
    }

    public void setSnsObject(SNSAngelGuardFBManager snsObject) {
        this.snsObject = snsObject;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public SettingsSNSAngelGuardJSPControlerResourcesAngels getJspResources() {
        return jspResources;
    }

    public void setJspResources(SettingsSNSAngelGuardJSPControlerResourcesAngels jspResources) {
        this.jspResources = jspResources;
    }

    public String getHdAngels() {
        return hdAngels;
    }

    public void setHdAngels(String hdAngels) {
        this.hdAngels = hdAngels;
    }

    public String getHdAngelsEd() {
        return hdAngelsEd;
    }

    public void setHdAngelsEd(String hdAngelsEd) {
        this.hdAngelsEd = hdAngelsEd;
    }

    public String getHdAngelsGoogleSelected() {
        return hdAngelsGoogleSelected;
    }

    public void setHdAngelsGoogleSelected(String hdAngelsGoogleSelected) {
        this.hdAngelsGoogleSelected = hdAngelsGoogleSelected;
    }
    
    public String getHdAngelsAux() {
        return hdAngelsAux;
    }

    public void setHdAngelsAux(String hdAngelsAux) {
        this.hdAngelsAux = hdAngelsAux;
    }

    public JSONObject getJsonFiltersInfo() {
        return jsonFiltersInfo;
    }

    public void setJsonFiltersInfo(JSONObject jsonFiltersInfo) {
        this.jsonFiltersInfo = jsonFiltersInfo;
    }

    public String getListActiveFilters() {
        return listActiveFilters;
    }

    public void setListActiveFilters(String listActiveFilters) {
        this.listActiveFilters = listActiveFilters;
    }
    
    /**
     * Constructor de clase. Podra lanzar excepciones del tipo InterDataBaseException, InterProcessException o InterEmailException.
     * 
     * @param request
     * @param response
     * @throws InterDataBaseException
     * @throws InterProcessException
     * @throws InterEmailException 
     */
    public SettingsSNSAngelGuardJSPControler_Angels(HttpServletRequest request, HttpServletResponse response) throws InterDataBaseException, InterProcessException, InterEmailException {
        try {    
            //Obtenemos la conexi?n a Facebook
            this.snsObject = SNSAngelGuardFBManager.getSessionInstance(request);
            snsObject.logSession(request, response);
            this.request = request;
            this.response = response;
            this.request.setCharacterEncoding("UTF-8");
        } catch (IOException ex) {
            logger.error(CodeException.IO_EXCEPTION, ex);
            this.snsObject.getExceptionManager().initControlException(ex);
        } catch (JSONException ex) {
            logger.error(CodeException.JSON_EXCEPTION, ex);
            this.snsObject.getExceptionManager().initControlException(ex);
        }
         
    }
    
    /**
     * Carga la informacion en un objeto JSON de todos los filtros a mostrar
     * cuando estamos en la navegacion entre pantallas.
     *
     * @throws JSONException
     */
    private void loadNotInitInfoFilters() throws JSONException {
        Iterator<String> itKeysFilters = this.snsObject.getConfigurationManager().getListActiveFilters().iterator();
        this.listActiveFilters = this.snsObject.getStringUtilities().arrayListToString(this.snsObject.getConfigurationManager().getListActiveFilters());
        jsonFiltersInfo = new JSONObject();
        String keyFilter;
       
        // Por cada filtro almacenamos su informacion en el JSON del objeto
        while(itKeysFilters.hasNext()) {
            
            keyFilter = itKeysFilters.next();
            
            JSONObject jsonFilter = new JSONObject();
            jsonFilter.put(KEY_JSON_ACTIVE_FILTER + keyFilter, request.getParameter(KEY_JSON_ACTIVE_FILTER + keyFilter));
            jsonFilter.put(KEY_JSON_FREC_FILTER + keyFilter, request.getParameter(KEY_JSON_FREC_FILTER + keyFilter));
            jsonFilter.put(KEY_JSON_ANGELS_FILTER + keyFilter, request.getParameter(KEY_JSON_ANGELS_FILTER + keyFilter));
            
            // Introducimos en el JSON del objeto las propiedades del filtro
            jsonFiltersInfo.put(keyFilter, jsonFilter);
        }
    }
    
    
    /**
     * Carga la informacion comun a los filtros.
     * 
     * @throws JSONException
     */
    private void loadInitInfoFilters() throws JSONException {
        Iterator<String> itKeysFilters = this.snsObject.getConfigurationManager().getListActiveFilters().iterator();
        this.listActiveFilters = this.snsObject.getStringUtilities().arrayListToString(this.snsObject.getConfigurationManager().getListActiveFilters());
        jsonFiltersInfo = new JSONObject();
        String keyFilter;
       
        // Por cada filtro almacenamos su informacion en el JSON del objeto
        while(itKeysFilters.hasNext()) {
            
            keyFilter = itKeysFilters.next();
            
            JSONObject jsonFilter = new JSONObject();
            jsonFilter.put(KEY_JSON_ACTIVE_FILTER + keyFilter, this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFilterDaoMap().get(keyFilter).getActive());
            jsonFilter.put(KEY_JSON_FREC_FILTER + keyFilter, this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFilterDaoMap().get(keyFilter).getFrec());
            
            if(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFilterDaoMap().get(keyFilter).getAngels() != null)
                jsonFilter.put(KEY_JSON_ANGELS_FILTER + keyFilter, this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFilterDaoMap().get(keyFilter).getAngels());
            else
                jsonFilter.put(KEY_JSON_ANGELS_FILTER + keyFilter, "");
            
            // Introducimos en el JSON del objeto las propiedades del filtro
            jsonFiltersInfo.put(keyFilter, jsonFilter);
        }
    }
    
    
    @Override
    public void process() throws InterDataBaseException, InterProcessException, InterEmailException {
        try {
            this.hdAngels = snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getAngelsUser("F");
            this.hdAngelsEd = snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getAngelsUser("O");
            this.hdAngelsGoogleSelected = snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getAngelsUser("G");
            this.hdAngelsAux = snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getAngelsUser("F");
            loadInitInfoFilters();

            if (!snsObject.isInicio()) {
                this.hdAngels = request.getParameter("hdAngels");
                this.hdAngelsEd = request.getParameter("hdAngelsEd");
                this.hdAngelsGoogleSelected = request.getParameter("hdAngelsGoogleSelected");
                loadNotInitInfoFilters();
                this.hdAngelsAux = request.getParameter("hdAngelsAux");
            }
        } catch (JSONException ex) {
            logger.error(CodeException.JSON_EXCEPTION, ex);
            this.snsObject.getExceptionManager().initControlException(ex);
        }
        
        loadResources();
    }
   
    
    /**
     * Carga los recursos de idioma.
     */
    public void loadResources(){
        // Cargamos los recursos de idioma
        this.jspResources = new SettingsSNSAngelGuardJSPControlerResourcesAngels(this.snsObject);
    }
    
}
