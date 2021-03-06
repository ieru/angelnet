package es.uah.cc.ie.snsangelguardfb.sources.filtersfuncionality;

import es.uah.cc.ie.snsangelguardfb.ILifeCycleFilter;
import bsh.ParseException;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.sun.jersey.api.client.UniformInterfaceException;
import es.uah.cc.ie.snsangelguardfb.facebookclient.data.PostWallFacebook;
import es.uah.cc.ie.snsangelguardfb.facebookclient.data.StreamCommentsFacebook;
import es.uah.cc.ie.snsangelguardfb.SNSAngelGuardFBManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Controla la ejecucion del filtro de control del lenguaje.
 * 
 * @author tote
 */
public class WallFilterFuncionality implements ILifeCycleFilter, IKeyArgsFilter {

    /** Logger Class */
    private static Logger logger = Logger.getLogger(WallFilterFuncionality.class);
    
    /** Path lexicalFile ingles */
    private static final String PATH_LEXICAL_FILE_EN = "badWords_en.txt";
    
    /** Path lexicalFile castellano */
    private static final String PATH_LEXICAL_FILE_ES = "badWords_es.txt";

    /** Clase Manager de la aplicacion */
    private SNSAngelGuardFBManager snsObject;
    
    /** Identificador del filtro */
    private String idFilter;

    /**
     * Constructor de clase sin par?metros.
     */
    public WallFilterFuncionality() { }

    /**
     * Obtiene el objeto Manager de la aplicacio.
     *
     * @return SNSAngelGuardFBManager
     */
    public SNSAngelGuardFBManager getSnsObject() {
        return snsObject;
    }

    /**
     * Establece el objeto Manager de la aplicaci?n.
     *
     * @param snsObject SNSAngelGuardFBManager
     */
    public void setSnsObject(SNSAngelGuardFBManager snsObject) {
        this.snsObject = snsObject;
    }

    /**
     * Obtiene todos los post del muro de Facebook del usuario y los almacena en
     * base de datos. Puede lanzar excepciones del tipo UniformInterfaceException, MySQLIntegrityConstraintViolationException, IOException o JSONException.
     *
     * @throws UniformInterfaceException
     * @throws MySQLIntegrityConstraintViolationException
     * @throws IOException
     * @throws JSONException
     */
    public void getPostWall() throws UniformInterfaceException, MySQLIntegrityConstraintViolationException, IOException, JSONException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getPostWall: Inicio getPostWall...");
        Long uidLong = (new Double(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid().toString())).longValue();

        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getPostWall: Actualizando base de datos del muro del usuario: " + this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid());
        String query = "SELECT post_id,viewer_id,app_id,source_id,updated_time,created_time,filter_key,attribution,actor_id,target_id,message,app_data,attachment,type,permalink,xid FROM stream WHERE source_id=" + uidLong.toString() + " LIMIT 10000;";

        List<PostWallFacebook> postList = this.snsObject.getFacebookQueryClient().executeQuery(query, PostWallFacebook.class);

        String respuesta = "";
        
