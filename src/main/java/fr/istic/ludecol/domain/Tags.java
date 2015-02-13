package fr.istic.ludecol.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Tags.
 */
@Entity
@Table(name = "T_TAGS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tags implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "pos_x")
    private Long pos_x;

    @Column(name = "pos_y")
    private Long pos_y;
    
    @ManyToOne
    private Pictures picture ;
    
    @ManyToOne
    private User user ;
    
    @ManyToOne
    private Species species ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPos_x() {
        return pos_x;
    }

    public void setPos_x(Long pos_x) {
        this.pos_x = pos_x;
    }

    public Long getPos_y() {
        return pos_y;
    }

    public void setPos_y(Long pos_y) {
        this.pos_y = pos_y;
    }

    public Pictures getPicture() {
		return picture;
	}

	public void setPicture(Pictures picture) {
		this.picture = picture;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Species getSpecies() {
		return species;
	}

	public void setSpecies(Species species) {
		this.species = species;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Tags tags = (Tags) o;

        if (id != null ? !id.equals(tags.id) : tags.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Tags{" +
                "id=" + id +
                ", pos_x='" + pos_x + "'" +
                ", pos_y='" + pos_y + "'" +
                '}';
    }
}
