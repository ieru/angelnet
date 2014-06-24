/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.entity;

import bsh.ParseException;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.sun.jersey.api.client.UniformInterfaceException;
import es.uah.cc.ie.snsangelguardfb.exception.CodeException;
import es.uah.cc.ie.snsangelguardfb.exception.InterDataBaseException;
import es.uah.cc.ie.snsangelguardfb.exception.InterEmailException;
import es.uah.cc.ie.snsangelguardfb.exception.InterProcessException;
import es.uah.cc.ie.snsangelguardfb.SNSAngelGuardFBManager;
import es.uah.cc.ie.snsangelguardfb.sources.dao.entity.UserSettings_SettingsFilterDAO;
import es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.GenericJSPControler;
import es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.resources.SettingsSNSAngelGuardJSPControlerResources;
import java.io.IOException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.Iterator;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

/**
 * Clase que controla la ejecucion de la pagina principal de la aplicacion.
 * 
 * @author tote
 */
public class SettingsSNSAngelGuardJSPControler extends GenericJSPControler {
    
    /** Logger Class */
    private static Logger logger = Logger.getLogger(SettingsSNSAngelGuardJSPControler.class);

    /** Valor por defecto para el activado del filtro */
    private final static String FILTER_DEFAULT_ACTIVE_VALUE = "0";
    
    /** Valor por defecto para los angeles de un filtro */
    private final static String FILTER_DEFAULT_ANGELS_VALUE = "";
    
    /** Valor por defecto para la frecuencia de un filtro */
    private final static String FILTER_DEFAULT_FREC_VALUE = "3";
    
    /** Manager principal de la aplicacion */
    private SNSAngelGuardFBManager snsObject;
    
    /** Recursos de la pagina */
    private SettingsSNSAngelGuardJSPControlerResources jspResources;
    
    /** Atributo request de la sesion */
    private HttpServletRequest request;
    
    /** Atributo response de la sesion */
    private HttpServletResponse response;
    
    /** Indicador de nueva conexion */
    private String newConection;
    
    /** Resultado de la operacion */
    private String resultSave;
    
    /** Indicador de retorno para la ventana modal */
    private String modal;

    /** En el caso del guardado de informacion de un angel */
    private String typeAngel;
    
    /** Identificador en Facebook del angel guardado */
    private String idFacebookAngel;
    
    /** Identificador en base de datos de un angel */
    private String uidAngel;
    
    /** Clave publica del usuario */
    private String uidPublic;
    
    /** Angeles del usuario */
    private String[][] angels;

    public SNSAngelGuardFBManager getSnsObject() {
        return snsObject;
    }

    public SettingsSNSAngelGuardJSPControlerResources getJspResources() {
        return jspResources;
    }

    public String getNewConection() {
        return newConection;
    }

    public String getResultSave() {
        return resultSave;
    }

    public String getModal() {
        return modal;
    }

    public String[][] getAngels() {
        return angels;
    }

    /**
     * Obtiene el tipo de angel que se ha guardado.
     * 
     * @return String  
     */
    public String getTypeAngel() {
        return typeAngel;
    }

    /**
     * Identificador en Facebook del angel guardado.
     * 
     * @return String 
     */
    public String getIdFacebookAngel() {
        return idFacebookAngel;
    }
    
    /**
     * Obtiene el identificador en base de datos del angel que se ha guardado.
     * 
     * @return String 
     */
    public String getUidAngel() {
        return uidAngel;
    }

    /**
     * Identificador publico del usuario.
     * 
     * @return String 
     */
    public String getUidPublic() {
        return uidPublic;
    }
    
