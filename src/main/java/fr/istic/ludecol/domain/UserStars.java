package fr.istic.ludecol.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A UserStars.
 */
@Entity
@Table(name = "T_USERSTARS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserStars implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "level_played")
    private Boolean level_played;

    @Column(name = "nb_stars")
    private Integer nb_stars;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getLevel_played() {
        return level_played;
    }

    public void setLevel_played(Boolean level_played) {
        this.level_played = level_played;
    }

    public Integer getNb_stars() {
        return nb_stars;
    }

    public void setNb_stars(Integer nb_stars) {
        this.nb_stars = nb_stars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserStars userStars = (UserStars) o;

        if (id != null ? !id.equals(userStars.id) : userStars.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "UserStars{" +
                "id=" + id +
                ", level_played='" + level_played + "'" +
                ", nb_stars='" + nb_stars + "'" +
                '}';
    }
}
