/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.converter;

import es.uah.cc.ie.persistence.UserSettings;
import java.net.URI;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.ws.rs.core.UriBuilder;
import javax.persistence.EntityManager;
import es.uah.cc.ie.persistence.LocaleSettings;
import es.uah.cc.ie.persistence.SettingsAngels;
import es.uah.cc.ie.persistence.SettingsFilter;
import java.util.Collection;
import es.uah.cc.ie.persistence.User;

/**
 *
 * @author tote
 */
@XmlRootElement(name = "userSettings")
public class UserSettingsConverter {

    private UserSettings entity;
    private URI uri;
    private int expandLevel;

    /** Creates a new instance of UserSettingsConverter */
    public UserSettingsConverter() {
        entity = new UserSettings();
    }

    /**
     * Creates a new instance of UserSettingsConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     * @param expandLevel indicates the number of levels the entity graph should be expanded@param isUriExtendable indicates whether the uri can be extended
     */
    public UserSettingsConverter(UserSettings entity, URI uri, int expandLevel, boolean isUriExtendable) {
        this.entity = entity;
        this.uri = (isUriExtendable) ? UriBuilder.fromUri(uri).path(entity.getUid() + "/").build() : uri;
        this.expandLevel = expandLevel;
        getSettingsAngelsCollection();
        getLocaleSettings();
        getSettingsFilterCollection();
        getUser();
    }

    /**
     * Creates a new instance of UserSettingsConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     * @param expandLevel indicates the number of levels the entity graph should be expanded
     */
    public UserSettingsConverter(UserSettings entity, URI uri, int expandLevel) {
        this(entity, uri, expandLevel, false);
    }

    /**
     * Getter for uid.
     *
     * @return value for uid
     */
    @XmlElement
    public String getUid() {
        return (expandLevel > 0) ? entity.getUid() : null;
    }

    /**
     * Setter for uid.
     *
     * @param value the value to set
     */
    public void setUid(String value) {
        entity.setUid(value);
    }

    /**
     * Getter for userName.
     *
     * @return value for userName
     */
    @XmlElement
    public String getUserName() {
        return (expandLevel > 0) ? entity.getUserName() : null;
    }

    /**
     * Setter for userName.
     *
     * @param value the value to set
     */
    public void setUserName(String value) {
        entity.setUserName(value);
    }

    /**
     * Getter for userEmail.
     *
     * @return value for userEmail
     */
    @XmlElement
    public String getUserEmail() {
        return (expandLevel > 0) ? entity.getUserEmail() : null;
    }

    /**
     * Setter for userEmail.
     *
     * @param value the value to set
     */
    public void setUserEmail(String value) {
        entity.setUserEmail(value);
    }

    /**
     * Getter for legalAccepted.
     *
     * @return value for legalAccepted
     */
    @XmlElement
    public String getLegalAccepted() {
        return (expandLevel > 0) ? entity.getLegalAccepted() : null;
    }

