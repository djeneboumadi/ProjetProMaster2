package fr.istic.ludecol.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

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

    @Column(name = "id_picture")
    private Integer id_picture;

    @Column(name = "url_picture")
    private String url_picture;

    @Column(name = "height")
    private Long height;

    @Column(name = "width")
    private Long width;

    @Column(name = "matrix_position")
    private String matrix_position;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getId_picture() {
        return id_picture;
    }

    public void setId_picture(Integer id_picture) {
        this.id_picture = id_picture;
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
                ", id_picture='" + id_picture + "'" +
                ", url_picture='" + url_picture + "'" +
                ", height='" + height + "'" +
                ", width='" + width + "'" +
                ", matrix_position='" + matrix_position + "'" +
                '}';
    }
}
