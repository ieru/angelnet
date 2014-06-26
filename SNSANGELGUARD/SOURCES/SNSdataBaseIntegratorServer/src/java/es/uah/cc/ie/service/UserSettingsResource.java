/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.service;

import es.uah.cc.ie.persistence.UserSettings;
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
import es.uah.cc.ie.persistence.LocaleSettings;
import es.uah.cc.ie.persistence.SettingsAngels;
import es.uah.cc.ie.persistence.SettingsFilter;
import java.util.Collection;
import es.uah.cc.ie.converter.UserSettingsConverter;
import com.sun.jersey.api.core.ResourceContext;
import es.uah.cc.ie.persistence.User;
import java.util.Date;

/**
 *
 * @author tote
 */
public class UserSettingsResource {

    @Context
    protected ResourceContext resourceContext;
    @Context
    protected UriInfo uriInfo;
    protected String id;

    /** Creates a new instance of UserSettingsResource */
    public UserSettingsResource() {
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get method for retrieving an instance of UserSettings identified by id in XML format.
     *
     * @param id identifier for the entity
     * @return an instance of UserSettingsConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public UserSettingsConverter get(@QueryParam("expandLevel")
            @DefaultValue("1") int expandLevel) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            return new UserSettingsConverter(getEntity(), uriInfo.getAbsolutePath(), expandLevel);
        } finally {
            PersistenceService.getInstance().close();
        }
    }

    /**
     * Put method for updating an instance of UserSettings identified by id using XML as the input format.
     *
     * @param id identifier for the entity
     * @param data an UserSettingsConverter entity that is deserialized from a XML stream
     */
    @PUT
    @Consumes({"application/xml", "application/json"})
    public void updateLastCheckUserSettings(@QueryParam("mode")
            @DefaultValue("1") String mode,
            @QueryParam("oauthToken") String oauthToken) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            EntityManager em = persistenceSvc.getEntityManager();
            UserSettings entity = em.find(UserSettings.class, id);
            if(mode.equals("1")){
                entity.setLastCheck(new Date());
            }
            else{
                entity.setBackupCheck(new Date());
            }
            entity.setUserSession(oauthToken);
            persistenceSvc.commitTx();
        } finally {
            persistenceSvc.close();
        }
    }

    /**
     * Delete method for deleting an instance of UserSettings identified by id.
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
     * Returns an instance of UserSettings identified by id.
     *
     * @param id identifier for the entity
     * @return an instance of UserSettings
     */
    protected UserSettings getEntity() {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        try {
            return (UserSettings) em.createQuery("SELECT e FROM UserSettings e where e.uid = :uid").setParameter("uid", id).getSingleResult();
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
    private UserSettings updateEntity(UserSettings entity, UserSettings newEntity) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        Collection<SettingsAngels> settingsAngelsCollection = entity.getSettingsAngelsCollection();
        Collection<SettingsAngels> settingsAngelsCollectionNew = newEntity.getSettingsAngelsCollection();
        Collection<SettingsFilter> settingsFilterCollection = entity.getSettingsFilterCollection();
        Collection<SettingsFilter> settingsFilterCollectionNew = newEntity.getSettingsFilterCollection();
        LocaleSettings localeSettings = entity.getLocaleSettings();
        LocaleSettings localeSettingsNew = newEntity.getLocaleSettings();
        User user = entity.getUser();
        User userNew = newEntity.getUser();
        entity = em.merge(newEntity);
        
        for (SettingsAngels value : settingsAngelsCollection) {
            if (!settingsAngelsCollectionNew.contains(value)) {
                value.getUserSettingsCollection().remove(entity);
            }
        }
        for (SettingsAngels value : settingsAngelsCollectionNew) {
            if (!settingsAngelsCollection.contains(value)) {
                value.getUserSettingsCollection().add(entity);
            }
        }
        
        for (SettingsFilter value : settingsFilterCollection) {
            if (!settingsFilterCollectionNew.contains(value)) {
                value.getUserSettingsCollection().remove(entity);
            }
        }
        for (SettingsFilter value : settingsFilterCollectionNew) {
            if (!settingsFilterCollection.contains(value)) {
                value.getUserSettingsCollection().add(entity);
            }
        }
        
        if (localeSettings != null && !localeSettings.equals(localeSettingsNew)) {
            localeSettings.getUserSettingsCollection().remove(entity);
        }
        if (localeSettingsNew != null && !localeSettingsNew.equals(localeSettings)) {
            localeSettingsNew.getUserSettingsCollection().add(entity);
        }
        if (user != null && !user.equals(userNew)) {
            user.setUserSettings(null);
        }
        if (userNew != null && !userNew.equals(user)) {
            userNew.setUserSettings(entity);
        }
        return entity;
    }

    /**
     * Deletes the entity.
     *
     * @param entity the entity to deletle
     */
    private void deleteEntity(UserSettings entity) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        for (SettingsAngels value : entity.getSettingsAngelsCollection()) {
            value.getUserSettingsCollection().remove(entity);
        }
        LocaleSettings localeSettings = entity.getLocaleSettings();
        if (localeSettings != null) {
            localeSettings.getUserSettingsCollection().remove(entity);
        }
        User user = entity.getUser();
        if (user != null) {
            user.setUserSettings(null);
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
     * Returns a dynamic instance of SettingsFilterResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of SettingsFilterResource
     */
    @Path("settingsFilterCollection/")
    public SettingsFiltersResource getSettingsFilterCollectionResource() {
        SettingsFilterCollectionResourceSub settingsFilterCollectionResourceSub = resourceContext.getResource(SettingsFilterCollectionResourceSub.class);
        settingsFilterCollectionResourceSub.setParent(getEntity());
        return settingsFilterCollectionResourceSub;
    }

    /**
     * Returns a dynamic instance of LocaleSettingsResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of LocaleSettingsResource
     */
    @Path("localeSettings/")
    public LocaleSettingsResource getLocaleSettingsResource() {
        LocaleSettingsResourceSub localeSettingsResourceSub = resourceContext.getResource(LocaleSettingsResourceSub.class);
        localeSettingsResourceSub.setParent(getEntity());
        return localeSettingsResourceSub;
    }

    /**
     * Returns a dynamic instance of UserResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of UserResource
     */
    @Path("user/")
    public UserResource getUserResource() {
        UserResourceSub userResourceSub = resourceContext.getResource(UserResourceSub.class);
        userResourceSub.setParent(getEntity());
        return userResourceSub;
    }

    public static class SettingsAngelsCollectionResourceSub extends SettingsAngelssResource {

        private UserSettings parent;

        public void setParent(UserSettings parent) {
            this.parent = parent;
        }

        @Override
        protected Collection<SettingsAngels> getEntities(int start, int max, String query) {
            Collection<SettingsAngels> result = new java.util.ArrayList<SettingsAngels>();
            int index = 0;
            for (SettingsAngels e : parent.getSettingsAngelsCollection()) {
                if (index >= start && (index - start) < max) {
                    result.add(e);
                }
                index++;
            }
            return result;
        }
    }
    
    public static class SettingsFilterCollectionResourceSub extends SettingsFiltersResource {

        private UserSettings parent;

        public void setParent(UserSettings parent) {
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

    public static class LocaleSettingsResourceSub extends LocaleSettingsResource {

        private UserSettings parent;

        public void setParent(UserSettings parent) {
            this.parent = parent;
        }

        @Override
        protected LocaleSettings getEntity() {
            LocaleSettings entity = parent.getLocaleSettings();
            if (entity == null) {
                throw new WebApplicationException(new Throwable("Resource for " + uriInfo.getAbsolutePath() + " does not exist."), 404);
            }
            return entity;
        }
    }

    public static class UserResourceSub extends UserResource {

        private UserSettings parent;

        public void setParent(UserSettings parent) {
            this.parent = parent;
        }

        @Override
        protected User getEntity() {
            User entity = parent.getUser();
            if (entity == null) {
                throw new WebApplicationException(new Throwable("Resource for " + uriInfo.getAbsolutePath() + " does not exist."), 404);
            }
            return entity;
        }
    }
}
