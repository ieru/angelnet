/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uah.cc.ie.snsangelguardfb.sources.filtersfuncionality;

import es.uah.cc.ie.snsangelguardfb.ILifeCycleFilter;
import es.uah.cc.ie.snsangelguardfb.SNSAngelGuardFBManager;
import java.util.Map;

/**
 * Clase que implementa el filtro de control de la privacidad.
 * 
 * @author tote
 */
public class PrivacyFilterFuncionality implements ILifeCycleFilter, IKeyArgsFilter {
    
    /** Identificador del filtro */
    private String idFilter;

    /** Clase Manager de la aplicacion */
    private SNSAngelGuardFBManager snsObject;

    /**
     * Constructor de clase.
     */
    public PrivacyFilterFuncionality() {
    }
    
    @Override
    public void init(SNSAngelGuardFBManager snsObject, String id) {
        this.snsObject = snsObject;
        this.idFilter = id;
    }

    @Override
    public String executeFilter(Map<String, Object> args) throws Exception {
        return "Filtro en construccion";
    }

    @Override
    public String getId() {
        return this.idFilter;
    }

    @Override
    public void updateInformationFacebook() throws Exception {}

    @Override
    public void getInformationFacebook() throws Exception {}

}
