/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.snsangelguardfb;

import java.util.Map;

/**
 * Clase que define el ciclo de vida de un filtro.
 * 
 * @author josejavierblecuadepedro1
 */
public interface ILifeCycleFilter {
   
    /**
     * Inicializa el filtro con la clase manager de la aplicacion.
     *
     * @param snsObject Objeto manater de la aplicacion.
     * @param id Identificador del filtro.
     */
    public void init(SNSAngelGuardFBManager snsObject, String id);
    
    /**
     * Ejecuta la funcionalidad del filtro.
     *
     * @param Mapa de par?metros de entrada.
     * @return Cadena de texto con el resultado del filtro en formato HTML
     * @throws Exception
     */
    public String executeFilter(Map<String, Object> args) throws Exception;
    
    /**
     * Obtiene toda la informacion interna de Facebook necesaria para el
     * funcionamiento del filtro y la almacena en base de datos.
     *
     * @throws Exception
     */
    public void getInformationFacebook() throws Exception;
    
    /**
     * Actualiza la informacion interna en base de datos para analizarla.
     * 
     * @throws Exception 
     */
    public void updateInformationFacebook() throws Exception;
    
    /**
     * Obtiene el identificador del filtro.
     * 
     * @return String 
     */
    public String getId();
}
