package es.uah.cc.ie.snsangelguardfb.sources.filtersfuncionality;

import es.uah.cc.ie.snsangelguardfb.ILifeCycleFilter;
import com.sun.jersey.api.client.UniformInterfaceException;
import es.uah.cc.ie.snsangelguardfb.exception.InterDataBaseException;
import es.uah.cc.ie.snsangelguardfb.exception.InterEmailException;
import es.uah.cc.ie.snsangelguardfb.exception.InterProcessException;
import es.uah.cc.ie.snsangelguardfb.facebookclient.data.FriendsFacebook;
import es.uah.cc.ie.snsangelguardfb.SNSAngelGuardFBManager;
import java.io.IOException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Clase que controla la ejecucion el filtro de amigos.
 *
 * @author tote
 */
public class FriendsFilterFuncionality implements ILifeCycleFilter, IKeyArgsFilter {
    
    /** Maxima diferencia de edad permitida para los contactos */
    private static final int MAX_AGE_LIMITED = 5;

    /** Logger Class */
    private static Logger logger = Logger.getLogger(FriendsFilterFuncionality.class);

    /** Clase Manager de la aplicacion */
    private SNSAngelGuardFBManager snsObject;
    
    /** Identificador del filtro */
    private String idFilter;

    /**
     * Constructor de clase sin argumentos.
     */
    public FriendsFilterFuncionality() { }

    /**
     * Obtiene el objeto manager de la aplicaci?n.
     *
     * @return SNSAngelGuardFBManager
     */
    public SNSAngelGuardFBManager getSnsObject() {
        return snsObject;
    }

    /**
     * Establece el objeto manager de la aplicaci?n.
     *
     * @param snsObject SNSAngelGuardFBManager
     */
    public void setSnsObject(SNSAngelGuardFBManager snsObject) {
        this.snsObject = snsObject;
    }

    /**
     * Convierte una URI obtenida para un friendFacebook desde servidor al
     * formato correcto para volver a ser introducida en la relacion.
     *
     * @param strURI URI obtenida de servidor.
     * @param strUidFriend UID del friendFacebook con la que se va a asociar.
     * @return URI asociada al friendFacebook.
     */
    public String getURIConverted(String strURI, String strUidFriend){
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getURIConverted: Reemplazando valor URI: " + strURI);
        strURI = strURI.replace("friendsFacebooks/userFacebookCollection/", "friendsFacebooks/" + strUidFriend + "/userFacebookCollection/");
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getURIConverted: Nuevo valor URI: " + strURI);

        return strURI;
    }

    /**
     * Convierte un array de objetos JSON con URIs obtenidas desde servidor en
     * un array de objetos JSON con URIs preparadas para volver a ser relacionadas. Podr? lanzar excepciones del tipo JSONException.
     *
     * @param jsonArrayUserFacebook Array JSON de objetos de servidor.
     * @param strUidFriend UID del friendFacebook con la que se va a asociar.
     * @return Array JSON con los objetos preparados para ser relacionados.
     * @throws JSONException
     */
    public JSONArray convertURIServerToURIUserFacebook(JSONArray jsonArrayUserFacebook, String strUidFriend) throws JSONException{
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - convertURIServerToURIUserFacebook: Inicio convertURIServerToURIUserFacebook para amigo: " + strUidFriend);
        JSONArray jsonArrayNewUserFacebook = new JSONArray();

        for(int i = 0; i < jsonArrayUserFacebook.length(); i++){
            JSONObject jsonUriUserFacebook = jsonArrayUserFacebook.getJSONObject(i);
            String strUri = getURIConverted(jsonUriUserFacebook.getString("uri"), strUidFriend);

            jsonUriUserFacebook.put("uri", strUri);
            jsonArrayNewUserFacebook.put(jsonUriUserFacebook);
        }

        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - convertURIServerToURIUserFacebook: Fin convertURIServerToURIUserFacebook para amigo: " + strUidFriend);
        return jsonArrayNewUserFacebook;
    }

