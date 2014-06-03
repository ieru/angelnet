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
import es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.resources.TutorialInicioJSPControlerResources;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

/**
 * Clase controladora de la JSP tutorialInicio.jsp
 * 
 * @author josejavierblecuadepedro1
 */
public class TutorialInicioJSPControler extends GenericJSPControler {
    
    /** Codigo interno de locale para el idioma castellano */
    public final String COD_LOCALE_SP = "00000002";

    /** Logger Class */
    private static Logger logger = Logger.getLogger(TutorialInicioJSPControler.class);
    
    /** Manager principal de la aplicacion */
    private SNSAngelGuardFBManager snsObject;
    
    /** Atributo request de la sesion */
    private HttpServletRequest request;
    
    /** Atributo response de la sesion */
    private HttpServletResponse response;
    
    /** Codigo de idioma */
    private String locale;
    
    /** Recursos de idioma */
    private TutorialInicioJSPControlerResources resources;

    /**
     * Obtiene el logger del sistema.
     * 
     * @return Logger
     */
    public static Logger getLogger() {
        return logger;
    }

    /**
     * Obtiene el objeto manager de la sesi?n del usuario.
     * 
     * @return SNSAngelGuardFBManager
     */
    public SNSAngelGuardFBManager getSnsObject() {
        return snsObject;
    }

    /**
     * Obtiene el objeto HttpServletRequest de la sesi?n.
     * 
     * @return HttpServletRequest
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * Obtiene el objeto HttpServletResponse de la sesi?n.
     * 
     * @return HttpServletResponse
     */
    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * Obtiene el c?digo de locale del usuario.
     * 
     * @return String
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Obtiene los recursos de idioma de la p?gina.
     * 
     * @return TutorialInicioJSPControlerResources 
     */
    public TutorialInicioJSPControlerResources getResources() {
        return resources;
    }

    /**
     * Constructor de la clase.
     * 
     * @param request
     * @param response
     * @throws InterDataBaseException
     * @throws InterProcessException
     * @throws InterEmailException 
     */
    public TutorialInicioJSPControler(HttpServletRequest request, HttpServletResponse response) throws InterDataBaseException, InterProcessException, InterEmailException {
        try {
            // Cargamos el objeto manager de la aplicacion
            this.snsObject = SNSAngelGuardFBManager.getSessionInstance(request);
            request.setCharacterEncoding("UTF-8");
            this.request = request;
            this.response = response;
            this.locale = getCodLocale();

            // Cargamos el log
            this.snsObject.logSession(request, response);
        } catch (FileNotFoundException ex) {
            logger.error(CodeException.UKNOWN_ERROR, ex);
            this.snsObject.getExceptionManager().initControlException(ex);
        } catch (IOException | JSONException ex) {
            logger.error(CodeException.IO_EXCEPTION, ex);
            this.snsObject.getExceptionManager().initControlException(ex);
        }
    }
    
    /**
     * M?todo que obtiene el c?digo del idioma del usuario para cargar sus
     * descripciones. Por defecto, siempre devolver? el c?digo de locale en
     * ingl?s. Si el usuario tiene idioma castellano, devolver? el locale de
     * Espa?a.
     * 
     * @return Codigo de Locale 
     */
    private String getCodLocale(){
        String idLocale = this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getLocaleSettings();
        String codLocale = "en";
        
        if(idLocale.equals(COD_LOCALE_SP)){
            codLocale = "es";
        }
        
        return codLocale;
    }
    
    @Override
    public void process() throws InterDataBaseException, InterProcessException, InterEmailException {
        // Cargamos los recursos de la clase.
        loadResources();
    }
    
    /**
     * Metodo que carga todos los recursos de la p?gina.
     */
    private void loadResources(){
        this.resources = new TutorialInicioJSPControlerResources(this.snsObject);
    }
}
