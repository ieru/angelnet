/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uah.cc.ie.service;

import es.uah.cc.ie.persistence.SettingsFilter;
import java.util.Collection;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.persistence.EntityManager;
import es.uah.cc.ie.persistence.SettingsAngels;
import es.uah.cc.ie.persistence.UserSettings;
import es.uah.cc.ie.converter.SettingsFiltersConverter;
import es.uah.cc.ie.converter.SettingsFilterConverter;
import com.sun.jersey.api.core.ResourceContext;
import java.util.Date;

/**
 *
 * @author tote
 */

@Path("/settingsFilters/")
public class SettingsFiltersResource {
    @Context
    protected ResourceContext resourceContext;
    @Context
    protected UriInfo uriInfo;
  
    /** Creates a new instance of SettingsFiltersResource */
    public SettingsFiltersResource() {
    }

    /**
     * Get method for retrieving a collection of SettingsFilter instance in XML format.
     *
     * @return an instance of SettingsFiltersConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public SettingsFiltersConverter get(@QueryParam("start")
                                         @DefaultValue("0")
    int start, @QueryParam("max")
               @DefaultValue("10")
    int max, @QueryParam("expandLevel")
             @DefaultValue("1")
    int expandLevel, @QueryParam("query")
                     @DefaultValue("SELECT e FROM SettingsFilter e")
    String query) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            return new SettingsFiltersConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), expandLevel);
        } finally {
            persistenceSvc.commitTx();
            persistenceSvc.close();
        }
    }

    /**
     * Post method for creating an instance of SettingsFilter using XML as the input format.
     *
     * @param data an SettingsFilterConverter entity that is deserialized from an XML stream
     * @return an instance of SettingsFilterConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(SettingsFilterConverter data) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            EntityManager em = persistenceSvc.getEntityManager();
            SettingsFilter entity = data.resolveEntity(em);
            entity.setLastCheck(new Date());
            createEntity(data.resolveEntity(em));
            persistenceSvc.commitTx();
            return Response.created(uriInfo.getAbsolutePath().resolve(entity.getIdFilter() + "/")).build();
        } finally {
            persistenceSvc.close();
        }
    }

    /**
     * Returns a dynamic instance of SettingsFilterResource used for entity navigation.
     *
     * @return an instance of SettingsFilterResource
     */
    @Path("{idFilter}/")
    public SettingsFilterResource getSettingsfltWallResource(@PathParam("idFilter")
    String id) {
        SettingsFilterResource settingsFilterResource = resourceContext.getResource(SettingsFilterResource.class);
        settingsFilterResource.setId(id);
        return settingsFilterResource;
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of SettingsFilter instances
     */
    protected Collection<SettingsFilter> getEntities(int start, int max, String query) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        return em.createQuery(query).setFirstResult(start).setMaxResults(max).getResultList();
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(SettingsFilter entity) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        em.persist(entity);
        for (SettingsAngels value : entity.getSettingsAngelsFilterCollection()) {
            value.getSettingsFilterCollection().add(entity);
        }

        for (UserSettings value : entity.getUserSettingsCollection()) {
            value.getSettingsFilterCollection().add(entity);
        }
    }
}
