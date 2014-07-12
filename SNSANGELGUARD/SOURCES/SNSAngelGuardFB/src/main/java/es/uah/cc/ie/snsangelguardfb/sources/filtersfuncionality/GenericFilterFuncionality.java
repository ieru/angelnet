/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.snsangelguardfb.sources.filtersfuncionality;

import es.uah.cc.ie.snsangelguardfb.ILifeCycleFilter;
import es.uah.cc.ie.snsangelguardfb.exception.InterDataBaseException;
import es.uah.cc.ie.snsangelguardfb.exception.InterEmailException;
import es.uah.cc.ie.snsangelguardfb.exception.InterProcessException;
import es.uah.cc.ie.snsangelguardfb.SNSAngelGuardFBManager;
import es.uah.cc.ie.snsangelguardfb.sources.dao.entity.UserSettingsDAO;
import static es.uah.cc.ie.snsangelguardfb.sources.filtersfuncionality.IKeyArgsFilter.ARGS_KEY_DESFILTER;
import static es.uah.cc.ie.snsangelguardfb.sources.filtersfuncionality.IKeyArgsFilter.ARGS_KEY_FIRSTCHECK;
import static es.uah.cc.ie.snsangelguardfb.sources.filtersfuncionality.IKeyArgsFilter.ARGS_KEY_JSONFILTER;
import static es.uah.cc.ie.snsangelguardfb.sources.filtersfuncionality.IKeyArgsFilter.ARGS_KEY_LASTCHECK;
import static es.uah.cc.ie.snsangelguardfb.sources.filtersfuncionality.IKeyArgsFilter.ARGS_KEY_REQUEST;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

/**
 * Controla la ejecucion de todos los filtros definidos en la aplicacion.
 *
 * @author tote
 */
public class GenericFilterFuncionality implements IKeyArgsFilter {

    /** Logger Class */
    private static Logger logger = Logger.getLogger(GenericFilterFuncionality.class);

    /** Manager de la aplicacion */
    private SNSAngelGuardFBManager snsObject;
    
    /** Mapa que contiene los filtros activos */
    private Map<String, ILifeCycleFilter> filterActiveMap;

    /**
     * Constructor de clase
     * 
     * @param snsObject: Clase Manager de la aplicacion
     */
    public GenericFilterFuncionality(SNSAngelGuardFBManager snsObject) {
        this.snsObject = snsObject;
        try {
            loadMapFilters();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - GenericFilterFuncionality: Excepcion capturada Exception: " + ex.getMessage());
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - GenericFilterFuncionality: No se ha podido cargar el mapa de los filtros");
        }
    }

    /**
     * Obtiene el objeto manager de la aplicaci?n.
     *
     * @return SNSAngelGuardFBManager
     */
    public SNSAngelGuardFBManager getSnsObject() {
        return snsObject;
    }

    /**
     * Establece en el objeto actual el objeto manager de la aplicaci?n.
     *
     * @param snsObject SNSAngelGuardFBManager Manager de la aplicaci?n.
     */
    public void setSnsObject(SNSAngelGuardFBManager snsObject) {
        this.snsObject = snsObject;
    }

    /**
     * Obtiene el mapa de filtros.
     * 
     * @return Map<String, ILifeCycleFilter>
     */
    public Map<String, ILifeCycleFilter> getFilterActiveMap() {
        return filterActiveMap;
    }

    /**
     * Comprueba si el usuario tiene activo el filtro desFilter
     *
     * @param desFilter: Tipo de filtro a comprobar
     * @return
     *  - true: Si el filtro est? activo
     *  - false: Si el filtro no est? activo
     */
    public boolean isActiveFilter(String desFilter) {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isActiveFilter: Comprobando si el filtro " + desFilter + " esta activo...");
        
        boolean activeFilter = this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFilterDaoMap().get(desFilter).getActive().equals("1");

        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isActiveFilter: Filtro " + desFilter + " activo: " + activeFilter);

        return activeFilter;
    }

