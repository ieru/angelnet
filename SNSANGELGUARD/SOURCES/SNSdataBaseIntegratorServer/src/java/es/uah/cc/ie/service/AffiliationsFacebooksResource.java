/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uah.cc.ie.service;

import es.uah.cc.ie.persistence.AffiliationsFacebook;
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
import es.uah.cc.ie.persistence.TypeAffiliationsFacebook;
import es.uah.cc.ie.persistence.UserFacebook;
import es.uah.cc.ie.converter.AffiliationsFacebooksConverter;
import es.uah.cc.ie.converter.AffiliationsFacebookConverter;
import com.sun.jersey.api.core.ResourceContext;

/**
 *
 * @author tote
 */

@Path("/affiliationsFacebooks/")
public class AffiliationsFacebooksResource {
    @Context
    protected ResourceContext resourceContext;
    @Context
    protected UriInfo uriInfo;
  
    /** Creates a new instance of AffiliationsFacebooksResource */
    public AffiliationsFacebooksResource() {
    }

    /**
     * Get method for retrieving a collection of AffiliationsFacebook instance in XML format.
     *
     * @return an instance of AffiliationsFacebooksConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public AffiliationsFacebooksConverter get(@QueryParam("start")
                                              @DefaultValue("0")
    int start, @QueryParam("max")
               @DefaultValue("10")
    int max, @QueryParam("expandLevel")
             @DefaultValue("1")
    int expandLevel, @QueryParam("query")
                     @DefaultValue("SELECT e FROM AffiliationsFacebook e")
    String query) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            return new AffiliationsFacebooksConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), expandLevel);
        } finally {
            persistenceSvc.commitTx();
            persistenceSvc.close();
        }
    }

    /**
     * Post method for creating an instance of AffiliationsFacebook using XML as the input format.
     *
     * @param data an AffiliationsFacebookConverter entity that is deserialized from an XML stream
     * @return an instance of AffiliationsFacebookConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(AffiliationsFacebookConverter data) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            EntityManager em = persistenceSvc.getEntityManager();
            AffiliationsFacebook entity = data.resolveEntity(em);
            createEntity(data.resolveEntity(em));
            persistenceSvc.commitTx();
            return Response.created(uriInfo.getAbsolutePath().resolve(entity.getIdAffiliationsFacebook() + "/")).build();
        } finally {
            persistenceSvc.close();
        }
    }

    /**
     * Returns a dynamic instance of AffiliationsFacebookResource used for entity navigation.
     *
     * @return an instance of AffiliationsFacebookResource
     */
    @Path("{idAffiliationsFacebook}/")
    public AffiliationsFacebookResource getAffiliationsFacebookResource(@PathParam("idAffiliationsFacebook")
    Integer id) {
        AffiliationsFacebookResource affiliationsFacebookResource = resourceContext.getResource(AffiliationsFacebookResource.class);
        affiliationsFacebookResource.setId(id);
        return affiliationsFacebookResource;
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of AffiliationsFacebook instances
     */
    protected Collection<AffiliationsFacebook> getEntities(int start, int max, String query) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        return em.createQuery(query).setFirstResult(start).setMaxResults(max).getResultList();
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(AffiliationsFacebook entity) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        em.persist(entity);
        UserFacebook userFacebook = entity.getUserFacebook();
        if (userFacebook != null) {
            userFacebook.getAffiliationsFacebookCollection().add(entity);
        }
        TypeAffiliationsFacebook typeAffiliationsFacebook = entity.getTypeAffiliationsFacebook();
        if (typeAffiliationsFacebook != null) {
            typeAffiliationsFacebook.getAffiliationsFacebookCollection().add(entity);
        }
    }
}