    /**
     * Constructor de clase. Al obtener par?metros del objeto request, podr? lanzar excepciones del tipo InterDataBaseException, InterProcessException o InterEmailException.
     * @param request
     * @param response
     * @throws InterDataBaseException
     * @throws InterProcessException
     * @throws InterEmailException 
     */
    public SettingsSNSAngelGuardJSPControler(HttpServletRequest request, HttpServletResponse response) throws InterDataBaseException, InterProcessException, InterEmailException{
        try {
            // Cargamos el objeto manager de la aplicacion
            this.snsObject = SNSAngelGuardFBManager.getSessionInstance(request);
            request.setCharacterEncoding("UTF-8");
            this.request = request;
            this.response = response;
            
            // Cargamos el log
            this.snsObject.logSession(request, response);
            this.newConection = request.getParameter("newConection");
            this.resultSave = request.getParameter("ok");
            this.modal = request.getParameter("modal");
            this.angels = null;
            this.typeAngel = request.getParameter("typeAngel");
            this.idFacebookAngel = request.getParameter("idFacebookAngel");
            this.uidAngel = request.getParameter("uidAngel");
            this.uidPublic = request.getParameter("uidPublic");
        } catch (Exception ex) {
            logger.error(CodeException.UKNOWN_ERROR, ex);
            this.snsObject.getExceptionManager().initControlException(ex);
        }
    }

    @Override
    public void process() throws InterDataBaseException, InterProcessException, InterEmailException {
        
        try {
            if (this.newConection.equals("0")) {
                // Nuevo usuario en la aplicacion
                this.snsObject.setInicio(true);
                String uid = this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid();

                Long uidLong = (new Double(uid)).longValue();

                Iterator<String> itKeyFilters = this.snsObject.getConfigurationManager().getListActiveFilters().iterator();
                String keyFilter;
               /** UserSettings_SettingsFilterDAO newFilter;
                
                while (itKeyFilters.hasNext()) {
                    keyFilter = itKeyFilters.next();
                    
                    // Seteamos en cada filtro los valores por defecto
                    newFilter = this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFilterDaoMap().get(keyFilter);
                    newFilter.setActive(FILTER_DEFAULT_ACTIVE_VALUE);
                    newFilter.setAngels(FILTER_DEFAULT_ANGELS_VALUE);
                    newFilter.setFrec(FILTER_DEFAULT_FREC_VALUE);
                    newFilter.setLastCheck(new Date());
                }*/

                this.snsObject.setInicio(true);

                this.angels = this.snsObject.getAngelsUtilities().getAngels();
                this.snsObject.getAngelsUtilities().setArrayAngels(this.angels);
                
                // Cargamos los recursos de idioma
                loadResources();

                this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().setAppActivated(true);
                this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().setLegalAccepted(true);
                this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().setLastCheck(new Date());
                this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().setBackupCheck(new Date());
                this.snsObject.getUserSettingsDaoManager().putNewInstanceUS(this.request, this.response);
                this.snsObject.getUserSettingsDaoManager().getUserInfo(true);


            } else {
                // Cargamos la pagina con la informacion de la base de datos
                // Cargamos la configuraci?n en pantalla despues de acceder a la DB
                // METODOS PARA CARGAR LA CONFIGURACION RECUPERADA
                this.snsObject.setInicio(true);
                //this.snsObject.getFriendsFilterFuncionality().getUserFriends();
                Long uidLong = (new Double(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid())).longValue();
                this.snsObject.getClient().userFacebook_getUserFacebookByUid(String.class, uidLong.toString());
                JSONArray jsonFriendsFacebook = this.snsObject.getUserSettingsDaoManager().getFacebookFriendsDB(uidLong.toString());
                this.angels = snsObject.getAngelsUtilities().getAngelsDB(jsonFriendsFacebook);
                this.snsObject.getAngelsUtilities().setArrayAngels(this.angels);
                
                // Cargamos los recursos de idioma
                loadResources();
            }
        } catch (NumberFormatException | UniformInterfaceException | IOException | JSONException | ParseException | DataLengthException | IllegalStateException | InvalidCipherTextException | NoSuchProviderException | MessagingException | InterDataBaseException | InterProcessException | InterEmailException | MySQLIntegrityConstraintViolationException ex) {
            logger.error(CodeException.UKNOWN_ERROR, ex);
            this.snsObject.getExceptionManager().initControlException(ex);
        } 
    }

    /**
     * Carga los recursos de idioma.
     */
    public void loadResources(){
        // Cargamos los recursos de idioma
        this.jspResources = new SettingsSNSAngelGuardJSPControlerResources(this.snsObject, this.snsObject.getStringUtilities().arrayToString(this.angels));
    }
}
