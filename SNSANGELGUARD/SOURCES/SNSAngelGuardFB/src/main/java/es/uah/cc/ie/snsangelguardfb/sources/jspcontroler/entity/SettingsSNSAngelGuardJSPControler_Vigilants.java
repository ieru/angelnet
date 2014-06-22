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
import es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.resources.SettingsSNSAngelGuardJSPControlerResourcesVigilants;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Clase que controla el flujo de informacion en la pagina settingsSNSAngelGuard_Vigilants.jsp
 * 
 * @author josejavierblecuadepedro1
 */
public class SettingsSNSAngelGuardJSPControler_Vigilants extends GenericJSPControler{
    
    /** Logger Class */
    private static Logger logger = Logger.getLogger(SettingsSNSAngelGuardJSPControler.class);
    
    /** URL a la imagen de carga entre p?ginas */
    public final String PATH_IMAGE_LOADING = "https://snsangelguard.com/SNSAngelGuardFB/resources/load.gif";
    
    /** Indicador de filtro activo */
    private final String KEY_JSON_ACTIVE_FILTER = "hdActive";
    
    /** Indicador de frecuencia del filtro */
    private final String KEY_JSON_FREC_FILTER = "hdFrec";
    
    /** Indicador de los angeles del filtro */
    private final String KEY_JSON_ANGELS_FILTER = "hdLstAngels";
    
    /** Indicador de la decodificacion */
    private final String KEY_DECODE_UTF8 = "UTF-8";
    
    /** Manager principal de la aplicacion */
    private SNSAngelGuardFBManager snsObject;
    
    /** Recursos de idioma */
    private SettingsSNSAngelGuardJSPControlerResourcesVigilants jspResources;
    
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
    private String[] listActiveFilters;
    
    /** Lista de angeles auxiliar */
    private String hdAngelsAux;
    
    /** Array bidimensional de angeles */
    private String[][] arrayAngels;

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        SettingsSNSAngelGuardJSPControler_Vigilants.logger = logger;
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

    public SettingsSNSAngelGuardJSPControlerResourcesVigilants getJspResources() {
        return jspResources;
    }

    public void setJspResources(SettingsSNSAngelGuardJSPControlerResourcesVigilants jspResources) {
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

    public String[][] getArrayAngels() {
        return arrayAngels;
    }

    public void setArrayAngels(String[][] arrayAngels) {
        this.arrayAngels = arrayAngels;
    }

    public JSONObject getJsonFiltersInfo() {
        return jsonFiltersInfo;
    }

    public void setJsonFiltersInfo(JSONObject jsonFiltersInfo) {
        this.jsonFiltersInfo = jsonFiltersInfo;
    }

    public String[] getListActiveFilters() {
        return listActiveFilters;
    }

    public void setListActiveFilters(String[] listActiveFilters) {
        this.listActiveFilters = listActiveFilters;
    }
    
    /**
     * Constructor de clase. Podra lanzar excepciones del tipo InterDataBaseException, InterEmailException o InterProcessException.
     * @param request
     * @param response
     * @throws InterDataBaseException
     * @throws InterEmailException
     * @throws InterProcessException 
     */
    public SettingsSNSAngelGuardJSPControler_Vigilants(HttpServletRequest request, HttpServletResponse response) throws InterDataBaseException, InterEmailException, InterProcessException {
        try {
            this.snsObject = SNSAngelGuardFBManager.getSessionInstance(request);
            this.snsObject.logSession(request, response);
            request.setCharacterEncoding("UTF-8");
            this.snsObject.setInicio(false);
            
            this.request = request;
            this.response = response;
        } catch (IOException ex) {
            logger.error(CodeException.IO_EXCEPTION, ex);
            this.snsObject.getExceptionManager().initControlException(ex);
        } catch (JSONException ex) {
            logger.error(CodeException.JSON_EXCEPTION, ex);
            this.snsObject.getExceptionManager().initControlException(ex);
        }
    }

    
    
    @Override
    public void process() throws InterDataBaseException, InterProcessException, InterEmailException {
        try {
            this.hdAngels = URLDecoder.decode(request.getParameter("hdAngels"),"UTF-8");
            this.hdAngelsEd = URLDecoder.decode(request.getParameter("hdAngelsEd"),"UTF-8");
            this.hdAngelsAux = URLDecoder.decode(request.getParameter("hdAngelsAux"),"UTF-8");
            this.hdAngelsGoogleSelected = URLDecoder.decode(request.getParameter("hdAngelsGoogleSelected"),"UTF-8");
            loadNotInitInfoFilters();
            this.snsObject.getAngelsUtilities().getAngelsSelected(hdAngels);
            this.snsObject.getAngelsUtilities().joinAngels(hdAngelsGoogleSelected,hdAngelsEd);
            this.arrayAngels = snsObject.getAngelsUtilities().getArrayAngelsSelected();
            
            loadResources();
        } catch (UnsupportedEncodingException ex) {
            logger.error(CodeException.UNSUPPORTED_ENCODING, ex);
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
    private void loadNotInitInfoFilters() throws JSONException, UnsupportedEncodingException {
        Iterator<String> itKeysFilters = this.snsObject.getConfigurationManager().getListActiveFilters().iterator();
        this.listActiveFilters = new String[this.snsObject.getConfigurationManager().getListActiveFilters().size()];
        jsonFiltersInfo = new JSONObject();
        String keyFilter;
        int cont = 0;
       
        // Por cada filtro almacenamos su informacion en el JSON del objeto
        while(itKeysFilters.hasNext()) {
            
            keyFilter = itKeysFilters.next();
            
            // A?adimos la key en la lista de filtros activos
            this.listActiveFilters[cont] = keyFilter;
            
            JSONObject jsonFilter = new JSONObject();
            jsonFilter.put(KEY_JSON_ACTIVE_FILTER + keyFilter, URLDecoder.decode(request.getParameter(KEY_JSON_ACTIVE_FILTER + keyFilter), KEY_DECODE_UTF8));
            jsonFilter.put(KEY_JSON_FREC_FILTER + keyFilter, URLDecoder.decode(request.getParameter(KEY_JSON_FREC_FILTER + keyFilter), KEY_DECODE_UTF8));
            jsonFilter.put(KEY_JSON_ANGELS_FILTER + keyFilter, URLDecoder.decode(request.getParameter(KEY_JSON_ANGELS_FILTER + keyFilter), KEY_DECODE_UTF8));
            
            // Introducimos en el JSON del objeto las propiedades del filtro
            jsonFiltersInfo.put(keyFilter, jsonFilter);
            
            // Incrementamos el contador
            cont++;
        }
    }
    
    /**
     * Carga los recursos de idioma.
     */
    public void loadResources(){
        // Cargamos los recursos de idioma
        this.jspResources = new SettingsSNSAngelGuardJSPControlerResourcesVigilants(this.snsObject);
    }
}
