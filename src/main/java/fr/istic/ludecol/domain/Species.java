package fr.istic.ludecol.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

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

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "url_picture_species")
    private String url_picture_species;

    @Column(name = "is_in_encyclo")
    private Boolean is_in_encyclo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUrl_picture_species() {
        return url_picture_species;
    }

    public void setUrl_picture_species(String url_picture_species) {
        this.url_picture_species = url_picture_species;
    }

    public Boolean getIs_in_encyclo() {
        return is_in_encyclo;
    }

    public void setIs_in_encyclo(Boolean is_in_encyclo) {
        this.is_in_encyclo = is_in_encyclo;
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
                ", name='" + name + "'" +
                ", category='" + category + "'" +
                ", description='" + description + "'" +
                ", url_picture_species='" + url_picture_species + "'" +
                ", is_in_encyclo='" + is_in_encyclo + "'" +
                '}';
    }
}
