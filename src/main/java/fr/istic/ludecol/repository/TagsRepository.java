package fr.istic.ludecol.repository;

import fr.istic.ludecol.domain.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Tags entity.
 */
public interface TagsRepository extends JpaRepository<Tags,Long>{

}
