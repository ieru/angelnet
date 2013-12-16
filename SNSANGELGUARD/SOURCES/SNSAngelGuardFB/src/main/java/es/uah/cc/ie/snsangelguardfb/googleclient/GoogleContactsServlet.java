/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.snsangelguardfb.googleclient;


import java.io.IOException;
import java.net.URL;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.Link;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.util.ServiceException;
import es.uah.cc.ie.snsangelguardfb.SNSAngelGuardFBManager;
import java.io.FileNotFoundException;
import java.net.URLDecoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.openide.util.Exceptions;

/**
 * Servlet de peticion de datos al API de Google Contacts para obtener los datos
 * de todos los contactos de un usuario a partir de su identificador de sesion
 * autenticado en la aplicacion mediante su access_token. Devolvera en un JSONArray
 * todos aquellos contactos que posean una direccion de correo.
 * 
 * @author josejavierblecuadepedro1
 */
public class GoogleContactsServlet extends HttpServlet {
    
    /** Logger Class */
    private static Logger logger = Logger.getLogger(GoogleContactsServlet.class);
    
    /** Nombre de la aplicacion dada de alta en Google API Console */
    public static final String APP_NAME = "SNSAngelGuardFB";

    /** Identificador de la aplicacion generado automaticamente al dar de alta la apliacion en Google API Console*/
    public static final String CLIENT_ID = "541921444258.apps.googleusercontent.com";
    
    /** Identificador secreto de la aplicacion generado automaticamanete al dar de alta la aplicacion en google API Console */
    public static final String CLIENT_SECRET = "iU3fBdbX_2PFSTwsGpVQe-Dm";
    
    /** URL de peticion de datos a Google */
    public static final String URL_GET_CONTACTS = "https://www.google.com/m8/feeds/contacts/default/full?max-results=999999";
    
