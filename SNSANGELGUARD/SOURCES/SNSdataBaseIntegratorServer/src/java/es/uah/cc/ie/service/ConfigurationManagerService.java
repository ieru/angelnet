/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Obtiene la configuración de la base de datos mediante su archivo pool.properties.
 * 
 * @author josejavierblecuadepedro1
 */
public class ConfigurationManagerService {

    /** Ruta al fichero de configuracion */
    private final String PATH_FILE_CONFIG = "/usr/local/snsangelguardfb/config/pool.properties";
    
    /** URL a la base de datos del fichero de configuracion */
    private final String FILE_URL = "url";
    
    /** Usuario de la base de datos del fichero de configuracion */
    private final String FILE_USER = "user";
    
    /** Usuario de la base de datos del fichero de configuracion */
    private final String FILE_PASS = "password";
    
    /** Usuario de la base de datos del fichero de configuracion */
    private final String FILE_DRIVER = "driver";
    
    /** Usuario de la base de datos del fichero de configuracion */
    private final String MAP_URL = "javax.persistence.jdbc.url";
    
    /** Usuario de la base de datos del fichero de configuracion */
    private final String MAP_USER = "javax.persistence.jdbc.user";
    
    /** Usuario de la base de datos del fichero de configuracion */
    private final String MAP_PASS = "javax.persistence.jdbc.password";
    
    /** Usuario de la base de datos del fichero de configuracion */
    private final String MAP_DRIVER = "javax.persistence.jdbc.driver";

    /** Mapa final con los atributos de conexion a la base de datos */
    private Map dbPool;
    
    /**
     * Obtiene el mapa de atributos de conexion de la base de datos.
     * 
     * @return HashMap 
     */
    public Map getDbPool() {
        return dbPool;
    }
    
    /**
     * Constructor de clase sin argumentos. Inicializa el mapa de atributos de 
     * conexion de la base de datos leyendo el fichero de configuracion 
     * pool.properties.
     */
    public ConfigurationManagerService() {
        loadConfigFile();
    }
    
    private void loadConfigFile() {
        this.dbPool = new HashMap();
        FileInputStream in;

        try {
            Properties defaultProps = new Properties();
            in = new FileInputStream(PATH_FILE_CONFIG);
            defaultProps.load(in);
            
            // Incluimos los parametros de configuracion en el mapa de atributos de conexion
            this.dbPool.put(MAP_URL, defaultProps.get(FILE_URL));
            this.dbPool.put(MAP_USER, defaultProps.get(FILE_USER));
            this.dbPool.put(MAP_PASS, defaultProps.get(FILE_PASS));
            this.dbPool.put(MAP_DRIVER, defaultProps.get(FILE_DRIVER));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConfigurationManagerService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConfigurationManagerService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