        for(int i = 0; i < postList.size(); i++){     
            PostWallFacebook auxPost = postList.get(i);

            JSONObject jsonAuxPost = auxPost.toJson();

            logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getPostWall: Comentario " + i + ": " + jsonAuxPost.toString());
            logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getPostWall: Identificador del comentario: " + jsonAuxPost.getString("postId"));

            respuesta = this.snsObject.getClient().userFacebook_setNewStreamComentsByUid(String.class, jsonAuxPost, uidLong.toString());
            logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getPostWall: Comentarios al post: " + getStreamComents(jsonAuxPost.getString("postId"), "newComment"));
        }

        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getPostWall: Fin getPostWall...");
    }

    /**
     * Obtiene de la base de datos los comentarios a un post del muro del usuario. Tendr? dos modos de funcionamiento:
     * - modo = newComment: Obtendra todos los comentarios almacenados para un post en la base de datos.
     * - modo = updateComment: Obtendra todos los comentarios almacenados para un post hasta la fecha indicada por el campo de la tabla user_settings backUpCheck.
     * Puede lanzar excepciones del tipo JSONException.
     *
     * @param postId String Identificador del post del que queremos obtener los comentarios.
     * @param modo String Modo de funcionamiento.
     * @return String Comentarios obtenidos para un post.
     * @throws JSONException
     */
    public String getStreamComents(String postId, String modo) throws JSONException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getStreamComents: Inicio getStreamComents...");
        String query = "";
        
        switch (modo) {
            case "newComment":
                logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getStreamComents: Modo de funcionamiento: " + modo);
                query = "SELECT xid,object_id,post_id,fromid,time,text,id,username,reply_xid FROM comment WHERE post_id ='" + postId + "' ;";
                break;
            case "updateComment":
                logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getStreamComents: Modo de funcionamiento: " + modo);
                query = "SELECT xid,object_id,post_id,fromid,time,text,id,username,reply_xid FROM comment WHERE post_id ='" + postId + "' AND time>" + (this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getLastCheck().getTime() / 1000) + ";";
                break;
        }

        List<StreamCommentsFacebook> streamCommentsList = this.snsObject.getFacebookQueryClient().executeQuery(query, StreamCommentsFacebook.class);
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getStreamComents: Comentarios al post " + postId + " obtenidos!!");

        for(int i = 0; i < streamCommentsList.size(); i++){
            StreamCommentsFacebook auxStreamComments = streamCommentsList.get(i);
            JSONObject jsonAuxStreamComments = auxStreamComments.toJson();
            try{
                this.snsObject.getClient().userFacebook_setComentsPost(String.class, jsonAuxStreamComments);
            } catch(Exception e){
                logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getStreamComents: No se ha podido almacenar el comentario...");
                logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getStreamComents: Excepcion capturada Exception: " + e.getMessage());
                logger.fatal(e);
            }
        }

        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getStreamComents: Fin getStreamComents...");
        return streamCommentsList.toString();
    }

    /**
     * Actualiza la base de datos con los nuevos comentarios y post que se han realizado en Facebook tras el ultimo chequeo de informacion.
     * Puede lanzar excepciones del tipo JSONException o ParseException.
     *
     * @throws JSONException
     * @throws ParseException
     */
    public void updatePostWall() throws JSONException, ParseException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updatePostWall: Inicio updatePostWall...");

        Long uidLong = (new Double(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid().toString())).longValue();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updatePostWall: Actualizando los post del muro del usuario, ultima actualizacion: " + formateador.format(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getBackupCheck().getTime()));

        String query = "SELECT post_id,viewer_id,app_id,source_id,updated_time,created_time,filter_key,attribution,actor_id,target_id,message,app_data,attachment,type,permalink,xid FROM stream WHERE source_id=" + uidLong + " AND updated_time>" + (this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getBackupCheck().getTime() / 1000) + " LIMIT 10000;";

        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updatePostWall: Query de actualizacion: " + query);

        try {
            List<PostWallFacebook> postList = this.snsObject.getFacebookQueryClient().executeQuery(query, PostWallFacebook.class);

            for (int i = 0; i < postList.size(); i++) {

                PostWallFacebook auxPost = postList.get(i);
                JSONObject jsonAuxPost = auxPost.toJson();

                logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updatePostWall: Identificador del post: " + jsonAuxPost.getString("postId"));

                if (isNewStreamPost(jsonAuxPost)) {
                    this.snsObject.getClient().userFacebook_setNewStreamComentsByUid(String.class, jsonAuxPost, uidLong.toString());
                    logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updatePostWall: Nuevo post en base de datos!!");
                    logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updatePostWall: Comentarios al post: " + getStreamComents(jsonAuxPost.getString("postId"), "newComment"));
                } else {
                    // Actualizar comentario
                    logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updatePostWall: Actualizando los comentarios a un post: " + isNewStreamPost(jsonAuxPost));
                    try {
                        this.snsObject.getClient().userFacebook_setStreamComentsByUid(String.class, jsonAuxPost, jsonAuxPost.getString("postId"), jsonAuxPost.getString("updatedTime"), jsonAuxPost.getString("createdTime"));
                    } catch (UniformInterfaceException ex) {
                        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updatePostWall: Comentarios actualizados!");
                        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updatePostWall: Nuevos comentarios al post: " + getStreamComents(jsonAuxPost.getString("postId"), "updateComment"));
                    }

                }
            }
        } catch (NullPointerException e) {
            logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updatePostWall: No existen novedades en el muro del usuario...");
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updatePostWall: Excepcion capturada NullPointerException: " + e.getMessage());
            logger.fatal(e);
        }
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updatePostWall: Fin updatePostWall...");
    }

    /**
     * Comprueba si un post obtenido de Facebook esta registrado en la base de datos.
     * 
     * @param comentario JSONObject con los datos del comentario.
     * @return boolean
     */
    public boolean isNewStreamPost(JSONObject comentario) {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isNewStreamPost: Inicio isNewStreamPost...");
        try {
            this.snsObject.getClient().userFacebook_getStreamComentByUid(String.class, comentario.getString("postId"));
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isNewStreamPost: El post existe dentro de la base de datos!!");
            return false;
        } catch (JSONException | UniformInterfaceException e) {
            logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isNewStreamPost: El post es nuevo y no esta registrado en la base de datos!!");
            logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isNewStreamPost: Excepcion capturada Exception: " + e.getMessage());
            logger.fatal(e);
            return true;
        }
    }
    
    /**
     * Obtiene el path del fichero badWords correspondiente a cada idioma configurado.
     * 
     * Devolver? el path al fichero en ingles por defecto y el path al fichero en
     * castellano si el idioma del usuario est? en espa?ol.
     * 
     * @param localeSettings
     * @return Path al fichero badWords correcto. 
     */
    private String getPathFileBadWords(String localeSettings){
        // Path del fichero badWords ingl?s.
        String path = this.snsObject.getConfigurationManager().getPathLexicalFiles() + PATH_LEXICAL_FILE_EN;
        
        // Si el idioma es castellano, obtenemos su path.
        if(localeSettings.equals("00000002")){
            path = this.snsObject.getConfigurationManager().getPathLexicalFiles() + PATH_LEXICAL_FILE_ES;
        }
        
        return path;
    }
    
    /**
     * Carga el fichero badWords correspondiente al idioma configurado del usuario.
     * 
     * @return JSONArray con todas las palabras y expresiones del fichero.
     * @throws MalformedURLException
     * @throws IOException
     * @throws JSONException 
     */
    private JSONArray loadFileBadWords() throws MalformedURLException, IOException, JSONException{
        String path = this.getPathFileBadWords(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getLocaleSettings());
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - loadFileBadWords: Path a lexicalFile: " + path);

        File lexicalFile = new File(path);
        FileReader lexicalFileReader = new FileReader(lexicalFile);
        BufferedReader buffer = new BufferedReader(lexicalFileReader);

        return cargarFichero(buffer);
    }
    

    /**
     * Realiza la ejecucion del filtro de control del lenguaje a partir de los datos almacenados en la base de datos.
     * Puede lanzar excepciones del tipo FileNotFoundException, IOException, JSONException, NoSuchProviderException, MessagingException, ParseException o bsh.ParseException.
     *
     * @param request HttpServletRequest que contiene los par?metros de inicio de sesi?n del usuario en Facebook.
     * @param firstCheck Indica si es el chequeo inicial de informaci?n.
     * @param jsonFilter Contiene la configuraci?n del filtro de control de lenguaje definida por el usuario.
     * @return Lista en formato HTML con el resultado del chequeo.
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * @throws JSONException
     * @throws NoSuchProviderException
     * @throws MessagingException
     * @throws ParseException
     * @throws bsh.ParseException
     */
    public String checkPostWall(HttpServletRequest request, boolean firstCheck, JSONObject jsonFilter, Date lastCheck) throws FileNotFoundException, IOException, JSONException, NoSuchProviderException, MessagingException, ParseException, bsh.ParseException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkPostWall: Inicio checkPostWall...");
        String informe = "";
        String comentarios;
        String menDB[] = this.snsObject.getStringUtilities().stringToArray(this.snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getMailNotification());
        JSONArray badWordsArray;
        JSONArray lstComentarios;
        Boolean badWord;

        badWordsArray = loadFileBadWords();
        if (!this.snsObject.isNewConnection() && firstCheck) {
            comentarios = this.snsObject.getClient().userFacebook_getStreamFacebookByUid(String.class, "\"" + this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + "\"");
        } else {
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strLastCheck = formateador.format(this.snsObject.getDateTimeUtilities().formatTime(String.valueOf(lastCheck.getTime())));
            logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkPostWall: Nueva fecha: " + jsonFilter.getString("lastCheck"));
            comentarios = this.snsObject.getClient().userFacebook_getStreamFacebookByUpdatedTime(String.class, "\"" + this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + "\"", "'" + formateador.format(this.snsObject.getDateTimeUtilities().formatTime(jsonFilter.getString("lastCheck").replace("T", " "))) + "'", "'" + strLastCheck + "'");
        }

        if (!comentarios.equals("{}") && !comentarios.contains("error_code")) {
            JSONObject aux = new JSONObject(comentarios);
            try {
                comentarios = aux.getString("streamFacebook");

                if (!comentarios.equals("{}") && !comentarios.contains("error_code")) {
                    if (!comentarios.substring(0, 1).equals("[") && !comentarios.substring(comentarios.length() - 1, comentarios.length()).equals("]")) {
                        String auxComment = "[" + comentarios + "]";
                        comentarios = auxComment;
                    }

                    lstComentarios = new JSONArray(comentarios);
                    logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkPostWall: Nuevos comentarios obtenidos: " + lstComentarios.length());

                    for (int i = 0; i < lstComentarios.length(); i++) {
                        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkPostWall: Comentario: " + lstComentarios.getJSONObject(i).getString("message"));
                        badWord = isBadWords(lstComentarios.getJSONObject(i).getString("message"), badWordsArray);

                        if (badWord) {
                            informe += menDB[2] + "\"" + lstComentarios.getJSONObject(i).getString("message") + "\" " + menDB[4] + " <br>";
                        }

                        informe = checkCommentPost(lstComentarios.getJSONObject(i), badWordsArray, informe, firstCheck, lastCheck);
                    }
                }
            } catch (JSONException | IOException | ParseException e) {
                logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkPostWall: No existen nuevos comentarios...");
                logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkPostWall: Excepcion capturada Exception: " + e.getMessage());
                logger.fatal(e);
            }

            if (informe.equals("")) {
                informe = menDB[5];
            }
        }

        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkPostWall: Fin checkPostWall...");
        return informe;
    }

    /**
     * Determina si un comentario contiene lenguaje ofensivo e introduce en el campo 'informe' las anomalias detectadas.
     * Puede lanzar excepciones del tipo JSONException, ParseException o bsh.ParseException.
     *
     * @param streamComment JSONObject con el comentario a analizar.
     * @param badWordsArray JSONArray con todas las expresiones consideradas como lenguaje ofensivo.
     * @param informe String con el informe realizado hasta ahora.
     * @param firstCheck Indica si estamos en el chequeo inicial de la aplicaci?n.
     * @param jsonFilter Configuraci?n del filtro.
     * @return Par?metro de entrada informe actualizado si se ha detectado lenguaje ofensivo.
     *
     * @throws JSONException
     * @throws ParseException
     * @throws bsh.ParseException
     */
    public String checkCommentPost(JSONObject streamComment, JSONArray badWordsArray, String informe, boolean firstCheck, Date lastCheck) throws JSONException, ParseException, bsh.ParseException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkCommentPost: Inicio checkCommentPost...");

        Boolean badWord;
        String commentPost;
        JSONObject auxCommentPost = null;
        JSONArray lstCommentPost;
        String menDB[] = this.snsObject.getStringUtilities().stringToArray(this.snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getMailNotification());

        if (!this.snsObject.isNewConnection() && firstCheck) {
            commentPost = this.snsObject.getClient().userFacebook_getComentsPostById(String.class, "\"" + streamComment.getString("postId") + "\"");
        } else {
            commentPost = this.snsObject.getClient().userFacebook_getComentsPostByTime(String.class, "\"" + streamComment.getString("postId") + "\"", lastCheck.getTime());
        }
        logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkCommentPost: Comentarios al post: " + commentPost);

        if (!commentPost.equals("{}") && !commentPost.contains("error_code")) {

            try {
                auxCommentPost = new JSONObject(commentPost);
                try {
                    lstCommentPost = auxCommentPost.getJSONArray("commentFacebook");
                } catch (Exception e) {
                    JSONArray comment = new JSONArray();
                    lstCommentPost = comment.put(auxCommentPost.getJSONObject("commentFacebook"));
                    logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkCommentPost: Excepcion capturada Exception: " + e.getMessage());
                    logger.fatal(e);
                }
                logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkCommentPost: Comentarios a analizar: " + lstCommentPost.length());

                for (int i = 0; i < lstCommentPost.length(); i++) {
                    logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkCommentPost: Mensaje del comentario: " + lstCommentPost.getJSONObject(i).getString("text"));
                    badWord = isBadWords(lstCommentPost.getJSONObject(i).getString("text"), badWordsArray);
                    if (badWord) {
                        informe += menDB[2] + "\"" + lstCommentPost.getJSONObject(i).getString("text") + "\"" + menDB[3]
                                + "\"" + streamComment.getString("message") + "\"" + menDB[4] + "<br>";

                    }
                }
            } catch (JSONException | IOException e) {
                logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkCommentPost: No existen nuevos comentarios...");
                logger.error(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkCommentPost: Excepcion capturada Exception: " + e.getMessage());
                logger.fatal(e);
            }
        }

        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - checkCommentPost: Fin checkCommentPost...");
        return informe;
    }

    /**
     * Carga el fichero que contiene las expresiones consideradas como lenguaje ofensivo.
     * Puede lanzar excepciones del tipo JSONException o IOException.
     *
     * @param buffer BufferedReader abierto del fichero.
     * @return JSONArray con todas las expresiones.
     * 
     * @throws JSONException
     * @throws IOException
     */
    public JSONArray cargarFichero(BufferedReader buffer) throws JSONException, IOException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - Inicio cargarFichero...");
        JSONArray aux = new JSONArray();
        String wordFile;
        int i = 0;

        while ((wordFile = buffer.readLine()) != null) {
            aux.put(i, wordFile);
            logger.debug(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - Palabra cargada: " + wordFile);
            i++;
        }

        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - Fin cargarFichero...");
        return aux;
    }

    /**
     * Analiza para una comentario si existe lenguaje ofensivo, devolviendo true en caso afirmativo. 
     * Puede lanzar excepciones del tipo IOException o JSONException.
     *
     * @param comentario String del comentario a analizar.
     * @param buffer JSONArray con las expresiones consideradas como vocabulario ofensivo.
     * @return boolean
     * @throws IOException
     * @throws JSONException
     */
    public boolean isBadWords(String comentario, JSONArray buffer) throws IOException, JSONException {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isBadWords: Inicio isBadWords para el comentario: " + comentario);
        Boolean isBadWordFound = false;
        String wordFile;
        int i = 0;
        
        // Obtenemos las palabras del comentario y las comparamos con las palabras del fichero cargado
        String[] arrayComm = comentario.split("\\ ");
            
        // Recorremos todas las palabras del comentario
        while(i < arrayComm.length && !isBadWordFound){
            // Obtenemos la palabra del comentario
            String wordComment = arrayComm[i].toLowerCase();
            int j = 0;
            
            // Para cada palabra del comentario, recorremos todas las palabras del diccionario del fichero cargado
            while(j < buffer.length() && !isBadWordFound){
                // Obtenemos la palabra del diccionario
                wordFile = buffer.getString(j).toLowerCase();
                
                // Si alguna palabra del diccionario es encontrada en el comentario, salimos indicando que hay vocabulario ofensivo en ?l
                if (wordComment.equals(wordFile) || wordComment.contains(wordFile)) {
                    logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isBadWords: Expresion \"" + wordFile + "\" encontrada en el comentario \"" + comentario + "\"");
                    isBadWordFound = true;
                }
                
                // Incrementamos el contador de palabras del diccionario
                j++;
            }
            
            // Incrementamos el contador de palabras del comentario recorridas
            i++;
        }

        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - isBadWords: Fin isBadWords para el comentario: " + comentario);
        return isBadWordFound;
    }

    @Override
    public String executeFilter(Map<String, Object> args) throws Exception {
        return checkPostWall((HttpServletRequest) args.get(ARGS_KEY_REQUEST), 
                (boolean) args.get(ARGS_KEY_FIRSTCHECK), 
                (JSONObject) args.get(ARGS_KEY_JSONFILTER), 
                (Date) args.get(ARGS_KEY_LASTCHECK));
    }

    @Override
    public void init(SNSAngelGuardFBManager snsObject, String id) {
        this.snsObject = snsObject;
        this.idFilter = id;
    }

    @Override
    public String getId() {
        return this.idFilter;
    }

    @Override
    public void updateInformationFacebook() throws Exception {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateInformationFacebook: Inicio updateInformationFacebook...");
        this.updatePostWall();
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - updateInformationFacebook: Fin updateInformationFacebook!!");
    }

    @Override
    public void getInformationFacebook() throws Exception {
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getInformationFacebook: Inicio getInformationFacebook...");
        this.getPostWall();
        logger.info(this.snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getUid() + " - getInformationFacebook: Fin getInformationFacebook!!");
    }
}