    /** Manager principal de la aplicacion */
    private SNSAngelGuardFBManager snsObject;
    
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response){
        try {
            // Cargamos el objeto manager de la aplicacion
            this.snsObject = SNSAngelGuardFBManager.getSessionInstance(request);
            
            // Establecemos el juego de caracteres en la respuesta de la peticion HTTP
            response.setContentType("text/html;charset=UTF-8");
            
            // Obtenemos de la peticion HTTPServletRequest la sesion de autenticacion en Google
            String access_token = URLDecoder.decode(request.getParameter("access_token"), "UTF8");
            
            // Obtenemos los angeles anteriormente seleccionados, para que no vuelvan a ser devueltos
            String hdAngelsGoogleSelected = URLDecoder.decode(request.getParameter("hdAngelsGoogleSelected"), "UTF8");
            
            // Creamos el Servicio de Google para realizar las llamadas a su API
            ContactsService contactsService = new ContactsService(APP_NAME);
            contactsService.setHeader("Authorization", "Bearer " + access_token);
         
            // Obtenemos el array con todos los contactos en Google que seran pintados en la aplicacion
            JSONArray jsonArrayContacts = getJSONArrayAllContactsUser(contactsService, hdAngelsGoogleSelected);
            
            // Devolvemos los datos obtenidos al cliente
            response.setContentType("application/json");
            response.getWriter().write(jsonArrayContacts.toString());
        } catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (ServiceException | JSONException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);

    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
    /**
     * Obtiene un JSONArray con todos los datos de todos los contactos del usuario
     * autenticado en la aplicacion.
     * 
     * @param myService Conexion con Google API Console
     * @param hdAngelsGoogleSelected Angeles anteriormente seleccionados
     * @throws ServiceException
     * @throws IOException
     * @throws JSONException 
     */
    public JSONArray getJSONArrayAllContactsUser(ContactsService myService, String hdAngelsGoogleSelected)
            throws ServiceException, IOException, JSONException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - printAllContacts: Inicio printAllContacts...");
        
        // Realizamos la peticion
        URL feedUrl = new URL(URL_GET_CONTACTS);
        ContactFeed resultFeed = myService.getFeed(feedUrl, ContactFeed.class);
        
        // Formateamos el resultado de la peticion
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - printAllContacts: " + resultFeed.getTitle().getPlainText());
        JSONArray arrayContacts = new JSONArray();
        
        for (ContactEntry entry : resultFeed.getEntries()) {
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - printAllContacts: Obteniendo el nombre del contacto...");
            String fullName = "";
            if (entry.hasName()) {
                Name name = entry.getName();
                if (name.hasFullName()) {
                    fullName = name.getFullName().getValue();
                    logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - printAllContacts: Nombre del contacto: " + fullName);
                } 
            } else {
                logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - printAllContacts: No se ha encontrado un nombre para este contacto...");
            }
                      
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - printAllContacts: Obteniendo el link de la foto del contacto...");
            Link photoLink = entry.getContactPhotoLink();
            String photoLinkHref = "";
            
            if(photoLink.getHref() != null){
                photoLinkHref = URLDecoder.decode(photoLink.getHref(), "UTF8");
                logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - printAllContacts: Link Photo: " + photoLinkHref);
            }else{
                logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - printAllContacts: No se ha encontrado un link a la foto de este contacto...");
            }
            
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - printAllContacts: Obteniendo el email del contacto...");
            for (Email email : entry.getEmailAddresses()) {
                String emailAddress = URLDecoder.decode(email.getAddress(), "UTF8");
            
                // Si el contacto no esta seleccionado como angel en la aplicacion, sera devuelto como dato para ser mostrado por pantalla
                if (!isSelectedAngel(emailAddress, hdAngelsGoogleSelected)) {
                    JSONObject jsonContact = getJSONContact(fullName, emailAddress, photoLinkHref);

                    // Si se ha creado el objeto json, lo introducimos en el array de contactos
                    if (jsonContact != null) {
                        arrayContacts.put(jsonContact);
                    }
                }
            }
            
        }
        
         logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - printAllContacts: Fin printAllContacts...");
         
         return arrayContacts;  
    }
    
    
    /**
     * Devuelve un objeto JSON agrupando los datos de un contacto de Google. Si 
     * se produce alguna excepcion en el proceso, el metodo devolvera el valor null.
     * 
     * @param name
     * @param email
     * @param linkPhoto
     * @return JSONObject 
     */
    private JSONObject getJSONContact(String name, String email, String linkPhoto){
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getJSONContact: Inicio getJSONContact para el contacto: " + email);
        JSONObject jsonContact = null;
        
        try {
            jsonContact = new JSONObject();
            
            // Introducimos los datos del contacto en el objeto JSON
            jsonContact.put("nameContact", name);
            jsonContact.put("emailContact", email);
            jsonContact.put("linkPhoto", linkPhoto);
            
        } catch (JSONException ex) {
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getJSONContact: Excepcion capturada JSONException: " + ex.getMessage());
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getJSONContact: No se devolvera informacion del contacto actual...");
        }
        
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getJSONContact: Valor del objeto JSON de salida del contacto: " + jsonContact);
        
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getJSONContact: Fin getJSONContact para el contacto: " + email);

        return jsonContact;
    }
    
    /**
     * Comprueba si el email de un contacto ya esta seleccionado como angel en la aplicacion.
     * 
     * @param email
     * @param hdAngelsGoogleSelected
     * @return boolean 
     */
    private boolean isSelectedAngel(String email, String hdAngelsGoogleSelected){
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isSelectedAngel: Inicio isSelectedAngel para el contacto: " + email);
        boolean isActiveContact = false;
        
        if(hdAngelsGoogleSelected != null && !hdAngelsGoogleSelected.equals("")){
            String[] arrayAngelsSelected = hdAngelsGoogleSelected.split(";");
            
            for(String angel: arrayAngelsSelected){
                if(!angel.equals("")){
                    if(angel.equals(email)){
                        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isSelectedAngel: El contacto " + email + " ya esta seleccionado "
                                + "en la aplicacion, por lo tanto, no sera pintado...");
                        isActiveContact = true;
                        break;
                    }
                }
            }
        }
        
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isSelectedAngel: Fin isSelectedAngel para el contacto: " + email);
        return isActiveContact;
    }
}
