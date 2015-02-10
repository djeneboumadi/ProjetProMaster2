package fr.istic.ludecol.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Levels.
 */
@Entity
@Table(name = "T_LEVELS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Levels implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_level")
    private Integer id_level;

    @Column(name = "question")
    private String question;

    @Column(name = "species_list")
    private String species_list;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getId_level() {
        return id_level;
    }

    public void setId_level(Integer id_level) {
        this.id_level = id_level;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getSpecies_list() {
        return species_list;
    }

    public void setSpecies_list(String species_list) {
        this.species_list = species_list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Levels levels = (Levels) o;

        if (id != null ? !id.equals(levels.id) : levels.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Levels{" +
                "id=" + id +
                ", id_level='" + id_level + "'" +
                ", question='" + question + "'" +
                ", species_list='" + species_list + "'" +
                '}';
    }
}
