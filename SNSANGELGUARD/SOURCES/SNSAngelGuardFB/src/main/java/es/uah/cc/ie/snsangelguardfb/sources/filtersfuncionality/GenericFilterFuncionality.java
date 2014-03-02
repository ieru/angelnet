/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.snsangelguardfb.sources.filtersfuncionality;

import es.uah.cc.ie.snsangelguardfb.exception.InterDataBaseException;
import es.uah.cc.ie.snsangelguardfb.exception.InterEmailException;
import es.uah.cc.ie.snsangelguardfb.exception.InterProcessException;
import es.uah.cc.ie.snsangelguardfb.SNSAngelGuardFBManager;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

/**
 * Controla la ejecucion de todos los filtros definidos en la aplicacion.
 *
 * @author tote
 */
public class GenericFilterFuncionality {

    /** Logger Class */
    private static Logger logger = Logger.getLogger(GenericFilterFuncionality.class);
    
    /** Key para filtro de vocabulario ofensivo */
    private static final String DES_WALL_FILTER = "fltWall";
    
    /** Key para el filtro de amistades */
    private static final String DES_FRIENDS_FILTER = "fltFriends";
    
    /** Key para el filtro de privacidad */
    private static final String DES_PRIV_FILTER = "fltPriv";
    
    /** Key para el filtro de visitas */
    private static final String DES_VIST_FILTER = "fltVist";

    /** Manager de la aplicacion */
    private SNSAngelGuardFBManager snsObject;