    /**
     * Obtiene la fecha de la ?ltima ejecuci?n del filtro referenciado por el
     * par?metro de entrada desFilter.
     *
     * @param desFilter Key del filtro.
     * @return Date
     */
    private Date getLastCheckGenericFilter(String desFilter) {
        return this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFilterDaoMap().get(desFilter).getLastCheck();
    }
    
    /**
     * Pasa el filtro desFilter a cada uno de los angeles definidos para desFilter
     * por el usuario si cumplen las siguientes condiciones:
     *  - El filtro esta activo
     *  - El filtro tiene definido algun angel para su actividad.
     *  - El tiempo transcurrido desde la ultima comprobacion es superior al
     * tiempo definido por el usuario.
     *
     * @param request: Sesion web
     * @param desFilter: Tipo de filtro
     * @param uidAngel: Identificador del angel a comprobar.
     * @param isTimeToCheckToday: Indica si se pasa o no el filtro
     * @param firstCheck: Indica si es la primera comprobacion
     * @return Resultado del paso del filtro
     *
     * @throws JSONException
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws MessagingException
     * @throws ParseException
     * @throws bsh.ParseException
     */
    public String checkFilter(HttpServletRequest request, String desFilter, boolean isTimeToCheckToday, boolean firstCheck, JSONObject jsonAngel, Date lastCheck) throws Exception{
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkFilter: Inicio checkFilter para el filtro " + desFilter + "...");
        JSONObject jsonFilter;
        boolean angelFound;
        boolean isActiveFilter;
        String result = "";

        // Obtenemos todos los filtros
        JSONArray arrayFilters = this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFiltersUserFromDB();
        
        // Cargamos el filtro a ejecutar
        this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().loadFilter(desFilter, arrayFilters);
        
        // Obtenemos si el filtro cargado esta activo
        try {
            isActiveFilter = this.isActiveFilter(desFilter);
        } catch (Exception e) {
            isActiveFilter = false;
        }
        
        // Si el angel esta definido para el filtro, se le mandaran notificaciones
        if(jsonAngel.getString("typeAngel").equals("F")){
            angelFound = this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().isActiveFilterForAngel(desFilter, jsonAngel.getString("idFacebook"));
        }else{
            angelFound = this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().isActiveFilterForAngel(desFilter, jsonAngel.getString("idAngel"));
        }

        // Si el angel existe, el filtro esta activo y hoy toca ejecutar el filtro, lo ejecutamos
        if (angelFound && isActiveFilter && isTimeToCheckToday) {
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkFilter: Ejecutamos el filtro " + desFilter + " para obtener la informacion...");            
            jsonFilter = this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().updateFilter(desFilter);
            result = getResultCheckFilter(request, desFilter, firstCheck, jsonFilter, lastCheck);
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkFilter: Filtro " + desFilter + " ejecutado!");
        }

        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkFilter: Fin checkFilter para el filtro " + desFilter + "...");
        return result;
    }

    /**
     * Obtiene un mapa con todos los parametros de la ejecucion de un filtro.
     * 
     * @param request
     * @param desFilter
     * @param firstCheck
     * @param jsonFilter
     * @param lastCheck
     * @return Map<String, Object> 
     */
    private Map<String, Object> getArgsFilter(HttpServletRequest request, String desFilter, boolean firstCheck, JSONObject jsonFilter, Date lastCheck){
        Map<String, Object> argsFilter = new HashMap<>();
        
        argsFilter.put(ARGS_KEY_REQUEST, request);
        argsFilter.put(ARGS_KEY_JSONFILTER, jsonFilter);
        argsFilter.put(ARGS_KEY_DESFILTER, desFilter);
        argsFilter.put(ARGS_KEY_FIRSTCHECK, firstCheck);
        argsFilter.put(ARGS_KEY_LASTCHECK, lastCheck);
        
        return argsFilter;
    }
    
