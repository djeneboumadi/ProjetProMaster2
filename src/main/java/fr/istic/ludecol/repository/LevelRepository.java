package fr.istic.ludecol.repository;

import fr.istic.ludecol.domain.Level;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Level entity.
 */
public interface LevelRepository extends JpaRepository<Level,Long>{

}
