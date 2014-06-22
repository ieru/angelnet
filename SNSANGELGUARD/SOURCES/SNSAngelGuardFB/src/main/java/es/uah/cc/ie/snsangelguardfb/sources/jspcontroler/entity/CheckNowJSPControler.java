package es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.entity;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.sun.jersey.api.client.UniformInterfaceException;
import es.uah.cc.ie.snsangelguardfb.exception.InterDataBaseException;
import es.uah.cc.ie.snsangelguardfb.exception.InterEmailException;
import es.uah.cc.ie.snsangelguardfb.exception.InterProcessException;
import es.uah.cc.ie.snsangelguardfb.SNSAngelGuardFBManager;
import es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.GenericJSPControler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Iterator;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

/**
 * Controla la ejecucion de la pagina checkNow.jsp
 * 
 * @author tote
 */
public class CheckNowJSPControler extends GenericJSPControler{

    /** Logger Class */
    private static Logger logger = Logger.getLogger(CheckNowJSPControler.class);
        
    /** Indicador de filtro activo */
    private final String KEY_JSON_ACTIVE_FILTER = "hdActive";
    
    /** Indicador de frecuencia del filtro */
    private final String KEY_JSON_FREC_FILTER = "hdFrec";
    
    /** Indicador de los angeles del filtro */
    private final String KEY_JSON_ANGELS_FILTER = "hdLstAngels";

    /** Clase Manager de la aplicacion */
    private SNSAngelGuardFBManager snsObject;

    /** Atributo request de la sesion */
    private HttpServletRequest request;

    /** Atributo response de la sesion */
    private HttpServletResponse response;

    public SNSAngelGuardFBManager getSnsObject() {
        return snsObject;
    }
    
    

   /**
    * Constructor de clase.
    *
    * @param snsObject SNSAngelGuardFBManager
    * @param request HttpServletRequest
    * @param response HttpServletResponse
    */
    public CheckNowJSPControler(HttpServletRequest request, HttpServletResponse response) throws InterDataBaseException, InterProcessException, InterEmailException {
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


    @Override
    public void process() throws InterDataBaseException, InterProcessException, InterEmailException {
        try {
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Inicio process...");

            this.snsObject.logSession(request, response);

            // Cargamos la informaci?n de los filtros en la aplicaci?n
            loadFilters();

            // Actualizamos la informaci?n del usuario y volvemos a la p?gina de configuraci?n
            updateInformationAndReturn();
        } catch (IOException | JSONException | UniformInterfaceException | MessagingException | MySQLIntegrityConstraintViolationException | InterDataBaseException | InterProcessException | InterEmailException e) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateInformationAndReturn: Excepcion capturada GenericException: " + e.getMessage());
            logger.fatal(e);
            this.snsObject.getExceptionManager().initControlException(e);
        }
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Fin process...");
    }

    /**
     * Actualiza la informacion de los filtros recogida de la interaccion con el usuario.
     */
    private void loadInfoFilters() {
        
        Iterator<String> itKeysFilters = this.snsObject.getConfigurationManager().getListActiveFilters().iterator();
        String keyFilter;
        
        while (itKeysFilters.hasNext()) {
            keyFilter = itKeysFilters.next();
            
            this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFilterDaoMap().get(keyFilter).setActive(request.getParameter(KEY_JSON_ACTIVE_FILTER + keyFilter));
            this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFilterDaoMap().get(keyFilter).setFrec(request.getParameter(KEY_JSON_FREC_FILTER + keyFilter));
            this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFilterDaoMap().get(keyFilter).setAngels(request.getParameter(KEY_JSON_ANGELS_FILTER + keyFilter));
            this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFilterDaoMap().get(keyFilter).setLastCheck(new Date());
        }
    }
    
    /**
     * Carga la informacion de los filtros obtenida de la pagina de configuracion
     * para, posteriormente, ser guardada en la base de datos. Podr? lanzar excepciones del tipo UnsupportedEncodingException.
     * 
     * @throws UnsupportedEncodingException 
     */
    private void loadFilters() throws UnsupportedEncodingException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - loadFilters: Inicio loadFilters...");
        request.setCharacterEncoding("UTF-8");
        HttpSession sesion = request.getSession(false);

        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - loadFilters: Amigos elegidos como angeles: " + sesion.getAttribute("hdAngels"));
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - loadFilters: Angeles seleccionados de Google: " + request.getParameter("hdAngelsGoogleSelected"));
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - loadFilters: Angeles ED: " + request.getParameter("hdAngelsEd"));

        // Cargamos la configuracion de los filtros
        loadInfoFilters();

        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - loadFilters: Fin loadFilters...");
    }

    /**
     * Obtiene un string con la activacion de los filtros separados por ";".
     * 
     * @return String 
     */
    private String getActiveFilters(){
        Iterator<String> itKeysFilters = this.snsObject.getConfigurationManager().getListActiveFilters().iterator();
        String keyFilter;
        String activeFilters = null;
        
        while (itKeysFilters.hasNext()) {
            keyFilter = itKeysFilters.next();
            
            activeFilters += this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFilterDaoMap().get(keyFilter).getActive() + ";";
        }
        
        // Le quitamos a la cadena el ?ltimo ";"
        if (activeFilters != null) {
            activeFilters = activeFilters.substring(0, activeFilters.length() - 1);
        }
        
        
        return activeFilters;
    }
    
    /**
     * Actualiza la informacion del usuario y vuelve a la pagina de configuracion de la aplicacion. Podra lanzar excepciones del
     * tipo UniformInterfaceException, IOException, JSONException, NoSuchProviderException, MessagingException, MySQLIntegrityConstraintViolationException, InterDataBaseException, InterProcessException o InterEmailException.
     * 
     * @throws UniformInterfaceException
     * @throws IOException
     * @throws JSONException
     * @throws NoSuchProviderException
     * @throws MessagingException
     * @throws MySQLIntegrityConstraintViolationException
     * @throws InterDataBaseException
     * @throws InterProcessException
     * @throws InterEmailException 
     */
    public void updateInformationAndReturn() throws UniformInterfaceException, IOException, JSONException, NoSuchProviderException, MessagingException, MySQLIntegrityConstraintViolationException, InterDataBaseException, InterProcessException, InterEmailException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateInformationAndReturn: Inicio updateInformationAndReturn...");

        snsObject.getUserSettingsDaoManager().getUserSettingsDAO().setAppActivated(true);
        snsObject.getUserSettingsDaoManager().getUserSettingsDAO().setLegalAccepted(true);
        snsObject.getUserSettingsDaoManager().getUserSettingsDAO().updateLastCheckUS();

        snsObject.getUserSettingsDaoManager().getUserSettingsDAO().setAngelsUserSettings(getActiveFilters(), snsObject.isNewConnection());

        snsObject.getUserSettingsDaoManager().getUserInfo(false);

        // Cerramos la conexi?n con la Base de Datos
        snsObject.getLocaleSettingsDaoManager().getSnsObject().cerrarConexionSNS();

        // Volvemos a la pagina de configuraci?n
        response.sendRedirect(request.getContextPath() + "/settingsSNSAngelGuard.jsp?newConection=1&ok=1");
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateInformationAndReturn: Fin updateInformationAndReturn...");
    }
}
