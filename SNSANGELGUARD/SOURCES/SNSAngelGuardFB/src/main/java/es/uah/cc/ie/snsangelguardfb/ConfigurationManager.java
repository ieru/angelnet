/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.snsangelguardfb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.openide.util.Exceptions;

/**
 * Carga los ficheros de configuracion y los almacena en parametros que seran 
 * utilizados por la clase SNSAngelGuardFBManager.
 * 
 * @author josejavierblecuadepedro1
 */
public class ConfigurationManager {
    
    /** Ruta al fichero de configuracion */
    private static final String PATH_FILE_CONFIG = "/usr/local/snsangelguardfb/config/config.properties";
    
    /** Ruta al fichero de filtros activos */
    private static final String PATH_FILE_FILTERS = "/usr/local/snsangelguardfb/config/filters.xml";
    
    /** Clave al host de la aplicacion en el fichero de configuracion */
    private static final String NAME_CONTEXT_APPLICATION = "configHostApplication";
    
    /** Clave SSL al host de la aplicacion en el fichero de configuracion */
    private static final String NAME_CONTEXT_APPLICATION_SSL = "configHostApplicationSSL";
    
    /** Clave al host de los servicios RESTFul en el fichero de configuracion */
    private static final String NAME_CONTEXT_RESTFULWS = "configHostRESTFullWS";
    
    /** Clave de aplicacion SNSAngelGuard en Facebook */
    public static final String API_KEY = "apiKey";

    /** Clave secreta de la aplicacion SNSAngelGuard en Facebook */
    public static final String API_SECRET_KEY = "apiSecretKey";
    
    /** Clave del path del certificado de confianza SSL */
    public static final String PATH_KEYSTORE_SSL = "pathKeyStoreSSL";
    
    /** Clave del password del certificado de confianza SSL */
    public static final String PASSWORD_KEYSTORE_SSL = "passwordKeyStoreSSL";
    
    /** Clave de la direccion de la aplicacion en Facebook */
    public static final String PATH_APPLICATION_FACEBOOK = "pathApplicationFacebook";
    /**
     * Clave de la direcci?n del servidor donde se encuentran los ficheros de
     * idioma referentes al control del lenguaje
     */
    public static final String PATH_LEXICAL_FILES = "pathLexicalFiles";
    
    /**
     * Key para el identificador del filtro en el fichero xml
     */
    private static final String XML_KEY_ID_FILTER = "idFilter";
    
    /**
     * Key para el valor de la clase del filtro
     */
    private static final String XML_VALUE_CLASS = "valueClass";
    
    /**
     * Key para el nombre de la aplicacion dada de alta en Google API Console
     */
    private static final String GOOGLE_APP_NAME_KEY = "googleAppName";
    
    /**
     * Key para el identificador de la aplicacion generado automaticamente al
     * dar de alta la apliacion en Google API Console
     */
    private static final String GOOGLE_APP_CLIENT_ID_KEY = "googleClientId";
    
    /**
     * Key para el identificador secreto de la aplicacion generado
     * automaticamanete al dar de alta la aplicacion en google API Console
     */
    private static final String GOOGLE_APP_CLIENT_SECRET_KEY = "googleClientSecret";
    
    /** Host de la aplicacion */
    private String configHostApplication;
    
    /** Host SSL de la aplicacion */
    private String configHostApplicationSSL;
    
    /** Host a los Servicios RESTFul */
    private String configHostRESTFullWS;
    
    /** Identificador de Facebook de la aplicacion */
    private String apiKey;
    
    /** Identificador secreto de Facebook de la aplicacion */
    private String apiSecretKey;
    
    /** Path al certificado de confianza SSL */
    private String pathKeyStoreSSL;
    
    /** Password del certificado de confianza SSL */
    private String passwordKeyStoreSSL;
    
    /** Path de la aplicacion en Facebook */
    private String pathApplicationFacebook;
    
    /** Path a los ficheros de idioma propios del filtro de control de lenguaje */
    private String pathLexicalFiles;
    
    /**
     * Nombre de la aplicacion dada de alta en Google API Console
     */
    private String googleAppName;
    
    /**
     * Identificador de la aplicacion generado automaticamente al dar de alta la
     * apliacion en Google API Console
     */
    private String googleClientId;
    
    /**
     * Identificador secreto de la aplicacion generado automaticamanete al dar
     * de alta la aplicacion en google API Console
     */
    private String googleClientSecret;
    
    /** Lista de keys de los filtros activos */
    private List<String> listActiveFilters;
    
