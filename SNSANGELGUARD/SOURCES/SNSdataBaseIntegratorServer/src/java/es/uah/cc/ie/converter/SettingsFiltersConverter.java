/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uah.cc.ie.converter;

import es.uah.cc.ie.persistence.SettingsFilter;
import java.net.URI;
import java.util.Collection;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.ArrayList;

/**
 *
 * @author tote
 */

@XmlRootElement(name = "settingsFilters")
public class SettingsFiltersConverter {
    private Collection<SettingsFilter> entities;
    private Collection<SettingsFilterConverter> items;
    private URI uri;
    private int expandLevel;
  
    /** Creates a new instance of SettingsFiltersConverter */
    public SettingsFiltersConverter() {
    }

    /**
     * Creates a new instance of SettingsFiltersConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     * @param expandLevel indicates the number of levels the entity graph should be expanded
     */
    public SettingsFiltersConverter(Collection<SettingsFilter> entities, URI uri, int expandLevel) {
        this.entities = entities;
        this.uri = uri;
        this.expandLevel = expandLevel;
        getSettingsFilter();
    }

    /**
     * Returns a collection of SettingsFilterConverter.
     *
     * @return a collection of SettingsFilterConverter
     */
    @XmlElement
    public Collection<SettingsFilterConverter> getSettingsFilter() {
        if (items == null) {
            items = new ArrayList<SettingsFilterConverter>();
        }
        if (entities != null) {
            items.clear();
            for (SettingsFilter entity : entities) {
                items.add(new SettingsFilterConverter(entity, uri, expandLevel, true));
            }
        }
        return items;
    }

    /**
     * Sets a collection of SettingsFilterConverter.
     *
     * @param a collection of SettingsFilterConverter to set
     */
    public void setSettingsFilter(Collection<SettingsFilterConverter> items) {
        this.items = items;
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
     * Returns a collection SettingsFilter entities.
     *
     * @return a collection of SettingsFilter entities
     */
    @XmlTransient
    public Collection<SettingsFilter> getEntities() {
        entities = new ArrayList<SettingsFilter>();
        if (items != null) {
            for (SettingsFilterConverter item : items) {
                entities.add(item.getEntity());
            }
        }
        return entities;
    }
}
