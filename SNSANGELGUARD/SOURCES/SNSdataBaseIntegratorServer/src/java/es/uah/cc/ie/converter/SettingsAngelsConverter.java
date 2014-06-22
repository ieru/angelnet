/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.converter;

import es.uah.cc.ie.persistence.SettingsAngels;
import java.net.URI;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.ws.rs.core.UriBuilder;
import javax.persistence.EntityManager;
import es.uah.cc.ie.persistence.UserSettings;
import es.uah.cc.ie.persistence.SettingsFilter;
import java.util.Collection;

/**
 *
 * @author tote
 */
@XmlRootElement(name = "settingsAngels")
public class SettingsAngelsConverter {

    private SettingsAngels entity;
    private URI uri;
    private int expandLevel;

    /** Creates a new instance of SettingsAngelsConverter */
    public SettingsAngelsConverter() {
        entity = new SettingsAngels();
    }

    /**
     * Creates a new instance of SettingsAngelsConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     * @param expandLevel indicates the number of levels the entity graph should be expanded@param isUriExtendable indicates whether the uri can be extended
     */
    public SettingsAngelsConverter(SettingsAngels entity, URI uri, int expandLevel, boolean isUriExtendable) {
        this.entity = entity;
        this.uri = (isUriExtendable) ? UriBuilder.fromUri(uri).path(entity.getUidAngel() + "/").build() : uri;
        this.expandLevel = expandLevel;
        getSettingsFilterCollection();
        getUserSettingsCollection();
    }

    /**
     * Creates a new instance of SettingsAngelsConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     * @param expandLevel indicates the number of levels the entity graph should be expanded
     */
    public SettingsAngelsConverter(SettingsAngels entity, URI uri, int expandLevel) {
        this(entity, uri, expandLevel, false);
    }

    /**
     * Getter for uidAngel.
     *
     * @return value for uidAngel
     */
    @XmlElement
    public Integer getUidAngel() {
        return (expandLevel > 0) ? entity.getUidAngel() : null;
    }

    /**
     * Setter for uidAngel.
     *
     * @param value the value to set
     */
    public void setUidAngel(Integer value) {
        entity.setUidAngel(value);
    }

    /**
     * Getter for idAngel.
     *
     * @return value for idAngel
     */
    @XmlElement
    public String getIdAngel() {
        return (expandLevel > 0) ? entity.getIdAngel() : null;
    }

    /**
     * Setter for idAngel.
     *
     * @param value the value to set
     */
    public void setIdAngel(String value) {
        entity.setIdAngel(value);
    }

    /**
     * Getter for nameAngel.
     *
     * @return value for nameAngel
     */
    @XmlElement
    public String getNameAngel() {
        return (expandLevel > 0) ? entity.getNameAngel() : null;
    }

    /**
     * Setter for nameAngel.
     *
     * @param value the value to set
     */
    public void setNameAngel(String value) {
        entity.setNameAngel(value);
    }

    /**
     * Getter for imgAngel.
     *
     * @return value for imgAngel
     */
    @XmlElement
    public String getImgAngel() {
        return (expandLevel > 0) ? entity.getImgAngel() : null;
    }

    /**
     * Setter for imgAngel.
     *
     * @param value the value to set
     */
    public void setImgAngel(String value) {
        entity.setImgAngel(value);
    }

    /**
     * Getter for typeAngel.
     *
     * @return value for typeAngel
     */
    @XmlElement
    public String getTypeAngel() {
        return (expandLevel > 0) ? entity.getTypeAngel() : null;
    }

    /**
     * Setter for typeAngel.
     *
     * @param value the value to set
     */
    public void setTypeAngel(String value) {
        entity.setTypeAngel(value);
    }

    /**
     * Getter for acceptAngel.
     *
     * @return value for acceptAngel
     */
    @XmlElement
    public String getAcceptAngel() {
        return (expandLevel > 0) ? entity.getAcceptAngel() : null;
    }

    /**
     * Setter for acceptAngel.
     *
     * @param value the value to set
     */
    public void setAcceptAngel(String value) {
        entity.setAcceptAngel(value);
    }

    /**
     * Getter for confirmAngel.
     *
     * @return value for confirmAngel
     */
    @XmlElement
    public String getConfirmAngel() {
        return (expandLevel > 0) ? entity.getConfirmAngel() : null;
    }