    /**
     * Obtiene un array con todas las relacciones de un usuario de Facebook con
     * los usuarios de la aplicacion. Devolvera un array con todas las URIs de 
     * los usuarios de la aplicacion ya preparadas para ser insertadas en la base
     * de datos.
     * 
     * @param jsonFriend
     * @return JSONArray
     */
    private JSONArray getRelationshipFriendsFacebook(JSONObject jsonFriend) {
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getRelationshipFriendsFacebook: Inicio getRelationshipFriendsFacebook...");

        JSONArray jsonArrayRelationship = new JSONArray();

        try {
            String collection = this.snsObject.getClient().userFacebook_isNewFriendsFacebookByUid(String.class, jsonFriend.getString("userUid"));
            JSONObject jsonAux = new JSONObject(collection);
            JSONObject jsonCollection = jsonAux.getJSONObject("userFacebookCollection");

            try {
                //Si es un array
                jsonArrayRelationship = jsonCollection.getJSONArray("userFacebook");

                // Convertimos todas las URI del array al formato permitido
                jsonArrayRelationship = convertURIServerToURIUserFacebook(jsonArrayRelationship, jsonFriend.getString("userUid"));

            } catch (JSONException ex) {
                if (jsonCollection.length() == 1) {
                    // Si unicamente hay un usuario enlazado con este friendFacebook              
                    JSONObject jsonUserFacebook = jsonCollection.getJSONObject("userFacebook");
                    jsonArrayRelationship.put(jsonUserFacebook);

                    // Convertimos todas las URI del array al formato permitido
                    jsonArrayRelationship = convertURIServerToURIUserFacebook(jsonArrayRelationship, jsonFriend.getString("userUid"));
                }
            }
        } catch (JSONException ex) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getRelationshipFriendsFacebook: Excepcion capturada JSONException: " + ex.getMessage());
        }

        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getRelationshipFriendsFacebook: Fin getRelationshipFriendsFacebook...");
        return jsonArrayRelationship;
    }
    
    /**
     * Incluye a un amigo en la relacion con un usuario de Facebook.
     *
     * @param jsonFriend Datos de un amigo de Facebook.
     */
    public void setCollectionFriendsFacebook(JSONObject jsonFriend) {
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - setCollectionFriendsFacebook: Inicio setCollectionFriendsFacebook...");

        Long uidLong = (new Double(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid())).longValue();

        try {
            JSONArray jsonArrayRelationship = getRelationshipFriendsFacebook(jsonFriend);
            JSONObject jsonUri = new JSONObject();
            JSONObject jsonCollection = new JSONObject();

            // Introducimos la nueva URI del friendFacebook que no estaba relacionado con el usuario de Facebook
            jsonUri.put("uri", this.snsObject.getConfigurationManager().getConfigHostApplication() + "SNSdataBaseIntegratorServer/resources/friendsFacebooks/" + jsonFriend.getString("userUid") + "/userFacebookCollection/" + uidLong + "/");
            jsonArrayRelationship.put(jsonUri);

            jsonCollection.put("userFacebook", jsonArrayRelationship);
            jsonFriend.put("userFacebookCollection", jsonCollection);

            this.snsObject.getClient().userFacebook_setFriendsFacebookByUid(String.class, jsonFriend, jsonFriend.getString("userUid"));
        } catch (JSONException ex) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - setCollectionFriendsFacebook: Excepcion capturada JSONException: " + ex.getMessage());
        }
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - setCollectionFriendsFacebook: Fin setCollectionFriendsFacebook...");
    }
    
    /**
     * Establece las relaciones con otros usuarios de la aplicacion de un contacto de 
     * Facebook.
     * 
     * @param jsonFriend
     * @param jsonArrayRelationship 
     */
    public void updateCollectionFriendsFacebook(JSONObject jsonFriend, JSONArray jsonArrayRelationship) {
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateCollectionFriendsFacebook: Inicio updateCollectionFriendsFacebook...");

        try {
            JSONObject jsonUriUserFacebook = new JSONObject();
            jsonUriUserFacebook.put("userFacebook", jsonArrayRelationship);
            jsonFriend.put("userFacebookCollection", jsonUriUserFacebook);

            this.snsObject.getClient().userFacebook_setFriendsFacebookByUid(String.class, jsonFriend, jsonFriend.getString("userUid"));

        } catch (JSONException ex) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateCollectionFriendsFacebook: Excepcion capturada JSONException: " + ex.getMessage());
        }
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateCollectionFriendsFacebook: Fin updateCollectionFriendsFacebook...");
    }

    /**
     * Relaciona un nuevo amigo con el conjunto de amigos del usuario en la base de datos SocialNetwork. 
     * Podra lanzar excepciones del tipo JSONException, InterDataBaseException, InterProcessException o InterEmailException.
     *
     * @param jsonFriendFacebook Nuevo amigo de Facebook.
     * @throws JSONException
     */
    public void updateRelationshipNewFriend(JSONObject jsonFriendFacebook) throws JSONException, InterDataBaseException, InterProcessException, InterEmailException{
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateRelationshipNewFriend: Inicio updateRelationshipNewFriend...");
        try {
            setCollectionFriendsFacebook(jsonFriendFacebook);
            logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateRelationshipNewFriend: Nuevo amigo " + jsonFriendFacebook.getString("userUid") + " relacionado en base de datos con " + this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid());
        } catch (UniformInterfaceException e) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateRelationshipNewFriend: Excepcion capturada UniformInterfaceException: " + e.getMessage());
            logger.fatal(e);
            this.snsObject.getExceptionManager().initControlException(e);
        }
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateRelationshipNewFriend: Fin updateRelationshipNewFriend...");
    }

    /**
     * Actualiza los datos de un amigo del usuario en base de datos.
     * Podra lanzar excepciones del tipo JSONException, InterDataBaseException, InterProcessException o InterEmailException.
     * 
     * @param jsonFriend
     * @throws JSONException
     * @throws InterDataBaseException
     * @throws InterProcessException
     * @throws InterEmailException 
     */
    public void updateFriend(JSONObject jsonFriend) throws JSONException, InterDataBaseException, InterProcessException, InterEmailException{
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateFriend: Inicio updateFriend para amigo " + jsonFriend.getString("userUid"));
        
        try {
            this.snsObject.getClient().userFacebook_setFriendsFacebookByUid(String.class, jsonFriend, jsonFriend.getString("userUid"));
        } catch (UniformInterfaceException ex) {
            if(ex.getResponse().getStatus() >= 200 && ex.getResponse().getStatus() < 300){
                logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateFriend: La informacion del amigo " + jsonFriend.getString("userUid") + " ha sido actualizada!!");
            }else{
                logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateFriend: No se ha podido actualizar la informacion del amigo: " + jsonFriend.getString("userUid"));
                logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateFriend: Excepcion capturada UniformInterfaceException: " + ex.getMessage());
                this.snsObject.getExceptionManager().initControlException(ex);
            }
        }
        
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateFriend: Fin updateFriend para amigo " + jsonFriend.getString("userUid"));
    }
    /**
     * Actualiza los datos de un amigo del usuario en la base de datos SocialNetwork. 
     * Podra lanzar excepciones del tipo JSONException, InterDataBaseException, InterProcessException o InterEmailException.
     *
     * @param jsonFriendFacebook Amigo de Facebook.
     * @throws JSONException
     */
    public void updateDatesFriend(JSONObject jsonFriend) throws JSONException, InterDataBaseException, InterProcessException, InterEmailException{
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateDatesFriend: Inicio updateDatesFriend...");

        // Recuperamos las relaciones con los usuarios de la aplicacion
        JSONArray jsonArrayRelationship = getRelationshipFriendsFacebook(jsonFriend);
        
        // Actualizamos el usuario de Facebook en la base de datos
        this.updateFriend(jsonFriend);
        try {     
            // Reestablecemos las relaciones del usuario de Facebook
            updateCollectionFriendsFacebook(jsonFriend, jsonArrayRelationship);
        } catch (UniformInterfaceException ex) {
            if (ex.getResponse().getStatus() >= 200 && ex.getResponse().getStatus() < 300) {
                logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateDatesFriend: Las relaciones con el amigo " + jsonFriend.getString("userUid") + " han sido actualizadas!!");
            } else {
                logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateDatesFriend: No se ha podido actualizar la informacion del amigo: " + jsonFriend.getString("userUid"));
                logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateDatesFriend: Excepcion capturada UniformInterfaceException: " + ex.getMessage());
                this.snsObject.getExceptionManager().initControlException(ex);
            }
        }

        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateDatesFriend: Fin updateDatesFriend...");
    }
    
    /**
     * Obtiene de Facebook todos los datos necesarios para almacenar un nuevo amigo en la base de datos. 
     * Podr? lanzar excepciones del tipo UniformInterfaceException, IOException, JSONException, InterDataBaseException, InterProcessException o InterEmailException.
     *
     * @param friendUid Identificador del amigo del usuario en Facebook.
     * @throws UniformInterfaceException
     * @throws IOException
     * @throws JSONException
     */
    public void getNewFriend(String friendUid) throws UniformInterfaceException, IOException, JSONException, InterDataBaseException, InterProcessException, InterEmailException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getNewFriend: Inicio getNewFriend para el amigo: " + friendUid);

        String query = "SELECT uid,name,birthday_date,pic_square FROM user WHERE uid=" + friendUid;
        
        List<FriendsFacebook> friendsFacebookList = this.snsObject.getFacebookQueryClient().executeQuery(query, FriendsFacebook.class);

        if (!friendsFacebookList.isEmpty()) {
            JSONObject jsonFriendFacebook = friendsFacebookList.get(0).toJson();
            try {
                jsonFriendFacebook.put("userBirthday", this.snsObject.getDateTimeUtilities().formatearFecha(friendsFacebookList.get(0).getBirthdayDate()));
                
                if (isNewInFriendsFacebook(jsonFriendFacebook)) {
                    try{
                        this.snsObject.getClient().userFacebook_setNewFriendFacebook(String.class, jsonFriendFacebook);
                        updateRelationshipNewFriend(jsonFriendFacebook);
                    } catch (UniformInterfaceException e){
                        logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getNewFriend: No se ha podido actualizar la informacion del usuario: " + jsonFriendFacebook.toString());
                        logger.fatal(e);
                    }
                } else if(isNewFriend(jsonFriendFacebook)){
                    updateRelationshipNewFriend(jsonFriendFacebook);
                } else
                    updateDatesFriend(jsonFriendFacebook);
            } catch (JSONException ex) {
                logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getNewFriend: Excepcion capturada JSONException: " + ex.getMessage());
                logger.fatal(ex);
            }
        }

        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getNewFriend: Fin getNewFriend para el amigo: " + friendUid);
    }

    /**
     * Obtiene todos los amigos de un usuario en Facebook y los almacena en la base de datos SocialNetwork.
     * Podr? lanzar excepciones del tipo UniformInterfaceException, IOException, InterDataBaseException, InterProcessException o InterEmailException.
     * 
     * @throws UniformInterfaceException
     * @throws IOException
     */
    public void getUserFriends() throws UniformInterfaceException, IOException, InterDataBaseException, InterProcessException, InterEmailException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getUserFriends: Inicio getUserFriends...");

        //Se obtienen los amigos del usuario
        List<String> myFriends = this.snsObject.getFacebookRestClient().executeForList("friends.get", String.class);

        for(int j = 0; j < myFriends.size(); j++){
            try {
                this.getNewFriend(myFriends.get(j));
            } catch (JSONException ex) {
                logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getUserFriends: Excepcion capturada JSONException: " + ex.getMessage());
                logger.fatal(ex);
                this.snsObject.getExceptionManager().initControlException(ex);
            }
        }
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getUserFriends: Fin getUserFriends...");
    }

    /**
     * Comprueba si un amigo de un usuario ya esta almacenado en la coleccion de amigos de la base de datos SocialNetwork.
     * Podr? lanzar excepciones del tipo JSONException.
     *
     * @param friend Datos del amigo de Facebook.
     * @return boolean
     * @throws JSONException
     */
    public boolean isNewFriend(JSONObject friend) throws JSONException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isNewFriend: Inicio isNewFriend para el amigo: " + friend.getString("userUid"));
        try {
            Long uidLong = (new Double(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid())).longValue();
            String result = this.snsObject.getClient().userFacebook_getFriendsFacebookByUid(String.class, uidLong.toString(), friend.getString("userUid"));

            if(result.contains("userFacebookCollection/" + uidLong.toString())){
                logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isNewFriend: El amigo " + friend.getString("userUid") + " existe en la coleccion de amigos del usuario...");
                return false;
            }else{
                logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isNewFriend: El amigo " + friend.getString("userUid") + " no existe en la coleccion de amigos del usuario...");
                return true;
            }
        } catch (NumberFormatException | JSONException | UniformInterfaceException e) {
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isNewFriend: El amigo " + friend.getString("userUid") + " no existe en la base de datos...");
            return true;
        }
    }

    /**
     * Comprueba si un amigo del usuario es nuevo dentro de la base de datos. Podr? lanzar excepciones del tipo JSONException.
     *
     * @param friend Datos del nuevo amigo.
     * @return boolean
     * @throws JSONException
     */
    public boolean isNewInFriendsFacebook(JSONObject friend) throws JSONException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isNewInFriendsFacebook: Inicio isNewInFriendsFacebook para el amigo: " + friend.getString("userUid"));
        try {
            this.snsObject.getClient().userFacebook_isNewFriendsFacebookByUid(String.class, friend.getString("userUid"));
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isNewInFriendsFacebook: El amigo " + friend.getString("userUid") + " existe en la base de datos...");
            return false;
        } catch (JSONException | UniformInterfaceException e) {
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isNewInFriendsFacebook: El amigo " + friend.getString("userUid") + " no existe en la base de datos...");
            return true;
        }
    }

    /**
     * Realiza el chequeo del filtro. Mandara una lista, en formato HTML, con todos los amigos
     * que tengan una edad mayor a cinco anios de diferencia con el usuario o no hayan especificado
     * su edad. Podr? lanzar excepciones del tipo UniformInterfaceException, IOException, NoSuchProviderException, MessagingException o JSONException.
     *
     * @return Lista en formato HTML con el resultado del filtro.
     * @throws UniformInterfaceException
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws MessagingException
     * @throws JSONException
     */
    public String checkFriends() throws UniformInterfaceException, IOException, NoSuchProviderException, MessagingException, JSONException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkFriends: Inicio checkFriends...");
        String informe = "";
        String menDB[] = this.snsObject.getStringUtilities().stringToArray(this.snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getMailNotification());
        Long uidLong = (new Double(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid().toString())).longValue();

        JSONObject jsonUserFacebook = new JSONObject(this.snsObject.getClient().userFacebook_getUserFacebookByUid(String.class, uidLong.toString()));
        String userBirthday = jsonUserFacebook.getString("birthdayDate");
        int intFecUser = Integer.parseInt(userBirthday.substring(6, userBirthday.length()));


        JSONArray jsonFriendsFacebook = this.snsObject.getUserSettingsDaoManager().getFacebookFriendsDB(uidLong.toString());

        for (int i = 0; i < jsonFriendsFacebook.length(); i++) {
            JSONObject jsonFriend = jsonFriendsFacebook.getJSONObject(i);
            
            // Obtenemos la clave cifrada para el amigo
            String uidCifrada = this.snsObject.getGenericFilter().cifrarUIDFriend(jsonFriend.getString("userUid"));

            if (jsonFriend.get("userBirthday").toString().contains("00-00-") ||
                    jsonFriend.getString("userBirthday").substring(6, jsonFriend.getString("userBirthday").length()).equals("0000")) {
                informe += uidCifrada + menDB[6] + " <br>";
            } else if (isAgeLimit(intFecUser, jsonFriend.getString("userBirthday").substring(6, jsonFriend.getString("userBirthday").length()))) {
                informe += uidCifrada + menDB[7] + this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUserName() + ". <br>";
            }
        }

        if (informe.equals("")) {
            informe = menDB[8];
        }

        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkFriends: Fin checkFriends...");
        return informe;
    }

    /**
     * Comprueba si la edad de un amigo de Facebook tiene una diferencia mayor de cinco anios
     * a la del usuario o no ha especificado su edad. Podra lanzar excepciones del tipo UniformInterfaceException, IOException o JSONException.
     *
     * @param birthdayUser Fecha de nacimiento del usuario.
     * @param anio A?o de nacimiento del amigo de Facebook.
     * @return boolean
     * @throws UniformInterfaceException
     * @throws IOException
     * @throws JSONException
     */
    public boolean isAgeLimit(int birthdayUser, String anio) throws UniformInterfaceException, IOException, JSONException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isAgeLimit: Usuario nacido en fecha: " + birthdayUser);
        boolean result = false;

        int birthdayFriend = Integer.parseInt(anio);
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isAgeLimit: Amigo  nacido en el a?o: " + birthdayFriend);

        // Si el anio de naciemiento del amigo es anterior que el del usuario, pasamos a comparar
        if (birthdayUser > birthdayFriend) {
            // Obtenemos la diferencia de edad
            int diff = birthdayUser - birthdayFriend;

            // Si es mayor que la edad l?mite
            if (diff >= MAX_AGE_LIMITED) {
                result = true;
            }
        }

        return result;
    }

    @Override
    public void init(SNSAngelGuardFBManager snsObject, String id) {
        this.snsObject = snsObject;
        this.idFilter = id;
    }

    @Override
    public String executeFilter(Map<String, Object> args) throws Exception {
        return checkFriends();
    }

    @Override
    public String getId() {
        return this.idFilter;
    }

    @Override
    public void updateInformationFacebook() throws Exception {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateInformationFacebook: Inicio updateInformationFacebook...");
        this.getUserFriends();
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateInformationFacebook: Fin updateInformationFacebook!!");
    }

    @Override
    public void getInformationFacebook() throws Exception {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getInformationFacebook: Inicio getInformationFacebook...");
        this.getUserFriends();
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getInformationFacebook: Fin getInformationFacebook!!");
    }
}
