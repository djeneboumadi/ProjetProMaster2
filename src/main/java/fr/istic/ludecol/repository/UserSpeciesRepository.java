package fr.istic.ludecol.repository;

import fr.istic.ludecol.domain.UserSpecies;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the UserSpecies entity.
 */
public interface UserSpeciesRepository extends JpaRepository<UserSpecies,Long>{

}
