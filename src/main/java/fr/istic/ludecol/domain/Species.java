package fr.istic.ludecol.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Species.
 */
@Entity
@Table(name = "T_SPECIES")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Species implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_species")
    private Integer id_species;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "picture_spe")
    private String picture_spe;

    @Column(name = "is_in_encyclo")
    private Boolean is_in_encyclo;

    @OneToMany(mappedBy = "species")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tag> tags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getId_species() {
        return id_species;
    }

    public void setId_species(Integer id_species) {
        this.id_species = id_species;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture_spe() {
        return picture_spe;
    }

    public void setPicture_spe(String picture_spe) {
        this.picture_spe = picture_spe;
    }

    public Boolean getIs_in_encyclo() {
        return is_in_encyclo;
    }

    public void setIs_in_encyclo(Boolean is_in_encyclo) {
        this.is_in_encyclo = is_in_encyclo;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Species species = (Species) o;

        if (id != null ? !id.equals(species.id) : species.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Species{" +
                "id=" + id +
                ", id_species='" + id_species + "'" +
                ", name='" + name + "'" +
                ", category='" + category + "'" +
                ", description='" + description + "'" +
                ", picture_spe='" + picture_spe + "'" +
                ", is_in_encyclo='" + is_in_encyclo + "'" +
                '}';
    }
}
