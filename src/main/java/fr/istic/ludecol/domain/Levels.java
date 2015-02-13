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

    @Column(name = "question")
    private String question;
    
    @Column(name = "difficulty")
    private int difficulty;

    @Column(name = "species_list")
    private String species_list;
    
    @ManyToOne
    private Pictures picture;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public String getSpecies_list() {
        return species_list;
    }

    public void setSpecies_list(String species_list) {
        this.species_list = species_list;
    }

    public Pictures getPicture() {
		return picture;
	}

	public void setPicture(Pictures picture) {
		this.picture = picture;
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
                ", difficulty='" + difficulty + "'" +
                ", question='" + question + "'" +
                ", species_list='" + species_list + "'" +
                '}';
    }
}
