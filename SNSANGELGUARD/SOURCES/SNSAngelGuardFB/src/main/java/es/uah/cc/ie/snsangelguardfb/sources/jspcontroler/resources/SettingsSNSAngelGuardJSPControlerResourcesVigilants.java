/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.resources;

import es.uah.cc.ie.snsangelguardfb.SNSAngelGuardFBManager;
import java.util.Iterator;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Clase de recursos de la pagina settingsSNSAngelGuard_Vigilants.jsp
 * 
 * @author josejavierblecuadepedro1
 */
public class SettingsSNSAngelGuardJSPControlerResourcesVigilants {
    
     /** URL a la imagen de carga entre p?ginas */
    public final String PATH_IMAGE_LOADING = "../resources/legalAccepted/load.gif";
    
    /** Clase Manager de la aplicacion */
    private SNSAngelGuardFBManager snsObject;
    
    /** Titulo de la pagina */
    private String titleSettVig;
    
    /** Titulo del contenedor de filtros */
    private String titleVigilantSettVig;
    
    /** Titulo del contenedor de angeles para seleccion de frecuencia */
    private String titleVigFrecSettVig;
    
    /** Titulos para las pesta?as del contenedor de los contactos de Facebook  */
    private String titleFbList;
    
    /** Titulo para cada contacto de Facebook */
    private String titleAngelSettAng;
    
    /** Titulos de los filtros disponibles */
    private JSONObject arrayVig;
    
    /** Descripcion para cada filtro */
    private JSONObject arrayDes;
    
    /** Titulos del contenedor de seleccion de frecuencia */
    private String[] arrayFrc;
    
    /** Titulo del contenedor de angeles */
    private String titleVigAngSettVig;
    
    /** Titulo para activar un vigilante */
    private String titleAltVigOn;
    
    /** Titulo para desactivar un vigilante */
    private String titleAltVigOff;
    
    /** Titulo para indicar que no se ha a?adido ningun angel */
    private String titleAlarmNoAngelsSelect;

    public SNSAngelGuardFBManager getSnsObject() {
        return snsObject;
    }

    public void setSnsObject(SNSAngelGuardFBManager snsObject) {
        this.snsObject = snsObject;
    }

    public String getTitleSettVig() {
        return titleSettVig;
    }

    public void setTitleSettVig(String titleSettVig) {
        this.titleSettVig = titleSettVig;
    }

    public String getTitleVigilantSettVig() {
        return titleVigilantSettVig;
    }

    public void setTitleVigilantSettVig(String titleVigilantSettVig) {
        this.titleVigilantSettVig = titleVigilantSettVig;
    }

    public String getTitleVigFrecSettVig() {
        return titleVigFrecSettVig;
    }

    public void setTitleVigFrecSettVig(String titleVigFrecSettVig) {
        this.titleVigFrecSettVig = titleVigFrecSettVig;
    }
    
    public String getTitleFbList() {
        return titleFbList;
    }

    public void setTitleFbList(String titleFbList) {
        this.titleFbList = titleFbList;
    }

    public String getTitleAngelSettAng() {
        return titleAngelSettAng;
    }

    public void setTitleAngelSettAng(String titleAngelSettAng) {
        this.titleAngelSettAng = titleAngelSettAng;
    }

    public JSONObject getArrayVig() {
        return arrayVig;
    }

    public void setArrayVig(JSONObject arrayVig) {
        this.arrayVig = arrayVig;
    }

    public JSONObject getArrayDes() {
        return arrayDes;
    }

    public void setArrayDes(JSONObject arrayDes) {
        this.arrayDes = arrayDes;
    }

    public String[] getArrayFrc() {
        return arrayFrc;
    }

    public void setArrayFrc(String[] arrayFrc) {
        this.arrayFrc = arrayFrc;
    }

    public String getTitleVigAngSettVig() {
        return titleVigAngSettVig;
    }

    public void setTitleVigAngSettVig(String titleVigAngSettVig) {
        this.titleVigAngSettVig = titleVigAngSettVig;
    }

    public String getTitleAltVigOn() {
        return titleAltVigOn;
    }

    public void setTitleAltVigOn(String titleAltVigOn) {
        this.titleAltVigOn = titleAltVigOn;
    }

    public String getTitleAltVigOff() {
        return titleAltVigOff;
    }

    public void setTitleAltVigOff(String titleAltVigOff) {
        this.titleAltVigOff = titleAltVigOff;
    }

    public String getTitleAlarmNoAngelsSelect() {
        return titleAlarmNoAngelsSelect;
    }

    public void setTitleAlarmNoAngelsSelect(String titleAlarmNoAngelsSelect) {
        this.titleAlarmNoAngelsSelect = titleAlarmNoAngelsSelect;
    }
    

    /**
     * Constructor de clase.
     * 
     * @param snsObject 
     */
    public SettingsSNSAngelGuardJSPControlerResourcesVigilants(SNSAngelGuardFBManager snsObject) throws JSONException {
        this.snsObject = snsObject;
        this.titleSettVig = this.snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getTitleSettVig();
        this.titleVigilantSettVig = this.snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getTitleVigilantSettVig();
        this.titleVigFrecSettVig = this.snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getTitleVigFrecSettVig();
        this.titleSettVig = snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getTitleSettVig();
        this.titleFbList = this.snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getTitleFbListSettAng();
        this.titleAngelSettAng = this.snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getSubTitleAngelSettAng();
        this.arrayVig = snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getTitleVigSettVig();
        this.arrayDes = replaceVigInDesc(snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getTitleVigDescriptionSettVig());
        this.arrayFrc = this.snsObject.getStringUtilities().stringToArray(snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getTitleVigFrecSelectSettVig());
        this.titleVigAngSettVig = this.snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getTitleVigAngSettVig();
        this.titleAltVigOn = this.snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getTitleActiveDesactiveVig().split(";")[0];
        this.titleAltVigOff = this.snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getTitleActiveDesactiveVig().split(";")[1];
        this.titleAlarmNoAngelsSelect = this.snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getWarnings().split(";")[14];
    }  
    
    /**
     * Metodo que reemplaza en cada descripci?n de cada vigilante su nombre 
     * correspondiente para ser mostrado por pantalla.
     * 
     * @param desc
     *      Array con las descripciones de todos los angeles.
     * @return 
     *      Array con las descripciones personalizadas de todos los angeles.
     */
    public final JSONObject replaceVigInDesc(JSONObject desc) throws JSONException{
        final String REPLACE_PATRON = "[1]";
        
        if(desc != null){
            
            Iterator itActiveVigilants = this.snsObject.getConfigurationManager().getListActiveFilters().iterator();
            String key;
            
            while(itActiveVigilants.hasNext()){
                key = itActiveVigilants.next().toString();
                
                desc.put(key, this.snsObject.getStringUtilities().escaparComSimples(desc.getString(key).replace(REPLACE_PATRON, this.arrayVig.getString(key))));
            }
        }
        
        // Retornamos el resultado
        return desc;
    }
}