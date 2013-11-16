/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.entity;

import bsh.ParseException;
import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultLegacyFacebookClient;
import com.sun.jersey.api.client.UniformInterfaceException;
import es.uah.cc.ie.snsangelguardfb.SNSAngelGuardFBManager;
import es.uah.cc.ie.snsangelguardfb.exception.CodeException;
import es.uah.cc.ie.snsangelguardfb.exception.InterDataBaseException;
import es.uah.cc.ie.snsangelguardfb.exception.InterEmailException;
import es.uah.cc.ie.snsangelguardfb.exception.InterProcessException;
import es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.GenericJSPControler;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

/**
 * Obtiene los datos de conexion del usuario y reparte el flujo de ejecucion de
 * la aplicacion en funcion de si el usuario es nuevo en la aplicacion o no.
 * 
 * @author josejavierblecuadepedro1
 */
public class SchedulerUserLoggedFacebookJSPControler extends GenericJSPControler {
    
    /** Logger class  */
    private static Logger logger = Logger.getLogger(SchedulerUserLoggedFacebookJSPControler.class);
    
    /** Manager de la aplicacion */
    private SNSAngelGuardFBManager snsObject;
    
    /** Atributo request de la sesion */
    private HttpServletRequest request;
    
    /** Atributo response de la sesion */
    private HttpServletResponse response;
        
    /** URL de destino de la pagina */
    private String pathDestino;
    
    /** Mensaje loading de guardado */
    private String loaderSave;
    
    /** Mensaje loading de espera */
    private String loaderWait;

    /**
     * Constructor de clase.
     * 
     * @param request
     * @param response 
     */
    public SchedulerUserLoggedFacebookJSPControler(HttpServletRequest request, HttpServletResponse response) throws InterDataBaseException, InterProcessException, InterEmailException {
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
     * Obtiene el objeto manager de la sesion.
     * 
     * @return SNSAngelGuardFBManager
     */
    public SNSAngelGuardFBManager getSnsObject() {
        return snsObject;
    }
    
    /**
     * Obtiene el path destino de la siguiente pagina.
     * 
     * @return String 
     */
    public String getPathDestino() {
        return pathDestino;
    }

    /**
     * Obtiene el mensaje de guardado del loader.
     * 
     * @return String 
     */
    public String getLoaderSave() {
        return loaderSave;
    }

    /**
     * Obtiene el mensaje de espera del loader.
     * 
     * @return String 
     */
    public String getLoaderWait() {
        return loaderWait;
    }

    /**
     * Lee la url que obtiene la accessToken ampliada.
     * 
     * @param url
     * @return
     * @throws IOException 
     */
    private String readURL(URL url) throws IOException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - readURL: Inicio readURL...");
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = url.openStream();
        int r;
        while ((r = is.read()) != -1) {
            baos.write(r);
        }
        
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - readURL: Fin readURL!!");
        return new String(baos.toByteArray());
    }
    
    /**
     * Metodo privado que amplia la accessToken a un rango temporal mayor. Necesario para accesos offline.
     * 
     * @param accessToken
     * @return
     * @throws InterDataBaseException
     * @throws InterProcessException
     * @throws InterEmailException 
     */
    private String getExtendedTokenFacebook(String accessToken) throws InterDataBaseException, InterProcessException, InterEmailException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getExtendedTokenFacebook: Inicio getExtendedTokenFacebook...");
        Integer expires = null;
        String newAccessToken = null;
            
