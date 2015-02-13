package fr.istic.ludecol.repository;

import fr.istic.ludecol.domain.Pictures;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Pictures entity.
 */
public interface PicturesRepository extends JpaRepository<Pictures,Long>{

}
