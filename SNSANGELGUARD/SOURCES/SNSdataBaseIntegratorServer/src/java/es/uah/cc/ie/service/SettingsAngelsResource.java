/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uah.cc.ie.service;

import es.uah.cc.ie.persistence.SettingsAngels;
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
import es.uah.cc.ie.persistence.UserSettings;
import es.uah.cc.ie.persistence.SettingsFilter;
import java.util.Collection;
import es.uah.cc.ie.converter.SettingsAngelsConverter;
import com.sun.jersey.api.core.ResourceContext;

/**
 *
 * @author tote
 */

public class SettingsAngelsResource {
    @Context
    protected ResourceContext resourceContext;
    @Context
    protected UriInfo uriInfo;
    protected Integer id;
  
    /** Creates a new instance of SettingsAngelsResource */
    public SettingsAngelsResource() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get method for retrieving an instance of SettingsAngels identified by id in XML format.
     *
     * @param id identifier for the entity
     * @return an instance of SettingsAngelsConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public SettingsAngelsConverter get(@QueryParam("expandLevel")
                                       @DefaultValue("1")
    int expandLevel) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            return new SettingsAngelsConverter(getEntity(), uriInfo.getAbsolutePath(), expandLevel);
        } finally {
            PersistenceService.getInstance().close();
        }
    }

    /**
     * Put method for updating an instance of SettingsAngels identified by id using XML as the input format.
     *
     * @param id identifier for the entity
     * @param data an SettingsAngelsConverter entity that is deserialized from a XML stream
     */
    @PUT
    @Consumes({"application/xml", "application/json"})
    public void put(SettingsAngelsConverter data) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            EntityManager em = persistenceSvc.getEntityManager();
            updateEntity(getEntity(), data.resolveEntity(em));
            persistenceSvc.commitTx();
        } finally {
            persistenceSvc.close();
        }
    }

    /**
     * Delete method for deleting an instance of SettingsAngels identified by id.
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
     * Returns an instance of SettingsAngels identified by id.
     *
     * @param id identifier for the entity
     * @return an instance of SettingsAngels
     */
    protected SettingsAngels getEntity() {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        try {
            return (SettingsAngels) em.createQuery("SELECT e FROM SettingsAngels e where e.uidAngel = :uidAngel").setParameter("uidAngel", id).getSingleResult();
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
    private SettingsAngels updateEntity(SettingsAngels entity, SettingsAngels newEntity) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        Collection<SettingsFilter> settingsFilterCollection = entity.getSettingsFilterCollection();
        Collection<SettingsFilter> settingsFilterCollectionNew = newEntity.getSettingsFilterCollection();
        Collection<UserSettings> userSettingsCollection = entity.getUserSettingsCollection();
        Collection<UserSettings> userSettingsCollectionNew = newEntity.getUserSettingsCollection();
        entity = em.merge(newEntity);
        for (SettingsFilter value : settingsFilterCollection) {
            if (!settingsFilterCollectionNew.contains(value)) {
                value.getSettingsAngelsFilterCollection().remove(entity);
            }
        }
        for (SettingsFilter value : settingsFilterCollectionNew) {
            if (!settingsFilterCollection.contains(value)) {
                value.getSettingsAngelsFilterCollection().add(entity);
            }
        }
        for (UserSettings value : userSettingsCollection) {
            if (!userSettingsCollectionNew.contains(value)) {
                value.getSettingsAngelsCollection().remove(entity);
            }
        }
        for (UserSettings value : userSettingsCollectionNew) {
            if (!userSettingsCollection.contains(value)) {
                value.getSettingsAngelsCollection().add(entity);
            }
        }
        return entity;
    }

    /**
     * Deletes the entity.
     *
     * @param entity the entity to deletle
     */
    private void deleteEntity(SettingsAngels entity) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();

        for (SettingsFilter value : entity.getSettingsFilterCollection()) {
            value.getSettingsAngelsFilterCollection().remove(entity);
        }

        for (UserSettings value : entity.getUserSettingsCollection()) {
            value.getSettingsAngelsCollection().remove(entity);
        }

        em.remove(entity);
    }

    /**
     * Returns a dynamic instance of SettingsFiltersResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of SettingsFiltersResource
     */
    @Path("settingsFilterCollection/")
    public SettingsFiltersResource getSettingsFilterCollectionResource() {
        SettingsFilterCollectionResourceSub settingsFilterCollectionResourceSub = resourceContext.getResource(SettingsFilterCollectionResourceSub.class);
        settingsFilterCollectionResourceSub.setParent(getEntity());
        return settingsFilterCollectionResourceSub;
    }

    /**
     * Returns a dynamic instance of UserSettingssResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of UserSettingssResource
     */
    @Path("userSettingsCollection/")
    public UserSettingssResource getUserSettingsCollectionResource() {
        UserSettingsCollectionResourceSub userSettingsCollectionResourceSub = resourceContext.getResource(UserSettingsCollectionResourceSub.class);
        userSettingsCollectionResourceSub.setParent(getEntity());
        return userSettingsCollectionResourceSub;
    }

    public static class SettingsFilterCollectionResourceSub extends SettingsFiltersResource {

        private SettingsAngels parent;

        public void setParent(SettingsAngels parent) {
            this.parent = parent;
        }

        @Override
        protected Collection<SettingsFilter> getEntities(int start, int max, String query) {
            Collection<SettingsFilter> result = new java.util.ArrayList<SettingsFilter>();
            int index = 0;
            for (SettingsFilter e : parent.getSettingsFilterCollection()) {
                if (index >= start && (index - start) < max) {
                    result.add(e);
                }
                index++;
            }
            return result;
        }
    }

    public static class UserSettingsCollectionResourceSub extends UserSettingssResource {

        private SettingsAngels parent;

        public void setParent(SettingsAngels parent) {
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
