package fr.istic.ludecol.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Pictures.
 */
@Entity
@Table(name = "T_PICTURES")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pictures implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "url_picture")
    private String url_picture;

    @Column(name = "height")
    private Long height;

    @Column(name = "width")
    private Long width;

    @Column(name = "matrix_position")
    private String matrix_position;
    
    @OneToMany(mappedBy="picture")
    private Collection<Levels> levels ;
    
    @OneToMany(mappedBy="picture")
    private Collection<Tags> Tags ;
    
    @ManyToOne
    private Pictures mother_picture ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl_picture() {
        return url_picture;
    }

    public void setUrl_picture(String url_picture) {
        this.url_picture = url_picture;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public String getMatrix_position() {
        return matrix_position;
    }

    public void setMatrix_position(String matrix_position) {
        this.matrix_position = matrix_position;
    }

    public Collection<Levels> getLevels() {
		return levels;
	}

	public void setLevels(Collection<Levels> levels) {
		this.levels = levels;
	}

	public Collection<Tags> getTags() {
		return Tags;
	}

	public void setTags(Collection<Tags> tags) {
		Tags = tags;
	}

	public Pictures getMother_picture() {
		return mother_picture;
	}

	public void setMother_picture(Pictures mother_picture) {
		this.mother_picture = mother_picture;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pictures pictures = (Pictures) o;

        if (id != null ? !id.equals(pictures.id) : pictures.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Pictures{" +
                "id=" + id +
                ", url_picture='" + url_picture + "'" +
                ", height='" + height + "'" +
                ", width='" + width + "'" +
                ", matrix_position='" + matrix_position + "'" +
                '}';
    }
}
