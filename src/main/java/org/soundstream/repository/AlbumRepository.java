package org.soundstream.repository;

import org.soundstream.model.Albums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Albums, Long> {
    Optional<Albums> findByAlbumNameIgnoreCase(String name);

    @Query("SELECT a FROM Album a JOIN a.songs s WHERE s.id = :songId")
    Albums findAlbumBySongId(@Param("songId") Long songId);
}
