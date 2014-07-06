/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uah.cc.ie.service;

import es.uah.cc.ie.persistence.SettingsFilter;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.WebApplicationException;
import javax.persistence.NoResultException;
import javax.persistence.EntityManager;
import es.uah.cc.ie.persistence.SettingsAngels;
import es.uah.cc.ie.persistence.UserSettings;
import java.util.Collection;
import es.uah.cc.ie.converter.SettingsFilterConverter;
import com.sun.jersey.api.core.ResourceContext;
import java.util.Date;

/**
 *
 * @author tote
 */

public class SettingsFilterResource {
    @Context
    protected ResourceContext resourceContext;
    @Context
    protected UriInfo uriInfo;
    protected Integer id;
  
    /** Creates a new instance of SettingsFilterResource */
    public SettingsFilterResource() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get method for retrieving an instance of SettingsFilter identified by id in XML format.
     *
     * @param id identifier for the entity
     * @return an instance of SettingsFilterConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public SettingsFilterConverter get(@QueryParam("expandLevel")
                                        @DefaultValue("1")
    int expandLevel) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            return new SettingsFilterConverter(getEntity(), uriInfo.getAbsolutePath(), expandLevel);
        } finally {
            PersistenceService.getInstance().close();
        }
    }

    /**
     * Put method for updating an instance of SettingsFilter identified by id using XML as the input format.
     *
     * @param id identifier for the entity
     * @param data an SettingsFilterConverter entity that is deserialized from a XML stream
     */
    @PUT
    @Consumes({"application/xml", "application/json"})
    public void put(@QueryParam("mode") 
            @DefaultValue("1") String mode, 
            SettingsFilterConverter data) {
        
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            EntityManager em = persistenceSvc.getEntityManager();
            SettingsFilter entity = data.resolveEntity(em);
            
            // Si es una ejecucion del filtro, actualizamos la fecha de ejecucion
            if(mode.equals("1"))
                entity.setLastCheck(new Date());
            
            updateEntity(getEntity(), data.resolveEntity(em));
            persistenceSvc.commitTx();
        } finally {
            persistenceSvc.close();
        }
    }

    /**
     * Delete method for deleting an instance of SettingsFilter identified by id.
     *
     * @param id identifier for the entity
     */
    @DELETE
    public void delete() {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            deleteEntity(getEntity());
            persistenceSvc.commitTx();
        } finally {
            persistenceSvc.close();
        }
    }

    /**
     * Returns an instance of SettingsFilter identified by id.
     *
     * @param id identifier for the entity
     * @return an instance of SettingsFilter
     */
    protected SettingsFilter getEntity() {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        try {
            return (SettingsFilter) em.createQuery("SELECT e FROM SettingsFilter e where e.idFilter = :idFilter").setParameter("idFilter", id).getSingleResult();
        } catch (NoResultException ex) {
            throw new WebApplicationException(new Throwable("Resource for " + uriInfo.getAbsolutePath() + " does not exist."), 404);
        }
    }

    /**
     * Updates entity using data from newEntity.
     *
     * @param entity the entity to update
     * @param newEntity the entity containing the new data
     * @return the updated entity
     */
    private SettingsFilter updateEntity(SettingsFilter entity, SettingsFilter newEntity) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        Collection<SettingsAngels> settingsAngelsCollection = entity.getSettingsAngelsFilterCollection();
        Collection<SettingsAngels> settingsAngelsCollectionNew = newEntity.getSettingsAngelsFilterCollection();
        Collection<UserSettings> userSettingsCollection = entity.getUserSettingsCollection();
        Collection<UserSettings> userSettingsCollectionNew = newEntity.getUserSettingsCollection();
        entity = em.merge(newEntity);
        
        for (SettingsAngels value : settingsAngelsCollection) {
            if (!settingsAngelsCollectionNew.contains(value)) {
                value.getSettingsFilterCollection().remove(entity);
            }
        }
        for (SettingsAngels value : settingsAngelsCollectionNew) {
            if (!settingsAngelsCollection.contains(value)) {
                value.getSettingsFilterCollection().add(entity);
            }
        }
        
        for (UserSettings value : userSettingsCollection) {
            if (!userSettingsCollectionNew.contains(value)) {
                value.getSettingsFilterCollection().remove(entity);
            }
        }
        for (UserSettings value : userSettingsCollectionNew) {
            if (!userSettingsCollection.contains(value)) {
                value.getSettingsFilterCollection().add(entity);
            }
        }
        
        return entity;
    }

    /**
     * Deletes the entity.
     *
     * @param entity the entity to deletle
     */
    private void deleteEntity(SettingsFilter entity) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        
        for (SettingsAngels value : entity.getSettingsAngelsFilterCollection()) {
            value.getSettingsFilterCollection().remove(entity);
        }
        
        for (UserSettings value: entity.getUserSettingsCollection()) {
            value.getSettingsFilterCollection().remove(entity);
        }
        
        em.remove(entity);
    }

    /**
     * Returns a dynamic instance of SettingsAngelssResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of SettingsAngelssResource
     */
    @Path("settingsAngelsCollection/")
    public SettingsAngelssResource getSettingsAngelsCollectionResource() {
        SettingsAngelsCollectionResourceSub settingsAngelsCollectionResourceSub = resourceContext.getResource(SettingsAngelsCollectionResourceSub.class);
        settingsAngelsCollectionResourceSub.setParent(getEntity());
        return settingsAngelsCollectionResourceSub;
    }

    /**
     * Returns a dynamic instance of UserSettingsResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of UserSettingsResource
     */
    @Path("userSettingsCollection/")
    public UserSettingssResource getUserSettingsCollectionResource() {
        UserSettingsCollectionResourceSub userSettingsCollectionResourceSub = resourceContext.getResource(UserSettingsCollectionResourceSub.class);
        userSettingsCollectionResourceSub.setParent(getEntity());
        return userSettingsCollectionResourceSub;
    }

    public static class SettingsAngelsCollectionResourceSub extends SettingsAngelssResource {

        private SettingsFilter parent;

        public void setParent(SettingsFilter parent) {
            this.parent = parent;
        }

        @Override
        protected Collection<SettingsAngels> getEntities(int start, int max, String query) {
            Collection<SettingsAngels> result = new java.util.ArrayList<SettingsAngels>();
            int index = 0;
            for (SettingsAngels e : parent.getSettingsAngelsFilterCollection()) {
                if (index >= start && (index - start) < max) {
                    result.add(e);
                }
                index++;
            }
            return result;
        }
    }

    public static class UserSettingsCollectionResourceSub extends UserSettingssResource {

        private SettingsFilter parent;

        public void setParent(SettingsFilter parent) {
            this.parent = parent;
        }

        @Override
        protected Collection<UserSettings> getEntities(int start, int max, String query) {
            Collection<UserSettings> result = new java.util.ArrayList<UserSettings>();
            int index = 0;
            for (UserSettings e : parent.getUserSettingsCollection()) {
                if (index >= start && (index - start) < max) {
                    result.add(e);
                }
                index++;
            }
            
            return result;
        }
    }
}
