package cz.roi.test.repository;

import cz.roi.test.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, String> {

    @Query("SELECT p FROM Photo p WHERE p.user.id = :fbId")
    Optional<List<Photo>> findAllByUserId(@Param("fbId") String fbId);

}
