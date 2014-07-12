package es.uah.cc.ie.snsangelguardfb.sources.filtersfuncionality;

import es.uah.cc.ie.snsangelguardfb.ILifeCycleFilter;
import com.sun.jersey.api.client.UniformInterfaceException;
import es.uah.cc.ie.snsangelguardfb.SNSAngelGuardFBManager;
import java.io.IOException;
import java.security.NoSuchProviderException;
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
        String userBirthday = !jsonUserFacebook.isNull("birthdayDate") ? jsonUserFacebook.getString("birthdayDate") : "00-00-0000";
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
        this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUserFriends();
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateInformationFacebook: Fin updateInformationFacebook!!");
    }

    @Override
    public void getInformationFacebook() throws Exception {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getInformationFacebook: Inicio getInformationFacebook...");
        this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUserFriends();
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getInformationFacebook: Fin getInformationFacebook!!");
    }
}
