/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.snsangelguardfb.sources.dao.entity;

import bsh.ParseException;
import com.sun.jersey.api.client.UniformInterfaceException;
import es.uah.cc.ie.snsangelguardfb.sources.dao.UserSettingsDaoManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Clase que realiza todas las operaciones DAO necesarias para un filtro.
 *
 * @author tote
 */
public class UserSettings_SettingsFilterDAO {

    /** Logger Class */
    private static Logger logger = Logger.getLogger(UserSettings_SettingsFilterDAO.class);

    /** Manager del objeto UserSettingsDAO */
    private UserSettingsDaoManager manager;
    
    /** Identificador dinamico de ejecucion */
    private String typeFilter;
    
    /** Identificador en base de datos del filtro */
    private Long uid;
    
    /** Frecuencia de ejecucion del filtro */
    private String frec;
    
    /** Angeles separados por el caracter ; */
    private String angels;
    
    /** Indicador de actividad del filtro */
    private String active;
    
    /** Ultimo chequeo realizado por el filtro */
    private Date lastCheck;

    /**
     * Constructor de clase. Recibira todos los parametros de la clase.
     *
     * @param manager
     * @param uid
     * @param frec
     * @param angels
     * @param active
     * @param uidProp
     * @param lastCheck
     */
    public UserSettings_SettingsFilterDAO(UserSettingsDaoManager manager, String idFilter, Long uid, String frec, String angels, String active, Date lastCheck) {
        this.manager = manager;
        this.typeFilter = idFilter;
        this.uid = uid;
        this.frec = frec;
        this.angels = angels;
        this.active = active;
        this.lastCheck = lastCheck;
    }

    /**
     * Constructor sin argumentos.
     */
    public UserSettings_SettingsFilterDAO(UserSettingsDaoManager manager, String idFilter) {
        this.manager = manager;
        this.typeFilter = idFilter;
    }

    /**
     * 
     * @return the typeFilter 
     */
    public String getTypeFilter() {
        return typeFilter;
    }
    
    /**
     * @return the uid
     */
    public Long getUid() {
        return uid;
    }

    /**
     * @param uid the uid to set
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * @return the frec
     */
    public String getFrec() {
        return frec;
    }

    /**
     * @param frec the frec to set
     */
    public void setFrec(String frec) {
        this.frec = frec;
    }

    /**
     * @return the angels
     */
    public String getAngels() {
        return angels;
    }

    /**
     * @param angels the angels to set
     */
    public void setAngels(String angels) {
        this.angels = angels;
    }