    /** Mapa que contiene los filtros a crear */
    private Map<String, String> keyValueClassFilter;
   
    
    public String getConfigHostApplication() {
        return configHostApplication;
    }
    
    public String getConfigHostApplicationSSL() {
        return configHostApplicationSSL;
    }

    public String getConfigHostRESTFullWS() {
        return configHostRESTFullWS;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiSecretKey() {
        return apiSecretKey;
    } 

    public String getPathKeyStoreSSL() {
        return pathKeyStoreSSL;
    }

    public String getPasswordKeyStoreSSL() {
        return passwordKeyStoreSSL;
    }

    public String getPathApplicationFacebook() {
        return pathApplicationFacebook;
    }

    public String getPathLexicalFiles() {
        return pathLexicalFiles;
    }

    public void setPathLexicalFiles(String pathLexicalFiles) {
        this.pathLexicalFiles = pathLexicalFiles;
    }

    public String getGoogleAppName() {
        return googleAppName;
    }

    public String getGoogleClientId() {
        return googleClientId;
    }

    public String getGoogleClientSecret() {
        return googleClientSecret;
    }

    public List<String> getListActiveFilters() {
        return listActiveFilters;
    }

    public Map<String, String> getKeyValueClassFilter() {
        return keyValueClassFilter;
    }
    
    /**
     * Constructor de clase.
     * 
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public ConfigurationManager() throws FileNotFoundException, IOException {
        // Cargamos las propiedades de la configuracion
        loadConfigFile();
        
        // Cargamos la lista de filtros activos
        loadActiveFiltersFile();
    }
    
    
    /**
     * Carga el fichero de configuracion con los parametros necesarios.
     */
    private void loadConfigFile() throws FileNotFoundException, IOException{
        FileInputStream in = null;
        try {
            Properties defaultProps = new Properties();
            in = new FileInputStream(PATH_FILE_CONFIG);
            defaultProps.load(in);
            
            this.configHostApplication = defaultProps.getProperty(NAME_CONTEXT_APPLICATION);
            this.configHostApplicationSSL = defaultProps.getProperty(NAME_CONTEXT_APPLICATION_SSL);
            this.configHostRESTFullWS  = defaultProps.getProperty(NAME_CONTEXT_RESTFULWS);
            this.apiKey = defaultProps.getProperty(API_KEY);
            this.apiSecretKey = defaultProps.getProperty(API_SECRET_KEY);
            this.pathKeyStoreSSL = defaultProps.getProperty(PATH_KEYSTORE_SSL);
            this.passwordKeyStoreSSL = defaultProps.getProperty(PASSWORD_KEYSTORE_SSL);
            this.pathApplicationFacebook = defaultProps.getProperty(PATH_APPLICATION_FACEBOOK);
            this.pathLexicalFiles = defaultProps.getProperty(PATH_LEXICAL_FILES);
            this.googleAppName = defaultProps.getProperty(GOOGLE_APP_NAME_KEY);
            this.googleClientId = defaultProps.getProperty(GOOGLE_APP_CLIENT_ID_KEY);
            this.googleClientSecret = defaultProps.getProperty(GOOGLE_APP_CLIENT_SECRET_KEY);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }
    
    /**
     * Carga el fichero de los filtros activos.
     * 
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private void loadActiveFiltersFile() throws FileNotFoundException, IOException {
        // Inicializamos la lista de keys y el mapa de definci?n de los filtros
        this.listActiveFilters = new ArrayList<>();
        this.keyValueClassFilter = new HashMap<>();
        
        try {
            SAXBuilder builder = new SAXBuilder();
            
            File in = new File(PATH_FILE_FILTERS);
            
            Document document = builder.build(in);
            
            List listFilter = document.getRootElement().getChildren();
            
            if(listFilter != null && !listFilter.isEmpty()){
                
                Element currentFilter;
                String keyFilter;
                String valueClassFilter;
                
                Iterator<Element> it = listFilter.iterator();
                
                while(it.hasNext()){
                    currentFilter = it.next();
                    
                    // Obtenemos los valores del fichero xml para cada filtro
                    keyFilter = currentFilter.getAttributeValue(XML_KEY_ID_FILTER);
                    valueClassFilter = currentFilter.getAttributeValue(XML_VALUE_CLASS);
                    
                    // Introducimos el id en el fichero de keys
                    this.listActiveFilters.add(keyFilter);
                    
                    // Introducimos la definicion de la clase a crear junto con su clave
                    this.keyValueClassFilter.put(keyFilter, valueClassFilter);
                }
            }
        } catch (JDOMException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