    /**
     * Setter for legalAccepted.
     *
     * @param value the value to set
     */
    public void setLegalAccepted(String value) {
        entity.setLegalAccepted(value);
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
     * Getter for uidPublic.
     *
     * @return value for uidPublic
     */
    @XmlElement
    public String getUidPublic() {
        return (expandLevel > 0) ? entity.getUidPublic() : null;
    }

    /**
     * Setter for uidPublic.
     *
     * @param value the value to set
     */
    public void setUidPublic(String value) {
        entity.setUidPublic(value);
    }

    /**
     * Getter for appActivated.
     *
     * @return value for appActivated
     */
    @XmlElement
    public String getAppActivated() {
        return (expandLevel > 0) ? entity.getAppActivated() : null;
    }

    /**
     * Setter for appActivated.
     *
     * @param value the value to set
     */
    public void setAppActivated(String value) {
        entity.setAppActivated(value);
    }

    /**
     * Getter for userSession.
     *
     * @return value for userSession
     */
    @XmlElement
    public String getUserSession() {
        return (expandLevel > 0) ? entity.getUserSession() : null;
    }

    /**
     * Setter for userSession.
     *
     * @param value the value to set
     */
    public void setUserSession(String value) {
        entity.setUserSession(value);
    }

    /**
     * Getter for backupCheck.
     *
     * @return value for backupCheck
     */
    @XmlElement
    public Date getBackupCheck() {
        return (expandLevel > 0) ? entity.getBackupCheck() : null;
    }

    /**
     * Setter for backupCheck.
     *
     * @param value the value to set
     */
    public void setBackupCheck(Date value) {
        entity.setBackupCheck(value);
    }

    /**
     * Getter for settingsAngelsCollection.
     *
     * @return value for settingsAngelsCollection
     */
    @XmlElement
    public SettingsAngelssConverter getSettingsAngelsCollection() {
        if (expandLevel > 0) {
            if (entity.getSettingsAngelsCollection() != null) {
                return new SettingsAngelssConverter(entity.getSettingsAngelsCollection(), uri.resolve("settingsAngelsCollection/"), expandLevel - 1);
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
        entity.setSettingsAngelsCollection((value != null) ? value.getEntities() : null);
    }

    /**
     * Getter for localeSettings.
     *
     * @return value for localeSettings
     */
    @XmlElement
    public LocaleSettingsConverter getLocaleSettings() {
        if (expandLevel > 0) {
            if (entity.getLocaleSettings() != null) {
                return new LocaleSettingsConverter(entity.getLocaleSettings(), uri.resolve("localeSettings/"), expandLevel - 1, false);
            }
        }
        return null;
    }

    /**
     * Setter for localeSettings.
     *
     * @param value the value to set
     */
    public void setLocaleSettings(LocaleSettingsConverter value) {
        entity.setLocaleSettings((value != null) ? value.getEntity() : null);
    }


    /**
     * Getter for settingsFilter.
     *
     * @return value for settingsFilter
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
     * Setter for settingsFilter.
     *
     * @param value the value to set
     */
    public void setSettingsFilterCollection(SettingsFiltersConverter value) {
        entity.setSettingsFilterCollection((value != null) ? value.getEntities() : null);
    }

    /**
     * Getter for user.
     *
     * @return value for user
     */
    @XmlElement
    public UserConverter getUser() {
        if (expandLevel > 0) {
            if (entity.getUser() != null) {
                return new UserConverter(entity.getUser(), uri.resolve("user/"), expandLevel - 1, false);
            }
        }
        return null;
    }

    /**
     * Setter for user.
     *
     * @param value the value to set
     */
    public void setUser(UserConverter value) {
        entity.setUser((value != null) ? value.getEntity() : null);
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
     * Returns the UserSettings entity.
     *
     * @return an entity
     */
    @XmlTransient
    public UserSettings getEntity() {
        if (entity.getUid() == null) {
            UserSettingsConverter converter = UriResolver.getInstance().resolve(UserSettingsConverter.class, uri);
            if (converter != null) {
                entity = converter.getEntity();
            }
        }
        return entity;
    }

    /**
     * Returns the resolved UserSettings entity.
     *
     * @return an resolved entity
     */
    public UserSettings resolveEntity(EntityManager em) {
        Collection<SettingsAngels> settingsAngelsCollection = entity.getSettingsAngelsCollection();
        Collection<SettingsAngels> newsettingsAngelsCollection = new java.util.ArrayList<SettingsAngels>();
        if (settingsAngelsCollection != null) {
            for (SettingsAngels item : settingsAngelsCollection) {
                newsettingsAngelsCollection.add(em.getReference(SettingsAngels.class, item.getUidAngel()));
            }
        }
        entity.setSettingsAngelsCollection(newsettingsAngelsCollection);
        
        LocaleSettings localeSettings = entity.getLocaleSettings();
        if (localeSettings != null) {
            entity.setLocaleSettings(em.getReference(LocaleSettings.class, localeSettings.getIdLocale()));
        }
        
        Collection<SettingsFilter> settingsFilterCollection = entity.getSettingsFilterCollection();
        Collection<SettingsFilter> newsettingsFilterCollection = new java.util.ArrayList<SettingsFilter>();
        if (settingsFilterCollection != null) {
            for (SettingsFilter item: settingsFilterCollection) {
                newsettingsFilterCollection.add(em.getReference(SettingsFilter.class, item.getIdFilter()));
            }
        }
        entity.setSettingsFilterCollection(newsettingsFilterCollection);
        
        User user = entity.getUser();
        if (user != null) {
            entity.setUser(em.getReference(User.class, user.getUserSettingsUid()));
        }
        return entity;
    }
}