    /**
     * @return the active
     */
    public String getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }

    /**
     * @return the lastCheck
     */
    public Date getLastCheck() {
        return lastCheck;
    }

    /**
     * @param lastCheck the lastCheck to set
     */
    public void setLastCheck(Date lastCheck) {
        this.lastCheck = lastCheck;
    }

    /**
     * Formatea un String a un Date con formato "yyyy-MM-dd HH:mm:ss". Podra lanzar excepciones del tipo ParseException o java.text.ParseException.
     *
     * @param strTime Fecha obtenida de la base de datos.
     * @return Date con la fecha parseada al formato anterior.
     * @throws ParseException
     * @throws java.text.ParseException
     */
    public Date formatTime(String strTime) throws ParseException, java.text.ParseException {
        logger.info(this.uid + " - formatTime: Inicio formatTime...");
        Date time = new Date(Long.parseLong(strTime));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       // time = sdf.parse(strTime);
        logger.info(this.uid + " - formatTime: Fin formatTime...");
        return time;
    }

    /**
     * Obtiene el filtro de la base de datos en formato JSON. Podra lanzar excepciones del tipo JSONException.
     *
     * @return
     * @throws JSONException
     */
    public JSONObject getObjectFilter() throws JSONException {
        logger.info(this.uid + " - getObjectFilter: Inicio getObjectFilter para el filtro " + typeFilter);
        JSONObject newObject = new JSONObject();
        JSONObject jsonUri = new JSONObject();

        newObject.put("idFilter", this.uid);
        newObject.put("frecFilter", this.getFrec());
        newObject.put("activeFilter", this.getActive());
        newObject.put("lastCheck", "");
        newObject.put("typeFilter", typeFilter);

        jsonUri.put("uri", this.manager.getSnsObject().getConfigurationManager().getConfigHostApplication() + "SNSdataBaseIntegratorServer/resources/userSettingss/" + this.getUid().toString() + "/");
        newObject.put("userSettings", jsonUri);

        logger.info(this.uid + " - getObjectFilter: Fin getObjectFilter para el filtro " + typeFilter);
        return newObject;
    }

    /**
     * Obtiene un JSONArray con todos los datos de los angeles de la aplicacion. Podra lanzar excepciones del tipo JSONException.
     *
     * @param arrayAngels
     * @return
     * @throws JSONException
     */
    public JSONArray getAngelsFilter(String[][] arrayAngels) throws JSONException {
        logger.info(this.uid + " - getAngelsFilter: Inicio getAngelsFilter...");
        String[] arrayAngelsFilter = this.getAngels().split(";");
        JSONArray jsonAngelsFilter = new JSONArray();
        JSONObject jsonDatesAngel = null;

        for (int i = 0; i < arrayAngelsFilter.length; i++) {
            for (int j = 0; j < arrayAngels.length; j++) {
                if (arrayAngelsFilter[i].equals(arrayAngels[j][0])) {
                    jsonDatesAngel = new JSONObject();
                    jsonDatesAngel.put("idAngel", arrayAngels[j][0]);
                    jsonDatesAngel.put("nameAngel", arrayAngels[j][1]);
                    jsonDatesAngel.put("imgAngel", arrayAngels[j][2]);
                    jsonDatesAngel.put("typeAngel", arrayAngels[j][3]);
                    jsonDatesAngel.put("acceptAngel", "0");
                    jsonDatesAngel.put("userPropAngel", this.manager.getUserSettingsDAO().getUid());
                    jsonAngelsFilter.put(jsonDatesAngel);
                }
            }
        }
        logger.debug(this.uid + " - getAngelsFilter: Angeles seleccionados: " + jsonAngelsFilter.toString());
        logger.info(this.uid + " - getAngelsFilter: Fin getAngelsFilter...");
        return jsonAngelsFilter;
    }

    /**
     * Carga en la aplicacion un filtro seleccionado. Podr? lanzar excepciones del tipo JSONException, ParseException o java.text.ParseException.
     *
     * @param jsonFilter
     * @param jsonArrayAngels
     * @param des Identificador de filtro.
     * @throws JSONException
     * @throws ParseException
     * @throws java.text.ParseException
     */
    public void loadSettingsFilter(JSONObject jsonFilter, JSONArray jsonArrayAngels, String des) throws JSONException, ParseException, java.text.ParseException {
        logger.info(this.uid + " - loadSettingsFilter: Inicio loadSettingsFilter para el filtro " + des);
        this.setUid(new Double(jsonFilter.getString("userSettingsUid")).longValue());
        this.setFrec(jsonFilter.getString("frec" + des));
        this.setActive(jsonFilter.getString("active" + des));
        this.setLastCheck(formatTime(jsonFilter.getString("lastCheck").replace("T", " ")));
        
        if (this.getActive().equals("1")) {
            loadAngelsFilter(jsonArrayAngels);
        }
        logger.info(this.uid + " - loadSettingsFilter: Fin loadSettingsFilter para el filtro " + des);
    }

    /**
     * Carga los angeles definidos en la aplicacion para presentarlos por pantalla. Podr? lanzar excepciones del tipo JSONException.
     *
     * @param jsonArrayAngels
     * @throws JSONException
     */
    public void loadAngelsFilter(JSONArray jsonArrayAngels) throws JSONException {
        logger.info(this.uid + " - loadAngelsFilter: Inicio loadAngelsFilter...");
        JSONObject jsonAngel = null;
        String lstAngels = "";
        if (jsonArrayAngels != null) {
            if (jsonArrayAngels.length() != 0) {
                for (int i = 0; i < jsonArrayAngels.length(); i++) {
                    jsonAngel = jsonArrayAngels.getJSONObject(i);
                    if (i == 0) {
                        if(jsonAngel.getString("typeAngel").equals("F")){
                            lstAngels = jsonAngel.getString("idFacebook") + ";";
                        }else{
                            lstAngels = jsonAngel.getString("idAngel") + ";";
                        }
                    } else {
                        if(jsonAngel.getString("typeAngel").equals("F")){
                            lstAngels += jsonAngel.getString("idFacebook") + ";";
                        }else{
                            lstAngels += jsonAngel.getString("idAngel") + ";";
                        }
                    }
                }
            }

            this.setAngels(lstAngels);
        }
        logger.info(this.uid + " - loadAngelsFilter: Fin loadAngelsFilter...");
    }
    
    /**
     * Inicializa la base de datos para un nuevo filtro no seleccionado.
     * 
     * @param des
     */
    public void saveNewFilter() throws JSONException{
        logger.info(this.manager.getUserSettingsDAO().getUid() + " - initDBnewFilter: Inicio initDBnewFilter...");

        // Convertimos de modelo interno a modelo JSON
        JSONObject newInstanceFilter = this.manager.getUserSettingsDAO().getFilterDaoMap().get(typeFilter).getObjectFilter();
        
        // Almacenamos el filtro en base de datos sin relacionar
        JSONObject resultNewInstanceFilter = new JSONObject(this.manager.getSnsObject().getClient().settingsFilter_setNewFilter(String.class, newInstanceFilter));
        
        // Almacenamos en modelo interno el identificador del filtro en base de datos
        this.uid = resultNewInstanceFilter.getLong("idFilter");
        
        // Preparamos el filtro relacionado en un objeto JSON
        resultNewInstanceFilter = getFilterWithRelationshipWithUserSettings(resultNewInstanceFilter, this.manager.getUserSettingsDAO().getUid());
        
        // Actualizamos el filtro en base de datos ya relacionado con su usuario
        this.manager.getSnsObject().getClient().settingsFilter_updateFilterByIdFilter(String.class, resultNewInstanceFilter.getString("idFilter"), newInstanceFilter);

        logger.info(this.manager.getUserSettingsDAO().getUid() + " - initDBnewFilter: Fin initDBnewFilter!!");
    }
    
    /**
     * Devuelve un objeto JSON del filtro preparado para ser enlazado por
     * primera vez con un usuario de la aplicacion.
     *
     * @param instanceFilter Objeto JSON con la informacion del filtro.
     * @param uidUserSettings Identificador del usuario de la aplicacion
     * @return JSONObject para ser actualizado en base de datos.
     * @throws JSONException
     */
    public JSONObject getFilterWithRelationshipWithUserSettings(JSONObject instanceFilter, String uidUserSettings) throws JSONException{
        logger.info(this.manager.getUserSettingsDAO().getUid() + " - getFilterWithRelationshipWithUserSettings: Inicio getFilterWithRelationshipWithUserSettings...");
        
        JSONObject jsonUri = new JSONObject();
        jsonUri.put("uri", this.manager.getSnsObject().getConfigurationManager().getConfigHostApplication() + 
                "SNSdataBaseIntegratorServer/resources/settingsFilters/" + instanceFilter.getString("idFilter") + 
                "/userSettingsCollection/" + uidUserSettings + "/");

        JSONObject jsonUriUserSettings = new JSONObject();
        jsonUriUserSettings.put("userSettings", jsonUri);

        instanceFilter.put("userSettingsCollection", jsonUriUserSettings);

        logger.info(this.manager.getUserSettingsDAO().getUid() + " - getFilterWithRelationshipWithUserSettings: Fin getFilterWithRelationshipWithUserSettings!!");
        return instanceFilter;
    }
    
    /**
     * Enlaza un angel al filtro.
     * 
     * @param jsonAngel Angel que va a enlazarse al filtro.
     * @return JSONObject con la relacion del angel y el filtro.
     * @throws JSONException 
     */
    public JSONObject getJsonAngelWithFilterRelationship(JSONObject jsonAngel) throws JSONException {
        logger.info(this.manager.getUserSettingsDAO().getUid() + " - getJsonAngelWithFilterRelationship: Inicio getJsonAngelWithFilterRelationship...");
        JSONObject jsonCollection;
        JSONArray aux;
        JSONObject jsonUri;

        try {
            jsonCollection = jsonAngel.getJSONObject("settingsFilterCollection");
            aux = jsonCollection.getJSONArray("settingsFilter");
            jsonUri = new JSONObject();

            jsonUri.put("uri", this.manager.getSnsObject().getConfigurationManager().getConfigHostApplication()
                    + "SNSdataBaseIntegratorServer/resources/settingsAngelss/" + jsonAngel.getString("uidAngel")
                    + "/settingsFilterCollection/" + this.uid + "/");
            aux.put(jsonUri);
            jsonCollection.put("settingsFilter", aux);

            jsonAngel.put("settingsFilterCollection", jsonCollection);
        } catch (JSONException e) {
            // Si no existen mas angeles relacionados.
            jsonUri = new JSONObject();
            jsonUri.put("uri", this.manager.getSnsObject().getConfigurationManager().getConfigHostApplication()
                    + "SNSdataBaseIntegratorServer/resources/settingsAngelss/" + jsonAngel.getString("uidAngel")
                    + "/settingsFilterCollection/" + this.uid + "/");

            JSONObject jsonUriUserSettings = new JSONObject();
            jsonUriUserSettings.put("settingsFilter", jsonUri);

            jsonAngel.put("settingsFilterCollection", jsonUriUserSettings);
        }

        // Retornamos el angel con las relaciones actualizadas
        return jsonAngel;
    }
    
    /**
     * Obtiene la informacion de un angel actualizada con el filtro marcado por el parametro desFilter.
     * 
     * @param jsonUpdateAngel
     * @param strAngelsSelected
     * @param desFilter
     * @return
     * @throws JSONException 
     */
    public JSONObject updateInformationAngelForFilter(JSONObject jsonUpdateAngel, String desFilter) throws JSONException {
        logger.info(this.manager.getUserSettingsDAO().getUid() + " - updateInformationAngelForFilter: Inicio updateInformationAngelForFilter para filtro " + desFilter + "...");
        String[] arrayAngelsSelected = this.angels.split(";");

        for (int i = 0; i < arrayAngelsSelected.length; i++) {
            if (arrayAngelsSelected[i].equals(jsonUpdateAngel.get("uidAngel"))) {
                jsonUpdateAngel = getJsonAngelWithFilterRelationship(jsonUpdateAngel);
            }
        }
        
        logger.info(this.manager.getUserSettingsDAO().getUid() + " - updateInformationAngelForFilter: Fin updateInformationAngelForFilter para filtro " + desFilter + "!!");
        return jsonUpdateAngel;
    }
    
    /**
     * Borra del objeto angel la relacion que le unia a un filtro.
     *
     * @param jsonAngel JSONObject con los datos del angel
     * @return JSONObject del angel con las relaciones con el filtro eliminadas.
     * @throws JSONException
     */
    public JSONObject deleteAngelFromFilterAngelCollection(JSONObject jsonAngel) throws JSONException {
        logger.info(this.manager.getUserSettingsDAO().getUid() + " - deleteAngelFromFilterAngelCollection: Inicio deleteAngelFromFilterAngelCollection para el filtro: " + this.typeFilter);

        JSONObject jsonUri = new JSONObject();
        jsonUri.put("uri", this.manager.getSnsObject().getConfigurationManager().getConfigHostApplication() + 
                "SNSdataBaseIntegratorServer/resources/settingsAngelss/" + jsonAngel.getString("uidAngel") + 
                "/settingsFilterCollection/");
        jsonAngel.put("settingsFilterCollection", jsonUri);

        logger.info(this.manager.getUserSettingsDAO().getUid() + " - deleteAngelFromFilterAngelCollection: Fin deleteAngelFromFilterAngelCollection para el filtro: " + this.typeFilter);
        return jsonAngel;
    }
    
    /* Actualiza los angeles de un usuario. Puede lanzar excepciones del tipo JSONException.
     *
     * @param jsonAngel
     * @param modo
     * @param des
     * @throws JSONException
     */
    public void putAngelInCollectionFilter(JSONObject jsonAngel) throws JSONException {
        logger.info(this.manager.getUserSettingsDAO().getUid() + " - setCollectionAngels: Inicio setCollectionAngels...");
        jsonAngel = getJsonAngelWithFilterRelationship(jsonAngel);
        
        try{
            this.manager.getSnsObject().getClient().userSettings_setNewAngelsCollectionByUid(String.class, jsonAngel.getString("uidAngel"), jsonAngel);
        } catch(UniformInterfaceException ex){
            logger.error(this.manager.getUserSettingsDAO().getUid() + " - setCollectionAngels: Excepcion capturada UniformInterfaceException: " + ex.getMessage());
        }    
        logger.info(this.manager.getUserSettingsDAO().getUid() + " - setCollectionAngels: Fin setCollectionAngels...");
    }
}