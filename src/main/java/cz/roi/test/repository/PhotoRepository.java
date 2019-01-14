package cz.roi.test.repository;

import cz.roi.test.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, String> {

    // todo make optimized photo method using @Query to use JPQL
}
