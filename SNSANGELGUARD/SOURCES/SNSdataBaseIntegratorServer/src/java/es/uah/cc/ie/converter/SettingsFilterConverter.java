/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.converter;

import es.uah.cc.ie.persistence.SettingsFilter;
import java.net.URI;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.ws.rs.core.UriBuilder;
import javax.persistence.EntityManager;
import es.uah.cc.ie.persistence.SettingsAngels;
import es.uah.cc.ie.persistence.UserSettings;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author tote
 */
@XmlRootElement(name = "settingsFilter")
public class SettingsFilterConverter {

    private SettingsFilter entity;
    private URI uri;
    private int expandLevel;

    /** Creates a new instance of SettingsFilterConverter */
    public SettingsFilterConverter() {
        entity = new SettingsFilter();
    }

    /**
     * Creates a new instance of SettingsFilterConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     * @param expandLevel indicates the number of levels the entity graph should be expanded@param isUriExtendable indicates whether the uri can be extended
     */
    public SettingsFilterConverter(SettingsFilter entity, URI uri, int expandLevel, boolean isUriExtendable) {
        this.entity = entity;
        this.uri = (isUriExtendable) ? UriBuilder.fromUri(uri).path(entity.getIdFilter() + "/").build() : uri;
        this.expandLevel = expandLevel;
        getSettingsAngelsCollection();
        getUserSettingsCollection();
    }

    /**
     * Creates a new instance of SettingsFilterConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     * @param expandLevel indicates the number of levels the entity graph should be expanded
     */
    public SettingsFilterConverter(SettingsFilter entity, URI uri, int expandLevel) {
        this(entity, uri, expandLevel, false);
    }

    /**
     * Getter for idFilter.
     *
     * @return value for userSettingsUid
     */
    @XmlElement
    public Integer getIdFilter() {
        return (expandLevel > 0) ? entity.getIdFilter() : null;
    }

    /**
     * Setter for idFilter.
     *
     * @param value the value to set
     */
    public void setIdFilter(Integer value) {
        entity.setIdFilter(value);
    }

    /**
     * Getter for frecFilter.
     *
     * @return value for frecFilter
     */
    @XmlElement
    public String getFrecFilter() {
        return (expandLevel > 0) ? entity.getFrecFilter() : null;
    }

    /**
     * Setter for frecFilter.
     *
     * @param value the value to set
     */
    public void setFrecFilter(String value) {
        entity.setFrecFilter(value);
    }

    /**
     * Getter for activeFilter.
     *
     * @return value for activeFilter
     */
    @XmlElement
    public String getActiveFilter() {
        return (expandLevel > 0) ? entity.getActiveFilter() : null;
    }

    /**
     * Setter for activeFilter.
     *
     * @param value the value to set
     */
    public void setActiveFilter(String value) {
        entity.setActiveFilter(value);
    }

    /**
     * Getter for lastCheck.
     *
     * @return value for lastCheck
     */
    @XmlElement
    public Date getLastCheck() {
        return (expandLevel > 0) ? entity.getLastCheck() : null;
    }

    /**
     * Setter for lastCheck.
     *
     * @param value the value to set
     */
    public void setLastCheck(Date value) {
        entity.setLastCheck(value);
    }

    /**
     * Getter for typeFilter.
     * 
     * @return value for typeFilter 
     */
    @XmlElement
    public String getTypeFilter(){
        return (expandLevel > 0) ? entity.getTypeFilter() : null;
    }
    
    /**
     * Setter for typeFilter.
     * 
     * @param value the value to set 
     */
    public void setTypeFilter(String value) {
        entity.setTypeFilter(value);
    }
    
    /**
     * Getter for settingsAngelsCollection.
     *
     * @return value for settingsAngelsCollection
     */
    @XmlElement
    public SettingsAngelssConverter getSettingsAngelsCollection() {
        if (expandLevel > 0) {
            if (entity.getSettingsAngelsFilterCollection() != null) {
                return new SettingsAngelssConverter(entity.getSettingsAngelsFilterCollection(), uri.resolve("settingsAngelsCollection/"), expandLevel - 1);
            }
        }
        return null;
    }

    /**
     * Setter for settingsAngelsCollection.
     *
     * @param value the value to set
     */
    public void setSettingsAngelsCollection(SettingsAngelssConverter value) {
        entity.setSettingsAngelsFilterCollection((value != null) ? value.getEntities() : null);
    }
    
    /**
     * Getter for userSettingsCollection.
     *
     * @return value for userSettingsCollection
     */
    @XmlElement
    public UserSettingssConverter getUserSettingsCollection() {
        if (expandLevel > 0) {
            if (entity.getSettingsFilterCollection() != null) {
                return new UserSettingssConverter(entity.getSettingsFilterCollection(), uri.resolve("userSettingsCollection/"), expandLevel - 1);
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
        entity.setSettingsFilterCollection((value != null) ? value.getEntities() : null);
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
     * Returns the SettingsFilter entity.
     *
     * @return an entity
     */
    @XmlTransient
    public SettingsFilter getEntity() {
        if (entity.getIdFilter() == null) {
            SettingsFilterConverter converter = UriResolver.getInstance().resolve(SettingsFilterConverter.class, uri);
            if (converter != null) {
                entity = converter.getEntity();
            }
        }
        return entity;
    }

    /**
     * Returns the resolved SettingsFilter entity.
     *
     * @return an resolved entity
     */
    public SettingsFilter resolveEntity(EntityManager em) {
        Collection<SettingsAngels> settingsAngelsCollection = entity.getSettingsAngelsFilterCollection();
        Collection<SettingsAngels> newsettingsAngelsCollection = new java.util.ArrayList<SettingsAngels>();
        if (settingsAngelsCollection != null) {
            for (SettingsAngels item : settingsAngelsCollection) {
                newsettingsAngelsCollection.add(em.getReference(SettingsAngels.class, item.getUidAngel()));
            }
        }
        entity.setSettingsAngelsFilterCollection(newsettingsAngelsCollection);
        
        Collection<UserSettings> userSettingsCollection = entity.getSettingsFilterCollection();
        Collection<UserSettings> newuserSettingsCollection = new java.util.ArrayList<UserSettings>();
        if (userSettingsCollection != null) {
            for(UserSettings userSettingsItem : userSettingsCollection) {
                newuserSettingsCollection.add(em.getReference(UserSettings.class, userSettingsItem.getUid()));
            }
        }
        entity.setSettingsFilterCollection(newuserSettingsCollection);
        
        return entity;
    }
}
