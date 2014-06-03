/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.resources;

import es.uah.cc.ie.snsangelguardfb.SNSAngelGuardFBManager;

/**
 * Clase que contiene los recursos de idioma de la p?gina del tutorial de
 * inicio.
 *
 * @author josejavierblecuadepedro1
 */
public class TutorialInicioJSPControlerResources {
    
    /** Manager de la aplicaci?n */
    SNSAngelGuardFBManager snsObject;
    
    /** Titulo de la p?gina */
    private String titleTutInitHelp;
    
    /** Titulo para el bot?n de primera p?gina de la paginaci?n */
    private String titleFirstPage;
    
    /** Titulo para el bot?n de p?gina anterior de la paginaci?n */
    private String titlePreviousPage;
    
    /** Titulo para el bot?n de p?gina siguiente de la paginaci?n */
    private String titleNextPage;
    
    /** Titulo para el bot?n de ?ltima p?gina de la paginaci?n */
    private String titleLastPage;
    
    /** Descripciones de las pantallas a mostrar en el tutorial */
    private String descPagesTutorial;

    /**
     * Obtiene el t?tulo de la p?gina.
     * 
     * @return String
     */
    public String getTitleTutInitHelp() {
        return titleTutInitHelp;
    }

    /**
     * Obtiene el titulo para el bot?n de primera p?gina de la paginaci?n.
     * 
     * @return String
     */
    public String getTitleFirstPage() {
        return titleFirstPage;
    }

    /**
     * Obtiene el titulo para el bot?n de p?gina anterior de la paginaci?n.
     * 
     * @return String
     */
    public String getTitlePreviousPage() {
        return titlePreviousPage;
    }

    /**
     * Obtiene el titulo para el bot?n de p?gina siguiente de la paginaci?n.
     * 
     * @return String 
     */
    public String getTitleNextPage() {
        return titleNextPage;
    }

    /**
     * Obtiene el titulo para el bot?n de ?ltima p?gina de la paginaci?n.
     * 
     * @return String
     */
    public String getTitleLastPage() {
        return titleLastPage;
    }

    /**
     * Obtiene las descripciones de las pantallas a mostrar en el tutorial.
     * 
     * @return String
     */
    public String getDescPagesTutorial() {
        return descPagesTutorial;
    }

    /**
     * Constructor que recibe el objeto manager de la aplicaci?n.
     * 
     * @param snsObject 
     */
    public TutorialInicioJSPControlerResources(SNSAngelGuardFBManager snsObject) {
        this.snsObject = snsObject;
        this.titleTutInitHelp = this.snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getTitleTutInitHelp();
        this.titleFirstPage = this.snsObject.getStringUtilities().stringToArray(this.snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getTitlePagTutInitHelp())[0];
        this.titlePreviousPage = this.snsObject.getStringUtilities().stringToArray(this.snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getTitlePagTutInitHelp())[1];
        this.titleNextPage = this.snsObject.getStringUtilities().stringToArray(this.snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getTitlePagTutInitHelp())[2];
        this.titleLastPage = this.snsObject.getStringUtilities().stringToArray(this.snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getTitlePagTutInitHelp())[3];
        this.descPagesTutorial = this.snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getDesTutInitHelp();
    }
}
