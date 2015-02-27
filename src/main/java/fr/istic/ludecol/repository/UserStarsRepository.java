package fr.istic.ludecol.repository;

import fr.istic.ludecol.domain.User;
import fr.istic.ludecol.domain.UserStars;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the UserStars entity.
 */
public interface UserStarsRepository extends JpaRepository<UserStars,Long>{

}