        try {
            String urlBaseToken = "https://graph.facebook.com/oauth/access_token?";

            urlBaseToken = urlBaseToken + "client_id=" + this.snsObject.getConfigurationManager().getApiKey() + "&"
                    + "client_secret=" + this.snsObject.getConfigurationManager().getApiSecretKey() + "&"
                    + "grant_type=fb_exchange_token" + "&"
                    + "fb_exchange_token=" + accessToken;
            URL urlFacebookExtendedToken = new URL(urlBaseToken);
            String result = readURL(urlFacebookExtendedToken);

            String[] pairs = result.split("&");
            for (String pair : pairs) {
                String[] kv = pair.split("=");
                if (kv.length != 2) {
                    logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid()+ " - getExtendedTokenFacebook: Unexpected auth response");
                    
                    throw new InterProcessException(new Exception(), CodeException.ERROR_NO_AUTH_ACCESSTOKEN);
                }
                if (kv[0].equals("access_token")) {
                    newAccessToken = kv[1];
                    logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getExtendedTokenFacebook: Nueva accessToken obtenida: " + newAccessToken);
                }
                if (kv[0].equals("expires")) {
                    expires = Integer.parseInt(kv[1]);
                    Calendar calendar = Calendar.getInstance(); // gets a calendar using the default time zone and locale.
                    calendar.add(Calendar.SECOND, expires);
                    
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                    logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getExtendedTokenFacebook: La nueva accessToken expira el " + sdf.format(calendar.getTime()));  
                }
            }
        } catch (MalformedURLException ex) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid()+ " - getExtendedTokenFacebook: Excepcion capturada " + ex.getClass().getName() + ": " + ex.getMessage());
            this.snsObject.getExceptionManager().initControlException(ex);
        } catch (IOException ex) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid()+ " - getExtendedTokenFacebook: Excepcion capturada " + ex.getClass().getName() + ": " + ex.getMessage());
            this.snsObject.getExceptionManager().initControlException(ex);
        } 
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getExtendedTokenFacebook: Fin getExtendedTokenFacebook!!");
        
        return newAccessToken;
    }

    @Override
    public void process() throws InterDataBaseException, InterProcessException, InterEmailException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Inicio process...");  
        
        try {
            HttpSession session = request.getSession(false);
            request.setCharacterEncoding("UTF-8");
            
            // Obtenemos los datos de conexion del usuario
            String uid = this.request.getParameter("uid");
            String accessToken = this.request.getParameter("accessToken");
            
            // Obtenemos un nuevo AccessToken ampliado para conexiones offline
            accessToken = getExtendedTokenFacebook(accessToken);
            
            // Establecemos los parametros del usuario en la sesion
            session.setAttribute("user_id", uid);
            session.setAttribute("oauth_token", accessToken);
            
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Usuario conectado.");
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: UID: " + uid);
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: AccessToken: " + accessToken);
            
            // Establecemos los datos en el objeto de su sesion
            this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().setUid(uid);
            this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().setUserSession(accessToken);

            // Inicializamos los objetos de consultas de Facebook en el objeto de sesion
            this.snsObject.setFacebookRestClient(new DefaultLegacyFacebookClient(accessToken));
            this.snsObject.setFacebookQueryClient(new DefaultFacebookClient(accessToken));
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Objetos de consulta API Facebook inicializados!!");
            
            // Cargamos los recursos de idioma
            this.snsObject.getUserSettingsDaoManager().loadSettings();

            String[] strBtn = this.snsObject.getStringUtilities().stringToArray(this.snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getWarnings());
            this.loaderSave = strBtn[3];
            this.loaderWait = strBtn[4];

            if (this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() != null) {
                if (this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().isLegalAccepted()) {
                    logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Usuario exitente en la aplicacion.");
                    // Actualizamos la user_token junto con la hora de entrada a la aplicacion.
                    this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().updateLastCheckUS();
                    this.pathDestino = "/settingsSNSAngelGuard.jsp?newConection=1&ok=0";
                } else {
                    // Si es un usuario nuevo, vamos a la pagina del acuerdo legal
                    logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Nuevo usuario de la aplicacion.");
                    this.pathDestino = "/legalAccepted.jsp";
                }
                response.sendRedirect(request.getContextPath() + this.pathDestino);
            }
        } catch (ParseException | JSONException | UniformInterfaceException | IOException ex) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid()+ " - process: Excepcion capturada " + ex.getClass().getName() + ": " + ex.getMessage());
            this.snsObject.getExceptionManager().initControlException(ex);
        }
        
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - process: Fin process...");
    }
    
}
