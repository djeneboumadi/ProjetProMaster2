package fr.istic.ludecol.repository;

import fr.istic.ludecol.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Picture entity.
 */
public interface PictureRepository extends JpaRepository<Picture,Long>{

}