    /**
     * Realiza el chequeo del filtro desFilter.
     *
     * @param request: Sesion Web
     * @param desFilter: Tipo de filtro
     * @param firstCheck: Indica si es la primera vez que se pasa el filtro
     * @param jsonFilter: Descripcion del filtro
     * @return Devuelve el resultado del chequeo del filtro desFilter
     *
     * @throws FileNotFoundException
     * @throws IOException
     * @throws JSONException
     * @throws NoSuchProviderException
     * @throws MessagingException
     * @throws ParseException
     * @throws bsh.ParseException
     */
    public String getResultCheckFilter(HttpServletRequest request, String desFilter, boolean firstCheck, JSONObject jsonFilter, Date lastCheck) throws Exception{
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getResultCheckFilter: Inicio getResultCheckFilter para el filtro " + desFilter + "...");    
      
        // Ejecutamos el filtro
        String result = this.filterActiveMap.get(desFilter).executeFilter(getArgsFilter(request, desFilter, firstCheck, jsonFilter, lastCheck));

        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getResultCheckFilter: Fin getResultCheckFilter para el filtro " + desFilter + "...");
        return result;
    }

    /**
     * Realiza el chequeo inicial de todos los filtros disponibles en la aplicacion,
     * mandando un email al angel correspondiente con el resultado del chequeo. Puede lanzar excepciones del tipo
     * InterDataBaseException, InterProcessException o InterEmailException.
     *
     * @param request: Sesion Web
     * @param jsonAngel: JSON con informacion del angel al que se le va a notificar el chequeo.
     *
     * @throws JSONException
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws MessagingException
     * @throws ParseException
     * @throws bsh.ParseException
     */
    public void firstCheckAngelConfirmation(HttpServletRequest request, JSONObject jsonAngel) throws InterDataBaseException, InterProcessException, InterEmailException{
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - firstCheckAngelConfirmation: Inicio firstCheckAngelConfirmation...");

        // Inicializamos la lista para almacenar el resultado de la ejecucion de los filtros
        Map<String, String> resultFilterList = new HashMap<>();
        
        // Inicializamos las variables para los filtros
        String desFilter;
        String resultFilter;
        
        try {
            
            Iterator itFilter = this.snsObject.getConfigurationManager().getListActiveFilters().iterator();
                        
            while(itFilter.hasNext()){

                // Obtenemos la descripci?n del filtro
                desFilter = itFilter.next().toString();
                
                // Obtenemos el resultado del filtro
                resultFilter = this.checkFilter(request, desFilter, true, true, jsonAngel, null);
                
                // A?adimos el resultado a la lista
                resultFilterList.put(desFilter, resultFilter);
            }

            // Mandamos email de confirmacion
            this.snsObject.getEmailObject().sendEmailCheck(resultFilterList, jsonAngel);
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - firstCheckAngelConfirmation: Fin firstCheckAngelConfirmation...");
        } catch (Exception ex) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - firstCheckAngelConfirmation: Excepcion capturada Exception: " + ex.getMessage());
            this.snsObject.getExceptionManager().initControlException(ex);
        }
    }
    
    private boolean isTimeToCheck(String desFilter, Date lastCheck){
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isTimeToCheck: Inicio isTimeToCheck con resultado para el filtro " + desFilter);
        
        boolean isTimeToCheck = false;
        try {
            isTimeToCheck = checkFilterToday(desFilter, lastCheck);
        } catch (Exception ex) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isTimeToCheck: Excepcion capturada Exception: " + ex.getMessage());
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isTimeToCheck: No se ejecutara el filtro: " + desFilter);
        }
        
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isTimeToCheck: Fin isTimeToCheck con resultado " + isTimeToCheck + " para el filtro " + desFilter);
        return isTimeToCheck;
    }
    

    /**
     * Metodo que realiza los chequeos correspondientes fuera de sesion. Es el
     * metodo utilizado para hacer los chequeos en el momento del backup diario. Puede lanzar excepciones del tipo
     * InterDataBaseException, InterProcessException o InterEmailException.
     *
     * @param request: Sesion Web asociada a cada usuario, sin la cual no podemos acceder a las utilidades de Facebook.
     * @param angelsUser: JSONArray que contiene la informacion de los angeles definidos por el usuario y a quienes se les enviar?n las notificaciones.
     * 
     * @throws JSONException
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws MessagingException
     * @throws ParseException
     * @throws bsh.ParseException
     */
    public void checkUserSettingsOffLine(HttpServletRequest request, JSONArray angelsUser) throws InterDataBaseException, InterProcessException, InterEmailException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkUserSettingsOffLine: Inicio checkUserSettingsOffLine...");
        Date lastCheck;
        Map<String, Date> lastCheckFilterList = new HashMap();
        Map<String, Boolean> isTimeToCheckFilterList = new HashMap();
        String key;
        
        // Obtenemos el iterador para la lista de keys de filtros
        Iterator itKey = this.snsObject.getConfigurationManager().getListActiveFilters().iterator();
        
        while(itKey.hasNext()){
            
            // Obtenemos la key del filtro
            key = itKey.next().toString();
            
            // Obtenemos la ?ltima fecha de ejecucion del filtro
            lastCheck = getLastCheckGenericFilter(key);
            
            // Introducimos la fecha en la lista
            lastCheckFilterList.put(key, lastCheck);
            
            // Introducimos el resultado en la lista
            isTimeToCheckFilterList.put(key, isTimeToCheck(key,lastCheck));
        }
        
        Integer count = 0;
        
        // Para cada angel definido por el usuario realizamos el envio de la informacion
        for (int j = 0; j < angelsUser.length(); j++) {

            count = 0;
            
            try {
                JSONObject jsonAngel = angelsUser.getJSONObject(j);
                logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkUserSettingsOffLine: Iniciando chequeo para el angel de entrada!!");

                Map<String, String> resultFilterList = new HashMap();
                String keyFilter;
                String resultFilter;
                Iterator itKeyFilter = this.snsObject.getConfigurationManager().getListActiveFilters().iterator();
                
                while(itKeyFilter.hasNext()){
                    // Obtenemos la key del filtro
                    keyFilter = itKeyFilter.next().toString();
                    
                    // Ejecutamos el filtro
                    resultFilter = this.checkFilter(request, keyFilter, isTimeToCheckFilterList.get(keyFilter), false, jsonAngel, lastCheckFilterList.get(keyFilter));
                    
                    // A?adimos el resultado a la lista de resultados para enviar el email
                    resultFilterList.put(keyFilter, resultFilter);
                    
                    // Incrementamos el contador
                    count++;
                }

                // Si el angel ha aceptado los terminos, se le envia el email con el resultado del chequeo
                if (jsonAngel.getString("acceptAngel").equals("1")) {
                    this.snsObject.getEmailObject().sendEmailCheck(resultFilterList, jsonAngel);
                }
                logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkUserSettingsOffLine: Fin chequeo para el angel de entrada!!");
            } catch (Exception ex) {
                logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkUserSettingsOffLine: Excepcion capturada Exception: " + ex.getMessage());
                this.snsObject.getExceptionManager().initControlException(ex);
            }
        }

        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkUserSettingsOffLine: Fin checkUserSettingsOffLine...");
    }

    /**
     * Indica si el filtro desFilter se debe pasar hoy. Puede lanzar excepciones del tipo Exception.
     * @param desFilter: Tipo de filtro a pasar.
     * @return
     *  - true: Si le corresponde ser ejecutado hoy.
     *  - false: Si no le corresponde ser ejecutado hoy.
     *
     * @throws JSONException
     * @throws ParseException
     */
    public boolean checkFilterToday(String desFilter, Date lastCheck) throws Exception {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkFilterToday: Inicio checkFilterToday...");
        
        // Obtenemos la fecha de la proxima ejecucion del filtro
        long proxCheck = this.snsObject.getDateTimeUtilities().getNextCheckTime(Integer.parseInt(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFilterDaoMap().get(desFilter).getFrec()), lastCheck);

        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkFilterToday: Proximo chequeo a realizar en fecha: " + new Date(proxCheck) + ", Ultimo chequeo realizado en fecha: " + new Date(lastCheck.getTime()));
        Calendar timeNow = Calendar.getInstance();

        if (proxCheck > timeNow.getTimeInMillis()) {
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkFilterToday: Fin checkFilterToday. No se realizara el chequeo para el filtro " + desFilter);
            return false;
        } else {
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkFilterToday: Fin checkFilterToday. Se realizara el chequeo para el filtro " + desFilter);
            return true;
        }
    }
    
    
    private String getAngelsFilterUpdated(String idNewAngel, String idOlderAngel, String olderAngels) {
        String newAngels = "";

        if (olderAngels != null) {
            if (!olderAngels.equals("")) {
                String[] arrayAngels = olderAngels.split(";");

                for (String angel : arrayAngels) {
                    String auxAngel = angel;

                    if (!angel.equals("") && !angel.equals("null")) {
                        if (angel.equals(idOlderAngel)) {
                            auxAngel = idNewAngel;
                        }

                        if (newAngels.equals("")) {
                            newAngels = auxAngel + ";";
                        } else {
                            newAngels = newAngels + auxAngel + ";";
                        }
                    }
                }
            }
        }

        return newAngels;
    }
    
    /**
     * Actualizamos el angel referenciado para los filtros.
     *
     * @param idOlderAngel Identificador antiguo angel.
     * @param idNewAngel Identificador nuevo angel.
     */
    public void updateAngelForFilter(String idOlderAngel, String idNewAngel) {
        
        String keyFilter;
        Iterator<String> itFilter = this.snsObject.getConfigurationManager().getListActiveFilters().iterator();
        
        while(itFilter.hasNext()){
            // Obtenemos la key del filtro
            keyFilter = itFilter.next();
            
            // Borramos el angel del filtro
            this.getSnsObject().getUserSettingsDaoManager().getUserSettingsDAO().getFilterDaoMap().get(keyFilter).setAngels(getAngelsFilterUpdated(idNewAngel, idOlderAngel, this.getSnsObject().getUserSettingsDaoManager().getUserSettingsDAO().getFilterDaoMap().get(keyFilter).getAngels()));
        }
}
    
    /**
     * Carga en el map interno los objetos que representan los filtros.
     * 
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException 
     */
    public final void loadMapFilters() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        
        this.filterActiveMap = new HashMap<>();
        
        Iterator<String> itFilter = this.snsObject.getConfigurationManager().getListActiveFilters().iterator();
        
        while(itFilter.hasNext()){
            String strFilter = itFilter.next();
            
            // Obtenemos el filtro
            Class classFilter = Class.forName(this.snsObject.getConfigurationManager().getKeyValueClassFilter().get(strFilter));
            ILifeCycleFilter iFilter = (ILifeCycleFilter) classFilter.newInstance();
            
            // Inicializamos el filtro
            iFilter.init(this.snsObject, strFilter);
            
            // Introducimos el filtro en el mapa
            this.filterActiveMap.put(strFilter, iFilter);
        }
    }  
    
    /**
     * Realiza el cifrado del identificador de un amigo para ser mandado por
     * email y as? respetar su intimidad.
     *
     * @param uidFriend Identificador en Facebook del amigo.
     * @return UID Cifrada en Base 64
     */
    public String cifrarUIDFriend(String uidFriend) {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - cifrarUIDFriend: Inicio cifrarUIDFriend para el amigo: " + uidFriend);
        String uidCifrada = "";

        try {
            uidCifrada = UserSettingsDAO.cifrar(uidFriend);
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - cifrarUIDFriend: UID cifrado " + uidCifrada + " para el amigo: " + uidFriend);
        } catch (DataLengthException | IllegalStateException | InvalidCipherTextException ex) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getMutualFriends: Excepcion capturada del tipo " + ex.getClass() + ": " + ex.getMessage());
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getMutualFriends: No se ha podido cifrar el identificador del amigo...");
        }
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - cifrarUIDFriend: Fin cifrarUIDFriend para el amigo: " + uidFriend);
        return uidCifrada;
    }
}
