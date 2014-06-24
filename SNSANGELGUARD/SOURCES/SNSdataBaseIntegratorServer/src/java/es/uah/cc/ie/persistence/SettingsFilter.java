/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.persistence;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author tote
 */
@Entity
@Table(name = "settings_filter")
@NamedQueries({
    @NamedQuery(name = "SettingsFilter.findAll", query = "SELECT s FROM SettingsFilter s"),
    @NamedQuery(name = "SettingsFilter.findByIdFilter", query = "SELECT s FROM SettingsFilter s WHERE s.idFilter = :idFilter"),
    @NamedQuery(name = "SettingsFilter.findByFrecFilter", query = "SELECT s FROM SettingsFilter s WHERE s.frecFilter = :frecFilter"),
    @NamedQuery(name = "SettingsFilter.findByActiveFilter", query = "SELECT s FROM SettingsFilter s WHERE s.activeFilter = :activeFilter"),
    @NamedQuery(name = "SettingsFilter.findByLastCheck", query = "SELECT s FROM SettingsFilter s WHERE s.lastCheck = :lastCheck"),
    @NamedQuery(name = "SettingsFilter.findByTypeFilter", query = "SELECT s FROM SettingsFilter s WHERE s.typeFilter = :typeFilter")})
public class SettingsFilter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_filter")
    private Integer idFilter;
    @Basic(optional = false)
    @Column(name = "frec_filter")
    private String frecFilter;
    @Basic(optional = false)
    @Column(name = "active_filter")
    private String activeFilter;
    @Column(name = "last_check")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastCheck;
    @Basic(optional = false)
    @Column(name = "type_filter")
    private String typeFilter;
    @JoinTable(name = "user_settings_has_settings_filter", joinColumns = {
        @JoinColumn(name = "settings_filter_id_filter", referencedColumnName = "id_filter")}, inverseJoinColumns = {
        @JoinColumn(name = "user_settings_uid", referencedColumnName = "uid")})
    @ManyToMany
    private Collection<UserSettings> settingsFilterCollection;
    @JoinTable(name = "settings_filter_has_settings_angels", joinColumns = {
        @JoinColumn(name = "settings_filter_id_filter", referencedColumnName = "id_filter")}, inverseJoinColumns = {
        @JoinColumn(name = "settings_angels_uid_angel", referencedColumnName = "uid_angel")})
    @ManyToMany
    private Collection<SettingsAngels> settingsAngelsFilterCollection;

    public SettingsFilter() {
    }

    public SettingsFilter(Integer idFilter, String typeFilter) {
        this.idFilter = idFilter;
        this.typeFilter = typeFilter;
    }

    public SettingsFilter(Integer idFilter, String frecFilter, String activeFilter, Date lastCheck, String typeFilter) {
        this.idFilter = idFilter;
        this.typeFilter = typeFilter;
        this.frecFilter = frecFilter;
        this.activeFilter = activeFilter;
        this.lastCheck = lastCheck;
    }

    public Integer getIdFilter() {
        return idFilter;
    }

    public void setIdFilter(Integer userSettingsUid) {
        this.idFilter = userSettingsUid;
    }

    public String getFrecFilter() {
        return frecFilter;
    }

    public void setFrecFilter(String frecFilter) {
        this.frecFilter = frecFilter;
    }

    public String getActiveFilter() {
        return activeFilter;
    }

    public void setActiveFilter(String activeFilter) {
        this.activeFilter = activeFilter;
    }

    public Date getLastCheck() {
        return lastCheck;
    }

    public void setLastCheck(Date lastCheck) {
        this.lastCheck = lastCheck;
    }

    public String getTypeFilter() {
        return typeFilter;
    }

    public void setTypeFilter(String typeFilter) {
        this.typeFilter = typeFilter;
    }

    public Collection<SettingsAngels> getSettingsAngelsFilterCollection() {
        return settingsAngelsFilterCollection;
    }

    public void setSettingsAngelsFilterCollection(Collection<SettingsAngels> settingsAngelsFilterCollection) {
        this.settingsAngelsFilterCollection = settingsAngelsFilterCollection;
    }

    public Collection<UserSettings> getSettingsFilterCollection() {
        return settingsFilterCollection;
    }

    public void setSettingsFilterCollection(Collection<UserSettings> settingsFilterCollection) {
        this.settingsFilterCollection = settingsFilterCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFilter != null ? idFilter.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SettingsFilter)) {
            return false;
        }
        SettingsFilter other = (SettingsFilter) object;
        if ((this.idFilter == null && other.idFilter != null) || (this.idFilter != null && !this.idFilter.equals(other.idFilter))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.uah.cc.ie.persistence.SettingsFilter[idFilter=" + idFilter + "]";
    }
}
