/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.snsangelguardfb.sources.filtersfuncionality;

/**
 * Interface de definicion de acceso a los parametros de entrada de los filtros.
 * 
 * @author josejavierblecuadepedro1
 */
public interface IKeyArgsFilter {
   
    /** Key de la request de entrada */
    public static final String ARGS_KEY_REQUEST = "request";
    
    /** Key de los parametros del filtro en formato JSON */
    public static final String ARGS_KEY_JSONFILTER = "jsonFilter";
    
    /** Key de la definicion del filtro */
    public static final String ARGS_KEY_DESFILTER = "desFilter";
    
    /** Key de la fecha del primer chequeo */
    public static final String ARGS_KEY_FIRSTCHECK = "firstCheck";
    
    /** Key de la fecha del ultimo chequeo */
    public static final String ARGS_KEY_LASTCHECK = "lastCheck";
}