    /**
     * Constructor de clase
     * 
     * @param snsObject: Clase Manager de la aplicacion
     */
    public GenericFilterFuncionality(SNSAngelGuardFBManager snsObject) {
        this.snsObject = snsObject;
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
     * Comprueba si el usuario tiene activo el filtro desFilter
     *
     * @param desFilter: Tipo de filtro a comprobar
     * @return
     *  - true: Si el filtro est? activo
     *  - false: Si el filtro no est? activo
     */
    public boolean isActiveFilter(String desFilter) {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isActiveFilter: Comprobando si el filtro " + desFilter + " est? activo...");
        boolean activeFilter = false;
        
        switch (desFilter) {
            case DES_WALL_FILTER:
                activeFilter = this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFltWall().getActive().equals("1");
                break;
            case DES_FRIENDS_FILTER:
                activeFilter = this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFltFriends().getActive().equals("1");
                break;
            case DES_PRIV_FILTER:
                activeFilter = this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFltPriv().getActive().equals("1");
                break;
            case DES_VIST_FILTER: 
                activeFilter = this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFltVist().getActive().equals("1");
                break;
        }

        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isActiveFilter: Filtro " + desFilter + " activo: " + activeFilter);

        return activeFilter;
    }
    
    /**
     * Encuentra de entre los ?ngeles definidos para un filtro, si est? definido para ?ste el angel actual.
     * 
     * @param desFilter
     * @param currentAngel
     * @return boolean 
     */
    private boolean isActiveFilterForAngel(String desFilter, String currentAngel){
        boolean angelFound = false;
         
        String angels = this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getAngelsFilter(desFilter);
        
        if(angels != null){
            if (!angels.equals("")) {
                
                String[] angelsSelected = angels.split(";");
                
                for(String angelEmail: angelsSelected){
                    if(angelEmail.equals(currentAngel)){
                        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkFilter: Angel a quien se le enviar? la notificaci?n del filtro " + desFilter + ": " + angelEmail);
                         angelFound = true;
                         break;
                    }
                }
               
            }
        }
        
        return angelFound;
    }

    /**
     * Obtiene la fecha de la ?ltima ejecuci?n del filtro referenciado por el
     * par?metro de entrada desFilter.
     *
     * @param desFilter Key del filtro.
     * @return Date
     */
    private Date getLastCheckGenericFilter(String desFilter) {
        Date lastCheck = null;

        // Dependiendo del tipo de filtro, devolvemos una fecha u otra
        switch (desFilter) {
            case DES_WALL_FILTER:
                lastCheck = this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFltWall().getLastCheck();
                break;
            case DES_FRIENDS_FILTER:
                lastCheck = this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFltFriends().getLastCheck();
                break;
            case DES_PRIV_FILTER:
                lastCheck = this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFltPriv().getLastCheck();
                break;
            case DES_VIST_FILTER:
                lastCheck = this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFltVist().getLastCheck();
                break;
        }

        return lastCheck;
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

        // Cargamos el filtro a ejecutar
        this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().loadFilter(desFilter);
        
        // Obtenemos si el filtro cargado esta activo
        try {
            isActiveFilter = this.isActiveFilter(desFilter);
        } catch (Exception e) {
            isActiveFilter = false;
        }
        
        // Si el angel esta definido para el filtro, se le mandaran notificaciones
        if(jsonAngel.getString("typeAngel").equals("F")){
            angelFound = isActiveFilterForAngel(desFilter, jsonAngel.getString("idFacebook"));
        }else{
            angelFound = isActiveFilterForAngel(desFilter, jsonAngel.getString("idAngel"));
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
        String result = "";
        
        // Ejecutamos el filtro y obtenemos los resultados a devolver dependiendo del tipo de filtro
        switch (desFilter) {
            case DES_WALL_FILTER:
                result = this.snsObject.getWallFilterFuncionality().checkPostWall(request, firstCheck, jsonFilter, lastCheck);
                break;
            case DES_FRIENDS_FILTER:
                result = this.snsObject.getFriendsFilterFuncionality().checkFriends();
                break;
            case DES_PRIV_FILTER:
                result = "Filtro en construccion";
                break;
            case DES_VIST_FILTER:
                result = this.snsObject.getVisitsFilterFuncionality().checkVisitFilter(firstCheck, lastCheck);
                break;
        }

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

        try {
            String resultFltWall = this.checkFilter(request, DES_WALL_FILTER, true, true, jsonAngel, null);
            String resultFltFriends = this.checkFilter(request, DES_FRIENDS_FILTER, true, true, jsonAngel, null);
            String resultFltPriv = this.checkFilter(request, DES_PRIV_FILTER, true, true, jsonAngel, null);
            String resultFltVist = this.checkFilter(request, DES_VIST_FILTER, true, true, jsonAngel, null);

            // Mandamos email de confirmacion
            this.snsObject.getEmailObject().sendEmailCheck(resultFltWall, resultFltFriends, resultFltPriv, resultFltVist, jsonAngel);
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
        
        // Obtenemos la ?ltima fecha de ejecuci?n de cada filtro
        Date lastCheckFltWall = getLastCheckGenericFilter(DES_WALL_FILTER);
        Date lastCheckFltFriends = getLastCheckGenericFilter(DES_WALL_FILTER);
        Date lastCheckFltPriv = getLastCheckGenericFilter(DES_PRIV_FILTER);
        Date lastCheckFltVist = getLastCheckGenericFilter(DES_WALL_FILTER);
        
        // Comprobamos si debemos pasar los filtros
        boolean isTimeToCheckFltWall = isTimeToCheck(DES_WALL_FILTER, lastCheckFltWall);
        boolean isTimeToCheckFltFriends = isTimeToCheck(DES_FRIENDS_FILTER, lastCheckFltFriends);
        boolean isTimeToCheckFltPriv = isTimeToCheck(DES_PRIV_FILTER, lastCheckFltPriv);
        boolean isTimeToCheckFltVist = isTimeToCheck(DES_VIST_FILTER, lastCheckFltVist);
        
        
        
        // Para cada angel definido por el usuario realizamos el envio de la informacion
        for (int j = 0; j < angelsUser.length(); j++) {

            try {
                JSONObject jsonAngel = angelsUser.getJSONObject(j);
                logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkUserSettingsOffLine: Iniciando chequeo para: " + jsonAngel.getString("idAngel"));

                // Ejecutamos los filtros
                String resultFltWall = this.checkFilter(request, DES_WALL_FILTER, isTimeToCheckFltWall, false, jsonAngel, lastCheckFltWall);
                String resultFltFriends = this.checkFilter(request, DES_FRIENDS_FILTER, isTimeToCheckFltFriends, false, jsonAngel, lastCheckFltFriends);
                String resultFltPriv = this.checkFilter(request, DES_PRIV_FILTER, isTimeToCheckFltPriv, false, jsonAngel, lastCheckFltPriv);
                String resultFltVist = this.checkFilter(request, DES_VIST_FILTER, isTimeToCheckFltVist, false, jsonAngel, lastCheckFltVist);

                // Si el angel ha aceptado los terminos, se le envia el email con el resultado del chequeo
                if (jsonAngel.getString("acceptAngel").equals("1")) {
                    this.snsObject.getEmailObject().sendEmailCheck(resultFltWall, resultFltFriends, resultFltPriv, resultFltVist, jsonAngel);
                }
                logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkUserSettingsOffLine: Fin chequeo para: " + jsonAngel.getString("idAngel"));
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
        long proxCheck = 0;
        
        switch (desFilter) {
            case DES_WALL_FILTER:
                proxCheck = this.snsObject.getDateTimeUtilities().getNextCheckTime(Integer.parseInt(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFltWall().getFrec()), lastCheck);
                break;
            case DES_FRIENDS_FILTER:
                proxCheck = this.snsObject.getDateTimeUtilities().getNextCheckTime(Integer.parseInt(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFltFriends().getFrec()), lastCheck);
                break;
            case DES_PRIV_FILTER:
                proxCheck = this.snsObject.getDateTimeUtilities().getNextCheckTime(Integer.parseInt(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFltPriv().getFrec()), lastCheck);
                break;
            case DES_VIST_FILTER:
                proxCheck = this.snsObject.getDateTimeUtilities().getNextCheckTime(Integer.parseInt(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getFltVist().getFrec()), lastCheck);
                break;
        }

        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkFilterToday: Proximo chequeo a realizar en fecha: " + new Date(proxCheck) + ", Ultimo chequeo realizado en fecha: " + new Date(lastCheck.getTime()));
        Calendar timeNow = Calendar.getInstance();

        if (proxCheck > timeNow.getTimeInMillis()) {
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkFilterToday: Fin checkFilterToday. No se realizar? el chequeo para el filtro " + desFilter);
            return false;
        } else {
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkFilterToday: Fin checkFilterToday. Se realizar? el chequeo para el filtro " + desFilter);
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

        // Borramos el angel del filtro de control del lenguaje
        this.getSnsObject().getUserSettingsDaoManager().getUserSettingsDAO().getFltWall().setAngels(getAngelsFilterUpdated(idNewAngel, idOlderAngel, this.getSnsObject().getUserSettingsDaoManager().getUserSettingsDAO().getFltWall().getAngels()));

        // Borramos el angel del filtro de control de amigos
        this.getSnsObject().getUserSettingsDaoManager().getUserSettingsDAO().getFltFriends().setAngels(getAngelsFilterUpdated(idNewAngel, idOlderAngel, this.getSnsObject().getUserSettingsDaoManager().getUserSettingsDAO().getFltFriends().getAngels()));

        // Borramos el angel del filtro de control de privacidad
        this.getSnsObject().getUserSettingsDaoManager().getUserSettingsDAO().getFltPriv().setAngels(getAngelsFilterUpdated(idNewAngel, idOlderAngel, this.getSnsObject().getUserSettingsDaoManager().getUserSettingsDAO().getFltPriv().getAngels()));

        // Borramos el angel del filtro de control de visitas
        this.getSnsObject().getUserSettingsDaoManager().getUserSettingsDAO().getFltVist().setAngels(getAngelsFilterUpdated(idNewAngel, idOlderAngel, this.getSnsObject().getUserSettingsDaoManager().getUserSettingsDAO().getFltVist().getAngels()));
    }
}