    /**
     * Setter for acceptAngel.
     *
     * @param value the value to set
     */
    public void setConfirmAngel(String value) {
        entity.setConfirmAngel(value);
    }

    /**
     * Getter for userPropAngel.
     *
     * @return value for userPropAngel
     */
    @XmlElement
    public String getUserPropAngel() {
        return (expandLevel > 0) ? entity.getUserPropAngel() : null;
    }

    /**
     * @param userPropAngel the userPropAngel to set
     */
    public void setUserPropAngel(String userPropAngel) {
        entity.setUserPropAngel(userPropAngel);
    }


    /**
     * Getter for idFacebook.
     *
     * @return value for userPropAngel
     */
    @XmlElement
    public String getIdFacebook() {
        return (expandLevel > 0) ? entity.getIdFacebook() : null;
    }

    /**
     * @param idFacebook the idFacebook to set
     */
    public void setIdFacebook(String idFacebook) {
        entity.setIdFacebook(idFacebook);
    }
    

    /**
     * Getter for settingsFilterCollection.
     *
     * @return value for settingsFilterCollection
     */
    @XmlElement
    public SettingsFiltersConverter getSettingsFilterCollection() {
        if (expandLevel > 0) {
            if (entity.getSettingsFilterCollection() != null) {
                return new SettingsFiltersConverter(entity.getSettingsFilterCollection(), uri.resolve("settingsFilterCollection/"), expandLevel - 1);
            }
        }
        return null;
    }

    /**
     * Setter for settingsFilterCollection.
     *
     * @param value the value to set
     */
    public void setSettingsFilterCollection(SettingsFiltersConverter value) {
        entity.setSettingsFilterCollection((value != null) ? value.getEntities() : null);
    }

    /**
     * Getter for userSettingsCollection.
     *
     * @return value for userSettingsCollection
     */
    @XmlElement
    public UserSettingssConverter getUserSettingsCollection() {
        if (expandLevel > 0) {
            if (entity.getUserSettingsCollection() != null) {
                return new UserSettingssConverter(entity.getUserSettingsCollection(), uri.resolve("userSettingsCollection/"), expandLevel - 1);
            }
        }
        return null;
    }

    /**
     * Setter for userSettingsCollection.
     *
     * @param value the value to set
     */
    public void setUserSettingsCollection(UserSettingssConverter value) {
        entity.setUserSettingsCollection((value != null) ? value.getEntities() : null);
    }

    /**
     * Returns the URI associated with this converter.
     *
     * @return the uri
     */
    @XmlAttribute
    public URI getUri() {
        return uri;
    }

    /**
     * Sets the URI for this reference converter.
     *
     */
    public void setUri(URI uri) {
        this.uri = uri;
    }

    /**
     * Returns the SettingsAngels entity.
     *
     * @return an entity
     */
    @XmlTransient
    public SettingsAngels getEntity() {
        if (entity.getUidAngel() == null) {
            SettingsAngelsConverter converter = UriResolver.getInstance().resolve(SettingsAngelsConverter.class, uri);
            if (converter != null) {
                entity = converter.getEntity();
            }
        }
        return entity;
    }

    /**
     * Returns the resolved SettingsAngels entity.
     *
     * @return an resolved entity
     */
    public SettingsAngels resolveEntity(EntityManager em) {

        Collection<SettingsFilter> settingsFilterCollection = entity.getSettingsFilterCollection();
        Collection<SettingsFilter> newsettingsFilterCollection = new java.util.ArrayList<SettingsFilter>();
        if (settingsFilterCollection != null) {
            for (SettingsFilter item : settingsFilterCollection) {
                newsettingsFilterCollection.add(em.getReference(SettingsFilter.class, item.getIdFilter()));
            }
        }
        entity.setSettingsFilterCollection(newsettingsFilterCollection);

        Collection<UserSettings> userSettingsCollection = entity.getUserSettingsCollection();
        Collection<UserSettings> newuserSettingsCollection = new java.util.ArrayList<UserSettings>();
        if (userSettingsCollection != null) {
            for (UserSettings item : userSettingsCollection) {
                newuserSettingsCollection.add(em.getReference(UserSettings.class, item.getUid()));
            }
        }
        entity.setUserSettingsCollection(newuserSettingsCollection);

        return entity;
    }
}
